package org.eclipse.scava.crossflow.tests.csvsourcesinkcase;

import org.apache.commons.csv.CSVRecord;

public class NumberPairCsvSource extends NumberPairCsvSourceBase {

	@Override
	public void produce() {
		for (CSVRecord record : records) {
			NumberPair numberPair = new NumberPair();
			numberPair.setA( Integer.parseInt( record.get(0) ) );
			numberPair.setB( Integer.parseInt ( record.get(1) ) );
			sendToAdditions(numberPair);
		}
	}

}