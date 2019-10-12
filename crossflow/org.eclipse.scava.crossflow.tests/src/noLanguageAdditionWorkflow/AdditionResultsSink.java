package noLanguageAdditionWorkflow;


public class AdditionResultsSink extends AdditionResultsSinkBase {
	
	@Override
	public void consumeAdditionResults(Number number) throws Exception {
		
		// TODO: Add implementation that instantiates, sets, and submits result objects (example below)
		System.out.println("[" + workflow.getName() + "] Result is " + number.toString() + " (cached=" + number.isCached() + ")");
	
	}


}
