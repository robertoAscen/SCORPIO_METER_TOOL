/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eneri.scorpio_metertool.ui;

import com.eneri.scorpio_metertool.server.Request;
import com.eneri.scorpio_metertool.server.WorkConcentrator;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Roberto
 */
public class ConcentratorMeters
  extends JPanel
{
  private static DefaultTableModel defaultable;
  private String[] columnas;
  private Object[] data;
  private JScrollPane scrollpane;
  private static Container contentPane;
  private static StringBuilder builderstring;
  private JButton jButtonGetMeters;
  private JButton jButtoneExportListMetersToXML;
  private JLabel jLabelConcentratorIP;
  private JLabel jLabelNameMeterConcentrator;
  private JScrollPane jScrollPane1;
  private JTable jTableMetersConcentrator;
  private JTextField jTextFieldConcentratorIP;
  
  public ConcentratorMeters()
  {
    initComponents();
    this.jButtoneExportListMetersToXML.setEnabled(false);
  }
  
  private void initComponents()
  {
    jLabelNameMeterConcentrator = new JLabel();
    jLabelConcentratorIP = new JLabel();
    jTextFieldConcentratorIP = new JTextField();
    jButtonGetMeters = new JButton();
    jButtoneExportListMetersToXML = new JButton();
    jScrollPane1 = new JScrollPane();
    jTableMetersConcentrator = new JTable();
    
    setPreferredSize(new Dimension(950, 600));
    
    jLabelNameMeterConcentrator.setFont(new Font("Ubuntu", 1, 18));
    jLabelNameMeterConcentrator.setText("MEDIDORES DEL CONCENTRADOR");
    //jLabelNameMeterConcentrator.setText("CONCENTRATOR METERS");
    
    jLabelConcentratorIP.setText("IP del concentrador");
    //jLabelConcentratorIP.setText("Concentrator IP");
    
    jTextFieldConcentratorIP.setText("10.0.0.253");
    jTextFieldConcentratorIP.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        ConcentratorMeters.this.jTextFieldConcentratorIPActionPerformed(evt);
      }
    });
    jButtonGetMeters.setText("VER MEDIDORES");
    //jButtonGetMeters.setText("SHOW METERS");
    jButtonGetMeters.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        ConcentratorMeters.this.jButtonGetMetersActionPerformed(evt);
      }
    });
    jButtoneExportListMetersToXML.setText("EXPORTAR LISTA A XML");
    //jButtoneExportListMetersToXML.setText("EXPORT LIST TO XML");
    jButtoneExportListMetersToXML.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        ConcentratorMeters.this.jButtoneExportListMetersToXMLActionPerformed(evt);
      }
    });
    jTableMetersConcentrator.setModel(new DefaultTableModel(new Object[0][], new String[0]));
    
    jScrollPane1.setViewportView(jTableMetersConcentrator);
    
    GroupLayout layout = new GroupLayout(this);
    setLayout(layout);
    layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addGap(0, 322, 32767).addComponent(this.jLabelNameMeterConcentrator).addGap(318, 318, 318)).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jScrollPane1).addGroup(layout.createSequentialGroup().addComponent(this.jLabelConcentratorIP).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jTextFieldConcentratorIP, -2, 119, -2).addGap(0, 0, 32767)).addGroup(layout.createSequentialGroup().addComponent(this.jButtonGetMeters).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, 32767).addComponent(this.jButtoneExportListMetersToXML, -2, 180, -2))).addContainerGap()));
    
    layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(this.jLabelNameMeterConcentrator).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabelConcentratorIP).addComponent(this.jTextFieldConcentratorIP, -2, -1, -2)).addGap(18, 18, 18).addComponent(this.jScrollPane1, -1, 476, 32767).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jButtoneExportListMetersToXML).addComponent(this.jButtonGetMeters)).addContainerGap()));
  }
  
  private void jTextFieldConcentratorIPActionPerformed(ActionEvent evt) {}
  
  private void jButtonGetMetersActionPerformed(ActionEvent evt)
  {
    createFrame();
  }
  
  private void jButtoneExportListMetersToXMLActionPerformed(ActionEvent evt)
  {
    WorkConcentrator metCon = new WorkConcentrator();
    metCon.convertMetersTableToXML(this.jTableMetersConcentrator);
  }
  
  public void createFrame()
  {
    Request req = new Request();
    scrollpane = new JScrollPane();
    defaultable = new DefaultTableModel((Object[][])null, getColumnas());
    
    WorkConcentrator metConXml = new WorkConcentrator();
    String request = "<appCommand type=\"request\"><command name=\"getMeters\" /></appCommand>";
    
    boolean estate = req.requestServer(jTextFieldConcentratorIP.getText(), request);
    if (estate == true)
    {
      String response = req.getResponse();
      metConXml.readMetersConcentratorXML(jTextFieldConcentratorIP.getText(), response);
      jButtoneExportListMetersToXML.setEnabled(true);
    }
    else
    {
      //JOptionPane.showMessageDialog(null, "Connection Fail","Error",JOptionPane.ERROR_MESSAGE);
      JOptionPane.showMessageDialog(null, "Fallo de conexión");
    }
    jTableMetersConcentrator.setModel(defaultable);
    jTableMetersConcentrator.repaint();
    scrollpane.repaint();
    setVisible(true);
  }
  
  public DefaultTableModel getTable()
  {
    return defaultable;
  }
  
  public String[] getColumnas()
  {
    //String[] columna = { "NumeroSerie", "Fecha", "Tipo" };
    String[] columna = { "NúmeroSerie", "Fecha", "Tipo" };
    return columna;
  }
}
