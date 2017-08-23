/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eneri.scorpio_metertool.ui;

import com.eneri.scorpio_metertool.bus.Conversions;
import com.eneri.scorpio_metertool.devices.Meter;
import com.eneri.scorpio_metertool.devices.Register;
import com.eneri.scorpio_metertool.devices.ScorpioMeter;
import com.eneri.scorpio_metertool.hardwarelayer.FlagsPasswords;
import com.eneri.scorpio_metertool.hardwarelayer.ModbusUtil;
import com.eneri.scorpio_metertool.hardwarelayer.PortComunication;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.LayoutStyle.ComponentPlacement;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 *
 * @author Roberto
 */
public class ReadWriteRegister
  extends JPanel
{
  ArrayList list = new ArrayList();
  ModbusUtil modb = new ModbusUtil();
  int address = 0;
  int count = 0;
  byte actionRW = 0;
  String selected = "";
  byte[] receiveData;
  private JButton jButton1;
  private JButton jButtonAscToHex;
  private JButton jButtonCleanLog;
  private JButton jButtonHexToAsc;
  private JButton jButtonReadRegister;
  private JButton jButtonSend;
  private JCheckBox jCheckBoxOther;
  private JComboBox jComboBoxDataType;
  public JComboBox jComboBoxRegisters;
  private JLabel jLabelNamePanel;
  private JLabel jLabelNewRegister;
  private JLabel jLabelReg;
  private JLabel jLabelRegWr;
  private JLabel jLabelRegister;
  private JLabel jLabelSize;
  private JLabel jLabelTableRegister;
  private JScrollPane jScrollPane1;
  private JTextArea jTextAreaLog;
  private JTextField jTextFieldConversor;
  private JTextField jTextFieldNewValue;
  private JTextField jTextFieldRegister;
  private JTextField jTextFieldSizeRegister;
  
  public ReadWriteRegister()
  {
    initComponents();
    for (Register reg : Register.values()) {
      if (((reg == Register.RANGE_MEASUREMENT_BLOCK ? 1 : 0) | (reg == Register.RANGO_DIFERENCIAL_CARGA_PERFIL ? 1 : 0)) == 0) {
        this.jComboBoxRegisters.addItem(reg.toString());
      }
    }
    AutoCompleteDecorator.decorate(this.jComboBoxRegisters);
    
    this.jButtonSend.setEnabled(false);
  }
  
  public void itemStateChanged()
  {
    for (Register reg : Register.values()) {
      if (this.jComboBoxRegisters.getSelectedItem() == reg.name())
      {
        String nameAddress = this.jComboBoxRegisters.getSelectedItem().toString();
        Register regs = Register.valueOf(nameAddress);
        int value = regs.getWordLength();
        
        this.jTextFieldRegister.setText(Integer.toHexString(regs.getAddress()));
        this.jLabelReg.setText("0x" + Integer.toString(value));
        this.jLabelRegWr.setText("0x" + Integer.toString(value * 2));
        if (reg.getTypeWrite().equals("R")) {
          this.jButtonSend.setEnabled(false);
        } else {
          this.jButtonSend.setEnabled(true);
        }
      }
    }
  }
  
  public void readRegistersOther()
  {
    if (this.jTextFieldSizeRegister.getText().equals(""))
    {
      //JOptionPane.showMessageDialog(this, "Inserta tamaño de Registro");
      JOptionPane.showMessageDialog(this, "Insert size Register","Information",JOptionPane.INFORMATION_MESSAGE);
      return;
    }
    //this.selected = "ESPECIFICO";
    this.selected = "SPECIFIC";
    String serialNumber = Meter.getSerialNumberCompl();
    this.actionRW = 3;
    String regType = this.jComboBoxDataType.getSelectedItem().toString();
    Register reg = Register.OTHER_REGISTER;
    for (Register regs : Register.values()) {
      if (regs.getType().equals(regType))
      {
        reg = regs;
        break;
      }
    }
    this.address = Integer.parseInt(this.jTextFieldRegister.getText(), 16);
    this.count = Integer.parseInt(this.jTextFieldSizeRegister.getText());
    readWriteRegister(serialNumber, this.address, this.count, this.actionRW, this.selected, reg);
  }
  
  public void readRegistersCombobox()
  {
    this.selected = this.jComboBoxRegisters.getSelectedItem().toString();
    String serialNumber = Meter.getSerialNumberCompl();
    this.actionRW = 3;
    for (Register reg : Register.values()) {
      if (reg.toString().equals(this.selected))
      {
        this.address = reg.getAddress();
        this.count = reg.getWordLength();
        readWriteRegister(serialNumber, this.address, this.count, this.actionRW, this.selected, reg);
      }
    }
  }
  
  private void initComponents()
  {
    this.jLabelNamePanel = new JLabel();
    this.jComboBoxRegisters = new JComboBox();
    this.jTextFieldNewValue = new JTextField();
    this.jButtonSend = new JButton();
    this.jScrollPane1 = new JScrollPane();
    this.jTextAreaLog = new JTextArea();
    this.jLabelRegister = new JLabel();
    this.jLabelNewRegister = new JLabel();
    this.jButtonCleanLog = new JButton();
    this.jButtonReadRegister = new JButton();
    this.jLabelReg = new JLabel();
    this.jLabelRegWr = new JLabel();
    this.jLabelTableRegister = new JLabel();
    this.jButton1 = new JButton();
    this.jCheckBoxOther = new JCheckBox();
    this.jTextFieldRegister = new JTextField();
    this.jLabelSize = new JLabel();
    this.jTextFieldSizeRegister = new JTextField();
    this.jComboBoxDataType = new JComboBox();
    this.jTextFieldConversor = new JTextField();
    this.jButtonAscToHex = new JButton();
    this.jButtonHexToAsc = new JButton();
    
    this.jLabelNamePanel.setFont(new Font("Tahoma", 1, 18));
    //this.jLabelNamePanel.setText("REGISTROS");
    this.jLabelNamePanel.setText("REGISTERS");
    
    this.jComboBoxRegisters.setEditable(true);
    this.jComboBoxRegisters.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        ReadWriteRegister.this.jComboBoxRegistersActionPerformed(evt);
      }
    });
    this.jTextFieldNewValue.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        ReadWriteRegister.this.jTextFieldNewValueActionPerformed(evt);
      }
    });
    //this.jButtonSend.setText("ESCRIBIR REGISTRO");
    this.jButtonSend.setText("READ REGISTERS");
    this.jButtonSend.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        ReadWriteRegister.this.jButtonSendActionPerformed(evt);
      }
    });
    this.jTextAreaLog.setColumns(20);
    this.jTextAreaLog.setLineWrap(true);
    this.jTextAreaLog.setRows(5);
    this.jScrollPane1.setViewportView(this.jTextAreaLog);
    
    //this.jLabelRegister.setText("Registro");
    this.jLabelRegister.setText("Register");
    
    //this.jLabelNewRegister.setText("Nuevo Registro");
    this.jLabelNewRegister.setText("New Register");
    
    //this.jButtonCleanLog.setText("LIMPIAR LOG");
    this.jButtonCleanLog.setText("CLEAR LOG");
    this.jButtonCleanLog.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        ReadWriteRegister.this.jButtonCleanLogActionPerformed(evt);
      }
    });
    //this.jButtonReadRegister.setText("LEER REGISTRO");
    this.jButtonReadRegister.setText("READ REGISTER");
    this.jButtonReadRegister.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        ReadWriteRegister.this.jButtonReadRegisterActionPerformed(evt);
      }
    });
    this.jLabelReg.setText("0x");
    
    this.jLabelRegWr.setText("0x");
    
    this.jLabelTableRegister.setText("Registro: 0x");
    
    this.jButton1.setText("STPMC RESET");
    this.jButton1.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        ReadWriteRegister.this.jButton1ActionPerformed(evt);
      }
    });
    //this.jCheckBoxOther.setText("Reg Especifico");
    this.jCheckBoxOther.setText("Reg Specific");
    
    //this.jLabelSize.setText("Tamaño: \"Words\"");
    this.jLabelSize.setText("Size: \"Words\"");
    
    this.jComboBoxDataType.setModel(new DefaultComboBoxModel(new String[] { "DO", "DA", "S", "L", "FW", "HEX" }));
    
    this.jButtonAscToHex.setText("CONV A HEX");
    this.jButtonAscToHex.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        ReadWriteRegister.this.jButtonAscToHexActionPerformed(evt);
      }
    });
    this.jButtonHexToAsc.setText("CONV A ASCII");
    this.jButtonHexToAsc.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        ReadWriteRegister.this.jButtonHexToAscActionPerformed(evt);
      }
    });
    GroupLayout layout = new GroupLayout(this);
    setLayout(layout);
    layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(21, 21, 21).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false).addGroup(layout.createSequentialGroup().addGap(10, 10, 10).addComponent(this.jButtonCleanLog).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, 32767).addComponent(this.jButtonHexToAsc).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jButtonAscToHex).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jTextFieldConversor, -2, 339, -2).addGap(38, 38, 38).addComponent(this.jButton1)).addComponent(this.jScrollPane1, -2, 903, -2)).addGroup(layout.createSequentialGroup().addGap(2, 2, 2).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(this.jButtonReadRegister, -2, 138, -2).addComponent(this.jComboBoxRegisters, -2, 380, -2)).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jLabelReg).addGap(78, 78, 78).addComponent(this.jTextFieldNewValue, -2, 383, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jLabelRegWr)).addGroup(layout.createSequentialGroup().addGap(348, 348, 348).addComponent(this.jButtonSend, -2, 153, -2)))).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false).addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup().addGap(3, 3, 3).addComponent(this.jCheckBoxOther).addGap(18, 18, 18).addComponent(this.jLabelTableRegister).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jTextFieldRegister, -2, 58, -2).addGap(18, 18, 18).addComponent(this.jLabelSize).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jTextFieldSizeRegister, -2, 57, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, 32767).addComponent(this.jComboBoxDataType, -2, 53, -2)).addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup().addComponent(this.jLabelRegister).addGap(324, 324, 324).addComponent(this.jLabelNamePanel))).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jLabelNewRegister))))).addContainerGap(26, 32767)));
    
    layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.jLabelNamePanel).addGap(0, 11, 32767)).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addGap(0, 0, 32767).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabelRegister).addComponent(this.jLabelNewRegister)))).addGap(8, 8, 8).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabelRegWr).addComponent(this.jTextFieldNewValue, -2, -1, -2).addComponent(this.jLabelReg).addComponent(this.jComboBoxRegisters, -2, -1, -2)).addGap(1, 1, 1).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jButtonSend).addComponent(this.jButtonReadRegister)).addGap(18, 18, 18).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jTextFieldRegister, -2, -1, -2).addComponent(this.jLabelTableRegister).addComponent(this.jCheckBoxOther).addComponent(this.jLabelSize).addComponent(this.jTextFieldSizeRegister, -2, -1, -2).addComponent(this.jComboBoxDataType, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jScrollPane1, -2, 424, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jTextFieldConversor, -2, -1, -2).addComponent(this.jButtonHexToAsc).addComponent(this.jButtonAscToHex)).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jButtonCleanLog).addComponent(this.jButton1))).addGap(23, 23, 23)));
  }
  
  private void jComboBoxRegistersActionPerformed(ActionEvent evt)
  {
    itemStateChanged();
  }
  
  private void jButtonReadRegisterActionPerformed(ActionEvent evt)
  {
    PortComunication port = new PortComunication();
    if (((!port.getStatePort() ? 1 : 0) | (Meter.serialNumber == null ? 1 : 0) /*| Meter.serialNumber.equals("0000000000000000")*/) != 0) {
      //JOptionPane.showMessageDialog(null, "Error Revise el numero de serie, Puerto o conexion");
      JOptionPane.showMessageDialog(null, "Error Check serial number, Port or Connection","Error",JOptionPane.ERROR_MESSAGE);
    } else if (this.jCheckBoxOther.isSelected()) {
      readRegistersOther();
    } else {
      readRegistersCombobox();
    }
  }
  
  private void jButtonCleanLogActionPerformed(ActionEvent evt)
  {
    this.jTextAreaLog.setText("");
  }
  
  private void jButtonSendActionPerformed(ActionEvent evt)
  {
    PortComunication port = new PortComunication();
    if (((!port.getStatePort() ? 1 : 0) | (Meter.serialNumber == null ? 1 : 0)) != 0) {
      //JOptionPane.showMessageDialog(null, "Error Revise el numero de serie, Puerto o conexion");
      JOptionPane.showMessageDialog(null, "Error Check serial number, Port or connection","Error",JOptionPane.ERROR_MESSAGE);
    } else if (!this.jCheckBoxOther.isSelected()) {
      writeRegistersCombobox();
    }
  }
  
  private void jTextFieldNewValueActionPerformed(ActionEvent evt) {}
  
  private void jButton1ActionPerformed(ActionEvent evt)
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
      FlagsPasswords flagPassword = FlagsPasswords.METER_STPMC_RESET;
      String serialNumber = Meter.getSerialNumberCompl();
      boolean estado = modb.writeFlagsPassw(flagPassword, serialNumber);
      if (estado == true) {
        //JOptionPane.showMessageDialog(null, "Correcto");
        JOptionPane.showMessageDialog(null, "Good","Information",JOptionPane.INFORMATION_MESSAGE);
      } else {
        //JOptionPane.showMessageDialog(null, "Error Revise el numero de serie, Puerto o conexion");
        JOptionPane.showMessageDialog(null, "Error Check serial number, Port or connection","Error",JOptionPane.ERROR_MESSAGE);
      }
    }
  }
  
  private void jButtonAscToHexActionPerformed(ActionEvent evt)
  {
    Conversions conv = new Conversions();
    this.jTextFieldConversor.setText(Conversions.asciiToHex(this.jTextFieldConversor.getText()));
  }
  
  private void jButtonHexToAscActionPerformed(ActionEvent evt)
  {
    Conversions conv = new Conversions();
    this.jTextFieldConversor.setText(Conversions.hexToASCII(this.jTextFieldConversor.getText()));
  }
  
  private boolean innerValidatePayload(Register reg)
  {
    boolean valid = true;
    if (this.jTextFieldNewValue.getText().isEmpty())
    {
      //System.out.println("EL valor debe ser escrito!");
      System.out.println("The value must be written!");
      return false;
    }
    String stringPayload = this.jTextFieldNewValue.getText().trim().replace(" ", "");
    stringPayload = stringPayload.replaceAll("\\s", "");
    
    String[] arrayMAC = stringPayload.split("(?<=\\G.{2})");
    if (arrayMAC.length > 16)
    {
      //System.out.println("Payload debe ser de 16 bytes o menos!");
      //JOptionPane.showMessageDialog(null, "Payload debe ser de 16 bytes o menos!");
      System.out.println("Payload must be 16 bytes or less!");
      JOptionPane.showMessageDialog(null, "Payload must be 16 bytes or less!","Information",JOptionPane.INFORMATION_MESSAGE);
      return false;
    }
    byte[] bytePayload = new byte[arrayMAC.length];
    try
    {
      for (int idx = 0; idx < arrayMAC.length; idx++)
      {
        int thisByte = Integer.parseInt(arrayMAC[idx], 16);
        bytePayload[idx] = ((byte)thisByte);
      }
    }
    catch (NumberFormatException ex)
    {
      //System.out.println("Valor debe contener HEX digitos validos.");
      //JOptionPane.showMessageDialog(null, "Valor debe contener HEX digitos validos.");
      System.out.println("HEX value must contain valid digits.");
      JOptionPane.showMessageDialog(null, "HEX value must contain valid digits.","Warning",JOptionPane.WARNING_MESSAGE);
      return false;
    }
    if ((reg.getWordLength() == 1) && 
      (bytePayload.length != 2))
    {
      //System.out.println("para ESCRIBIR_REGISTROS, debe ser de 2 bytes para el valor.");
      //JOptionPane.showMessageDialog(null, "para ESCRIBIR_REGISTROS, debe ser de 2 bytes para el valor.");
      System.out.println("to write registers , it should be 2 bytes for the value.");
      JOptionPane.showMessageDialog(null, "to write registers , it should be 2 bytes for the value.","Warning",JOptionPane.WARNING_MESSAGE);
      valid = false;
    }
    if ((reg.getWordLength() > 1) && 
      (bytePayload.length <= 2))
    {
      //System.out.println("para ESCRIBIR_MULTIPLES_REGISTROS, este debe ser mas de 2 bytes para el valor.");
      //JOptionPane.showMessageDialog(null, "para ESCRIBIR_MULTIPLES_REGISTROS, este debe ser mas de 2 bytes para el valor.");
      System.out.println("To write multiple registers , this should be more than 2 bytes for the value.");
      JOptionPane.showMessageDialog(null, "To write multiple registers , this should be more than 2 bytes for the value.","Warning",JOptionPane.WARNING_MESSAGE);
      valid = false;
    }
    return valid;
  }
  
  public void writeRegistersCombobox()
  {
    this.selected = this.jComboBoxRegisters.getSelectedItem().toString();
    String serialNumber = Meter.getSerialNumberCompl();
    for (Register reg : Register.values()) {
      if (reg.toString().equals(this.selected))
      {
        this.address = reg.getAddress();
        this.count = reg.getWordLength();
        if (reg.getWordLength() > 1) {
          this.actionRW = 16;
        } else {
          this.actionRW = 6;
        }
        boolean valid = innerValidatePayload(reg);
        if (valid) {
          readWriteRegister(serialNumber, this.address, this.count, this.actionRW, this.selected, reg);
        }
      }
    }
  }
  
  public void readWriteRegister(String serialNumber, int regStart, int countWords, byte actionRW, String nameRegister, Register reg)
  {
    Font f = new Font("Helvetica", 1, 12);
    this.jTextAreaLog.setFont(f);
    
    ModbusUtil modb = new ModbusUtil();
    PortComunication port = new PortComunication();
    Conversions conv = new Conversions();
    ScorpioMeter scorp = new ScorpioMeter();
    
    byte[] data = Conversions.hexStringToByteArray(this.jTextFieldNewValue.getText().trim().replace(" ", ""));
    byte[] frame = new byte[0];
    int countonlyresponsebytes = 19;
    if (actionRW == 6)
    {
      frame = modb.writeRegister(serialNumber, regStart, data);
      countonlyresponsebytes = 20;
    }
    else if (actionRW == 16)
    {
      frame = modb.writeMultipleRegisters(serialNumber, regStart, countWords, data, actionRW);
      countonlyresponsebytes = 22;
    }
    else if (actionRW == 3)
    {
      frame = modb.readRegister(serialNumber, regStart, countWords, actionRW);
      countonlyresponsebytes = 19;
    }
    port.write(frame, PortComunication.DELAY400);
    
    byte[] response = port.getByteBuffer();
    if (response == null)
    {
      //JOptionPane.showMessageDialog(null, "Error Revise el numero de serie, Puerto o conexion");
      JOptionPane.showMessageDialog(null, "Error Check serial number, Port or connection","Error",JOptionPane.ERROR_MESSAGE);
    }
    else
    {
      int responseCountWords = countWords * 2;
      int responseCount = countWords * 2 - 3;
      
      int total = frame.length + responseCount;
      String resp = "";
      
      String timeStampComplete = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
      try
      {
        this.receiveData = new byte[responseCountWords];
        System.arraycopy(response, countonlyresponsebytes, this.receiveData, 0, responseCountWords);
      }
      catch (Exception ex)
      {
        JOptionPane.showMessageDialog(null, ex.getMessage());
        return;
      }
      try
      {
        resp = ScorpioMeter.convert(this.receiveData, reg);
      }
      catch (Exception e) {}
      String a = Conversions.byteArrayToHex(this.receiveData);
      System.out.println(timeStampComplete + " Received: " + a);
      System.out.println(timeStampComplete + " Received: " + resp);
      this.jTextAreaLog.append(timeStampComplete + " ---> [ Received ]\n");
      this.jTextAreaLog.append(timeStampComplete + " ---> " + nameRegister.replace('_', ' ') + "\n");
      this.jTextAreaLog.append(timeStampComplete + " ---> [ " + a + " ]\n");
      this.jTextAreaLog.append(timeStampComplete + " ---> " + resp + "\n");
    }
  }
  
  public static ReadWriteRegister getInstance()
  {
    return getInstance();
  }
}
