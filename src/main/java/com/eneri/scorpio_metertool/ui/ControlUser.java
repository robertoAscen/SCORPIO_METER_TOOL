/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eneri.scorpio_metertool.ui;

import com.eneri.scorpio_metertool.database.User;
import com.eneri.scorpio_metertool.database.UserManager;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingUtilities;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Roberto
 */
public class ControlUser
  extends JPanel
{
  private DefaultTableModel defaultable;
  private JScrollPane scrollpane;
  private int valueRow;
  private String valueRowName;
  private DefaultTableModel defaultablePerms;
  private JScrollPane scrollpanePerms;
  private int valueRowPerms;
  private String valueRowNamePerms;
  JTextField textName = new JTextField();
  JTextField textDesc = new JTextField();
  Container contentPanePerms = new Container();
  DefaultTableModel defaultableShowPerms = new DefaultTableModel();
  UserManager usr = new UserManager();
  private JButton jButton1;
  private JButton jButtonAddUser;
  private JButton jButtonCreateGroups;
  private JButton jButtonDeleteUsers;
  private JButton jButtonShowUsers;
  private JButton jButtonUpdatePassword;
  private JButton jButtonViewPerm;
  private JComboBox jComboBoxTypeUser;
  private JLabel jLabelNameUserControl;
  private JLabel jLabelPassword;
  private JLabel jLabelTypeUser;
  private JLabel jLabelUser;
  private JPasswordField jPasswordFieldPassword;
  private JScrollPane jScrollPane1;
  private JTable jTableUsers;
  private JTextField jTextFieldUserName;
  
  public ControlUser()
  {
    initComponents();
    for (Object gp : this.usr.printTableGroup()) {
      this.jComboBoxTypeUser.addItem(gp.toString());
    }
  }
  
  private void initComponents()
  {
    this.jLabelNameUserControl = new JLabel();
    this.jLabelUser = new JLabel();
    this.jTextFieldUserName = new JTextField();
    this.jLabelPassword = new JLabel();
    this.jPasswordFieldPassword = new JPasswordField();
    this.jLabelTypeUser = new JLabel();
    this.jComboBoxTypeUser = new JComboBox();
    this.jButtonAddUser = new JButton();
    this.jScrollPane1 = new JScrollPane();
    this.jTableUsers = new JTable();
    this.jButtonShowUsers = new JButton();
    this.jButtonDeleteUsers = new JButton();
    this.jButtonUpdatePassword = new JButton();
    this.jButton1 = new JButton();
    this.jButtonViewPerm = new JButton();
    this.jButtonCreateGroups = new JButton();
    
    setPreferredSize(new Dimension(950, 600));
    
    this.jLabelNameUserControl.setFont(new Font("Ubuntu", 1, 18));
    //this.jLabelNameUserControl.setText("CONTROL DE USUARIOS");
    this.jLabelNameUserControl.setText("USER CONTROL");
    this.jLabelNameUserControl.setName("jLabelTitleUserControl");
    
    this.jLabelUser.setText("User:");
    //this.jLabelUser.setText("Usuario:");
    this.jLabelUser.setName("jLabelUser");
    
    this.jTextFieldUserName.setToolTipText("");
    this.jTextFieldUserName.setName("jTextFieldUserName");
    
    this.jLabelPassword.setText("Password");
    //this.jLabelPassword.setText("Contraseña");
    this.jLabelPassword.setName("jLabelPassword");
    
    this.jPasswordFieldPassword.setName("jPasswordFieldPassword");
    
    //this.jLabelTypeUser.setText("Tipo de usuario");
    this.jLabelTypeUser.setText("Type of User");
    this.jLabelTypeUser.setName("jLabelTypeUser");
    
    this.jComboBoxTypeUser.setName("jComboBoxTypeUser");
    
    this.jButtonAddUser.setText("add USER");
    //this.jButtonAddUser.setText("AGREGAR USUARIO");
    this.jButtonAddUser.setName("jButtonAddUser");
    this.jButtonAddUser.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        ControlUser.this.jButtonAddUserActionPerformed(evt);
      }
    });
    this.jTableUsers.setModel(new DefaultTableModel(new Object[0][], new String[0]));
    
    this.jTableUsers.setName("jTableUsers");
    this.jScrollPane1.setViewportView(this.jTableUsers);
    
    this.jButtonShowUsers.setText("SHOW TABLE OF USERS");
    //this.jButtonShowUsers.setText("MOSTRAR TABLA DE USUARIOS");
    this.jButtonShowUsers.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        ControlUser.this.jButtonShowUsersActionPerformed(evt);
      }
    });
    this.jButtonDeleteUsers.setText("DELETE USER");
    //this.jButtonDeleteUsers.setText("ELIMINAR USUARIO");
    this.jButtonDeleteUsers.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        ControlUser.this.jButtonDeleteUsersActionPerformed(evt);
      }
    });
    this.jButtonUpdatePassword.setText("UPDATING PASSWORD");
    //this.jButtonUpdatePassword.setText("ACTUALIZAR CONTRASEÑA");
    this.jButtonUpdatePassword.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        ControlUser.this.jButtonUpdatePasswordActionPerformed(evt);
      }
    });
    this.jButton1.setForeground(new Color(255, 0, 0));
    this.jButton1.setText("RESTORE DATABASE");
    //this.jButton1.setText("RESTAURAR BASE DE DATOS");
    this.jButton1.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        ControlUser.this.jButton1ActionPerformed(evt);
      }
    });
    this.jButtonViewPerm.setText("SHOW PERMITS");
    //this.jButtonViewPerm.setText("VER PERMISOS");
    this.jButtonViewPerm.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        ControlUser.this.jButtonViewPermActionPerformed(evt);
      }
    });
    this.jButtonCreateGroups.setText("CREATE GROUPS");
    //this.jButtonCreateGroups.setText("CREAR GRUPOS");
    this.jButtonCreateGroups.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        ControlUser.this.jButtonCreateGroupsActionPerformed(evt);
      }
    });
    GroupLayout layout = new GroupLayout(this);
    setLayout(layout);
    layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.jScrollPane1).addContainerGap()).addGroup(layout.createSequentialGroup().addComponent(this.jLabelUser).addGap(6, 6, 6).addComponent(this.jTextFieldUserName, -2, 119, -2).addGap(1, 1, 1).addComponent(this.jLabelPassword).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jPasswordFieldPassword, -2, 119, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jLabelTypeUser).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jComboBoxTypeUser, -2, 279, -2).addGap(27, 27, 27).addComponent(this.jButtonAddUser).addGap(0, 77, 32767)))).addGroup(layout.createSequentialGroup().addGap(345, 345, 345).addComponent(this.jLabelNameUserControl).addContainerGap(-1, 32767)).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.jButtonShowUsers, -1, -1, 32767).addComponent(this.jButtonViewPerm, -1, -1, 32767)).addGap(172, 172, 172).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.jButtonUpdatePassword, -1, 207, 32767).addComponent(this.jButtonCreateGroups, -1, -1, 32767)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, 32767).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.jButton1, -1, -1, 32767).addComponent(this.jButtonDeleteUsers, -1, -1, 32767)).addContainerGap()));
    
    layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(this.jLabelNameUserControl).addGap(21, 21, 21).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabelUser).addComponent(this.jTextFieldUserName, -2, -1, -2).addComponent(this.jLabelPassword).addComponent(this.jPasswordFieldPassword, -2, -1, -2).addComponent(this.jLabelTypeUser).addComponent(this.jComboBoxTypeUser, -2, -1, -2).addComponent(this.jButtonAddUser)).addGap(18, 18, 18).addComponent(this.jScrollPane1, -1, 418, 32767).addGap(18, 18, 18).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jButtonShowUsers).addComponent(this.jButtonUpdatePassword).addComponent(this.jButtonDeleteUsers)).addGap(21, 21, 21).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jButtonViewPerm).addComponent(this.jButtonCreateGroups)).addComponent(this.jButton1, -2, 12, -2))));
  }
  
  private void jButtonAddUserActionPerformed(ActionEvent evt)
  {
    User us = new User();
    String user = this.jTextFieldUserName.getText();
    String password = new String(this.jPasswordFieldPassword.getPassword());
    String[] selected = this.jComboBoxTypeUser.getSelectedItem().toString().split(",");
    String id_group = selected[0];
    
    Pattern p = Pattern.compile("([\\s])");
    Matcher m = p.matcher(this.jTextFieldUserName.getText());
    Matcher m2 = p.matcher(password);
    if ((this.jTextFieldUserName.getText().equals("") | password.equals("") | m.find() | m2.find() | this.jTextFieldUserName.getText().trim().length() > 10 | password.trim().length() > 10))
    {
      JOptionPane.showMessageDialog(null, "Check the data to insert, The username and password can not have more than 10 digits, only numbers and letters and no spaces","Error",JOptionPane.ERROR_MESSAGE);
      //JOptionPane.showMessageDialog(null, "Revisa los datos a insertar, El usuario y contraseña no pueden tener MAS DE 10 DIGITOS, SOLO NUMEROS Y LETRAS Y SIN ESPACIOS");
      return;
    }
    if (!us.searchUser(user))
    {
      boolean value = us.createUser(this.jTextFieldUserName.getText(), password, id_group);
      if (value == true)
      {
        //JOptionPane.showMessageDialog(null, "Usuario (" + this.jTextFieldUserName.getText() + ") creado correctamente");
        JOptionPane.showMessageDialog(null, "User (" + this.jTextFieldUserName.getText() + ") create succesful","Information",JOptionPane.INFORMATION_MESSAGE);
        createFrame();
      }
    }
    else
    {
      //JOptionPane.showMessageDialog(null, "Ya existe un usuario registrado " + this.jTextFieldUserName.getText());
      JOptionPane.showMessageDialog(null, "a user already exists registered " + this.jTextFieldUserName.getText(),"Error",JOptionPane.ERROR_MESSAGE);
    }
    this.jTextFieldUserName.setText("");
    this.jPasswordFieldPassword.setText("");
    this.jComboBoxTypeUser.setSelectedIndex(0);
  }
  
  private void jButtonShowUsersActionPerformed(ActionEvent evt)
  {
    createFrame();
  }
  
  private void jButtonDeleteUsersActionPerformed(ActionEvent evt)
  {
    UserManager usman = new UserManager();
    if (getSelectedRow() == 1)
    {
      //JOptionPane.showMessageDialog(null, "No puedes eliminar (" + getSelectedRowName() + ")");
      JOptionPane.showMessageDialog(null, "You can not delete (" + getSelectedRowName() + ")","Error",JOptionPane.ERROR_MESSAGE);
      return;
    }
    if (getSelectedRow() == 0)
    {
      //JOptionPane.showMessageDialog(null, "Selecciona algun usuario");
      JOptionPane.showMessageDialog(null, "Select any user","Information",JOptionPane.INFORMATION_MESSAGE);
      return;
    }
    boolean value = usman.deleteUser(getSelectedRow());
    if (value)
    {
      //JOptionPane.showMessageDialog(null, "Usuario eliminado correctamente (" + getSelectedRowName() + ")");
      JOptionPane.showMessageDialog(null, "User delete succesful (" + getSelectedRowName() + ")","Information",JOptionPane.INFORMATION_MESSAGE);
      createFrame();
    }
  }
  
  private void jButtonUpdatePasswordActionPerformed(ActionEvent evt)
  {
    SwingUtilities.invokeLater(new Runnable()
    {
      public void run()
      {
        //String newPassword = JOptionPane.showInputDialog("Inserta contraseña de usuario:", null);
        String newPassword = JOptionPane.showInputDialog("Enter password:", null);
        if (ControlUser.this.getSelectedRow() == 0)
        {
          //JOptionPane.showMessageDialog(null, "Selecciona algun usuario");
          JOptionPane.showMessageDialog(null, "Select any user","Information",JOptionPane.INFORMATION_MESSAGE);
          return;
        }
        if (((newPassword != null ? 1 : 0) & (!newPassword.equals("") ? 1 : 0)) != 0)
        {
          UserManager usman = new UserManager();
          boolean value = usman.updateUserPassword(ControlUser.this.getSelectedRow(), newPassword);
          if (value) {
            //JOptionPane.showMessageDialog(null, "Contraseña actualizada correctamente para el usuario (" + ControlUser.this.getSelectedRowName() + ")");
            JOptionPane.showMessageDialog(null, "Password successfully updated user (" + ControlUser.this.getSelectedRowName() + ")","Information",JOptionPane.INFORMATION_MESSAGE);
          } else {
            //JOptionPane.showMessageDialog(null, "Error actualizando contraseña, revise la seleccion de usuario o la base de datos");
            JOptionPane.showMessageDialog(null, "Error updating password , check the user selection or database","Error",JOptionPane.ERROR_MESSAGE);
          }
        }
        else
        {
          //JOptionPane.showMessageDialog(null, "Inserta contraseña de usuario");
          JOptionPane.showMessageDialog(null, "Enter Password","Warning",JOptionPane.WARNING_MESSAGE);
        }
      }
    });
  }
  
  private void jButton1ActionPerformed(ActionEvent evt)
  {
    User usss = new User();
    usss.closeAll();
    boolean dat = usss.deleteDataBase();
    boolean datcreate = usss.createDataFile();
    if (datcreate)
    {
      //JOptionPane.showMessageDialog(null, "La base de datos se restauro correctamente.");
      JOptionPane.showMessageDialog(null, "The database is restored correctly","Information",JOptionPane.INFORMATION_MESSAGE);
      createFrame();
      this.jComboBoxTypeUser.removeAllItems();
      for (Object gp : this.usr.printTableGroup()) {
        this.jComboBoxTypeUser.addItem(gp.toString());
      }
    }
  }
  
  private void jButtonViewPermActionPerformed(ActionEvent evt)
  {
    UserManager usr = new UserManager();
    //final JFrame framShowPerms = new JFrame("Permisos");
    final JFrame framShowPerms = new JFrame("Permissions");
    JMenuBar menuBar = new JMenuBar();
    JMenu menu = new JMenu("Menu");
    JPanel panel1 = new JPanel();
    JCheckBox[] checkboxes = new JCheckBox[51];
    JButton but = new JButton();
    final JTable jtable = new JTable(this.defaultablePerms);
    Container contentPanePerms = new Container();
    
    contentPanePerms = framShowPerms.getContentPane();
    this.scrollpanePerms = new JScrollPane();
    
    //but.setText("Guardar");
    but.setText("Save");
    menuBar.add(menu);
    JMenuItem item = new JMenuItem("Exit");
    item.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        framShowPerms.setVisible(false);
      }
    });
    this.defaultablePerms = new DefaultTableModel((Object[][])null, printTableEnum())
    {
      public boolean isCellEditable(int row, int column)
      {
        switch (column)
        {
        case 3: 
          return true;
        }
        return false;
      }
    };
    for (Object compval : usr.printTableEnum())
    {
      String[] arrData = new String[usr.printTableEnum().size()];
      arrData = compval.toString().split(",");
      this.defaultablePerms.addRow(arrData);
    }
    jtable.addMouseListener(new MouseAdapter()
    {
      public void mousePressed(MouseEvent e)
      {
        int row = jtable.rowAtPoint(e.getPoint());
        int column = jtable.columnAtPoint(e.getPoint());
        if ((row >= 0) && (column >= 0))
        {
          int dato = Integer.parseInt(jtable.getValueAt(row, 0).toString());
          String name = jtable.getValueAt(row, 1).toString();
          ControlUser.this.setSelectedRowPerms(dato);
          ControlUser.this.setSelectedRowNamePerms(name);
        }
      }
    });
    this.defaultablePerms.addTableModelListener(new TableModelListener()
    {
      public void tableChanged(TableModelEvent e)
      {
        System.out.println(ControlUser.this.defaultablePerms.getValueAt(e.getFirstRow(), e.getColumn()));
        String value = ControlUser.this.defaultablePerms.getValueAt(e.getFirstRow(), e.getColumn()).toString();
        ControlUser.this.updateGroupEnum(value);
      }
    });
    jtable.setModel(this.defaultablePerms);
    jtable.setPreferredScrollableViewportSize(new Dimension(800, 350));
    this.scrollpanePerms.add(jtable);
    this.scrollpanePerms.setViewportView(jtable);
    panel1.add(this.scrollpanePerms);
    contentPanePerms.add(panel1);
    framShowPerms.setMinimumSize(new Dimension(900, 400));
    framShowPerms.setSize(500, 500);
    framShowPerms.setBackground(Color.BLACK);
    //framShowPerms.setTitle("Permisos");
    framShowPerms.setTitle("Permissions");
    framShowPerms.add(panel1);
    framShowPerms.pack();
    framShowPerms.setVisible(true);
    framShowPerms.setLocationRelativeTo(null);
  }
  
  private void jButtonCreateGroupsActionPerformed(ActionEvent evt)
  {
    //final JFrame framShow = new JFrame("Grupos");
    final JFrame framShow = new JFrame("Groups");
    JMenuBar menuBar = new JMenuBar();
    JMenu menu = new JMenu("Menu");
    JPanel panel1 = new JPanel();
    JScrollPane scrollpaneShow = new JScrollPane();
    JTable jtable = new JTable(this.defaultableShowPerms);
    
    this.contentPanePerms = framShow.getContentPane();
    this.defaultableShowPerms = new DefaultTableModel((Object[][])null, printTableGroup());
    
    JLabel jlabelName = new JLabel();
    JLabel jlabelDescript = new JLabel();
    JButton butAdd = new JButton();
    //butAdd.setText("Guardar");
    butAdd.setText("Save");
    //jlabelName.setText("Nombre: ");
    jlabelName.setText("Name: ");
    //jlabelDescript.setText("Descripcion: ");
    jlabelDescript.setText("Description: ");
    jlabelName.setBounds(50, 500, 150, 20);
    jlabelDescript.setBounds(270, 500, 150, 20);
    this.textName.setBounds(100, 500, 150, 20);
    this.textDesc.setBounds(350, 500, 420, 20);
    butAdd.setBounds(850, 500, 140, 20);
    butAdd.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        boolean value = ControlUser.this.usr.createGroup(ControlUser.this.textName.getText(), ControlUser.this.textDesc.getText());
        if (value)
        {
          //JOptionPane.showMessageDialog(null, "grupo [ " + ControlUser.this.textName.getText() + " ] creado exitosamente.");
          JOptionPane.showMessageDialog(null, "group [ " + ControlUser.this.textName.getText() + " ] create succesful.","Information",JOptionPane.INFORMATION_MESSAGE);
          
          ControlUser.this.defaultableShowPerms.getDataVector().removeAllElements();
          ControlUser.this.defaultableShowPerms.fireTableDataChanged();
          for (Object compval : ControlUser.this.usr.printTableGroup())
          {
            String[] arrData = new String[ControlUser.this.usr.printTableGroup().size()];
            arrData = compval.toString().split(",");
            ControlUser.this.defaultableShowPerms.addRow(arrData);
          }
          ControlUser.this.jComboBoxTypeUser.removeAllItems();
          for (Object gp : ControlUser.this.usr.printTableGroup()) {
            ControlUser.this.jComboBoxTypeUser.addItem(gp.toString());
          }
          ControlUser.this.textName.setText("");
          ControlUser.this.textDesc.setText("");
        }
        else
        {
          //JOptionPane.showMessageDialog(null, "Error, revisa el nombre, descripcion o base de datos.");
          JOptionPane.showMessageDialog(null, "Error, Check the name, description or database","Error",JOptionPane.ERROR_MESSAGE);
          ControlUser.this.textName.setText("");
          ControlUser.this.textDesc.setText("");
        }
      }
    });
    menuBar.add(menu);
    JMenuItem item = new JMenuItem("Exit");
    item.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        framShow.setVisible(false);
      }
    });
    for (Object compval : this.usr.printTableGroup())
    {
      String[] arrData = new String[this.usr.printTableGroup().size()];
      arrData = compval.toString().split(",");
      this.defaultableShowPerms.addRow(arrData);
    }
    jtable.setModel(this.defaultableShowPerms);
    jtable.setPreferredScrollableViewportSize(new Dimension(900, 450));
    scrollpaneShow.add(jtable);
    scrollpaneShow.setViewportView(jtable);
    panel1.add(scrollpaneShow);
    framShow.add(jlabelName);
    framShow.add(jlabelDescript);
    framShow.add(this.textDesc);
    framShow.add(this.textName);
    framShow.add(butAdd);
    framShow.setMinimumSize(new Dimension(1000, 600));
    framShow.setSize(1000, 600);
    framShow.setBackground(Color.BLACK);
    //framShow.setTitle("Permisos");
    framShow.setTitle("Permissions");
    framShow.add(panel1);
    framShow.pack();
    framShow.setVisible(true);
    framShow.setLocationRelativeTo(null);
  }
  
  public void createFrame()
  {
    this.scrollpane = new JScrollPane();
    this.defaultable = new DefaultTableModel((Object[][])null, getColumnas())
    {
      public boolean isCellEditable(int row, int column)
      {
        switch (column)
        {
        case 2: 
          return true;
        }
        return false;
      }
    };
    ArrayList dataUsers = new ArrayList();
    UserManager usman = new UserManager();
    dataUsers = usman.printTable();
    String[] arrData = new String[0];
    for (Object dataUs : dataUsers)
    {
      arrData = dataUs.toString().split(",");
      this.defaultable.addRow(arrData);
    }
    this.jTableUsers.addMouseListener(new MouseAdapter()
    {
      public void mousePressed(MouseEvent e)
      {
        int row = ControlUser.this.jTableUsers.rowAtPoint(e.getPoint());
        int column = ControlUser.this.jTableUsers.columnAtPoint(e.getPoint());
        if ((row >= 0) && (column >= 0))
        {
          int dato = Integer.parseInt(ControlUser.this.jTableUsers.getValueAt(row, 0).toString());
          String name = ControlUser.this.jTableUsers.getValueAt(row, 1).toString();
          ControlUser.this.setSelectedRow(dato);
          ControlUser.this.setSelectedRowName(name);
        }
      }
    });
    this.defaultable.addTableModelListener(new TableModelListener()
    {
      public void tableChanged(TableModelEvent e)
      {
        System.out.println(ControlUser.this.defaultable.getValueAt(e.getFirstRow(), e.getColumn()));
        String value = ControlUser.this.defaultable.getValueAt(e.getFirstRow(), e.getColumn()).toString();
        ControlUser.this.updateGroup(value);
      }
    });
    this.jTableUsers.setModel(this.defaultable);
    this.jTableUsers.repaint();
    this.scrollpane.repaint();
    setVisible(true);
  }
  
  public void setSelectedRow(int value)
  {
    this.valueRow = value;
  }
  
  public int getSelectedRow()
  {
    return this.valueRow;
  }
  
  public void setSelectedRowName(String name)
  {
    this.valueRowName = name;
  }
  
  public String getSelectedRowName()
  {
    return this.valueRowName;
  }
  
  public void updateGroup(String valueRow)
  {
    UserManager usman = new UserManager();
    if (getSelectedRow() == 1)
    {
      //JOptionPane.showMessageDialog(null, "No puedes cambiar de Grupo para el usuario (" + getSelectedRowName() + ")");
      JOptionPane.showMessageDialog(null, "You can not change User Group (" + getSelectedRowName() + ")","Error",JOptionPane.WARNING_MESSAGE);
      return;
    }
    if (!valueRow.equals(""))
    {
      ArrayList arrGroups = new ArrayList();
      arrGroups = this.usr.getGroupLevels();
      try
      {
        if (!arrGroups.contains(valueRow))
        {
          //JOptionPane.showMessageDialog(null, "Error no existe el grupo " + valueRow);
          JOptionPane.showMessageDialog(null, "Error no group " + valueRow,"Error",JOptionPane.ERROR_MESSAGE);
          return;
        }
      }
      catch (Exception e)
      {
        return;
      }
      boolean value = usman.updateUserGroupLevel(getSelectedRow(), valueRow);
      if (value) {
        //JOptionPane.showMessageDialog(null, "Grupo actualizado correctamente para el usuario (" + getSelectedRowName() + ")");
        JOptionPane.showMessageDialog(null, "Group updated successfully for user (" + getSelectedRowName() + ")","Information",JOptionPane.INFORMATION_MESSAGE);
      } else {
        JOptionPane.showMessageDialog(null, "Error updating Group , review the selection of user or database","Error",JOptionPane.ERROR_MESSAGE);
      }
    }
    else
    {
      //JOptionPane.showMessageDialog(null, "Error no dejes en blanco el grupo y solo puedes escribir numeros");
      JOptionPane.showMessageDialog(null, "Error not leave blank the group and can only write numbers","Error",JOptionPane.ERROR_MESSAGE);
    }
  }
  
  public void setSelectedRowPerms(int value)
  {
    this.valueRowPerms = value;
  }
  
  public int getSelectedRowPerms()
  {
    return this.valueRowPerms;
  }
  
  public void setSelectedRowNamePerms(String name)
  {
    this.valueRowNamePerms = name;
  }
  
  public String getSelectedRowNamePerms()
  {
    return this.valueRowNamePerms;
  }
  
  public void updateGroupEnum(String valueRowPer)
  {
    UserManager usman = new UserManager();
    boolean continu = false;
    if (!(valueRowPer.matches("(1)+.*(-\\d)") | valueRowPer.matches("(1)+.*(-\\d\\d)") | valueRowPer.matches("(1)")))
    {
      //JOptionPane.showMessageDialog(null, "Error esta mal escrito [ " + valueRowPer + " ]  No puedes borrar [ 1 ]   Ejemplo: 1-2-3-4 o siemplemente [ 1 ] ");
      JOptionPane.showMessageDialog(null, "Error, this misspelled [ " + valueRowPer + " ]  you can not delete [ 1 ]   Example: 1-2-3-4 o siemplemente [ 1 ] ","Error",JOptionPane.ERROR_MESSAGE);
      return;
    }
    if (!valueRowPer.equals(""))
    {
      ArrayList arrGroups = new ArrayList();
      arrGroups = this.usr.getGroupLevels();
      String[] valPerms = new String[0];
      try
      {
        valPerms = valueRowPer.split("-");
        for (String valPerm : valPerms) {
          if (!arrGroups.contains(valPerm))
          {
            //JOptionPane.showMessageDialog(null, "Error no existe el grupo " + valPerm);
            JOptionPane.showMessageDialog(null, "Error no group " + valPerm,"Error",JOptionPane.ERROR_MESSAGE);
            return;
          }
        }
      }
      catch (Exception e)
      {
        return;
      }
      boolean value = usman.updateEnumLevel(getSelectedRowPerms(), valueRowPer);
      if (value) {
        //JOptionPane.showMessageDialog(null, "Grupo actualizado correctamente para  (" + getSelectedRowNamePerms() + ")");
        JOptionPane.showMessageDialog(null, "Group updated successfully for (" + getSelectedRowNamePerms() + ")","Information",JOptionPane.INFORMATION_MESSAGE);
      } else {
        //JOptionPane.showMessageDialog(null, "Error actualizando Grupo , revise la seleccion de usuario o la base de datos");
        JOptionPane.showMessageDialog(null, "Error updating Group , review the selection of user or database","Error",JOptionPane.ERROR_MESSAGE);
      }
    }
    else
    {
      JOptionPane.showMessageDialog(null, "Error","Error",JOptionPane.ERROR_MESSAGE);
    }
  }
  
  public String[] getColumnas()
  {
    String[] columna = { "ID_USUARIO", "USUARIO", "NIVEL" };
    return columna;
  }
  
  public String[] printTableEnum()
  {
    String[] columna = { "ID_ENUM", "NOMBRE", "DESCRIPCION", "NIVELES" };
    return columna;
  }
  
  public String[] printTableGroup()
  {
    String[] columna = { "ID_GROUP", "NOMBRE", "DESCRIPCION" };
    return columna;
  }
}
