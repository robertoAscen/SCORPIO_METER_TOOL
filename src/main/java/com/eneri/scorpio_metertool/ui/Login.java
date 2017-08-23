/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eneri.scorpio_metertool.ui;

import com.eneri.scorpio_metertool.database.User;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.LayoutStyle.ComponentPlacement;

/**
 *
 * @author Roberto
 */
public class Login
  extends JFrame
{
  User us = new User();
  private JButton jButtonLogin;
  private JLabel jLabelPassword;
  private JLabel jLabelStartSession;
  private JLabel jLabelUser;
  private JLabel jLabelVersion;
  private JPasswordField jPasswordFieldPassword;
  private JTextField jTextFieldUserName;
  
  public Login()
  {
    initComponents();
    JTextFieldAndActionListener();
  }
  
  private void initComponents()
  {
    this.jTextFieldUserName = new JTextField();
    this.jPasswordFieldPassword = new JPasswordField();
    this.jButtonLogin = new JButton();
    this.jLabelStartSession = new JLabel();
    this.jLabelUser = new JLabel();
    this.jLabelPassword = new JLabel();
    this.jLabelVersion = new JLabel();
    
    setDefaultCloseOperation(3);
    
    this.jPasswordFieldPassword.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        Login.this.jPasswordFieldPasswordActionPerformed(evt);
      }
    });
    this.jPasswordFieldPassword.addKeyListener(new KeyAdapter()
    {
      public void keyPressed(KeyEvent evt)
      {
        Login.this.jPasswordFieldPasswordKeyPressed(evt);
      }
    });
    this.jButtonLogin.setText("ACCEDER");
    this.jButtonLogin.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        Login.this.jButtonLoginActionPerformed(evt);
      }
    });
    this.jLabelStartSession.setFont(new Font("Tahoma", 1, 24));
    //this.jLabelStartSession.setText("Inicio de Sesion");
    this.jLabelStartSession.setText("Inicio de Session");
    
    //this.jLabelUser.setText("Usuario:");
    this.jLabelUser.setText("User:");
    
    //this.jLabelPassword.setText("Contraseña:");
    this.jLabelPassword.setText("Password:");
    
    //this.jLabelVersion.setText("IK_Meter_Tool V1.0.21 24-08-2015");
    this.jLabelVersion.setText("Scorpio_Meter_Tool V1.0.0 25-08-2016");
    
    GroupLayout layout = new GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(47, 47, 47).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabelPassword).addComponent(this.jLabelUser, GroupLayout.Alignment.TRAILING)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jPasswordFieldPassword, -2, 151, -2).addComponent(this.jTextFieldUserName, -2, 151, -2))).addGroup(layout.createSequentialGroup().addGap(98, 98, 98).addComponent(this.jLabelStartSession)).addGroup(layout.createSequentialGroup().addGap(146, 146, 146).addComponent(this.jButtonLogin)).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(this.jLabelVersion))).addContainerGap(112, 32767)));
    
    layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(38, 38, 38).addComponent(this.jLabelStartSession).addGap(44, 44, 44).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jTextFieldUserName, -2, 20, -2).addComponent(this.jLabelUser)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jPasswordFieldPassword, -2, -1, -2).addComponent(this.jLabelPassword)).addGap(18, 18, 18).addComponent(this.jButtonLogin).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 31, 32767).addComponent(this.jLabelVersion).addContainerGap()));
    
    pack();
  }
  
  private void jButtonLoginActionPerformed(ActionEvent evt)
  {
    accessing();
    this.jTextFieldUserName.setText("");
    this.jPasswordFieldPassword.setText("");
  }
  
  private void jPasswordFieldPasswordKeyPressed(KeyEvent evt) {}
  
  private void jPasswordFieldPasswordActionPerformed(ActionEvent evt) {}
  
  public void accessing()
  {
    String passText = new String(this.jPasswordFieldPassword.getPassword());
    boolean value = this.us.setUser(this.jTextFieldUserName.getText(), passText);
    if (value)
    {
      setVisible(false);
      JFrameMain MainFrame = new JFrameMain();
      MainFrame.setVisible(true);
      MainFrame.setLocationRelativeTo(null);
    }
    else
    {
      //JOptionPane.showMessageDialog(null, "No coinciden los datos ingresados");
      JOptionPane.showMessageDialog(null, "Do not match the data entered","Error",JOptionPane.ERROR_MESSAGE);
    }
  }
  
  public void JTextFieldAndActionListener()
  {
    this.jPasswordFieldPassword.addKeyListener(new KeyAdapter()
    {
      public void keyPressed(KeyEvent evt)
      {
        if (evt.getKeyCode() == 10)
        {
          Login.this.accessing();
          Login.this.jTextFieldUserName.setText("");
          Login.this.jPasswordFieldPassword.setText("");
        }
      }
    });
  }
}
