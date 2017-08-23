/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eneri.scorpio_metertool.devices;

import java.util.List;

/**
 *
 * @author Roberto
 */
public abstract class Meter
{
  public static String serialNumber = "0000000000000000";
  public static String firmwareVersion = null;
  private static byte[] serialNumberBytes = null;
  private static byte[] frame = null;
  public static final byte READ_REGISTER = 3;
  public static final byte WRITE_REGISTER = 6;
  public static final byte WRITE_MULTIPLE_REGISTER = 16;
  
  public static String setSerialNumber(String SN)
  {
    int tamanoSn = SN.length();
    int restaTamanoSn = 16 - tamanoSn;
    String nuevoSN = "";
    if (tamanoSn < 16)
    {
      for (int i = 0; i < restaTamanoSn; i++) {
        nuevoSN = nuevoSN + "0";
      }
      nuevoSN = nuevoSN + SN;
      
      serialNumber = nuevoSN;
    }
    else
    {
      serialNumber = SN;
    }
    return serialNumber;
  }
  
  public static String getSerialNumberCompl()
  {
    return serialNumber;
  }
  
  public static byte[] getSerialNumberBytes(String sn)
  {
    serialNumberBytes = sn.getBytes();
    
    return serialNumberBytes;
  }
  
  public abstract List<Boolean> readFlags();
  
  public abstract Readings getAllReadings();
  
  public abstract boolean reset();
  
  public abstract boolean connect();
  
  public abstract boolean disconnect();
  
  public abstract boolean resetEnergy();
  
  public abstract float getTotalActiveEnergyReceived();
}
