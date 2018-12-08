package org.eclipse.scava.crossflow.tests.csvsourcesinkcase;

import org.apache.commons.csv.CSVRecord;

public class NumberPairCsvSource extends NumberPairCsvSourceBase {

	@Override
	public void produce() {
		for (CSVRecord record : records) {
			sendToAdditions(new NumberPair(Integer.parseInt(record.get(0)), Integer.parseInt (record.get(1))));
		}
	}

}