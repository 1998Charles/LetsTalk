// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

package com.example.letstalk.simplechat1.client;

import com.example.letstalk.simplechat1.ocsf.client.*;
import com.example.letstalk.simplechat1.common.*;
import java.io.*;

/**
 * This class overrides some of the methods defined in the abstract
 * superclass in order to give more functionality to the client.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;
 * @author Fran&ccedil;ois B&eacute;langer
 * @version July 2000
 */

/**
 * @author Xiaoxi Jia
 * @version July 2020
 */

public class ChatClient extends AbstractClient
{
  //Instance variables **********************************************
  
  /**
   * The interface type variable.  It allows the implementation of 
   * the display method in the client.
   */
  ChatIF clientUI; 
  String id;

  StringBuilder message = new StringBuilder("");
  
  //Constructors ****************************************************
  
  /**
   * Constructs an instance of the chat client.
   *
   * @param host The server to connect to.
   * @param port The port number to connect on.
   * @param clientUI The interface type variable.
   */
  
  public ChatClient(String id, String host, int port, ChatIF clientUI) 
    throws IOException 
  {
    super(host, port); //Call the superclass constructor
    this.clientUI = clientUI;
    this.id = id;
    openConnection();
    // login();
  }

  
  //Instance methods ************************************************
    
  /**
   * This method handles all data that comes in from the server.
   *
   * @param msg The message from the server.
   */
  public void handleMessageFromServer(Object msg) 
  {
    setMessage((String) msg);
    // clientUI.display(msg.toString());
  }

  public void setMessage(String str){
    message.append(str);
    message.append("\r\n");
  }

  public StringBuilder getMessage(){
    return message;
  }

  public void login() {
    handleMessageFromClientUI("#" + this.id);
  }

  /**
   * This method handles all data coming from the UI            
   *
   * @param message The message from the UI.    
   */
  public void handleMessageFromClientUI(String message)
  {
    try
    {
      sendToServer(message);
    }
    catch(IOException e)
    {
      clientUI.display
        ("Could not send message to server.  Terminating client.");
      quit();
    }
  }

  protected void connectionEstablished() {
    clientUI.display("Connection established.");  //Indicates the connection has established.
    try
    {
      login();
    }
    catch(Exception e)
    {
      clientUI.display("Could not set login ID.  Terminating client.");
        quit();
    }
  }

  protected void connectionClosed() {
    clientUI.display("Connection Closed.");
  }

  protected void connectionException(Exception exception) {
    clientUI.display("Server shut down.");
    quit();
  }
  
  /**
   * This method terminates the client.
   */
  public void quit()
  {
    try
    {
      closeConnection();
    }
    catch(IOException e) {}
    //clientUI.display("Client shut down.");
    System.exit(0);
  }
}
//End of ChatClient class
