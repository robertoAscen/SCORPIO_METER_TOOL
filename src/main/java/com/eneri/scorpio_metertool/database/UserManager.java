/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eneri.scorpio_metertool.database;

import java.io.File;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Roberto
 */
public class UserManager
  extends Encript
{
  Conection conect = new Conection();
  private String groupst;
  File fileDatabase = new File(this.conect.rutaConexion());
  
  public boolean createUser(String user, String password, String level)
  {
    this.conect.conect();
    boolean dato = false;
    addKey("1234560");
    
    String q = " INSERT INTO USERS ( id_user,user,pass,level ) VALUES ( null,'" + encrypt(user) + "','" + encrypt(password) + "','" + encrypt(level) + "');";
    try
    {
      PreparedStatement pstm = this.conect.getConexion().prepareStatement(q);
      pstm.execute();
      this.conect.getConexion().commit();
      pstm.close();
      dato = true;
    }
    catch (Exception e)
    {
      System.err.println(e.getMessage());
    }
    finally
    {
      this.conect.closeAll();
    }
    return dato;
  }
  
  public boolean updateUserPassword(int id_user, String pass)
  {
    this.conect.conect();
    boolean dato = false;
    addKey("1234560");
    String q = "UPDATE USERS SET PASS='" + encrypt(pass) + "' WHERE ID_USER=" + id_user + ";";
    try
    {
      PreparedStatement pstm = this.conect.getConexion().prepareStatement(q);
      pstm.executeUpdate();
      this.conect.getConexion().commit();
      pstm.close();
      dato = true;
    }
    catch (Exception e)
    {
      System.err.println(e.getMessage());
    }
    finally
    {
      this.conect.closeAll();
    }
    return dato;
  }
  
  public boolean updateUserGroupLevel(int id_user, String level)
  {
    this.conect.conect();
    boolean dato = false;
    addKey("1234560");
    String q = "UPDATE USERS SET LEVEL ='" + encrypt(level) + "' WHERE ID_USER=" + id_user + ";";
    try
    {
      PreparedStatement pstm = this.conect.getConexion().prepareStatement(q);
      pstm.execute();
      this.conect.getConexion().commit();
      pstm.close();
      dato = true;
    }
    catch (Exception e)
    {
      System.err.println(e.getMessage());
    }
    finally
    {
      this.conect.closeAll();
    }
    return dato;
  }
  
  public boolean deleteUser(int id_user)
  {
    this.conect.conect();
    boolean dato = false;
    addKey("1234560");
    String q = "DELETE FROM USERS WHERE ID_USER='" + id_user + "';";
    try
    {
      PreparedStatement pstm = this.conect.getConexion().prepareStatement(q);
      pstm.execute();
      this.conect.getConexion().commit();
      pstm.close();
      dato = true;
    }
    catch (Exception e)
    {
      System.err.println(e.getMessage());
    }
    finally
    {
      this.conect.closeAll();
    }
    return dato;
  }
  
  public String getIdUser(User user)
  {
    this.conect.conect();
    String id_u = "";
    try
    {
      ResultSet resultSet = null;
      addKey("1234560");
      String dato = "SELECT id_user FROM USERS WHERE USER='" + decrypt(user.getUser()) + "'  limit 1;";
      resultSet = this.conect.getEstate().executeQuery(dato);
      while (resultSet.next()) {
        id_u = decrypt(resultSet.getString("id_user"));
      }
      resultSet.close();
    }
    catch (SQLException ex)
    {
      System.out.println(ex);
    }
    finally
    {
      this.conect.closeAll();
    }
    return id_u;
  }
  
  public String getLevel()
  {
    return this.groupst;
  }
  
  public boolean loginUser(User user)
  {
    this.conect.conect();
    boolean datas = false;
    this.groupst = "";
    try
    {
      ResultSet resultSet = null;
      addKey("1234560");
      String dato = "SELECT level FROM USERS WHERE USER='" + encrypt(user.getUser()) + "' AND level=(SELECT level FROM USERS WHERE PASS='" + encrypt(user.getPassword()) + "' LIMIT 1);";
      resultSet = this.conect.getEstate().executeQuery(dato);
      while (resultSet.next())
      {
        this.groupst = decrypt(resultSet.getString("level"));
        if (!this.groupst.equals("")) {
          datas = true;
        }
      }
      resultSet.close();
    }
    catch (SQLException ex)
    {
      System.out.println(ex);
    }
    finally
    {
      this.conect.closeAll();
    }
    return datas;
  }
  
  public boolean updateEnumLevel(int id_enum, String levels)
  {
    this.conect.conect();
    boolean dato = false;
    addKey("1234560");
    String q = "UPDATE enums SET levels ='" + encrypt(levels) + "' WHERE ID_ENUM=" + id_enum + ";";
    try
    {
      PreparedStatement pstm = this.conect.getConexion().prepareStatement(q);
      pstm.execute();
      this.conect.getConexion().commit();
      pstm.close();
      dato = true;
    }
    catch (Exception e)
    {
      System.err.println(e.getMessage());
    }
    finally
    {
      this.conect.closeAll();
    }
    return dato;
  }
  
  public List<String> getUsers()
  {
    this.conect.conect();
    ArrayList List = new ArrayList();
    try
    {
      ResultSet resultSet = null;
      addKey("1234560");
      resultSet = this.conect.getEstate().executeQuery("SELECT * FROM USERS;");
      while (resultSet.next()) {
        List.add(decrypt(resultSet.getString("user")));
      }
      resultSet.close();
    }
    catch (SQLException ex)
    {
      System.out.println(ex);
    }
    finally
    {
      this.conect.closeAll();
    }
    return List;
  }
  
  public boolean createGroup(String name, String desc)
  {
    this.conect.conect();
    boolean dato = false;
    addKey("1234560");
    
    String q = " INSERT INTO GROUPS ( id_group,name,description ) VALUES ( null,'" + name + "','" + desc + "');";
    try
    {
      PreparedStatement pstm = this.conect.getConexion().prepareStatement(q);
      pstm.execute();
      this.conect.getConexion().commit();
      pstm.close();
      dato = true;
    }
    catch (Exception e)
    {
      System.err.println(e.getMessage());
    }
    finally
    {
      this.conect.closeAll();
    }
    return dato;
  }
  
  public ArrayList printTable()
  {
    this.conect.conect();
    ArrayList dataview = new ArrayList();
    try
    {
      ResultSet resultSet = null;
      addKey("1234560");
      resultSet = this.conect.getEstate().executeQuery(" SELECT * FROM users ; ");
      while (resultSet.next())
      {
        String res = resultSet.getInt("id_user") + "," + decrypt(resultSet.getString("user")) + "," + decrypt(resultSet.getString("level"));
        dataview.add(res);
      }
      resultSet.close();
    }
    catch (SQLException ex)
    {
      System.out.println(ex);
    }
    finally
    {
      this.conect.closeAll();
    }
    return dataview;
  }
  
  public ArrayList getEnumLevels()
  {
    this.conect.conect();
    ArrayList dataview = new ArrayList();
    try
    {
      ResultSet resultSet = null;
      addKey("1234560");
      resultSet = this.conect.getEstate().executeQuery(" SELECT level FROM ENUMS ; ");
      while (resultSet.next())
      {
        String res = decrypt(resultSet.getString("level"));
        dataview.add(res);
      }
      resultSet.close();
    }
    catch (SQLException ex)
    {
      System.out.println(ex);
    }
    finally
    {
      this.conect.closeAll();
    }
    return dataview;
  }
  
  public ArrayList getGroupLevels()
  {
    this.conect.conect();
    ArrayList<String> dataview = new ArrayList();
    try
    {
      ResultSet resultSet = null;
      addKey("1234560");
      resultSet = this.conect.getEstate().executeQuery(" SELECT id_group FROM GROUPS ; ");
      while (resultSet.next())
      {
        String res = Integer.toString(resultSet.getInt("id_group"));
        dataview.add(res);
      }
      resultSet.close();
    }
    catch (SQLException ex)
    {
      System.out.println(ex);
    }
    finally
    {
      this.conect.closeAll();
    }
    return dataview;
  }
  
  public ArrayList printTableEnum()
  {
    this.conect.conect();
    ArrayList<String> dataview = new ArrayList();
    try
    {
      ResultSet resultSet = null;
      addKey("1234560");
      resultSet = this.conect.getEstate().executeQuery(" SELECT * FROM ENUMS ; ");
      while (resultSet.next())
      {
        String res = resultSet.getInt("id_enum") + "," + resultSet.getString("paname") + "," + resultSet.getString("pandesc") + "," + decrypt(resultSet.getString("levels"));
        dataview.add(res);
      }
      resultSet.close();
    }
    catch (SQLException ex)
    {
      ex = 
      
        ex;System.out.println(ex);
    }
    finally {}
    return dataview;
  }
  
  public ArrayList printTableGroup()
  {
    this.conect.conect();
    ArrayList<String> dataview = new ArrayList();
    try
    {
      ResultSet resultSet = null;
      addKey("1234560");
      resultSet = this.conect.getEstate().executeQuery(" SELECT * FROM GROUPS ; ");
      while (resultSet.next())
      {
        String res = resultSet.getInt("id_group") + "," + resultSet.getString("name") + "," + resultSet.getString("description");
        dataview.add(res);
      }
      resultSet.close();
    }
    catch (SQLException ex)
    {
      System.out.println(ex);
    }
    finally
    {
      this.conect.closeAll();
    }
    return dataview;
  }
  
  public boolean createDatabase()
  {
    this.conect.conect();
    boolean datas = false;
    addKey("1234560");
    List<String> list = new ArrayList();
    list.add("CREATE TABLE groups(id_group integer primary key autoincrement not null ,name varchar not null,description varchar not null);");
    list.add("insert into groups ( id_group,name,description ) values(null,'AVANZADO','TODO');");
    
    list.add("CREATE TABLE users(id_user integer primary key autoincrement not null,user varchar not null,pass varchar not null,level varchar not null);");
    list.add("INSERT INTO users ( id_user,user,pass,level ) VALUES ( null,'" + encrypt("admin") + "','" + encrypt("admin") + "','" + encrypt("1") + "' ); ");
    
    list.add("CREATE TABLE enums(id_enum integer primary key autoincrement not null,paname varchar not null,pandesc varchar not null,levels varchar not null);");
    
    list.add("insert into enums values(null,'ItemCalibration','SUBMENU CALIBRACION','" + encrypt("1") + "');");
    list.add("insert into enums values(null,'ItemFlags','SUBMENU BANDERAS','" + encrypt("1") + "');");
    list.add("insert into enums values(null,'MenuConfiguration','MENU CONFIGURACION','" + encrypt("1") + "');");
    list.add("insert into enums values(null,'ItemReset','SUBMENU REINICIOS','" + encrypt("1") + "');");
    list.add("insert into enums values(null,'ItemAdvancedView','SUBMENU VISTA AVANZADA','" + encrypt("1") + "');");
    list.add("insert into enums values(null,'ItemSchedules','SUBMENU HORARIOS','" + encrypt("1") + "');");
    list.add("insert into enums values(null,'MenuUploadFirmware','MENU FIRMWARE','" + encrypt("1") + "');");
    list.add("insert into enums values(null,'ItemUploadFirmware','SUBMENU FIRMWARE','" + encrypt("1") + "');");
    list.add("insert into enums values(null,'MenuLoadProfile','MENU PERFIL DE CARGA','" + encrypt("1") + "');");
    list.add("insert into enums values(null,'ItemLoadProfile','SUBMENU PERFIL DE CARGA','" + encrypt("1") + "');");
    list.add("insert into enums values(null,'MenuConcentrator','SUBMENU CONCENTRADOR','" + encrypt("1") + "');");
    list.add("insert into enums values(null,'ItemReadingsConcentrator','SUBMENU LECTURAS CONCENTRADOR','" + encrypt("1") + "');");
    list.add("insert into enums values(null,'ItemMeterConcentrator','SUBMENU MEDIDORES CONCENTRADOR','" + encrypt("1") + "');");
    
    list.add("insert into enums values(null,'MenuUsers','MENU USUARIOS','" + encrypt("1") + "');");
    list.add("insert into enums values(null,'ItemAdminUsers','SUBMENU ADMINISTRAR USUARIOS','" + encrypt("1") + "');");
    try
    {
      for (String q : list)
      {
        PreparedStatement pstm = this.conect.getConexion().prepareStatement(q);
        pstm.execute();
        this.conect.getConexion().commit();
        pstm.close();
      }
      datas = true;
    }
    catch (Exception e)
    {
      datas = false;
      System.err.println(e.getMessage());
    }
    finally
    {
      this.conect.closeAll();
    }
    return datas;
  }
  
  public void closeAll()
  {
    Conection contt = new Conection();
    try
    {
      contt.closeAll();
    }
    catch (Exception s)
    {
      System.out.println(s);
    }
  }
}
