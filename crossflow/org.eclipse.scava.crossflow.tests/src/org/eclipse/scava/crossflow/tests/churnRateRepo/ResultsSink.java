package org.eclipse.scava.crossflow.tests.churnRateRepo;


public class ResultsSink extends ResultsSinkBase {
	
	@Override
	public void consumeChurnRates(ChurnRate churnRate) throws Exception {
		
		// TODO: Add implementation that instantiates, sets, and submits result objects (example below)
	//	System.out.println("[" + workflow.getName() + "] Result is " + churnRate.toString() + " (cached=" + churnRate.isCached() + ")");
		System.out.println("["+ workflow.getName() +"] Result is: "+"Repository " + churnRate.getUrl() +" "+"Branch: " + churnRate.getBranch() +" "+"ChurnRate: "+ churnRate.getChurnRate()+ " "+ "(cached=" + churnRate.isCached() + ")");

	}

}