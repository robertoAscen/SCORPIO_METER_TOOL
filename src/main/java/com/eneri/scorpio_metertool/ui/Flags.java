/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eneri.scorpio_metertool.ui;

import com.eneri.scorpio_metertool.bus.Conversions;
import com.eneri.scorpio_metertool.devices.Meter;
import com.eneri.scorpio_metertool.devices.Register;
import com.eneri.scorpio_metertool.devices.rwRegisters;
import com.eneri.scorpio_metertool.hardwarelayer.PortComunication;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Roberto
 */
public class Flags
  extends JPanel
{
  public LinkedHashMap<String, Integer> contenedor = new LinkedHashMap();
  public static DefaultTableModel defaultable;
  String[] columnas;
  Object[] data;
  JScrollPane scrollpane;
  public static Container contentPane;
  public static StringBuilder builderstring;
  private JButton jButtonReadFlags;
  private JLabel jLabelNamePanel;
  private JScrollPane jScrollPane1;
  private JTable jTableFlags;
  
  public Flags()
  {
    initComponents();
  }
  
  private void initComponents()
  {
    jLabelNamePanel = new JLabel();
    jScrollPane1 = new JScrollPane();
    jTableFlags = new JTable();
    jButtonReadFlags = new JButton();
    
    jLabelNamePanel.setFont(new Font("Tahoma", 1, 18));
    jLabelNamePanel.setText("BANDERAS");
    //jLabelNamePanel.setText("FLAGS");
    
    jTableFlags.setModel(new DefaultTableModel(new Object[0][], new String[0]));
    
    jScrollPane1.setViewportView(this.jTableFlags);
    
    jButtonReadFlags.setText("LEER");
    //jButtonReadFlags.setText("READ");
    jButtonReadFlags.setToolTipText("Oprime el botón para leer el estado de las bandera en el medidor");
    jButtonReadFlags.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        Flags.this.jButtonReadFlagsActionPerformed(evt);
      }
    });
    GroupLayout layout = new GroupLayout(this);
    setLayout(layout);
    layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(29, 29, 29).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(403, 403, 403).addComponent(this.jLabelNamePanel)).addComponent(this.jScrollPane1, -2, 893, -2).addComponent(this.jButtonReadFlags, -2, 110, -2)).addContainerGap(28, 32767)));
    
    layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(16, 16, 16).addComponent(this.jLabelNamePanel).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jButtonReadFlags).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jScrollPane1, -1, 531, 32767).addContainerGap()));
  }
  
  private void jButtonReadFlagsActionPerformed(ActionEvent evt)
  {
    createFrame();
  }
  
  public void createFrame()
  {
    JFrameMain f = (JFrameMain)SwingUtilities.getAncestorOfClass(JFrameMain.class, this);
    contentPane = f.getContentPane();
    scrollpane = new JScrollPane();
    defaultable = new DefaultTableModel((Object[][])null, getColumnas());
    
    PortComunication port = new PortComunication();
    //if (((!port.getStatePort() ? 1 : 0) | (Meter.serialNumber == null ? 1 : 0) /*| Meter.serialNumber.equals("0000000000000000")*/) != 0)
    if((port.getStatePort() == false) | (Meter.serialNumber == null) | Meter.serialNumber.equals("0000000000000000"))//Solución al error de la línea de arriba
    {
        JOptionPane.showMessageDialog(null, "Error, Revise el número de serie, Puerto o conexión", "Error", JOptionPane.ERROR_MESSAGE);
        //JOptionPane.showMessageDialog(null, "Error Check serial number, Port or connection","Error",JOptionPane.ERROR_MESSAGE);
    }
    else
    {
        String serialNumber = Meter.getSerialNumberCompl();
        Register regStart = Register.BANDERAS_DEL_SISTEMA;
        int countWords = 2;
        byte actionRW = 3;
        byte[] response = rwRegisters.readRegister(PortComunication.DELAY300, serialNumber, regStart, countWords, actionRW);
        if (response != null) {
            settingValueEnergy(response);
        }
        this.jTableFlags.setModel(defaultable);
        this.jTableFlags.repaint();
        this.scrollpane.repaint();
        setVisible(true);
    }
  }
  
  public String[] getColumnas()
  {
    String[] columna = { "BANDERA", "NOMBRE", "ESTADO" };
    //String[] columna = { "FLAGS", "NAME", "STATUS" };
    return columna;
  }
  
  public void settingValueEnergy(byte[] frame)
  {
    Conversions conv = new Conversions();
    String bitFlag = "";
    int values = Conversions.byteArrayToInt(frame);
    String activ = "";
    
    contenedor.put("METER_RELAY_STATE", Integer.valueOf(0));
    contenedor.put("METER_IR_MODE", Integer.valueOf(1));
    contenedor.put("METER_3PH_MODE", Integer.valueOf(2));
    contenedor.put("METER_HM_MODE", Integer.valueOf(3));
    contenedor.put("METER_VOLTAGE_MODE", Integer.valueOf(4));
    contenedor.put("BOOT_BACKDOOR_ENABLE", Integer.valueOf(5));
    contenedor.put("BOOT_UPGRADE_PROCESS_ENABLE", Integer.valueOf(6));
    contenedor.put("METER_ACC_RESET", Integer.valueOf(7));
    contenedor.put("METER_FORCE_RESET", Integer.valueOf(8));
    contenedor.put("METER_DEMAND_RESET", Integer.valueOf(9));
    contenedor.put("METER_STPMC_RESET", Integer.valueOf(10));
    contenedor.put("BOOT_FORCE_BOOTLOADER", Integer.valueOf(11));
    contenedor.put("METER_DAYLIGHT_SAVING_TIME_ACTIVE", Integer.valueOf(12));
    contenedor.put("METER_DAYLIGHT_SAVING_TIME_ENABLE", Integer.valueOf(13));
    contenedor.put("METER_UART_PARITY_ENABLE", Integer.valueOf(14));
    contenedor.put("METER_UART_PARITY_SELECT", Integer.valueOf(15));
    contenedor.put("ROLLING_DEMAND_INTERVAL", Integer.valueOf(16));
    contenedor.put("METER_LOW_LEVEL_BACKUP_BATTERY", Integer.valueOf(17));
    contenedor.put("METER_VOLTAGE_RECOVERY_INPUT", Integer.valueOf(18));
    contenedor.put("METER_ZERO_VOLTAGE_INPUT", Integer.valueOf(19));
    contenedor.put("METER_FRAM_ERROR", Integer.valueOf(20));
    contenedor.put("METER_STPMC_READ_ERROR", Integer.valueOf(21));
    contenedor.put("BOOT_FLASH_ERROR", Integer.valueOf(22));
    contenedor.put("METER_RTC_DESYNC_RISK", Integer.valueOf(23));
    contenedor.put("METER_NO_LOAD_CONNECTED", Integer.valueOf(24));
    contenedor.put("METER_RUNNING_APP_CODE", Integer.valueOf(25));
    contenedor.put("METER_VALID_IIC_DATABASE", Integer.valueOf(26));
    contenedor.put("METER_PHASE_SEQUENCE", Integer.valueOf(27));
    contenedor.put("METER_REVERSE_CURRENT", Integer.valueOf(28));
    contenedor.put("METER_LOW_RANGE", Integer.valueOf(29));
    contenedor.put("METER_HIGH_RANGE", Integer.valueOf(30));
    contenedor.put("METER_REACT_CAP", Integer.valueOf(31));
    int count = 0;
    Iterator it = null;
    Set set = contenedor.entrySet();
    it = set.iterator();
    while (it.hasNext())
    {
      Map.Entry entry = (Map.Entry)it.next();
      String value = entry.getValue().toString();
      String key = entry.getKey().toString();
      
      int balint0 = values & 1 << ((Integer)contenedor.get(key)).intValue();
      if (balint0 != 0)
      {
        if (key == "METER_RELAY_STATE") {
          activ = "CERRADO";
          //activ = "CLOSED";
        } else {
          activ = "ACTIVADO";
          //activ = "ACTIVATED";
        }
      }
      else if (key == "METER_RELAY_STATE") {
        activ = "ACTIVADO";
        //activ = "ON";
      } else {
        activ = "DESACTIVADO";
        //activ = "OFF";
      }
      Object[] data = { Integer.valueOf(count), key, activ };
      defaultable.addRow(data);
      
      count++;
    }
  }
}
