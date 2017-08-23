/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eneri.scorpio_metertool.database;

import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author Roberto
 */
public class Conection
{
  private static Connection con = null;
  private static Statement state = null;
  private String db = "dbCrypto.sqlite";
  
  public String rutaConexion()
  {
    return this.db;
  }
  
  public void conect()
  {
    try
    {
      Class.forName("org.sqlite.JDBC");
      con = DriverManager.getConnection("jdbc:sqlite:" + this.db);
      con.setAutoCommit(false);
      System.out.println("Opened database successfully");
      
      state = (Statement) con.createStatement();
    }
    catch (Exception e)
    {
      JOptionPane.showMessageDialog(null, e.getMessage());
      System.err.println(e.getClass().getName() + ": " + e.getMessage());
    }
  }
  
  public Statement getEstate()
  {
    return state;
  }
  
  public void setEstateClose()
  {
    try
    {
      state.close();
    }
    catch (Exception e)
    {
      System.out.println(e);
    }
  }
  
  public Connection getConexion()
  {
    return con;
  }
  
  public void setConexionClose()
  {
    try
    {
      con.close();
    }
    catch (Exception e)
    {
      System.out.println(e);
    }
  }
  
  public void closeAll()
  {
    try
    {
      if (((state == null ? 1 : 0) & (con == null ? 1 : 0)) != 0)
      {
        System.out.println("Closed database previously");
      }
      else if ((!state.isClosed()) && (!con.isClosed()))
      {
        state.close();
        state = null;
        con.close();
        con = null;
        System.gc();
        System.out.println("Closed database successfully");
      }
      else
      {
        System.out.println("Closed database previously");
      }
    }
    catch (Exception e)
    {
      JOptionPane.showMessageDialog(null, e.getMessage());
      System.err.println(e.getClass().getName() + ": " + e.getMessage());
    }
  }
}
