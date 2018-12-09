package org.eclipse.scava.crossflow.examples.firstcommitment.csvmdetech;

import org.eclipse.scava.crossflow.runtime.Task;
import org.eclipse.scava.crossflow.runtime.Workflow;
import java.io.IOException;
import org.apache.commons.csv.CSVRecord;
import org.eclipse.scava.crossflow.runtime.utils.CsvParser;
import org.eclipse.scava.crossflow.runtime.permanentqueues.*;

public abstract class MdeTechnologyCsvSourceBase extends Task {
		
	protected MdeTechnologyCsvExample workflow;
	
	public void setWorkflow(MdeTechnologyCsvExample workflow) {
		this.workflow = workflow;
	}
	
	public Workflow getWorkflow() {
		return workflow;
	}
	
	public String getId(){
		return "MdeTechnologyCsvSource:"+workflow.getName();
	}
	
	protected MdeTechnologies mdeTechnologies;
	
	protected void setMdeTechnologies(MdeTechnologies mdeTechnologies) {
		this.mdeTechnologies = mdeTechnologies;
	}
	
	private MdeTechnologies getMdeTechnologies() {
		return mdeTechnologies;
	}
	
	public void sendToMdeTechnologies(ExtensionKeywordTuple extensionKeywordTuple) {
		getMdeTechnologies().send(extensionKeywordTuple, this.getClass().getName());
	}
	
	
	
	public abstract void produce();
	
	protected static final CsvParser parser = new CsvParser("csvs/MDE.csv");
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