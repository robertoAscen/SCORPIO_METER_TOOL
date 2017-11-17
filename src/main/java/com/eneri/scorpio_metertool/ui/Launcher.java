/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eneri.scorpio_metertool.ui;

import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;

/**
 *
 * @author Roberto
 */
public class Launcher
{
  public static JFrameMain mainFrame = new JFrameMain();
  
  
  public static void main(String[] args)
  {
    EventQueue.invokeLater(new Runnable()
    {
      public void run()
      {
        Launcher.getMainFrame().setVisible(true);
        Launcher.getMainFrame().setTitle("www.eneri.com.mx Ver 2.0.1");
        Launcher.getMainFrame().setLocationRelativeTo(null);
      }

        private void setIconImage(Image icon) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    });
  }
  
  public static JFrameMain getMainFrame()
  {
    return mainFrame;
  }
}
