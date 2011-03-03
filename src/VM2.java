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

public class VM2
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
		// establish the connection at port 8000
		VirtualMachine vm = new VM2().connect( 8000 );
		vm.suspend();

		// Get all threads from VM object
		List<ThreadReference> threads = vm.allThreads();
		System.out.println( threads );
 
		// get list of stack frames for each thread
		for( ThreadReference tr : threads )
		{
			List<StackFrame> frames = new ArrayList<StackFrame>();
			frames = tr.frames();
			
			// go through stack frames and get objects
			for( StackFrame s : frames )
			{
				ObjectReference obj = s.thisObject();
				String object = ( obj == null ) ? "null" : obj.toString();
				System.out.println( "Thread " + tr + " -> Frame " + s + " -> " + object );
				try{
					List<LocalVariable> l = s.visibleVariables();
					for( LocalVariable lv : l )
					{
						System.out.println(" local: " + lv.name() + " = " + s.getValue(lv));
					 	// need to do search here
						//search( s.getValue(lv, 1));				
                			}
					if( !object.equals("null") ){
						List<Field> fields = obj.referenceType().fields();
                				for( Field f: fields )
                				{
                        				Value fval = obj.getValue( f );
                        				System.out.println( "***** field name " + f.name() + " ****field value " + fval + " *****type " + f.typeName() );
						}
					}
					//put lv.name() as edges, s.getValue(lv) as object nodes & s as function nodes
					
				  } catch( AbsentInformationException e ) {
					e.printStackTrace();
				  }
			}
		}

      		} catch( IOException e ) {
         		e.printStackTrace();
      		  }
	}
	
	//need a recursive search method to look through all values of the local variables
	public static void search( Value v , int depth )
	{
		if (value instanceof ObjectReference) {
            		ObjectReference obj = (ObjectReference) v;
            		List<Field> fields = obj.referenceType().fields();
            
           		 for (Field f : fields) {
                		Value fval = obj.getValue(f);
				//make tempgraph a global variable in VMtest
				if( !tempGraph.contains( fval ) ) {
					//add new node to tempGraph
					search( fval, depth+1 );
				}
				else {
					//.....
				
					






	
}// end class


