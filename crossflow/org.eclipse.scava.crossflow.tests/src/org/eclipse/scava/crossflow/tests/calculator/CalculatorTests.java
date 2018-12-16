package org.eclipse.scava.crossflow.tests.calculator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;

import org.eclipse.scava.crossflow.runtime.utils.CsvParser;
import org.eclipse.scava.crossflow.tests.WorkflowTests;
import org.junit.Test;

public class CalculatorTests extends WorkflowTests {
	
	@Test
	public void testOutput() throws Exception {
		
		File output = new File("outputs/calculator/output.csv");
		if (output.exists()) output.delete();
		
		CalculatorWorkflow workflow = new CalculatorWorkflow();
		workflow.setName("master");
		workflow.setInputDirectory(new File("inputs/calculator"));
		workflow.setOutputDirectory(new File("outputs/calculator"));
		workflow.run();
		
		waitFor(workflow);
		
		CsvParser parser = new CsvParser(output.getAbsolutePath());
		assertEquals("8", parser.getRecordsList().get(0).get(3));
		
	}
	
}
