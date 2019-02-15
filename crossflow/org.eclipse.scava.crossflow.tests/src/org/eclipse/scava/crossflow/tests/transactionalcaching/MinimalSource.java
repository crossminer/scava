package org.eclipse.scava.crossflow.tests.transactionalcaching;

import java.util.List;

import org.eclipse.scava.crossflow.runtime.utils.Result;

public class MinimalSource extends MinimalSourceBase {

	protected List<String> elements;
	protected boolean sendToResults = false;

	@Override
	public void produce() {
		for (String n : elements) {
			sendToInput(new Element(n));
			if (sendToResults) {
				Result res = new Result();
				res.add(n);
				try {
					workflow.getResultsTopic().send(res);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void setElements(List<String> e) {
		this.elements = e;
	}

	public boolean isSendToResultsEnabled() {
		return sendToResults;
	}

	public void setSendToResults(boolean sendToResults) {
		this.sendToResults = sendToResults;
	}
}