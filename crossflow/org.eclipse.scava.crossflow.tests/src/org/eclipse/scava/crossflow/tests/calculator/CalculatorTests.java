package org.eclipse.scava.crossflow.tests.calculator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;

import org.eclipse.scava.crossflow.runtime.DirectoryCache;
import org.eclipse.scava.crossflow.runtime.utils.CsvParser;
import org.eclipse.scava.crossflow.tests.WorkflowTests;
import org.junit.Test;

public class CalculatorTests extends WorkflowTests {
	
	
	@Test
	public void testOutput() throws Exception {
		
		File output = new File("outputs/calculator/output.csv");
		if (output.exists()) output.delete();
		
		CalculatorWorkflow workflow = new CalculatorWorkflow();
		if (singleBroker)
			workflow.createBroker(false);
		workflow.setName("master");
		workflow.setInputDirectory(new File("inputs/calculator"));
		workflow.setOutputDirectory(new File("outputs/calculator"));
		workflow.run();
		
		waitFor(workflow);
		
		CsvParser parser = new CsvParser(output.getAbsolutePath());
		assertEquals("8", parser.getRecordsList().get(0).get(3));
		
	}
	
	@Test
	public void testCache() throws Exception {
		CalculatorWorkflow workflow = new CalculatorWorkflow();
		if (singleBroker)
			workflow.createBroker(false);
		workflow.setInputDirectory(new File("inputs/calculator"));
		workflow.setOutputDirectory(new File("outputs/calculator"));
		DirectoryCache cache = new DirectoryCache();
		workflow.setCache(cache);
		
		workflow.run();
		waitFor(workflow);
		
		workflow = new CalculatorWorkflow();
		workflow.setInputDirectory(new File("inputs/calculator"));
		workflow.setOutputDirectory(new File("outputs/calculator"));
		workflow.setCache(new DirectoryCache(cache.getDirectory()));
		
		workflow.run();
		waitFor(workflow);
		
		assertEquals(0, workflow.getCalculator().getExecutions());
	}
	
	
	
	
}
