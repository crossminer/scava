package org.eclipse.scava.crossflow.tests.calculator;

import java.io.File;

import org.eclipse.scava.crossflow.runtime.utils.CsvWriter;

public class CalculationResultsSink extends CalculationResultsSinkBase {
	
	protected CsvWriter writer = null;
	
	@Override
	public synchronized void consumeCalculationResults(CalculationResult result) throws Exception {
		if (writer == null) {
			File output = new File(workflow.getOutputDirectory(), "output.csv");
			if (output.exists()) { output.delete(); }
			writer = new CsvWriter(output.getAbsolutePath(), 
					"a", "operator", "b", "result", "worker", "workerLang");
		}
		
		writer.writeRecord(result.getA(), result.getOperator(), 
				result.getB(), result.getResult(), result.getWorker(), result.getWorkerLang());
		writer.flush();
	}

}