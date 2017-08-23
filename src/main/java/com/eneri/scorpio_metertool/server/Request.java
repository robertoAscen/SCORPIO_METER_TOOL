/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eneri.scorpio_metertool.server;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;
import java.io.PrintStream;

/**
 *
 * @author Roberto
 */
public class Request
{
  ClientResponse response = null;
  String output = "";
  boolean estate = true;
  
  public boolean requestServer(String ip, String request)
  {
    try
    {
      Client client = Client.create();
      WebResource webResource = client.resource("http://" + ip + ":8080/Eneribox/appCommand");
      
      this.response = ((ClientResponse)webResource.type("application/xml").post(ClientResponse.class, request));
      if (this.response.getStatus() != 200) {
        throw new RuntimeException("Failed : HTTP error code : " + this.response.getStatus());
      }
      System.out.println("Output from Server .... \n");
      this.output = ((String)this.response.getEntity(String.class));
      System.out.println(this.output);
    }
    catch (Exception e)
    {
      e.printStackTrace();
      this.estate = false;
    }
    return this.estate;
  }
  
  public String getResponse()
  {
    return this.output;
  }
}
