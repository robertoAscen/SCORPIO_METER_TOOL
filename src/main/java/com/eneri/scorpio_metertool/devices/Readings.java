/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eneri.scorpio_metertool.devices;

import com.eneri.scorpio_metertool.bus.Conversions;
import com.eneri.scorpio_metertool.bus.ModbusRTUCRC16;
import com.eneri.scorpio_metertool.hardwarelayer.PortComunication;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.PrintStream;
import static java.lang.Double.NaN;
import java.nio.ByteBuffer;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;

/**
 *
 * @author Roberto
 */
public class Readings
  extends Thread
{
  PortComunication port = new PortComunication();
  Conversions conv = new Conversions();
  public final int RANGE_MEASUREMENT_BLOCK = 220;
  public final int NUMBER_REGISTERS = 50;
  byte[] readBuffer;
  double[] datos;
  public boolean bolreadings;
  public Set set = null;
  public Iterator it = null;
  public String serialNumber;
  private byte[] frame;
  
  public byte[] buildFrame()
    throws Exception
  {
    this.serialNumber = Meter.getSerialNumberCompl();
    Register countWordsReceive = Register.RANGE_MEASUREMENT_BLOCK;
    byte[] snBytes = Meter.getSerialNumberBytes(this.serialNumber);
    this.frame = new byte[24];
    
    System.arraycopy(snBytes, 0, this.frame, 0, snBytes.length);
    this.frame[16] = 0;
    this.frame[17] = 3;
    this.frame[18] = 0;
    this.frame[19] = ((byte)countWordsReceive.getAddress());
    this.frame[20] = 0;
    this.frame[21] = ((byte)countWordsReceive.getWordLength());
    
    int frameCRC = ModbusRTUCRC16.doCRC16(this.frame, 0, this.frame.length - 2);
    int frameCRCHi = frameCRC & 0xFF00;
    frameCRCHi >>>= 8;
    this.frame[22] = ((byte)frameCRCHi);
    this.frame[23] = ((byte)(frameCRC & 0xFF));
    
    return this.frame;
  }
  
  public double[] readBytes(byte[] frame)
  {
    String timeStampComplete = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
    
    byte[] receiveData = new byte['�'];
    
    double[] data = new double[50];
    if (frame.length != 241)
    {
      data = null;
    }
    else
    {
      System.arraycopy(frame, 19, receiveData, 0, 220);
      String a = Conversions.byteArrayToHex(receiveData);
      System.out.println(timeStampComplete + " Received: " + a);
      
      ByteBuffer bb = ByteBuffer.allocate(receiveData.length);
      bb.put(receiveData);
      bb.position(0);
      
      int kh = bb.getShort() & 0xFFFF;
      int TOUId = bb.getShort();
      int FWVersion = bb.getShort();
      int TiempodeduracionMododePrueba = bb.getShort();
      int Tiemporestantedelsubintervalo = bb.getShort();
      int HoradeRestablecimientodeTension = bb.getInt();
      int HoradeAusenciadeTension = bb.getInt();
      int ContadorInterrupciones = bb.getShort();
      int Flags = bb.getInt();
      float EnergiaActivaTotal = bb.getFloat();
      float EnergiaReactivaTotal = bb.getFloat();
      float EnergiaNegativaActivaTotal = bb.getFloat();
      float EnergiaNegativaReactivaTotal = bb.getFloat();
      long UTCTime = bb.getInt();
      long Localtime = bb.getInt();
      float Frecuencia = bb.getFloat();
      float voltajeA = bb.getFloat();
      float voltajeB = bb.getFloat();
      float voltajeC = bb.getFloat();
      float CorrienteA = bb.getFloat();
      float CorrienteB = bb.getFloat();
      float CorrienteC = bb.getFloat();
      float FPSistema = bb.getFloat();
      float FPFaseA = bb.getFloat();
      float FPFaseB = bb.getFloat();
      float FPFaseC = bb.getFloat();
      float DemandaRoladaActivaEnt = bb.getFloat();
      long TiempoDemRoladaActivaEnt = bb.getInt();
      float DemandaRoladaReactivaEnt = bb.getFloat();
      long TiempoDemRoladaReactivaEnt = bb.getInt();
      float DemandaRoladaActivaRecib = bb.getFloat();
      long TiempoDemRoladaActivaRecib = bb.getInt();
      float DemandaRoladaReactivaRecib = bb.getFloat();
      long TiempoDemRoladaReactivaRecib = bb.getInt();
      float PotenciaActivaSistema = bb.getFloat();
      float PotenciaReactivaSistema = bb.getFloat();
      float PotenciaAparenteSistema = bb.getFloat();
      
      float EnergiaActivaFaseA = bb.getFloat();
      float EnergiaActivaFaseB = bb.getFloat();
      float EnergiaActivaFaseC = bb.getFloat();
      float EnergiaReactivaFaseA = bb.getFloat();
      float EnergiaReactivaFaseB = bb.getFloat();
      float EnergiaReactivaFaseC = bb.getFloat();
      float EnergiaNegativaActivaFaseA = bb.getFloat();
      float EnergiaNegativaActivaFaseB = bb.getFloat();
      float EnergiaNegativaActivaFaseC = bb.getFloat();
      float EnergiaNegativaReactivaFaseA = bb.getFloat();
      float EnergiaNegativaReactivaFaseB = bb.getFloat();
      float EnergiaNegativaReactivaFaseC = bb.getFloat();
      
      float PotenciaActivaSistemaA = bb.getFloat();
      float PotenciaActivaSistemaB = bb.getFloat();
      float PotenciaActivaSistemaC = bb.getFloat();
      float PotenciaReactivaSistemaA = bb.getFloat();
      float PotenciaReactivaSistemaB = bb.getFloat();
      float PotenciaReactivaSistemaC = bb.getFloat();
      float PotenciaAparenteSistemaA = bb.getFloat();
      float PotenciaAparenteSistemaB = bb.getFloat();
      float PotenciaAparenteSistemaC = bb.getFloat();
      float Kh1 = kh / 10000.0F;
      //float Kh1;
      /*if(kh == NaN.0F)
      {
        Kh1 = 65535.0F;
      }
      else
      {
        Kh1 = kh / 10000.0F;
      }*/
      int FWVersion1 = FWVersion;
      
      long Localtime1 = Localtime;
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
      float FrecuenciaHz = Frecuencia;
      float Va;
      //float Va;
      if ((voltajeA == 65535.0F) || (Float.isNaN(voltajeA))) {
        Va = 65535.0F;
      } else {
        Va = voltajeA;
      }
      float Vb;
      //float Vb;
      if ((voltajeB == 65535.0F) || (Float.isNaN(voltajeB))) {
        Vb = 65535.0F;
      } else {
        Vb = voltajeB;
      }
      float Vc;
      //float Vc;
      if ((voltajeC == 65535.0F) || (Float.isNaN(voltajeC))) {
        Vc = 65535.0F;
      } else {
        Vc = voltajeC;
      }
      float Ia;
      //float Ia;
      if ((CorrienteA == 65535.0F) || (Float.isNaN(CorrienteA))) {
        Ia = 65535.0F;
      } else {
        Ia = CorrienteA;
      }
      float Ib;
      //float Ib;
      if ((CorrienteB == 65535.0F) || (Float.isNaN(CorrienteB))) {
        Ib = 65535.0F;
      } else {
        Ib = CorrienteB;
      }
      float Ic;
      //float Ic;
      if ((CorrienteC == 65535.0F) || (Float.isNaN(CorrienteC))) {
        Ic = 65535.0F;
      } else {
        Ic = CorrienteC;
      }
      float PF;
      //float PF;
      if ((FPSistema == 65535.0F) || (Float.isNaN(FPSistema))) {
        PF = 65535.0F;
      } else {
        PF = FPSistema;
      }
      float FPFaseA1;
      //float FPFaseA1;
      if ((FPFaseA == 65535.0F) || (Float.isNaN(FPFaseA))) {
        FPFaseA1 = 65535.0F;
      } else {
        FPFaseA1 = FPFaseA;
      }
      float FPFaseB1;
      //float FPFaseB1;
      if ((FPFaseB == 65535.0F) || (Float.isNaN(FPFaseB))) {
        FPFaseB1 = 65535.0F;
      } else {
        FPFaseB1 = FPFaseB;
      }
      float FPFaseC1;
      //float FPFaseC1;
      if ((FPFaseC == 65535.0F) || (Float.isNaN(FPFaseC))) {
        FPFaseC1 = 65535.0F;
      } else {
        FPFaseC1 = FPFaseC;
      }
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
      float PotenciaActivaSistemakWsys;
      //float PotenciaActivaSistemakWsys;
      if ((PotenciaActivaSistema == 65535.0F) || (Float.isNaN(PotenciaActivaSistema))) {
        PotenciaActivaSistemakWsys = 65535.0F;
      } else {
        PotenciaActivaSistemakWsys = PotenciaActivaSistema;
      }
      float PotenciaReactivaSistemakvar;
      //float PotenciaReactivaSistemakvar;
      if ((PotenciaReactivaSistema == 65535.0F) || (Float.isNaN(PotenciaReactivaSistema))) {
        PotenciaReactivaSistemakvar = 65535.0F;
      } else {
        PotenciaReactivaSistemakvar = PotenciaReactivaSistema;
      }
      float PotenciaAparenteSistemakVA;
      //float PotenciaAparenteSistemakVA;
      if ((PotenciaAparenteSistema == 65535.0F) || (Float.isNaN(PotenciaAparenteSistema))) {
        PotenciaAparenteSistemakVA = 65535.0F;
      } else {
        PotenciaAparenteSistemakVA = PotenciaAparenteSistema;
      }
      float EnergiaActivaFaseAWhA;
      //float EnergiaActivaFaseAWhA;
      if ((EnergiaActivaFaseA == -1.0F) || (Float.isNaN(EnergiaActivaFaseA))) {
        EnergiaActivaFaseAWhA = 65535.0F;
      } else {
        EnergiaActivaFaseAWhA = EnergiaActivaFaseA;
      }
      float EnergiaActivaFaseBWhB;
      //float EnergiaActivaFaseBWhB;
      if ((EnergiaActivaFaseB == -1.0F) || (Float.isNaN(EnergiaActivaFaseB))) {
        EnergiaActivaFaseBWhB = 65535.0F;
      } else {
        EnergiaActivaFaseBWhB = EnergiaActivaFaseB;
      }
      float EnergiaActivaFaseCWhC;
      //float EnergiaActivaFaseCWhC;
      if ((EnergiaActivaFaseC == -1.0F) || (Float.isNaN(EnergiaActivaFaseC))) {
        EnergiaActivaFaseCWhC = 65535.0F;
      } else {
        EnergiaActivaFaseCWhC = EnergiaActivaFaseC;
      }
      float EnergiaReactivaFaseAWhA;
      //float EnergiaReactivaFaseAWhA;
      if ((EnergiaReactivaFaseA == -1.0F) || (Float.isNaN(EnergiaReactivaFaseA))) {
        EnergiaReactivaFaseAWhA = 65535.0F;
      } else {
        EnergiaReactivaFaseAWhA = EnergiaReactivaFaseA;
      }
      float EnergiaReactivaFaseBWhA;
      //float EnergiaReactivaFaseBWhA;
      if ((EnergiaReactivaFaseB == -1.0F) || (Float.isNaN(EnergiaReactivaFaseB))) {
        EnergiaReactivaFaseBWhA = 65535.0F;
      } else {
        EnergiaReactivaFaseBWhA = EnergiaReactivaFaseB;
      }
      float EnergiaReactivaFaseCWhA;
      //float EnergiaReactivaFaseCWhA;
      if ((EnergiaReactivaFaseC == -1.0F) || (Float.isNaN(EnergiaReactivaFaseC))) {
        EnergiaReactivaFaseCWhA = 65535.0F;
      } else {
        EnergiaReactivaFaseCWhA = EnergiaReactivaFaseC;
      }
      float EnergiaNegativaActivaFaseAWhA;
      //float EnergiaNegativaActivaFaseAWhA;
      if ((EnergiaNegativaActivaFaseA == -1.0F) || (Float.isNaN(EnergiaNegativaActivaFaseA))) {
        EnergiaNegativaActivaFaseAWhA = 65535.0F;
      } else {
        EnergiaNegativaActivaFaseAWhA = EnergiaNegativaActivaFaseA;
      }
      float EnergiaNegativaActivaFaseBWhA;
      //float EnergiaNegativaActivaFaseBWhA;
      if ((EnergiaNegativaActivaFaseB == -1.0F) || (Float.isNaN(EnergiaNegativaActivaFaseB))) {
        EnergiaNegativaActivaFaseBWhA = 65535.0F;
      } else {
        EnergiaNegativaActivaFaseBWhA = EnergiaNegativaActivaFaseB;
      }
      float EnergiaNegativaActivaFaseCWhA;
      //float EnergiaNegativaActivaFaseCWhA;
      if ((EnergiaNegativaActivaFaseC == -1.0F) || (Float.isNaN(EnergiaNegativaActivaFaseC))) {
        EnergiaNegativaActivaFaseCWhA = 65535.0F;
      } else {
        EnergiaNegativaActivaFaseCWhA = EnergiaNegativaActivaFaseC;
      }
      float EnergiaNegativaReactivaFaseAWhA;
      //float EnergiaNegativaReactivaFaseAWhA;
      if ((EnergiaNegativaReactivaFaseA == -1.0F) || (Float.isNaN(EnergiaNegativaReactivaFaseA))) {
        EnergiaNegativaReactivaFaseAWhA = 65535.0F;
      } else {
        EnergiaNegativaReactivaFaseAWhA = EnergiaNegativaReactivaFaseA;
      }
      float EnergiaNegativaReactivaFaseBWhA;
      //float EnergiaNegativaReactivaFaseBWhA;
      if ((EnergiaNegativaReactivaFaseB == -1.0F) || (Float.isNaN(EnergiaNegativaReactivaFaseB))) {
        EnergiaNegativaReactivaFaseBWhA = 65535.0F;
      } else {
        EnergiaNegativaReactivaFaseBWhA = EnergiaNegativaReactivaFaseB;
      }
      float EnergiaNegativaReactivaFaseCWhA;
      //float EnergiaNegativaReactivaFaseCWhA;
      if ((EnergiaNegativaReactivaFaseC == -1.0F) || (Float.isNaN(EnergiaNegativaReactivaFaseC))) {
        EnergiaNegativaReactivaFaseCWhA = 65535.0F;
      } else {
        EnergiaNegativaReactivaFaseCWhA = EnergiaNegativaReactivaFaseC;
      }
      float PotenciaActivaSistemaAWsys;
      //float PotenciaActivaSistemaAWsys;
      if ((PotenciaActivaSistemaA == -1.0F) || (PotenciaActivaSistemaA == 65535.0F) || (Float.isNaN(PotenciaActivaSistemaA))) {
        PotenciaActivaSistemaAWsys = 65535.0F;
      } else {
        PotenciaActivaSistemaAWsys = PotenciaActivaSistemaA;
      }
      float PotenciaActivaSistemaBWsys;
      //float PotenciaActivaSistemaBWsys;
      if ((PotenciaActivaSistemaB == -1.0F) || (PotenciaActivaSistemaB == 65535.0F) || (Float.isNaN(PotenciaActivaSistemaB))) {
        PotenciaActivaSistemaBWsys = 65535.0F;
      } else {
        PotenciaActivaSistemaBWsys = PotenciaActivaSistemaB;
      }
      float PotenciaActivaSistemaCWsys;
      //float PotenciaActivaSistemaCWsys;
      if ((PotenciaActivaSistemaC == -1.0F) || (PotenciaActivaSistemaC == 65535.0F) || (Float.isNaN(PotenciaActivaSistemaC))) {
        PotenciaActivaSistemaCWsys = 65535.0F;
      } else {
        PotenciaActivaSistemaCWsys = PotenciaActivaSistemaC;
      }
      float PotenciaReactivaSistemaAkvar;
      //float PotenciaReactivaSistemaAkvar;
      if ((PotenciaReactivaSistemaA == -1.0F) || (PotenciaReactivaSistemaA == 65535.0F) || (Float.isNaN(PotenciaReactivaSistemaA))) {
        PotenciaReactivaSistemaAkvar = 65535.0F;
      } else {
        PotenciaReactivaSistemaAkvar = PotenciaReactivaSistemaA;
      }
      float PotenciaReactivaSistemaBkvar;
      //float PotenciaReactivaSistemaBkvar;
      if ((PotenciaReactivaSistemaB == -1.0F) || (PotenciaReactivaSistemaB == 65535.0F) || (Float.isNaN(PotenciaReactivaSistemaB))) {
        PotenciaReactivaSistemaBkvar = 65535.0F;
      } else {
        PotenciaReactivaSistemaBkvar = PotenciaReactivaSistemaB;
      }
      float PotenciaReactivaSistemaCkvar;
      //float PotenciaReactivaSistemaCkvar;
      if ((PotenciaReactivaSistemaC == -1.0F) || (PotenciaReactivaSistemaC == 65535.0F) || (Float.isNaN(PotenciaReactivaSistemaC))) {
        PotenciaReactivaSistemaCkvar = 65535.0F;
      } else {
        PotenciaReactivaSistemaCkvar = PotenciaReactivaSistemaC;
      }
      float PotenciaAparenteSistemaAkVA;
      //float PotenciaAparenteSistemaAkVA;
      if ((PotenciaAparenteSistemaA == -1.0F) || (PotenciaAparenteSistemaA == 65535.0F) || (Float.isNaN(PotenciaAparenteSistemaA))) {
        PotenciaAparenteSistemaAkVA = 65535.0F;
      } else {
        PotenciaAparenteSistemaAkVA = PotenciaAparenteSistemaA;
      }
      float PotenciaAparenteSistemaBkVA;
      //float PotenciaAparenteSistemaBkVA;
      if ((PotenciaAparenteSistemaB == -1.0F) || (PotenciaAparenteSistemaB == 65535.0F) || (Float.isNaN(PotenciaAparenteSistemaB))) {
        PotenciaAparenteSistemaBkVA = 65535.0F;
      } else {
        PotenciaAparenteSistemaBkVA = PotenciaAparenteSistemaB;
      }
      float PotenciaAparenteSistemaCkVA;
      //float PotenciaAparenteSistemaCkVA;
      if ((PotenciaAparenteSistemaC == -1.0F) || (PotenciaAparenteSistemaC == 65535.0F) || (Float.isNaN(PotenciaAparenteSistemaC))) {
        PotenciaAparenteSistemaCkVA = 65535.0F;
      } else {
        PotenciaAparenteSistemaCkVA = PotenciaAparenteSistemaC;
      }
      data[0] = Kh1;
      data[1] = FWVersion1;
      data[2] = EnergiaActivaTotalVArh;
      data[3] = EnergiaReactivaTotalkVArh;
      data[4] = EnergiaNegativaActivaTotal1;
      data[5] = EnergiaNegativaReactivaTotal1;
      
      data[6] = Localtime1;
      data[7] = FrecuenciaHz;
      data[8] = Va;
      data[9] = Vb;
      data[10] = Vc;
      data[11] = Ia;
      data[12] = Ib;
      data[13] = Ic;
      data[14] = PF;
      data[15] = FPFaseA1;
      data[16] = FPFaseB1;
      data[17] = FPFaseC1;
      data[18] = DemandaRoladaActivaEnt1;
      data[19] = TiempoDemRoladaActivaEnt1;
      data[20] = DemandaRoladaReactivaEnt1;
      data[21] = TiempoDemRoladaReactivaEnt1;
      data[22] = DemandaRoladaActivaRecib1;
      data[23] = TiempoDemRoladaActivaRecib1;
      data[24] = DemandaRoladaReactivaRecib1;
      data[25] = TiempoDemRoladaReactivaRecib1;
      data[26] = PotenciaActivaSistemakWsys;
      data[27] = PotenciaReactivaSistemakvar;
      data[28] = PotenciaAparenteSistemakVA;
      data[29] = EnergiaActivaFaseAWhA;
      data[30] = EnergiaActivaFaseBWhB;
      data[31] = EnergiaActivaFaseCWhC;
      
      data[32] = EnergiaReactivaFaseAWhA;
      data[33] = EnergiaReactivaFaseBWhA;
      data[34] = EnergiaReactivaFaseCWhA;
      data[35] = EnergiaNegativaActivaFaseAWhA;
      data[36] = EnergiaNegativaActivaFaseBWhA;
      data[37] = EnergiaNegativaActivaFaseCWhA;
      data[38] = EnergiaNegativaReactivaFaseAWhA;
      data[39] = EnergiaNegativaReactivaFaseBWhA;
      data[40] = EnergiaNegativaReactivaFaseCWhA;
      data[41] = PotenciaActivaSistemaAWsys;
      data[42] = PotenciaActivaSistemaBWsys;
      data[43] = PotenciaActivaSistemaCWsys;
      data[44] = PotenciaReactivaSistemaAkvar;
      data[45] = PotenciaReactivaSistemaBkvar;
      data[46] = PotenciaReactivaSistemaCkvar;
      data[47] = PotenciaAparenteSistemaAkVA;
      data[48] = PotenciaAparenteSistemaBkVA;
      data[49] = PotenciaAparenteSistemaCkVA;
    }
    return data;
  }
  
  public void requestReadings(long delay)
  {
    try
    {
      this.readBuffer = null;
      byte[] frame = buildFrame();
      
      this.port.write(frame, delay);
    }
    catch (Exception e) {}
  }
  
  public boolean getStatusReadings()
  {
    return this.bolreadings;
  }
  
  public void setStatusReadings(boolean bolreadings)
  {
    this.bolreadings = bolreadings;
  }
  
  public double[] responseReadings()
  {
    if (this.bolreadings == true)
    {
      this.readBuffer = this.port.getByteBuffer();
      int tryAgain = 0;
      while (((this.readBuffer == null ? 1 : 0) & (tryAgain <= 5 ? 1 : 0)) != 0)
      {
        PortComunication.threadDelay(50L);
        this.readBuffer = this.port.getByteBuffer();
        tryAgain++;
      }
      if (this.readBuffer == null)
      {
        this.bolreadings = false;
        this.datos = null;
      }
      else
      {
        this.datos = readBytes(this.readBuffer);
      }
    }
    else if (!this.bolreadings)
    {
      this.datos = null;
    }
    return this.datos;
  }
  
  public void WriteXMLFile(LinkedHashMap<String, String> list)
  {
    String timeStampCorto = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());
    String timeStampComplete = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(Calendar.getInstance().getTime());
    String timeStampHora = new SimpleDateFormat("HH-mm-ss").format(Calendar.getInstance().getTime());
    
    File carpeta = new File("");
    File defaultname = new File("C:/DatosObtenidosMeter/" + timeStampCorto + "/");
    JFileChooser f = new JFileChooser();
    f.setFileSelectionMode(1);
    f.showSaveDialog(null);
    
    carpeta = f.getSelectedFile();
    if (carpeta == null) {
      return;
    }
    carpeta = new File(f.getSelectedFile() + "/" + timeStampCorto + "/");
    if (carpeta.mkdirs()) {
      System.out.println("Multiple directories are created!");
    } else {
      System.out.println("Failed to create multiple directories!");
    }
    Path path = Paths.get(carpeta + "/", new String[0]);
    try
    {
      DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
      
      int contador = 0;
      
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("\r\n").append("<lecturas>").append("\r\n");
      
      Iterator it = null;
      Set set = list.entrySet();
      it = set.iterator();
      while (it.hasNext())
      {
        Map.Entry entry = (Map.Entry)it.next();
        String value = entry.getValue().toString();
        String key = entry.getKey().toString();
        String contadorString = Integer.toString(contador);
        stringBuilder.append("\t").append("<").append("dato").append(">").append("\r\n");
        if (value == "") {
          value = " ";
        }
        stringBuilder.append("\t\t").append("<").append(key).append(">").append(value).append("</").append(key).append(">").append("\r\n");
        
        String valor = value.toString();
        String medidorsinlect = "Medidor sin lecturas";
        stringBuilder.append("\t").append("</").append("dato").append(">").append("\r\n");
        
        contador++;
      }
      stringBuilder.append("</lecturas>");
      System.out.println(stringBuilder.toString());
      
      TransformerFactory transformerFactory = TransformerFactory.newInstance();
      Transformer transformer = transformerFactory.newTransformer();
      
      ByteArrayInputStream bis = new ByteArrayInputStream(stringBuilder.toString().getBytes());
      
      Document doc = docBuilder.parse(bis);
      
      DOMSource source = new DOMSource(doc);
      
      StreamResult result = new StreamResult(new File(carpeta + "/" + "LecturasActuales" + timeStampComplete + ".xml"));
      
      transformer.transform(source, result);
      
      System.out.println("File saved!");
      JOptionPane.showMessageDialog(null, "Archivo Guardado!");
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
