//Some connection code from http://illegalargumentexception.blogspot.com/2009/03/java-using-jpda-to-write-debugger.html

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.sun.jdi.AbsentInformationException;
import com.sun.jdi.Bootstrap;
import com.sun.jdi.Field;
import com.sun.jdi.IncompatibleThreadStateException;
import com.sun.jdi.LocalVariable;
import com.sun.jdi.ObjectReference;
import com.sun.jdi.StackFrame;
import com.sun.jdi.ThreadReference;
import com.sun.jdi.Value;
import com.sun.jdi.VirtualMachine;
import com.sun.jdi.VirtualMachineManager;
import com.sun.jdi.connect.AttachingConnector;
import com.sun.jdi.connect.Connector;
import com.sun.jdi.connect.IllegalConnectorArgumentsException;

public class VMtest {
	public static VirtualMachine connect(int port) throws IOException {
		String strPort = Integer.toString(port);
		AttachingConnector connector = getConnector();
		try {
			VirtualMachine vm = connect(connector, strPort);
			return vm;
		} catch (IllegalConnectorArgumentsException e) {
			throw new IllegalStateException(e);
		}
	}

	private static AttachingConnector getConnector() {
		VirtualMachineManager vmManager = Bootstrap.virtualMachineManager();
		for (Connector connector : vmManager.attachingConnectors()) {
			System.out.println(connector.name());
			if ("com.sun.jdi.SocketAttach".equals(connector.name())) {
				return (AttachingConnector) connector;
			}
		}
		throw new IllegalStateException();
	}

<<<<<<< HEAD
	public static void main( String [] args ) 
  	{		
 		AttachingConnector connector = getConnector();
    		try{
			//VirtualMachine v = connect(8000)

			VirtualMachine vm = new VMtest().connect( 8000 );


			List<ThreadReference> threads = new ArrayList<ThreadElements>();
			System.out.println(threads);
//Should get classes	System.out.println(threads.allClasses());
//Should get Threads	System.out.println(threads.allThreads());
     		   } catch( IOException e ) {
        		e.printStackTrace();
     		    }
=======
	/*
	 * public static void main( String [] args ) { AttachingConnector connector
	 * = getConnector(); try{ //VirtualMachine v = connect(8000)
	 * 
	 * VirtualMachine vm = new VMtest().connect( 8000 );
	 * 
	 * // createVM needs reworking vm.createVirtualMachine (vm.getConnector());
	 * 
	 * System.out.println( "Test" ); List<ThreadReference> threads = new
	 * ArrayList<ThreadElements>(); System.out.println(threads); //Should get
	 * classes System.out.println(threads.allClasses()); //Should get Threads
	 * System.out.println(threads.allThreads()); // New goal: need hash table }
	 * catch( IOException e ) { e.printStackTrace(); }
	 */
>>>>>>> ba76c884f279339ee374e09c10ae4ff3bdca335d
	private static VirtualMachine connect(AttachingConnector connector,
			String port) throws IllegalConnectorArgumentsException, IOException {
		Map<String, Connector.Argument> args = connector.defaultArguments();
		Connector.Argument pidArgument = args.get("port");
		if (pidArgument == null) {
			throw new IllegalStateException();
		}
		pidArgument.setValue(port);
		return connector.attach(args);
	}

	public static Vector<Node> main(String[] args)
			throws IncompatibleThreadStateException {

		Vector<Node> tempGraph = new Vector<Node>();
		try {

			// VirtualMachine v = connect(8000);
			// establish the connection at port 8000
			VirtualMachine vm = new VMtest().connect(8000);
			vm.suspend();

			// Get all threads from VM object
			List<ThreadReference> threads = vm.allThreads();
			System.out.println(threads);

			// get list of stack frames for each thread
			for (ThreadReference tr : threads) {
				List<StackFrame> frames = new ArrayList<StackFrame>();
				frames = tr.frames();

				// go through stack frames and get objects
				for (StackFrame s : frames) {
					ObjectReference obj = s.thisObject();
					String object = (obj == null) ? "null" : obj.toString();
					System.out.println("Thread " + tr + " -> Frame " + s
							+ " -> " + object);
					FunctionNode objfunc;
					int calledFrom = BuilderDebugger
							.findPrevFuncNode(tempGraph);
					if (calledFrom == -1) {
						objfunc = new FunctionNode();
					} else {
						objfunc = new FunctionNode((FunctionNode) tempGraph
								.get(calledFrom), s.toString(), String
								.valueOf(s.hashCode()), tempGraph
								.get(calledFrom).stackPosition + 1);
					}
					tempGraph.add(objfunc);

					try {
<<<<<<< HEAD
//for each visible variable getvalue
							for (LocalVariable lv : s.visibleVariables()) {
								System.out.println("    local: " + lv.name()
										+ " = " + s.getValue(lv));
								ObjectNode objlv = new ObjectNode();
								objlv.name = lv.name();
								tempGraph.add(objlv);
								objfunc.ObjectsConnectedTo.put(objlv.name, objlv);
=======
						// if (object != "null") {
						for (LocalVariable lv : s.visibleVariables()) {
							System.out.println(" local: " + lv.name() + " = "
									+ s.getValue(lv));
							
							  ObjectNode objlv = new ObjectNode(lv.name(), lv.name()); 
							  tempGraph.add(objlv);
							  objfunc.ObjectsConnectedTo.add(objlv);
							 
>>>>>>> ba76c884f279339ee374e09c10ae4ff3bdca335d

							if (object != "null") {
								/* new additions for fields and values */
								List<Field> fields = obj.referenceType()
										.fields();
								for (Field f : fields) {
									if (f.typeName() == null) {
									} else {
										Value fval = obj.getValue(f);
										System.out.println("***** field name "
												+ f.name()
												+ " ****field value " + fval
												+ " *****type " + f.typeName());
										String value;
										if (fval == null) {
											value = "";
										} else {
											value = fval.toString();
										}
										ObjectNode objectN = new ObjectNode(f
												.name(), value);
										tempGraph.add(objectN);
										objfunc.ObjectsConnectedTo.add(objectN);
									}
								}
							}
<<<<<<< HEAD
						// put lv.name() as object nodes & s as function nodes
=======
						}

						// put lv.name() as object nodes & s as function
						// nodes
>>>>>>> ba76c884f279339ee374e09c10ae4ff3bdca335d
					} catch (AbsentInformationException e) {
						e.printStackTrace();
					}
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return tempGraph;
	}
}// end class
