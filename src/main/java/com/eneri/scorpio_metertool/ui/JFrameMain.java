/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eneri.scorpio_metertool.ui;

import com.eneri.scorpio_metertool.database.User;
import com.eneri.scorpio_metertool.devices.Readings;
import com.eneri.scorpio_metertool.hardwarelayer.PortComunication;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.help.HelpBroker;
import javax.help.HelpSet;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

/**
 *
 * @author Roberto
 */
public class JFrameMain
  extends JFrame
{
  User us = new User();
  private static int variableTotalHelp;
  private static LinkedHashMap<JPanel, Integer> hasht = new LinkedHashMap();
  public static final int CURRENT_READINGS_RECORD_PANEL = 0;
  public static final int CALIBRATION = 1;
  public static final int FLAGS = 2;
  public static final int CONCENTRATOR_READINGS = 3;
  public static final int CONCENTRATOR_METERS = 4;
  public static final int RESETS = 5;
  public static final int ADVANCE_VIEW = 6;
  public static final int HORARY = 7;
  public static final int CONTROL_USER = 8;
  public static final int FIRMWARE_UPLOAD = 9;
  public static final int LOAD_PROFILE = 10;
  public static final int LOG = 11;
  JPanel currentPanel = null;
  public static Set set = null;
  public static Iterator it = null;
  private JMenu JMenuReadings;
  private JMenuBar jMenuBar1;
  private JMenu jMenuConcentrator;
  private JMenu jMenuConfiguration;
  private JMenu jMenuHelp;
  private JMenuItem jMenuItemAdminUsers;
  private JMenuItem jMenuItemAdvancedView;
  private JMenuItem jMenuItemCalibration;
  private JMenuItem jMenuItemExit;
  private JMenuItem jMenuItemFlags;
  private JMenuItem jMenuItemJavaHelp;
  private JMenuItem jMenuItemLoadProfile;
  private JMenuItem jMenuItemLog;
  private JMenuItem jMenuItemMeterConcentrator;
  private JMenuItem jMenuItemPorts;
  private JMenuItem jMenuItemReadingsConcentrator;
  private JMenuItem jMenuItemReadingsMeter;
  private JMenuItem jMenuItemReset;
  private JMenuItem jMenuItemSchedules;
  private JMenuItem jMenuItemUploadFirmware;
  private JMenuItem jMenuItemVersion;
  private JMenu jMenuLoadProfile;
  private JMenu jMenuLog;
  private JMenu jMenuLogOut;
  private JMenu jMenuPort;
  private JMenu jMenuUploadFirmware;
  private JMenu jMenuUsers;
  private ImageIcon iIcon;
  
  public JFrameMain()
  {
    initComponents();
    
    hasht.clear();
    hasht.put(new CurrentReadingsRecords(), Integer.valueOf(0));
    hasht.put(new Calibration(), Integer.valueOf(1));
    hasht.put(new Flags(), Integer.valueOf(2));
    hasht.put(new ConcentratorReadings(), Integer.valueOf(3));
    hasht.put(new ConcentratorMeters(), Integer.valueOf(4));
    hasht.put(new Resets(), Integer.valueOf(5));
    hasht.put(new ReadWriteRegister(), Integer.valueOf(6));
    hasht.put(new Horary(), Integer.valueOf(7));
    hasht.put(new ControlUser(), Integer.valueOf(8));
    hasht.put(new FirmwareUpload(), Integer.valueOf(9));
    hasht.put(new LoadProfile(), Integer.valueOf(10));
    hasht.put(new Log(), Integer.valueOf(11));
    for (JPanel key : hasht.keySet())
    {
      getContentPane().add(key);
      key.setBounds(20, 5, 950, 650);
      key.setVisible(false);
    }
    set = hasht.entrySet();
    it = set.iterator();
    while (it.hasNext())
    {
      Map.Entry entry = (Map.Entry)it.next();
      if (((Integer)entry.getValue()).intValue() == 0)
      {
        JPanel pane = (JPanel)entry.getKey();
        this.currentPanel = pane;
        pane.setVisible(true);
      }
    }
    getPermitsSelected();
    cargarAyuda();
  }
  
  public static LinkedHashMap getJPanels()
  {
    return hasht;
  }
  
  private void initComponents()
  {
    this.jMenuBar1 = new JMenuBar();
    this.JMenuReadings = new JMenu();
    this.jMenuItemReadingsMeter = new JMenuItem();
    this.jMenuItemCalibration = new JMenuItem();
    this.jMenuItemFlags = new JMenuItem();
    jMenuConfiguration = new JMenu();
    this.jMenuItemAdvancedView = new JMenuItem();
    this.jMenuItemReset = new JMenuItem();
    this.jMenuItemSchedules = new JMenuItem();
    jMenuUploadFirmware = new JMenu();
    this.jMenuItemUploadFirmware = new JMenuItem();
    jMenuLoadProfile = new JMenu();
    this.jMenuItemLoadProfile = new JMenuItem();
    jMenuConcentrator = new JMenu();
    this.jMenuItemReadingsConcentrator = new JMenuItem();
    this.jMenuItemMeterConcentrator = new JMenuItem();
    jMenuLog = new JMenu();
    this.jMenuItemLog = new JMenuItem();
    jMenuPort = new JMenu();
    this.jMenuItemPorts = new JMenuItem();
    jMenuUsers = new JMenu();
    jMenuItemAdminUsers = new JMenuItem();
    jMenuLogOut = new JMenu();
    this.jMenuItemExit = new JMenuItem();
    jMenuHelp = new JMenu();
    this.jMenuItemJavaHelp = new JMenuItem();
    this.jMenuItemVersion = new JMenuItem();
    this.iIcon = new ImageIcon("image/ImageEneri.png");
    
    setDefaultCloseOperation(3);
    
    this.JMenuReadings.setText("Menu");//Este no cambia por que es lo mismo
    this.JMenuReadings.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        JFrameMain.this.JMenuReadingsActionPerformed(evt);
      }
    });
    this.jMenuItemReadingsMeter.setAccelerator(KeyStroke.getKeyStroke(113, 0));
    this.jMenuItemReadingsMeter.setText("Meter Reads");
    //this.jMenuItemReadingsMeter.setText("Lecturas de Medidor");//Este es en español
    this.jMenuItemReadingsMeter.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        JFrameMain.this.jMenuItemReadingsMeterActionPerformed(evt);
      }
    });
    this.JMenuReadings.add(this.jMenuItemReadingsMeter);
    
    this.jMenuItemCalibration.setAccelerator(KeyStroke.getKeyStroke(114, 0));
    this.jMenuItemCalibration.setText("Calibration");
    //this.jMenuItemCalibration.setText("Calibracion");//Este es en español
    this.jMenuItemCalibration.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        JFrameMain.this.jMenuItemCalibrationActionPerformed(evt);
      }
    });
    this.JMenuReadings.add(this.jMenuItemCalibration);
    
    this.jMenuItemFlags.setAccelerator(KeyStroke.getKeyStroke(115, 0));
    this.jMenuItemFlags.setText("Flags");
    //this.jMenuItemFlags.setText("Banderas");//Este es en español
    this.jMenuItemFlags.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        JFrameMain.this.jMenuItemFlagsActionPerformed(evt);
      }
    });
    this.JMenuReadings.add(this.jMenuItemFlags);
    
    this.jMenuBar1.add(this.JMenuReadings);
    
    jMenuConfiguration.setText("Configuration");
    //this.jMenuConfiguration.setText("Configuracion");
    
    this.jMenuItemAdvancedView.setAccelerator(KeyStroke.getKeyStroke(117, 0));
    this.jMenuItemAdvancedView.setText("Advanced View");
    //this.jMenuItemAdvancedView.setText("Vista Avanzada");
    this.jMenuItemAdvancedView.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        JFrameMain.this.jMenuItemAdvancedViewActionPerformed(evt);
      }
    });
    jMenuConfiguration.add(this.jMenuItemAdvancedView);
    
    this.jMenuItemReset.setAccelerator(KeyStroke.getKeyStroke(116, 0));
    this.jMenuItemReset.setText("Restarts");
    //this.jMenuItemReset.setText("Reinicios");
    this.jMenuItemReset.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        JFrameMain.this.jMenuItemResetActionPerformed(evt);
      }
    });
    jMenuConfiguration.add(this.jMenuItemReset);
    
    this.jMenuItemSchedules.setAccelerator(KeyStroke.getKeyStroke(118, 0));
    this.jMenuItemSchedules.setText("Timetable");
    //this.jMenuItemSchedules.setText("Horarios");
    this.jMenuItemSchedules.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        JFrameMain.this.jMenuItemSchedulesActionPerformed(evt);
      }
    });
    jMenuConfiguration.add(this.jMenuItemSchedules);
    
    jMenuBar1.add(jMenuConfiguration);
    
    jMenuUploadFirmware.setText("Load Firm");
    //this.jMenuUploadFirmware.setText("Carga de Firm");
    
    this.jMenuItemUploadFirmware.setAccelerator(KeyStroke.getKeyStroke(119, 0));
    this.jMenuItemUploadFirmware.setText("Load Firmware");
    //this.jMenuItemUploadFirmware.setText("Cargar Firmware");
    this.jMenuItemUploadFirmware.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        JFrameMain.this.jMenuItemUploadFirmwareActionPerformed(evt);
      }
    });
    jMenuUploadFirmware.add(this.jMenuItemUploadFirmware);
    
    jMenuBar1.add(this.jMenuUploadFirmware);
    
    jMenuLoadProfile.setText("Load Profile");
    //this.jMenuLoadProfile.setText("Perfil de Carga");
    
    this.jMenuItemLoadProfile.setAccelerator(KeyStroke.getKeyStroke(120, 0));
    this.jMenuItemLoadProfile.setText("Load Profile");
    //this.jMenuItemLoadProfile.setText("Perfil de Carga");
    this.jMenuItemLoadProfile.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        JFrameMain.this.jMenuItemLoadProfileActionPerformed(evt);
      }
    });
    jMenuLoadProfile.add(this.jMenuItemLoadProfile);
    
    jMenuBar1.add(this.jMenuLoadProfile);
    
    jMenuConcentrator.setText("Concentrator");
    //this.jMenuConcentrator.setText("Concentrador");
    jMenuConcentrator.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        JFrameMain.this.jMenuConcentratorActionPerformed(evt);
      }
    });    
    this.jMenuItemReadingsConcentrator.setAccelerator(KeyStroke.getKeyStroke(67, 3));
    this.jMenuItemReadingsConcentrator.setText("Concentrator Reads");
    this.jMenuItemReadingsConcentrator.setText("Lecturas de Concentrador");
    this.jMenuItemReadingsConcentrator.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        JFrameMain.this.jMenuItemReadingsConcentratorActionPerformed(evt);
      }
    });
    
    jMenuConcentrator.add(jMenuItemReadingsConcentrator);    
    jMenuItemMeterConcentrator.setAccelerator(KeyStroke.getKeyStroke(88, 3));
    jMenuItemMeterConcentrator.setText("Concentrator Meters");
    //this.jMenuItemMeterConcentrator.setText("Medidores de Concentrador");
    jMenuItemMeterConcentrator.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        JFrameMain.this.jMenuItemMeterConcentratorActionPerformed(evt);
      }
    });
    jMenuConcentrator.add(jMenuItemMeterConcentrator);
    
    jMenuBar1.add(jMenuConcentrator);
    
    jMenuLog.setText("Show Log");
    //this.jMenuLog.setText("Mostrar Log");
    
    this.jMenuItemLog.setText("Actions Log Hex");
    //this.jMenuItemLog.setText("Log de Acciones HEX");
    this.jMenuItemLog.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        JFrameMain.this.jMenuItemLogActionPerformed(evt);
      }
    });
    jMenuLog.add(this.jMenuItemLog);
    
    jMenuBar1.add(this.jMenuLog);
    
    jMenuPort.setText("Port");
    //this.jMenuPort.setText("Puerto");
    
    this.jMenuItemPorts.setAccelerator(KeyStroke.getKeyStroke(82, 2));
    this.jMenuItemPorts.setText("Refresh Ports");
    //this.jMenuItemPorts.setText("Refrescar Puertos");
    this.jMenuItemPorts.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        JFrameMain.this.jMenuItemPortsActionPerformed(evt);
      }
    });
    jMenuPort.add(this.jMenuItemPorts);
    
    jMenuBar1.add(this.jMenuPort);
    
    jMenuUsers.setText("Users");
    //this.jMenuUsers.setText("Usuarios");
    
    jMenuItemAdminUsers.setAccelerator(KeyStroke.getKeyStroke(122, 0));
    jMenuItemAdminUsers.setText("Users Admin");
    //this.jMenuItemAdminUsers.setText("Admin de Usuarios");
    jMenuItemAdminUsers.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        jMenuItemAdminUsersActionPerformed(evt);
      }
    });
    jMenuUsers.add(jMenuItemAdminUsers);
    
    jMenuBar1.add(jMenuUsers);
    
    jMenuLogOut.setText("Cerrar Session");
    //this.jMenuLogOut.setText("Cerrar Sesion");
    
    this.jMenuItemExit.setAccelerator(KeyStroke.getKeyStroke(27, 0));
    this.jMenuItemExit.setText("Exit");
    //this.jMenuItemExit.setText("Salir");
    this.jMenuItemExit.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        JFrameMain.this.jMenuItemExitActionPerformed(evt);
      }
    });
    jMenuLogOut.add(this.jMenuItemExit);
    
    jMenuBar1.add(this.jMenuLogOut);
    
    jMenuHelp.setText("Help");
    //this.jMenuHelp.setText("Ayuda");
    
    this.jMenuItemJavaHelp.setAccelerator(KeyStroke.getKeyStroke(112, 0));
    this.jMenuItemJavaHelp.setText("Help");
    //this.jMenuItemJavaHelp.setText("Ayuda");
    this.jMenuItemJavaHelp.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        JFrameMain.this.jMenuItemJavaHelpActionPerformed(evt);
      }
    });
    jMenuHelp.add(this.jMenuItemJavaHelp);
    
    this.jMenuItemVersion.setText("About");
    //this.jMenuItemVersion.setText("Acerca de");
    this.jMenuItemVersion.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        JFrameMain.this.jMenuItemVersionActionPerformed(evt);
      }
    });
    jMenuHelp.add(this.jMenuItemVersion);
    
    jMenuBar1.add(this.jMenuHelp);
    
    setJMenuBar(this.jMenuBar1);
    
    GroupLayout layout = new GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 1024, 32767));
    
    layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 681, 32767));
    
    pack();
  }
  
  private void JMenuReadingsActionPerformed(ActionEvent evt) {}
  
  private void jMenuConcentratorActionPerformed(ActionEvent evt) {}  
  
  private void jMenuItemReadingsMeterActionPerformed(ActionEvent evt)
  {
    set = hasht.entrySet();
    it = set.iterator();
    while (it.hasNext())
    {
      Map.Entry entry = (Map.Entry)it.next();
      if (((Integer)entry.getValue()).intValue() == 0)
      {
        JPanel pane = (JPanel)entry.getKey();
        
        this.currentPanel.setVisible(false);
        this.currentPanel = pane;
        pane.setVisible(true);
      }
    }
  } 
  
  private void jMenuItemPortsActionPerformed(ActionEvent evt)
  {
    set = hasht.entrySet();
    it = set.iterator();
    while (it.hasNext())
    {
      Map.Entry entry = (Map.Entry)it.next();
      if (((Integer)entry.getValue()).intValue() == 0)
      {
        JPanel pane = (JPanel)entry.getKey();
        CurrentReadingsRecords current = (CurrentReadingsRecords)entry.getKey();
        current.refreshPorts();
      }
    }
  }
  
  private void jMenuItemCalibrationActionPerformed(ActionEvent evt)
  {
    set = hasht.entrySet();
    it = set.iterator();
    while (it.hasNext())
    {
      Map.Entry entry = (Map.Entry)it.next();
      if (((Integer)entry.getValue()).intValue() == 1)
      {
        JPanel pane = (JPanel)entry.getKey();
        
        this.currentPanel.setVisible(false);
        this.currentPanel = pane;
        pane.setVisible(true);
      }
    }
  }
  
  private void jMenuItemFlagsActionPerformed(ActionEvent evt)
  {
    set = hasht.entrySet();
    it = set.iterator();
    while (it.hasNext())
    {
      Map.Entry entry = (Map.Entry)it.next();
      if (((Integer)entry.getValue()).intValue() == 2)
      {
        JPanel pane = (JPanel)entry.getKey();
        
        this.currentPanel.setVisible(false);
        this.currentPanel = pane;
        pane.setVisible(true);
      }
    }
  }
  
  private void jMenuItemReadingsConcentratorActionPerformed(ActionEvent evt)
  {
    set = hasht.entrySet();
    it = set.iterator();
    while (it.hasNext())
    {
      Map.Entry entry = (Map.Entry)it.next();
      if (((Integer)entry.getValue()).intValue() == 3)
      {
        JPanel pane = (JPanel)entry.getKey();
        
        this.currentPanel.setVisible(false);
        this.currentPanel = pane;
        pane.setVisible(true);
      }
    }
  }
  
  private void jMenuItemMeterConcentratorActionPerformed(ActionEvent evt)
  {
    set = hasht.entrySet();
    it = set.iterator();
    while (it.hasNext())
    {
      Map.Entry entry = (Map.Entry)it.next();
      if (((Integer)entry.getValue()).intValue() == 4)
      {
        JPanel pane = (JPanel)entry.getKey();
        
        this.currentPanel.setVisible(false);
        this.currentPanel = pane;
        pane.setVisible(true);
      }
    }
  }
  
  private void jMenuItemResetActionPerformed(ActionEvent evt)
  {
    set = hasht.entrySet();
    it = set.iterator();
    while (it.hasNext())
    {
      Map.Entry entry = (Map.Entry)it.next();
      if (((Integer)entry.getValue()).intValue() == 5)
      {
        JPanel pane = (JPanel)entry.getKey();
        
        this.currentPanel.setVisible(false);
        this.currentPanel = pane;
        pane.setVisible(true);
      }
    }
  } 
  
  private void jMenuItemAdvancedViewActionPerformed(ActionEvent evt)
  {
    set = hasht.entrySet();
    it = set.iterator();
    while (it.hasNext())
    {
      Map.Entry entry = (Map.Entry)it.next();
      if (((Integer)entry.getValue()).intValue() == 6)
      {
        JPanel pane = (JPanel)entry.getKey();
        this.currentPanel.setVisible(false);
        this.currentPanel = pane;
        pane.setVisible(true);
      }
    }
  }
  
  private void jMenuItemSchedulesActionPerformed(ActionEvent evt)
  {
    set = hasht.entrySet();
    it = set.iterator();
    while (it.hasNext())
    {
      Map.Entry entry = (Map.Entry)it.next();
      if (((Integer)entry.getValue()).intValue() == 7)
      {
        JPanel pane = (JPanel)entry.getKey();
        
        this.currentPanel.setVisible(false);
        this.currentPanel = pane;
        pane.setVisible(true);
      }
    }
  }
  
  private void jMenuItemAdminUsersActionPerformed(ActionEvent evt)
  {
    set = hasht.entrySet();
    it = set.iterator();
    while (it.hasNext())
    {
      Map.Entry entry = (Map.Entry)it.next();
      if (((Integer)entry.getValue()).intValue() == 8)
      {
        JPanel pane = (JPanel)entry.getKey();
        
        currentPanel.setVisible(false);
        currentPanel = pane;
        pane.setVisible(true);
      }
    }
  }
  
  private void jMenuItemUploadFirmwareActionPerformed(ActionEvent evt)
  {
    set = hasht.entrySet();
    it = set.iterator();
    while (it.hasNext())
    {
      Map.Entry entry = (Map.Entry)it.next();
      if (((Integer)entry.getValue()).intValue() == 9)
      {
        JPanel pane = (JPanel)entry.getKey();
        
        this.currentPanel.setVisible(false);
        this.currentPanel = pane;
        pane.setVisible(true);
      }
    }
  }
  
  private void jMenuItemLoadProfileActionPerformed(ActionEvent evt)
  {
    set = hasht.entrySet();
    it = set.iterator();
    while (it.hasNext())
    {
      Map.Entry entry = (Map.Entry)it.next();
      if (((Integer)entry.getValue()).intValue() == 10)
      {
        JPanel pane = (JPanel)entry.getKey();
        
        this.currentPanel.setVisible(false);
        this.currentPanel = pane;
        pane.setVisible(true);
      }
    }
  }
  
  private void jMenuItemExitActionPerformed(ActionEvent evt)
  {
    this.us.closeAll();
    setVisible(false);
    PortComunication port = new PortComunication();
    Readings read = new Readings();
    if (port.getStatePort() == true)
    {
      port.closePort();
      read.bolreadings = false;
    }
    Login log = new Login();
    log.setVisible(true);
    log.setLocationRelativeTo(null);
  }
  
  private void jMenuItemJavaHelpActionPerformed(ActionEvent evt) 
  {
      //String manual = "c:/Users/Roberto/Documents/NetBeansProjects/Scorpio_MeterTool/src/main/resources/Manual/manual.pdf";
      String manual = "Manual/manual.pdf";
      
      try 
      {
          Runtime.getRuntime().exec("cmd /c start "+manual);
      } 
      catch (IOException ex) 
      {
          Logger.getLogger(JFrameMain.class.getName()).log(Level.SEVERE, null, ex);
      }
  }
  
  private void jMenuItemVersionActionPerformed(ActionEvent evt)
  {
    JOptionPane.showMessageDialog(null, "SCORPIO METER TOOL \nENERI \nwww.eneri.com.mx \nVersion 1.0.0\n24-08-2016","Information",JOptionPane.INFORMATION_MESSAGE, iIcon);
    //JOptionPane.showMessageDialog(null, "SCORPIO METER TOOL \nSMEI \nVersion 1.0.25\n09-09-2015");
  }
  
  private void jMenuItemLogActionPerformed(ActionEvent evt)
  {
    set = hasht.entrySet();
    it = set.iterator();
    while (it.hasNext())
    {
      Map.Entry entry = (Map.Entry)it.next();
      if (((Integer)entry.getValue()).intValue() == 11)
      {
        JPanel pane = (JPanel)entry.getKey();
        this.currentPanel.setVisible(false);
        this.currentPanel = pane;
        pane.setVisible(true);
      }
    }
  }
  
  private void cargarAyuda()
  {
    try
    {
      if (variableTotalHelp == 0)
      {
        File fichero = new File("javahelp/help_set.hs");
        
        URL hsURL = fichero.toURI().toURL();
        
        HelpSet helpset = new HelpSet(getClass().getClassLoader(), hsURL);
        HelpBroker hb = helpset.createHelpBroker();
        
        hb.enableHelpOnButton(this.jMenuItemJavaHelp, "main", helpset);
        
        hb.setSize(new Dimension(1370, 700));
      }
    }
    catch (Exception e) {}
  }
  
  public LinkedHashMap getComponentFramee()
  {
    LinkedHashMap<Component, String> listComponents = new LinkedHashMap();
    
    listComponents.put(this.jMenuItemCalibration, "ItemCalibration");
    listComponents.put(this.jMenuItemFlags, "ItemFlags");
    listComponents.put(this.jMenuConfiguration, "MenuConfiguration");
    listComponents.put(this.jMenuItemReset, "ItemReset");
    listComponents.put(this.jMenuItemAdvancedView, "ItemAdvancedView");
    listComponents.put(this.jMenuItemSchedules, "ItemSchedules");
    //listComponents.put(jMenuConcentrator, "MenuConcentrator");
    //listComponents.put(jMenuItemReadingsConcentrator, "ItemReadingsConcentrator");
    listComponents.put(jMenuItemMeterConcentrator, "ItemMeterConcentrator");
    listComponents.put(this.jMenuUploadFirmware, "MenuUploadFirmware");
    listComponents.put(this.jMenuItemUploadFirmware, "ItemUploadFirmware");
    listComponents.put(this.jMenuLoadProfile, "MenuLoadProfile");
    listComponents.put(this.jMenuItemLoadProfile, "ItemLoadProfile");
    listComponents.put(this.jMenuConcentrator, "MenuConcentrator");
    listComponents.put(this.jMenuItemReadingsConcentrator, "ItemReadingsConcentrator");
    listComponents.put(this.jMenuItemMeterConcentrator, "ItemMeterConcentrator");
    listComponents.put(this.jMenuLog, "MenuLog");
    listComponents.put(this.jMenuItemLog, "ItemLog");    
    listComponents.put(this.jMenuUsers, "MenuUsers");
    listComponents.put(jMenuItemAdminUsers, "ItemAdminUsers");
    listComponents.put(this.jMenuLogOut, "MenuLogOut");
    
    return listComponents;
  }
  
  public void getPermits()
  {
    Iterator it = null;
    String group = this.us.getGroup();
    
    Set set = getComponentFramee().entrySet();
    it = set.iterator();
    
    ArrayList listEnum = this.us.printTableEnum();
    while (it.hasNext())
    {
      Map.Entry entry = (Map.Entry)it.next();
      ((Component)entry.getKey()).setVisible(false);
    }
    it = set.iterator();
    Map.Entry entry;
    while (it.hasNext())
    {
      entry = (Map.Entry)it.next();
      for (Object comps : listEnum)
      {
        String[] spgetgr = comps.toString().split(",");
        String[] level = spgetgr[3].split("-");
        for (String level1 : level) {
          if ((group.equals(level1)) && 
            (entry.getValue().equals(spgetgr[1]))) {
            ((Component)entry.getKey()).setVisible(true);
          }
        }
      }
    }
  }
  
  public void getPermitsSelected()
  {
    Iterator itt = null;
    Set sett = getComponentFramee().entrySet();
    itt = sett.iterator();
    while (itt.hasNext())
    {
      Map.Entry entry = (Map.Entry)itt.next();
      String value = (String)entry.getValue();
      if (value.equals("MenuLoadProfile")) 
      {
        ((Component)entry.getKey()).setVisible(false);
      }
      if (value.equals("ItemLoadProfile")) 
      {
        ((Component)entry.getKey()).setVisible(false);
      }
      if (value.equals("MenuConcentrator")) 
      {
        ((Component)entry.getKey()).setVisible(false);
      }
      if (value.equals("ItemReadingsConcentrator")) 
      {
        ((Component)entry.getKey()).setVisible(false);
      }
      if (value.equals("ItemMeterConcentrator"))
      {
        ((Component)entry.getKey()).setVisible(false);
      }
      if (value.equals("MenuUsers")) 
      {
        ((Component)entry.getKey()).setVisible(false);
      }
      if (value.equals("ItemAdminUsers"))
      {
        ((Component)entry.getKey()).setVisible(false);
      }
      if (value.equals("MenuLogOut")) 
      {
        ((Component)entry.getKey()).setVisible(false);
      }
    }
  }
}
