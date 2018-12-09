package org.eclipse.scava.crossflow.tests.csvsourcesinkcase;

import org.eclipse.scava.crossflow.runtime.Task;
import org.eclipse.scava.crossflow.runtime.Workflow;
import java.io.IOException;
import org.apache.commons.csv.CSVRecord;
import org.eclipse.scava.crossflow.runtime.utils.CsvParser;
import org.eclipse.scava.crossflow.runtime.permanentqueues.*;

public abstract class NumberPairCsvSourceBase extends Task {
		
	protected BaseCase workflow;
	
	public void setWorkflow(BaseCase workflow) {
		this.workflow = workflow;
	}
	
	public Workflow getWorkflow() {
		return workflow;
	}
	
	public String getId(){
		return "NumberPairCsvSource:"+workflow.getName();
	}
	
	protected Additions additions;
	
	protected void setAdditions(Additions additions) {
		this.additions = additions;
	}
	
	private Additions getAdditions() {
		return additions;
	}
	
	public void sendToAdditions(NumberPair numberPair) {
		getAdditions().send(numberPair, this.getClass().getName());
	}
	
	
	
	public abstract void produce();
	
	protected static final CsvParser parser = new CsvParser("csvs/NumberPairCsvSource.csv");
	protected static final Iterable<CSVRecord> records = parser.getRecordsIterable();
	
	/**
	 * Determines if the provided String {@param s} occurs within {@param column}.
	 * 
	 * @param s String to look for in CSV column
	 * @param column column number in CSV file
	 * @return true if provided String {@param s} occurs within {@param column} and false otherwise
	 */
	public static boolean inCollection(String s, int column) {
		try {
			for (CSVRecord record : records) {
				if (record.get(column).equals(s)) {
					return true;
				}
			}
		} catch (Exception e) {
			if (e instanceof IOException && e.getMessage().contains("failed to parse")) {
				// skip
			}
		}
		return false;
	}
	
}