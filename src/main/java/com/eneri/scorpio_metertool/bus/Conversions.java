/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eneri.scorpio_metertool.bus;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author Roberto
 */
public class Conversions
{
  private static final int DECIMAL_ROUND_SCALE = 4;
  
  public static byte[] toByteArray(double value)
  {
    byte[] bytes = new byte[8];
    ByteBuffer.wrap(bytes).putDouble(value);
    return bytes;
  }
  
  public static double toDouble(byte[] bytes)
  {
    return ByteBuffer.wrap(bytes).getDouble();
  }
  
  public static double toDouble2(byte[] data)
  {
    if ((data == null) || (data.length != 8)) {
      return 0.0D;
    }
    return Double.longBitsToDouble(toLong(data));
  }
  
  public static long toLong(byte[] data)
  {
    if ((data == null) || (data.length != 8)) {
      return 0L;
    }
    return (0xFF & data[0]) << 56 | (0xFF & data[1]) << 48 | (0xFF & data[2]) << 40 | (0xFF & data[3]) << 32 | (0xFF & data[4]) << 24 | (0xFF & data[5]) << 16 | (0xFF & data[6]) << 8 | (0xFF & data[7]) << 0;
  }
  
  public static String byteArrayToHex(byte[] a)
  {
    StringBuilder sb = new StringBuilder(a.length * 2);
    for (byte b : a) {
      sb.append(String.format("%02x", new Object[] { Integer.valueOf(b & 0xFF) }) + " ");
    }
    return sb.toString().toUpperCase();
  }
  
  public String doubleRound(double value)
  {
    BigDecimal bd = BigDecimal.valueOf(value);
    BigDecimal bdRound = bd.setScale(4, RoundingMode.HALF_EVEN);
    DecimalFormat df = new DecimalFormat("#,###.####");
    String datos = df.format(value);
    
    return datos;
  }
  
  public static byte[] stringToBytesASCII(String str)
  {
    byte[] b = new byte[str.length()];
    for (int i = 0; i < b.length; i++) {
      b[i] = ((byte)str.charAt(i));
    }
    return b;
  }
  
  public static byte[] toByteArray(String s)
  {
    return DatatypeConverter.parseHexBinary(s);
  }
  
  public static byte[] hexStringToByteArray(String s)
  {
    int len = s.length();
    byte[] data = new byte[len / 2];
    for (int i = 0; i < len; i += 2) {
      data[(i / 2)] = ((byte)((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16)));
    }
    return data;
  }
  
  public static byte[] hexStringToByteArray2(String s)
  {
    byte[] b = new byte[s.length() / 2];
    for (int i = 0; i < b.length; i++)
    {
      int index = i * 2;
      int v = Integer.parseInt(s.substring(index, index + 2), 16);
      b[i] = ((byte)v);
    }
    return b;
  }
  
  public static int byteArrayToInt(byte[] b)
  {
    return byteArrayToInt(b, 0);
  }
  
  public static int byteArrayToInt(byte[] b, int offset)
  {
    int value = 0;
    for (int i = 0; i < 4; i++)
    {
      int shift = (3 - i) * 8;
      value += ((b[(i + offset)] & 0xFF) << shift);
    }
    return value;
  }
  
  public static final byte[] intToByteArray(int value)
  {
    return new byte[] { (byte)(value >>> 24), (byte)(value >>> 16), (byte)(value >>> 8), (byte)value };
  }
  
  public static final byte[] intToByteArrayBuffer(int value)
  {
    byte[] bytes = ByteBuffer.allocate(4).putInt(value).array();
    
    return bytes;
  }
  
  public static String asciiToHex(String asciiValue)
  {
    char[] chars = asciiValue.toCharArray();
    StringBuffer hex = new StringBuffer();
    for (int i = 0; i < chars.length; i++) {
      hex.append(Integer.toHexString(chars[i]));
    }
    return hex.toString();
  }
  
  public static String hexToASCII(String hexValue)
  {
    hexValue = hexValue.replace(" ", "").replace(":", "").trim();
    StringBuilder output = new StringBuilder("");
    for (int i = 0; i < hexValue.length(); i += 2)
    {
      String str = hexValue.substring(i, i + 2);
      
      output.append((char)Integer.parseInt(str, 16));
    }
    return output.toString();
  }
  
  private static final String md5(String password)
  {
    try
    {
      MessageDigest digest = MessageDigest.getInstance("MD5");
      
      digest.update(password.getBytes());
      byte[] messageDigest = digest.digest();
      
      StringBuffer hexString = new StringBuffer();
      for (int i = 0; i < messageDigest.length; i++)
      {
        String h = Integer.toHexString(0xFF & messageDigest[i]);
        while (h.length() < 2) {
          h = "0" + h;
        }
        hexString.append(h);
      }
      return hexString.toString();
    }
    catch (NoSuchAlgorithmException e)
    {
      e.printStackTrace();
    }
    return "";
  }
}
