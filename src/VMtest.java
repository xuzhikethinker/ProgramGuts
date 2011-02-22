//Most code from http://illegalargumentexception.blogspot.com/2009/03/java-using-jpda-to-write-debugger.html

import com.sun.jdi.ObjectReference;
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
import com.sun.jdi.event.*;
import com.sun.jdi.event.Event;
import com.sun.jdi.event.MethodEntryEvent;
import com.sun.jdi.event.MethodExitEvent;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.*;
import java.util.*;
import com.sun.jdi.event.EventQueue;
import java.util.Iterator;
import com.sun.jdi.Method;
import com.sun.jdi.StackFrame;
import com.sun.jdi.IncompatibleThreadStateException;
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

	public static void main( String [] args ) throws IncompatibleThreadStateException 
  	{		
    		try{
			//VirtualMachine v = connect(8000);
			// establish the connection at port 8000
			VirtualMachine vm = new VMtest().connect( 8000 );
		
			// Get all threads from VM object	
			List<ThreadReference> threads = new ArrayList<ThreadReference>();
			threads = vm.allThreads();
			System.out.println( threads );

/*                                ThreadReference tr = threads.get( 1 );
                                List<StackFrame> frames = new ArrayList<StackFrame>();
                                frames = tr.frames();
                                System.out.println( frames );
*/	
 
			// get list of stack frames for each thread
			for( int i = 0; i < threads.size(); i++ )
			{
				ThreadReference tr = threads.get( i );
				List<StackFrame> frames = new ArrayList<StackFrame>();
	//			try{
					frames = tr.frames();
	//				System.out.println( frames );
	//			} catch( IncompatibleThreadStateException e ) {
	//				e.printStackTrace();
	//				}
				for ( int j = 0; j < frames.size(); j++ )
				{
					StackFrame s = frames.get( i );
					ObjectReference obj = s.thisObject();
					String object = obj.toString();
					System.out.println( object );
				}			
			}
		
     		   } catch( IOException e ) {
        		e.printStackTrace();
     		    }
	}
}// end class
