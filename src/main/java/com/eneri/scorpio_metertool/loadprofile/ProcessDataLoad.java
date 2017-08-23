/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eneri.scorpio_metertool.loadprofile;

import com.eneri.scorpio_metertool.bus.Conversions;
import com.eneri.scorpio_metertool.bus.ModbusRTUCRC16;
import com.eneri.scorpio_metertool.ui.LoadProfile;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Roberto
 */
public class ProcessDataLoad
{
  public static final int TOTAL_EXPECTED_PAGES = 358;
  public static final int PAGE_BYTE_SIZE = 271;
  private static final int TOTAL_PACKETS = 25;
  private static final int PACKET_SIZE = 10;
  LinkedHashMap<Integer, byte[]> linkedLoadProc = new LinkedHashMap();
  LinkedHashMap<Boolean, byte[]> linkedLoadProcPackSize = new LinkedHashMap();
  
  public void processLoad(ArrayList listLoadProfile)
  {
    int countotal = 0;
    int contandotodo = 0;
    for (Object val : listLoadProfile)
    {
      byte[] dato = (byte[])val;
      countotal += dato.length;
    }
    byte[] total = new byte[countotal];
    for (int i = 0; i < listLoadProfile.size(); i++)
    {
      byte[] dato = (byte[])listLoadProfile.get(i);
      for (int a = 0; a < dato.length; a++)
      {
        total[contandotodo] = dato[a];
        contandotodo++;
      }
    }
    System.out.println(countotal);
    processLoad(total);
  }
  
  public void processLoad(byte[] raw)
  {
    Conversions conv = new Conversions();
    LoadProfile loadproc = new LoadProfile();
    int totalPage = raw.length / 271;
    int estate = 0;
    int estatePack = 0;
    for (int i = 0; i < totalPage; i++)
    {
      byte[] active = new byte['?'];
      estate = i * 271;
      System.arraycopy(raw, estate, active, 0, 271);
      this.linkedLoadProc.put(Integer.valueOf(i), active);
      boolean statusPacketComp = testCrc(active);
      
      byte[] activePack = new byte[10];
      estatePack = 19;
      System.arraycopy(active, estatePack, activePack, 0, 10);
      
      boolean status = testCrc(activePack);
      if (status == true)
      {
        byte[] sn = new byte[16];
        System.arraycopy(active, 0, sn, 0, 16);
        String snst = new String(sn);
        int numberPAge = (active[(estatePack - 2)] & 0xFF) << 8 | active[(estatePack - 1)] & 0xFF;
        int watsh = (active[estatePack] & 0xFF) << 8 | active[(estatePack + 1)] & 0xFF;
        int varsh = (active[(estatePack + 2)] & 0xFF) << 8 | active[(estatePack + 3)] & 0xFF;
        long date = (active[(estatePack + 4)] & 0xFF) << 16 | (active[(estatePack + 5)] & 0xFF) << 12 | (active[(estatePack + 6)] & 0xFF) << 8 | active[(estatePack + 7)] & 0xFF;
        int cRc = (active[(estatePack + 8)] & 0xFF) << 8 | active[(estatePack + 9)] & 0xFF;
        
        loadproc.setTable(numberPAge, watsh, varsh, date);
        String hex = Conversions.byteArrayToHex(activePack);
        System.out.println(hex);
      }
      this.linkedLoadProcPackSize.put(Boolean.valueOf(status), activePack);
      System.out.println("packet " + status);
      System.out.println("packetComp " + statusPacketComp);
    }
  }
  
  public boolean testCrc(byte[] line)
  {
    boolean stat = true;
    
    int intcrc = ModbusRTUCRC16.doCRC16(line, 0, line.length - 2);
    
    int crcdefault = (line[(line.length - 2)] & 0xFF) << 8 | line[(line.length - 1)] & 0xFF;
    if (intcrc == crcdefault) {
      stat = true;
    } else {
      stat = false;
    }
    return stat;
  }
  
  public void exportarCSV(JTable table)
  {
    File fileTxt = null;
    Writer writer = null;
    File fileData = new File("");
    String timeStampCorto = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());
    String timeStampComplete = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss").format(Calendar.getInstance().getTime());
    String timeStampHora = new SimpleDateFormat("HH-mm-ss").format(Calendar.getInstance().getTime());
    
    File defaultname = new File("C:/LecturasMedidor/" + timeStampCorto + "/");
    JFileChooser f = new JFileChooser();
    f.setFileSelectionMode(1);
    f.showSaveDialog(null);
    
    fileData = f.getSelectedFile();
    if (fileData == null) {
      return;
    }
    fileData = new File(f.getSelectedFile() + "/" + timeStampCorto + "/");
    if (fileData.mkdirs()) {
      System.out.println("Multiple directories are created!");
    } else {
      System.out.println("Failed to create multiple directories!");
    }
    try
    {
      fileTxt = new File(fileData + "/" + timeStampHora + "-LoadProfile" + ".csv");
      DefaultTableModel dtm = (DefaultTableModel)table.getModel();
      int nRow = dtm.getRowCount();
      int nCol = dtm.getColumnCount();
      
      writer = new BufferedWriter(new FileWriter(fileTxt));
      
      StringBuffer bufferHeader = new StringBuffer();
      for (int j = 0; j < nCol; j++)
      {
        bufferHeader.append(dtm.getColumnName(j));
        if (j != nCol) {
          bufferHeader.append(", ");
        }
      }
      writer.write(bufferHeader.toString() + "\r\n");
      for (int i = 0; i < nRow; i++)
      {
        StringBuffer buffer = new StringBuffer();
        for (int j = 0; j < nCol; j++)
        {
          buffer.append(dtm.getValueAt(i, j));
          if (j != nCol) {
            buffer.append(", ");
          }
        }
        writer.write(buffer.toString() + "\r\n");
      }
      return;
    }
    catch (FileNotFoundException e)
    {
      e.printStackTrace();
      JOptionPane.showMessageDialog(null, "No se encontro el archivo");
    }
    catch (IOException e)
    {
      e.printStackTrace();
      JOptionPane.showMessageDialog(null, "No se guardo el archivo");
    }
    finally
    {
      try
      {
        if (writer != null)
        {
          writer.flush();
          writer.close();
          JOptionPane.showMessageDialog(null, "Archivo guardado correctamente");
        }
      }
      catch (IOException e)
      {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "No se guardo el archivo");
      }
    }
  }
}