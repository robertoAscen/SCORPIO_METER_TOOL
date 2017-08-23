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
public class HexUtils
{
  public static boolean isHexDigit(char c)
  {
    if ((Character.isDigit(c)) || (c == 'a') 
                               || (c == 'b') 
                               || (c == 'c') 
                               || (c == 'd') 
                               || (c == 'e') 
                               || (c == 'f') 
                               || (c == 'A') 
                               || (c == 'B') 
                               || (c == 'C') 
                               || (c == 'D') 
                               || (c == 'E') 
                               || (c == 'F')) {
      return true;
    }
    return false;
  }
}
