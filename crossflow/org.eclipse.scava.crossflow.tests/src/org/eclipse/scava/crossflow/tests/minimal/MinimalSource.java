package org.eclipse.scava.crossflow.tests.minimal;

import java.util.List;

import org.eclipse.scava.crossflow.runtime.utils.Result;

public class MinimalSource extends MinimalSourceBase {

	protected List<Integer> numbers;
	protected boolean sendToResults = false;

	@Override
	public void produce() {
		for (int n : numbers) {
			sendToInput(new Number(n));
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

	public void setNumbers(List<Integer> numbers) {
		this.numbers = numbers;
	}

	public boolean isSendToResultsEnabled() {
		return sendToResults;
	}

	public void setSendToResults(boolean sendToResults) {
		this.sendToResults = sendToResults;
	}
}