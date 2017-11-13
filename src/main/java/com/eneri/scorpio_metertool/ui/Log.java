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
    jScrollPane1 = new JScrollPane();
    jTextAreaLog = new JTextArea();
    jButtonClearLog = new JButton();
    jButtonExportLog = new JButton();
    
    jTextAreaLog.setColumns(20);
    jTextAreaLog.setLineWrap(true);
    jTextAreaLog.setRows(5);
    jTextAreaLog.setToolTipText("Área de log");
    jScrollPane1.setViewportView(jTextAreaLog);
    
    jButtonClearLog.setText("LIMPIAR LOG");
    //jButtonClearLog.setText("CLEAR LOG");
    jButtonClearLog.setToolTipText("Oprime el botón para limpiar el área del log");
    jButtonClearLog.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        Log.this.jButtonClearLogActionPerformed(evt);
      }
    });
    
    jButtonExportLog.setText("EXPORTAR LOG");
    //jButtonExportLog.setText("EXPORT LOG");
    jButtonExportLog.setToolTipText("Oprime el botón para exportar el log");
    jButtonExportLog.addActionListener(new ActionListener()
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
    jTextAreaLog.setText("");
  }
  
  private void jButtonExportLogActionPerformed(ActionEvent evt)
  {
    exportarTxt();
  }
  
  public void setLog(String data)
  {
    jTextAreaLog.append(data);
    DefaultCaret caret = (DefaultCaret)jTextAreaLog.getCaret();
    caret.setUpdatePolicy(2);
    jTextAreaLog.setCaretPosition(jTextAreaLog.getDocument().getLength());
  }
  
  public void exportarTxt()
  {
    timeStampCorto = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());
    timeStampComplete = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS").format(Calendar.getInstance().getTime());
    timeStampHora = new SimpleDateFormat("HH-mm-ss").format(Calendar.getInstance().getTime());
    
    JFileChooser f = new JFileChooser();
    f.setFileSelectionMode(1);
    f.showSaveDialog(null);
    fileData = f.getSelectedFile();
    if (fileData == null) {
      return;
    }
    fileData = new File(f.getSelectedFile() + "/" + "LogScorpioTool" + "/" + timeStampCorto + "/");
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
      
      writer.write(jTextAreaLog.getText()); return;
    }
    catch (FileNotFoundException e)
    {
      e.printStackTrace();
      JOptionPane.showMessageDialog(null, "No se encontro el archivo");
      //JOptionPane.showMessageDialog(null, "File not found","Error",JOptionPane.ERROR_MESSAGE);
      
    }
    catch (IOException e)
    {
      e.printStackTrace();
      JOptionPane.showMessageDialog(null, "No se guardo el archivo");
      //JOptionPane.showMessageDialog(null, "File not save","Error",JOptionPane.ERROR_MESSAGE);
    }
    finally
    {
      try
      {
        if (writer != null)
        {
          writer.flush();
          writer.close();
          JOptionPane.showMessageDialog(null, "Archivo guardado correctamente");
          //JOptionPane.showMessageDialog(null, "File saved succesfully","Error",JOptionPane.ERROR_MESSAGE);
        }
      }
      catch (IOException e)
      {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "No se guardo el archivo");
        //JOptionPane.showMessageDialog(null, "File not saved","Error",JOptionPane.ERROR_MESSAGE);
      }
    }
  }
}
