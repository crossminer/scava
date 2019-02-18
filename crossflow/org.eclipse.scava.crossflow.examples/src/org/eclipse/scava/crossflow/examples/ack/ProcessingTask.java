package org.eclipse.scava.crossflow.examples.ack;


public class ProcessingTask extends ProcessingTaskBase {
	
	private int lag = 1000;
	
	public int getLag() {
		return lag;
	}

	public void setLag(int lag) {
		this.lag = lag;
	}

	@Override
	public StringElement consumeNumbers(IntElement intElement) throws Exception {
		Thread.sleep(lag);
		System.out.println(workflow.getName()+" used for processing");
		return new StringElement(workflow.getName());
	}

}