
public class ProgramGutsMain {
	
	static BuilderDebugger bd;
	static GraphViewer gv;

	public static void main(String args[]) {
		bd = new BuilderDebugger();
		
		//builder/debugger part
		bd.DebugProgram();
		
		// graph viewer part
		gv = new GraphViewer( bd.getGraph() );
		
	}
}
