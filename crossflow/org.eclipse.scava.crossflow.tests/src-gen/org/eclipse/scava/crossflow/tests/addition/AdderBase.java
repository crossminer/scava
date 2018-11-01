package org.eclipse.scava.crossflow.tests.addition;

import org.eclipse.scava.crossflow.runtime.Workflow;

public abstract class AdderBase implements AdditionsConsumer{
	
	protected AdditionWorkflow workflow;
	
	public void setWorkflow(AdditionWorkflow workflow) {
		this.workflow = workflow;
	}
	
	public Workflow getWorkflow() {
		return workflow;
	}
	
	protected AdditionResults additionResults;
	
	public void setAdditionResults(AdditionResults additionResults) {
		this.additionResults = additionResults;
	}
	
	public AdditionResults getAdditionResults() {
		return additionResults;
	}
	
	
	
	protected EclipseResultPublisher eclipseResultPublisher;
	
	public void setEclipseResultPublisher(EclipseResultPublisher eclipseResultPublisher) {
		this.eclipseResultPublisher = eclipseResultPublisher;
	}
	
	public EclipseResultPublisher getEclipseResultPublisher() {
		return eclipseResultPublisher;
	}
	
	
	
	@Override
	public void consumeAdditionsActual(NumberPair numberPair) {

		workflow.setTaskInProgess(this);
		
		consumeAdditions(numberPair);
		
		workflow.setTaskWaiting(this);
		
	}
	
	
	/**
	 * Call this within consumeXYZ() to denote task blocked due to some reason
	 * @param reason
	 */
	protected void taskBlocked(String reason) {
		
		workflow.setTaskBlocked(this,reason);
		
	}
	
	/**
	 * Call this within consumeXYZ() to denote task is now unblocked
	 * @param reason
	 */
	protected void taskUnblocked() {
		
		workflow.setTaskUnblocked(this);
		
	}
	
	
}