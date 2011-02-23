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
import com.sun.jdi.request.*;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.*;

public class VMtest 
{
	public static VirtualMachine connect( int port ) throws IOException 
	{
		String strPort = Integer.toString( port );
    		AttachingConnector connector = getConnector();
    		try {
      			VirtualMachine vm = connect( connector, strPort );
      			return vm;
    		    } catch( IllegalConnectorArgumentsException e ) {
      				throw new IllegalStateException( e );
    			}
  	}

	private static AttachingConnector getConnector() 
	{
		VirtualMachineManager vmManager = Bootstrap.virtualMachineManager();
    		for( Connector connector : vmManager.attachingConnectors() ) 
		{
      			System.out.println( connector.name() );
      			if( "com.sun.jdi.SocketAttach".equals( connector.name() ) ) 
			{
        			return( AttachingConnector ) connector;
      			}
    		}
    		throw new IllegalStateException();
  	}

	private static VirtualMachine connect( AttachingConnector connector, String port ) throws IllegalConnectorArgumentsException, IOException 
	{
    		Map<String, Connector.Argument> args = connector.defaultArguments();
    		Connector.Argument pidArgument = args.get( "port" );
    		if( pidArgument == null ) 
		{
      			throw new IllegalStateException();
    		}
    		pidArgument.setValue( port );
		return connector.attach( args );
  	}

	public static void main( String [] args ) 
  	{		
 		AttachingConnector connector = getConnector();
    		try{
			//VirtualMachine v = connect(8000)

			VirtualMachine vm = new VMtest().connect( 8000 );

// createVM needs reworking   vm.createVirtualMachine (vm.getConnector());

			System.out.println( "Test" );
			List<ThreadReference> threads = new ArrayList<ThreadElements>();
			System.out.println(threads);
//Should get classes	System.out.println(threads.allClasses());
//Should get Threads	System.out.println(threads.allThreads());

     		   } catch( IOException e ) {
        		e.printStackTrace();
     		    }
	}
}
