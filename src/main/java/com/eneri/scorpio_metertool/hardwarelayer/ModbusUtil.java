/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eneri.scorpio_metertool.hardwarelayer;

import com.eneri.scorpio_metertool.bus.Conversions;
import com.eneri.scorpio_metertool.bus.ModbusRTUCRC16;
import com.eneri.scorpio_metertool.devices.Meter;
import com.eneri.scorpio_metertool.ui.FirmwareUpload;
import java.io.PrintStream;
import java.util.Arrays;

/**
 *
 * @author Roberto
 */
public class ModbusUtil
{
  private static ModbusUtil INSTANCE;
  public byte[] frame;
  
  public byte[] readRegister(String snMeter, int startAddres, int wordsNumber, byte actionRW)
  {
    String serialNumber = snMeter;
    byte[] snBytes = Meter.getSerialNumberBytes(serialNumber);
    this.frame = new byte[24];
    
    System.arraycopy(snBytes, 0, this.frame, 0, snBytes.length);
    this.frame[16] = 0;
    this.frame[17] = actionRW;
    this.frame[18] = 0;
    this.frame[19] = ((byte)startAddres);
    this.frame[20] = 0;
    this.frame[21] = ((byte)wordsNumber);
    
    int frameCRC = ModbusRTUCRC16.doCRC16(this.frame, 0, this.frame.length - 2);
    int frameCRCHi = frameCRC & 0xFF00;
    frameCRCHi >>>= 8;
    this.frame[22] = ((byte)frameCRCHi);
    this.frame[23] = ((byte)(frameCRC & 0xFF));
    
    return this.frame;
  }
  
  public byte[] writeRegister(String snMeter, int startAddres, byte[] data)
  {
    Conversions conv = new Conversions();
    PortComunication port = new PortComunication();
    boolean estateResponse = false;
    
    String serialNumber = snMeter;
    byte[] snBytes = Meter.getSerialNumberBytes(serialNumber);
    
    this.frame = new byte[24];
    
    System.arraycopy(snBytes, 0, this.frame, 0, snBytes.length);
    this.frame[16] = 0;
    this.frame[17] = 6;
    this.frame[18] = 0;
    this.frame[19] = ((byte)startAddres);
    this.frame[20] = data[0];
    this.frame[21] = data[1];
    
    int frameCRC = ModbusRTUCRC16.doCRC16(this.frame, 0, this.frame.length - 2);
    int frameCRCHi = frameCRC & 0xFF00;
    frameCRCHi >>>= 8;
    this.frame[22] = ((byte)frameCRCHi);
    this.frame[23] = ((byte)(frameCRC & 0xFF));
    
    return this.frame;
  }
  
  public byte[] writeMultipleRegisters(String snMeter, int startAddres, int wordsNumber, byte[] data, byte actionRW)
  {
    PortComunication port = new PortComunication();
    boolean estateResponse = false;
    
    byte[] snBytes = Meter.getSerialNumberBytes(snMeter);
    
    int numbytesCountwords = wordsNumber * 2;
    this.frame = new byte[24 + numbytesCountwords];
    
    System.arraycopy(snBytes, 0, this.frame, 0, snBytes.length);
    this.frame[16] = 0;
    this.frame[17] = actionRW;
    this.frame[18] = 0;
    this.frame[19] = ((byte)startAddres);
    this.frame[20] = 0;
    this.frame[21] = ((byte)wordsNumber);
    if (data.length > 0) {
      System.arraycopy(data, 0, this.frame, 22, data.length);
    }
    int frameCRC = ModbusRTUCRC16.doCRC16(this.frame, 0, this.frame.length - 2);
    int frameCRCHi = frameCRC & 0xFF00;
    frameCRCHi >>>= 8;
    this.frame[(22 + data.length)] = ((byte)frameCRCHi);
    this.frame[(23 + data.length)] = ((byte)(frameCRC & 0xFF));
    
    return this.frame;
  }
  
  public boolean writeFlagsPassw(FlagsPasswords values, String serialNumber)
  {
    boolean estateResponse = false;
    PortComunication port = new PortComunication();
    
    byte[] snBytes = Meter.getSerialNumberBytes(serialNumber);
    int valuereg = 0;
    int count = 0;
    byte[] flagpass = new byte[4];
    while (count <= 1)
    {
      if (count == 0)
      {
        valuereg = values.getPassReg();
        flagpass = values.getPass();
      }
      else if (count == 1)
      {
        valuereg = values.getFlagReg();
        flagpass = values.getFlag();
      }
      byte[] frame = new byte[28];
      
      System.arraycopy(snBytes, 0, frame, 0, snBytes.length);
      frame[16] = 0;
      frame[17] = 16;
      frame[18] = 0;
      frame[19] = ((byte)valuereg);
      frame[20] = 0;
      frame[21] = ((byte)values.getCountBytes());
      if (flagpass.length > 0) {
        System.arraycopy(flagpass, 0, frame, 22, flagpass.length);
      }
      int frameCRC = ModbusRTUCRC16.doCRC16(frame, 0, frame.length - 2);
      int frameCRCHi = frameCRC & 0xFF00;
      frameCRCHi >>>= 8;
      frame[(22 + flagpass.length)] = ((byte)frameCRCHi);
      frame[(23 + flagpass.length)] = ((byte)(frameCRC & 0xFF));
      
      port.write(frame, PortComunication.DELAY400);
      
      byte[] dato = port.getByteBuffer();
      if ((Arrays.equals(frame, dato) | FirmwareUpload.broadCast == true | values == FlagsPasswords.METER_FORCE_BOOTLOADER))
      {
        System.out.println("Correcto");
        count++;
      }
      else
      {
        count = 3;
      }
    }
    if (count == 2) {
      estateResponse = true;
    } else if ((count < 2) || (count > 2)) {
      estateResponse = false;
    }
    return estateResponse;
  }
  
  public static ModbusUtil getInstance()
  {
    return INSTANCE;
  }
}
