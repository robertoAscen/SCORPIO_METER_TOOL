/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eneri.scorpio_metertool.ui;

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
import java.util.Calendar;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.text.DefaultCaret;
import javax.swing.text.Document;

/**
 *
 * @author Roberto
 */
public class Log
  extends JPanel
{
  final JFileChooser fileChooser = new JFileChooser();
  public File fileTxt;
  public Writer writer;
  public FileWriter fileWriter;
  public int contadorLog;
  File fileData = new File("");
  boolean cerrarArchivo = false;
  boolean startRecord = false;
  String timeStampComplete;
  String timeStampCorto;
  String timeStampHora;
  private JButton jButtonClearLog;
  private JButton jButtonExportLog;
  private JScrollPane jScrollPane1;
  private JTextArea jTextAreaLog;
  
  public Log()
  {
    initComponents();
  }
  
  private void initComponents()
  {
    this.jScrollPane1 = new JScrollPane();
    this.jTextAreaLog = new JTextArea();
    this.jButtonClearLog = new JButton();
    this.jButtonExportLog = new JButton();
    
    this.jTextAreaLog.setColumns(20);
    this.jTextAreaLog.setLineWrap(true);
    this.jTextAreaLog.setRows(5);
    this.jScrollPane1.setViewportView(this.jTextAreaLog);
    
    //this.jButtonClearLog.setText("LIMPIAR LOG");
    this.jButtonClearLog.setText("CLEAR LOG");
    this.jButtonClearLog.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        Log.this.jButtonClearLogActionPerformed(evt);
      }
    });
    //this.jButtonExportLog.setText("EXPORTAR LOG");
    this.jButtonExportLog.setText("EXPORT LOG");
    this.jButtonExportLog.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        Log.this.jButtonExportLogActionPerformed(evt);
      }
    });
    GroupLayout layout = new GroupLayout(this);
    setLayout(layout);
    layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(19, 19, 19).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false).addGroup(layout.createSequentialGroup().addComponent(this.jButtonClearLog).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, 32767).addComponent(this.jButtonExportLog)).addComponent(this.jScrollPane1, -2, 911, -2)).addContainerGap(20, 32767)));
    
    layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(38, 38, 38).addComponent(this.jScrollPane1, -1, 522, 32767).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jButtonClearLog).addComponent(this.jButtonExportLog)).addContainerGap()));
  }
  
  private void jButtonClearLogActionPerformed(ActionEvent evt)
  {
    this.jTextAreaLog.setText("");
  }
  
  private void jButtonExportLogActionPerformed(ActionEvent evt)
  {
    exportarTxt();
  }
  
  public void setLog(String data)
  {
    this.jTextAreaLog.append(data);
    DefaultCaret caret = (DefaultCaret)this.jTextAreaLog.getCaret();
    caret.setUpdatePolicy(2);
    this.jTextAreaLog.setCaretPosition(this.jTextAreaLog.getDocument().getLength());
  }
  
  public void exportarTxt()
  {
    this.timeStampCorto = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());
    this.timeStampComplete = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS").format(Calendar.getInstance().getTime());
    this.timeStampHora = new SimpleDateFormat("HH-mm-ss").format(Calendar.getInstance().getTime());
    
    JFileChooser f = new JFileChooser();
    f.setFileSelectionMode(1);
    f.showSaveDialog(null);
    this.fileData = f.getSelectedFile();
    if (this.fileData == null) {
      return;
    }
    this.fileData = new File(f.getSelectedFile() + "/" + "LogScorpioTool" + "/" + this.timeStampCorto + "/");
    if (this.fileData.mkdirs()) {
      System.out.println("Multiple directories are created!");
    } else {
      System.out.println("Failed to create multiple directories!");
    }
    try
    {
      Path path = Paths.get(this.fileData + "/", new String[0]);
      this.fileTxt = new File(this.fileData + "/" + this.timeStampHora + ".txt");
      if (Files.exists(path, new LinkOption[0])) {}
      this.writer = new BufferedWriter(new FileWriter(this.fileTxt));
      
      this.writer.write(this.jTextAreaLog.getText()); return;
    }
    catch (FileNotFoundException e)
    {
      e.printStackTrace();
      //JOptionPane.showMessageDialog(null, "No se encontro el archivo");
      JOptionPane.showMessageDialog(null, "File not found","Error",JOptionPane.ERROR_MESSAGE);
      
    }
    catch (IOException e)
    {
      e.printStackTrace();
      //JOptionPane.showMessageDialog(null, "No se guardo el archivo");
      JOptionPane.showMessageDialog(null, "File not save","Error",JOptionPane.ERROR_MESSAGE);
    }
    finally
    {
      try
      {
        if (this.writer != null)
        {
          this.writer.flush();
          this.writer.close();
          //JOptionPane.showMessageDialog(null, "Archivo guardado correctamente");
          JOptionPane.showMessageDialog(null, "File saved succesfully","Error",JOptionPane.ERROR_MESSAGE);
        }
      }
      catch (IOException e)
      {
        e.printStackTrace();
        //JOptionPane.showMessageDialog(null, "No se guardo el archivo");
        JOptionPane.showMessageDialog(null, "File not saved","Error",JOptionPane.ERROR_MESSAGE);
      }
    }
  }
}
