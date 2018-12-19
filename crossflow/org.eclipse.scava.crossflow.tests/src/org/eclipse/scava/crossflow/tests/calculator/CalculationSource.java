package org.eclipse.scava.crossflow.tests.calculator;

import java.io.File;

import org.apache.commons.csv.CSVRecord;
import org.eclipse.scava.crossflow.runtime.utils.CsvParser;

public class CalculationSource extends CalculationSourceBase {
	
	@Override
	public void produce() throws Exception {
		CsvParser parser = new CsvParser(new File(workflow.getInputDirectory(), "input.csv").getAbsolutePath());
		
		for (CSVRecord record : parser.getRecordsList()){
			Calculation calculation = new Calculation();
			calculation.setA(Integer.parseInt(record.get(0)));
			calculation.setOperator(record.get(1));
			calculation.setB(Integer.parseInt(record.get(2)));
			sendToCalculations(calculation);
		}
	
	}

}