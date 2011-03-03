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
		//Hashtable<LocalVariable> graph = new Hashtable<LocalVariable>();
		//Vector<LocalVariable> graph= new Vector<LocalVariable>();
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
						//graph.add( lv );
						//explored = new boolean[l.size()];
				                //inpath = new boolean[l.size()];
						
						System.out.println(" local: " + lv.name() + " = " + s.getValue(lv));
									
						//ObjectNode on = new ObjectNode( lv.name() );
						//Value v = s.getValue(lv);	
						//ObjectReference o = (ObjectReference) v;
                				List<Field> fields = obj.referenceType().fields();
                				for( Field f: fields )
                				{
                        				Value fval = obj.getValue( f );
                        				System.out.println( "***** field name " + f.name() + " ****field value " + fval + " *****type " + f.typeName() );
						}
//						search(  s.getValue(lv),  1 );//instead of 'on', use objfunc in VMtest.java
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
/*
	public static void search( Value v,  int depth )
	{
		ObjectReference o = (ObjectReference) v;
		List<Field> fields = o.referenceType().fields();
		for( Field f: fields )
		{
			Value fval = o.getValue( f );
			System.out.println( " field value " + fval + "field name " + f.name() + "type " + f.typeName() );
			
		//	ObjectNode on = new ObjectNode( f.typeName() );
			search( fval,  depth + 1 );
		}
	}
*/

//	public static boolean explored[];
//       public static boolean inpath[];

/*	
	public static boolean dfs( Integer vertex , Vector<LocalVariable> graph)
        {
                explored[vertex] = true;
                inpath[vertex] = true;
                for( Integer neighbor : graph.get(vertex) )
                {
                        if( inpath[neighbor] )
                                return true;

                        if( !explored[neighbor] )
                        {
                                if( dfs( neighbor, graph ) )
                                        return true;
                        }
                }
                inpath[vertex] = false;
                return false;
        }//end dfs function
*/
	
}// end class


