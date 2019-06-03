/*******************************************************************************
 * Copyright (c) 2019 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributor(s):
 *      Patrick Neubauer - initial API and implementation
 ******************************************************************************/
package org.eclipse.scava.crossflow.examples.ghmde;

import java.io.File;

import org.apache.commons.csv.CSVRecord;
import org.eclipse.scava.crossflow.runtime.utils.CrossflowLogger;
import org.eclipse.scava.crossflow.runtime.utils.CsvParser;

public class MDETechnologyTupleSource extends MDETechnologyTupleSourceBase {
	
	protected Iterable<CSVRecord> records;
	
	public MDETechnologyTupleSource() {
		CsvParser parser;
		try {
			parser = new CsvParser(new File(workflow.getInputDirectory(), "input.csv").getAbsolutePath());
			records = parser.getRecordsIterable();
		} catch (Exception e) {
			workflow.log(CrossflowLogger.SEVERITY.ERROR, "Failed to read from source. Message: " + e.getMessage());
		}
	}
	
	@Override
	public void produce() {
//		try {
//			CsvParser parser = new CsvParser(new File(workflow.getInputDirectory(), "input.csv").getAbsolutePath());
//			records = parser.getRecordsIterable();
		
			for (CSVRecord record : records) {
				MDETechnologyTuple tuple = new MDETechnologyTuple();
				 tuple.setFileExt(record.get(0));
				 tuple.setTechKey(record.get(1));
				sendToMDETechnologyTuples( tuple);
	
			}
//		} catch (Exception e) {
//			workflow.log(SEVERITY.ERROR, e.getMessage());
//		}
	}
	
	public Iterable<CSVRecord> getRecords() {
		return records;
	}
	
	public boolean inCollection(String s) {
		for ( CSVRecord record : getRecords() ) {
			if (record.get(0).equals(s))
				return true;
		}
		return false; // not in collection
	}// inCollection
	
}//MDETechnologyTupleSource
