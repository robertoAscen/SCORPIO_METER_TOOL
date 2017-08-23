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
      this.jComboBoxHours.addItem(Integer.valueOf(i));
    }
    for (int i = 0; i < 60; i++) {
      this.jComboBoxMinutes.addItem(Integer.valueOf(i));
    }
    ArrayList list = new ArrayList();
    
    list.add(Register.PERFIL_DE_CARGA);
    list.add(Register.PERFIL_DE_CARGA_DIFF_KWH);
    list.add(Register.PERFIL_DE_CARGA_DIFF_KVARH);
    list.add(Register.PERFIL_DE_CARGA_DIFF_FECHA);
    for (int i = 0; i < list.size(); i++)
    {
      Register reg = (Register)list.get(i);
      this.jComboBoxRegister.addItem(list.get(i));
    }
    this.jButtonExportLoadProfileToXML.setEnabled(false);
    this.jButtonExportLoadProfileToCSV.setEnabled(false);
    this.jButtonApliLoadProfile.setEnabled(false);
  }
  
  private void initComponents()
  {
    this.jLabelNameLoadProfile = new JLabel();
    this.jComboBoxLoadProfileInterval = new JComboBox();
    this.jButtonApliLoadProfile = new JButton();
    this.jScrollPane1 = new JScrollPane();
    this.jTableMetersLoadProfile = new JTable();
    this.jButtonGetLoadProfile = new JButton();
    this.jButtonExportLoadProfileToXML = new JButton();
    this.jButtonExportLoadProfileToCSV = new JButton();
    this.jComboBoxMonth = new JComboBox();
    this.jComboBoxDays = new JComboBox();
    this.jComboBoxHours = new JComboBox();
    this.jComboBoxMinutes = new JComboBox();
    this.jLabelMonth = new JLabel();
    this.jLabelDays = new JLabel();
    this.jLabelHours = new JLabel();
    this.jLabelMinutes = new JLabel();
    this.jLabel5 = new JLabel();
    this.jCheckBoxFileTransfer = new JCheckBox();
    this.jComboBoxRegister = new JComboBox();
    this.jScrollPane2 = new JScrollPane();
    this.jTextAreaLoadProcesor = new JTextArea();
    this.jProgressBarLoadProfileTransfer = new JProgressBar();
    this.jButtonClearTextBox = new JButton();
    
    setName("JPanelLoadProfileTitle");
    
    this.jLabelNameLoadProfile.setFont(new Font("Ubuntu", 1, 18));
    //this.jLabelNameLoadProfile.setText("PERFIL DE CARGA");
    this.jLabelNameLoadProfile.setText("LOAD PROFILE");
    
    this.jComboBoxLoadProfileInterval.setModel(new DefaultComboBoxModel(new String[] { "0", "1" }));
    
    this.jButtonApliLoadProfile.setFont(new Font("Tahoma", 1, 12));
    //this.jButtonApliLoadProfile.setText("MOSTRAR");
    this.jButtonApliLoadProfile.setText("SHOW");
    this.jButtonApliLoadProfile.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        LoadProfile.this.jButtonApliLoadProfileActionPerformed(evt);
      }
    });
    this.jTableMetersLoadProfile.setModel(new DefaultTableModel(new Object[0][], new String[0]));
    
    this.jScrollPane1.setViewportView(this.jTableMetersLoadProfile);
    
    this.jButtonGetLoadProfile.setFont(new Font("Tahoma", 1, 12));
    //this.jButtonGetLoadProfile.setText("LEER");
    this.jButtonGetLoadProfile.setText("READ");
    this.jButtonGetLoadProfile.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        LoadProfile.this.jButtonGetLoadProfileActionPerformed(evt);
      }
    });
    //this.jButtonExportLoadProfileToXML.setText("EXPORTAR A XML");
    this.jButtonExportLoadProfileToXML.setText("EXPORT TO XML");
    this.jButtonExportLoadProfileToXML.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        LoadProfile.this.jButtonExportLoadProfileToXMLActionPerformed(evt);
      }
    });
    //this.jButtonExportLoadProfileToCSV.setText("EXPORTAR A CSV");
    this.jButtonExportLoadProfileToCSV.setText("EXPORT TO CSV");
    this.jButtonExportLoadProfileToCSV.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        LoadProfile.this.jButtonExportLoadProfileToCSVActionPerformed(evt);
      }
    });
    //this.jComboBoxMonth.setModel(new DefaultComboBoxModel(new String[] { "Anterior", "Actual" }));
    this.jComboBoxMonth.setModel(new DefaultComboBoxModel(new String[] { "Previous", "Current" }));
    
    this.jComboBoxDays.setEditable(true);
    this.jComboBoxDays.setModel(new DefaultComboBoxModel(new String[] { "5", "10", "15" }));
    
    this.jComboBoxHours.setEditable(true);
    
    this.jComboBoxMinutes.setEditable(true);
    
    //this.jLabelMonth.setText("MES:");
    this.jLabelMonth.setText("MONTH:");
    
    //this.jLabelDays.setText("DIAS:");
    this.jLabelDays.setText("DAYS:");
    
    //this.jLabelHours.setText("HORA:");
    this.jLabelHours.setText("TIME:");
    
    //this.jLabelMinutes.setText("MINUTOS:");
    this.jLabelMinutes.setText("MINUTES:");
    
    //this.jLabel5.setText("Memoria:");
    this.jLabel5.setText("Memory:");
    
    this.jCheckBoxFileTransfer.setText("FileTransfer");
    this.jCheckBoxFileTransfer.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        LoadProfile.this.jCheckBoxFileTransferActionPerformed(evt);
      }
    });
    this.jTextAreaLoadProcesor.setColumns(20);
    this.jTextAreaLoadProcesor.setLineWrap(true);
    this.jTextAreaLoadProcesor.setRows(5);
    this.jScrollPane2.setViewportView(this.jTextAreaLoadProcesor);
    
    this.jProgressBarLoadProfileTransfer.setMaximum(307);
    
    //this.jButtonClearTextBox.setText("LIMPIAR");
    this.jButtonClearTextBox.setText("CLEAR");
    this.jButtonClearTextBox.addActionListener(new ActionListener()
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
    Object reg = this.jComboBoxRegister.getSelectedItem();
    this.timeProggressBar.stop();
    readRegister(reg);
  }
  
  private void jButtonApliLoadProfileActionPerformed(ActionEvent evt)
  {
    this.timeProggressBar.stop();
    JFrameMain f = (JFrameMain)SwingUtilities.getAncestorOfClass(JFrameMain.class, this);
    contentPane = f.getContentPane();
    this.scrollpane = new JScrollPane();
    defaultable = new DefaultTableModel((Object[][])null, getColumnas());
    
    ProcessDataLoad procLoad = new ProcessDataLoad();
    procLoad.processLoad(this.listLoadProfile);
    
    this.jTableMetersLoadProfile.setModel(defaultable);
    this.jTableMetersLoadProfile.repaint();
    this.scrollpane.repaint();
    setVisible(true);
    this.jButtonExportLoadProfileToXML.setEnabled(true);
    this.jButtonExportLoadProfileToCSV.setEnabled(true);
  }
  
  private void jButtonExportLoadProfileToXMLActionPerformed(ActionEvent evt)
  {
    WorkConcentrator metCon = new WorkConcentrator();
    metCon.convertLoadProfileXML(this.jTableMetersLoadProfile);
  }
  
  private void jButtonExportLoadProfileToCSVActionPerformed(ActionEvent evt)
  {
    ProcessDataLoad process = new ProcessDataLoad();
    process.exportarCSV(this.jTableMetersLoadProfile);
  }
  
  private void jButtonClearTextBoxActionPerformed(ActionEvent evt)
  {
    this.jTextAreaLoadProcesor.setText("");
  }
  
  private void jCheckBoxFileTransferActionPerformed(ActionEvent evt) {}
  
  public void readRegister(Object obReg)
  {
    PortComunication port = new PortComunication();
    Conversions conv = new Conversions();
    ModbusUtil modb = new ModbusUtil();
    Register reg = (Register)obReg;
    if (((!port.getStatePort() ? 1 : 0) | (Meter.serialNumber == null ? 1 : 0) /*| Meter.serialNumber.equals("0000000000000000")*/) != 0)
    {
      //JOptionPane.showMessageDialog(null, "Error Revise el numero de serie, Puerto o conexion");
      JOptionPane.showMessageDialog(null, "Error Check serial number, Port or connection","Error",JOptionPane.ERROR_MESSAGE);
      return;
    }
    String serialNumber = Meter.getSerialNumberCompl();
    
    int countWords = 2;
    byte actionRW = 3;
    //if (this.jComboBoxMonth.getSelectedItem().toString().equals("Actual"))
    if (this.jComboBoxMonth.getSelectedItem().toString().equals("Current")) {
      this.month = 0;
    } else {
      this.month = 1;
    }
    this.rawData = Integer.parseInt(this.jComboBoxLoadProfileInterval.getSelectedItem().toString());
    if (this.jCheckBoxFileTransfer.isSelected()) {
      this.fileT = 1;
    } else {
      this.fileT = 0;
    }
    this.days = Integer.parseInt(this.jComboBoxDays.getSelectedItem().toString());
    this.hours = Integer.parseInt(this.jComboBoxHours.getSelectedItem().toString());
    this.minutes = Integer.parseInt(this.jComboBoxMinutes.getSelectedItem().toString());
    
    int value = this.month << 31 | this.rawData << 30 | this.fileT << 29 | this.days << 16 | this.hours << 8 | this.minutes << 0;
    byte[] valcmd = Conversions.intToByteArray(value);
    if ((this.jCheckBoxFileTransfer.isSelected()) && ((Register)this.jComboBoxRegister.getSelectedItem() == Register.PERFIL_DE_CARGA))
    {
      this.listLoadProfile.clear();
      loadProfileFlag = true;
      
      actionRW = 16;
      byte[] frame = modb.writeMultipleRegisters(serialNumber, reg.getAddress(), countWords, valcmd, actionRW);
      port.write(frame, PortComunication.DELAY300);
      
      this.timeProggressBar.start();
    }
    else if (!this.jCheckBoxFileTransfer.isSelected())
    {
      byte[] frame = modb.writeMultipleRegisters(serialNumber, reg.getAddress(), countWords, valcmd, actionRW);
      port.write(frame, PortComunication.DELAY300);
      
      byte[] response = port.getByteBuffer();
      if (response == null)
      {
        //JOptionPane.showMessageDialog(null, "Error Revise el numero de serie, Puerto o conexion");
        JOptionPane.showMessageDialog(null, "Error Check serial number, Port or connection","Error",JOptionPane.ERROR_MESSAGE);
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
        this.jTextAreaLoadProcesor.setFont(font);
        this.jTextAreaLoadProcesor.append(timeStampComplete + " ------> " + obReg.toString() + "\n");
        this.jTextAreaLoadProcesor.append(timeStampComplete + " ---> [ " + a + " ]\n");
        if ((Register)this.jComboBoxRegister.getSelectedItem() == Register.PERFIL_DE_CARGA_DIFF_FECHA)
        {
          long datDoul = bb.getInt() & 0xFFFF;
          datDoul *= 1000L;
          
          SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          format.setTimeZone(TimeZone.getTimeZone("GMT"));
          String datee = format.format(new Date(datDoul)).toString();
          this.jTextAreaLoadProcesor.append(timeStampComplete + " ---> " + datee + "\n");
        }
        else
        {
          float datDou = bb.getShort() & 0xFFFF;
          this.jTextAreaLoadProcesor.append(timeStampComplete + " ---> " + datDou + "\n");
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
        this.jTextAreaLoadProcesor.append(timeStampComplete + " ------> " + obReg.toString() + "\n");
        this.jTextAreaLoadProcesor.append(timeStampComplete + " ---> [ " + a + " ]\n");
        this.jTextAreaLoadProcesor.append(timeStampComplete + " ---> " + datDou + "\n");
        
        //System.out.println("Correcto");
        System.out.println("Good");
      }
      else
      {
        //JOptionPane.showMessageDialog(null, "Error falta o exceso de comandos en respuesta");
        JOptionPane.showMessageDialog(null, "Error lack or excess commands in response","Error",JOptionPane.ERROR_MESSAGE);
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
    //String[] columna = { "Numero", "Wh", "varh", "Fecha" };
    String[] columna = { "Number", "Wh", "varh", "Date" };
    return columna;
  }
}
