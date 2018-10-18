package org.eclipse.scava.crossflow.examples.firstcommitment.mdetech;

public class MdeTechnologyRepoOwnerPopularityCountPrinter extends MdeTechnologyRepoOwnerPopularityCountPrinterBase {

	@Override
	public void consumeMdeTechnologyRepoOwnerPopularityCountEntries(
			StringStringIntegerStringIntegerTuple stringStringIntegerStringIntegerTuple) {
		System.out.println("[" + workflow.getName() + "] Repository " + stringStringIntegerStringIntegerTuple.getField1() + " with " + stringStringIntegerStringIntegerTuple.getField2() + " stars has " + stringStringIntegerStringIntegerTuple.getField4() + " owner popularity (cached=" + stringStringIntegerStringIntegerTuple.isCached() + ")");
		
	}

}
