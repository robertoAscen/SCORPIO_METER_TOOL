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
public class FirmwareProgress
{
  private int currentFrame;
  private int totalFrames;
  
  public FirmwareProgress(int currentFrame, int totalFrames)
  {
    this.currentFrame = currentFrame;
    this.totalFrames = totalFrames;
  }
  
  public int getCurrentFrame()
  {
    return this.currentFrame;
  }
  
  public int getTotalFrames()
  {
    return this.totalFrames;
  }
}
