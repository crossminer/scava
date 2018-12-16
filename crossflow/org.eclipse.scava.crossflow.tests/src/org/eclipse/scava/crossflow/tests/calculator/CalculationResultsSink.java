package org.eclipse.scava.crossflow.tests.calculator;

import java.io.File;

import org.eclipse.scava.crossflow.runtime.utils.CsvWriter;

public class CalculationResultsSink extends CalculationResultsSinkBase {
	
	protected CsvWriter writer = null;
	
	@Override
	public synchronized void consumeCalculationResults(CalculationResult result) {
		try {
			if (writer == null) {
				File output = new File(workflow.getOutputDirectory(), "output.csv");
				writer = new CsvWriter(output.getAbsolutePath(), 
						"a", "operator", "b", "result", "worker");
			}
			
			writer.writeRecord(result.getA(), result.getOperator(), 
					result.getB(), result.getResult(), result.getWorker());
			writer.flush();
		}
		catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

}