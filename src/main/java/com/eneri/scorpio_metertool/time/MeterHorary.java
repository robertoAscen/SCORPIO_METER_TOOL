/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eneri.scorpio_metertool.time;

/**
 *
 * @author Roberto
 */
public enum MeterHorary
{
  UTC_NEG_12_00(0, "-120", "-12"),  
  UTC_NEG_11_00(1, "-110", "-11"),  
  UTC_NEG_10_00(2, "-100", "-10"),  
  UTC_NEG_09_30(3, "-9-30", "-9:30"),  
  UTC_NEG_09_00(4, "-90", "-9"),  
  UTC_NEG_08_00(5, "-80", "-8"),  
  UTC_NEG_07_00(6, "-70", "-7"),  
  UTC_NEG_06_00(7, "-60", "-6"),  
  UTC_NEG_05_00(8, "-50", "-5"),  
  UTC_NEG_04_30(9, "-4-30", "-4:30"),  
  UTC_NEG_04_00(10, "-40", "-4"),  
  UTC_NEG_03_30(11, "-3-30", "-3:30"),  
  UTC_NEG_03_00(12, "-30", "-3"),  
  UTC_NEG_02_00(13, "-20", "-2"),  
  UTC_NEG_01_00(14, "-10", "-1"),  
  UTC_00_00(15, "+00", "00"),  
  UTC_POS_01_00(16, "+10", "+1"),  
  UTC_POS_02_00(17, "+20", "+2"),  
  UTC_POS_03_00(18, "+30", "+3"),  
  UTC_POS_03_30(19, "+3-30", "+3:30"),  
  UTC_POS_04_00(20, "+40", "+4"),  
  UTC_POS_04_30(21, "+4-30", "+4:30"),  
  UTC_POS_05_00(22, "+50", "+5"),  
  UTC_POS_05_30(23, "+5-30", "+5:30"),  
  UTC_POS_05_45(24, "+5-45", "+5:45"),  
  UTC_POS_06_00(25, "+60", "+6"),  
  UTC_POS_06_30(26, "+6-30", "+6:30"),  
  UTC_POS_07_00(27, "+70", "+7"),  
  UTC_POS_08_00(28, "+80", "+8"),  
  UTC_POS_08_45(29, "+8-45", "+8:45"),  
  UTC_POS_09_00(30, "+90", "+9"),  
  UTC_POS_09_30(31, "+9-30", "+9:30"),  
  UTC_POS_10_00(32, "+10", "+10"),  
  UTC_POS_10_30(33, "+10-30", "+10:30"),  
  UTC_POS_11_00(34, "+110", "+11"),  
  UTC_POS_11_30(35, "+11-30", "+11:30"),  
  UTC_POS_12_00(36, "+120", "+12"),  
  UTC_POS_12_45(37, "+12-45", "+12:45"),  
  UTC_POS_13_00(38, "+130", "+13"),  
  UTC_POS_14_00(39, "+140", "+14"),  
  MAX_UTC(40, "+00", "00");
  
  private int value;
  private String time;
  private String timeNameOnly;
  
  private MeterHorary(int values, String times, String timeNameOnly)
  {
    this.value = values;
    this.time = times;
    this.timeNameOnly = timeNameOnly;
  }
  
  public int getValue()
  {
    return this.value;
  }
  
  public String getTime()
  {
    return this.time;
  }
  
  public String getTimeNameOnly()
  {
    return this.timeNameOnly;
  }
}
