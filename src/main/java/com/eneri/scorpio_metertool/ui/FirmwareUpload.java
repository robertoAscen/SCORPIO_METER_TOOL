/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eneri.scorpio_metertool.ui;

import com.eneri.scorpio_metertool.devices.Meter;
import com.eneri.scorpio_metertool.firmware.Firmware3PHProcessor;
import com.eneri.scorpio_metertool.hardwarelayer.FlagsPasswords;
import com.eneri.scorpio_metertool.hardwarelayer.ModbusUtil;
import com.eneri.scorpio_metertool.hardwarelayer.PortComunication;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.Timer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Roberto
 */
public class FirmwareUpload
  extends JPanel
{
  private static Logger log = LoggerFactory.getLogger(FirmwareUpload.class);
  public String pathh = "";
  File fileData = new File("");
  public boolean stopFirmware = false;
  public static boolean broadCast = false;
  
  public FirmwareUpload()
  {
    initComponents();
    this.jButtonStartUploadFirmware.setEnabled(false);
  }
  
  private void initComponents()
  {
    this.jButtonExamineFirmware = new JButton();
    this.jLabelFirmwareName = new JLabel();
    this.jButtonStartUploadFirmware = new JButton();
    this.jCheckBoxBroadcastFirmware = new JCheckBox();
    this.jButtonDowngradeFirmware = new JButton();
    this.jCheckBoxBroadcastDowngrade = new JCheckBox();
    this.jProgressBarFirmware = new JProgressBar();
    this.jLabelProgressBar = new JLabel();
    this.jLabelLog = new JLabel();
    this.jLabelNamePanel = new JLabel();
    this.jScrollPane1 = new JScrollPane();
    this.jTextAreaLog = new JTextArea();
    this.jButtonClear = new JButton();
    
    setPreferredSize(new Dimension(950, 600));
    
    //this.jButtonExamineFirmware.setText("EXAMINAR FIRMWARE ...(HEX)");
    this.jButtonExamineFirmware.setText("BROWSE FIRMWARE ...(HEX)");
    this.jButtonExamineFirmware.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        FirmwareUpload.this.jButtonExamineFirmwareActionPerformed(evt);
      }
    });
    this.jLabelFirmwareName.setText("...");
    
    //this.jButtonStartUploadFirmware.setText("INICIAR LA CARGA DE FIRMWARE");
    this.jButtonStartUploadFirmware.setText("START LOAD FIRMWARE");
    this.jButtonStartUploadFirmware.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        FirmwareUpload.this.jButtonStartUploadFirmwareActionPerformed(evt);
      }
    });
    //this.jCheckBoxBroadcastFirmware.setText("Varios Medidores");
    this.jCheckBoxBroadcastFirmware.setText("Various Meters");
    
    this.jButtonDowngradeFirmware.setText("DOWNGRADE FIRMWARE");
    this.jButtonDowngradeFirmware.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        FirmwareUpload.this.jButtonDowngradeFirmwareActionPerformed(evt);
      }
    });
    //this.jCheckBoxBroadcastDowngrade.setText("Varios Medidores");
    this.jCheckBoxBroadcastDowngrade.setText("Various Meters");
    
    //this.jLabelProgressBar.setText("Estado de la carga de Firmware");
    this.jLabelProgressBar.setText("Charge Status Firmware");
    
    this.jLabelLog.setText("Log");
    
    this.jLabelNamePanel.setFont(new Font("Tahoma", 1, 18));
    //this.jLabelNamePanel.setText("CARGA DE FIRMWARE");
    this.jLabelNamePanel.setText("LOAD FIRMWARE");
    
    this.jTextAreaLog.setColumns(20);
    this.jTextAreaLog.setLineWrap(true);
    this.jTextAreaLog.setRows(5);
    this.jScrollPane1.setViewportView(this.jTextAreaLog);
    
    //this.jButtonClear.setText("LIMPIAR LOG");
    this.jButtonClear.setText("CLEAR LOG");
    this.jButtonClear.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        FirmwareUpload.this.jButtonClearActionPerformed(evt);
      }
    });
    GroupLayout layout = new GroupLayout(this);
    setLayout(layout);
    layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addContainerGap(-1, 32767).addComponent(this.jLabelNamePanel).addGap(373, 373, 373)).addGroup(layout.createSequentialGroup().addGap(31, 31, 31).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.jButtonClear).addGap(0, 0, 32767)).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jScrollPane1).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabelLog).addComponent(this.jLabelProgressBar)).addGap(0, 719, 32767)).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(this.jButtonExamineFirmware, -1, -1, 32767).addComponent(this.jButtonStartUploadFirmware, -1, -1, 32767)).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addGap(18, 18, 32767).addComponent(this.jCheckBoxBroadcastFirmware).addGap(246, 246, 246).addComponent(this.jButtonDowngradeFirmware).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jCheckBoxBroadcastDowngrade)).addGroup(layout.createSequentialGroup().addGap(12, 12, 12).addComponent(this.jLabelFirmwareName)))).addComponent(this.jProgressBarFirmware, -1, -1, 32767)).addGap(49, 49, 49)))));
    
    layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(8, 8, 8).addComponent(this.jLabelNamePanel).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jButtonExamineFirmware).addComponent(this.jLabelFirmwareName)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jButtonStartUploadFirmware).addComponent(this.jCheckBoxBroadcastFirmware).addComponent(this.jCheckBoxBroadcastDowngrade).addComponent(this.jButtonDowngradeFirmware)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jLabelProgressBar).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jProgressBarFirmware, -2, 33, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jLabelLog).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jScrollPane1, -2, 390, -2).addGap(18, 18, 18).addComponent(this.jButtonClear).addContainerGap(19, 32767)));
  }
  
  private void jButtonExamineFirmwareActionPerformed(ActionEvent evt)
  {
    JFileChooser fileChooser = new JFileChooser();
    int returnOption = fileChooser.showOpenDialog(this);
    if (returnOption == 0)
    {
      this.fileData = fileChooser.getSelectedFile();
      this.jLabelFirmwareName.setText(this.fileData.getName());
    }
    else
    {
      this.fileData = null;
      this.jLabelFirmwareName.setText("...");
    }
    Path path = Paths.get(this.fileData + "/", new String[0]);
    this.pathh = path.toString();
    
    int memoryCheckSum = -1;
    try
    {
      memoryCheckSum = Firmware3PHProcessor.getInstance().processHEXFile(this.fileData);
    }
    catch (IOException ex)
    {
      log.error("Unexpected error at swingActionOpenFile()", ex);
    }
    if (memoryCheckSum != -1)
    {
      this.jTextAreaLog.append("HEX file processed successfully. MemoryMap CheckSum: 0x" + Integer.toHexString(memoryCheckSum) + "\n");
      this.jButtonStartUploadFirmware.setEnabled(true);
    }
    else
    {
      //JOptionPane.showMessageDialog(null, "Error Revise el archivo");
      JOptionPane.showMessageDialog(null, "Error Check the file","Error",JOptionPane.ERROR_MESSAGE);
      this.jButtonStartUploadFirmware.setEnabled(false);
      return;
    }
  }
  
  private void jButtonDowngradeFirmwareActionPerformed(ActionEvent evt)
  {
    ModbusUtil modb = new ModbusUtil();
    PortComunication port = new PortComunication();
    FlagsPasswords flagPassword = FlagsPasswords.METER_FORCE_RESET;
    String serialNumber = "";
    boolean continueReboot = true;
    if (this.jCheckBoxBroadcastDowngrade.isSelected())
    {
      serialNumber = "0000000000000000";
      broadCast = true;
    }
    else
    {
      if (((Meter.getSerialNumberCompl() == null ? 1 : 0) | (!port.getStatePort() ? 1 : 0)) != 0)
      {
        //JOptionPane.showMessageDialog(null, "Error Revise el numero de serie, Puerto o conexion");
        JOptionPane.showMessageDialog(null, "Error Check serial number, Port or connection","Error",JOptionPane.ERROR_MESSAGE);
        return;
      }
      serialNumber = Meter.getSerialNumberCompl();
      broadCast = false;
    }
    boolean estado = modb.writeFlagsPassw(flagPassword, serialNumber);
    if (estado == true)
    {
      continueReboot = true;
    }
    else
    {
      //JOptionPane.showMessageDialog(null, "Error Revise el numero de serie, Puerto o conexion");
      JOptionPane.showMessageDialog(null, "Error Check serial number, Port or connection","Error",JOptionPane.ERROR_MESSAGE);
      continueReboot = false;
    }
    try
    {
      Thread.sleep(2000L);
    }
    catch (Exception e) {}
    if (continueReboot == true)
    {
      FlagsPasswords flagPasswordDowng = FlagsPasswords.METER_FORCE_BOOTLOADER;
      boolean estado2 = modb.writeFlagsPassw(flagPasswordDowng, serialNumber);
      if (estado2 == true) {
        //this.jTextAreaLog.append("Downgrade correctamente\n");
        this.jTextAreaLog.append("Downgrade succesfully\n");
      } else {
        //this.jTextAreaLog.append("Error Revise el numero de serie, Puerto o conexion\n");
        this.jTextAreaLog.append("Error Check serial number, Port or connection\n");
      }
    }
  }
  
  ActionListener taskFWUpload = new ActionListener()
  {
    public void actionPerformed(ActionEvent e)
    {
      FirmwareUpload.this.jProgressBarFirmware.setValue(Firmware3PHProcessor.getInstance().getTxCount());
    }
  };
  
  private void swingActionSTARTFW(File fileData)
  {
    if (Meter.getSerialNumberCompl() == null)
    {
      //JOptionPane.showMessageDialog(null, "Error Revise el numero de serie, Puerto o conexion");
      JOptionPane.showMessageDialog(null, "Error Check serial number, Port or connection","Error",JOptionPane.ERROR_MESSAGE);
      return;
    }
    try
    {
      if (Firmware3PHProcessor.getInstance().processHEXFile(fileData) != -1)
      {
        this.jProgressBarFirmware.setMaximum(Firmware3PHProcessor.getInstance().getTxTotal());
        //this.jTextAreaLog.append("Un total de " + Firmware3PHProcessor.getInstance().getTxTotal() + " frames will be transmitted. \n");
        //this.jTextAreaLog.append("Proceso inicio a las " + new Date().toString() + "\n");
        this.jTextAreaLog.append("A total of " + Firmware3PHProcessor.getInstance().getTxTotal() + " frames will be transmitted. \n");
        this.jTextAreaLog.append("Start the process " + new Date().toString() + "\n");
        this.timeFWUpload.start();
        this.jButtonStartUploadFirmware.setEnabled(false);
        
        new Thread()
        {
          public void run()
          {
            Firmware3PHProcessor.getInstance().startUpload(200, FirmwareUpload.this.jCheckBoxBroadcastFirmware.isSelected());
            FirmwareUpload.this.timeFWUpload.stop();
            //FirmwareUpload.this.jTextAreaLog.append("Proceso termino a las " + new Date().toString() + "\n");
            FirmwareUpload.this.jTextAreaLog.append("Process finished " + new Date().toString() + "\n");
            FirmwareUpload.this.jButtonStartUploadFirmware.setEnabled(true);
          }
        }.start();
      }
    }
    catch (IOException ex)
    {
      ex = ex;
      this.timeFWUpload.stop();this.jProgressBarFirmware.setValue(0);log.error("Unexpected I/O error at swingActionSTARTFW().", ex);this.jTextAreaLog.append("An unexpected error has ocurred, please verify the system log. \n");this.jButtonStartUploadFirmware.setEnabled(true);
    }
    finally {}
  }
  
  private void jButtonStartUploadFirmwareActionPerformed(ActionEvent evt)
  {
    EventQueue.invokeLater(new Runnable()
    {
      public void run()
      {
        if (FirmwareUpload.this.jCheckBoxBroadcastFirmware.isSelected()) {
          FirmwareUpload.broadCast = true;
        } else {
          FirmwareUpload.broadCast = false;
        }
        PortComunication port = new PortComunication();
        if (!port.getStatePort())
        {
          //JOptionPane.showMessageDialog(null, "Error Revise el numero de serie, Puerto o conexion");
          JOptionPane.showMessageDialog(null, "Error Check serial number, Port or connection","Error",JOptionPane.ERROR_MESSAGE);
          return;
        }
        FirmwareUpload.this.swingActionSTARTFW(FirmwareUpload.this.fileData);
      }
    });
  }
  
  private void jButtonClearActionPerformed(ActionEvent evt)
  {
    this.jTextAreaLog.setText("");
  }
  
  private Timer timeFWUpload = new Timer(100, this.taskFWUpload);
  private JButton jButtonClear;
  private JButton jButtonDowngradeFirmware;
  private JButton jButtonExamineFirmware;
  private JButton jButtonStartUploadFirmware;
  private JCheckBox jCheckBoxBroadcastDowngrade;
  private JCheckBox jCheckBoxBroadcastFirmware;
  private JLabel jLabelFirmwareName;
  private JLabel jLabelLog;
  private JLabel jLabelNamePanel;
  private JLabel jLabelProgressBar;
  private JProgressBar jProgressBarFirmware;
  private JScrollPane jScrollPane1;
  private JTextArea jTextAreaLog;
}
