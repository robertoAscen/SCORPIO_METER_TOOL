/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eneri.scorpio_metertool.database;

import java.io.File;
import java.io.PrintStream;

/**
 *
 * @author Roberto
 */
public class User
  extends UserManager
{
  private static String id_user;
  private static String user;
  private static String password;
  private static String group;
  
  public boolean setUser(String user, String password)
  {
    boolean dato = false;
    user = user;
    password = password;
    if (loginUser(this))
    {
      dato = true;
      group = getLevel();
    }
    return dato;
  }
  
  public String getId_user()
  {
    return id_user;
  }
  
  public String getUser()
  {
    return user;
  }
  
  public String getPassword()
  {
    return password;
  }
  
  public String getGroup()
  {
    return group;
  }
  
  public boolean searchUser(String user)
  {
    boolean dato = false;
    for (String dataUser : getUsers()) {
      if (user.equals(dataUser)) {
        dato = true;
      }
    }
    return dato;
  }
  
  public void setIdUser(User user)
  {
    String id_u = getIdUser(user);
    id_user = id_u;
  }
  
  public boolean deleteDataBase()
  {
    boolean data = this.fileDatabase.delete();
    if (data) {
      System.out.println(this.fileDatabase.getName() + " is deleted!");
    } else {
      System.out.println("Delete operation is failed.");
    }
    return data;
  }
  
  public boolean createDataFile()
  {
    boolean data = false;
    try
    {
      data = this.fileDatabase.createNewFile();
      if (data)
      {
        Conection contt = new Conection();
        contt.conect();
        data = createDatabase();
      }
    }
    catch (Exception e) {}
    return data;
  }
}
