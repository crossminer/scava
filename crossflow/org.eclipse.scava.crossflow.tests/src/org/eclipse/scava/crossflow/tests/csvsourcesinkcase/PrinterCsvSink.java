package org.eclipse.scava.crossflow.tests.csvsourcesinkcase;

public class PrinterCsvSink extends PrinterCsvSinkBase {

	
	@Override
	public void consumeAdditionResults(Number number) {
			
		writer0.writeRecord( number.getN(),  number.isCached() );
		writer0.flush();
		
	}

}