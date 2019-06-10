/*******************************************************************************
 * Copyright (c) 2019 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributor(s):
 *      Patrick Neubauer - initial API and implementation
 ******************************************************************************/
package org.eclipse.scava.crossflow.examples.ghmde.xtext;

import org.eclipse.scava.crossflow.runtime.utils.CrossflowLogger.SEVERITY;

import java.io.File;

import org.apache.commons.csv.CSVRecord;
import org.eclipse.scava.crossflow.runtime.utils.CsvParser;

public class MDETechnologyTupleSource extends MDETechnologyTupleSourceBase {
	
	protected Iterable<CSVRecord> records;
	
	@Override
	public void produce() {
		try {
			final CsvParser parser = new CsvParser(new File(workflow.getInputDirectory(), "input.csv").getAbsolutePath());
			records = parser.getRecordsIterable();
		
			for (CSVRecord record : records) {
				MDETechnologyTuple mDETechnologyTuple = new MDETechnologyTuple();
				mDETechnologyTuple.setFileExt(record.get(0));
				mDETechnologyTuple.setTechKey(record.get(1));
				sendToMDETechnologyTuples( mDETechnologyTuple);
	
			}
		} catch (Exception e) {
			workflow.log(SEVERITY.ERROR, e.getMessage());
		}
	}
	
	public Iterable<CSVRecord> getRecords() {
		return records;
	}
}
