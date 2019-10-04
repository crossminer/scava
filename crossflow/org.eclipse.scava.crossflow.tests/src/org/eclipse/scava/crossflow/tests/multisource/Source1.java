package org.eclipse.scava.crossflow.tests.multisource;

public class Source1 extends Source1Base {

	private boolean finished = false;

	public boolean getFinished() {
		return finished;
	}

	@Override
	public void produce() throws Exception {

		for (int i = 0; i < 5; i++) {
			Thread.sleep(500);
			sendToResults(new StringElement("Source1"));			
		}
		finished = true;

	}

}