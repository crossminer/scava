package org.eclipse.scava.crossflow.tests.csvsourcesinkcase;

import org.eclipse.scava.crossflow.runtime.utils.CsvWriter;

public class PrinterCsvSink extends PrinterCsvSinkBase {
	// CSV file writer(s)
	protected CsvWriter writer1 = new CsvWriter("csvs/AdditionResultsCsvSink.csv", "n",  "cached");
	
	public void flushAll() {
		writer1.flush();
	}
	
	@Override
	public void consumeAdditionResults(Number number) {
		writer1.writeRecord( number.getN(),  number.isCached() );
		writer1.flush();
	}
	
}