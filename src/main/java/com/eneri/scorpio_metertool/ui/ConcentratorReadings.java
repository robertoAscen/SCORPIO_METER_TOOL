/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eneri.scorpio_metertool.ui;

import com.eneri.scorpio_metertool.server.Request;
import com.eneri.scorpio_metertool.server.WorkConcentrator;
import java.awt.Container;
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
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Roberto
 */
public class ConcentratorReadings
  extends JPanel
{
  private static DefaultTableModel defaultable;
  private String[] columnas;
  private Object[] data;
  private JScrollPane scrollpane;
  private static Container contentPane;
  private static StringBuilder builderstring;
  private JButton jButtonExportReadingsXml;
  private JButton jButtonGetReadings;
  private JLabel jLabelConcentratorReadingsTitle;
  private JLabel jLabelIpConcentrator;
  private JScrollPane jScrollPane1;
  private JTable jTableConcentratorReadings;
  private JTextField jTextFieldIpConcentraror;
  
  public ConcentratorReadings()
  {
    initComponents();
    jButtonExportReadingsXml.setEnabled(false);
  }
  
  private void initComponents()
  {
    jLabelConcentratorReadingsTitle = new JLabel();
    jLabelIpConcentrator = new JLabel();
    jTextFieldIpConcentraror = new JTextField();
    jScrollPane1 = new JScrollPane();
    jTableConcentratorReadings = new JTable();
    jButtonGetReadings = new JButton();
    jButtonExportReadingsXml = new JButton();
    
    setName("JPanelConcentratorReadings");
    
    jLabelConcentratorReadingsTitle.setFont(new Font("Ubuntu", 1, 18));
    jLabelConcentratorReadingsTitle.setText("LECTURAS DEL CONCENTRADOR");
    //jLabelConcentratorReadingsTitle.setText("CONCENTRATOR METER");
    
    jLabelIpConcentrator.setText("IP del concentrador:");
    //jLabelIpConcentrator.setText("Concentrator IP:");
    
    jTextFieldIpConcentraror.setText("10.0.0.254");
    jTextFieldIpConcentraror.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        ConcentratorReadings.this.jTextFieldIpConcentrarorActionPerformed(evt);
      }
    });
    jTableConcentratorReadings.setModel(new DefaultTableModel(new Object[0][], new String[0]));
    
    jScrollPane1.setViewportView(jTableConcentratorReadings);
    
    jButtonGetReadings.setText("VER LECTURAS");
    //jButtonGetReadings.setText("SHOW READS");
    jButtonGetReadings.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        ConcentratorReadings.this.jButtonGetReadingsActionPerformed(evt);
      }
    });
    //jButtonExportReadingsXml.setText("EXPORT READS TO XML");
    jButtonExportReadingsXml.setText("EXPORTAR LECTURAS A XML");
    jButtonExportReadingsXml.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        ConcentratorReadings.this.jButtonExportReadingsXmlActionPerformed(evt);
      }
    });
    GroupLayout layout = new GroupLayout(this);
    setLayout(layout);
    layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jScrollPane1, -1, 930, 32767).addGroup(layout.createSequentialGroup().addComponent(this.jLabelIpConcentrator).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jTextFieldIpConcentraror, -2, 119, -2).addGap(0, 709, 32767)).addGroup(layout.createSequentialGroup().addComponent(this.jButtonGetReadings).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, 32767).addComponent(this.jButtonExportReadingsXml))).addContainerGap()).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addContainerGap(-1, 32767).addComponent(this.jLabelConcentratorReadingsTitle).addGap(320, 320, 320)));
    
    layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(this.jLabelConcentratorReadingsTitle).addGap(1, 1, 1).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabelIpConcentrator).addComponent(this.jTextFieldIpConcentraror, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jScrollPane1, -1, 488, 32767).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jButtonExportReadingsXml).addComponent(this.jButtonGetReadings)).addContainerGap()));
  }
  
  private void jTextFieldIpConcentrarorActionPerformed(ActionEvent evt) {}
  
  private void jButtonGetReadingsActionPerformed(ActionEvent evt)
  {
    createFrame();
  }
  
  private void jButtonExportReadingsXmlActionPerformed(ActionEvent evt)
  {
    WorkConcentrator metCon = new WorkConcentrator();
    metCon.convertReadingsTableToXML(jTableConcentratorReadings);
  }
  
  public void createFrame()
  {
    Request req = new Request();
    JFrameMain f = (JFrameMain)SwingUtilities.getAncestorOfClass(JFrameMain.class, this);
    contentPane = f.getContentPane();
    scrollpane = new JScrollPane();
    defaultable = new DefaultTableModel((Object[][])null, getColumnas());
    
    WorkConcentrator metConXml = new WorkConcentrator();
    String request = "<appCommand type=\"request\">\n\t<command name=\"getReadings\" />\n</appCommand>";
    
    boolean estate = req.requestServer(jTextFieldIpConcentraror.getText(), request);
    if (estate == true)
    {
      String response = req.getResponse();
      metConXml.readReadingsConcentratorXML(jTextFieldIpConcentraror.getText(), response);
      jButtonExportReadingsXml.setEnabled(true);
    }
    else
    {
      JOptionPane.showMessageDialog(null, "Fallo de conexión");
      //JOptionPane.showMessageDialog(null, "Connection Fail","Error",JOptionPane.ERROR_MESSAGE);
    }
    jTableConcentratorReadings.setModel(defaultable);
    jTableConcentratorReadings.repaint();
    scrollpane.repaint();
    setVisible(true);
  }
  
  public void setDataDefaultTable(Object[] data)
  {
    defaultable.addRow(data);
  }
  
  public String[] getColumnas()
  {
    //String[] columna = { "NumeroSerie", "Dato", "Mensaje", "Fecha" };
    String[] columna = { "NúmeroSerie", "Dato", "Mensaje", "Fecha" };//Para cambiar a ingles
    return columna;
  }
}
