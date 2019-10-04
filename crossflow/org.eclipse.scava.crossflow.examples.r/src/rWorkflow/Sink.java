package rWorkflow;


public class Sink extends SinkBase {
	
	@Override
	public void consumeSiQ(RData rData) throws Exception {
		
		// TODO: Add implementation that instantiates, sets, and submits result objects (example below)
		System.out.println("[" + workflow.getName() + "] Result is " + rData.toString() + " (cached=" + rData.isCached() + ")");
	
	}


}
