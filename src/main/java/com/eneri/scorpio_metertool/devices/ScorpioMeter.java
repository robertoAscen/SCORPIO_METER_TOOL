/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eneri.scorpio_metertool.devices;

import java.io.PrintStream;
import java.nio.ByteBuffer;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Roberto
 */
public class ScorpioMeter
{
  private static final long EPOCH_2K_ZERO = 946684800000L;
  public static DecimalFormat df = new DecimalFormat("#,###.####");
  
  public static String convert(byte[] response, Register reg)
    throws Exception
  {
    long rawADC = 0L;
    switch (response.length)
    {
    case 4: 
      rawADC = (response[0] & 0xFF) << 24;
      rawADC |= (response[1] & 0xFF) << 16;
      rawADC |= (response[2] & 0xFF) << 8;
      rawADC |= response[3] & 0xFF;
      break;
    case 3: 
      rawADC = (response[0] & 0xFF) << 16;
      rawADC |= (response[1] & 0xFF) << 8;
      rawADC |= response[2] & 0xFF;
      break;
    case 2: 
      rawADC = (response[0] & 0xFF) << 8;
      rawADC |= response[1] & 0xFF;
      break;
    case 1: 
      rawADC = response[0] & 0xFF;
    }
    StringBuilder sb = new StringBuilder();
    float resultFloat = 0.0F;
    switch (reg.getType())
    {
    case "DO": 
      resultFloat = datosFloat(response);
      if (rawADC == 65535L) {
        sb.append(0);
      } else if (Float.isNaN(resultFloat)) {
        sb.append(0);
      } else if ((reg.equals(Register.CONTADOR_INTERRUPCIONES)) || (reg.equals(Register.BANDERAS_DEL_SISTEMA)) || (reg.equals(Register.KH)) || (reg.equals(Register.NUMERO_LINKER))) {
        sb.append(rawADC);
      } else {
        sb.append(df.format(resultFloat));
      }
      break;
    case "DA": 
      if (((rawADC == 65535L ? 1 : 0) | (rawADC == -1L ? 1 : 0) | (rawADC <= 0L ? 1 : 0)) != 0)
      {
        sb.append(0);
      }
      else if ((reg.equals(Register.FECHA) | reg.equals(Register.FECHA_LOCAL)))
      {
        rawADC *= 1000L;
        
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        sb.append(format.format(new Date(rawADC + 946684800000L)).toString());
      }
      else
      {
        Date date = new Date();
        int offset = (byte)(int)TimeUnit.MILLISECONDS.toHours(TimeZone.getDefault().getOffset(date.getTime()));
        rawADC *= 1000L;
        
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone("GMT" + offset + ""));
        sb.append(format.format(new Date(rawADC + 946684800000L)).toString());
      }
      break;
    case "S": 
      sb.append(new String(response));
      break;
    case "L": 
      if (rawADC == 65535L) {
        sb.append(0);
      } else {
        sb.append(rawADC);
      }
      break;
    case "FW": 
      long intMajorVer = rawADC >> 10 & 0x3F;
      long intMinorVer = rawADC >> 5 & 0x1F;
      long intRevVer = rawADC & 0x1F;
      sb.append(Long.toString(intMajorVer));
      sb.append(".");
      sb.append(Long.toString(intMinorVer));
      sb.append(".");
      sb.append(Long.toString(intRevVer));
      break;
    case "HEX": 
      for (byte value : response) {
        if ((value & 0xFF) < 16)
        {
          sb.append(" 0");
          sb.append(Integer.toHexString(value & 0xFF));
        }
        else
        {
          sb.append(" ");
          sb.append(Integer.toHexString(value & 0xFF));
        }
      }
      break;
    default: 
      for (byte value : response) {
        if ((value & 0xFF) < 16)
        {
          sb.append(" 0");
          sb.append(Integer.toHexString(value & 0xFF));
        }
        else
        {
          sb.append(" ");
          sb.append(Integer.toHexString(value & 0xFF));
        }
      }
    }
    return sb.toString();
  }
  
  public static float datosFloat(byte[] recib)
  {
    ByteBuffer b = ByteBuffer.allocate(4);
    
    b.put(recib);
    b.position(0);
    
    float value = b.getFloat();
    System.out.println(String.valueOf(value));
    
    return value;
  }
}
