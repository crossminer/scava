package org.eclipse.scava.crossflow.examples.firstcommitment.mdetech;

public class MdeTechnologyRepoFileCountPrinter extends MdeTechnologyRepoFileCountPrinterBase {

	@Override
	public void consumeMdeTechnologyRepoFileCountEntries(
			StringStringIntegerStringIntegerTuple stringStringIntegerStringIntegerTuple) {
		System.out.println("[" + workflow.getName() + "] Repository " + stringStringIntegerStringIntegerTuple.getField1() + " with " + stringStringIntegerStringIntegerTuple.getField2() + " stars has " + stringStringIntegerStringIntegerTuple.getField4() + " files (cached=" + stringStringIntegerStringIntegerTuple.isCached() + ")");
		
	}

}
