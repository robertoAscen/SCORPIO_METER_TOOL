/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eneri.scorpio_metertool.firmware;

import com.eneri.scorpio_metertool.bus.ModbusRTUCRC16;
import com.eneri.scorpio_metertool.devices.Meter;
import com.eneri.scorpio_metertool.devices.Register;
import com.eneri.scorpio_metertool.hardwarelayer.FlagsPasswords;
import com.eneri.scorpio_metertool.hardwarelayer.ModbusUtil;
import com.eneri.scorpio_metertool.hardwarelayer.PortComunication;
import com.eneri.scorpio_metertool.ui.FirmwareUpload;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Observable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import javax.swing.JOptionPane;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Roberto
 */
public class Firmware3PHProcessor
  extends Observable
{
  public static final int RTU_PAYLOAD_SIZE = 58;
  public static final int RTU_SIGNATURE_SIZE = 32;
  private static final int MEMORY_MAP_FLASH_APP_SIZE = 54272;
  private static final int ENERIBUS_RTU_PAYLOAD_INDEX = 24;
  private static final int MAX_MEMORY_OFFSET = 480;
  private static final int PAYLOAD_SIZE = 32;
  private static final int PAYLOAD_SIZE_HALF = 16;
  private byte[] memoryMap;
  private byte[] signature;
  public LinkedList<ModbusRTUFrame> listModbusFrames;
  public static org.slf4j.Logger log = LoggerFactory.getLogger(Firmware3PHProcessor.class);
  private final AtomicInteger atomicFrameCount;
  
  public Firmware3PHProcessor()
  {
    this.memoryMap = new byte[54272];
    this.signature = new byte[6];
    this.listModbusFrames = new LinkedList();
    
    this.atomicFrameCount = new AtomicInteger(0);
  }
  
  private static class SingletonHolder
  {
    public static final Firmware3PHProcessor INSTANCE = new Firmware3PHProcessor();
  }
  
  public static Firmware3PHProcessor getInstance()
  {
    return SingletonHolder.INSTANCE;
  }
  
  public synchronized int getTxTotal()
  {
    return this.listModbusFrames.size();
  }
  
  public int getTxCount()
  {
    return this.atomicFrameCount.get();
  }
  
  public int processHEXFile(File hexFile)
    throws IOException
  {
    if (hexFile == null)
    {
      JOptionPane.showMessageDialog(null, "Null hexFile. (Or clicked CANCEL?)");
      return -1;
    }
    this.listModbusFrames.clear();
    for (int idx = 0; idx < this.memoryMap.length; idx++) {
      this.memoryMap[idx] = -1;
    }
    if (!hexFile.exists()) {
      throw new IOException("HEX file not found.");
    }
    int memoryIndex = 0;
    int signatureIndex = 0;
    int frameCount = 0;
    
    int memoryOffset = 0;
    
    int lowestAddress = 65535;
    try
    {
      BufferedReader in = new BufferedReader(new FileReader(hexFile));
      String strLine;
      while ((strLine = in.readLine()) != null)
      {
        IntelHEXLine hexLine = new IntelHEXLine(strLine);
        hexLine.parseLine();
        if (hexLine.getAddress() == 4096) {
          for (byte b : hexLine.getData())
          {
            this.signature[signatureIndex] = b;
            signatureIndex++;
          }
        } else {
          for (byte b : hexLine.getData())
          {
            if ((hexLine.getAddress() != 4096) && 
              (hexLine.getAddress() < lowestAddress)) {
              lowestAddress = hexLine.getAddress();
            }
            this.memoryMap[memoryIndex] = b;
            memoryIndex++;
          }
        }
      }
    }
    catch (FileNotFoundException ex)
    {
      java.util.logging.Logger.getLogger(Firmware3PHProcessor.class.getName()).log(Level.SEVERE, null, ex);
      System.exit(-1);
    }
    catch (IOException ex)
    {
      java.util.logging.Logger.getLogger(Firmware3PHProcessor.class.getName()).log(Level.SEVERE, null, ex);
      System.exit(-1);
    }
    catch (Exception ex)
    {
      java.util.logging.Logger.getLogger(Firmware3PHProcessor.class.getName()).log(Level.SEVERE, null, ex);
      System.exit(-1);
    }
    int memoryPage = lowestAddress / 512 + 1;
    
    byte[] modbusFrame = new byte[58];
    int modbusFrameIndex = 24;
    for (int idx = 0; idx < this.memoryMap.length; idx++)
    {
      modbusFrame[modbusFrameIndex] = this.memoryMap[idx];
      modbusFrameIndex++;
      if ((idx + 1) % 32 == 0)
      {
        frameCount++;
        modbusFrameIndex = 24;
        if ((modbusFrame[24] == -1) && (modbusFrame[25] == -1) && (modbusFrame[26] == -1) && (modbusFrame[27] == -1)) {
          break;
        }
        modbusFrame[0] = 48;
        modbusFrame[1] = 48;
        modbusFrame[2] = 48;
        modbusFrame[3] = 48;
        modbusFrame[4] = 48;
        modbusFrame[5] = 48;
        modbusFrame[6] = 48;
        modbusFrame[7] = 48;
        modbusFrame[8] = 48;
        modbusFrame[9] = 48;
        modbusFrame[10] = 48;
        modbusFrame[11] = 48;
        modbusFrame[12] = 48;
        modbusFrame[13] = 48;
        modbusFrame[14] = 48;
        modbusFrame[15] = 48;
        modbusFrame[16] = 0;
        
        modbusFrame[17] = 21;
        
        int pageHi = memoryPage & 0xFF00;
        pageHi >>>= 8;
        modbusFrame[18] = ((byte)pageHi);
        modbusFrame[19] = ((byte)(memoryPage & 0xFF));
        
        int offsetHi = memoryOffset & 0xFF00;
        offsetHi >>>= 8;
        modbusFrame[20] = ((byte)offsetHi);
        modbusFrame[21] = ((byte)(memoryOffset & 0xFF));
        
        memoryOffset += 32;
        if (memoryOffset > 480)
        {
          memoryOffset = 0;
          memoryPage++;
        }
        modbusFrame[22] = 0;
        modbusFrame[23] = 16;
        
        int frameCRC = ModbusRTUCRC16.doCRC16(modbusFrame, 0, modbusFrame.length - 2);
        int frameCRCHi = frameCRC & 0xFF00;
        frameCRCHi >>>= 8;
        
        modbusFrame[56] = ((byte)frameCRCHi);
        modbusFrame[57] = ((byte)(frameCRC & 0xFF));
        
        ModbusRTUFrame frame = new ModbusRTUFrame(frameCount, modbusFrame, false);
        this.listModbusFrames.add(frame);
      }
    }
    frameCount++;
    
    int memoryMapCRC = ModbusRTUCRC16.doCRC16(this.memoryMap);
    int memoryMapCRCHi = memoryMapCRC & 0xFF00;
    memoryMapCRCHi >>>= 8;
    for (int idx = 0; idx < this.signature.length; idx++) {
      switch (idx)
      {
      case 4: 
        this.signature[idx] = ((byte)(memoryMapCRC & 0xFF));
        break;
      case 5: 
        this.signature[idx] = ((byte)memoryMapCRCHi);
      }
    }
    modbusFrame = new byte[32];
    
    modbusFrame[0] = 48;
    modbusFrame[1] = 48;
    modbusFrame[2] = 48;
    modbusFrame[3] = 48;
    modbusFrame[4] = 48;
    modbusFrame[5] = 48;
    modbusFrame[6] = 48;
    modbusFrame[7] = 48;
    modbusFrame[8] = 48;
    modbusFrame[9] = 48;
    modbusFrame[10] = 48;
    modbusFrame[11] = 48;
    modbusFrame[12] = 48;
    modbusFrame[13] = 48;
    modbusFrame[14] = 48;
    modbusFrame[15] = 48;
    modbusFrame[16] = 0;
    modbusFrame[17] = 21;
    
    modbusFrame[18] = 0;
    modbusFrame[19] = 9;
    
    modbusFrame[20] = 0;
    modbusFrame[21] = 0;
    
    modbusFrame[22] = 0;
    modbusFrame[23] = 3;
    System.arraycopy(this.signature, 0, modbusFrame, 24, this.signature.length);
    
    int frameCRC = ModbusRTUCRC16.doCRC16(modbusFrame, 0, modbusFrame.length - 2);
    int frameCRCHi = frameCRC & 0xFF00;
    frameCRCHi >>>= 8;
    
    modbusFrame[30] = ((byte)frameCRCHi);
    modbusFrame[31] = ((byte)(frameCRC & 0xFF));
    
    ModbusRTUFrame frame = new ModbusRTUFrame(frameCount, modbusFrame, true);
    this.listModbusFrames.add(frame);
    
    log.info("Memory Map CRC: 0x{} for FILE: \"{}\"", new Object[] { Integer.toHexString(memoryMapCRC), hexFile.getName() });
    
    return memoryMapCRC;
  }
  
  private void tomDelay(long delay)
  {
    try
    {
      Thread.sleep(delay);
    }
    catch (InterruptedException ex) {}
  }
  
  public void startUpload(int delay, boolean broadcast)
  {
    ModbusUtil modb = new ModbusUtil();
    boolean isBroadcast = false;
    String serialNumber = "";
    if (((Meter.getSerialNumberCompl() == null ? 1 : 0) & (!FirmwareUpload.broadCast ? 1 : 0)) != 0) {
      return;
    }
    if ((Meter.getSerialNumberCompl().equals("0000000000000000") | FirmwareUpload.broadCast == true))
    {
      isBroadcast = true;
      serialNumber = "0000000000000000";
    }
    else
    {
      serialNumber = Meter.getSerialNumberCompl();
      isBroadcast = false;
    }
    boolean estado = true;
    PortComunication port = new PortComunication();
    log.info("Sending Reboot command to Energy Meter...");
    if (isBroadcast)
    {
      tomDelay(500L);
      FlagsPasswords flagPassword = FlagsPasswords.METER_FORCE_RESET;
      estado = modb.writeFlagsPassw(flagPassword, serialNumber);
    }
    else
    {
      tomDelay(700L);
      FlagsPasswords flagPassword = FlagsPasswords.METER_FORCE_RESET;
      estado = modb.writeFlagsPassw(flagPassword, serialNumber);
      if (!estado)
      {
        JOptionPane.showMessageDialog(null, "Error while transmitting METER_FORCE_RESET, returned null.", "Rx/Tx Error", 0);
        return;
      }
    }
    tomDelay(2000L);
    Register regStart = Register.CONTRASENA;
    int countWords = regStart.getWordLength();
    int startAddress = regStart.getAddress();
    
    log.info("Sending Password command to Energy Meter...");
    if (isBroadcast)
    {
      byte[] frame = modb.writeMultipleRegisters(serialNumber, startAddress, countWords, new byte[] { -36, 38, -122, -29 }, (byte)16);
      port.write(frame, PortComunication.DELAY0);
    }
    else
    {
      byte[] frame = modb.writeMultipleRegisters(serialNumber, startAddress, countWords, new byte[] { -36, 38, -122, -29 }, (byte)16);
      port.write(frame, PortComunication.DELAY0);
    }
    tomDelay(7000L);
    
    int framesCount = this.listModbusFrames.size();
    this.atomicFrameCount.getAndSet(0);
    for (ModbusRTUFrame frame : this.listModbusFrames)
    {
      if (frame.isSignature())
      {
        log.info("FINISHED! Writing signature...");
        port.getInstance().write(frame.getSignatureFrame(), delay);
      }
      else
      {
        log.info("Writing Frame #" + frame.getFrameCount() + " out of " + framesCount + "...");
        port.getInstance().write(frame.getModbusFrame(), delay);
      }
      this.atomicFrameCount.getAndIncrement();
    }
    tomDelay(5000L);
    log.info("Firmware Upload sent to device!");
  }
}
