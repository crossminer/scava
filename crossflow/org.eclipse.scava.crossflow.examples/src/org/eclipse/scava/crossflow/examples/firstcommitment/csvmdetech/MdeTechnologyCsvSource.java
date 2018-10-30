package org.eclipse.scava.crossflow.examples.firstcommitment.csvmdetech;

import org.apache.commons.csv.CSVRecord;

public class MdeTechnologyCsvSource extends MdeTechnologyCsvSourceBase {

	@Override
	public void produce() {
		for (CSVRecord record : records) {
			ExtensionKeywordTuple extensionKeywordTuple = new ExtensionKeywordTuple();
			extensionKeywordTuple.setField0( record.get(0) );
			extensionKeywordTuple.setField1( record.get(1) );
			getMdeTechnologies().send( extensionKeywordTuple, this.getClass().getName() );
	
		}
	}

}