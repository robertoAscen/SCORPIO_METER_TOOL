/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eneri.scorpio_metertool.server;

import com.eneri.scorpio_metertool.ui.ConcentratorMeters;
import com.eneri.scorpio_metertool.ui.ConcentratorReadings;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Roberto
 */
public class WorkConcentrator
{
  String timeStampCorto;
  String timeStampComplete;
  String timeStampHora;
  int nListcantidadTemp;
  int nListcantidad;
  int nListcantidadTotal;
  int nListcantidadRestante;
  int nListcantidadSumando;
  int nListcantidadEscala;
  int datoEnum;
  
  public WorkConcentrator()
  {
    this.nListcantidadTemp = 0;
    this.nListcantidad = 0;
    this.nListcantidadTotal = 0;
    this.nListcantidadRestante = 0;
    this.nListcantidadSumando = 0;
    this.nListcantidadEscala = 0;
  }
  
  public void readMetersConcentratorXML(String ip, String response)
  {
    ConcentratorMeters conMetUi = new ConcentratorMeters();
    try
    {
      DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
      ByteArrayInputStream bis = new ByteArrayInputStream(response.getBytes());
      Document doc = dBuilder.parse(bis);
      doc.getDocumentElement().normalize();
      
      System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
      
      NodeList nList2 = doc.getElementsByTagName("meters");
      System.out.println("----------------------------");
      for (int temp = 0; temp < nList2.getLength(); temp++)
      {
        Node nNode = nList2.item(temp);
        System.out.println("\nCurrent Element :" + nNode.getNodeName());
        if (nNode.getNodeType() == 1)
        {
          Element eElement = (Element)nNode;
          System.out.println("meters : " + eElement.getAttribute("count"));
        }
      }
      NodeList nList = doc.getElementsByTagName("meter");
      System.out.println("----------------------------");
      for (int temp = 0; temp < nList.getLength(); temp++)
      {
        Node nNode = nList.item(temp);
        System.out.println("\nCurrent Element :" + nNode.getNodeName());
        if (nNode.getNodeType() == 1)
        {
          Element eElement = (Element)nNode;
          
          System.out.println("serialNumber : " + eElement.getAttribute("serialNumber"));
          System.out.println("registrationDate : " + eElement.getAttribute("registrationDate"));
          System.out.println("type : " + eElement.getAttribute("type"));
          
          String serialn = eElement.getAttribute("serialNumber");
          String registrationDate = eElement.getAttribute("registrationDate");
          Date d = new Date(Long.parseLong(registrationDate));
          registrationDate = d.toString();
          String tipo = eElement.getAttribute("type");
          
          Object[] data = { serialn, registrationDate, tipo };
          conMetUi.getTable().addRow(data);
        }
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
  
  public void readReadingsConcentratorXML(String ip, String response)
  {
    ConcentratorReadings conReadUi = new ConcentratorReadings();
    boolean seguir = true;
    int balint = 0;
    try
    {
      DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
      ByteArrayInputStream bis = new ByteArrayInputStream(response.getBytes());
      Document doc = dBuilder.parse(bis);
      doc.getDocumentElement().normalize();
      System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
      NodeList nList2 = doc.getElementsByTagName("meters");
      System.out.println("----------------------------");
      for (int temp = 0; temp < nList2.getLength(); temp++)
      {
        Node nNode = nList2.item(temp);
        System.out.println("\nCurrent Element :" + nNode.getNodeName());
        if (nNode.getNodeType() == 1)
        {
          Element eElement = (Element)nNode;
          System.out.println("meters : " + eElement.getAttribute("count"));
        }
      }
      NodeList nList3 = doc.getElementsByTagName("meter");
      System.out.println("----------------------------");
      for (int temp = 0; temp < nList3.getLength(); temp++)
      {
        Node nNode = nList3.item(temp);
        System.out.println("\nCurrent Element :" + nNode.getNodeName());
        if (nNode.getNodeType() == 1)
        {
          Element eElement = (Element)nNode;
          
          System.out.println("SerialNumber : " + eElement.getAttribute("serialNumber"));
          System.out.println("success : " + eElement.getAttribute("success"));
          System.out.println("message : " + eElement.getAttribute("message"));
          System.out.println("registrationDate : " + eElement.getAttribute("registrationDate"));
          
          String SerialNumber = eElement.getAttribute("serialNumber");
          String success = eElement.getAttribute("success");
          String message = eElement.getAttribute("message");
          String registrationDate = eElement.getAttribute("registrationDate");
          if (registrationDate == "")
          {
            registrationDate = "";
          }
          else
          {
            Date d = new Date(Long.parseLong(registrationDate));
            registrationDate = d.toString();
          }
          Object[] data = { SerialNumber, success, message, registrationDate };
          conReadUi.setDataDefaultTable(data);
          NodeList nList = doc.getElementsByTagName("reading");
          this.nListcantidadTotal = nList.getLength();
          System.out.println("----------------------------");
          for (int temp1 = this.nListcantidadEscala; temp1 < nList.getLength(); temp1++)
          {
            Node nNode1 = nList.item(temp1);
            System.out.println("\nCurrent Element :" + nNode1.getNodeName());
            if (nNode1.getNodeType() == 1)
            {
              Element eElement1 = (Element)nNode1;
              System.out.println("id : " + eElement1.getAttribute("id"));
              System.out.println("name : " + eElement1.getAttribute("name"));
              System.out.println("value : " + eElement1.getAttribute("value"));
              String id = eElement1.getAttribute("id");
              String name = eElement1.getAttribute("name");
              String value = eElement1.getAttribute("value");
              if (value.equals("-1.0")) {
                value = "invalido";
              }
              String nada = "";
              String bitFlag = "";
              if (name.equals("flags"))
              {
                int values = Integer.parseInt(value);
                
                Hashtable<String, Integer> contenedor = new Hashtable();
                
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
                contenedor.put("NOT_USED", Integer.valueOf(12));
                contenedor.put("NOT_USED2", Integer.valueOf(13));
                contenedor.put("NOT_USED3", Integer.valueOf(14));
                contenedor.put("NOT_USED4", Integer.valueOf(15));
                contenedor.put("NOT_USED5", Integer.valueOf(16));
                contenedor.put("NOT_USED6", Integer.valueOf(17));
                contenedor.put("NOT_USED7", Integer.valueOf(18));
                contenedor.put("NOT_USED8", Integer.valueOf(19));
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
                
                Enumeration names = contenedor.keys();
                while (names.hasMoreElements())
                {
                  String str = (String)names.nextElement();
                  System.out.println(str + ": " + contenedor.get(str));
                }
                System.out.println();
                String activ = "";
                
                int balint0 = values & 1 << ((Integer)contenedor.get("METER_RELAY_STATE")).intValue();
                if (balint0 != 0) {
                  activ = "Cerrado";
                } else {
                  activ = "Abierto";
                }
                Object[] data1 = { Integer.valueOf(20), "METER_RELAY_STATE", activ, nada };
                conReadUi.setDataDefaultTable(data1);
                
                int balint1 = values & 1 << ((Integer)contenedor.get("METER_IR_MODE")).intValue();
                if (balint1 != 0) {
                  activ = "Activado";
                } else {
                  activ = "Desactivado";
                }
                Object[] data2 = { Integer.valueOf(21), "METER_IR_MODE", activ, nada };
                conReadUi.setDataDefaultTable(data2);
                int balint2 = values & 1 << ((Integer)contenedor.get("METER_3PH_MODE")).intValue();
                if (balint2 != 0) {
                  activ = "Activado";
                } else {
                  activ = "Desactivado";
                }
                Object[] data3 = { Integer.valueOf(22), "METER_3PH_MODE", activ, nada };
                conReadUi.setDataDefaultTable(data3);
                
                int balint3 = values & 1 << ((Integer)contenedor.get("METER_HM_MODE")).intValue();
                if (balint3 != 0) {
                  activ = "Activado";
                } else {
                  activ = "Desactivado";
                }
                Object[] data4 = { Integer.valueOf(23), "METER_HM_MODE", activ, nada };
                conReadUi.setDataDefaultTable(data4);
                
                int balint4 = values & 1 << ((Integer)contenedor.get("METER_VOLTAGE_MODE")).intValue();
                if (balint4 != 0) {
                  activ = "Activado";
                } else {
                  activ = "Desactivado";
                }
                Object[] data5 = { Integer.valueOf(24), "METER_VOLTAGE_MODE", activ, nada };
                conReadUi.setDataDefaultTable(data5);
                
                int balint5 = values & 1 << ((Integer)contenedor.get("BOOT_BACKDOOR_ENABLE")).intValue();
                if (balint5 != 0) {
                  activ = "Activado";
                } else {
                  activ = "Desactivado";
                }
                Object[] data6 = { Integer.valueOf(25), "BOOT_BACKDOOR_ENABLE", activ, nada };
                conReadUi.setDataDefaultTable(data6);
                
                int balint6 = values & 1 << ((Integer)contenedor.get("BOOT_UPGRADE_PROCESS_ENABLE")).intValue();
                if (balint6 != 0) {
                  activ = "Activado";
                } else {
                  activ = "Desactivado";
                }
                Object[] data7 = { Integer.valueOf(26), "BOOT_UPGRADE_PROCESS_ENABLE", activ, nada };
                conReadUi.setDataDefaultTable(data7);
                
                int balint7 = values & 1 << ((Integer)contenedor.get("METER_ACC_RESET")).intValue();
                if (balint7 != 0) {
                  activ = "Activado";
                } else {
                  activ = "Desactivado";
                }
                Object[] data8 = { Integer.valueOf(27), "METER_ACC_RESET", activ, nada };
                conReadUi.setDataDefaultTable(data8);
                
                int balint8 = values & 1 << ((Integer)contenedor.get("METER_FORCE_RESET")).intValue();
                if (balint8 != 0) {
                  activ = "Activado";
                } else {
                  activ = "Desactivado";
                }
                Object[] data9 = { Integer.valueOf(28), "METER_FORCE_RESET", activ, nada };
                conReadUi.setDataDefaultTable(data9);
                
                int balint9 = values & 1 << ((Integer)contenedor.get("METER_DEMAND_RESET")).intValue();
                if (balint9 != 0) {
                  activ = "Activado";
                } else {
                  activ = "Desactivado";
                }
                Object[] data10 = { Integer.valueOf(29), "METER_DEMAND_RESET", activ, nada };
                conReadUi.setDataDefaultTable(data10);
                
                int balint10 = values & 1 << ((Integer)contenedor.get("METER_STPMC_RESET")).intValue();
                if (balint10 != 0) {
                  activ = "Activado";
                } else {
                  activ = "Desactivado";
                }
                Object[] data11 = { Integer.valueOf(30), "METER_STPMC_RESET", activ, nada };
                conReadUi.setDataDefaultTable(data11);
                
                int balint11 = values & 1 << ((Integer)contenedor.get("BOOT_FORCE_BOOTLOADER")).intValue();
                if (balint11 != 0) {
                  activ = "Activado";
                } else {
                  activ = "Desactivado";
                }
                Object[] data12 = { Integer.valueOf(31), "BOOT_FORCE_BOOTLOADER", activ, nada };
                conReadUi.setDataDefaultTable(data12);
                
                int balint12 = values & 1 << ((Integer)contenedor.get("NOT_USED")).intValue();
                if (balint12 != 0) {
                  activ = "Activado";
                } else {
                  activ = "Desactivado";
                }
                Object[] data13 = { Integer.valueOf(32), "NOT_USED", activ, nada };
                conReadUi.setDataDefaultTable(data13);
                
                int balint13 = values & 1 << ((Integer)contenedor.get("NOT_USED2")).intValue();
                if (balint13 != 0) {
                  activ = "Activado";
                } else {
                  activ = "Desactivado";
                }
                Object[] data14 = { Integer.valueOf(33), "NOT_USED2", activ, nada };
                conReadUi.setDataDefaultTable(data14);
                
                int balint14 = values & 1 << ((Integer)contenedor.get("NOT_USED3")).intValue();
                if (balint14 != 0) {
                  activ = "Activado";
                } else {
                  activ = "Desactivado";
                }
                Object[] data15 = { Integer.valueOf(34), "NOT_USED3", activ, nada };
                conReadUi.setDataDefaultTable(data15);
                
                int balint15 = values & 1 << ((Integer)contenedor.get("NOT_USED4")).intValue();
                if (balint15 != 0) {
                  activ = "Activado";
                } else {
                  activ = "Desactivado";
                }
                Object[] data16 = { Integer.valueOf(35), "NOT_USED4", activ, nada };
                conReadUi.setDataDefaultTable(data16);
                
                int balint16 = values & 1 << ((Integer)contenedor.get("NOT_USED5")).intValue();
                if (balint16 != 0) {
                  activ = "Activado";
                } else {
                  activ = "Desactivado";
                }
                Object[] data17 = { Integer.valueOf(36), "NOT_USED5", activ, nada };
                conReadUi.setDataDefaultTable(data17);
                
                int balint17 = values & 1 << ((Integer)contenedor.get("NOT_USED6")).intValue();
                if (balint17 != 0) {
                  activ = "Activado";
                } else {
                  activ = "Desactivado";
                }
                Object[] data18 = { Integer.valueOf(37), "NOT_USED6", activ, nada };
                conReadUi.setDataDefaultTable(data18);
                
                int balint18 = values & 1 << ((Integer)contenedor.get("NOT_USED7")).intValue();
                if (balint18 != 0) {
                  activ = "Activado";
                } else {
                  activ = "Desactivado";
                }
                Object[] data19 = { Integer.valueOf(38), "NOT_USED7", activ, nada };
                conReadUi.setDataDefaultTable(data19);
                
                int balint19 = values & 1 << ((Integer)contenedor.get("NOT_USED8")).intValue();
                if (balint19 != 0) {
                  activ = "Activado";
                } else {
                  activ = "Desactivado";
                }
                Object[] data20 = { Integer.valueOf(39), "NOT_USED8", activ, nada };
                conReadUi.setDataDefaultTable(data20);
                
                int balint20 = values & 1 << ((Integer)contenedor.get("METER_FRAM_ERROR")).intValue();
                if (balint20 != 0) {
                  activ = "Activado";
                } else {
                  activ = "Desactivado";
                }
                Object[] data21 = { Integer.valueOf(40), "METER_FRAM_ERROR", activ, nada };
                conReadUi.setDataDefaultTable(data21);
                
                int balint21 = values & 1 << ((Integer)contenedor.get("METER_STPMC_READ_ERROR")).intValue();
                if (balint21 != 0) {
                  activ = "Activado";
                } else {
                  activ = "Desactivado";
                }
                Object[] data22 = { Integer.valueOf(41), "METER_STPMC_READ_ERROR", activ, nada };
                conReadUi.setDataDefaultTable(data22);
                
                int balint22 = values & 1 << ((Integer)contenedor.get("BOOT_FLASH_ERROR")).intValue();
                if (balint22 != 0) {
                  activ = "Activado";
                } else {
                  activ = "Desactivado";
                }
                Object[] data23 = { Integer.valueOf(42), "BOOT_FLASH_ERROR", activ, nada };
                conReadUi.setDataDefaultTable(data23);
                
                int balint23 = values & 1 << ((Integer)contenedor.get("METER_RTC_DESYNC_RISK")).intValue();
                if (balint23 != 0) {
                  activ = "Activado";
                } else {
                  activ = "Desactivado";
                }
                Object[] data24 = { Integer.valueOf(43), "METER_RTC_DESYNC_RISK", activ, nada };
                conReadUi.setDataDefaultTable(data24);
                
                int balint24 = values & 1 << ((Integer)contenedor.get("METER_NO_LOAD_CONNECTED")).intValue();
                if (balint24 != 0) {
                  activ = "Activado";
                } else {
                  activ = "Desactivado";
                }
                Object[] data25 = { Integer.valueOf(44), "METER_NO_LOAD_CONNECTED", activ, nada };
                conReadUi.setDataDefaultTable(data25);
                
                int balint25 = values & 1 << ((Integer)contenedor.get("METER_RUNNING_APP_CODE")).intValue();
                if (balint25 != 0) {
                  activ = "Activado";
                } else {
                  activ = "Desactivado";
                }
                Object[] data26 = { Integer.valueOf(45), "METER_RUNNING_APP_CODE", activ, nada };
                conReadUi.setDataDefaultTable(data26);
                
                int balint26 = values & 1 << ((Integer)contenedor.get("METER_VALID_IIC_DATABASE")).intValue();
                if (balint26 != 0) {
                  activ = "Activado";
                } else {
                  activ = "Desactivado";
                }
                Object[] data27 = { Integer.valueOf(46), "METER_VALID_IIC_DATABASE", activ, nada };
                conReadUi.setDataDefaultTable(data27);
                
                int balint27 = values & 1 << ((Integer)contenedor.get("METER_PHASE_SEQUENCE")).intValue();
                if (balint27 != 0) {
                  activ = "Activado";
                } else {
                  activ = "Desactivado";
                }
                Object[] data28 = { Integer.valueOf(47), "METER_PHASE_SEQUENCE", activ, nada };
                conReadUi.setDataDefaultTable(data28);
                
                int balint28 = values & 1 << ((Integer)contenedor.get("METER_REVERSE_CURRENT")).intValue();
                if (balint28 != 0) {
                  activ = "Activado";
                } else {
                  activ = "Desactivado";
                }
                Object[] data29 = { Integer.valueOf(48), "METER_REVERSE_CURRENT", activ, nada };
                conReadUi.setDataDefaultTable(data29);
                
                int balint29 = values & 1 << ((Integer)contenedor.get("METER_LOW_RANGE")).intValue();
                if (balint29 != 0) {
                  activ = "Activado";
                } else {
                  activ = "Desactivado";
                }
                Object[] data30 = { Integer.valueOf(49), "METER_LOW_RANGE", activ, nada };
                conReadUi.setDataDefaultTable(data30);
                
                int balint30 = values & 1 << ((Integer)contenedor.get("METER_HIGH_RANGE")).intValue();
                if (balint30 != 0) {
                  activ = "Activado";
                } else {
                  activ = "Desactivado";
                }
                Object[] data31 = { Integer.valueOf(50), "METER_HIGH_RANGE", activ, nada };
                conReadUi.setDataDefaultTable(data31);
                
                int balint31 = values & 1 << ((Integer)contenedor.get("METER_REACT_CAP")).intValue();
                if (balint31 != 0) {
                  activ = "Activado";
                } else {
                  activ = "Desactivado";
                }
                Object[] data32 = { Integer.valueOf(51), "METER_REACT_CAP", activ, nada };
                conReadUi.setDataDefaultTable(data32);
                
                int flag0 = values & 1 << balint0;
                
                seguir = false;
              }
              if (name.equals("relayStatus")) {
                if (value.equals("1"))
                {
                  value = "cerrado";
                  id = "52";
                }
                else
                {
                  value = "abierto";
                  id = "52";
                }
              }
              if (!name.equals("flags"))
              {
                Object[] data1 = { id, name, value, nada };
                
                conReadUi.setDataDefaultTable(data1);
              }
              this.nListcantidadTemp += 1;
              this.nListcantidadSumando += 1;
              if (this.nListcantidadTemp == 21)
              {
                this.nListcantidadTemp = 0;
                temp1 = this.nListcantidadTotal;
                this.nListcantidadEscala += 21;
              }
            }
          }
        }
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
  
  public static enum Flags
  {
    METER_RELAY_STATE(0),  METER_IR_MODE(1),  METER_3PH_MODE(2),  METER_HM_MODE(3),  METER_VOLTAGE_MODE(4),  BOOT_BACKDOOR_ENABLE(5),  BOOT_UPGRADE_PROCESS_ENABLE(6),  METER_ACC_RESET(7),  METER_FORCE_RESET(8),  METER_DEMAND_RESET(9),  METER_STPMC_RESET(10),  BOOT_FORCE_BOOTLOADER(11),  NOT_USED(12),  NOT_USED2(13),  NOT_USED3(14),  NOT_USED4(15),  NOT_USED5(16),  NOT_USED6(17),  NOT_USED7(18),  NOT_USED8(19),  METER_FRAM_ERROR(20),  METER_STPMC_READ_ERROR(21),  BOOT_FLASH_ERROR(22),  METER_RTC_DESYNC_RISK(23),  METER_NO_LOAD_CONNECTED(24),  METER_RUNNING_APP_CODE(25),  METER_VALID_IIC_DATABASE(26),  METER_PHASE_SEQUENCE(27),  METER_REVERSE_CURRENT(28),  METER_LOW_RANGE(29),  METER_HIGH_RANGE(30),  METER_REACT_CAP(31);
    
    private int cc;
    
    private Flags(int cc)
    {
      this.cc = cc;
    }
    
    public int getFlags()
    {
      return this.cc;
    }
  }
  
  public void convertMetersTableToXML(JTable table)
  {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("<medidores>").append("\r\n");
    int columnCount = table.getColumnCount();
    int rowCount = table.getRowCount();
    for (int i = 0; i < rowCount; i++)
    {
      stringBuilder.append("\t").append("<medidor>").append("\r\n");
      for (int j = 0; j < columnCount; j++)
      {
        String columnName = table.getColumnName(j);
        Object value = table.getValueAt(i, j);
        stringBuilder.append("\t\t").append("<").append(columnName).append(">").append(value).append("</").append(columnName).append(">").append("\r\n");
      }
      stringBuilder.append("\t").append("</medidor>").append("\r\n");
    }
    stringBuilder.append("</medidores>");
    System.out.println(stringBuilder.toString());
    writeXML(stringBuilder, "Medidores-");
  }
  
  public void convertReadingsTableToXML(JTable table)
  {
    int contador = 0;
    
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("<lecturas>").append("\r\n");
    int columnCount = table.getColumnCount();
    int rowCount = table.getRowCount();
    for (int i = 0; i < rowCount; i++)
    {
      stringBuilder.append("\t").append("<datosmedidor>").append("\r\n");
      String medidorsinlect;
      for (int j = 0; j < columnCount; j++)
      {
        String columnName = table.getColumnName(j);
        Object value = table.getValueAt(i, j);
        if (value == "") {
          value = " ";
        }
        stringBuilder.append("\t\t").append("<").append(columnName).append(">").append(value).append("</").append(columnName).append(">").append("\r\n");
        
        contador++;
        String valor = value.toString();
        medidorsinlect = "Medidor sin lecturas";
      }
      stringBuilder.append("\t").append("</datosmedidor>").append("\r\n");
    }
    stringBuilder.append("</lecturas>");
    System.out.println(stringBuilder.toString());
    writeXML(stringBuilder, "Lecturas-");
  }
  
  public void convertLoadProfileXML(JTable table)
  {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("<loadprofile>").append("\r\n");
    int columnCount = table.getColumnCount();
    int rowCount = table.getRowCount();
    for (int i = 0; i < rowCount; i++)
    {
      stringBuilder.append("\t").append("<value>").append("\r\n");
      for (int j = 0; j < columnCount; j++)
      {
        String columnName = table.getColumnName(j);
        Object value = table.getValueAt(i, j);
        stringBuilder.append("\t\t").append("<").append(columnName).append(">").append(value).append("</").append(columnName).append(">").append("\r\n");
      }
      stringBuilder.append("\t").append("</value>").append("\r\n");
    }
    stringBuilder.append("</loadprofile>");
    System.out.println(stringBuilder.toString());
    writeXML(stringBuilder, "loadProfile-");
  }
  
  public void writeXML(StringBuilder stringBuilder, String nameFile)
  {
    this.timeStampCorto = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());
    this.timeStampComplete = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(Calendar.getInstance().getTime());
    this.timeStampHora = new SimpleDateFormat("HH-mm-ss").format(Calendar.getInstance().getTime());
    try
    {
      DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
      ByteArrayInputStream bis = new ByteArrayInputStream(stringBuilder.toString().getBytes());
      Document doc = dBuilder.parse(bis);
      TransformerFactory transformerFactory = TransformerFactory.newInstance();
      Transformer transformer = transformerFactory.newTransformer();
      DOMSource source = new DOMSource(doc);
      
      File carpeta = new File("");
      File defaultname = new File("C:/DatosObtenidosMeter/" + this.timeStampCorto + "/");
      JFileChooser f = new JFileChooser();
      f.setFileSelectionMode(1);
      f.showSaveDialog(null);
      carpeta = f.getSelectedFile();
      if (carpeta == null) {
        return;
      }
      carpeta = new File(f.getSelectedFile() + "/" + this.timeStampCorto + "/");
      if (carpeta.mkdirs()) {
        System.out.println("Multiple directories are created!");
      } else {
        System.out.println("Failed to create multiple directories!");
      }
      Path path = Paths.get(carpeta + "/", new String[0]);
      StreamResult result = new StreamResult(new File(carpeta + "/" + nameFile + this.timeStampComplete + ".xml"));
      if (Files.exists(path, new LinkOption[0])) {}
      transformer.transform(source, result);
      System.out.println("File saved!");
      JOptionPane.showMessageDialog(null, "Guardado Exitosamente");
    }
    catch (ParserConfigurationException pce)
    {
      pce.printStackTrace();
    }
    catch (TransformerException tfe)
    {
      tfe.printStackTrace();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
}
