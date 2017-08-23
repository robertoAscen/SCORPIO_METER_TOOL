/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eneri.scorpio_metertool.hardwarelayer;

import com.eneri.scorpio_metertool.bus.Conversions;
import com.eneri.scorpio_metertool.ui.JFrameMain;
import com.eneri.scorpio_metertool.ui.Launcher;
import com.eneri.scorpio_metertool.ui.Log;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import jssc.SerialPortList;

/**
 *
 * @author Roberto
 */
public class PortComunication
  implements SerialPortEventListener
{
  private static SerialPort serialPort;
  private static byte[] dataReceived;
  private static PortComunication INSTANCE;
  private static String portName = "";
  private static int availableBytes = 0;
  private static boolean flagLoadProfile;
  public static long DELAY0 = 0L;
  public static long DELAY100 = 100L;
  public static long DELAY200 = 200L;
  public static long DELAY300 = 300L;
  public static long DELAY400 = 400L;
  public static long DELAY600 = 600L;
  public static long DELAY800 = 800L;
  Conversions conv = new Conversions();
  
  public void serialEvent(SerialPortEvent event)
  {
    if ((event.isRXCHAR()) && (event.getEventValue() > 0)) {
      dataAvailable(event);
    }
  }
  
  protected synchronized void dataAvailable(SerialPortEvent event)
  {
    try
    {
      try
      {
        Thread.sleep(200L);
      }
      catch (InterruptedException ie) {}
      dataReceived = null;
      availableBytes = available(event);
      if (availableBytes > 0)
      {
        byte[] readBuffer = serialPort.readBytes();
        StringBuilder message = new StringBuilder();
        String timeStampComplete = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
        String hex = Conversions.byteArrayToHex(readBuffer);
        System.out.println(timeStampComplete + " Received: " + hex + " ---" + readBuffer.length);
        synchronized (this)
        {
          dataReceived = readBuffer;
        }
        JPanel currentPanel = null;
        Set set = null;
        Iterator it = null;
        Launcher.getMainFrame();set = JFrameMain.getJPanels().entrySet();
        it = set.iterator();
        while (it.hasNext())
        {
          Map.Entry entry = (Map.Entry)it.next();
          if (((Integer)entry.getValue()).intValue() == 11)
          {
            JPanel pane = (JPanel)entry.getKey();
            currentPanel = pane;
          }
        }
        Log log = (Log)currentPanel;
        log.setLog(timeStampComplete + " Recibido: " + hex + " ---" + readBuffer.length + "\r\t\n\r\t\n");
      }
      else if (!getPortList().contains(portName))
      {
        closePort();
      }
    }
    catch (Exception ex) {}
  }
  
  public boolean getStatePort()
  {
    if (serialPort == null) {
      return false;
    }
    return serialPort.isOpened();
  }
  
  private void setSerialEventHandler(SerialPort serialPort)
  {
    try
    {
      serialPort.addEventListener(getInstance());
    }
    catch (SerialPortException se) {}
  }
  
  public synchronized boolean openPort(String portName, int baudR, int dataBit, int stopBits, int parity, int flowcontrol)
  {
    boolean statePort = false;
    serialPort = new SerialPort(portName);
    try
    {
      serialPort.openPort();
      
      setSerialPortParameters(baudR, dataBit, stopBits, parity, flowcontrol);
      int mask = 1;
      serialPort.setEventsMask(mask);
      serialPort.addEventListener(getInstance());
      
      System.out.println("Puerto Abierto " + portName);
      statePort = true;
      portName = portName;
    }
    catch (SerialPortException ex)
    {
      System.out.println(ex);
      JOptionPane.showConfirmDialog(null, "Error, revise que el puerto " + portName + " no haya sido abierto anteriormente.");
    }
    catch (Exception ex)
    {
      System.out.println(ex);
    }
    return statePort;
  }
  
  public synchronized void closePort()
  {
    try
    {
      serialPort.removeEventListener();
      serialPort.closePort();
      dataReceived = null;
      System.out.println("Puerto Cerrado " + portName);
      portName = "";
    }
    catch (SerialPortException e)
    {
      serialPort = null;
      JOptionPane.showMessageDialog(null, e.getMessage());
    }
    catch (Exception e)
    {
      JOptionPane.showMessageDialog(null, e.getMessage());
    }
  }
  
  public List<String> getPortList()
  {
    ArrayList List = new ArrayList();
    String[] portArray = null;
    String[] portNames = SerialPortList.getPortNames();
    for (int i = 0; i < portNames.length; i++) {
      List.add(portNames[i]);
    }
    portArray = (String[])List.toArray(new String[0]);
    
    return List;
  }
  
  private void setSerialPortParameters(int baudRate, int dataBit, int stopBits, int parity, int flowcontrol)
    throws IOException
  {
    try
    {
      serialPort.setParams(baudRate, dataBit, stopBits, parity);
      
      serialPort.setFlowControlMode(flowcontrol);
    }
    catch (SerialPortException ex)
    {
      System.out.println(ex);
    }
  }
  
  public synchronized void write(byte[] data, long delay)
  {
    try
    {
      dataReceived = null;
      serialPort.writeBytes(data);
      String timeStampComplete = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
      String hex = Conversions.byteArrayToHex(data);
      System.out.println(timeStampComplete + " Sent: " + hex + " ---" + data.length);
      JPanel currentPanel = null;
      Set set = null;
      Iterator it = null;
      Launcher.getMainFrame();set = JFrameMain.getJPanels().entrySet();
      it = set.iterator();
      while (it.hasNext())
      {
        Map.Entry entry = (Map.Entry)it.next();
        if (((Integer)entry.getValue()).intValue() == 11)
        {
          JPanel pane = (JPanel)entry.getKey();
          currentPanel = pane;
        }
      }
      Log log = (Log)currentPanel;
      log.setLog(timeStampComplete + " Enviado: " + hex + " ---" + data.length + "\r\t\n");
      threadDelay(delay);
    }
    catch (Exception e)
    {
      e.printStackTrace();
      JOptionPane.showMessageDialog(null, "Error escribiendo en el puerto ");
    }
  }
  
  public int available(SerialPortEvent event)
  {
    if ((event.isRXCHAR()) && (event.getEventValue() > 0)) {
      try
      {
        return event.getEventValue();
      }
      catch (Exception ex)
      {
        return -1;
      }
    }
    return -1;
  }
  
  public synchronized byte[] getByteBuffer()
  {
    return dataReceived;
  }
  
  public void clearByteBuffer()
  {
    dataReceived = null;
  }
  
  public PortComunication getInstance()
  {
    if (INSTANCE == null) {
      INSTANCE = new PortComunication();
    }
    return INSTANCE;
  }
  
  public static void threadDelay(long delay)
  {
    try
    {
      Thread.sleep(delay);
    }
    catch (InterruptedException ex) {}
  }
  
  public void update() {}
}
