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
import com.eneri.scorpio_metertool.hardwarelayer.FlagsPasswords;
import com.eneri.scorpio_metertool.hardwarelayer.ModbusUtil;
import com.eneri.scorpio_metertool.hardwarelayer.PortComunication;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author Roberto
 */
public class Resets
  extends JPanel
{
  private LinkedHashMap<String, FlagsPasswords> listPasswords = new LinkedHashMap();
  private static final long EPOCH_2K_ZERO = 946684800000L;
  
  public Resets()
  {
    initComponents();
    for (FlagsPasswords col : FlagsPasswords.values())
    {
      this.listPasswords.put(col.getName(), col);
      this.jComboBoxFlagPasswords.addItem(col.getName());
    }
  }
  
  private void initComponents()
  {
    this.jScrollPane1 = new JScrollPane();
    this.jTableAccumulateds = new JTable();
    this.jScrollPane2 = new JScrollPane();
    this.jTableRolDemands = new JTable();
    this.jLabelAccumulated = new JLabel();
    this.jLabelRolDemands = new JLabel();
    this.jButtonReadAccumulated = new JButton();
    this.jButtonReadRolDemands = new JButton();
    this.jButtonResetAccumulated = new JButton();
    this.jButtonResetRolDemands = new JButton();
    this.jButtonReboottMeter = new JButton();
    this.jButtonChangeSerialNumber = new JButton();
    this.jLabelRelayOpened = new JLabel();
    this.jButtonRelayOpened = new JButton();
    this.jLabelRelayClosed = new JLabel();
    this.jButtonRelayClosed = new JButton();
    this.jLabelNamePanel = new JLabel();
    this.jComboBoxFlagPasswords = new JComboBox();
    this.jButtonSendPassword = new JButton();
    this.jLabelSerialNumber = new JLabel();
    
    setPreferredSize(new Dimension(950, 600));
    
    
    this.jTableAccumulateds.setModel(this.tableAcummulates);
    this.jTableAccumulateds.setColumnSelectionAllowed(true);
    this.jScrollPane1.setViewportView(this.jTableAccumulateds);
    this.jTableAccumulateds.getColumnModel().getSelectionModel().setSelectionMode(2);
    
    this.jTableRolDemands.setModel(this.tableDemands);
    this.jTableRolDemands.setColumnSelectionAllowed(true);
    this.jScrollPane2.setViewportView(this.jTableRolDemands);
    this.jTableRolDemands.getColumnModel().getSelectionModel().setSelectionMode(2);
    
    this.jLabelAccumulated.setFont(new Font("Tahoma", 1, 12));
    //this.jLabelAccumulated.setText("Acumulados");
    this.jLabelAccumulated.setText("Cumulated");
    
    this.jLabelRolDemands.setFont(new Font("Tahoma", 1, 12));
    //this.jLabelRolDemands.setText("Demandas Roladas");
    this.jLabelRolDemands.setText("Rolling Demands");
    
    //this.jButtonReadAccumulated.setText("LEER");
    this.jButtonReadAccumulated.setText("READ");
    this.jButtonReadAccumulated.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        Resets.this.jButtonReadAccumulatedActionPerformed(evt);
      }
    });
    //this.jButtonReadRolDemands.setText("LEER");
    this.jButtonReadRolDemands.setText("READ");
    this.jButtonReadRolDemands.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        Resets.this.jButtonReadRolDemandsActionPerformed(evt);
      }
    });
    this.jButtonResetAccumulated.setForeground(new Color(255, 0, 0));
    //this.jButtonResetAccumulated.setText("REINICIO DE ACUMULADO");
    this.jButtonResetAccumulated.setText("RESTART OF CUMULATIVE");
    this.jButtonResetAccumulated.setMaximumSize(new Dimension(205, 23));
    this.jButtonResetAccumulated.setMinimumSize(new Dimension(205, 23));
    this.jButtonResetAccumulated.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        Resets.this.jButtonResetAccumulatedActionPerformed(evt);
      }
    });
    this.jButtonResetRolDemands.setForeground(new Color(255, 0, 0));
    //this.jButtonResetRolDemands.setText("REINICIO DE DEMANDAS ROLADAS");
    this.jButtonResetRolDemands.setText("RESTART OF ROLLING DEMANDS");
    this.jButtonResetRolDemands.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        Resets.this.jButtonResetRolDemandsActionPerformed(evt);
      }
    });
    //this.jButtonReboottMeter.setText("REINICIO DE MEDIDOR");
    this.jButtonReboottMeter.setText("RESET METER");
    this.jButtonReboottMeter.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        Resets.this.jButtonReboottMeterActionPerformed(evt);
      }
    });
    //this.jButtonChangeSerialNumber.setText("CAMBIAR NUMERO DE IDENTIFICACION");
    this.jButtonChangeSerialNumber.setText("IDENTIFICATION NUMBER CHANGE");
    this.jButtonChangeSerialNumber.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        Resets.this.jButtonChangeSerialNumberActionPerformed(evt);
      }
    });
    this.jLabelRelayOpened.setText("___/  ___");
    
    //this.jButtonRelayOpened.setText("ABRIR RELAY");
    this.jButtonRelayOpened.setText("OPEN RELAY");
    this.jButtonRelayOpened.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        Resets.this.jButtonRelayOpenedActionPerformed(evt);
      }
    });
    this.jLabelRelayClosed.setText("___ __ ___");
    
    //this.jButtonRelayClosed.setText("CERRAR RELAY");
    this.jButtonRelayClosed.setText("CLOSE RELAY");
    this.jButtonRelayClosed.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        Resets.this.jButtonRelayClosedActionPerformed(evt);
      }
    });
    this.jLabelNamePanel.setFont(new Font("Tahoma", 1, 18));
    //this.jLabelNamePanel.setText("REINICIOS");
    this.jLabelNamePanel.setText("RESTARTS");
    
    //this.jButtonSendPassword.setText("ENVIAR");
    this.jButtonSendPassword.setText("SEND");
    this.jButtonSendPassword.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        Resets.this.jButtonSendPasswordActionPerformed(evt);
      }
    });
    //this.jLabelSerialNumber.setText("Numero de Serie Actual:");
    this.jLabelSerialNumber.setText("Current Serial Number:");
    
    GroupLayout layout = new GroupLayout(this);
    setLayout(layout);
    layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addContainerGap(-1, 32767).addComponent(this.jLabelNamePanel).addGap(410, 410, 410)).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.jLabelRolDemands).addComponent(this.jScrollPane2, -2, 903, -2).addGroup(layout.createSequentialGroup().addGap(16, 16, 16).addComponent(this.jButtonReadRolDemands, -2, 69, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, 32767).addComponent(this.jButtonResetRolDemands)))).addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup().addGap(21, 21, 21).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.jComboBoxFlagPasswords, -2, 400, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jButtonSendPassword, -2, 147, -2)).addComponent(this.jLabelAccumulated).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(this.jScrollPane1, -2, 905, -2).addGroup(layout.createSequentialGroup().addComponent(this.jButtonReadAccumulated, -2, 67, -2).addGap(591, 591, 591).addComponent(this.jButtonResetAccumulated, -2, 229, -2)))).addGap(0, 0, 32767)).addGroup(layout.createSequentialGroup().addComponent(this.jButtonReboottMeter).addGap(32, 32, 32).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.jLabelSerialNumber).addGap(0, 0, 32767)).addGroup(layout.createSequentialGroup().addComponent(this.jButtonChangeSerialNumber).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, 32767).addComponent(this.jLabelRelayOpened).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jButtonRelayOpened).addGap(39, 39, 39).addComponent(this.jLabelRelayClosed).addGap(18, 18, 18).addComponent(this.jButtonRelayClosed))))))).addContainerGap(24, 32767)));
    
    layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(7, 7, 7).addComponent(this.jLabelNamePanel).addGap(18, 18, 18).addComponent(this.jLabelAccumulated).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jScrollPane1, -2, 41, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jButtonReadAccumulated).addComponent(this.jButtonResetAccumulated, -2, -1, -2)).addGap(98, 98, 98).addComponent(this.jLabelRolDemands).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jScrollPane2, -2, 42, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jButtonReadRolDemands).addComponent(this.jButtonResetRolDemands)).addGap(44, 44, 44).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jComboBoxFlagPasswords, -2, -1, -2).addComponent(this.jButtonSendPassword)).addGap(23, 23, 23).addComponent(this.jLabelSerialNumber).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jButtonReboottMeter).addComponent(this.jButtonChangeSerialNumber).addComponent(this.jLabelRelayOpened).addComponent(this.jButtonRelayOpened).addComponent(this.jLabelRelayClosed).addComponent(this.jButtonRelayClosed)).addContainerGap(139, 32767)));
  }
  
  public void setActualSN(String SNMedidorr)
  {
    //this.jLabelSerialNumber.setText("Numero de Serie Actual: " + SNMedidorr);
    this.jLabelSerialNumber.setText("Current Serial Number: " + SNMedidorr);
  }
  
  private void jButtonReadRolDemandsActionPerformed(ActionEvent evt)
  {
    PortComunication port = new PortComunication();
    if (((!port.getStatePort() ? 1 : 0) | (Meter.serialNumber == null ? 1 : 0) /*| Meter.serialNumber.equals("0000000000000000")*/) != 0)
    {
      //JOptionPane.showMessageDialog(null, "Error Revise el numero de serie, Puerto o conexion");
      JOptionPane.showMessageDialog(null, "Error Check serial number, Port or connection","Error",JOptionPane.ERROR_MESSAGE);
    }
    else
    {
      String serialNumber = Meter.getSerialNumberCompl();
      Register regStart = Register.DEMANDA_ROLADA_ACTIVA_ENT;
      int countWords = 16;
      byte actionRW = 3;
      byte[] response = rwRegisters.readRegister(PortComunication.DELAY300, serialNumber, regStart, countWords, actionRW);
      if (response != null) {
        settingValueDemand(response);
      }
    }
  }
  
  private void jButtonResetAccumulatedActionPerformed(ActionEvent evt)
  {
    ModbusUtil modb = new ModbusUtil();
    FlagsPasswords flagPassword = FlagsPasswords.METER_ACC_RESET;
    
    String serialNumber = Meter.getSerialNumberCompl();
    boolean estado = modb.writeFlagsPassw(flagPassword, serialNumber);
    if (estado == true) {
      //JOptionPane.showMessageDialog(null, "Correcto");
      JOptionPane.showMessageDialog(null, "Done!!!","Information",JOptionPane.INFORMATION_MESSAGE);
    } else {
      //JOptionPane.showMessageDialog(null, "Error Revise el numero de serie, Puerto o conexion");
      JOptionPane.showMessageDialog(null, "Error Check serial number, Port or connection","Error",JOptionPane.ERROR_MESSAGE);
    }
  }
  
  private void jButtonReadAccumulatedActionPerformed(ActionEvent evt)
  {
    PortComunication port = new PortComunication();
    if (((!port.getStatePort() ? 1 : 0) | (Meter.serialNumber == null ? 1 : 0) /*| Meter.serialNumber.equals("0000000000000000")*/) != 0)
    {
      //JOptionPane.showMessageDialog(null, "Error Revise el numero de serie, Puerto o conexion");
      JOptionPane.showMessageDialog(null, "Error Check number serial, Port or connection","Error",JOptionPane.ERROR_MESSAGE);
    }
    else
    {
      String serialNumber = Meter.getSerialNumberCompl();
      Register regStart = Register.ENERGIA_ACTIVA_POSITIVA;
      int countWords = 8;
      byte actionRW = 3;
      byte[] response = rwRegisters.readRegister(PortComunication.DELAY300, serialNumber, regStart, countWords, actionRW);
      if (response != null) {
        settingValueEnergy(response);
      }
    }
  }
  
  private void jButtonReboottMeterActionPerformed(ActionEvent evt)
  {
    PortComunication port = new PortComunication();
    if (((!port.getStatePort() ? 1 : 0) | (Meter.serialNumber == null ? 1 : 0)) != 0)
    {
      //JOptionPane.showMessageDialog(null, "Error Revise el numero de serie, Puerto o conexion");
      JOptionPane.showMessageDialog(null, "Error Check serial number, Port or connection","Error",JOptionPane.ERROR_MESSAGE);
    }
    else
    {
      ModbusUtil modb = new ModbusUtil();
      FlagsPasswords flagPassword = FlagsPasswords.METER_FORCE_RESET;
      String serialNumber = Meter.getSerialNumberCompl();
      boolean estado = modb.writeFlagsPassw(flagPassword, serialNumber);
      if (estado == true) {
        //JOptionPane.showMessageDialog(null, "Correcto");
        JOptionPane.showMessageDialog(null, "Done!!!","Information",JOptionPane.INFORMATION_MESSAGE);
      } else {
        //JOptionPane.showMessageDialog(null, "Error Revise el numero de serie, Puerto o conexion");
        JOptionPane.showMessageDialog(null, "Error Check serial number, Port or connection","Error",JOptionPane.ERROR_MESSAGE);
      }
    }
  }
  
  private void jButtonRelayOpenedActionPerformed(ActionEvent evt)
  {
    PortComunication port = new PortComunication();
    if (((!port.getStatePort() ? 1 : 0) | (Meter.serialNumber == null ? 1 : 0) /*| Meter.serialNumber.equals("0000000000000000")*/) != 0)
    {
      //JOptionPane.showMessageDialog(null, "Error Revise el numero de serie, Puerto o conexion");
      JOptionPane.showMessageDialog(null, "Error Check serial number, Port or connection","Error",JOptionPane.ERROR_MESSAGE);
    }
    else
    {
      ModbusUtil modb = new ModbusUtil();
      FlagsPasswords flagPassword = FlagsPasswords.METER_TURN_ON;
      String serialNumber = Meter.getSerialNumberCompl();
      boolean estado = modb.writeFlagsPassw(flagPassword, serialNumber);
      if (estado == true) {
        //JOptionPane.showMessageDialog(null, "Correcto");
        JOptionPane.showMessageDialog(null, "Done!!!","Information",JOptionPane.INFORMATION_MESSAGE);
      } else {
        //JOptionPane.showMessageDialog(null, "Error Revise el numero de serie, Puerto o conexion");
        JOptionPane.showMessageDialog(null, "Error Check serial number, Port or connection","Error",JOptionPane.ERROR_MESSAGE);
      }
    }
  }
  
  private void jButtonRelayClosedActionPerformed(ActionEvent evt)
  {
    PortComunication port = new PortComunication();
    if (((!port.getStatePort() ? 1 : 0) | (Meter.serialNumber == null ? 1 : 0) /*| Meter.serialNumber.equals("0000000000000000")*/) != 0)
    {
      //JOptionPane.showMessageDialog(null, "Error Revise el numero de serie, Puerto o conexion");
      JOptionPane.showMessageDialog(null, "Error Check serial number, Port or connection","Error",JOptionPane.ERROR_MESSAGE);
    }
    else
    {
      ModbusUtil modb = new ModbusUtil();
      FlagsPasswords flagPassword = FlagsPasswords.METER_TURN_OFF;
      String serialNumber = Meter.getSerialNumberCompl();
      boolean estado = modb.writeFlagsPassw(flagPassword, serialNumber);
      if (estado == true) {
        //JOptionPane.showMessageDialog(null, "Correcto");
        JOptionPane.showMessageDialog(null, "Done!!!","Information",JOptionPane.INFORMATION_MESSAGE);
      } else {
        //JOptionPane.showMessageDialog(null, "Error Revise el numero de serie, Puerto o conexion");
        JOptionPane.showMessageDialog(null, "Error Check serial number, Port or connection","Error",JOptionPane.ERROR_MESSAGE);
      }
    }
  }
  
  private void jButtonChangeSerialNumberActionPerformed(ActionEvent evt)
  {
    SwingUtilities.invokeLater(new Runnable()
    {
      public void run()
      {
        Pattern r = Pattern.compile("[^a-zA-Z0-9]");
        //String newserialNumber = JOptionPane.showInputDialog("Inserta el numero de serie del medidor:", null);
        String newserialNumber = JOptionPane.showInputDialog("Insert the meter serial number:", null);
        Matcher m = r.matcher(newserialNumber);
        boolean dato = m.find();
        if ((dato == true | newserialNumber.trim().equals("")))
        {
          //JOptionPane.showMessageDialog(null, "Inserta el numero de serie (\"Ejemplo: NK114190011\") o valores alfanumericos");
          JOptionPane.showMessageDialog(null, "Insert the meter serial number (\"Example: NK114190011\") or values alphanumerics","Information",JOptionPane.INFORMATION_MESSAGE);
          return;
        }
        if (newserialNumber != null)
        {
          PortComunication port = new PortComunication();
          if (((!port.getStatePort() ? 1 : 0) | (Meter.serialNumber == null ? 1 : 0)) != 0)
          {
            //JOptionPane.showMessageDialog(null, "Error Revise el numero de serie, Puerto o conexion");
            JOptionPane.showMessageDialog(null, "Error Check serial number, Port or connection","Error",JOptionPane.ERROR_MESSAGE);
          }
          else if (newserialNumber.length() > 16)
          {
            //JOptionPane.showMessageDialog(null, "Error el nombre no puede tener mas de 16 Caracteres");
            JOptionPane.showMessageDialog(null, "Error name can not have more than 16 characters","Error",JOptionPane.ERROR_MESSAGE);
          }
          else
          {
            String serialNumber = Meter.getSerialNumberCompl();
            Register regStart = Register.NUMERO_CFE;
            int countWords = regStart.getWordLength();
            byte actionRW = 16;
            newserialNumber = Meter.setSerialNumber(newserialNumber);
            rwRegisters.writeMultipleRegisterString(PortComunication.DELAY400, serialNumber, regStart, countWords, newserialNumber, actionRW);
            
            JPanel currentPanel = null;
            Set set = null;
            Iterator it = null;
            Launcher.getMainFrame();set = JFrameMain.getJPanels().entrySet();
            it = set.iterator();
            while (it.hasNext())
            {
              Map.Entry entry = (Map.Entry)it.next();
              if (((Integer)entry.getValue()).intValue() == 0)
              {
                JPanel pane = (JPanel)entry.getKey();
                currentPanel = pane;
              }
            }
            CurrentReadingsRecords readRecords = (CurrentReadingsRecords)currentPanel;
            readRecords.settingSerialNumber(newserialNumber);
          }
        }
        else
        {
          //JOptionPane.showMessageDialog(null, "Inserta el numero de serie (\"Ejemplo: NK114190011\")");
          JOptionPane.showMessageDialog(null, "Insert the serial number (\"Example: NK114190011\")","Information",JOptionPane.INFORMATION_MESSAGE);
        }
      }
    });
  }
  
  private void jButtonResetRolDemandsActionPerformed(ActionEvent evt)
  {
    ModbusUtil modb = new ModbusUtil();
    FlagsPasswords flagPassword = FlagsPasswords.METER_DEMAND_RESET;
    String serialNumber = Meter.getSerialNumberCompl();
    boolean estado = modb.writeFlagsPassw(flagPassword, serialNumber);
    if (estado == true) {
      //JOptionPane.showMessageDialog(null, "Correcto");
      JOptionPane.showMessageDialog(null, "Done!!!","Information",JOptionPane.INFORMATION_MESSAGE);
    } else {
      //JOptionPane.showMessageDialog(null, "Error Revise el numero de serie, Puerto o conexion");
      JOptionPane.showMessageDialog(null, "Error Check serial number, Port or connection","Error",JOptionPane.ERROR_MESSAGE);
    }
  }
  
  private void jButtonSendPasswordActionPerformed(ActionEvent evt)
  {
    Iterator it = null;
    Set set = this.listPasswords.entrySet();
    it = set.iterator();
    while (it.hasNext())
    {
      Map.Entry entry = (Map.Entry)it.next();
      String keyName = entry.getKey().toString();
      FlagsPasswords key = (FlagsPasswords)entry.getValue();
      if (keyName.equals(this.jComboBoxFlagPasswords.getSelectedItem().toString())) {
        setFlags(key);
      }
    }
  }
  
  public void setFlags(FlagsPasswords flagPassword)
  {
    PortComunication port = new PortComunication();
    if (((!port.getStatePort() ? 1 : 0) | (Meter.serialNumber == null ? 1 : 0)) != 0)
    {
      //JOptionPane.showMessageDialog(null, "Error Revise el numero de serie, Puerto o conexion");
      JOptionPane.showMessageDialog(null, "Error Check serial number, Port or connection","Error",JOptionPane.ERROR_MESSAGE);
    }
    else
    {
      ModbusUtil modb = new ModbusUtil();
      
      String serialNumber = Meter.getSerialNumberCompl();
      boolean estado = modb.writeFlagsPassw(flagPassword, serialNumber);
      if (estado == true) {
        //JOptionPane.showMessageDialog(null, "Correcto");
        JOptionPane.showMessageDialog(null, "Done","Information",JOptionPane.INFORMATION_MESSAGE);
      } else {
        //JOptionPane.showMessageDialog(null, "Error Revise el numero de serie, Puerto o conexion");
        JOptionPane.showMessageDialog(null, "Error Check serial number, Port or connection","Error",JOptionPane.ERROR_MESSAGE);
      }
    }
  }
  
  static enum ROW_TYPE
  {
    ROW_KWH(0, "");
    
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
    PHASE_1(0),  PHASE_2(1),  PHASE_3(2),  PHASE_4(3);
    
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
    case ROW_KWH: 
      this.tableAcummulates.setValueAt(value, type.getRow(), phase.getCol());
    }
    this.tableAcummulates.setValueAt(value, type.getRow(), phase.getCol());
  }
  
  //private DefaultTableModel tableAcummulates = new DefaultTableModel(new Object[][] { { "---", "---", "---", "---" } }, new String[] { "Energia Activa Entregada", "Energia Reactiva Entregada", "Energia Activa Recibida", "Energia Reactiva Recibida" })
  private DefaultTableModel tableAcummulates = new DefaultTableModel(new Object[][] { { "---", "---", "---", "---" } }, new String[] { "Active Energy Delivered", "Reactive Energy Delivered", "Active Energy Received", "Reactive Energy Received" })
  {
    Class[] types = { String.class, String.class, String.class, String.class };
    boolean[] canEdit = { false, false, false, false };
    
    public Class getColumnClass(int columnIndex)
    {
      return this.types[columnIndex];
    }
    
    public boolean isCellEditable(int rowIndex, int columnIndex)
    {
      return this.canEdit[columnIndex];
    }
  };
  
  public void settingValueEnergy(byte[] frame)
  {
    Conversions conv = new Conversions();
    
    ByteBuffer bb = ByteBuffer.allocate(frame.length);
    bb.put(frame);
    bb.position(0);
    
    float EnergiaActivaTotal = bb.getFloat();
    float EnergiaReactivaTotal = bb.getFloat();
    float EnergiaNegativaActivaTotal = bb.getFloat();
    float EnergiaNegativaReactivaTotal = bb.getFloat();
    float EnergiaActivaTotalVArh;
    //float EnergiaActivaTotalVArh;
    if (Float.isNaN(EnergiaActivaTotal)) {
      EnergiaActivaTotalVArh = 65535.0F;
    } else {
      EnergiaActivaTotalVArh = EnergiaActivaTotal;
    }
    float EnergiaReactivaTotalkVArh;
    //float EnergiaReactivaTotalkVArh;
    if (Float.isNaN(EnergiaReactivaTotal)) {
      EnergiaReactivaTotalkVArh = 65535.0F;
    } else {
      EnergiaReactivaTotalkVArh = EnergiaReactivaTotal;
    }
    float EnergiaNegativaReactivaTotal1;
    //float EnergiaNegativaReactivaTotal1;
    if (Float.isNaN(EnergiaNegativaReactivaTotal)) {
      EnergiaNegativaReactivaTotal1 = 65535.0F;
    } else {
      EnergiaNegativaReactivaTotal1 = EnergiaNegativaReactivaTotal;
    }
    float EnergiaNegativaActivaTotal1;
    //float EnergiaNegativaActivaTotal1;
    if (Float.isNaN(EnergiaNegativaActivaTotal)) {
      EnergiaNegativaActivaTotal1 = 65535.0F;
    } else {
      EnergiaNegativaActivaTotal1 = EnergiaNegativaActivaTotal;
    }
    double[] data = new double[4];
    data[0] = EnergiaActivaTotal;
    data[1] = EnergiaReactivaTotal;
    data[2] = EnergiaNegativaActivaTotal;
    data[3] = EnergiaNegativaReactivaTotal;
    if (Double.compare(65535.0D, data[0]) == 0) {
      setRow(ROW_TYPE.ROW_KWH, COL_PHASE.PHASE_1, "----");
    } else {
      setRow(ROW_TYPE.ROW_KWH, COL_PHASE.PHASE_1, conv.doubleRound(data[0]));
    }
    if (Double.compare(65535.0D, data[1]) == 0) {
      setRow(ROW_TYPE.ROW_KWH, COL_PHASE.PHASE_2, "----");
    } else {
      setRow(ROW_TYPE.ROW_KWH, COL_PHASE.PHASE_2, conv.doubleRound(data[1]));
    }
    if (Double.compare(65535.0D, data[2]) == 0) {
      setRow(ROW_TYPE.ROW_KWH, COL_PHASE.PHASE_3, "----");
    } else {
      setRow(ROW_TYPE.ROW_KWH, COL_PHASE.PHASE_3, conv.doubleRound(data[2]));
    }
    if (Double.compare(65535.0D, data[3]) == 0) {
      setRow(ROW_TYPE.ROW_KWH, COL_PHASE.PHASE_4, "----");
    } else {
      setRow(ROW_TYPE.ROW_KWH, COL_PHASE.PHASE_4, conv.doubleRound(data[3]));
    }
  }
  
  public void settingValueDemand(byte[] frame)
  {
    Conversions conv = new Conversions();
    
    ByteBuffer bb = ByteBuffer.allocate(frame.length);
    bb.put(frame);
    bb.position(0);
    
    float DemandaRoladaActivaEnt = bb.getFloat();
    long TiempoDemRoladaActivaEnt = bb.getInt();
    float DemandaRoladaReactivaEnt = bb.getFloat();
    long TiempoDemRoladaReactivaEnt = bb.getInt();
    float DemandaRoladaActivaRecib = bb.getFloat();
    long TiempoDemRoladaActivaRecib = bb.getInt();
    float DemandaRoladaReactivaRecib = bb.getFloat();
    long TiempoDemRoladaReactivaRecib = bb.getInt();
    float DemandaRoladaActivaEnt1;
    //float DemandaRoladaActivaEnt1;
    if ((DemandaRoladaActivaEnt == 65535.0F) || (Float.isNaN(DemandaRoladaActivaEnt))) {
      DemandaRoladaActivaEnt1 = 65535.0F;
    } else {
      DemandaRoladaActivaEnt1 = DemandaRoladaActivaEnt;
    }
    long TiempoDemRoladaActivaEnt1 = TiempoDemRoladaActivaEnt;
    float DemandaRoladaReactivaEnt1;
    //float DemandaRoladaReactivaEnt1;
    if ((DemandaRoladaReactivaEnt == 65535.0F) || (Float.isNaN(DemandaRoladaReactivaEnt))) {
      DemandaRoladaReactivaEnt1 = 65535.0F;
    } else {
      DemandaRoladaReactivaEnt1 = DemandaRoladaReactivaEnt;
    }
    long TiempoDemRoladaReactivaEnt1 = TiempoDemRoladaReactivaEnt;
    float DemandaRoladaActivaRecib1;
    //float DemandaRoladaActivaRecib1;
    if ((DemandaRoladaActivaRecib == 65535.0F) || (Float.isNaN(DemandaRoladaActivaRecib))) {
      DemandaRoladaActivaRecib1 = 65535.0F;
    } else {
      DemandaRoladaActivaRecib1 = DemandaRoladaActivaRecib;
    }
    long TiempoDemRoladaActivaRecib1 = TiempoDemRoladaActivaRecib;
    float DemandaRoladaReactivaRecib1;
    //float DemandaRoladaReactivaRecib1;
    if ((DemandaRoladaReactivaRecib == 65535.0F) || (Float.isNaN(DemandaRoladaReactivaRecib))) {
      DemandaRoladaReactivaRecib1 = 65535.0F;
    } else {
      DemandaRoladaReactivaRecib1 = DemandaRoladaReactivaRecib;
    }
    long TiempoDemRoladaReactivaRecib1 = TiempoDemRoladaReactivaRecib;
    
    double[] data = new double[8];
    data[0] = DemandaRoladaActivaEnt1;
    data[1] = TiempoDemRoladaActivaEnt1;
    data[2] = DemandaRoladaReactivaEnt1;
    data[3] = TiempoDemRoladaReactivaEnt1;
    data[4] = DemandaRoladaActivaRecib1;
    data[5] = TiempoDemRoladaActivaRecib1;
    data[6] = DemandaRoladaReactivaRecib1;
    data[7] = TiempoDemRoladaReactivaRecib1;
    
    StringBuilder sb = new StringBuilder();
    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    Date date = new Date();
    int offset = (byte)(int)TimeUnit.MILLISECONDS.toHours(TimeZone.getDefault().getOffset(date.getTime()));
    format.setTimeZone(TimeZone.getTimeZone("GMT" + offset + ""));
    if (Double.compare(65535.0D, data[0]) == 0) {
      setRowDem(ROW_TYPEDEMAND.ROW_DEM, COL_PHASEDEM.PHASE_1, "----");
    } else {
      setRowDem(ROW_TYPEDEMAND.ROW_DEM, COL_PHASEDEM.PHASE_1, conv.doubleRound(data[0]));
    }
    sb.delete(0, sb.length());
    if (data[1] <= 0.0D)
    {
      setRowDem(ROW_TYPEDEMAND.ROW_DEM, COL_PHASEDEM.PHASE_2, "----");
    }
    else
    {
      long TiempoDemRoladaActivaEnt11 = (int)data[1];
      TiempoDemRoladaActivaEnt11 *= 1000L;
      
      sb.append(format.format(new Date(TiempoDemRoladaActivaEnt11 + 946684800000L)).toString());
      setRowDem(ROW_TYPEDEMAND.ROW_DEM, COL_PHASEDEM.PHASE_2, sb.toString());
    }
    if (Double.compare(65535.0D, data[2]) == 0) {
      setRowDem(ROW_TYPEDEMAND.ROW_DEM, COL_PHASEDEM.PHASE_3, "----");
    } else {
      setRowDem(ROW_TYPEDEMAND.ROW_DEM, COL_PHASEDEM.PHASE_3, conv.doubleRound(data[2]));
    }
    sb.delete(0, sb.length());
    if (data[3] <= 0.0D)
    {
      setRowDem(ROW_TYPEDEMAND.ROW_DEM, COL_PHASEDEM.PHASE_4, "----");
    }
    else
    {
      long TiempoDemRoladaReactivaEnt2 = (int)data[3];
      TiempoDemRoladaReactivaEnt2 *= 1000L;
      sb.append(format.format(new Date(TiempoDemRoladaReactivaEnt2 + 946684800000L)).toString());
      setRowDem(ROW_TYPEDEMAND.ROW_DEM, COL_PHASEDEM.PHASE_4, sb.toString());
    }
    if (Double.compare(65535.0D, data[4]) == 0) {
      setRowDem(ROW_TYPEDEMAND.ROW_DEM, COL_PHASEDEM.PHASE_5, "----");
    } else {
      setRowDem(ROW_TYPEDEMAND.ROW_DEM, COL_PHASEDEM.PHASE_5, conv.doubleRound(data[4]));
    }
    sb.delete(0, sb.length());
    if (data[5] <= 0.0D)
    {
      setRowDem(ROW_TYPEDEMAND.ROW_DEM, COL_PHASEDEM.PHASE_6, "----");
    }
    else
    {
      long TiempoDemRoladaActivaRecib3 = (int)data[5];
      TiempoDemRoladaActivaRecib3 *= 1000L;
      sb.append(format.format(new Date(TiempoDemRoladaActivaRecib3 + 946684800000L)).toString());
      setRowDem(ROW_TYPEDEMAND.ROW_DEM, COL_PHASEDEM.PHASE_6, sb.toString());
    }
    if (Double.compare(65535.0D, data[6]) == 0) {
      setRowDem(ROW_TYPEDEMAND.ROW_DEM, COL_PHASEDEM.PHASE_7, "----");
    } else {
      setRowDem(ROW_TYPEDEMAND.ROW_DEM, COL_PHASEDEM.PHASE_7, conv.doubleRound(data[6]));
    }
    sb.delete(0, sb.length());
    if (data[7] <= 0.0D)
    {
      setRowDem(ROW_TYPEDEMAND.ROW_DEM, COL_PHASEDEM.PHASE_8, "----");
    }
    else
    {
      long TiempoDemRoladaReactivaRecib4 = (int)data[7];
      TiempoDemRoladaReactivaRecib4 *= 1000L;
      sb.append(format.format(new Date(TiempoDemRoladaReactivaRecib4 + 946684800000L)).toString());
      setRowDem(ROW_TYPEDEMAND.ROW_DEM, COL_PHASEDEM.PHASE_8, sb.toString());
    }
  }
  
  static enum ROW_TYPEDEMAND
  {
    ROW_DEM(0, "");
    
    int row;
    String units;
    
    private ROW_TYPEDEMAND(int row, String units)
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
  
  static enum COL_PHASEDEM
  {
    PHASE_1(0),  PHASE_2(1),  PHASE_3(2),  PHASE_4(3),  PHASE_5(4),  PHASE_6(5),  PHASE_7(6),  PHASE_8(7);
    
    int col;
    
    private COL_PHASEDEM(int col)
    {
      this.col = col;
    }
    
    public int getCol()
    {
      return this.col;
    }
    
    public int getColumn()
    {
      return this.col;
    }
  }
  
  private void setRowDem(ROW_TYPEDEMAND type, COL_PHASEDEM phase, String value)
  {
    switch (type)
    {
    case ROW_DEM: 
      this.tableDemands.setValueAt(value, type.getRow(), phase.getCol());
    }
    this.tableDemands.setValueAt(value, type.getRow(), phase.getCol());
  }
  
  //private DefaultTableModel tableDemands = new DefaultTableModel(new Object[][] { { "---", "---", "---", "---", "---", "---", "---", "---" } }, new String[] { "Activa Entr.", "Tiempo", "Reactiva Entr.", "Tiempo", "Activa Recib.", "Tiempo", "Reactiva Recib.", "Tiempo" })
  private DefaultTableModel tableDemands = new DefaultTableModel(new Object[][] { { "---", "---", "---", "---", "---", "---", "---", "---" } }, new String[] { "Active delivered", "Time", "Delivered Reactivate", "Time", "Active Received", "Time", "reactive Received", "Time" })
  {
    Class[] typesDem = { String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class };
    boolean[] canEditDem = { false, false, false, false, false, false, false, false };
    
    public Class getColumnClass(int columnIndex)
    {
      return this.typesDem[columnIndex];
    }
    
    public boolean isCellEditable(int rowIndex, int columnIndex)
    {
      return this.canEditDem[columnIndex];
    }
  };
  private JButton jButtonChangeSerialNumber;
  private JButton jButtonReadAccumulated;
  private JButton jButtonReadRolDemands;
  private JButton jButtonReboottMeter;
  private JButton jButtonRelayClosed;
  private JButton jButtonRelayOpened;
  private JButton jButtonResetAccumulated;
  private JButton jButtonResetRolDemands;
  private JButton jButtonSendPassword;
  private JComboBox jComboBoxFlagPasswords;
  private JLabel jLabelAccumulated;
  private JLabel jLabelNamePanel;
  private JLabel jLabelRelayClosed;
  private JLabel jLabelRelayOpened;
  private JLabel jLabelRolDemands;
  private JLabel jLabelSerialNumber;
  private JScrollPane jScrollPane1;
  private JScrollPane jScrollPane2;
  private JTable jTableAccumulateds;
  private JTable jTableRolDemands;
}
