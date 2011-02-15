//Code from http://illegalargumentexception.blogspot.com/2009/03/java-using-jpda-to-write-debugger.html

//package com.google.code.maven2.plugins.hotswap;
import com.sun.jdi.Bootstrap;
import com.sun.jdi.ReferenceType;
import com.sun.jdi.VirtualMachine;
import com.sun.jdi.VirtualMachineManager;
import com.sun.jdi.connect.AttachingConnector;
import com.sun.jdi.connect.Connector;
import com.sun.jdi.connect.Transport;

import com.sun.jdi.*;
import com.sun.jdi.connect.*;
import com.sun.jdi.connect.IllegalConnectorArgumentsException;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.*;

 public class VMtest {

  public VirtualMachine connect(int port)
      throws IOException {
    String strPort = Integer.toString(port);
    AttachingConnector connector = getConnector();
    try {
      VirtualMachine vm = connect(connector, strPort);
      return vm;
    } catch (IllegalConnectorArgumentsException e) {
      throw new IllegalStateException(e);
    }
  }

  private AttachingConnector getConnector() {
    VirtualMachineManager vmManager = Bootstrap
        .virtualMachineManager();
    for (Connector connector : vmManager
        .attachingConnectors()) {
      System.out.println(connector.name());
      if ("com.sun.jdi.SocketAttach".equals(connector
          .name())) {
        return (AttachingConnector) connector;
      }
    }
    throw new IllegalStateException();
  }

  private VirtualMachine connect(
      AttachingConnector connector, String port)
      throws IllegalConnectorArgumentsException,
      IOException {
    Map<String, Connector.Argument> args = connector
        .defaultArguments();
    Connector.Argument pidArgument = args.get("port");
    if (pidArgument == null) {
      throw new IllegalStateException();
    }
    pidArgument.setValue(port);

    return connector.attach(args);
  }
/*  public void main(String [] args) 
	throws IOException
  {		
 	
    AttachingConnector connector = getConnector();
      VirtualMachine v = connect(8000);
   
  }
*/
}

