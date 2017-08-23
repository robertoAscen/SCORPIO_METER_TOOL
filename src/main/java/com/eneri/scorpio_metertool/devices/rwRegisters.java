/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eneri.scorpio_metertool.devices;

import com.eneri.scorpio_metertool.bus.Conversions;
import com.eneri.scorpio_metertool.hardwarelayer.ModbusUtil;
import com.eneri.scorpio_metertool.hardwarelayer.PortComunication;
import com.eneri.scorpio_metertool.ui.FirmwareUpload;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import javax.swing.JOptionPane;

/**
 *
 * @author Roberto
 */
public class rwRegisters
{
  private static byte[] receiveData;
  
  public static byte[] readRegister(long delay, String serialNumber, Register regStart, int countWords, byte actionRW)
  {
    ModbusUtil modb = new ModbusUtil();
    PortComunication port = new PortComunication();
    Conversions conv = new Conversions();
    
    byte[] frame = modb.readRegister(serialNumber, regStart.getAddress(), countWords, actionRW);
    port.write(frame, delay);
    
    byte[] response = port.getByteBuffer();
    if (response == null)
    {
      JOptionPane.showMessageDialog(null, "Error Revise el numero de serie, Puerto o conexion");
    }
    else
    {
      int responseCount = countWords * 2 - 3;
      int total = frame.length + responseCount;
      
      receiveData = new byte[countWords * 2];
      if (response.length == total)
      {
        String timeStampComplete = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
        
        System.arraycopy(response, 19, receiveData, 0, countWords * 2);
        
        String a = Conversions.byteArrayToHex(receiveData);
        
        System.out.println(timeStampComplete + " Received: " + a);
      }
      else
      {
        receiveData = null;
        JOptionPane.showMessageDialog(null, "Error Revise el numero de serie, Puerto o conexion");
      }
    }
    return receiveData;
  }
  
  public static void writeMultipleRegister(long delay, String serialNumber, Register regStart, int countWords, byte[] valueToSe, byte actionRW)
  {
    String timeStampComplete = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
    Conversions conv = new Conversions();
    ModbusUtil modb = new ModbusUtil();
    PortComunication port = new PortComunication();
    
    byte[] frame = modb.writeMultipleRegisters(serialNumber, regStart.getAddress(), countWords, valueToSe, actionRW);
    port.write(frame, delay);
    
    byte[] response = port.getByteBuffer();
    if ((Arrays.equals(frame, response) & response != null))
    {
      byte[] onlyResponse = new byte[frame.length - 24];
      System.arraycopy(frame, 22, onlyResponse, 0, frame.length - 24);
      String a = Conversions.byteArrayToHex(onlyResponse);
      System.out.println(timeStampComplete + " Received: " + a);
      
      JOptionPane.showMessageDialog(null, "Se escribio Correctamente");
      System.out.println("Correcto");
    }
    else
    {
      JOptionPane.showMessageDialog(null, "Error Revise el numero de serie, Puerto o conexion");
    }
  }
  
  public static void writeMultipleRegisterString(long delay, String serialNumber, Register regStart, int countWords, String valueToSe, byte actionRW)
  {
    String timeStampComplete = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
    Conversions conv = new Conversions();
    ModbusUtil modb = new ModbusUtil();
    PortComunication port = new PortComunication();
    
    byte[] valueToSend = Conversions.stringToBytesASCII(valueToSe);
    
    byte[] frame = modb.writeMultipleRegisters(serialNumber, regStart.getAddress(), countWords, valueToSend, actionRW);
    port.write(frame, delay);
    
    byte[] response = port.getByteBuffer();
    if ((Arrays.equals(frame, response) | FirmwareUpload.broadCast == true))
    {
      byte[] onlyResponse = new byte[frame.length - 24];
      System.arraycopy(frame, 22, onlyResponse, 0, frame.length - 24);
      String a = Conversions.byteArrayToHex(onlyResponse);
      System.out.println(timeStampComplete + " Received: " + a);
      
      JOptionPane.showMessageDialog(null, "Se escribio Correctamente");
      System.out.println("Correcto");
    }
    else
    {
      JOptionPane.showMessageDialog(null, "Error Revise el numero de serie, Puerto o conexion");
    }
  }
}
