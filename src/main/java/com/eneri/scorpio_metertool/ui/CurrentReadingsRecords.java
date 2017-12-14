/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eneri.scorpio_metertool.ui;

import com.eneri.scorpio_metertool.bus.Conversions;
import com.eneri.scorpio_metertool.devices.Meter;
import com.eneri.scorpio_metertool.devices.Readings;
import com.eneri.scorpio_metertool.hardwarelayer.PortComunication;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Roberto
 */
public class CurrentReadingsRecords
  extends JPanel
{
  private static final long EPOCH_2K_ZERO = 946684800000L;
  PortComunication port = new PortComunication();
  Readings read = new Readings();
  Conversions conv = new Conversions();
  private static Container contentPane;
  public static int timeReadings = 10;
  static double[] datos;
  public static final int DATABITS_5 = 5;
  public static final int DATABITS_6 = 6;
  public static final int DATABITS_7 = 7;
  public static final int DATABITS_8 = 8;
  public static final int PARITY_NONE = 0;
  public static final int PARITY_ODD = 1;
  public static final int PARITY_EVEN = 2;
  public static final int PARITY_MARK = 3;
  public static final int PARITY_SPACE = 4;
  public static final int STOPBITS_1 = 1;
  public static final int STOPBITS_2 = 2;
  public static final int STOPBITS_1_5 = 3;
  public static final int FLOWCONTROL_NONE = 0;
  public static final int FLOWCONTROL_RTSCTS_IN = 1;
  public static final int FLOWCONTROL_RTSCTS_OUT = 2;
  public static final int FLOWCONTROL_XONXOFF_IN = 4;
  public static final int FLOWCONTROL_XONXOFF_OUT = 8;
  String SNMedidorr;
  String timeStampComplete;
  String timeStampCorto;
  String timeStampHora;
  final JFileChooser fileChooser = new JFileChooser();
  public File fileTxt;
  public Writer writer;
  public FileWriter fileWriter;
  public int contadorLog;
  File fileData = new File("");
  boolean cerrarArchivo = false;
  boolean startRecord = false;
  
  public LinkedHashMap<String, String> listReadingsComplete = new LinkedHashMap();
  public LinkedHashMap<String, String> listReadings = new LinkedHashMap();
  public LinkedHashMap<String, Boolean> listSelected = new LinkedHashMap();
  
  String ROW_KH2 = "";
  String ROW_Firmware1 = "";
  String ROW_KWH2 = "";
  String ROW_KVARH2 = "";
  String ROW_EnergiaNegativaActivaTotal2 = "";
  String ROW_EnergiaNegativaReactivaTotal2 = "";
  String ROW_Localtime2 = "";
  String ROW_HZ2 = "";
  String ROW_VOLTAGEP1 = "";
  String ROW_VOLTAGEP2 = "";
  String ROW_VOLTAGEP3 = "";
  String ROW_CURRENTP1 = "";
  String ROW_CURRENTP2 = "";
  String ROW_CURRENTP3 = "";
  String ROW_SYS_POWER_FACTOR2 = "";
  String ROW_SYS_POWER_FACTOR_FASES1 = "";
  String ROW_SYS_POWER_FACTOR_FASES2 = "";
  String ROW_SYS_POWER_FACTOR_FASES3 = "";
  String ROW_DemandaRoladaActivaEnt2 = "";
  String ROW_TiempoDemRoladaActivaEnt2 = "";
  String ROW_DemandaRoladaReactivaEnt2 = "";
  String ROW_TiempoDemRoladaReactivaEnt2 = "";
  String ROW_DemandaRoladaActivaRecib2 = "";
  String ROW_TiempoDemRoladaActivaRecib2 = "";
  String ROW_DemandaRoladaReactivaRecib2 = "";
  String ROW_TiempoDemRoladaReactivaRecib2 = "";
  String ROW_SYS_REAL_POWER2 = "";
  String ROW_SYS_REACTIVE_POWER2 = "";
  String ROW_SYS_APPARENT_POWER2 = "";
  String ROW_EnergiaActiva1 = "";
  String ROW_EnergiaActiva2 = "";
  String ROW_EnergiaActiva3 = "";
  String ROW_EnergiaReactiva1 = "";
  String ROW_EnergiaReactiva2 = "";
  String ROW_EnergiaReactiva3 = "";
  String ROW_EnergiaNegativaActiva1 = "";
  String ROW_EnergiaNegativaActiva2 = "";
  String ROW_EnergiaNegativaActiva3 = "";
  String ROW_EnergiaNegativaReactiva1 = "";
  String ROW_EnergiaNegativaReactiva2 = "";
  String ROW_EnergiaNegativaReactiva3 = "";
  String ROW_PotenciaActiva1 = "";
  String ROW_PotenciaActiva2 = "";
  String ROW_PotenciaActiva3 = "";
  String ROW_PotenciaReactiva1 = "";
  String ROW_PotenciaReactiva2 = "";
  String ROW_PotenciaReactiva3 = "";
  String ROW_PotenciaAparente1 = "";
  String ROW_PotenciaAparente2 = "";
  String ROW_PotenciaAparente3 = "";
  String ROW_SYS_TIMESTAMP2 = "";
  
  JFrameMain f = (JFrameMain)SwingUtilities.getAncestorOfClass(JFrameMain.class, this);
  DefaultComboBoxModel model = new DefaultComboBoxModel();
  
  public CurrentReadingsRecords()
  {
    initComponents();
    
    getPorts();
    jButtonExportCurrentReadingsToTxt.setEnabled(false);
    jButtonExportCurrentReadingsToXML.setEnabled(false);
    jButtonLogConfiguration.setEnabled(false);
  }
  
  private void threadDelay(long delay)
  {
    try
    {
      Thread.sleep(delay);
    }
    catch (InterruptedException ex) {}
  }
  
  public void setSerialNumberJTextbox(String value)
  {
    jTextFieldSerialNumber.setText(value);
  }
  
  private void initComponents()
  {
    jCheckBox1 = new JCheckBox();
    jLabelCurrentReadingsTitle = new JLabel();
    jLabelPort = new JLabel();
    jTextFieldMeterSerialNumber = new JTextField();
    jButtonOpenPort = new JButton();
    jScrollPane1 = new JScrollPane();
    jTableCurrentReadings = new JTable();
    jButtonStartStop = new JButton();
    jButtonExportCurrentReadingsToTxt = new JButton();
    jCheckBoxLogMode = new JCheckBox();
    jButtonLogConfiguration = new JButton();
    jButtonExportCurrentReadingsToXML = new JButton();
    jLabelPortConfigurations = new JLabel();
    jComboBoxPort = new JComboBox();
    jButtonSerialNumber = new JButton();
    jTextFieldSerialNumber = new JTextField();
    jCheckBoxSelected = new JCheckBox();
    jSeparator1 = new JSeparator();
    jButtonUnitReading = new JButton();
    jLabelPortSelected = new JLabel();
    
    jCheckBox1.setText("jCheckBox1");
    
    jComboBoxPort.setToolTipText("Selecciona el puerto a utilizar");
    
    setMaximumSize(new Dimension(806, 572));
    //setMinimumSize(new Dimension(806, 572));
    
    jLabelCurrentReadingsTitle.setFont(new Font("Ubuntu", 1, 18));
    jLabelCurrentReadingsTitle.setText("Lecturas de registros actuales");
    //jLabelCurrentReadingsTitle.setText("Current readings registers");
    
    //jLabelPort.setText("Port:");
    jLabelPort.setText("Puerto:");
    jLabelPort.setName("jLabelPort");
    
    jButtonOpenPort.setText("Abrir puerto");
    jButtonOpenPort.setToolTipText("Oprime el botón para abrir puerto");
    //jButtonOpenPort.setText("Open Port");
    jButtonOpenPort.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        CurrentReadingsRecords.this.jButtonOpenPortActionPerformed(evt);
      }
    });
    jTableCurrentReadings.setFont(new Font("Arial", 1, 12));
    jTableCurrentReadings.setModel(meterTable);
    jTableCurrentReadings.setRowHeight(23);
    jScrollPane1.setViewportView(jTableCurrentReadings);
    
    jButtonStartStop.setFont(new Font("Tahoma", 1, 18));
    jButtonStartStop.setText("Iniciar Lecturas");
    jButtonStartStop.setEnabled(false);
    jButtonStartStop.setToolTipText("Oprime el botón para iniciar las lecturas del medidor");
    //jButtonStartStop.setText("Start reading");
    jButtonStartStop.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        CurrentReadingsRecords.this.jButtonStartStopActionPerformed(evt);
      }
    });
    jButtonExportCurrentReadingsToTxt.setText("Exportar datos de lectura a TXT");
    //jButtonExportCurrentReadingsToTxt.setText("Export data reading to TXT");
    jButtonExportCurrentReadingsToTxt.setToolTipText("Oprime el botón para exportar a archivo .txt");
    jButtonExportCurrentReadingsToTxt.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        CurrentReadingsRecords.this.jButtonExportCurrentReadingsToTxtActionPerformed(evt);
      }
    });
    jCheckBoxLogMode.setText("Modo log TXT");
    jCheckBoxLogMode.setEnabled(false);
    //jCheckBoxLogMode.setText("Mode log TXT");
    jCheckBoxLogMode.setToolTipText("Habilita el modo .txt del log");
    jCheckBoxLogMode.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        CurrentReadingsRecords.this.jCheckBoxLogModeActionPerformed(evt);
      }
    });
    jButtonLogConfiguration.setText("Configuración de Log");
    //jButtonLogConfiguration.setText("Configuration de Log");
    jButtonLogConfiguration.setName("ButtonLogConfiguration");
    jButtonLogConfiguration.setToolTipText("Oprime el botón para configurar las opciones del log");
    jButtonLogConfiguration.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        CurrentReadingsRecords.this.jButtonLogConfigurationActionPerformed(evt);
      }
    });
    jButtonExportCurrentReadingsToXML.setText("Exportar datos de lectura a XML");
    //jButtonExportCurrentReadingsToXML.setText("Export data reading to XML");
    jButtonExportCurrentReadingsToXML.setToolTipText("Oprime el botón para exportar a .xml");
    jButtonExportCurrentReadingsToXML.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        CurrentReadingsRecords.this.jButtonExportCurrentReadingsToXMLActionPerformed(evt);
      }
    });
    jLabelPortConfigurations.setFont(new Font("Tahoma", 1, 18));
    jLabelPortConfigurations.setText("CONFIGURACIONES DE PUERTO");
   // jLabelPortConfigurations.setText("PORT CONFIGURATIONS");
    
    jButtonSerialNumber.setText("Aplicar Número Serie");
    jButtonSerialNumber.setEnabled(false);
    jButtonSerialNumber.setToolTipText("Oprime el botón para establecer el número de serie del medidor a interrogar");
    //jButtonSerialNumber.setText("Apply Serial Number");
    jButtonSerialNumber.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        CurrentReadingsRecords.this.jButtonSerialNumberActionPerformed(evt);
      }
    });
    
    jTextFieldMeterSerialNumber.setEnabled(false);
    jTextFieldMeterSerialNumber.setToolTipText("Ingresa el número de serie del medidor a interrogar");
    
    jTextFieldSerialNumber.setEditable(false);
    jTextFieldSerialNumber.setText("Número de Serie del Medidor");
    //jTextFieldSerialNumber.setText("Serial Number Meter");
    
    jCheckBoxSelected.setText("Modo Seleccionado");
    jCheckBoxSelected.setEnabled(false);
    //jCheckBoxSelected.setText("Selected Mode");
    
    jSeparator1.setOrientation(1);
    
    jButtonUnitReading.setText("Lecuras Unitarias");
    jButtonUnitReading.setEnabled(false);
    //jButtonUnitReading.setText("Individual Readings");
    jButtonUnitReading.setToolTipText("Oprime el botón para solicitar lecturas unitarias al medidor");
    jButtonUnitReading.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        CurrentReadingsRecords.this.jButtonUnitReadingActionPerformed(evt);
      }
    });
    GroupLayout layout = new GroupLayout(this);
    setLayout(layout);
    layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jScrollPane1).addGroup(layout.createSequentialGroup().addComponent(this.jLabelPort).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jComboBoxPort, -2, 68, -2).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jButtonOpenPort, -2, 131, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jLabelPortSelected, -2, 44, -2).addGap(35, 35, 35).addComponent(this.jTextFieldSerialNumber, -2, 173, -2).addGap(18, 18, 18).addComponent(this.jTextFieldMeterSerialNumber, -1, 190, 32767).addGap(18, 18, 18).addComponent(this.jButtonSerialNumber, -2, 202, -2)).addGroup(layout.createSequentialGroup().addGap(214, 214, 214).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabelCurrentReadingsTitle).addComponent(this.jLabelPortConfigurations)))))).addGap(13, 13, 13)).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jButtonStartStop, -1, -1, 32767).addComponent(this.jButtonUnitReading, -1, -1, 32767)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jButtonExportCurrentReadingsToTxt, GroupLayout.Alignment.TRAILING).addComponent(this.jButtonExportCurrentReadingsToXML, GroupLayout.Alignment.TRAILING)).addGap(18, 18, 18).addComponent(this.jCheckBoxLogMode).addGap(51, 51, 51).addComponent(this.jSeparator1, -2, 13, -2).addGap(18, 18, 18).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jButtonLogConfiguration, -2, 176, -2).addComponent(this.jCheckBoxSelected)).addContainerGap()))));
    
    layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.jLabelPortConfigurations).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabelPort).addComponent(this.jTextFieldMeterSerialNumber, -2, -1, -2).addComponent(this.jButtonOpenPort).addComponent(this.jComboBoxPort, -2, -1, -2).addComponent(this.jButtonSerialNumber).addComponent(this.jTextFieldSerialNumber, -2, -1, -2).addComponent(this.jLabelPortSelected, -2, 23, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jLabelCurrentReadingsTitle).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jScrollPane1, -1, 421, 32767).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jButtonExportCurrentReadingsToTxt).addComponent(this.jCheckBoxLogMode).addComponent(this.jButtonLogConfiguration)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jButtonExportCurrentReadingsToXML).addComponent(this.jCheckBoxSelected))).addComponent(this.jSeparator1, -2, 73, -2).addGroup(layout.createSequentialGroup().addComponent(this.jButtonStartStop, -2, 41, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jButtonUnitReading))).addGap(3, 3, 3)));
  }
  
  private void jCheckBoxLogModeActionPerformed(ActionEvent evt) {}
  
  private void jButtonLogConfigurationActionPerformed(ActionEvent evt)
  {
    frameConf();
  }
  
  public void frameConf()
  {
    final JFrame framConfAv = new JFrame("Configurar Exportación de Lecturas");
    //final JFrame framConfAv = new JFrame("Set Export Readings");
    JMenuBar menuBar = new JMenuBar();
    JMenu menu = new JMenu("Menú");
    JPanel panel1 = new JPanel();
    final JCheckBox[] checkboxes = new JCheckBox[51];
    JButton but = new JButton();
    JCheckBox cb = new JCheckBox();
    but.setText("Guardar");
    //but.setText("Save");
    menuBar.add(menu);
    
    JMenuItem item = new JMenuItem("Salir");
    //JMenuItem item = new JMenuItem("Exit");
    
    item.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        framConfAv.setVisible(false);
      }
    });
    readingsExport();
    int valueTotalDim = 20;
    int valuCount = 0;
    
    Iterator it = null;
    Set set = this.listReadingsComplete.entrySet();
    it = set.iterator();
    while (it.hasNext())
    {
      Map.Entry entry = (Map.Entry)it.next();
      String value = entry.getValue().toString();
      String key = entry.getKey().toString();
      cb = new JCheckBox(key);
      cb.setName(key);
      add(cb);
      checkboxes[valuCount] = cb;
      checkboxes[valuCount].setPreferredSize(new Dimension(270, valueTotalDim + 10));
      checkboxes[valuCount].setFont(new Font("Helvetica", 0, 12));
      panel1.add(checkboxes[valuCount]);
      Insets insets = panel1.getInsets();
      valuCount++;
    }
    but.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        for (int i = 0; i < 51; i++)
        {
          boolean state = checkboxes[i].isSelected();
          String dato = checkboxes[i].getName();
          CurrentReadingsRecords.this.listSelected.put(dato, Boolean.valueOf(state));
        }
        CurrentReadingsRecords.this.jCheckBoxSelected.setSelected(true);
        framConfAv.setVisible(false);
      }
    });
    panel1.add(but);
    framConfAv.add(panel1);
    menu.add(item);
    framConfAv.setJMenuBar(menuBar);
    framConfAv.setDefaultCloseOperation(1);
    framConfAv.setSize(1000, 700);
    framConfAv.setVisible(true);
    framConfAv.setLocationRelativeTo(null);
  }
  
  private void jButtonOpenPortActionPerformed(ActionEvent evt)
  {
    if (jComboBoxPort.getSelectedItem() == null)
    {
      JOptionPane.showMessageDialog(null, "Error, no hay puertos seleccionados", "Error", JOptionPane.ERROR_MESSAGE);
      //JOptionPane.showMessageDialog(null, "Error, no selected port","Error",JOptionPane.ERROR_MESSAGE);
      return;
    }
    if (!port.getStatePort())
    {
      port.openPort(jComboBoxPort.getSelectedItem().toString(), 19200, 8, 1, 2, 0);
      if (port.getStatePort() == true)
      {
        //jButtonOpenPort.setText("Cerrar Puerto");
        JOptionPane.showMessageDialog(null, "Puerto Abierto","Información",JOptionPane.INFORMATION_MESSAGE); //Este fue puesto por Roberto
        jButtonOpenPort.setText("Cerrar Puerto");
        jLabelPortSelected.setText(jComboBoxPort.getSelectedItem().toString());
        
        jTextFieldMeterSerialNumber.setEnabled(true);
        
        jButtonSerialNumber.setEnabled(true);
        jCheckBoxLogMode.setEnabled(true);
        jCheckBoxSelected.setEnabled(true);
      }
    }
    else if (port.getStatePort() == true)
    {
      port.closePort();
      if (!port.getStatePort())
      {
        timePoll.stop();
        jButtonStartStop.setText("Iniciar Lecturas");
        //jButtonStartStop.setText("Start Readings");
        JOptionPane.showMessageDialog(null, "Puerto Cerrado","Información",JOptionPane.INFORMATION_MESSAGE);//Este fue puesto por Roberto
        jButtonOpenPort.setText("Abrir Puerto");
        
        read.bolreadings = false;
      }
      jButtonSerialNumber.setEnabled(false);
      jCheckBoxLogMode.setEnabled(false);
      jCheckBoxSelected.setEnabled(false);
      jTextFieldMeterSerialNumber.setEnabled(false);
      jButtonStartStop.setEnabled(false);
      jButtonUnitReading.setEnabled(false);
      jButtonExportCurrentReadingsToTxt.setEnabled(false);
      jButtonExportCurrentReadingsToXML.setEnabled(false);
      jButtonLogConfiguration.setEnabled(false);
    }
  }
  
  private void jButtonStartStopActionPerformed(ActionEvent evt)
  {
    boolean statusRead = read.getStatusReadings();
    if (statusRead == true)
    {
      read.setStatusReadings(false);
      timePoll.stop();
      jButtonStartStop.setText("Iniciar Lecturas");
      //jButtonStartStop.setText("Start Readings");
    }
    else if ((!statusRead) && (this.port.getStatePort() == true))
    {
      read.setStatusReadings(true);
      timePoll.start();
      jButtonStartStop.setText("Parar Lecturas");
      //jButtonStartStop.setText("Stop Reading");
    }
    else
    {
      JOptionPane.showMessageDialog(null, "Error, Revisa que el puerto este abierto", "Error", JOptionPane.ERROR_MESSAGE);
      jButtonStartStop.setText("Iniciar Lecturas");
      //JOptionPane.showMessageDialog(null, "Error Check that the port is open","Error",JOptionPane.ERROR_MESSAGE);
      //jButtonStartStop.setText("Start Readings");
    }
  }
  
  private void jButtonSerialNumberActionPerformed(ActionEvent evt)
  {
    settingSerialNumber(jTextFieldMeterSerialNumber.getText());
  }
  
  public void settingSerialNumber(String serialNumber)
  {
    if (serialNumber != null) {
      if (serialNumber.equals(""))
      {
        JOptionPane.showMessageDialog(null, "Error, Ingresa Número de Serie", "Error", JOptionPane.ERROR_MESSAGE );
        //JOptionPane.showMessageDialog(null, "Error, Enter Serial Number","Error",JOptionPane.ERROR_MESSAGE);
      }
      else if (serialNumber.length() > 16)
      {
        JOptionPane.showMessageDialog(null, "Error, Número de Serie mayor a 16 dígitos", "Error",JOptionPane.ERROR_MESSAGE);
        //JOptionPane.showMessageDialog(null, "Error Serial Number greater than 16","Error",JOptionPane.ERROR_MESSAGE);
      }
      else
      {
        Meter.setSerialNumber(serialNumber);
        SNMedidorr = Meter.getSerialNumberCompl();
        jTextFieldSerialNumber.setText(serialNumber);
        JOptionPane.showMessageDialog(null, "Número de Serie listo", "Información", JOptionPane.INFORMATION_MESSAGE);
        //JOptionPane.showMessageDialog(null, "Serial Number ready","Information",JOptionPane.INFORMATION_MESSAGE);
        jButtonSerialNumber.setText("Cambiar Número de Serie");
        //jButtonSerialNumber.setText("Change Serial Number");
        
        jButtonStartStop.setEnabled(true);
        jButtonUnitReading.setEnabled(true);
        
        JPanel currentPanel = null;
        Set set = null;
        Iterator it = null;
        Launcher.getMainFrame();set = JFrameMain.getJPanels().entrySet();
        it = set.iterator();
        while (it.hasNext())
        {
          Map.Entry entry = (Map.Entry)it.next();
          if (((Integer)entry.getValue()).intValue() == 5)
          {
            JPanel pane = (JPanel)entry.getKey();
            currentPanel = pane;
          }
        }
        Resets rest = (Resets)currentPanel;
        rest.setActualSN(SNMedidorr);
      }
    }
  }
  
  private void jButtonExportCurrentReadingsToTxtActionPerformed(ActionEvent evt)
  {
    if (jCheckBoxLogMode.isSelected()) {
      createFileTxt();
    } else {
      exportarTxt();
    }
  }
  
  private void jButtonExportCurrentReadingsToXMLActionPerformed(ActionEvent evt)
  {
    timeToXML.start();
  }
  
  private void jButtonUnitReadingActionPerformed(ActionEvent evt)
  {
    if (port.getStatePort() == true)
    {
      read.setStatusReadings(true);
      
      datos = readingUnit();
      if (datos == null)
      {
        JOptionPane.showMessageDialog(null, "No hay respuesta del medidor, revisa número de serie, o, que este conectado", "Advertencia", JOptionPane.WARNING_MESSAGE);
        //JOptionPane.showMessageDialog(null, "Meter not response, check serial number or that is connected","Error",JOptionPane.ERROR_MESSAGE);
      }
      else
      {
        procesingReadingsTable(datos);
        jButtonExportCurrentReadingsToTxt.setEnabled(true);
        jButtonExportCurrentReadingsToXML.setEnabled(true);
        jButtonLogConfiguration.setEnabled(true);
        read.setStatusReadings(false);
      }
    }
  }
  
  public void getPorts()
  {
    model = ((DefaultComboBoxModel)jComboBoxPort.getModel());
    model.removeAllElements();
    for (int i = 0; i < port.getPortList().size(); i++) {
      model.addElement(port.getPortList().get(i));
    }
    jComboBoxPort.setModel(model);
    jComboBoxPort.updateUI();
    jComboBoxPort.revalidate();
    jComboBoxPort.repaint();
  }
  
  public void refreshPorts()
  {
    model = ((DefaultComboBoxModel)jComboBoxPort.getModel());
    model.removeAllElements();
    for (int i = 0; i < port.getPortList().size(); i++) {
      model.addElement(port.getPortList().get(i));
    }
    jComboBoxPort.updateUI();
    jComboBoxPort.revalidate();
    jComboBoxPort.repaint();
  }
  
  public void procesingReadingsTable(double[] data)
  {
    timeStampCorto = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());
    timeStampComplete = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS").format(Calendar.getInstance().getTime());
    this.timeStampHora = new SimpleDateFormat("HH-mm-ss").format(Calendar.getInstance().getTime());
    
    StringBuilder sb = new StringBuilder();
    long intMajorVer = (int)data[1] >> 10 & 0x3F;
    long intMinorVer = (int)data[1] >> 5 & 0x1F;
    long intRevVer = (int)data[1] & 0x1F;
    String firmconverter = "";
    firmconverter = Long.toString(intMajorVer) + "." + Long.toString(intMinorVer) + "." + Long.toString(intRevVer);
    
    Date date = new Date();
    long timeStamplocal = (int)data[6];
    timeStamplocal *= 1000L;
    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    
    format.setTimeZone(TimeZone.getTimeZone("UTC"));
    
    int offset = (byte)(int)TimeUnit.MILLISECONDS.toHours(TimeZone.getDefault().getOffset(date.getTime()));
    
    sb.append(format.format(new Date(timeStamplocal + 946684800000L)).toString());
    setRow(ROW_TYPE.ROW_Localtime, COL_PHASE.SYS, sb.toString());
    
    this.ROW_Localtime2 = format.format(new Date(timeStamplocal + 946684800000L)).toString();
    sb.delete(0, sb.length());
    
    format.setTimeZone(TimeZone.getTimeZone("GMT" + offset + ""));
    if (Double.compare(65535.0D, data[0]) == 0)
    {
      setRow(ROW_TYPE.ROW_KH, COL_PHASE.SYS, "----");
      this.ROW_KH2 = "----";
    }
    else
    {
      setRow(ROW_TYPE.ROW_KH, COL_PHASE.SYS, Double.toString(data[0]));
      this.ROW_KH2 = this.conv.doubleRound(data[0]);
    }
    setRow(ROW_TYPE.ROW_FWVersion, COL_PHASE.SYS, firmconverter);
    this.ROW_Firmware1 = firmconverter;
    if (Double.compare(65535.0D, data[2]) == 0)
    {
      setRow(ROW_TYPE.ROW_KWH, COL_PHASE.SYS, "----");
      this.ROW_KWH2 = "----";
    }
    else
    {
      setRow(ROW_TYPE.ROW_KWH, COL_PHASE.SYS, this.conv.doubleRound(data[2]));
      this.ROW_KWH2 = this.conv.doubleRound(data[2]);
    }
    if (Double.compare(65535.0D, data[3]) == 0)
    {
      setRow(ROW_TYPE.ROW_KVARH, COL_PHASE.SYS, "----");
      this.ROW_KVARH2 = "----";
    }
    else
    {
      setRow(ROW_TYPE.ROW_KVARH, COL_PHASE.SYS, this.conv.doubleRound(data[3]));
      this.ROW_KVARH2 = this.conv.doubleRound(data[3]);
    }
    if (Double.compare(65535.0D, data[4]) == 0)
    {
      setRow(ROW_TYPE.ROW_EnergiaNegativaActivaTotal, COL_PHASE.SYS, "----");
      this.ROW_EnergiaNegativaActivaTotal2 = "----";
    }
    else
    {
      setRow(ROW_TYPE.ROW_EnergiaNegativaActivaTotal, COL_PHASE.SYS, this.conv.doubleRound(data[4]));
      this.ROW_EnergiaNegativaActivaTotal2 = this.conv.doubleRound(data[4]);
    }
    if (Double.compare(65535.0D, data[5]) == 0)
    {
      setRow(ROW_TYPE.ROW_EnergiaNegativaReactivaTotal, COL_PHASE.SYS, "----");
      this.ROW_EnergiaNegativaReactivaTotal2 = "----";
    }
    else
    {
      setRow(ROW_TYPE.ROW_EnergiaNegativaReactivaTotal, COL_PHASE.SYS, this.conv.doubleRound(data[5]));
      this.ROW_EnergiaNegativaReactivaTotal2 = this.conv.doubleRound(data[5]);
    }
    if (Double.compare(65535.0D, data[7]) == 0)
    {
      setRow(ROW_TYPE.ROW_HZ, COL_PHASE.SYS, "----");
      this.ROW_HZ2 = "----";
    }
    else
    {
      setRow(ROW_TYPE.ROW_HZ, COL_PHASE.SYS, this.conv.doubleRound(data[7]));
      this.ROW_HZ2 = this.conv.doubleRound(data[7]);
    }
    if (Double.compare(65535.0D, data[8]) == 0)
    {
      setRow(ROW_TYPE.ROW_VOLTAGE, COL_PHASE.PHASE_1, "----");
      this.ROW_VOLTAGEP1 = "----";
    }
    else
    {
      setRow(ROW_TYPE.ROW_VOLTAGE, COL_PHASE.PHASE_1, this.conv.doubleRound(data[8]));
      this.ROW_VOLTAGEP1 = this.conv.doubleRound(data[8]);
    }
    if (Double.compare(65535.0D, data[9]) == 0)
    {
      setRow(ROW_TYPE.ROW_VOLTAGE, COL_PHASE.PHASE_2, "----");
      this.ROW_VOLTAGEP2 = "----";
    }
    else
    {
      setRow(ROW_TYPE.ROW_VOLTAGE, COL_PHASE.PHASE_2, this.conv.doubleRound(data[9]));
      this.ROW_VOLTAGEP2 = this.conv.doubleRound(data[9]);
    }
    if (Double.compare(65535.0D, data[10]) == 0)
    {
      setRow(ROW_TYPE.ROW_VOLTAGE, COL_PHASE.PHASE_3, "----");
      this.ROW_VOLTAGEP3 = "----";
    }
    else
    {
      setRow(ROW_TYPE.ROW_VOLTAGE, COL_PHASE.PHASE_3, this.conv.doubleRound(data[10]));
      this.ROW_VOLTAGEP3 = this.conv.doubleRound(data[10]);
    }
    if (Double.compare(65535.0D, data[11]) == 0)
    {
      setRow(ROW_TYPE.ROW_CURRENT, COL_PHASE.PHASE_1, "----");
      this.ROW_CURRENTP1 = "----";
    }
    else
    {
      setRow(ROW_TYPE.ROW_CURRENT, COL_PHASE.PHASE_1, this.conv.doubleRound(data[11]));
      this.ROW_CURRENTP1 = this.conv.doubleRound(data[11]);
    }
    if (Double.compare(65535.0D, data[12]) == 0)
    {
      setRow(ROW_TYPE.ROW_CURRENT, COL_PHASE.PHASE_2, "----");
      this.ROW_CURRENTP2 = "----";
    }
    else
    {
      setRow(ROW_TYPE.ROW_CURRENT, COL_PHASE.PHASE_2, this.conv.doubleRound(data[12]));
      this.ROW_CURRENTP2 = this.conv.doubleRound(data[12]);
    }
    if (Double.compare(65535.0D, data[13]) == 0)
    {
      setRow(ROW_TYPE.ROW_CURRENT, COL_PHASE.PHASE_3, "----");
      this.ROW_CURRENTP3 = "----";
    }
    else
    {
      setRow(ROW_TYPE.ROW_CURRENT, COL_PHASE.PHASE_3, this.conv.doubleRound(data[13]));
      this.ROW_CURRENTP3 = this.conv.doubleRound(data[13]);
    }
    if (Double.compare(65535.0D, data[14]) == 0)
    {
      setRow(ROW_TYPE.ROW_SYS_POWER_FACTOR, COL_PHASE.SYS, "----");
      this.ROW_SYS_POWER_FACTOR2 = "----";
    }
    else
    {
      setRow(ROW_TYPE.ROW_SYS_POWER_FACTOR, COL_PHASE.SYS, this.conv.doubleRound(data[14]));
      this.ROW_SYS_POWER_FACTOR2 = this.conv.doubleRound(data[14]);
    }
    if (Double.compare(65535.0D, data[15]) == 0)
    {
      setRow(ROW_TYPE.ROW_SYS_POWER_FACTOR, COL_PHASE.PHASE_1, "----");
      this.ROW_SYS_POWER_FACTOR_FASES1 = "----";
    }
    else
    {
      setRow(ROW_TYPE.ROW_SYS_POWER_FACTOR, COL_PHASE.PHASE_1, this.conv.doubleRound(data[15]));
      this.ROW_SYS_POWER_FACTOR_FASES1 = this.conv.doubleRound(data[15]);
    }
    if (Double.compare(65535.0D, data[16]) == 0)
    {
      setRow(ROW_TYPE.ROW_SYS_POWER_FACTOR, COL_PHASE.PHASE_2, "----");
      this.ROW_SYS_POWER_FACTOR_FASES2 = "----";
    }
    else
    {
      setRow(ROW_TYPE.ROW_SYS_POWER_FACTOR, COL_PHASE.PHASE_2, this.conv.doubleRound(data[16]));
      this.ROW_SYS_POWER_FACTOR_FASES2 = this.conv.doubleRound(data[16]);
    }
    if (Double.compare(65535.0D, data[17]) == 0)
    {
      setRow(ROW_TYPE.ROW_SYS_POWER_FACTOR, COL_PHASE.PHASE_3, "----");
      this.ROW_SYS_POWER_FACTOR_FASES3 = "----";
    }
    else
    {
      setRow(ROW_TYPE.ROW_SYS_POWER_FACTOR, COL_PHASE.PHASE_3, this.conv.doubleRound(data[17]));
      this.ROW_SYS_POWER_FACTOR_FASES3 = this.conv.doubleRound(data[17]);
    }
    if (Double.compare(65535.0D, data[18]) == 0)
    {
      setRow(ROW_TYPE.ROW_DEMANDAROLActivaEnt, COL_PHASE.SYS, "----");
      this.ROW_DemandaRoladaActivaEnt2 = "----";
    }
    else
    {
      setRow(ROW_TYPE.ROW_DEMANDAROLActivaEnt, COL_PHASE.SYS, this.conv.doubleRound(data[18]));
      this.ROW_DemandaRoladaActivaEnt2 = this.conv.doubleRound(data[18]);
    }
    sb.delete(0, sb.length());
    if (data[19] <= 0.0D)
    {
      setRow(ROW_TYPE.ROW_DEMANDAROLActivaEnt, COL_PHASE.PHASE_3, "----");
      this.ROW_TiempoDemRoladaActivaEnt2 = "----";
    }
    else
    {
      long TiempoDemRoladaActivaEnt = (int)data[19];
      TiempoDemRoladaActivaEnt *= 1000L;
      long val = TiempoDemRoladaActivaEnt + 946684800000L + offset;
      sb.append(format.format(new Date(TiempoDemRoladaActivaEnt + 946684800000L)).toString());
      setRow(ROW_TYPE.ROW_DEMANDAROLActivaEnt, COL_PHASE.PHASE_3, sb.toString());
      
      this.ROW_TiempoDemRoladaActivaEnt2 = format.format(new Date(TiempoDemRoladaActivaEnt + 946684800000L)).toString();
    }
    if (Double.compare(65535.0D, data[20]) == 0)
    {
      setRow(ROW_TYPE.ROW_DEMANDAROLReactivaEnt, COL_PHASE.SYS, "----");
      this.ROW_DemandaRoladaReactivaEnt2 = "----";
    }
    else
    {
      setRow(ROW_TYPE.ROW_DEMANDAROLReactivaEnt, COL_PHASE.SYS, this.conv.doubleRound(data[20]));
      this.ROW_DemandaRoladaReactivaEnt2 = this.conv.doubleRound(data[20]);
    }
    sb.delete(0, sb.length());
    if (data[21] <= 0.0D)
    {
      setRow(ROW_TYPE.ROW_DEMANDAROLReactivaEnt, COL_PHASE.PHASE_3, "----");
      this.ROW_TiempoDemRoladaReactivaEnt2 = "----";
    }
    else
    {
      long TiempoDemRoladaReactivaEnt = (int)data[21];
      TiempoDemRoladaReactivaEnt *= 1000L;
      sb.append(format.format(new Date(TiempoDemRoladaReactivaEnt + 946684800000L)).toString());
      setRow(ROW_TYPE.ROW_DEMANDAROLReactivaEnt, COL_PHASE.PHASE_3, sb.toString());
      this.ROW_TiempoDemRoladaReactivaEnt2 = format.format(new Date(TiempoDemRoladaReactivaEnt + 946684800000L)).toString();
    }
    if (Double.compare(65535.0D, data[22]) == 0)
    {
      setRow(ROW_TYPE.ROW_DEMANDAROLActivaRecib, COL_PHASE.SYS, "----");
      this.ROW_DemandaRoladaActivaRecib2 = "----";
    }
    else
    {
      setRow(ROW_TYPE.ROW_DEMANDAROLActivaRecib, COL_PHASE.SYS, this.conv.doubleRound(data[22]));
      this.ROW_DemandaRoladaActivaRecib2 = this.conv.doubleRound(data[22]);
    }
    sb.delete(0, sb.length());
    if (data[23] <= 0.0D)
    {
      setRow(ROW_TYPE.ROW_DEMANDAROLActivaRecib, COL_PHASE.PHASE_3, "----");
      this.ROW_TiempoDemRoladaActivaRecib2 = "----";
    }
    else
    {
      sb.delete(0, sb.length());
      long TiempoDemRoladaActivaRecib = (int)data[23];
      TiempoDemRoladaActivaRecib *= 1000L;
      sb.append(format.format(new Date(TiempoDemRoladaActivaRecib + 946684800000L)).toString());
      setRow(ROW_TYPE.ROW_DEMANDAROLActivaRecib, COL_PHASE.PHASE_3, sb.toString());
      
      this.ROW_TiempoDemRoladaActivaRecib2 = format.format(new Date(TiempoDemRoladaActivaRecib + 946684800000L)).toString();
    }
    if (Double.compare(65535.0D, data[24]) == 0)
    {
      setRow(ROW_TYPE.ROW_DEMANDAROLAReactivaRecib, COL_PHASE.SYS, "----");
      this.ROW_DemandaRoladaReactivaRecib2 = "----";
    }
    else
    {
      setRow(ROW_TYPE.ROW_DEMANDAROLAReactivaRecib, COL_PHASE.SYS, this.conv.doubleRound(data[24]));
      this.ROW_DemandaRoladaReactivaRecib2 = this.conv.doubleRound(data[24]);
    }
    sb.delete(0, sb.length());
    if (data[25] <= 0.0D)
    {
      setRow(ROW_TYPE.ROW_DEMANDAROLAReactivaRecib, COL_PHASE.PHASE_3, "----");
      this.ROW_TiempoDemRoladaReactivaRecib2 = "----";
    }
    else
    {
      long TiempoDemRoladaReactivaRecib = (int)data[25];
      TiempoDemRoladaReactivaRecib *= 1000L;
      sb.append(format.format(new Date(TiempoDemRoladaReactivaRecib + 946684800000L)).toString());
      setRow(ROW_TYPE.ROW_DEMANDAROLAReactivaRecib, COL_PHASE.PHASE_3, sb.toString());
      
      this.ROW_TiempoDemRoladaReactivaRecib2 = format.format(new Date(TiempoDemRoladaReactivaRecib + 946684800000L)).toString();
    }
    if (Double.compare(65535.0D, data[26]) == 0)
    {
      setRow(ROW_TYPE.ROW_SYS_REAL_POWER, COL_PHASE.SYS, "----");
      this.ROW_SYS_REAL_POWER2 = "----";
    }
    else
    {
      setRow(ROW_TYPE.ROW_SYS_REAL_POWER, COL_PHASE.SYS, this.conv.doubleRound(data[26]));
      this.ROW_SYS_REAL_POWER2 = this.conv.doubleRound(data[26]);
    }
    if (Double.compare(65535.0D, data[27]) == 0)
    {
      setRow(ROW_TYPE.ROW_SYS_REACTIVE_POWER, COL_PHASE.SYS, "----");
      this.ROW_SYS_REACTIVE_POWER2 = "----";
    }
    else
    {
      setRow(ROW_TYPE.ROW_SYS_REACTIVE_POWER, COL_PHASE.SYS, this.conv.doubleRound(data[27]));
      this.ROW_SYS_REACTIVE_POWER2 = this.conv.doubleRound(data[27]);
    }
    if (Double.compare(65535.0D, data[28]) == 0)
    {
      setRow(ROW_TYPE.ROW_SYS_APPARENT_POWER, COL_PHASE.SYS, "----");
      this.ROW_SYS_APPARENT_POWER2 = "----";
    }
    else
    {
      setRow(ROW_TYPE.ROW_SYS_APPARENT_POWER, COL_PHASE.SYS, this.conv.doubleRound(data[28]));
      this.ROW_SYS_APPARENT_POWER2 = this.conv.doubleRound(data[28]);
    }
    if (Double.compare(65535.0D, data[29]) == 0)
    {
      setRow(ROW_TYPE.ROW_KWH, COL_PHASE.PHASE_1, "----");
      this.ROW_EnergiaActiva1 = "----";
    }
    else
    {
      setRow(ROW_TYPE.ROW_KWH, COL_PHASE.PHASE_1, this.conv.doubleRound(data[29]));
      this.ROW_EnergiaActiva1 = this.conv.doubleRound(data[29]);
    }
    if (Double.compare(65535.0D, data[30]) == 0)
    {
      setRow(ROW_TYPE.ROW_KWH, COL_PHASE.PHASE_2, "----");
      this.ROW_EnergiaActiva2 = "----";
    }
    else
    {
      setRow(ROW_TYPE.ROW_KWH, COL_PHASE.PHASE_2, this.conv.doubleRound(data[30]));
      this.ROW_EnergiaActiva2 = this.conv.doubleRound(data[30]);
    }
    if (Double.compare(65535.0D, data[31]) == 0)
    {
      setRow(ROW_TYPE.ROW_KWH, COL_PHASE.PHASE_3, "----");
      this.ROW_EnergiaActiva3 = "----";
    }
    else
    {
      setRow(ROW_TYPE.ROW_KWH, COL_PHASE.PHASE_3, this.conv.doubleRound(data[31]));
      this.ROW_EnergiaActiva3 = this.conv.doubleRound(data[31]);
    }
    if (Double.compare(65535.0D, data[32]) == 0)
    {
      setRow(ROW_TYPE.ROW_KVARH, COL_PHASE.PHASE_1, "----");
      this.ROW_EnergiaReactiva1 = "----";
    }
    else
    {
      setRow(ROW_TYPE.ROW_KVARH, COL_PHASE.PHASE_1, this.conv.doubleRound(data[32]));
      this.ROW_EnergiaReactiva1 = this.conv.doubleRound(data[32]);
    }
    if (Double.compare(65535.0D, data[33]) == 0)
    {
      setRow(ROW_TYPE.ROW_KVARH, COL_PHASE.PHASE_2, "----");
      this.ROW_EnergiaReactiva2 = "----";
    }
    else
    {
      setRow(ROW_TYPE.ROW_KVARH, COL_PHASE.PHASE_2, this.conv.doubleRound(data[33]));
      this.ROW_EnergiaReactiva2 = this.conv.doubleRound(data[33]);
    }
    if (Double.compare(65535.0D, data[34]) == 0)
    {
      setRow(ROW_TYPE.ROW_KVARH, COL_PHASE.PHASE_3, "----");
      this.ROW_EnergiaReactiva3 = "----";
    }
    else
    {
      setRow(ROW_TYPE.ROW_KVARH, COL_PHASE.PHASE_3, this.conv.doubleRound(data[34]));
      this.ROW_EnergiaReactiva3 = this.conv.doubleRound(data[34]);
    }
    if (Double.compare(65535.0D, data[35]) == 0)
    {
      setRow(ROW_TYPE.ROW_EnergiaNegativaActivaTotal, COL_PHASE.PHASE_1, "----");
      this.ROW_EnergiaNegativaActiva1 = "----";
    }
    else
    {
      setRow(ROW_TYPE.ROW_EnergiaNegativaActivaTotal, COL_PHASE.PHASE_1, this.conv.doubleRound(data[35]));
      this.ROW_EnergiaNegativaActiva1 = this.conv.doubleRound(data[35]);
    }
    if (Double.compare(65535.0D, data[36]) == 0)
    {
      setRow(ROW_TYPE.ROW_EnergiaNegativaActivaTotal, COL_PHASE.PHASE_2, "----");
      this.ROW_EnergiaNegativaActiva2 = "----";
    }
    else
    {
      setRow(ROW_TYPE.ROW_EnergiaNegativaActivaTotal, COL_PHASE.PHASE_2, this.conv.doubleRound(data[36]));
      this.ROW_EnergiaNegativaActiva2 = this.conv.doubleRound(data[36]);
    }
    if (Double.compare(65535.0D, data[37]) == 0)
    {
      setRow(ROW_TYPE.ROW_EnergiaNegativaActivaTotal, COL_PHASE.PHASE_3, "----");
      this.ROW_EnergiaNegativaActiva3 = "----";
    }
    else
    {
      setRow(ROW_TYPE.ROW_EnergiaNegativaActivaTotal, COL_PHASE.PHASE_3, this.conv.doubleRound(data[37]));
      this.ROW_EnergiaNegativaActiva3 = this.conv.doubleRound(data[37]);
    }
    if (Double.compare(65535.0D, data[38]) == 0)
    {
      setRow(ROW_TYPE.ROW_EnergiaNegativaReactivaTotal, COL_PHASE.PHASE_1, "----");
      this.ROW_EnergiaNegativaReactiva1 = "----";
    }
    else
    {
      setRow(ROW_TYPE.ROW_EnergiaNegativaReactivaTotal, COL_PHASE.PHASE_1, this.conv.doubleRound(data[38]));
      this.ROW_EnergiaNegativaReactiva1 = this.conv.doubleRound(data[38]);
    }
    if (Double.compare(65535.0D, data[39]) == 0)
    {
      setRow(ROW_TYPE.ROW_EnergiaNegativaReactivaTotal, COL_PHASE.PHASE_2, "----");
      this.ROW_EnergiaNegativaReactiva2 = "----";
    }
    else
    {
      setRow(ROW_TYPE.ROW_EnergiaNegativaReactivaTotal, COL_PHASE.PHASE_2, this.conv.doubleRound(data[39]));
      this.ROW_EnergiaNegativaReactiva2 = this.conv.doubleRound(data[39]);
    }
    if (Double.compare(65535.0D, data[40]) == 0)
    {
      setRow(ROW_TYPE.ROW_EnergiaNegativaReactivaTotal, COL_PHASE.PHASE_3, "----");
      this.ROW_EnergiaNegativaReactiva3 = "----";
    }
    else
    {
      setRow(ROW_TYPE.ROW_EnergiaNegativaReactivaTotal, COL_PHASE.PHASE_3, this.conv.doubleRound(data[40]));
      this.ROW_EnergiaNegativaReactiva3 = this.conv.doubleRound(data[40]);
    }
    if (Double.compare(65535.0D, data[41]) == 0)
    {
      setRow(ROW_TYPE.ROW_SYS_REAL_POWER, COL_PHASE.PHASE_1, "----");
      this.ROW_PotenciaActiva1 = "----";
    }
    else
    {
      setRow(ROW_TYPE.ROW_SYS_REAL_POWER, COL_PHASE.PHASE_1, this.conv.doubleRound(data[41]));
      this.ROW_PotenciaActiva1 = this.conv.doubleRound(data[41]);
    }
    if (Double.compare(65535.0D, data[42]) == 0)
    {
      setRow(ROW_TYPE.ROW_SYS_REAL_POWER, COL_PHASE.PHASE_2, "----");
      this.ROW_PotenciaActiva2 = "----";
    }
    else
    {
      setRow(ROW_TYPE.ROW_SYS_REAL_POWER, COL_PHASE.PHASE_2, this.conv.doubleRound(data[42]));
      this.ROW_PotenciaActiva2 = this.conv.doubleRound(data[42]);
    }
    if (Double.compare(65535.0D, data[43]) == 0)
    {
      setRow(ROW_TYPE.ROW_SYS_REAL_POWER, COL_PHASE.PHASE_3, "----");
      this.ROW_PotenciaActiva3 = "----";
    }
    else
    {
      setRow(ROW_TYPE.ROW_SYS_REAL_POWER, COL_PHASE.PHASE_3, this.conv.doubleRound(data[43]));
      this.ROW_PotenciaActiva3 = this.conv.doubleRound(data[43]);
    }
    if (Double.compare(65535.0D, data[44]) == 0)
    {
      setRow(ROW_TYPE.ROW_SYS_REACTIVE_POWER, COL_PHASE.PHASE_1, "----");
      this.ROW_PotenciaReactiva1 = "----";
    }
    else
    {
      setRow(ROW_TYPE.ROW_SYS_REACTIVE_POWER, COL_PHASE.PHASE_1, this.conv.doubleRound(data[44]));
      this.ROW_PotenciaReactiva1 = this.conv.doubleRound(data[44]);
    }
    if (Double.compare(65535.0D, data[45]) == 0)
    {
      setRow(ROW_TYPE.ROW_SYS_REACTIVE_POWER, COL_PHASE.PHASE_2, "----");
      this.ROW_PotenciaReactiva2 = "----";
    }
    else
    {
      setRow(ROW_TYPE.ROW_SYS_REACTIVE_POWER, COL_PHASE.PHASE_2, this.conv.doubleRound(data[45]));
      this.ROW_PotenciaReactiva2 = this.conv.doubleRound(data[45]);
    }
    if (Double.compare(65535.0D, data[46]) == 0)
    {
      setRow(ROW_TYPE.ROW_SYS_REACTIVE_POWER, COL_PHASE.PHASE_3, "----");
      this.ROW_PotenciaReactiva3 = "----";
    }
    else
    {
      setRow(ROW_TYPE.ROW_SYS_REACTIVE_POWER, COL_PHASE.PHASE_3, this.conv.doubleRound(data[46]));
      this.ROW_PotenciaReactiva3 = this.conv.doubleRound(data[46]);
    }
    if (Double.compare(65535.0D, data[47]) == 0)
    {
      setRow(ROW_TYPE.ROW_SYS_APPARENT_POWER, COL_PHASE.PHASE_1, "----");
      this.ROW_PotenciaAparente1 = "----";
    }
    else
    {
      setRow(ROW_TYPE.ROW_SYS_APPARENT_POWER, COL_PHASE.PHASE_1, this.conv.doubleRound(data[47]));
      this.ROW_PotenciaAparente1 = this.conv.doubleRound(data[47]);
    }
    if (Double.compare(65535.0D, data[48]) == 0)
    {
      setRow(ROW_TYPE.ROW_SYS_APPARENT_POWER, COL_PHASE.PHASE_2, "----");
      this.ROW_PotenciaAparente2 = "----";
    }
    else
    {
      setRow(ROW_TYPE.ROW_SYS_APPARENT_POWER, COL_PHASE.PHASE_2, this.conv.doubleRound(data[48]));
      this.ROW_PotenciaAparente2 = this.conv.doubleRound(data[48]);
    }
    if (Double.compare(65535.0D, data[49]) == 0)
    {
      setRow(ROW_TYPE.ROW_SYS_APPARENT_POWER, COL_PHASE.PHASE_3, "----");
      this.ROW_PotenciaAparente3 = "----";
    }
    else
    {
      setRow(ROW_TYPE.ROW_SYS_APPARENT_POWER, COL_PHASE.PHASE_3, this.conv.doubleRound(data[49]));
      this.ROW_PotenciaAparente3 = this.conv.doubleRound(data[49]);
    }
    if ((this.startRecord == true & this.jCheckBoxLogMode.isSelected())) {
      setRegisterExportLog();
    }
  }
  
  public void readingsExport()
  {
    ArrayList<String> keyList = new ArrayList();
    ArrayList<String> valueList = new ArrayList();
    keyList.add("FECHA:-----------------------------------------------------");
    keyList.add("KH:");
    keyList.add("FIRMWARE:");
    keyList.add("WH_ENERGIA_ACTIVA_A:");
    keyList.add("WH_ENERGIA_ACTIVA_B:");
    keyList.add("WH_ENERGIA_ACTIVA_C:");
    keyList.add("KWH_ENERGIA_ACTIVA_TOTAL:");
    keyList.add("VArh_E_REACTIVA_A:");
    keyList.add("VArh_E_REACTIVA_B:");
    keyList.add("VArh_E_REACTIVA_C:");
    keyList.add("KVARH_E_REACTIVA_TOTAL:");
    keyList.add("E-WH_E_NEGATIVA_ACTIVA_A:");
    keyList.add("E-WH_E_NEGATIVA_ACTIVA_B:");
    keyList.add("E-WH_E_NEGATIVA_ACTIVA_C:");
    keyList.add("E-KWH_E_NEGATIVA_ACTIVA_TOTAL:");
    keyList.add("E-VArh_E_NEGATIVA_REACTIVA_A:");
    keyList.add("E-VArh_E_NEGATIVA_REACTIVA_B:");
    keyList.add("E-VArh_E_NEGATIVA_REACTIVA_C:");
    keyList.add("E-KVARH_E_NEGATIVA_REACTIVA_TOTAL:");
    keyList.add("FECHA_DEL_SISTEMA:");
    keyList.add("HZ_FRECUENCIA:");
    keyList.add("V_VOLTAJE_A:");
    keyList.add("V_VOLTAJE_B:");
    keyList.add("V_VOLTAJE_C:");
    keyList.add("A_CORRIENTE_A:");
    keyList.add("A_CORRIENTE_B:");
    keyList.add("A_CORRIENTE_C:");
    keyList.add("FP_A:");
    keyList.add("FP_B:");
    keyList.add("FP_C:");
    keyList.add("FP_DEL_SISTEMA:");
    keyList.add("Wh_DEMANDA_ROL_ACTIVA_ENT:");
    keyList.add("TIEMPO_DEMANDA_ROL_ACTIVA_ENT:");
    keyList.add("Wh_DEMANDA_ROL_REACTIVA_ENT:");
    keyList.add("TIEMPO_DEMANDA_ROL_REACTIVA_ENT:");
    keyList.add("Wh_DEMANDA_ROL_ACTIVA_RECIB:");
    keyList.add("TIEMPO_DEMANDA_ROL_ACTIVA_RECIB:");
    keyList.add("Wh_DEMANDA_ROL_REACTIVA_RECIB:");
    keyList.add("TIEMPO_DEMANDA_ROL_REACTIVA_RECIB:");
    keyList.add("W_POTENCIA_ACTIVA_A:");
    keyList.add("W_POTENCIA_ACTIVA_B:");
    keyList.add("W_POTENCIA_ACTIVA_C:");
    keyList.add("W_POTENCIA_ACTIVA_TOTAL:");
    keyList.add("VAr_POTENCIA_REACTIVA_A:");
    keyList.add("VAr_POTENCIA_REACTIVA_B:");
    keyList.add("VAr_POTENCIA_REACTIVA_C:");
    keyList.add("VAr_POTENCIA_REACTIVA_TOTAL:");
    keyList.add("VA_POTENCIA_APARENTE_A:");
    keyList.add("VA_POTENCIA_APARENTE_B:");
    keyList.add("VA_POTENCIA_APARENTE_C:");
    keyList.add("VA_POTENCIA_APARENTE_TOTAL:");
    
    valueList.add(this.timeStampComplete);
    valueList.add(this.ROW_KH2);
    valueList.add(this.ROW_Firmware1);
    valueList.add(this.ROW_EnergiaActiva1);
    valueList.add(this.ROW_EnergiaActiva2);
    valueList.add(this.ROW_EnergiaActiva3);
    valueList.add(this.ROW_KWH2);
    valueList.add(this.ROW_EnergiaReactiva1);
    valueList.add(this.ROW_EnergiaReactiva2);
    valueList.add(this.ROW_EnergiaReactiva3);
    valueList.add(this.ROW_KVARH2);
    valueList.add(this.ROW_EnergiaNegativaActiva1);
    valueList.add(this.ROW_EnergiaNegativaActiva2);
    valueList.add(this.ROW_EnergiaNegativaActiva3);
    valueList.add(this.ROW_EnergiaNegativaActivaTotal2);
    valueList.add(this.ROW_EnergiaNegativaReactiva1);
    valueList.add(this.ROW_EnergiaNegativaReactiva2);
    valueList.add(this.ROW_EnergiaNegativaReactiva3);
    valueList.add(this.ROW_EnergiaNegativaReactivaTotal2);
    valueList.add(this.ROW_Localtime2);
    valueList.add(this.ROW_HZ2);
    valueList.add(this.ROW_VOLTAGEP1);
    valueList.add(this.ROW_VOLTAGEP2);
    valueList.add(this.ROW_VOLTAGEP3);
    valueList.add(this.ROW_CURRENTP1);
    valueList.add(this.ROW_CURRENTP2);
    valueList.add(this.ROW_CURRENTP3);
    valueList.add(this.ROW_SYS_POWER_FACTOR_FASES1);
    valueList.add(this.ROW_SYS_POWER_FACTOR_FASES2);
    valueList.add(this.ROW_SYS_POWER_FACTOR_FASES3);
    valueList.add(this.ROW_SYS_POWER_FACTOR2);
    valueList.add(this.ROW_DemandaRoladaActivaEnt2);
    valueList.add(this.ROW_TiempoDemRoladaActivaEnt2);
    valueList.add(this.ROW_DemandaRoladaReactivaEnt2);
    valueList.add(this.ROW_TiempoDemRoladaReactivaEnt2);
    valueList.add(this.ROW_DemandaRoladaActivaRecib2);
    valueList.add(this.ROW_TiempoDemRoladaActivaRecib2);
    valueList.add(this.ROW_DemandaRoladaReactivaRecib2);
    valueList.add(this.ROW_TiempoDemRoladaReactivaRecib2);
    valueList.add(this.ROW_PotenciaActiva1);
    valueList.add(this.ROW_PotenciaActiva2);
    valueList.add(this.ROW_PotenciaActiva3);
    valueList.add(this.ROW_SYS_REAL_POWER2);
    valueList.add(this.ROW_PotenciaReactiva1);
    valueList.add(this.ROW_PotenciaReactiva2);
    valueList.add(this.ROW_PotenciaReactiva3);
    valueList.add(this.ROW_SYS_REACTIVE_POWER2);
    valueList.add(this.ROW_PotenciaAparente1);
    valueList.add(this.ROW_PotenciaAparente2);
    valueList.add(this.ROW_PotenciaAparente3);
    valueList.add(this.ROW_SYS_APPARENT_POWER2);
    for (int i = 0; i < 51; i++) {
      listReadingsComplete.put(keyList.get(i), valueList.get(i));
    }
    String timeStampComplete = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss.SSS").format(Calendar.getInstance().getTime());
    if (jCheckBoxSelected.isSelected())
    {
      Iterator it = null;
      Set set = this.listSelected.entrySet();
      it = set.iterator();
      while (it.hasNext())
      {
        Map.Entry entry = (Map.Entry)it.next();
        String value = entry.getValue().toString();
        String key = entry.getKey().toString();
        for (int i = 0; i < 51; i++) {
          if ((keyList.get(i) == key) && (value == "true")) {
            listReadings.put(keyList.get(i), valueList.get(i));
          }
        }
      }
    }
    else
    {
      for (int i = 0; i < 51; i++) {
        listReadings.put(keyList.get(i), valueList.get(i));
      }
    }
  }
  
  public void exportarTxt()
  {
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
      Path path = Paths.get(this.fileData + "/", new String[0]);
      fileTxt = new File(this.fileData + "/" + timeStampHora + ".txt");
      if (Files.exists(path, new LinkOption[0])) {}
      writer = new BufferedWriter(new FileWriter(this.fileTxt));
      listReadings.clear();
      readingsExport();
      Iterator it = null;
      Set set = listReadings.entrySet();
      it = set.iterator();
      while (it.hasNext())
      {
        Map.Entry entry = (Map.Entry)it.next();
        String value = entry.getValue().toString();
        String key = entry.getKey().toString();
        writer.write("\r\n");
        writer.write(key + value);
        writer.write("\r\n");
      }
      return;
    }
    catch (FileNotFoundException e)
    {
      e.printStackTrace();
      JOptionPane.showMessageDialog(null, "No se encontro el archivo", "Error", JOptionPane.ERROR_MESSAGE);
      //JOptionPane.showMessageDialog(null, "File not found","Error",JOptionPane.ERROR_MESSAGE);
    }
    catch (IOException e)
    {
      e.printStackTrace();
      JOptionPane.showMessageDialog(null, "No se guardo el archivo", "Error", JOptionPane.ERROR_MESSAGE);
      //JOptionPane.showMessageDialog(null, "File not save","Error",JOptionPane.ERROR_MESSAGE);
    }
    finally
    {
      try
      {
        if (writer != null)
        {
          writer.flush();
          writer.close();
          JOptionPane.showMessageDialog(null, "Archivo guardado correctamente!!!", "Información", JOptionPane.INFORMATION_MESSAGE);
          //JOptionPane.showMessageDialog(null, "File saved succesfully","Information",JOptionPane.INFORMATION_MESSAGE);
        }
      }
      catch (IOException e)
      {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "No se guardo el archivo", "Advertencia", JOptionPane.WARNING_MESSAGE);
        //JOptionPane.showMessageDialog(null, "File not save","Error",JOptionPane.ERROR_MESSAGE);
      }
    }
  }
  
  public void createFileTxt()
  {
    File defaultname = new File("C:/LecturasMedidor/" + timeStampCorto + "/");
    JFileChooser f = new JFileChooser();
    f.setFileSelectionMode(1);
    f.showSaveDialog(null);
    
    fileData = f.getSelectedFile();
    if ((fileData.getName().equals("")) || (this.fileData.getName() == null)) {
      fileData = defaultname;
    } else {
      fileData = new File(f.getSelectedFile() + "/" + timeStampCorto + "/");
    }
    if (fileData.mkdirs()) {
      System.out.println("Multiple directories are created!");
    } else {
      System.out.println("Failed to create multiple directories!");
    }
    Path path = Paths.get(fileData + "/", new String[0]);
    fileTxt = new File(fileData + "/" + timeStampHora + ".txt");
    contadorLog = 0;
    try
    {
      writer = new BufferedWriter(new FileWriter(fileTxt));
      startRecord = true;
    }
    catch (FileNotFoundException e)
    {
      e.printStackTrace();
      JOptionPane.showMessageDialog(null, "No se encontro el archivo", "Error", JOptionPane.ERROR_MESSAGE);
      //JOptionPane.showMessageDialog(null, "File not found","Error",JOptionPane.ERROR_MESSAGE);
    }
    catch (IOException e)
    {
      e.printStackTrace();
      JOptionPane.showMessageDialog(null, "No se guardo el archivo", "Error", JOptionPane.ERROR_MESSAGE);
      //JOptionPane.showMessageDialog(null, "File not save","Error",JOptionPane.ERROR_MESSAGE);
    }
  }
  
  public void closeFileLogTxt()
  {
    if (!jCheckBoxLogMode.isSelected()) {
      try
      {
        if (writer != null)
        {
          writer.flush();
          writer.close();
          contadorLog = 0;
          JOptionPane.showMessageDialog(null, "Archivo guardado correctamente!!!", "Información", JOptionPane.INFORMATION_MESSAGE);
          //JOptionPane.showMessageDialog(null, "File saved succesfully","Information",JOptionPane.INFORMATION_MESSAGE);
        }
      }
      catch (IOException e)
      {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "No se guardo el archivo", "Error", JOptionPane.ERROR_MESSAGE);
        //JOptionPane.showMessageDialog(null, "File not save","Error",JOptionPane.ERROR_MESSAGE);
      }
    }
  }
  
  public void setRegisterExportLog()
  {
    try
    {
      listReadings.clear();
      readingsExport();
      Iterator it = null;
      Set set = listReadings.entrySet();
      it = set.iterator();
      while (it.hasNext())
      {
        Map.Entry entry = (Map.Entry)it.next();
        String value = entry.getValue().toString();
        String key = entry.getKey().toString();
        writer.write("\r\n");
        writer.write(key + value);
        writer.write("\r\n");
      }
      contadorLog += 1;
      if (contadorLog == 300)
      {
        try
        {
          if (writer != null)
          {
            writer.flush();
            writer.close();
          }
        }
        catch (IOException e)
        {
          e.printStackTrace();
        }
        contadorLog = 0;
        try
        {
          fileTxt = new File(fileData + "/" + timeStampHora + ".txt");
          
          writer = new BufferedWriter(new FileWriter(fileTxt));
        }
        catch (FileNotFoundException e)
        {
          e.printStackTrace();
          JOptionPane.showMessageDialog(null, "No se encontro el archivo", "Error", JOptionPane.ERROR_MESSAGE);
          //JOptionPane.showMessageDialog(null, "File not found","Error",JOptionPane.ERROR_MESSAGE);
        }
        catch (IOException e)
        {
          e.printStackTrace();
          JOptionPane.showMessageDialog(null, "No se guardo el archivo", "Error", JOptionPane.ERROR_MESSAGE);
          //JOptionPane.showMessageDialog(null, "File not save","Error",JOptionPane.ERROR_MESSAGE);
        }
      }
    }
    catch (FileNotFoundException e)
    {
      e.printStackTrace();
      JOptionPane.showMessageDialog(null, "No se encontro el archivo", "Error", JOptionPane.ERROR_MESSAGE);
      //JOptionPane.showMessageDialog(null, "File not found","Error",JOptionPane.ERROR_MESSAGE);
    }
    catch (IOException e)
    {
      e.printStackTrace();
      JOptionPane.showMessageDialog(null, "No se guardo el archivo", "Error", JOptionPane.ERROR_MESSAGE);
      //JOptionPane.showMessageDialog(null, "File not save","Error",JOptionPane.ERROR_MESSAGE);
    }
  }
  
  ActionListener exporToXML = new ActionListener()
  {
    public void actionPerformed(ActionEvent e)
    {
      CurrentReadingsRecords.this.listReadings.clear();
      CurrentReadingsRecords.this.readingsExport();
      CurrentReadingsRecords.this.read.WriteXMLFile(CurrentReadingsRecords.this.listReadings);
      CurrentReadingsRecords.this.timeToXML.stop();
    }
  };
  
  static enum ROW_TYPE
  {
    ROW_KH(0, ""),  
    ROW_FWVersion(1, ""),  
    ROW_KWH(2, ""),  
    ROW_KVARH(3, ""),  
    ROW_EnergiaNegativaActivaTotal(4, ""),  
    ROW_EnergiaNegativaReactivaTotal(5, ""),  
    ROW_Localtime(6, ""),  
    ROW_HZ(7, ""),  
    ROW_VOLTAGE(8, ""),  
    ROW_CURRENT(9, ""),  
    ROW_SYS_POWER_FACTOR(10, ""),  
    ROW_SYS_REAL_POWER(11, ""),  
    ROW_SYS_REACTIVE_POWER(12, ""),  
    ROW_SYS_APPARENT_POWER(13, ""),  
    ROW_DEMANDAROLActivaEnt(14, ""),  
    ROW_DEMANDAROLReactivaEnt(15, ""),  
    ROW_DEMANDAROLActivaRecib(16, ""),  
    ROW_DEMANDAROLAReactivaRecib(17, "");
    
    int row;
    String units;
    
    private ROW_TYPE(int row, String units)
    {
      this.row = row;
      this.units = units;
    }
    
    public int getRow()
    {
      return this.row;
    }
    
    public String getUnits()
    {
      return this.units;
    }
  }
  
  static enum COL_PHASE
  {
    PHASE_1(1),  PHASE_2(2),  PHASE_3(3),  SYS(4);
    
    int col;
    
    private COL_PHASE(int col)
    {
      this.col = col;
    }
    
    public int getCol()
    {
      return this.col;
    }
  }
  
  private void setRow(ROW_TYPE type, COL_PHASE phase, String value)
  {
    switch (type)
    {
    case ROW_VOLTAGE: 
    case ROW_CURRENT: 
      meterTable.setValueAt(value + " " + type.getUnits(), type.getRow(), phase.getCol());
      break;
    default: 
      meterTable.setValueAt(value + " " + type.getUnits(), type.getRow(), phase.getCol());
    }
  }
  
  private DefaultTableModel meterTable = new DefaultTableModel(new Object[][] { { "KH", 
                                                                                  "---", 
                                                                                  "---", 
                                                                                  "---", 
                                                                                  "---" }, 
                                                                                { "FW Versión", 
                                                                                  "---", 
                                                                                  "---", 
                                                                                  "---", 
                                                                                  "---" }, 
                                                                                { "Energía Activa Entregada Wh", 
                                                                                //{ "Active Energy Delivered V", 
                                                                                  "---", 
                                                                                  "---", 
                                                                                  "---", 
                                                                                  "---" }, 
                                                                                { "Energía Reactiva Entregada varh", 
                                                                                //{ "Reactive Energy Delivered V",
                                                                                  "---", 
                                                                                  "---", 
                                                                                  "---", 
                                                                                  "---" }, 
                                                                                { "Energía Activa Recibida -Wh", 
                                                                                //{ "Active Energy Received -Wh",
                                                                                  "---", 
                                                                                  "---", 
                                                                                  "---", 
                                                                                  "---" }, 
                                                                                { "Energía Reactiva Recibida -varh", 
                                                                                //{ "Reactive Power Received -VArh",
                                                                                  "---", 
                                                                                  "---", 
                                                                                  "---", 
                                                                                  "---" }, 
                                                                                { "Hora Local", 
                                                                                //{ "Local Time",
                                                                                  "---", 
                                                                                  "---", 
                                                                                  "---", 
                                                                                  "---" }, 
                                                                                { "Frecuencia de Línea Hz", 
                                                                                //{ "Line Frequency Hz",
                                                                                  "---", 
                                                                                  "---", 
                                                                                  "---", 
                                                                                  "---" }, 
                                                                                { "Voltaje V", 
                                                                                //{ "Voltage V",
                                                                                  "---", 
                                                                                  "---", 
                                                                                  "---", 
                                                                                  "---" }, 
                                                                                { "Corriente A", 
                                                                                //{ "Current A", 
                                                                                  "---", 
                                                                                  "---", 
                                                                                  "---", 
                                                                                  "---" }, 
                                                                                { "Factor de Potencia [cos (?)]", 
                                                                                //{ "Power Factor [cos (?)]",
                                                                                  "---", 
                                                                                  "---", 
                                                                                  "---", 
                                                                                  "---" }, 
                                                                                { "Potencia Activa [P] W", 
                                                                                //{ "Active Power [P] W",
                                                                                  "---", 
                                                                                  "---", 
                                                                                  "---", 
                                                                                  "---" }, 
                                                                                { "Potencia Reactiva [Q] var", 
                                                                                //{ "Reactive Power [Q] VAr",
                                                                                  "---", 
                                                                                  "---", 
                                                                                  "---", 
                                                                                  "---" }, 
                                                                                { "Potencia Aparente [S] VA", 
                                                                                //{ "Apparent Power [S] VA",
                                                                                  "---", 
                                                                                  "---", 
                                                                                  "---", 
                                                                                  "---" }, 
                                                                                { "Demanda Rol Activa Ent W", 
                                                                                //{ "Delivered Active Rol Demand W",
                                                                                  "---", 
                                                                                  "---", 
                                                                                  "---", 
                                                                                  "---" }, 
                                                                                { "Demanda Rol Reactiva Ent var", 
                                                                                //{ "Delivered Reactive Rol Demand VAr",
                                                                                  "---", 
                                                                                  "---", 
                                                                                  "---", 
                                                                                  "---" }, 
                                                                                { "Demanda Rol Activa Recib W", 
                                                                                //{ "Active Demand Rol Received W",
                                                                                  "---", 
                                                                                  "---", 
                                                                                  "---", 
                                                                                  "---" }, 
                                                                                { "Demanda Rol Reactiva Recib var", 
                                                                                //{ "Reactive Demand Rol Received VAr",
                                                                                  "---", 
                                                                                  "---", 
                                                                                  "---", 
                                                                                  "---" } }, 
                                                                                //new String[] { "Variable", "Phase 1", "Phase 2", "Phase 3", "Sys" })
                                                                                new String[] { "Campo", "Fase 1", "Fase 2", "Fase 3", "Sys" })
  {
    Class[] types = { String.class, String.class, String.class, String.class, String.class };
    boolean[] canEdit = { false, false, false, false, false };
    
    public Class getColumnClass(int columnIndex)
    {
      return types[columnIndex];
    }
    
    public boolean isCellEditable(int rowIndex, int columnIndex)
    {
      return canEdit[columnIndex];
    }
  };
  
  ActionListener taskReadings = new ActionListener()
  {
    public void actionPerformed(ActionEvent e)
    {
      CurrentReadingsRecords.datos = CurrentReadingsRecords.this.readingUnit();
      if (CurrentReadingsRecords.datos == null)
      {
        JOptionPane.showMessageDialog(null, "No hay respuesta del medidor, revisa número de serie o que este conectado", "Error", JOptionPane.ERROR_MESSAGE);
        //JOptionPane.showMessageDialog(null, "Meter not response, check serial number or connection","Error",JOptionPane.ERROR_MESSAGE);
        CurrentReadingsRecords.this.timePoll.stop();
        CurrentReadingsRecords.this.jButtonStartStop.setText("Iniciar Lecturas");
        //CurrentReadingsRecords.this.jButtonStartStop.setText("Start Readings");
      }
      else
      {
        CurrentReadingsRecords.this.procesingReadingsTable(CurrentReadingsRecords.datos);
        CurrentReadingsRecords.this.jButtonExportCurrentReadingsToTxt.setEnabled(true);
        CurrentReadingsRecords.this.jButtonExportCurrentReadingsToXML.setEnabled(true);
        CurrentReadingsRecords.this.jButtonLogConfiguration.setEnabled(true);
        if (((CurrentReadingsRecords.this.startRecord == true ? 1 : 0) & (!CurrentReadingsRecords.this.jCheckBoxLogMode.isSelected() ? 1 : 0)) != 0)
        {
          CurrentReadingsRecords.this.closeFileLogTxt();
          CurrentReadingsRecords.this.startRecord = false;
        }
      }
    }
  };
  
  public double[] readingUnit()
  {
    read.requestReadings(PortComunication.DELAY400);
    datos = read.responseReadings();
    int retrys = 0;
    while ((datos == null) && (retrys < 10))
    {
      read.requestReadings(PortComunication.DELAY400);
      
      datos = read.responseReadings();
      retrys++;
    }
    return datos;
  }
  
  private Timer timePoll = new Timer(10, this.taskReadings);
  private Timer timeToXML = new Timer(100, this.exporToXML);
  private JButton jButtonExportCurrentReadingsToTxt;
  private JButton jButtonExportCurrentReadingsToXML;
  private JButton jButtonLogConfiguration;
  private JButton jButtonOpenPort;
  private JButton jButtonSerialNumber;
  private JButton jButtonStartStop;
  private JButton jButtonUnitReading;
  private JCheckBox jCheckBox1;
  private JCheckBox jCheckBoxLogMode;
  private JCheckBox jCheckBoxSelected;
  private JComboBox jComboBoxPort;
  private JLabel jLabelCurrentReadingsTitle;
  private JLabel jLabelPort;
  private JLabel jLabelPortConfigurations;
  private JLabel jLabelPortSelected;
  private JScrollPane jScrollPane1;
  private JSeparator jSeparator1;
  public JTable jTableCurrentReadings;
  private JTextField jTextFieldMeterSerialNumber;
  private JTextField jTextFieldSerialNumber;
}