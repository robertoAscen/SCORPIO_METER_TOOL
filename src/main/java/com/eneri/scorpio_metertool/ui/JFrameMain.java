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
  public JMenu jMenuHelp;
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
    //cargarAyuda();
  }
  
  public static LinkedHashMap getJPanels()
  {
    return hasht;
  }
  
  private void initComponents()
  {
    //Objetos de esta clase
      
    jMenuBar1 = new JMenuBar();
    JMenuReadings = new JMenu();
    jMenuItemReadingsMeter = new JMenuItem();
    jMenuItemCalibration = new JMenuItem();
    jMenuItemFlags = new JMenuItem();
    jMenuConfiguration = new JMenu();
    jMenuItemAdvancedView = new JMenuItem();
    jMenuItemReset = new JMenuItem();
    jMenuItemSchedules = new JMenuItem();
    jMenuUploadFirmware = new JMenu();
    jMenuItemUploadFirmware = new JMenuItem();
    jMenuLoadProfile = new JMenu();
    jMenuItemLoadProfile = new JMenuItem();
    jMenuConcentrator = new JMenu();
    jMenuItemReadingsConcentrator = new JMenuItem();
    jMenuItemMeterConcentrator = new JMenuItem();
    jMenuLog = new JMenu();
    jMenuItemLog = new JMenuItem();
    jMenuPort = new JMenu();
    jMenuItemPorts = new JMenuItem();
    jMenuUsers = new JMenu();
    jMenuItemAdminUsers = new JMenuItem();
    jMenuLogOut = new JMenu();
    jMenuItemExit = new JMenuItem();
    jMenuHelp = new JMenu();
    jMenuItemJavaHelp = new JMenuItem();
    jMenuItemVersion = new JMenuItem();
    iIcon = new ImageIcon("image/ImageEneri.png");
    
    setDefaultCloseOperation(3);
    
    JMenuReadings.setText("Menú");
    JMenuReadings.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        JFrameMain.this.JMenuReadingsActionPerformed(evt);
      }
    });
    jMenuItemReadingsMeter.setAccelerator(KeyStroke.getKeyStroke(113, 0));
    //jMenuItemReadingsMeter.setText("Meter Reads");//Para uso en ingles
    jMenuItemReadingsMeter.setText("Lecturas de Medidor");//Este es en español
    jMenuItemReadingsMeter.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        JFrameMain.this.jMenuItemReadingsMeterActionPerformed(evt);
      }
    });
    JMenuReadings.add(this.jMenuItemReadingsMeter);
    
    jMenuItemCalibration.setAccelerator(KeyStroke.getKeyStroke(114, 0));
    //this.jMenuItemCalibration.setText("Calibration");//Para uso en ingles
    jMenuItemCalibration.setText("Calibración");//Este es en español
    jMenuItemCalibration.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        JFrameMain.this.jMenuItemCalibrationActionPerformed(evt);
      }
    });
    JMenuReadings.add(jMenuItemCalibration);
    
    jMenuItemFlags.setAccelerator(KeyStroke.getKeyStroke(115, 0));
    //jMenuItemFlags.setText("Flags");//Para uso en ingles
    jMenuItemFlags.setText("Banderas");//Este es en español
    jMenuItemFlags.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        JFrameMain.this.jMenuItemFlagsActionPerformed(evt);
      }
    });
    JMenuReadings.add(jMenuItemFlags);
    
    jMenuBar1.add(this.JMenuReadings);
    
    //jMenuConfiguration.setText("Configuration");
    jMenuConfiguration.setText("Configuración");
    
    jMenuItemAdvancedView.setAccelerator(KeyStroke.getKeyStroke(117, 0));
    //jMenuItemAdvancedView.setText("Advanced View");//Para uso en ingles
    jMenuItemAdvancedView.setText("Vista Avanzada");//Para uso en español
    jMenuItemAdvancedView.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        JFrameMain.this.jMenuItemAdvancedViewActionPerformed(evt);
      }
    });
    jMenuConfiguration.add(jMenuItemAdvancedView);
    
    jMenuItemReset.setAccelerator(KeyStroke.getKeyStroke(116, 0));
    //jMenuItemReset.setText("Restarts");//Para uso en ingles
    jMenuItemReset.setText("Reinicios");//Para uso en español
    jMenuItemReset.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        JFrameMain.this.jMenuItemResetActionPerformed(evt);
      }
    });
    jMenuConfiguration.add(jMenuItemReset);
    
    jMenuItemSchedules.setAccelerator(KeyStroke.getKeyStroke(118, 0));
    //jMenuItemSchedules.setText("Timetable");//Uso en ingles
    jMenuItemSchedules.setText("Horarios");//Uso en español
    jMenuItemSchedules.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        JFrameMain.this.jMenuItemSchedulesActionPerformed(evt);
      }
    });
    jMenuConfiguration.add(jMenuItemSchedules);
    
    jMenuBar1.add(jMenuConfiguration);
    
    //jMenuUploadFirmware.setText("Load Firm");//Uso en ingles
    jMenuUploadFirmware.setText("Carga de FW");//Uso en español
    
    jMenuItemUploadFirmware.setAccelerator(KeyStroke.getKeyStroke(119, 0));
    //jMenuItemUploadFirmware.setText("Load Firmware");//Uso en ingles
    jMenuItemUploadFirmware.setText("Cargar Firmware");//Uso en español
    jMenuItemUploadFirmware.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        JFrameMain.this.jMenuItemUploadFirmwareActionPerformed(evt);
      }
    });
    jMenuUploadFirmware.add(jMenuItemUploadFirmware);
    
    jMenuBar1.add(jMenuUploadFirmware);
    
    //jMenuLoadProfile.setText("Load Profile");//Uso en ingles
    jMenuLoadProfile.setText("Perfil de Carga");//Uso en español
    
    jMenuItemLoadProfile.setAccelerator(KeyStroke.getKeyStroke(120, 0));
    //jMenuItemLoadProfile.setText("Load Profile");//Uso en ingles
    jMenuItemLoadProfile.setText("Perfil de Carga");//Uso en español
    jMenuItemLoadProfile.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        JFrameMain.this.jMenuItemLoadProfileActionPerformed(evt);
      }
    });
    jMenuLoadProfile.add(jMenuItemLoadProfile);
    
    jMenuBar1.add(jMenuLoadProfile);
    
    //jMenuConcentrator.setText("Concentrator");//Uso en ingles
    jMenuConcentrator.setText("Concentrador");//Uso en español
    jMenuConcentrator.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        JFrameMain.this.jMenuConcentratorActionPerformed(evt);
      }
    });    
    jMenuItemReadingsConcentrator.setAccelerator(KeyStroke.getKeyStroke(67, 3));
    //jMenuItemReadingsConcentrator.setText("Concentrator Reads");//Uso en ingles
    jMenuItemReadingsConcentrator.setText("Lecturas de Concentrador");//Uso en español
    jMenuItemReadingsConcentrator.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        JFrameMain.this.jMenuItemReadingsConcentratorActionPerformed(evt);
      }
    });
    
    jMenuConcentrator.add(jMenuItemReadingsConcentrator);    
    jMenuItemMeterConcentrator.setAccelerator(KeyStroke.getKeyStroke(88, 3));
    //jMenuItemMeterConcentrator.setText("Concentrator Meters");//Uso en ingles
    jMenuItemMeterConcentrator.setText("Medidores de Concentrador");//Uso en español
    jMenuItemMeterConcentrator.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        JFrameMain.this.jMenuItemMeterConcentratorActionPerformed(evt);
      }
    });
    jMenuConcentrator.add(jMenuItemMeterConcentrator);
    
    jMenuBar1.add(jMenuConcentrator);
    
    //jMenuLog.setText("Show Log");//Uso en ingles
    jMenuLog.setText("Mostrar Log");//Uso en español
    
    //jMenuItemLog.setText("Actions Log Hex");//Uso en ingles
    jMenuItemLog.setText("Log de Acciones HEX");//Uso en español
    jMenuItemLog.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        JFrameMain.this.jMenuItemLogActionPerformed(evt);
      }
    });
    jMenuLog.add(jMenuItemLog);
    
    jMenuBar1.add(jMenuLog);
    
    //jMenuPort.setText("Port");//Uso en ingles
    jMenuPort.setText("Puerto");//Uso en español
    
    jMenuItemPorts.setAccelerator(KeyStroke.getKeyStroke(82, 2));
    //jMenuItemPorts.setText("Refresh Ports");//Uso en ingles
    jMenuItemPorts.setText("Refrescar Puertos");//Uso en español
    jMenuItemPorts.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        JFrameMain.this.jMenuItemPortsActionPerformed(evt);
      }
    });
    jMenuPort.add(jMenuItemPorts);
    
    jMenuBar1.add(jMenuPort);
    
    //jMenuUsers.setText("Users");//Uso en ingles
    jMenuUsers.setText("Usuarios");//Uso en español
    
    jMenuItemAdminUsers.setAccelerator(KeyStroke.getKeyStroke(122, 0));
    //jMenuItemAdminUsers.setText("Users Admin");//Uso en Ingles
    jMenuItemAdminUsers.setText("Admin de Usuarios");//Uso en español
    jMenuItemAdminUsers.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        jMenuItemAdminUsersActionPerformed(evt);
      }
    });
    jMenuUsers.add(jMenuItemAdminUsers);
    
    jMenuBar1.add(jMenuUsers);
    
    //jMenuLogOut.setText("Cerrar Session");//Uso en ingles
    jMenuLogOut.setText("Cerrar Sesion");//Uso en español
    
    jMenuItemExit.setAccelerator(KeyStroke.getKeyStroke(27, 0));
    //jMenuItemExit.setText("Exit");//Uso en ingles
    jMenuItemExit.setText("Salir");//Uso en español
    jMenuItemExit.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        JFrameMain.this.jMenuItemExitActionPerformed(evt);
      }
    });
    jMenuLogOut.add(jMenuItemExit);
    
    jMenuBar1.add(jMenuLogOut);
    
    //jMenuHelp.setText("Help");//Uso en ingles
    jMenuHelp.setText("Ayuda");//Uso en español
    
    jMenuItemJavaHelp.setAccelerator(KeyStroke.getKeyStroke(112, 0));
    //jMenuItemJavaHelp.setText("Help");//Uso en ingles
    jMenuItemJavaHelp.setText("Ayuda");//Uso en español
    jMenuItemJavaHelp.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        JFrameMain.this.jMenuItemJavaHelpActionPerformed(evt);
      }
    });
    
    //jMenuHelp.add(jMenuItemJavaHelp);
    
    //jMenuItemVersion.setText("About");//Uso en ingles
    jMenuItemVersion.setText("Acerca de");//Uso en español
    jMenuItemVersion.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        JFrameMain.this.jMenuItemVersionActionPerformed(evt);
      }
    });
    jMenuHelp.add(jMenuItemVersion);
    
    jMenuBar1.add(jMenuHelp);
    
    setJMenuBar(jMenuBar1);
    
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
        
        currentPanel.setVisible(false);
        currentPanel = pane;
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
        
        currentPanel.setVisible(false);
        currentPanel = pane;
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
        
        currentPanel.setVisible(false);
        currentPanel = pane;
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
        
        currentPanel.setVisible(false);
        currentPanel = pane;
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
        
        currentPanel.setVisible(false);
        currentPanel = pane;
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
        currentPanel.setVisible(false);
        currentPanel = pane;
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
        
        currentPanel.setVisible(false);
        currentPanel = pane;
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
        
        currentPanel.setVisible(false);
        currentPanel = pane;
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
        
        currentPanel.setVisible(false);
        currentPanel = pane;
        pane.setVisible(true);
      }
    }
  }
  
  private void jMenuItemExitActionPerformed(ActionEvent evt)
  {
    us.closeAll();
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
    JOptionPane.showMessageDialog(null, "SCORPIO METER TOOL \nENERI \nwww.eneri.com.mx \nVersion 2.0.1\n17-11-2017","Información",JOptionPane.INFORMATION_MESSAGE, iIcon);
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
        currentPanel.setVisible(false);
        currentPanel = pane;
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
    
    listComponents.put(jMenuItemCalibration, "ItemCalibration");
    listComponents.put(jMenuItemFlags, "ItemFlags");
    listComponents.put(jMenuConfiguration, "MenuConfiguration");
    listComponents.put(jMenuItemReset, "ItemReset");
    listComponents.put(jMenuItemAdvancedView, "ItemAdvancedView");
    listComponents.put(jMenuItemSchedules, "ItemSchedules");
    listComponents.put(jMenuConcentrator, "MenuConcentrator");
    listComponents.put(jMenuItemReadingsConcentrator, "ItemReadingsConcentrator");
    listComponents.put(jMenuItemMeterConcentrator, "ItemMeterConcentrator");
    listComponents.put(jMenuUploadFirmware, "MenuUploadFirmware");
    listComponents.put(jMenuItemUploadFirmware, "ItemUploadFirmware");
    listComponents.put(jMenuLoadProfile, "MenuLoadProfile");
    listComponents.put(jMenuItemLoadProfile, "ItemLoadProfile");
    listComponents.put(jMenuConcentrator, "MenuConcentrator");
    listComponents.put(jMenuItemReadingsConcentrator, "ItemReadingsConcentrator");
    listComponents.put(jMenuItemMeterConcentrator, "ItemMeterConcentrator");
    listComponents.put(jMenuLog, "MenuLog");
    listComponents.put(jMenuItemLog, "ItemLog");    
    listComponents.put(jMenuUsers, "MenuUsers");
    listComponents.put(jMenuItemAdminUsers, "ItemAdminUsers");
    listComponents.put(jMenuLogOut, "MenuLogOut");
    
    return listComponents;
  }
  
  public void getPermits()
  {
    Iterator it = null;
    String group = us.getGroup();
    
    Set set = getComponentFramee().entrySet();
    it = set.iterator();
    
    ArrayList listEnum = us.printTableEnum();
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
  
  //Función para mostrar las todas las opciones disponibles para esta aplicación
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
