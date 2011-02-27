//Some connection code from http://illegalargumentexception.blogspot.com/2009/03/java-using-jpda-to-write-debugger.html

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.sun.jdi.AbsentInformationException;
import com.sun.jdi.Bootstrap;
import com.sun.jdi.IncompatibleThreadStateException;
import com.sun.jdi.LocalVariable;
import com.sun.jdi.ObjectReference;
import com.sun.jdi.StackFrame;
import com.sun.jdi.ThreadReference;
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

	public static Vector<Node>  main(String[] args)
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
				// try{
				frames = tr.frames();
				// System.out.println( frames );
				// } catch( IncompatibleThreadStateException e ) {
				// e.printStackTrace();
				// }
				// go through stack frames and get objects
				for (StackFrame s : frames) {
					ObjectReference obj = s.thisObject();
					String object = (obj == null) ? "null" : obj.toString();
					System.out.println("Thread " + tr + " -> Frame  " + s
							+ " -> " + object);
					FunctionNode objfunc;
					int calledFrom = BuilderDebugger.findPrevFuncNode(tempGraph);
					if ( calledFrom == -1 ) { objfunc = new FunctionNode(); }
					else {objfunc = new FunctionNode((FunctionNode)tempGraph.get(calledFrom), s.toString(), tempGraph.get(calledFrom).stackPosition+1); }
					tempGraph.add(objfunc);
					
					
					try {
						if ( object != "null" ) {
							for (LocalVariable lv : s.visibleVariables()) {
								System.out.println("    local: " + lv.name()
										+ " = " + s.getValue(lv));
								ObjectNode objlv = new ObjectNode();
								objlv.name = lv.name();
								tempGraph.add(objlv);
								objfunc.ObjectsConnectedTo.put(objlv.name, objlv);
							}
						}
						// put lv.name() as object nodes & s as function nodes
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
