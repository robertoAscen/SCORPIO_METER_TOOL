/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eneri.scorpio_metertool.database;

import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JOptionPane;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 *
 * @author Roberto
 */
public class Encript
{
  private SecretKey key;
  private String skey = "";
  private Cipher desCipher;
  
  public void addKey(String value)
  {
    try
    {
      this.skey = value;
      MessageDigest md = MessageDigest.getInstance("MD5");
      byte[] digestOfPassword = md.digest(value.getBytes("utf-8"));
      byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
      this.key = new SecretKeySpec(keyBytes, "DESede");
    }
    catch (NoSuchAlgorithmException ex)
    {
      System.err.println(ex.getMessage());
    }
    catch (UnsupportedEncodingException ex)
    {
      System.err.println(ex.getMessage());
    }
  }
  
  public String getsKey()
  {
    return this.skey;
  }
  
  public String encrypt(String texto)
  {
    String value = "";
    try
    {
      this.desCipher = Cipher.getInstance("DESede");
      
      this.desCipher.init(1, this.key);
      byte[] byteDataToEncrypt = texto.getBytes();
      byte[] byteCipherText = this.desCipher.doFinal(byteDataToEncrypt);
      value = new BASE64Encoder().encode(byteCipherText);
    }
    catch (NoSuchAlgorithmException ex)
    {
      System.err.println(ex.getMessage());
    }
    catch (NoSuchPaddingException ex)
    {
      System.err.println(ex.getMessage());
    }
    catch (InvalidKeyException ex)
    {
      System.err.println(ex.getMessage());
    }
    catch (IllegalBlockSizeException ex)
    {
      System.err.println(ex.getMessage());
    }
    catch (BadPaddingException ex)
    {
      System.err.println(ex.getMessage());
    }
    return value;
  }
  
  public String decrypt(String texto)
  {
    String strDecryptedText = "";
    try
    {
      byte[] value = new BASE64Decoder().decodeBuffer(texto);
      
      this.desCipher = Cipher.getInstance("DESede");
      this.desCipher.init(2, this.key, this.desCipher.getParameters());
      byte[] byteDecryptedText = this.desCipher.doFinal(value);
      strDecryptedText = new String(byteDecryptedText);
    }
    catch (InvalidKeyException ex)
    {
      System.err.println(ex.getMessage());
    }
    catch (IllegalBlockSizeException ex)
    {
      System.err.println(ex.getMessage());
    }
    catch (BadPaddingException ex)
    {
      System.err.println(ex.getMessage());
      JOptionPane.showMessageDialog(null, "La contrase�a es incorrecta.");
    }
    catch (IOException ex)
    {
      System.err.println(ex.getMessage());
    }
    catch (InvalidAlgorithmParameterException ex)
    {
      System.err.println(ex.getMessage());
    }
    catch (NoSuchAlgorithmException ex)
    {
      System.err.println(ex.getMessage());
    }
    catch (NoSuchPaddingException ex)
    {
      System.err.println(ex.getMessage());
    }
    return strDecryptedText;
  }
}
