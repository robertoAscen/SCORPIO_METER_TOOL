/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eneri.scorpio_metertool.firmware;

/**
 *
 * @author Roberto
 */
public class ModbusRTUFrame
{
  private int frameCount = 0;
  private byte[] modbusFrame = new byte[58];
  private byte[] signatureFrame = new byte[32];
  private boolean signature = false;
  
  public ModbusRTUFrame(int frameCount, byte[] modbusFrame, boolean isSignature)
  {
    this.frameCount = frameCount;
    this.signature = isSignature;
    if (isSignature) {
      System.arraycopy(modbusFrame, 0, this.signatureFrame, 0, 32);
    } else {
      System.arraycopy(modbusFrame, 0, this.modbusFrame, 0, 58);
    }
  }
  
  public int getFrameCount()
  {
    return this.frameCount;
  }
  
  public byte[] getModbusFrame()
  {
    return this.modbusFrame;
  }
  
  public boolean isSignature()
  {
    return this.signature;
  }
  
  public void setSignature(boolean val)
  {
    this.signature = false;
  }
  
  public byte[] getSignatureFrame()
  {
    return this.signatureFrame;
  }
  
  public static byte[] getPasswordEnablingFrame()
  {
    byte[] startupFrame = new byte[8];
    
    startupFrame[0] = 0;
    startupFrame[1] = 6;
    startupFrame[2] = 0;
    startupFrame[3] = 28;
    startupFrame[4] = -61;
    startupFrame[5] = 16;
    startupFrame[6] = 24;
    startupFrame[7] = -31;
    
    return startupFrame;
  }
  
  public static byte[] getForceBootloaderFrame()
  {
    byte[] startupFrame = new byte[8];
    
    startupFrame[0] = 0;
    startupFrame[1] = 6;
    startupFrame[2] = 0;
    startupFrame[3] = 12;
    startupFrame[4] = 0;
    startupFrame[5] = 16;
    startupFrame[6] = 73;
    startupFrame[7] = -44;
    
    return startupFrame;
  }
  
  public String toString()
  {
    StringBuilder sb = new StringBuilder();
    if (this.signature) {
      for (byte b : this.signatureFrame)
      {
        int intByte = b & 0xFF;
        if (intByte < 16)
        {
          sb.append(0);
          sb.append(Integer.toHexString(intByte));
        }
        else
        {
          sb.append(Integer.toHexString(intByte));
        }
        sb.append(" ");
      }
    } else {
      for (byte b : this.modbusFrame)
      {
        int intByte = b & 0xFF;
        if (intByte < 16)
        {
          sb.append(0);
          sb.append(Integer.toHexString(intByte));
        }
        else
        {
          sb.append(Integer.toHexString(intByte));
        }
        sb.append(" ");
      }
    }
    return sb.toString();
  }
}
