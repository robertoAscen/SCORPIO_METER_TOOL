/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eneri.scorpio_metertool.firmware;

import java.util.LinkedList;

/**
 *
 * @author Roberto
 */
public class IntelHEXLine
{
  private int byteCount;
  private int address;
  private int recordType;
  private byte[] data;
  private int checksum;
  private String rawLine;
  
  public IntelHEXLine(String rawLine)
  {
    this.rawLine = rawLine;
  }
  
  public int getAddress()
  {
    return this.address;
  }
  
  public int getByteCount()
  {
    return this.byteCount;
  }
  
  public int getChecksum()
  {
    return this.checksum;
  }
  
  public byte[] getData()
  {
    return this.data;
  }
  
  public int getRecordType()
  {
    return this.recordType;
  }
  
  public void parseLine()
    throws Exception
  {
    if (!this.rawLine.startsWith(":")) {
      throw new Exception("Doesn't start with :");
    }
    String strData = this.rawLine.substring(1);
    
    char[] charArrayData = strData.toCharArray();
    LinkedList<Byte> listData = new LinkedList();
    
    StringBuilder sb = new StringBuilder();
    for (int idx = 0; idx < charArrayData.length; idx++)
    {
      char c = charArrayData[idx];
      if (HexUtils.isHexDigit(c)) {
        sb.append(c);
      }
      switch (idx)
      {
      case 1: 
        this.byteCount = Integer.parseInt(sb.toString(), 16);
        sb.delete(0, sb.length());
        break;
      case 5: 
        this.address = Integer.parseInt(sb.toString(), 16);
        sb.delete(0, sb.length());
        break;
      case 7: 
        this.recordType = Integer.parseInt(sb.toString(), 16);
        sb.delete(0, sb.length());
      }
      if ((idx > 7) && 
        (sb.length() == 2))
      {
        int byteData = Integer.parseInt(sb.toString(), 16);
        byteData &= 0xFF;
        listData.add(Byte.valueOf((byte)byteData));
        sb.delete(0, sb.length());
      }
    }
    this.checksum = (((Byte)listData.removeLast()).byteValue() & 0xFF);
    
    this.data = new byte[listData.size()];
    for (int idx = 0; idx < this.data.length; idx++) {
      this.data[idx] = ((Byte)listData.get(idx)).byteValue();
    }
  }
}
