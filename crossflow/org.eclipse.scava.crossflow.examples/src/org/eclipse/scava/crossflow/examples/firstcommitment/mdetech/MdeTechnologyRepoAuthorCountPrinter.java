package org.eclipse.scava.crossflow.examples.firstcommitment.mdetech;

public class MdeTechnologyRepoAuthorCountPrinter extends MdeTechnologyRepoAuthorCountPrinterBase {

	@Override
	public void consumeMdeTechnologyRepoAuthorCountEntries(
			StringStringIntegerStringIntegerTuple stringStringIntegerStringIntegerTuple) {
		System.out.println("[" + workflow.getName() + "] Repository " + stringStringIntegerStringIntegerTuple.getField1() + " with " + stringStringIntegerStringIntegerTuple.getField2() + " stars has " + stringStringIntegerStringIntegerTuple.getField4() + " unique authors (cached=" + stringStringIntegerStringIntegerTuple.isCached() + ")");
		
	}

}
