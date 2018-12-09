package org.eclipse.scava.crossflow.examples.addition;

import org.eclipse.scava.crossflow.runtime.Task;
import org.eclipse.scava.crossflow.runtime.Workflow;
import org.eclipse.scava.crossflow.runtime.permanentqueues.*;

public abstract class AdderBase extends Task  implements AdditionsConsumer{
		
	protected AdditionExample workflow;
	
	public void setWorkflow(AdditionExample workflow) {
		this.workflow = workflow;
	}
	
	public Workflow getWorkflow() {
		return workflow;
	}
	
	public String getId(){
		return "Adder:"+workflow.getName();
	}
	
	protected AdditionResults additionResults;
	
	protected void setAdditionResults(AdditionResults additionResults) {
		this.additionResults = additionResults;
	}
	
	private AdditionResults getAdditionResults() {
		return additionResults;
	}
	
	public void sendToAdditionResults(Number number) {
		getAdditionResults().send(number, this.getClass().getName());
	}
	
	
	
	@Override
	public void consumeAdditionsActual(NumberPair numberPair) {

		workflow.setTaskInProgess(this);
		
		consumeAdditions(numberPair);
		
		workflow.setTaskWaiting(this);
		
	}
	
	
	
	
}