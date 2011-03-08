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

	static ObjectNode tempObj;
	static String tempInstOf;

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

	public static Vector<Node> main(String[] args)
			throws IncompatibleThreadStateException {

		Vector<Node> tempGraph = new Vector<Node>();
		int sfCt = 0;
		try {
			// establish the connection at port 8000
			VirtualMachine vm = VMtest.connect(8000);
			vm.suspend();

			// Get all threads from VM object
			List<ThreadReference> threads = vm.allThreads();
			System.out.println(threads);

			// get list of stack frames for each thread
			for (ThreadReference tr : threads) {
				List<StackFrame> frames = new ArrayList<StackFrame>();
				frames = tr.frames();

				boolean atMain = false;

				// goes through stack frames and gets objects
				for (StackFrame s : frames) {
					ObjectReference obj = s.thisObject();
					String object = (obj == null) ? "null" : obj.toString();
					System.out.println("Thread " + tr + " -> Frame " + s
							+ " -> " + object);
					if (sfCt > 10) {
						atMain = true;
					}
					else
						sfCt++;

					if (atMain) {
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
							// for each visible variable getvalue
							for (LocalVariable lv : s.visibleVariables()) {
								System.out.println(" local: " + lv.name()
										+ " = " + s.getValue(lv));

								ObjectNode objlv = new ObjectNode(lv.name(), lv
										.name());
								
								
								tempGraph.add(objlv);
								objfunc.ObjectsConnectedTo
										.put(lv.name(), objlv);

								// need to do recursive search here
								search(s.getValue(lv), tempGraph, objlv);
							}
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
									}
								}
							}
						} catch (AbsentInformationException e) {
							e.printStackTrace();
						}
					}
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return tempGraph;
	}

	// recursive search method to look through all values of the local variables
	public static void search(Value v, Vector<Node> tempGraph,
			ObjectNode current) {
		if (v instanceof ObjectReference) {
			ObjectReference obj = (ObjectReference) v;
			List<Field> fields = obj.referenceType().fields();

			for (int i = 0; i < fields.size(); i++) {
				Value fval = obj.getValue(fields.get(i));

				// pass tempGraph as a parameter to search method in VMtest

				if (fval == null || fval.equals(null))
					continue;

				else if (!tempGraph.contains(fval)) {
					if (fval.toString().startsWith("instance")) {
						System.out.println(fields.get(i) + " -> "
								+ fval.type().name() + " " + fval);

						tempObj = new ObjectNode(fields.get(i).toString(), fval
								.toString());

						tempGraph.add(tempObj);
					
						tempInstOf = fields.get(i).toString();
						tempObj.name = tempInstOf;
						tempObj.ObjectsConnectedTo.put(tempInstOf, current);
						search(fval, tempGraph, tempObj);
					}
					else {}
					
				}
			}
		} else 
		{
		}
	}
}
