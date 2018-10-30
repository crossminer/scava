package org.eclipse.scava.crossflow.tests.csvsourcesinkcase;

import org.apache.commons.csv.CSVRecord;
import org.eclipse.scava.crossflow.runtime.utils.CsvParser;

public class NumberPairCsvSource extends NumberPairCsvSourceBase {

	@Override
	public void produce() {
		for (CSVRecord record : records) {
			NumberPair numberPair = new NumberPair();
			
			numberPair.setA( Integer.parseInt( record.get(0) ) );
			numberPair.setB( Integer.parseInt ( record.get(1) ) );
			
			getAdditions().send( numberPair, this.getClass().getName() );
	
		}
	}

}