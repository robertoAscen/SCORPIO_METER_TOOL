/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eneri.scorpio_metertool.hardwarelayer;

/**
 *
 * @author Roberto
 */
public enum FlagsPasswords
{
  METER_TURN_ON(23, 35, new byte[] { 0, 0, 0, 0 }, new byte[] { -61, 0, 0, 1 }, 2, "METER_TURN_ON"),  
  METER_TURN_OFF(23, 35, new byte[] { 0, 0, 0, 1 }, new byte[] { -61, 0, 0, 1 }, 2, "METER_TURN_OFF"),  
  METER_IRMODE_REA(23, 35, new byte[] { 0, 0, 0, 2 }, new byte[] { -61, 0, 0, 2 }, 2, "METER_IRMODE_REA"), 
  METER_IRMODE_ACT(23, 35, new byte[] { 0, 0, 0, 0 }, new byte[] { -61, 0, 0, 2 }, 2, "METER_IRMODE_ACT"),  
  METER_3PHMODE_DELTA(23, 35, new byte[] { 0, 0, 0, 4 }, new byte[] { -61, 0, 0, 4 }, 2, "METER_3PHMODE_DELTA"),
  METER_3PHMODE_WYE(23, 35, new byte[] { 0, 0, 0, 0 }, new byte[] { -61, 0, 0, 4 }, 2, "METER_3PHMODE_WYE"),  
  METER_HMMODE_HM(23, 35, new byte[] { 0, 0, 0, 8 }, new byte[] { -61, 0, 0, 8 }, 2, "METER_HMMODE_HM"),  
  METER_HMMODE_OM(23, 35, new byte[] { 0, 0, 0, 0 }, new byte[] { -61, 0, 0, 8 }, 2, "METER_HMMODE_OM"),  
  METER_VOLTAGEMODE_120(23, 35, new byte[] { 0, 0, 0, 16 }, new byte[] { -61, 0, 0, 16 }, 2, "METER_VOLTAGEMODE_120"),  
  METER_VOLTAGEMODE_240(23, 35, new byte[] { 0, 0, 0, 0 }, new byte[] { -61, 0, 0, 16 }, 2, "METER_VOLTAGEMODE_240"),  
  METER_ACC_RESET(23, 35, new byte[] { 0, 0, 0, 32 }, new byte[] { -61, 0, 0, 32 }, 2, "METER_ACC_RESET"), 
  METER_FORCE_RESET(23, 35, new byte[] { 0, 0, 0, 64 }, new byte[] { -61, 0, 0, 64 }, 2, "METER_FORCE_RESET"), 
  METER_STPMC_RESET(23, 35, new byte[] { 0, 0, 0, Byte.MIN_VALUE }, new byte[] { -61, 0, 0, Byte.MIN_VALUE }, 2, "METER_STPMC_RESET"),  
  METER_DEMAND_RESET(23, 35, new byte[] { 0, 0, 1, 0 }, new byte[] { -61, 0, 1, 0 }, 2, "METER_DEMAND_RESET"),  
  METER_UPGRADE_PROCESS_ENABLE(23, 35, new byte[] { 0, 0, 2, 0 }, new byte[] { -61, 0, 2, 0 }, 2, "METER_UPGRADE_PROCESS_ENABLE"),  
  ROLLING_DEMAND_INTERVAL3MIN(23, 35, new byte[] { 0, 1, 0, 0 }, new byte[] { -61, 1, 0, 0 }, 2, "ROLLING_DEMAND_INTERVAL_3MIN"),  
  ROLLING_DEMAND_INTERVAL15MIN(23, 35, new byte[] { 0, 0, 0, 0 }, new byte[] { -61, 1, 0, 0 }, 2, "ROLLING_DEMAND_INTERVAL_15MIN"),  
  METER_FORCE_BOOTLOADER(23, 35, new byte[] { -61, 0, 0, 64 }, new byte[] { 116, -54, -16, 45 }, 2, "METER_FORCE_BOOTLOADER"),  
  METER_FORCE_BOOTLOADER_REGULAR(23, 35, new byte[] { 0, 0, 64, 0 }, new byte[] { -61, 0, 64, 0 }, 2, "METER_FORCE_BOOTLOADER_REGULAR"), 
  METER_UARTPARITY_NONE(23, 35, new byte[] { 0, 0, 0, 0 }, new byte[] { -61, 0, 64, 0 }, 2, "METER_UARTPARITY_NONE"),  
  METER_UARTPARITY_ODD(23, 35, new byte[] { 0, 0, 64, 0 }, new byte[] { -61, 0, 64, 0 }, 2, "METER_UARTPARITY_ODD"),  
  METER_UARTPARITY_EVEN(23, 35, new byte[] { 0, 0, -64, 0 }, new byte[] { -61, 0, 64, 0 }, 2, "METER_UARTPARITY_EVEN");
  
  int flagReg;
  int passwReg;
  byte[] flag;
  byte[] passwd;
  int countBytes;
  String name;
  
  private FlagsPasswords(int passwReg, int flagReg, byte[] flag, byte[] passwd, int countBytes, String name)
  {
    this.passwReg = passwReg;
    this.flagReg = flagReg;
    
    this.flag = new byte[flag.length];
    System.arraycopy(flag, 0, this.flag, 0, flag.length);
    
    this.passwd = new byte[passwd.length];
    System.arraycopy(passwd, 0, this.passwd, 0, passwd.length);
    
    this.countBytes = countBytes;
    
    this.name = name;
  }
  
  public int getPassReg()
  {
    return this.passwReg;
  }
  
  public int getFlagReg()
  {
    return this.flagReg;
  }
  
  public byte[] getFlag()
  {
    return this.flag;
  }
  
  public byte[] getPass()
  {
    return this.passwd;
  }
  
  public int getCountBytes()
  {
    return this.countBytes;
  }
  
  public String getName()
  {
    return this.name;
  }
}
