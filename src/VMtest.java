//Some connection code from http://illegalargumentexception.blogspot.com/2009/03/java-using-jpda-to-write-debugger.html

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;
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

    static Set<Value> remember = new HashSet<Value>();
    static Hashtable<Value, ObjectNode> objectHash = new Hashtable<Value, ObjectNode>();

        public static ObjectNode getNode(Value v) 
        {
            if (!objectHash.containsKey(v)) {
                objectHash.put(v, new ObjectNode(v.toString(), v.toString()));
            }
            return objectHash.get(v);
        }
    
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
            //System.out.println(connector.name());
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
            //System.out.println(threads);

            // get list of stack frames for each thread
            for (ThreadReference tr : threads) {
                List<StackFrame> frames = new ArrayList<StackFrame>();
                frames = tr.frames();

                boolean atMain = false;

                // go through stack frames and get objects
                for (StackFrame s : frames) {
                    ObjectReference obj = s.thisObject();
                    String object = (obj == null) ? "null" : obj.toString();
                    //System.out.println("Thread " + tr + " -> Frame " + s + " -> " + object);
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
                            objfunc = new FunctionNode(
                                            (FunctionNode) tempGraph.get(calledFrom), 
                                            s.toString(), 
                                            String.valueOf(s.hashCode()), 
                                            tempGraph.get(calledFrom).stackPosition + 1);
                        }
                        tempGraph.add(objfunc);

                        try {
                            // for each visible variable, get the value
                            for (LocalVariable lv : s.visibleVariables()) {
                                //System.out.println(" local: " + lv.name() + " = " + s.getValue(lv));
                                
                                Value v = s.getValue(lv);
                                ObjectNode objlv = getNode(v);
                                objfunc.addConnection(lv.toString(), objlv);

                                // do recursive search 
                                search(v, tempGraph);
                            }

                            if (!object.equals("null")) {
                                // look through fields and get values
                                List<Field> fields = obj.referenceType().fields();
                                for (Field f : fields) {
                                    if (f.typeName() == null) {
                                    } else {
                                        Value fval = obj.getValue(f);
                                        search(fval, tempGraph);
                                        //System.out.println("***** field name " + f.name() + " ****field value " + fval + " *****type " + f.typeName());
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
    public static void search(Value v, Vector<Node> tempGraph) {
        ObjectNode current = getNode(v);

        if (remember.contains(v))
            return;
        remember.add(v);
        
        if (v instanceof ObjectReference) {
            ObjectReference obj = (ObjectReference) v;
            List<Field> fields = obj.referenceType().fields();

            for (int i = 0; i < fields.size(); i++) {
                Field f = fields.get(i);
                Value fval = obj.getValue(f);

                if (fval == null || fval.equals(null))
                    continue;

                if (fval.toString().startsWith("instance")) {
                    //System.out.println(f + " -> " + fval.type().name() + " " + fval);
                    
                    ObjectNode tempObj = getNode(fval);
                    if (!tempGraph.contains(tempObj))
                        tempGraph.add(tempObj);

                    current.addConnection(f.toString(), tempObj);
                    search(fval, tempGraph);
                }
            }
        } 
    }
}
