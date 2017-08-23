/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eneri.scorpio_metertool.firmware;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;

/**
 *
 * @author Roberto
 */
public class FirmwareProcess
{
  private static final int MEMORY_MAP_FLASH_APP_SIZE = 54272;
  private static char[] nBytes = new char[2];
  private static char[] address = new char[4];
  private static char[] dataFrame = new char[64];
  private static int count = 1;
  private static StringBuilder sb = new StringBuilder();
  private static int byteCountint;
  private static int addressint;
  private static int recordTypeint;
  private byte[] data;
  private int checksum;
  private String rawLine;
  private byte[] memoryMap = new byte[54272];
  LinkedList<Byte> listData = new LinkedList();
  
  public void firmwareProcesor(String fileData)
  {
    Path path = Paths.get(fileData + "/", new String[0]);
    for (int idx = 0; idx < this.memoryMap.length; idx++) {
      this.memoryMap[idx] = -1;
    }
    try
    {
      InputStream in = Files.newInputStream(path, new OpenOption[0]);Throwable localThrowable3 = null;
      try
      {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));Throwable localThrowable4 = null;
        try
        {
          String line = null;
          byte[] address;
          while ((line = reader.readLine()) != null)
          {
            System.out.println(line);
            
            address = toHexProcesor(line);
          }
        }
        catch (Throwable localThrowable1)
        {
          localThrowable4 = localThrowable1;throw localThrowable1;
        }
        finally {}
      }
      catch (Throwable localThrowable2)
      {
        localThrowable3 = localThrowable2;throw localThrowable2;
      }
      finally
      {
        if (in != null) {
          if (localThrowable3 != null) {
            try
            {
              in.close();
            }
            catch (Throwable x2)
            {
              localThrowable3.addSuppressed(x2);
            }
          } else {
            in.close();
          }
        }
      }
    }
    catch (IOException x)
    {
      System.err.println(x);
    }
  }
  
  public byte[] toHexProcesor(String lineValue)
  {
    byte[] val = new byte[0];
    if (!lineValue.startsWith(":")) {
      System.out.print("Can't be start with :");
    }
    String strData = lineValue.substring(1);
    
    char[] charArrayData = strData.toCharArray();
    
    return val;
  }
}
