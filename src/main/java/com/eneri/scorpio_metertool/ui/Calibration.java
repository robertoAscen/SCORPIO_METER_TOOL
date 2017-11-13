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
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Writer;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Roberto
 */
public class Calibration
  extends JPanel
{
  public LinkedHashMap<Register, JTextField> contenedor = new LinkedHashMap();
  public LinkedHashMap<Object, Object> listCalibrations = new LinkedHashMap();
  private JButton jButton1;
  private JButton jButtonChangeRegister;
  private JButton jButtonExportTXT;
  private JButton jButtonReadValues;
  private JButton jButtonSTPMCreset;
  private JLabel jLabelCCA;
  private JLabel jLabelCCB;
  private JLabel jLabelCIN;
  private JLabel jLabelCIR;
  private JLabel jLabelCIS;
  private JLabel jLabelCIT;
  private JLabel jLabelCPC;
  private JLabel jLabelCPR;
  private JLabel jLabelCPS;
  private JLabel jLabelCPT;
  private JLabel jLabelCVR;
  private JLabel jLabelCVS;
  private JLabel jLabelCVT;
  private JLabel jLabelNamePanel;
  private JScrollPane jScrollPane1;
  private JTable jTableCalibration;
  private JTextField jTextFieldCCA;
  private JTextField jTextFieldCCB;
  private JTextField jTextFieldCIN;
  private JTextField jTextFieldCIR;
  private JTextField jTextFieldCIS;
  private JTextField jTextFieldCIT;
  private JTextField jTextFieldCPC;
  private JTextField jTextFieldCPR;
  private JTextField jTextFieldCPS;
  private JTextField jTextFieldCPT;
  private JTextField jTextFieldCVR;
  private JTextField jTextFieldCVS;
  private JTextField jTextFieldCVT;
  
  public Calibration()
  {
    initComponents();
  }
  
  private void initComponents()
  {
    jLabelNamePanel = new JLabel();
    jScrollPane1 = new JScrollPane();
    jTableCalibration = new JTable();
    jTextFieldCCA = new JTextField();
    jTextFieldCIN = new JTextField();
    jTextFieldCIR = new JTextField();
    jTextFieldCIS = new JTextField();
    jTextFieldCIT = new JTextField();
    jTextFieldCVR = new JTextField();
    jTextFieldCVS = new JTextField();
    jTextFieldCVT = new JTextField();
    jTextFieldCPR = new JTextField();
    jTextFieldCPS = new JTextField();
    jTextFieldCPT = new JTextField();
    jTextFieldCCB = new JTextField();
    jTextFieldCPC = new JTextField();
    jLabelCCA = new JLabel();
    jLabelCIN = new JLabel();
    jLabelCIR = new JLabel();
    jLabelCIS = new JLabel();
    jLabelCIT = new JLabel();
    jLabelCVR = new JLabel();
    jLabelCVS = new JLabel();
    jLabelCVT = new JLabel();
    jLabelCPR = new JLabel();
    jLabelCPS = new JLabel();
    jLabelCPT = new JLabel();
    jLabelCCB = new JLabel();
    jLabelCPC = new JLabel();
    jButtonChangeRegister = new JButton();
    jButtonSTPMCreset = new JButton();
    jButtonReadValues = new JButton();
    jButtonExportTXT = new JButton();
    jButton1 = new JButton();
    
    setPreferredSize(new Dimension(950, 600));
    
    jLabelNamePanel.setFont(new Font("Tahoma", 1, 18));
    //jLabelNamePanel.setText("CALIBRATION");//Uso en ingles
    jLabelNamePanel.setText("CALIBRACIÓN");//Uso en español
    
    jTableCalibration.setFont(new Font("Tahoma", 0, 14));
    jTableCalibration.setModel(defaultCalibration);
    jTableCalibration.setRowHeight(24);
    jScrollPane1.setViewportView(jTableCalibration);
    
    jTextFieldCIT.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        Calibration.this.jTextFieldCITActionPerformed(evt);
      }
    });
    
    jTextFieldCPS.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        Calibration.this.jTextFieldCPSActionPerformed(evt);
      }
    });
    
    jTextFieldCPT.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        Calibration.this.jTextFieldCPTActionPerformed(evt);
      }
    });
    
    jLabelCCA.setText("CCA");    
    jLabelCIN.setText("CIN");    
    jLabelCIR.setText("CIR");    
    jLabelCIS.setText("CIS");    
    jLabelCIT.setText("CIT");    
    jLabelCVR.setText("CVR");    
    jLabelCVS.setText("CVS");    
    jLabelCVT.setText("CVT");    
    jLabelCPR.setText("CPR");    
    jLabelCPS.setText("CPS");    
    jLabelCPT.setText("CPT");    
    jLabelCCB.setText("CCB");    
    jLabelCPC.setText("CPC");

    jTextFieldCCA.setToolTipText("Ingresa el valor a establecer en el medidor");
    jTextFieldCCB.setToolTipText("Ingresa el valor a establecer en el medidor");
    jTextFieldCIN.setToolTipText("Ingresa el valor a establecer en el medidor");
    jTextFieldCIR.setToolTipText("Ingresa el valor a establecer en el medidor");
    jTextFieldCIS.setToolTipText("Ingresa el valor a establecer en el medidor");
    jTextFieldCIT.setToolTipText("Ingresa el valor a establecer en el medidor");
    jTextFieldCPC.setToolTipText("Ingresa el valor a establecer en el medidor");
    jTextFieldCPR.setToolTipText("Ingresa el valor a establecer en el medidor");
    jTextFieldCPS.setToolTipText("Ingresa el valor a establecer en el medidor");
    jTextFieldCPT.setToolTipText("Ingresa el valor a establecer en el medidor");
    jTextFieldCVR.setToolTipText("Ingresa el valor a establecer en el medidor");
    jTextFieldCVS.setToolTipText("Ingresa el valor a establecer en el medidor");
    jTextFieldCVT.setToolTipText("Ingresa el valor a establecer en el medidor");
    
    //jButtonChangeRegister.setText("SAVE REGISTERS");//Uso en ingles
    jButtonChangeRegister.setText("GUARDAR REGISTROS");//Uso en español
    jButtonChangeRegister.setToolTipText("Oprime el botón para guardar los registros en el medidor");
    jButtonChangeRegister.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        Calibration.this.jButtonChangeRegisterActionPerformed(evt);
      }
    });
    
    jButtonSTPMCreset.setText("STPMC RESET");
    jButtonSTPMCreset.setToolTipText("Oprime el botón para fijar los valores antes guardados en el medidor");
    jButtonSTPMCreset.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        Calibration.this.jButtonSTPMCresetActionPerformed(evt);
      }
    });
    
    //jButtonReadValues.setText("READ");//Uso en ingles
    jButtonReadValues.setText("LEER");//Uso en español
    jButtonReadValues.setToolTipText("Oprime el botón para leer los registros establecidos en el medidor");
    jButtonReadValues.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        Calibration.this.jButtonReadValuesActionPerformed(evt);
      }
    });
    
    //jButtonExportTXT.setText("EXPORT TO TXT");//Uso en ingles
    jButtonExportTXT.setText("EXPORTAR A TXT");//Uso en español
    jButtonExportTXT.setToolTipText("Oprime el botón para exportar los valores a un archivo .txt");
    jButtonExportTXT.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        Calibration.this.jButtonExportTXTActionPerformed(evt);
      }
    });
    
    jButton1.setForeground(new Color(255, 0, 0));
    //jButton1.setText("RESET VALUES");//Uso en ingles
    jButton1.setText("RESETEAR VALORES");//Uso en español
    jButton1.setToolTipText("Oprime el botón para establecer los valores de calibración a valores de fabrica");
    jButton1.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        Calibration.this.jButton1ActionPerformed(evt);
      }
    });
    GroupLayout layout = new GroupLayout(this);
    setLayout(layout);
    layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(415, 415, 415).addComponent(this.jLabelNamePanel).addContainerGap(-1, 32767)).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addContainerGap(93, 32767).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false).addComponent(this.jScrollPane1, -2, 312, -2).addGroup(layout.createSequentialGroup().addComponent(this.jButtonReadValues).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, 32767).addComponent(this.jButtonExportTXT))).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addGroup(layout.createSequentialGroup().addGap(155, 155, 155).addComponent(this.jButtonSTPMCreset, -2, 340, -2)).addGroup(layout.createSequentialGroup().addGap(154, 154, 154).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabelCCA, GroupLayout.Alignment.TRAILING).addComponent(this.jLabelCIN, GroupLayout.Alignment.TRAILING).addComponent(this.jLabelCIR, GroupLayout.Alignment.TRAILING).addComponent(this.jLabelCIS, GroupLayout.Alignment.TRAILING).addComponent(this.jLabelCIT, GroupLayout.Alignment.TRAILING).addComponent(this.jLabelCVR, GroupLayout.Alignment.TRAILING).addComponent(this.jLabelCVS, GroupLayout.Alignment.TRAILING).addComponent(this.jLabelCVT, GroupLayout.Alignment.TRAILING).addComponent(this.jLabelCPR, GroupLayout.Alignment.TRAILING).addComponent(this.jLabelCPC, GroupLayout.Alignment.TRAILING).addGroup(layout.createSequentialGroup().addGap(1, 1, 1).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(this.jLabelCPS).addComponent(this.jLabelCPT).addComponent(this.jLabelCCB)))).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.jTextFieldCIN).addComponent(this.jTextFieldCIR).addComponent(this.jTextFieldCIS).addComponent(this.jTextFieldCIT).addComponent(this.jTextFieldCVR).addComponent(this.jTextFieldCVS).addComponent(this.jTextFieldCVT).addComponent(this.jTextFieldCPR).addComponent(this.jTextFieldCCA).addComponent(this.jTextFieldCPC).addComponent(this.jTextFieldCCB).addComponent(this.jTextFieldCPT).addComponent(this.jTextFieldCPS, -2, 80, -2)).addGap(197, 197, 197)).addGroup(layout.createSequentialGroup().addGap(1, 1, 1).addComponent(this.jButtonChangeRegister).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, 32767).addComponent(this.jButton1))))).addContainerGap(50, 32767)));
    
    layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(this.jLabelNamePanel).addGap(67, 67, 67).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.jScrollPane1, -2, 335, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jButtonReadValues).addComponent(this.jButtonExportTXT))).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jTextFieldCCA, -2, -1, -2).addComponent(this.jLabelCCA)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jTextFieldCIN, -2, -1, -2).addComponent(this.jLabelCIN)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jTextFieldCIR, -2, -1, -2).addComponent(this.jLabelCIR)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jTextFieldCIS, -2, -1, -2).addComponent(this.jLabelCIS)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jTextFieldCIT, -2, -1, -2).addComponent(this.jLabelCIT)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jTextFieldCVR, -2, -1, -2).addComponent(this.jLabelCVR)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jTextFieldCVS, -2, -1, -2).addComponent(this.jLabelCVS)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jTextFieldCVT, -2, -1, -2).addComponent(this.jLabelCVT)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jTextFieldCPR, -2, -1, -2).addComponent(this.jLabelCPR)).addGap(6, 6, 6).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jTextFieldCPS, -2, -1, -2).addComponent(this.jLabelCPS)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jTextFieldCPT, -2, -1, -2).addComponent(this.jLabelCPT)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jTextFieldCCB, -2, -1, -2).addComponent(this.jLabelCCB)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jTextFieldCPC, -2, -1, -2).addComponent(this.jLabelCPC)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jButtonChangeRegister).addComponent(this.jButton1)))).addGap(18, 18, 18).addComponent(this.jButtonSTPMCreset).addContainerGap(95, 32767)));
  }
  
  private void jTextFieldCPTActionPerformed(ActionEvent evt) {}
  
  private void jTextFieldCITActionPerformed(ActionEvent evt) {}
  
  private void jTextFieldCPSActionPerformed(ActionEvent evt) {}
  
  private void jButtonChangeRegisterActionPerformed(ActionEvent evt)
  {
      if(jTextFieldCCA.getText().equals("") && jTextFieldCCB.getText().equals("") && jTextFieldCIN.getText().equals("") &&
              jTextFieldCIR.getText().equals("") && jTextFieldCIS.getText().equals("") && jTextFieldCIT.getText().equals("") &&
              jTextFieldCPC.getText().equals("") && jTextFieldCPR.getText().equals("") && jTextFieldCPS.getText().equals("") &&
              jTextFieldCPT.getText().equals("") && jTextFieldCVR.getText().equals("") && jTextFieldCVS.getText().equals("") &&
              jTextFieldCVT.getText().equals(""))
      {
          JOptionPane.showMessageDialog(null, "No hay valores que ingresar", "Advertencia", JOptionPane.WARNING_MESSAGE);
      }
      else
      {
          for (int i = 0; i < 13; i++)
          {
              contenedor.put(Register.ADDR_CALIBRATION_CCA, jTextFieldCCA);
              contenedor.put(Register.ADDR_CALIBRATION_CIN, jTextFieldCIN);
              contenedor.put(Register.ADDR_CALIBRATION_CIR, jTextFieldCIR);
              contenedor.put(Register.ADDR_CALIBRATION_CIS, jTextFieldCIS);
              contenedor.put(Register.ADDR_CALIBRATION_CIT, jTextFieldCIT);
              contenedor.put(Register.ADDR_CALIBRATION_CVR, jTextFieldCVR);
              contenedor.put(Register.ADDR_CALIBRATION_CVS, jTextFieldCVS);
              contenedor.put(Register.ADDR_CALIBRATION_CVT, jTextFieldCVT);
              contenedor.put(Register.ADDR_CALIBRATION_CPR, jTextFieldCPR);
              contenedor.put(Register.ADDR_CALIBRATION_CPS, jTextFieldCPS);
              contenedor.put(Register.ADDR_CALIBRATION_CPT, jTextFieldCPT);
              contenedor.put(Register.ADDR_CALIBRATION_CCB, jTextFieldCCB);
              contenedor.put(Register.ADDR_CALIBRATION_CPC, jTextFieldCPC);
          }
          int count = 0;
          Iterator it = null;
          Set set = contenedor.entrySet();
          it = set.iterator();
          while (it.hasNext())
          {
              Map.Entry entry = (Map.Entry)it.next();
              JTextField value = (JTextField)entry.getValue();
              Register key = (Register)entry.getKey();
              if (value.getText().equals("")) 
              {
                  //System.out.println("Not have written values " + key.name());//Uso en ingles
                  System.out.println("No tiene valores escritos en " + key.name());//Uso en español
              }
              else
              {
                  count += writeRegister(key, value.getText());
              }
          }
          //JOptionPane.showMessageDialog(null, "Save Succesful " + count + " Registers");//Uso en ingles
          JOptionPane.showMessageDialog(null, "Se guardaron correctamente " + count + " Registros", "Información", JOptionPane.INFORMATION_MESSAGE);//Uso en español
          jTextFieldCCA.setText("");
          jTextFieldCCB.setText("");
          jTextFieldCIN.setText("");
          jTextFieldCIR.setText("");
          jTextFieldCIS.setText("");
          jTextFieldCIT.setText("");
          jTextFieldCPC.setText("");
          jTextFieldCPR.setText("");
          jTextFieldCPS.setText("");
          jTextFieldCPT.setText("");
          jTextFieldCVR.setText("");
          jTextFieldCVS.setText("");
          jTextFieldCVT.setText("");
      }
  }
  
  private void jButtonReadValuesActionPerformed(ActionEvent evt)
  {
    PortComunication port = new PortComunication();
    //if (((!port.getStatePort() ? 1 : 0) | (Meter.serialNumber == null ? 1 : 0) /*| Meter.serialNumber.equals("0000000000000000")*/) != 0)
    if((port.getStatePort() == false) | (Meter.serialNumber == null) | Meter.serialNumber.equals("0000000000000000"))//Solución al error de la línea de arriba
    {
        //JOptionPane.showMessageDialog(null, "Error Check the serial number, port or connection","Error",JOptionPane.ERROR_MESSAGE); 
        JOptionPane.showMessageDialog(null, "Error, Revise el número de serie, Puerto o conexión", "Error", JOptionPane.ERROR_MESSAGE);
    }
    else
    {
        String serialNumber = Meter.getSerialNumberCompl();
        Register regStart = Register.ADDR_CALIBRATION_CCA;
        int countWords = 13;
        byte actionRW = 3;
        byte[] responseMeter = rwRegisters.readRegister(PortComunication.DELAY300, serialNumber, regStart, countWords, actionRW);
        if (responseMeter != null) {
            settingValueCalibration(responseMeter);
        }
    }
  }
  
  private void jButtonExportTXTActionPerformed(ActionEvent evt)
  {
    listCalibrations.clear();
    for (ROW_TYPE roww : ROW_TYPE.values())
    {
      Object name = getRow(roww, COL_PHASE.PHASE_1);
      Object value = getRow(roww, COL_PHASE.PHASE_2);
      if (value.equals("---"))
      {
          //JOptionPane.showMessageDialog(null, "Error no hay Calibration data","Error",JOptionPane.ERROR_MESSAGE);//Uso en ingles
          JOptionPane.showMessageDialog(null, "No hay datos de Calibración", "Advertencia", JOptionPane.WARNING_MESSAGE);//Uso en español
        return;
      }
      listCalibrations.put(name, value);
    }
    exportarTxt(listCalibrations);
  }
  
  private void jButtonSTPMCresetActionPerformed(ActionEvent evt)
  {
    PortComunication port = new PortComunication();
    //if (((!port.getStatePort() ? 1 : 0) | (Meter.serialNumber == null ? 1 : 0)) != 0)
    if((port.getStatePort() == false) | (Meter.serialNumber == null) | Meter.serialNumber.equals("0000000000000000"))//Solución al error de la línea de arriba
    {
      JOptionPane.showMessageDialog(null, "Error, Revise el número de serie, Puerto o conexión", "Error", JOptionPane.ERROR_MESSAGE);
      //JOptionPane.showMessageDialog(null, "Error Check the serial number, port or connection","Error",JOptionPane.ERROR_MESSAGE);
    }
    else
    {
      ModbusUtil modb = new ModbusUtil();
      FlagsPasswords flagPassword = FlagsPasswords.METER_STPMC_RESET;
      String serialNumber = Meter.getSerialNumberCompl();
      boolean estado = modb.writeFlagsPassw(flagPassword, serialNumber);
      if (estado == true) {
        JOptionPane.showMessageDialog(null, "Correcto!!!", "Información", JOptionPane.INFORMATION_MESSAGE);
        //JOptionPane.showMessageDialog(null, "Good!!!","Message",JOptionPane.INFORMATION_MESSAGE);
      } else {
        JOptionPane.showMessageDialog(null, "Error, Revise el número de serie, Puerto o conexión", "Error", JOptionPane.ERROR_MESSAGE);
        //JOptionPane.showMessageDialog(null, "Error Check the serial number, port or connection","Error",JOptionPane.ERROR_MESSAGE);
      }
    }
  }
  
  private void jButton1ActionPerformed(ActionEvent evt)
  {
      PortComunication port = new PortComunication();
    //if (((!port.getStatePort() ? 1 : 0) | (Meter.serialNumber == null ? 1 : 0)) != 0)
    if((port.getStatePort() == false) | (Meter.serialNumber == null) | Meter.serialNumber.equals("0000000000000000"))//Solución al error de la línea de arriba
    {
      JOptionPane.showMessageDialog(null, "Error, Revise el número de serie, Puerto o conexión", "Error", JOptionPane.ERROR_MESSAGE);
      //JOptionPane.showMessageDialog(null, "Error Check the serial number, port or connection","Error",JOptionPane.ERROR_MESSAGE);
    }
    else
    {
        int validation = JOptionPane.showConfirmDialog(null, "¿Estás seguro?");
        //int validation = JOptionPane.showConfirmDialog(null, "Are you sure?","Question",JOptionPane.QUESTION_MESSAGE);
        if (validation == 0)
        {
            LinkedHashMap<Register, Integer> contenedorDefault = new LinkedHashMap();
            for (int i = 0; i < 13; i++)
            {
                contenedorDefault.put(Register.ADDR_CALIBRATION_CCA, Integer.valueOf(0));
                contenedorDefault.put(Register.ADDR_CALIBRATION_CIN, Integer.valueOf(128));
                contenedorDefault.put(Register.ADDR_CALIBRATION_CIR, Integer.valueOf(128));
                contenedorDefault.put(Register.ADDR_CALIBRATION_CIS, Integer.valueOf(128));
                contenedorDefault.put(Register.ADDR_CALIBRATION_CIT, Integer.valueOf(128));
                contenedorDefault.put(Register.ADDR_CALIBRATION_CVR, Integer.valueOf(128));
                contenedorDefault.put(Register.ADDR_CALIBRATION_CVS, Integer.valueOf(128));
                contenedorDefault.put(Register.ADDR_CALIBRATION_CVT, Integer.valueOf(128));
                contenedorDefault.put(Register.ADDR_CALIBRATION_CPR, Integer.valueOf(0));
                contenedorDefault.put(Register.ADDR_CALIBRATION_CPS, Integer.valueOf(0));
                contenedorDefault.put(Register.ADDR_CALIBRATION_CPT, Integer.valueOf(0));
                contenedorDefault.put(Register.ADDR_CALIBRATION_CCB, Integer.valueOf(0));
                contenedorDefault.put(Register.ADDR_CALIBRATION_CPC, Integer.valueOf(0));
            }
            int count = 0;
            Iterator it = null;
            Set set = contenedorDefault.entrySet();
            it = set.iterator();
            while (it.hasNext())
            {
                Map.Entry entry = (Map.Entry)it.next();
                Integer value = (Integer)entry.getValue();
                Register key = (Register)entry.getKey();
                count += writeRegister(key, value.toString());
            }
            //JOptionPane.showMessageDialog(null, "Save Succesful " + count + " Registers","Message", JOptionPane.INFORMATION_MESSAGE);
            JOptionPane.showMessageDialog(null, "Se guardaron correctamente " + count + " Registros", "Información", JOptionPane.INFORMATION_MESSAGE);
        }
        else if (((validation == 2 ? 1 : 0) | (validation == 1 ? 1 : 0)) != 0)
        {
            //JOptionPane.showMessageDialog(null, "Process canceled","Warning", JOptionPane.WARNING_MESSAGE);
            JOptionPane.showMessageDialog(null, "Cancelaste el proceso", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }        
    } 
  }
  
  public int writeRegister(Register regStart, String textField)
  {
    PortComunication port = new PortComunication();
    String tim = textField;
    int result = 0;
    if (tim.equals("")) {
      tim = "0";
    }
    //if (((!port.getStatePort() ? 1 : 0) | (Meter.serialNumber == null ? 1 : 0)) != 0)
    if((port.getStatePort() == false) | (Meter.serialNumber == null) | Meter.serialNumber.equals("0000000000000000"))//Solución al error de la línea de arriba
    {
      //JOptionPane.showMessageDialog(null, "Error Check the serial number, port or connection","Error",JOptionPane.ERROR_MESSAGE);
      JOptionPane.showMessageDialog(null, "Error, Revise el número de serie, Puerto o conexión", "Error", JOptionPane.ERROR_MESSAGE);
    }
    else
    {
      Conversions conv = new Conversions();
      ModbusUtil modb = new ModbusUtil();
      
      String serialNumber = Meter.getSerialNumberCompl();
      byte[] data = Conversions.intToByteArray(Integer.parseInt(tim));
      byte[] payload = new byte[2];
      payload[0] = data[2];
      payload[1] = data[3];
      
      int address = regStart.getAddress();
      int countWords = regStart.getWordLength();
      byte actionRW = 6;
      
      byte[] frame = modb.writeRegister(serialNumber, address, payload);
      port.write(frame, PortComunication.DELAY300);
      
      byte[] response = port.getByteBuffer();
      if (response == null)
      {
        //JOptionPane.showMessageDialog(null, "Error Check the serial number, port or connection","Error",JOptionPane.ERROR_MESSAGE);
        JOptionPane.showMessageDialog(null, "Error, Revise el número de serie, Puerto o conexión", "Error", JOptionPane.ERROR_MESSAGE);
      }
      else
      {
        int responseCountWords = countWords * 2;
        int responseCount = countWords * 2 - 3;
        int total = frame.length + responseCount;
        String resp = "";
        if (((response.length > responseCountWords ? 1 : 0) & (response.length >= total ? 1 : 0)) != 0)
        {
          String timeStampComplete = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
          
          byte[] receiveData = new byte[countWords * 2];
          System.arraycopy(response, 20, receiveData, 0, countWords * 2);
          
          String a = Conversions.byteArrayToHex(receiveData);
          System.out.println(timeStampComplete + " Received: " + a);
          result = 1;
        }
        else
        {
          //JOptionPane.showMessageDialog(null, "Error Check the serial number, port or connection","Error",JOptionPane.ERROR_MESSAGE);
          JOptionPane.showMessageDialog(null, "Error, Revise el número de serie, Puerto o conexión", "Error", JOptionPane.ERROR_MESSAGE);
        }
      }
    }
    return result;
  }
  
  public void exportarTxt(LinkedHashMap list)
  {
    JFileChooser fileChooser = new JFileChooser();
    File fileTxt = null;
    Writer writer = null;
    FileWriter fileWriter = null;
    int contadorLog = 0;
    File fileData = new File("");
    boolean cerrarArchivo = false;
    String timeStampCorto = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());
    String timeStampComplete = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
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
      Path path = Paths.get(fileData + "/", new String[0]);
      
      fileTxt = new File(fileData + "/" + timeStampHora + ".txt");
      if (Files.exists(path, new LinkOption[0])) {}
      writer = new BufferedWriter(new FileWriter(fileTxt));
      
      Iterator it = null;
      Set set = list.entrySet();
      it = set.iterator();
      while (it.hasNext())
      {
        Map.Entry entry = (Map.Entry)it.next();
        String value = entry.getValue().toString();
        String key = entry.getKey().toString();
        writer.write("\r\n");
        writer.write(key + " : " + value);
        writer.write("\r\n");
      }
      return;
    }
    catch (FileNotFoundException e)
    {
      e.printStackTrace();
      //JOptionPane.showMessageDialog(null, "File not found","Error",JOptionPane.ERROR_MESSAGE);
      JOptionPane.showMessageDialog(null, "No se encontro el archivo", "Error", JOptionPane.ERROR_MESSAGE);
    }
    catch (IOException e)
    {
      e.printStackTrace();
      //JOptionPane.showMessageDialog(null, "File not save","Error",JOptionPane.ERROR_MESSAGE);
      JOptionPane.showMessageDialog(null, "No se guardo el archivo", "Error", JOptionPane.ERROR_MESSAGE);
    }
    finally
    {
      try
      {
        if (writer != null)
        {
          writer.flush();
          writer.close();
          //JOptionPane.showMessageDialog(null, "File saved successfully","Information",JOptionPane.INFORMATION_MESSAGE);
          JOptionPane.showMessageDialog(null, "Archivo guardado correctamente!!!", "Información", JOptionPane.INFORMATION_MESSAGE);
        }
      }
      catch (IOException e)
      {
        e.printStackTrace();
        //JOptionPane.showMessageDialog(null, "File not save","Error",JOptionPane.ERROR_MESSAGE);
        JOptionPane.showMessageDialog(null, "No se guardo el archivo", "Error", JOptionPane.ERROR_MESSAGE);
      }
    }
  }
  
  public void settingValueCalibration(byte[] frame)
  {
    Conversions conv = new Conversions();
    
    ByteBuffer bb = ByteBuffer.allocate(frame.length);
    bb.put(frame);
    bb.position(0);
    
    int CCA = bb.getShort();
    int CIN = bb.getShort();
    int CIR = bb.getShort();
    int CIS = bb.getShort();
    int CIT = bb.getShort();
    int CVR = bb.getShort();
    int CVS = bb.getShort();
    int CVT = bb.getShort();
    int CPR = bb.getShort();
    int CPS = bb.getShort();
    int CPT = bb.getShort();
    int CCB = bb.getShort();
    int CPC = bb.getShort();
    
    double[] data = new double[13];
    data[0] = CCA;
    data[1] = CIN;
    data[2] = CIR;
    data[3] = CIS;
    data[4] = CIT;
    data[5] = CVR;
    data[6] = CVS;
    data[7] = CVT;
    data[8] = CPR;
    data[9] = CPS;
    data[10] = CPT;
    data[11] = CCB;
    data[12] = CPC;
    if (Double.compare(65535.0D, data[0]) == 0) {
      setRow(ROW_TYPE.ROW_CCA, COL_PHASE.PHASE_2, "----");
    } else {
      setRow(ROW_TYPE.ROW_CCA, COL_PHASE.PHASE_2, conv.doubleRound(data[0]));
    }
    if (Double.compare(65535.0D, data[1]) == 0) {
      setRow(ROW_TYPE.ROW_CIN, COL_PHASE.PHASE_2, "----");
    } else {
      setRow(ROW_TYPE.ROW_CIN, COL_PHASE.PHASE_2, conv.doubleRound(data[1]));
    }
    if (Double.compare(65535.0D, data[2]) == 0) {
      setRow(ROW_TYPE.ROW_CIR, COL_PHASE.PHASE_2, "----");
    } else {
      setRow(ROW_TYPE.ROW_CIR, COL_PHASE.PHASE_2, conv.doubleRound(data[2]));
    }
    if (Double.compare(65535.0D, data[3]) == 0) {
      setRow(ROW_TYPE.ROW_CIS, COL_PHASE.PHASE_2, "----");
    } else {
      setRow(ROW_TYPE.ROW_CIS, COL_PHASE.PHASE_2, conv.doubleRound(data[3]));
    }
    if (Double.compare(65535.0D, data[4]) == 0) {
      setRow(ROW_TYPE.ROW_CIT, COL_PHASE.PHASE_2, "----");
    } else {
      setRow(ROW_TYPE.ROW_CIT, COL_PHASE.PHASE_2, conv.doubleRound(data[4]));
    }
    if (Double.compare(65535.0D, data[5]) == 0) {
      setRow(ROW_TYPE.ROW_CVR, COL_PHASE.PHASE_2, "----");
    } else {
      setRow(ROW_TYPE.ROW_CVR, COL_PHASE.PHASE_2, conv.doubleRound(data[5]));
    }
    if (Double.compare(65535.0D, data[6]) == 0) {
      setRow(ROW_TYPE.ROW_CVS, COL_PHASE.PHASE_2, "----");
    } else {
      setRow(ROW_TYPE.ROW_CVS, COL_PHASE.PHASE_2, conv.doubleRound(data[6]));
    }
    if (Double.compare(65535.0D, data[7]) == 0) {
      setRow(ROW_TYPE.ROW_CVT, COL_PHASE.PHASE_2, "----");
    } else {
      setRow(ROW_TYPE.ROW_CVT, COL_PHASE.PHASE_2, conv.doubleRound(data[7]));
    }
    if (Double.compare(65535.0D, data[8]) == 0) {
      setRow(ROW_TYPE.ROW_CPR, COL_PHASE.PHASE_2, "----");
    } else {
      setRow(ROW_TYPE.ROW_CPR, COL_PHASE.PHASE_2, conv.doubleRound(data[8]));
    }
    if (Double.compare(65535.0D, data[9]) == 0) {
      setRow(ROW_TYPE.ROW_CPS, COL_PHASE.PHASE_2, "----");
    } else {
      setRow(ROW_TYPE.ROW_CPS, COL_PHASE.PHASE_2, conv.doubleRound(data[9]));
    }
    if (Double.compare(65535.0D, data[10]) == 0) {
      setRow(ROW_TYPE.ROW_CPT, COL_PHASE.PHASE_2, "----");
    } else {
      setRow(ROW_TYPE.ROW_CPT, COL_PHASE.PHASE_2, conv.doubleRound(data[10]));
    }
    if (Double.compare(65535.0D, data[11]) == 0) {
      setRow(ROW_TYPE.ROW_CCB, COL_PHASE.PHASE_2, "----");
    } else {
      setRow(ROW_TYPE.ROW_CCB, COL_PHASE.PHASE_2, conv.doubleRound(data[11]));
    }
    if (Double.compare(65535.0D, data[12]) == 0) {
      setRow(ROW_TYPE.ROW_CPC, COL_PHASE.PHASE_2, "----");
    } else {
      setRow(ROW_TYPE.ROW_CPC, COL_PHASE.PHASE_2, conv.doubleRound(data[12]));
    }
  }
  
  static enum ROW_TYPE
  {
    ROW_CCA(0, ""),  
    ROW_CIN(1, ""),  
    ROW_CIR(2, ""),  
    ROW_CIS(3, ""),  
    ROW_CIT(4, ""),  
    ROW_CVR(5, ""),  
    ROW_CVS(6, ""),  
    ROW_CVT(7, ""),  
    ROW_CPR(8, ""),  
    ROW_CPS(9, ""),  
    ROW_CPT(10, ""),  
    ROW_CCB(11, ""),  
    ROW_CPC(12, "");
    
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
    PHASE_1(0),  PHASE_2(1);
    
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
    case ROW_CCA: 
      defaultCalibration.setValueAt(value, type.getRow(), phase.getCol());
    }
    defaultCalibration.setValueAt(value, type.getRow(), phase.getCol());
  }
  
  private Object getRow(ROW_TYPE type, COL_PHASE phase)
  {
    Object ob = defaultCalibration.getValueAt(type.getRow(), phase.getCol());
    return ob;
  }
  
  private DefaultTableModel defaultCalibration = new DefaultTableModel(new Object[][] { { "CCA", "---" }, 
                                                                                        { "CIN", "---" }, 
                                                                                        { "CIR", "---" }, 
                                                                                        { "CIS", "---" }, 
                                                                                        { "CIT", "---" }, 
                                                                                        { "CVR", "---" }, 
                                                                                        { "CVS", "---" }, 
                                                                                        { "CVT", "---" }, 
                                                                                        { "CPR", "---" }, 
                                                                                        { "CPS", "---" }, 
                                                                                        { "CPT", "---" }, 
                                                                                        { "CCB", "---" }, 
                                                                                        { "CPC", "---" } }, 
                                                                        new String[] { "NOMBRE", "VALOR" })
                                                                        //new String[] { "NAME", "VALUE" })
  {
    Class[] types = { String.class, String.class };
    boolean[] canEdit = { false, false };
    
    public Class getColumnClass(int columnIndex)
    {
      return types[columnIndex];
    }
    
    public boolean isCellEditable(int rowIndex, int columnIndex)
    {
      return canEdit[columnIndex];
    }
  };
}