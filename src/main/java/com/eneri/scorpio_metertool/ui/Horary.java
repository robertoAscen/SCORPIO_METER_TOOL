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
import com.eneri.scorpio_metertool.devices.rwRegisters;
import com.eneri.scorpio_metertool.hardwarelayer.ModbusUtil;
import com.eneri.scorpio_metertool.hardwarelayer.PortComunication;
import com.eneri.scorpio_metertool.time.DatePicker;
import com.eneri.scorpio_metertool.time.MeterHorary;
import com.toedter.calendar.JCalendar;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.PrintStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.ComboBoxEditor;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 *
 * @author Roberto
 */
public class Horary
  extends JPanel
{
  private static final long EPOCH_2K_ZERO = 946684800000L;
  private static Horary INSTANCE;
  private String datePattern = "dd-MM-yyyy hh:mm:ss";
  private SimpleDateFormat dateFormatter = new SimpleDateFormat(this.datePattern);
  JFrameMain f = (JFrameMain)SwingUtilities.getAncestorOfClass(JFrameMain.class, this);
  JCalendar fromCal = new JCalendar();
  public static LinkedHashMap<String, Integer> timeZoneList = new LinkedHashMap();
  public static LinkedHashMap<String, String> TimeZoneZoneList = new LinkedHashMap();
  Calendar calendar = new GregorianCalendar();
  TimeZone timeZone = this.calendar.getTimeZone();
  int countDST = 0;
  Calendar calStart = Calendar.getInstance();
  Calendar calEnd = Calendar.getInstance();
  TimeZone timezoneone;
  public String timeOfStartDST = "02:00:00";
  public String timeOfEndDST = "00:00:00";
  
  public Horary()
  {
    initComponents();
    getTimeZoneJCombobox();
    jButtonCalendarStart.setEnabled(false);
    jButtonCalendarEnd.setEnabled(false);
    jTextFieldStartHorary.setEnabled(false);
    jTextFieldEndHorary.setEnabled(false);
    jButtonReadHorary.setEnabled(false);
    timeRTC.start();
  }
  
  public void checkjTextFieldStartHorary()
  {
    SwingUtilities.invokeLater(new Runnable()
    {
      public void run()
      {
        Iterator it = null;
        Set set = Horary.TimeZoneZoneList.entrySet();
        it = set.iterator();
        while (it.hasNext())
        {
          Map.Entry entry = (Map.Entry)it.next();
          String value = entry.getValue().toString();
          String key = entry.getKey().toString();
          boolean estate = false;
          if (Horary.this.jTextFieldStartHorary.getText().toString().equals(""))
          {
              Horary.this.jTextFieldStartHorary.setBackground(Color.red);
              return;
          }
          if (key.equals(Horary.this.jComboBoxHoraryZone.getSelectedItem().toString()))
          {
            Date date = new Date();
            
            Horary.this.timezoneone = TimeZone.getTimeZone(value);
            try
            {
              date = Horary.this.dateFormatter.parse(Horary.this.jTextFieldStartHorary.getText().toString() + " " + Horary.this.timeOfStartDST);
              Horary.this.calStart.setTime(date);
            }
            catch (ParseException e) 
            {}
            if (!Horary.this.timezoneone.inDaylightTime(date)) 
            {
              Horary.this.jTextFieldStartHorary.setBackground(Color.red);
            }
            else 
            {
              Horary.this.jTextFieldStartHorary.setBackground(Color.green);
            }
            System.out.println("In daylight time:" + Horary.this.timezoneone.inDaylightTime(date) + date);
          }
        }
      }
    });
  }
  
  public void checkjTextFieldEndHorary()
  {
    SwingUtilities.invokeLater(new Runnable()
    {
      public void run()
      {
        Iterator it = null;
        Set set = Horary.TimeZoneZoneList.entrySet();
        it = set.iterator();
        while (it.hasNext())
        {
          Map.Entry entry = (Map.Entry)it.next();
          String value = entry.getValue().toString();
          String key = entry.getKey().toString();
          boolean estate = false;
          if (Horary.this.jTextFieldEndHorary.getText().toString().equals(""))
          {
            Horary.this.jTextFieldEndHorary.setBackground(Color.red);
            return;
          }
          if (key.equals(Horary.this.jComboBoxHoraryZone.getSelectedItem().toString()))
          {
            Date date = new Date();
            Horary.this.timezoneone = TimeZone.getTimeZone(value);
            try
            {
              date = Horary.this.dateFormatter.parse(Horary.this.jTextFieldEndHorary.getText().toString() + " " + Horary.this.timeOfEndDST);
              Horary.this.calEnd.setTime(date);
            }
            catch (ParseException e) {}
            if (!Horary.this.timezoneone.inDaylightTime(date)) {
              Horary.this.jTextFieldEndHorary.setBackground(Color.red);
            } else {
              Horary.this.jTextFieldEndHorary.setBackground(Color.green);
            }
            System.out.println("In daylight time:" + Horary.this.timezoneone.inDaylightTime(date) + date);
          }
        }
      }
    });
  }
  
  public void getTimeZoneJCombobox()
  {
    String[] allTimeZones = TimeZone.getAvailableIDs();
    Arrays.sort(allTimeZones);
    for (MeterHorary reg : MeterHorary.values()) {
      for (int i = 0; i < allTimeZones.length; i++)
      {
        TimeZone tz = TimeZone.getTimeZone(allTimeZones[i]);
        int offset = tz.getRawOffset();
        String text = String.format("%s%d%d", new Object[] { offset >= 0 ? "+" : "", Integer.valueOf(offset / 3600000), Integer.valueOf(offset / 60000 % 60) });
        if (reg.getTime().equals(text))
        {
          this.jComboBoxHoraryZone.addItem(reg.getTimeNameOnly() + " " + allTimeZones[i]);
          timeZoneList.put(reg.getTimeNameOnly() + " " + allTimeZones[i], Integer.valueOf(reg.getValue()));
          TimeZoneZoneList.put(reg.getTimeNameOnly() + " " + allTimeZones[i], allTimeZones[i]);
        }
      }
    }
    AutoCompleteDecorator.decorate(this.jComboBoxHoraryZone);
  }
  
  public Object stringToValue(String text)
    throws ParseException
  {
    return dateFormatter.parseObject(text);
  }
  
  public String valueToString(Object value)
    throws ParseException
  {
    if (value != null)
    {
      Calendar cal = (Calendar)value;
      return dateFormatter.format(cal.getTime());
    }
    return "";
  }
  
  private void initComponents()
  {
    jLabelNamePanel = new JLabel();
    jComboBoxHoraryZone = new JComboBox();
    jButtonReadHorary = new JButton();
    jButtonSaveHorary = new JButton();
    jButtonLocalSynchronization = new JButton();
    jLabelStartHorary = new JLabel();
    jLabelEndHorary = new JLabel();
    jLabelHoraryZone = new JLabel();
    jButtonCalendarStart = new JButton();
    jTextFieldStartHorary = new JTextField();
    jTextFieldEndHorary = new JTextField();
    jButtonCalendarEnd = new JButton();
    jTextFieldReadWriteUTCTime = new JTextField();
    jButton1 = new JButton();
    jLabelUTCTime = new JLabel();
    jLabelTimeUTC = new JLabel();
    
    setPreferredSize(new Dimension(950, 600));
    
    jTextFieldReadWriteUTCTime.setToolTipText("Hora global del medidor");
    
    jLabelNamePanel.setFont(new Font("Tahoma", 1, 18));
    jLabelNamePanel.setText("HORARIOS");
    //jLabelNamePanel.setText("Schedules");
    
    jComboBoxHoraryZone.setEditable(true);
    jComboBoxHoraryZone.setToolTipText("Selecciona una zona horaria");
    jComboBoxHoraryZone.addItemListener(new ItemListener()
    {
      public void itemStateChanged(ItemEvent evt)
      {
        Horary.this.jComboBoxHoraryZoneItemStateChanged(evt);
      }
    });
    jComboBoxHoraryZone.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        Horary.this.jComboBoxHoraryZoneActionPerformed(evt);
      }
    });
    /*jButtonReadHorary.setText("LEER");
    jButtonReadHorary.setToolTipText("Oprime el botón para obtener los valores");
    //jButtonReadHorary.setText("READ");
    jButtonReadHorary.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        Horary.this.jButtonReadHoraryActionPerformed(evt);
      }
    });*/
    jButtonSaveHorary.setText("Guardar");
    jButtonSaveHorary.setToolTipText("Oprime el botón para guardar los valores establecidos");
    //jButtonSaveHorary.setText("Save");
    jButtonSaveHorary.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        Horary.this.jButtonSaveHoraryActionPerformed(evt);
      }
    });
    jButtonLocalSynchronization.setText("SINCRONIZAR RELOJ MEDIDOR");
    jButtonLocalSynchronization.setToolTipText("Oprime el botón para sincronizar el tiempo global en el medidor");
    //jButtonLocalSynchronization.setText("SYNCHRONIZE CLOCK GLOBAL");
    jButtonLocalSynchronization.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        Horary.this.jButtonLocalSynchronizationActionPerformed(evt);
      }
    });
    jLabelStartHorary.setText("INICIO DE HORARIO DE VERANO");
    //jLabelStartHorary.setText("SUMMER TIME STARUP");
    
    jLabelEndHorary.setText("FIN DE HORARIO DE VERANO");
    //jLabelEndHorary.setText("SUMMER TIME END");
    
    jLabelHoraryZone.setText("ZONA HORARIA");
    //jLabelHoraryZone.setText("TIME ZONE");
    
    jButtonCalendarStart.setText("CALENDARIO");
    jButtonCalendarStart.setToolTipText("Oprime el valor para establecer el inicio del horario de verano");
    //jButtonCalendarStart.setText("CALENDAR");
    jButtonCalendarStart.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        Horary.this.jButtonCalendarStartActionPerformed(evt);
      }
    });
    
    jTextFieldStartHorary.setToolTipText("Fecha de inicio de horario de verano para la zona horaria seleccionada");
    jTextFieldStartHorary.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        Horary.this.jTextFieldStartHoraryActionPerformed(evt);
      }
    });
    
    jTextFieldStartHorary.addKeyListener(new KeyAdapter()
    {
      public void keyReleased(KeyEvent evt)
      {
        Horary.this.jTextFieldStartHoraryKeyReleased(evt);
      }
    });
    
    jTextFieldEndHorary.setToolTipText("Fecha de fin de horario de verano para la zona horaria seleccionada");
    jTextFieldEndHorary.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        Horary.this.jTextFieldEndHoraryActionPerformed(evt);
      }
    });
    
    jTextFieldEndHorary.addKeyListener(new KeyAdapter()
    {
      public void keyReleased(KeyEvent evt)
      {
        Horary.this.jTextFieldEndHoraryKeyReleased(evt);
      }
    });
    
    jButtonCalendarEnd.setText("CALENDARIO");
    //jButtonCalendarEnd.setText("CALENDAR");
    jButtonCalendarEnd.setToolTipText("Oprime el botón para establer el fin del horario de verano");
    jButtonCalendarEnd.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        Horary.this.jButtonCalendarEndActionPerformed(evt);
      }
    });
    jButton1.setText("LEER RELOJ MEDIDOR");
    //jButton1.setText("READ CLOCK GLOBAL");
    jButton1.setToolTipText("Oprime el botón para leer el reloj global del medidor");
    jButton1.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        Horary.this.jButton1ActionPerformed(evt);
      }
    });
    
    jLabelTimeUTC.setText("HORA GLOBAL:");
    //jLabelTimeUTC.setText("GLOBAL TIME:");
    
    jLabelUTCTime.setText("HORA DEL MEDIDOR");
    //jLabelUTCTime.setText("GLOBAL TIME");   
    
    GroupLayout layout = new GroupLayout(this);
    setLayout(layout);
    layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(409, 409, 409).addComponent(this.jLabelNamePanel)).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addGap(192, 192, 192).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabelEndHorary, GroupLayout.Alignment.TRAILING).addComponent(this.jLabelStartHorary, GroupLayout.Alignment.TRAILING).addComponent(this.jLabelHoraryZone, GroupLayout.Alignment.TRAILING)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false).addComponent(this.jTextFieldStartHorary).addComponent(this.jTextFieldEndHorary).addComponent(this.jComboBoxHoraryZone, -2, 215, -2))).addGroup(layout.createSequentialGroup().addComponent(this.jLabelUTCTime).addGap(18, 18, 18).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabelTimeUTC).addComponent(this.jTextFieldReadWriteUTCTime, -2, 215, -2)))).addGap(18, 18, 18).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jButtonCalendarStart).addComponent(this.jButtonCalendarEnd))).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addComponent(this.jButton1, -2, 188, -2).addGap(30, 30, 30).addComponent(this.jButtonLocalSynchronization, -2, 215, -2).addGap(9, 9, 9)))).addGroup(layout.createSequentialGroup().addGap(363, 363, 363).addComponent(this.jButtonReadHorary, -2, 88, -2).addGap(18, 18, 18).addComponent(this.jButtonSaveHorary, -1, 88, -1).addGap(138, 138, 138)))).addContainerGap(255, 32767)));
    
    layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(22, 22, 22).addComponent(this.jLabelNamePanel).addGap(39, 39, 39).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jComboBoxHoraryZone, -2, -1, -2).addComponent(this.jLabelHoraryZone)).addGap(48, 48, 48).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jTextFieldStartHorary, -2, -1, -2).addComponent(this.jLabelStartHorary).addComponent(this.jButtonCalendarStart)).addGap(52, 52, 52).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jTextFieldEndHorary, -2, -1, -2).addComponent(this.jLabelEndHorary).addComponent(this.jButtonCalendarEnd)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jButtonSaveHorary).addComponent(this.jButtonReadHorary)).addGap(115, 115, 115).addComponent(this.jLabelTimeUTC).addGap(32, 32, 32).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jTextFieldReadWriteUTCTime, -2, -1, -2).addComponent(this.jLabelUTCTime)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jButtonLocalSynchronization).addComponent(this.jButton1)).addContainerGap(105, 32767)));
  }
  
  private void jButtonLocalSynchronizationActionPerformed(ActionEvent evt)
  {
    EventQueue.invokeLater(new Runnable()
    {
      public void run()
      {
          PortComunication port = new PortComunication();
          if((port.getStatePort() == false) | (Meter.serialNumber == null) | Meter.serialNumber.equals("0000000000000000"))//Solución al error de la línea de arriba
          {
              JOptionPane.showMessageDialog(null, "Error, Revise el número de serie, Puerto o conexión", "Error", JOptionPane.ERROR_MESSAGE);
              //JOptionPane.showMessageDialog(null, "Error Check serial number, Port or connection","Error",JOptionPane.ERROR_MESSAGE);
          }
          else 
          {
              //Horary.this.swingActionSyncRTC(Horary.this.jTextFieldReadWriteUTCTime.getText());
              System.out.println(jLabelTimeUTC.getText().substring(13));
              Horary.this.swingActionSyncRTC(Horary.this.jLabelTimeUTC.getText().substring(13));
          }
      }
    });
  }
  
  private void jButtonReadHoraryActionPerformed(ActionEvent evt)
  {
    PortComunication port = new PortComunication();
    ScorpioMeter scorp = new ScorpioMeter();
    //if (((!port.getStatePort() ? 1 : 0) | (Meter.serialNumber == null ? 1 : 0) /*| Meter.serialNumber.equals("0000000000000000")*/) != 0)
    if((port.getStatePort() == false) | (Meter.serialNumber == null) | Meter.serialNumber.equals("0000000000000000"))//Solución al error de la línea de arriba
    {
      JOptionPane.showMessageDialog(null, "Error, Revise el número de serie, Puerto o conexión", "Error", JOptionPane.ERROR_MESSAGE);
      //JOptionPane.showMessageDialog(null, "Error Check serial number, Port or connection","Error",JOptionPane.ERROR_MESSAGE);
    }
    else
    {
      String serialNumber = Meter.getSerialNumberCompl();
      Register regStart = null;
      int countWords = 0;
      byte actionRW = 0;
      for (int i = 0; i < 3; i++)
      {
        switch (i)
        {
        case 0: 
          regStart = Register.ZONA_HORARIA;
          break;
        case 1: 
          regStart = Register.INICIO_HORARIO_VERANO;
          break;
        case 2: 
          regStart = Register.FIN_HORARIO_VERANO;
        }
        countWords = regStart.getWordLength();
        actionRW = 3;
        byte[] response = rwRegisters.readRegister(PortComunication.DELAY300, serialNumber, regStart, countWords, actionRW);
        if (response != null)
        {
          String resp = "";
          try
          {
            resp = ScorpioMeter.convert(response, regStart);
          }
          catch (Exception e) {}
          int responseInt = Integer.parseInt(resp);
          StringBuilder sb = new StringBuilder();
          Iterator it = null;
          switch (regStart)
          { 
              case ZONA_HORARIA: 
                  it = null;
                  Set set = timeZoneList.entrySet();
                  it = set.iterator();
              case INICIO_HORARIO_VERANO: 
                  //break;
              case FIN_HORARIO_VERANO:
                  while (it.hasNext())
                  {
                      Map.Entry entry = (Map.Entry)it.next();
                      int value = Integer.parseInt(entry.getValue().toString());
                      String key = entry.getKey().toString();
                      if (value == responseInt)
                      {
                          jComboBoxHoraryZone.getEditor().setItem(key);
                      }
                      else
                      {
                          //continue;
                          short month = (short) (responseInt >> 11 & 0xF);
                          short day = (short)(responseInt >> 5 & 0x7);
                          sb.append("DoW: ");
                          sb.append(day);
                          sb.append(" Mes: ");
                          //sb.append(" Month: ");
                          sb.append(month);
                          jTextFieldStartHorary.setText(sb.toString());
                          //break;
                          
                          month = (short)(responseInt >> 11 & 0xF);
                          day = (short)(responseInt >> 5 & 0x7);
                          sb.append("DoW: ");
                          sb.append(day);
                          sb.append(" Mes: ");
                          //sb.append(" Month: ");
                          sb.append(month);
                          jTextFieldEndHorary.setText(sb.toString());
                      }
                  }
                  break;
          }
        }
        PortComunication.threadDelay(100L);
      }
    }
  }
  
  public byte[] getStartDST()
  {
    Conversions conv = new Conversions();
    int dataAlllll = 0;
    String[] date = jTextFieldStartHorary.getText().toString().split("-");
    
    int datoD = Integer.parseInt(date[0]);
    int datoM = Integer.parseInt(date[1]);
    int datoY = Integer.parseInt(date[2]);
    
    int weekNumber = calStart.get(4);
    int dayOfWeek = calStart.get(7);
    
    String[] timeStart = timeOfStartDST.split(":");
    
    int datoHour = Integer.parseInt(timeStart[0]);
    int datoMin = Integer.parseInt(timeStart[1]);
    int datoSec = Integer.parseInt(timeStart[2]);
    
    dataAlllll = 0x8000 | (datoM & 0xF) << 11 | (weekNumber & 0x7) << 8 | (dayOfWeek & 0x7) << 5 | (datoHour & 0x1F) << 0;
    dataAlllll &= 0xFFFFFFFF;
    byte[] datosStart = Conversions.intToByteArray(dataAlllll);
    return datosStart;
  }
  
  public byte[] getEndDST()
  {
    Conversions conv = new Conversions();
    int dataAlllll = 0;
    String[] date = jTextFieldEndHorary.getText().toString().split("-");
    
    int datoD = Integer.parseInt(date[0]);
    int datoM = Integer.parseInt(date[1]);
    int datoY = Integer.parseInt(date[2]);
    
    int weekNumber = calEnd.get(4);
    int dayOfWeek = calEnd.get(7);
    
    String[] timeStart = timeOfEndDST.split(":");
    
    int datoHour = Integer.parseInt(timeStart[0]);
    int datoMin = Integer.parseInt(timeStart[1]);
    int datoSec = Integer.parseInt(timeStart[2]);
    dataAlllll = 0x8000 | (datoM & 0xF) << 11 | (weekNumber & 0x7) << 8 | (dayOfWeek & 0x7) << 5 | (datoHour & 0x1F) << 0;
    dataAlllll &= 0xFFFFFFFF;
    byte[] datosEnd = Conversions.intToByteArray(dataAlllll);
    return datosEnd;
  }
  
  private void jButtonSaveHoraryActionPerformed(ActionEvent evt)
  {
    Pattern p = Pattern.compile("\\d\\d-\\d\\d-\\d\\d\\d\\d");
    Matcher m = p.matcher(jTextFieldStartHorary.getText());
    Matcher m2 = p.matcher(jTextFieldEndHorary.getText());
    if ((jTextFieldStartHorary.getText().isEmpty() | jTextFieldEndHorary.getText().isEmpty() | jTextFieldStartHorary.getText().length() != 10 | !m.find() | !m2.find() | jTextFieldEndHorary.getText().length() != 10))
    {
      JOptionPane.showMessageDialog(null, "Debes seleccionar ZONA HORARIA, el INICIO Y FIN de Horario de Verano", "Advertencia", JOptionPane.WARNING_MESSAGE);
      //JOptionPane.showMessageDialog(null, "You most select TIME ZONE, start and end the summer time ","Information",JOptionPane.INFORMATION_MESSAGE);
      return;
    }//Validar que no guarde el horario de verano si es que no esta correcto.
    if(jTextFieldStartHorary.getBackground().equals(Color.red) | jTextFieldEndHorary.getBackground().equals(Color.red))
    {
        JOptionPane.showMessageDialog(null, "Debes seleccionar valores validos de inicio y fin de horario de verano", "Advertencia", JOptionPane.WARNING_MESSAGE);
    }
    else
    {
        System.out.println(jTextFieldStartHorary.getBackground());
        saveHoraryZone();
        PortComunication.threadDelay(200L);
        byte[] startDST = getStartDST();
        saveStartEndDST(startDST, Register.INICIO_HORARIO_VERANO);
        PortComunication.threadDelay(200L);
        byte[] endDST = getEndDST();
        saveStartEndDST(endDST, Register.FIN_HORARIO_VERANO);         
    }    
  }
  
  public void saveStartEndDST(byte[] data, Register regStart)
  {
    PortComunication port = new PortComunication();
    Conversions conv = new Conversions();
    ModbusUtil modb = new ModbusUtil();
    if (((!port.getStatePort() ? 1 : 0) | (Meter.serialNumber == null ? 1 : 0)) != 0)
    {
      JOptionPane.showMessageDialog(null, "Error, Revise el número de serie, Puerto o conexión", "Error", JOptionPane.ERROR_MESSAGE);
      //JOptionPane.showMessageDialog(null, "Error Check serial numer, Port or connection","Error",JOptionPane.ERROR_MESSAGE);
    }
    else
    {
      String serialNumber = Meter.getSerialNumberCompl();
      byte[] payload = new byte[2];
      payload[0] = data[2];
      payload[1] = data[3];
      int address = regStart.getAddress();
      int countWords = regStart.getWordLength();
      byte actionRW = 6;
      
      byte[] frame = modb.writeRegister(serialNumber, address, payload);
      port.write(frame, PortComunication.DELAY400);
      
      byte[] response = port.getByteBuffer();
      if (response == null)
      {
        JOptionPane.showMessageDialog(null, "Error Revise el número de serie, Puerto o conexión", "Error", JOptionPane.ERROR_MESSAGE);
        //JOptionPane.showMessageDialog(null, "Error Check serial numer, Port or connection","Error",JOptionPane.ERROR_MESSAGE);
      }
      else
      {
        this.countDST += 1;
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
          //System.out.println("Guardado Exitosamente");
          System.out.println("Saved succesfully");
          if (countDST == 2)
          {
            JOptionPane.showMessageDialog(null, "Guardado Exitosamente!!!", "Información", JOptionPane.INFORMATION_MESSAGE);
            //JOptionPane.showMessageDialog(null, "Saved succesfully","Information",JOptionPane.INFORMATION_MESSAGE);
            this.countDST = 0;
          }
        }
        else
        {
          JOptionPane.showMessageDialog(null, "Error, Revise el número de serie, Puerto o conexión", "Error", JOptionPane.ERROR_MESSAGE);
          //JOptionPane.showMessageDialog(null, "Error Check serial number, Port or connection","Error",JOptionPane.ERROR_MESSAGE);
        }
      }
    }
  }
  
  public void saveHoraryZone()
  {
    PortComunication port = new PortComunication();
    if (((!port.getStatePort() ? 1 : 0) | (Meter.serialNumber == null ? 1 : 0)) != 0)
    {
      JOptionPane.showMessageDialog(null, "Error, Revise el número de serie, Puerto o conexión", "Error", JOptionPane.ERROR_MESSAGE);
      //JOptionPane.showMessageDialog(null, "Error Check serial number, Port or connection","Error",JOptionPane.ERROR_MESSAGE);
    }
    else
    {
      String tim = jComboBoxHoraryZone.getSelectedItem().toString();
      Conversions conv = new Conversions();
      ModbusUtil modb = new ModbusUtil();
      Iterator it = null;
      Set set = timeZoneList.entrySet();
      it = set.iterator();
      while (it.hasNext())
      {
        Map.Entry entry = (Map.Entry)it.next();
        int value = Integer.parseInt(entry.getValue().toString());
        String key = entry.getKey().toString();
        if (key.equals(tim))
        {
          String serialNumber = Meter.getSerialNumberCompl();
          byte[] data = Conversions.intToByteArray(value);
          byte[] payload = new byte[2];
          payload[0] = data[2];
          payload[1] = data[3];
          Register regStart = Register.ZONA_HORARIA;
          int address = regStart.getAddress();
          int countWords = regStart.getWordLength();
          byte actionRW = 6;
          
          byte[] frame = modb.writeRegister(serialNumber, address, payload);
          port.write(frame, PortComunication.DELAY400);
          
          byte[] response = port.getByteBuffer();
          if (response == null)
          {
            JOptionPane.showMessageDialog(null, "Error, Revise el número de serie, Puerto o conexión", "Error", JOptionPane.ERROR_MESSAGE);
            //JOptionPane.showMessageDialog(null, "Error Check serial number, Port or connection","Error",JOptionPane.ERROR_MESSAGE);
            return;
          }
          int responseCountWords = countWords * 2;
          int responseCount = countWords * 2 - 3;
          int total = frame.length + responseCount;
          if (((response.length > responseCountWords ? 1 : 0) & (response.length >= total ? 1 : 0)) != 0)
          {
            String timeStampComplete = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
            
            byte[] receiveData = new byte[countWords * 2];
            System.arraycopy(response, 20, receiveData, 0, countWords * 2);
            
            String a = Conversions.byteArrayToHex(receiveData);
            System.out.println(timeStampComplete + " Received: " + a);
          }
          else
          {
            JOptionPane.showMessageDialog(null, "Error, Revise el número de serie, Puerto o conexión", "Error", JOptionPane.ERROR_MESSAGE);
            //JOptionPane.showMessageDialog(null, "Error Check serial number, Port or connection","Error",JOptionPane.ERROR_MESSAGE);
          }
        }
      }
    }
  }
  
  private void jButtonCalendarStartActionPerformed(ActionEvent evt)
  {
      jTextFieldStartHorary.setEnabled(true);
      jTextFieldStartHorary.setText(new DatePicker(f, jComboBoxHoraryZone.getSelectedItem().toString()).setPickedDate());
      checkjTextFieldStartHorary();
  }
  
  private void jComboBoxHoraryZoneActionPerformed(ActionEvent evt)
  {
    jButtonCalendarStart.setEnabled(true);
    jButtonCalendarEnd.setEnabled(true);
  }
  
  private void jButtonCalendarEndActionPerformed(ActionEvent evt)
  {
      jTextFieldEndHorary.setEnabled(true);
      jTextFieldEndHorary.setText(new DatePicker(f, jComboBoxHoraryZone.getSelectedItem().toString()).setPickedDate());
      checkjTextFieldEndHorary();
  }
  
  private void jTextFieldStartHoraryActionPerformed(ActionEvent evt) {}
  
  private void jTextFieldEndHoraryActionPerformed(ActionEvent evt) {}
  
  private void jTextFieldStartHoraryKeyReleased(KeyEvent evt)
  {
    checkjTextFieldStartHorary();
  }
  
  private void jTextFieldEndHoraryKeyReleased(KeyEvent evt)
  {
    checkjTextFieldEndHorary();
  }
  
  private void jComboBoxHoraryZoneItemStateChanged(ItemEvent evt) {}
  
  private void jButton1ActionPerformed(ActionEvent evt)
  {
    PortComunication port = new PortComunication();
    ScorpioMeter scorp = new ScorpioMeter();
    if (((!port.getStatePort() ? 1 : 0) | (Meter.serialNumber == null ? 1 : 0) /*| Meter.serialNumber.equals("0000000000000000")*/) != 0)
    {
      JOptionPane.showMessageDialog(null, "Error, Revise el número de serie, Puerto o conexión", "Error", JOptionPane.ERROR_MESSAGE);
      //JOptionPane.showMessageDialog(null, "Error Check serial number, Port or connection","Error",JOptionPane.ERROR_MESSAGE);
    }
    else
    {
      String serialNumber = Meter.getSerialNumberCompl();
      Register regStart = null;
      int countWords = 0;
      byte actionRW = 0;
      
      regStart = Register.FECHA;
      countWords = regStart.getWordLength();
      actionRW = 3;
      byte[] response = rwRegisters.readRegister(PortComunication.DELAY300, serialNumber, regStart, countWords, actionRW);
      if (response != null)
      {
        String resp = "";
        try
        {
          resp = ScorpioMeter.convert(response, regStart);
        }
        catch (Exception e) {}
        jTextFieldReadWriteUTCTime.setText(resp);
      }
    }
  }
  
  private void swingActionSyncRTC(String dateTime)
  {
    PortComunication port = new PortComunication();
    long seconds = 0L;
    if ((dateTime == null) || (dateTime.equals("")))
    {
      seconds = (System.currentTimeMillis() + 600L - 946684800000L) / 1000L;
    }
    else
    {
      SimpleDateFormat f = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
      f.setTimeZone(TimeZone.getTimeZone("UTC"));
      try
      {
        Date date = f.parse(dateTime);
        seconds = date.getTime();
        seconds = (seconds - 946684800000L) / 1000L;
      }
      catch (Exception e) {}
    }
    System.out.println(seconds);
    
    byte[] payload = new byte[4];
    
    long byteValue = seconds & 0xFFFFFFFFFF000000L;
    byteValue >>= 24;
    payload[0] = ((byte)(int)byteValue);
    
    byteValue = seconds & 0xFF0000;
    byteValue >>= 16;
    payload[1] = ((byte)(int)byteValue);
    
    byteValue = seconds & 0xFF00;
    byteValue >>= 8;
    payload[2] = ((byte)(int)byteValue);
    
    byteValue = seconds & 0xFF;
    payload[3] = ((byte)(int)byteValue);
    
    String serialNumber = Meter.getSerialNumberCompl();
    Register regStart = Register.FECHA;
    int countWords = regStart.getWordLength();
    byte actionRW = 16;
    rwRegisters.writeMultipleRegister(PortComunication.DELAY300, serialNumber, regStart, countWords, payload, actionRW);
  }
  
  ActionListener taskRTC = new ActionListener()
  {
    public void actionPerformed(ActionEvent e)
    {
      SimpleDateFormat f = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
      f.setTimeZone(TimeZone.getTimeZone("UTC"));
      String timeutc = f.format(new Date());
      
      Horary.this.jLabelTimeUTC.setText("HORA GLOBAL: " + timeutc);
      //Horary.this.jLabelTimeUTC.setText("GLOBAL TIME: " + timeutc);
    }
  };
  private Timer timeRTC = new Timer(500, this.taskRTC);
  private JButton jButton1;
  private JButton jButtonCalendarEnd;
  private JButton jButtonCalendarStart;
  private JButton jButtonLocalSynchronization;
  private JButton jButtonReadHorary;
  private JButton jButtonSaveHorary;
  private JComboBox jComboBoxHoraryZone;
  private JLabel jLabelEndHorary;
  private JLabel jLabelHoraryZone;
  private JLabel jLabelNamePanel;
  private JLabel jLabelStartHorary;
  private JLabel jLabelTimeUTC;
  private JLabel jLabelUTCTime;
  private JTextField jTextFieldEndHorary;
  private JTextField jTextFieldReadWriteUTCTime;
  private JTextField jTextFieldStartHorary;
  
  public static Horary getInstance()
  {
    return INSTANCE;
  }
}
