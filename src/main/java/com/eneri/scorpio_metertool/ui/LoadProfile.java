/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eneri.scorpio_metertool.ui;

import com.eneri.scorpio_metertool.bus.Conversions;
import com.eneri.scorpio_metertool.devices.Meter;
import com.eneri.scorpio_metertool.devices.Register;
import com.eneri.scorpio_metertool.hardwarelayer.ModbusUtil;
import com.eneri.scorpio_metertool.hardwarelayer.PortComunication;
import com.eneri.scorpio_metertool.loadprofile.ProcessDataLoad;
import com.eneri.scorpio_metertool.server.WorkConcentrator;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Roberto
 */
public class LoadProfile
  extends JPanel
{
  private int month;
  private int rawData;
  private int fileT;
  private int days;
  private int hours;
  private int minutes;
  private static boolean loadProfileFlag;
  private static int loadProgreessBarint;
  private ArrayList listLoadProfile = new ArrayList();
  private byte[] lastLoad;
  private String[] columnas;
  private Object[] data;
  private JScrollPane scrollpane;
  private static Container contentPane;
  private static StringBuilder builderstring;
  private int countNothings = 0;
  private static DefaultTableModel defaultable;
  
  public LoadProfile()
  {
    initComponents();
    for (int i = 0; i < 24; i++) {
      jComboBoxHours.addItem(Integer.valueOf(i));
    }
    for (int i = 0; i < 60; i++) {
      jComboBoxMinutes.addItem(Integer.valueOf(i));
    }
    ArrayList list = new ArrayList();
    
    list.add(Register.PERFIL_DE_CARGA);
    list.add(Register.PERFIL_DE_CARGA_DIFF_KWH);
    list.add(Register.PERFIL_DE_CARGA_DIFF_KVARH);
    list.add(Register.PERFIL_DE_CARGA_DIFF_FECHA);
    for (int i = 0; i < list.size(); i++)
    {
      Register reg = (Register)list.get(i);
      jComboBoxRegister.addItem(list.get(i));
    }
    jButtonExportLoadProfileToXML.setEnabled(false);
    jButtonExportLoadProfileToCSV.setEnabled(false);
    jButtonApliLoadProfile.setEnabled(false);
  }
  
  private void initComponents()
  {
    jLabelNameLoadProfile = new JLabel();
    jComboBoxLoadProfileInterval = new JComboBox();
    jButtonApliLoadProfile = new JButton();
    jScrollPane1 = new JScrollPane();
    jTableMetersLoadProfile = new JTable();
    jButtonGetLoadProfile = new JButton();
    jButtonExportLoadProfileToXML = new JButton();
    jButtonExportLoadProfileToCSV = new JButton();
    jComboBoxMonth = new JComboBox();
    jComboBoxDays = new JComboBox();
    jComboBoxHours = new JComboBox();
    jComboBoxMinutes = new JComboBox();
    jLabelMonth = new JLabel();
    jLabelDays = new JLabel();
    jLabelHours = new JLabel();
    jLabelMinutes = new JLabel();
    jLabel5 = new JLabel();
    jCheckBoxFileTransfer = new JCheckBox();
    jComboBoxRegister = new JComboBox();
    jScrollPane2 = new JScrollPane();
    jTextAreaLoadProcesor = new JTextArea();
    jProgressBarLoadProfileTransfer = new JProgressBar();
    jButtonClearTextBox = new JButton();
    
    setName("JPanelLoadProfileTitle");
    
    jLabelNameLoadProfile.setFont(new Font("Ubuntu", 1, 18));
    jLabelNameLoadProfile.setText("PERFIL DE CARGA");
    //jLabelNameLoadProfile.setText("LOAD PROFILE");
    
    jComboBoxLoadProfileInterval.setModel(new DefaultComboBoxModel(new String[] { "0", "1" }));
    
    jButtonApliLoadProfile.setFont(new Font("Tahoma", 1, 12));
    jButtonApliLoadProfile.setText("MOSTRAR");
    //jButtonApliLoadProfile.setText("SHOW");
    jButtonApliLoadProfile.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        LoadProfile.this.jButtonApliLoadProfileActionPerformed(evt);
      }
    });
    jTableMetersLoadProfile.setModel(new DefaultTableModel(new Object[0][], new String[0]));
    
    jScrollPane1.setViewportView(jTableMetersLoadProfile);
    
    jButtonGetLoadProfile.setFont(new Font("Tahoma", 1, 12));
    jButtonGetLoadProfile.setText("LEER");
    //jButtonGetLoadProfile.setText("READ");
    jButtonGetLoadProfile.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        LoadProfile.this.jButtonGetLoadProfileActionPerformed(evt);
      }
    });
    jButtonExportLoadProfileToXML.setText("EXPORTAR A XML");
    //jButtonExportLoadProfileToXML.setText("EXPORT TO XML");
    jButtonExportLoadProfileToXML.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        LoadProfile.this.jButtonExportLoadProfileToXMLActionPerformed(evt);
      }
    });
    jButtonExportLoadProfileToCSV.setText("EXPORTAR A CSV");
    //jButtonExportLoadProfileToCSV.setText("EXPORT TO CSV");
    jButtonExportLoadProfileToCSV.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        LoadProfile.this.jButtonExportLoadProfileToCSVActionPerformed(evt);
      }
    });
    jComboBoxMonth.setModel(new DefaultComboBoxModel(new String[] { "Anterior", "Actual" }));
    //jComboBoxMonth.setModel(new DefaultComboBoxModel(new String[] { "Previous", "Current" }));
    
    jComboBoxDays.setEditable(true);
    jComboBoxDays.setModel(new DefaultComboBoxModel(new String[] { "5", "10", "15" }));
    
    jComboBoxHours.setEditable(true);
    
    jComboBoxMinutes.setEditable(true);
    
    jLabelMonth.setText("MES:");
    //jLabelMonth.setText("MONTH:");
    
    jLabelDays.setText("DIAS:");
    //jLabelDays.setText("DAYS:");
    
    jLabelHours.setText("HORA:");
    //jLabelHours.setText("TIME:");
    
    jLabelMinutes.setText("MINUTOS:");
    //jLabelMinutes.setText("MINUTES:");
    
    jLabel5.setText("Memoria:");
    //jLabel5.setText("Memory:");
    
    jCheckBoxFileTransfer.setText("FileTransfer");
    jCheckBoxFileTransfer.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        LoadProfile.this.jCheckBoxFileTransferActionPerformed(evt);
      }
    });
    jTextAreaLoadProcesor.setColumns(20);
    jTextAreaLoadProcesor.setLineWrap(true);
    jTextAreaLoadProcesor.setRows(5);
    jScrollPane2.setViewportView(jTextAreaLoadProcesor);
    
    jProgressBarLoadProfileTransfer.setMaximum(307);
    
    jButtonClearTextBox.setText("LIMPIAR");
    //jButtonClearTextBox.setText("CLEAR");
    jButtonClearTextBox.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        LoadProfile.this.jButtonClearTextBoxActionPerformed(evt);
      }
    });
    GroupLayout layout = new GroupLayout(this);
    setLayout(layout);
    layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(377, 377, 377).addComponent(this.jLabelNameLoadProfile).addGap(0, 0, 32767)).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jProgressBarLoadProfileTransfer, -1, -1, 32767).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.jScrollPane2, -2, 819, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jButtonClearTextBox, -2, 93, -2)).addGroup(layout.createSequentialGroup().addComponent(this.jButtonGetLoadProfile, -2, 279, -2).addGap(18, 18, 18).addComponent(this.jComboBoxRegister, -2, 256, -2).addGap(18, 18, 18).addComponent(this.jButtonExportLoadProfileToXML).addGap(18, 18, 18).addComponent(this.jButtonExportLoadProfileToCSV, -2, 159, -2)).addGroup(layout.createSequentialGroup().addComponent(this.jLabelMonth).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jComboBoxMonth, -2, 76, -2).addGap(29, 29, 29).addComponent(this.jLabel5).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jComboBoxLoadProfileInterval, -2, 47, -2).addGap(31, 31, 31).addComponent(this.jLabelDays).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jComboBoxDays, -2, 54, -2).addGap(18, 18, 18).addComponent(this.jLabelHours).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jComboBoxHours, -2, 44, -2).addGap(38, 38, 38).addComponent(this.jLabelMinutes).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jComboBoxMinutes, -2, 44, -2).addGap(28, 28, 28).addComponent(this.jCheckBoxFileTransfer).addGap(30, 30, 30).addComponent(this.jButtonApliLoadProfile, -2, 130, -2)).addComponent(this.jScrollPane1, -2, 922, -2)).addGap(0, 8, 32767))).addContainerGap()));
    
    layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.jLabelNameLoadProfile).addGap(17, 17, 17).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jButtonApliLoadProfile).addComponent(this.jComboBoxMonth, -2, -1, -2).addComponent(this.jComboBoxDays, -2, -1, -2).addComponent(this.jComboBoxHours, -2, -1, -2).addComponent(this.jComboBoxMinutes, -2, -1, -2).addComponent(this.jLabelMonth).addComponent(this.jLabelHours).addComponent(this.jLabelMinutes).addComponent(this.jLabel5).addComponent(this.jCheckBoxFileTransfer).addComponent(this.jComboBoxLoadProfileInterval, -2, -1, -2).addComponent(this.jLabelDays)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jProgressBarLoadProfileTransfer, -2, 24, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jScrollPane1, -2, 341, -2).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(18, 18, 18).addComponent(this.jScrollPane2, -2, 129, -2)).addGroup(layout.createSequentialGroup().addGap(38, 38, 38).addComponent(this.jButtonClearTextBox, -2, 47, -2))).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jButtonExportLoadProfileToCSV).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jComboBoxRegister, -2, -1, -2).addComponent(this.jButtonExportLoadProfileToXML)).addComponent(this.jButtonGetLoadProfile, -2, 35, -2)).addContainerGap(-1, 32767)));
  }
  
  public void setTable(int numberPage, int watsh, int varsh, long dat)
  {
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
    format.setTimeZone(TimeZone.getTimeZone("GMT"));
    if (watsh == 65535) {
      watsh = 0;
    }
    if (varsh == 65535) {
      varsh = 0;
    }
    String numberPag = Integer.toString(numberPage);
    String whats = Integer.toString(watsh);
    String vars = Integer.toString(varsh);
    String compldate = format.format(new Date(dat)).toString();
    Object[] dataObject = { Integer.valueOf(numberPage), Integer.valueOf(watsh), Integer.valueOf(varsh), compldate };
    defaultable.addRow(dataObject);
  }
  
  private void jButtonGetLoadProfileActionPerformed(ActionEvent evt)
  {
    Object reg = jComboBoxRegister.getSelectedItem();
    timeProggressBar.stop();
    readRegister(reg);
  }
  
  private void jButtonApliLoadProfileActionPerformed(ActionEvent evt)
  {
    timeProggressBar.stop();
    JFrameMain f = (JFrameMain)SwingUtilities.getAncestorOfClass(JFrameMain.class, this);
    contentPane = f.getContentPane();
    scrollpane = new JScrollPane();
    defaultable = new DefaultTableModel((Object[][])null, getColumnas());
    
    ProcessDataLoad procLoad = new ProcessDataLoad();
    procLoad.processLoad(listLoadProfile);
    
    jTableMetersLoadProfile.setModel(defaultable);
    jTableMetersLoadProfile.repaint();
    scrollpane.repaint();
    setVisible(true);
    jButtonExportLoadProfileToXML.setEnabled(true);
    jButtonExportLoadProfileToCSV.setEnabled(true);
  }
  
  private void jButtonExportLoadProfileToXMLActionPerformed(ActionEvent evt)
  {
    WorkConcentrator metCon = new WorkConcentrator();
    metCon.convertLoadProfileXML(jTableMetersLoadProfile);
  }
  
  private void jButtonExportLoadProfileToCSVActionPerformed(ActionEvent evt)
  {
    ProcessDataLoad process = new ProcessDataLoad();
    process.exportarCSV(jTableMetersLoadProfile);
  }
  
  private void jButtonClearTextBoxActionPerformed(ActionEvent evt)
  {
    jTextAreaLoadProcesor.setText("");
  }
  
  private void jCheckBoxFileTransferActionPerformed(ActionEvent evt) {}
  
  public void readRegister(Object obReg)
  {
    PortComunication port = new PortComunication();
    Conversions conv = new Conversions();
    ModbusUtil modb = new ModbusUtil();
    Register reg = (Register)obReg;
    //if (((!port.getStatePort() ? 1 : 0) | (Meter.serialNumber == null ? 1 : 0) /*| Meter.serialNumber.equals("0000000000000000")*/) != 0)
    if((port.getStatePort() == false) | (Meter.serialNumber == null) | Meter.serialNumber.equals("0000000000000000"))//Solución al error de la línea de arriba
    {
      JOptionPane.showMessageDialog(null, "Error, Revise el número de serie, Puerto o conexión");
      //JOptionPane.showMessageDialog(null, "Error Check serial number, Port or connection","Error",JOptionPane.ERROR_MESSAGE);
      return;
    }
    String serialNumber = Meter.getSerialNumberCompl();
    
    int countWords = 2;
    byte actionRW = 3;
    if (jComboBoxMonth.getSelectedItem().toString().equals("Actual")){
    //if (this.jComboBoxMonth.getSelectedItem().toString().equals("Current")) {
      month = 0;
    } else {
      month = 1;
    }
    rawData = Integer.parseInt(jComboBoxLoadProfileInterval.getSelectedItem().toString());
    if (jCheckBoxFileTransfer.isSelected()) {
      fileT = 1;
    } else {
      fileT = 0;
    }
    days = Integer.parseInt(jComboBoxDays.getSelectedItem().toString());
    hours = Integer.parseInt(jComboBoxHours.getSelectedItem().toString());
    minutes = Integer.parseInt(jComboBoxMinutes.getSelectedItem().toString());
    
    int value = month << 31 | rawData << 30 | fileT << 29 | days << 16 | hours << 8 | minutes << 0;
    byte[] valcmd = Conversions.intToByteArray(value);
    if ((jCheckBoxFileTransfer.isSelected()) && ((Register)jComboBoxRegister.getSelectedItem() == Register.PERFIL_DE_CARGA))
    {
      listLoadProfile.clear();
      loadProfileFlag = true;
      
      actionRW = 16;
      byte[] frame = modb.writeMultipleRegisters(serialNumber, reg.getAddress(), countWords, valcmd, actionRW);
      port.write(frame, PortComunication.DELAY300);
      
      timeProggressBar.start();
    }
    else if (!jCheckBoxFileTransfer.isSelected())
    {
      byte[] frame = modb.writeMultipleRegisters(serialNumber, reg.getAddress(), countWords, valcmd, actionRW);
      port.write(frame, PortComunication.DELAY300);
      
      byte[] response = port.getByteBuffer();
      if (response == null)
      {
        JOptionPane.showMessageDialog(null, "Error, Revise el número de serie, Puerto o conexión");
        //JOptionPane.showMessageDialog(null, "Error Check serial number, Port or connection","Error",JOptionPane.ERROR_MESSAGE);
        return;
      }
      int responseCount = countWords * 2 - 3;
      int total = frame.length + responseCount;
      
      String timeStampComplete = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
      if (response.length < frame.length)
      {
        int tot = countWords * 2;
        byte[] receiveData = new byte[countWords * 2];
        System.arraycopy(response, 19, receiveData, 0, tot);
        
        String a = Conversions.byteArrayToHex(receiveData);
        
        ByteBuffer bb = ByteBuffer.allocate(receiveData.length);
        bb.put(receiveData);
        bb.position(0);
        
        System.out.println(timeStampComplete + " Received: " + a);
        Font font = new Font("Verdana", 1, 12);
        jTextAreaLoadProcesor.setFont(font);
        jTextAreaLoadProcesor.append(timeStampComplete + " ------> " + obReg.toString() + "\n");
        jTextAreaLoadProcesor.append(timeStampComplete + " ---> [ " + a + " ]\n");
        if ((Register)jComboBoxRegister.getSelectedItem() == Register.PERFIL_DE_CARGA_DIFF_FECHA)
        {
          long datDoul = bb.getInt() & 0xFFFF;
          datDoul *= 1000L;
          
          SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          format.setTimeZone(TimeZone.getTimeZone("GMT"));
          String datee = format.format(new Date(datDoul)).toString();
          jTextAreaLoadProcesor.append(timeStampComplete + " ---> " + datee + "\n");
        }
        else
        {
          float datDou = bb.getShort() & 0xFFFF;
          jTextAreaLoadProcesor.append(timeStampComplete + " ---> " + datDou + "\n");
        }
      }
      else if (Arrays.equals(frame, response))
      {
        byte[] onlyResponse = new byte[frame.length - 24];
        System.arraycopy(frame, 22, onlyResponse, 0, frame.length - 24);
        String a = Conversions.byteArrayToHex(onlyResponse);
        
        ByteBuffer bb = ByteBuffer.allocate(onlyResponse.length);
        bb.put(onlyResponse);
        bb.position(0);
        
        float datDou = bb.getShort() & 0xFFFF;
        
        System.out.println(timeStampComplete + " Received: " + a);
        jTextAreaLoadProcesor.append(timeStampComplete + " ------> " + obReg.toString() + "\n");
        jTextAreaLoadProcesor.append(timeStampComplete + " ---> [ " + a + " ]\n");
        jTextAreaLoadProcesor.append(timeStampComplete + " ---> " + datDou + "\n");
        
        System.out.println("Correcto");
        //System.out.println("Good");
      }
      else
      {
        JOptionPane.showMessageDialog(null, "Error, falta o exceso de comandos en respuesta");
        //JOptionPane.showMessageDialog(null, "Error lack or excess commands in response","Error",JOptionPane.ERROR_MESSAGE);
      }
    }
  }
  
  public void setProgressBar(int val)
  {
    loadProgreessBarint = val;
  }
  
  ActionListener tasktimeProggressBar = new ActionListener()
  {
    public void actionPerformed(ActionEvent e)
    {
      PortComunication port = new PortComunication();
      Conversions conv = new Conversions();
      byte[] response = port.getByteBuffer();
      port.clearByteBuffer();
      LoadProfile.this.lastLoad = null;
      boolean status = true;
      if (LoadProfile.this.listLoadProfile.size() > 0) {
        LoadProfile.this.lastLoad = ((byte[])LoadProfile.this.listLoadProfile.get(LoadProfile.this.listLoadProfile.size() - 1));
      }
      if (((response != null ? 1 : 0) & (LoadProfile.this.lastLoad != response ? 1 : 0)) != 0)
      {
        if (LoadProfile.this.listLoadProfile.size() == 0)
        {
          byte[] response2 = new byte[response.length - 28];
          System.arraycopy(response, 28, response2, 0, response2.length);
          response = response2;
        }
        LoadProfile.this.listLoadProfile.add(response);
        
        LoadProfile.this.jProgressBarLoadProfileTransfer.setStringPainted(true);
        LoadProfile.this.jProgressBarLoadProfileTransfer.setBackground(Color.WHITE);
        LoadProfile.this.jProgressBarLoadProfileTransfer.setForeground(Color.GREEN);
        LoadProfile.this.jProgressBarLoadProfileTransfer.setValue(LoadProfile.this.listLoadProfile.size());
        if (((LoadProfile.this.listLoadProfile.size() > 300 ? 1 : 0) & (LoadProfile.this.listLoadProfile.size() < 311 ? 1 : 0)) != 0) {
          LoadProfile.this.jProgressBarLoadProfileTransfer.setValue(307);
        }
      }
      else if (((response == null ? 1 : 0) & (LoadProfile.this.listLoadProfile.size() > 300 ? 1 : 0) & (LoadProfile.this.listLoadProfile.size() < 311 ? 1 : 0)) != 0)
      {
        //LoadProfile.access$908(LoadProfile.this);
        if (LoadProfile.this.countNothings > 30)
        {
          LoadProfile.this.timeProggressBar.stop();
          LoadProfile.this.countNothings = 0;
          LoadProfile.this.jButtonApliLoadProfile.setEnabled(true);
        }
      }
    }
  };
  
  public boolean getStatusProfileTransfer()
  {
    return loadProfileFlag;
  }
  
  public void setStatusProfileTransfer(boolean value)
  {
    loadProfileFlag = value;
  }
  
  private Timer timeProggressBar = new Timer(100, this.tasktimeProggressBar);
  private JButton jButtonApliLoadProfile;
  private JButton jButtonClearTextBox;
  private JButton jButtonExportLoadProfileToCSV;
  private JButton jButtonExportLoadProfileToXML;
  private JButton jButtonGetLoadProfile;
  private JCheckBox jCheckBoxFileTransfer;
  private JComboBox jComboBoxDays;
  private JComboBox jComboBoxHours;
  private JComboBox jComboBoxLoadProfileInterval;
  private JComboBox jComboBoxMinutes;
  private JComboBox jComboBoxMonth;
  private JComboBox jComboBoxRegister;
  private JLabel jLabel5;
  private JLabel jLabelDays;
  private JLabel jLabelHours;
  private JLabel jLabelMinutes;
  private JLabel jLabelMonth;
  private JLabel jLabelNameLoadProfile;
  private JProgressBar jProgressBarLoadProfileTransfer;
  private JScrollPane jScrollPane1;
  private JScrollPane jScrollPane2;
  private JTable jTableMetersLoadProfile;
  private JTextArea jTextAreaLoadProcesor;
  
  public String[] getColumnas()
  {
    String[] columna = { "Número", "Wh", "varh", "Fecha" };
    //String[] columna = { "Number", "Wh", "varh", "Date" };
    return columna;
  }
}
