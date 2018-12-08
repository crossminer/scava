package org.eclipse.scava.crossflow.tests.csvsourcesinkcase;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.eclipse.scava.crossflow.tests.WorkflowTests;
import org.junit.Test;

public class CsvSourceSinkTestCase extends WorkflowTests {
	
	private final static Path ACTUAL_TEST_CASE_RESULT = Paths.get("csvs/AdditionResultsCsvSink.csv");
	private final static Path EXPECTED_TEST_CASE_RESULT = Paths.get("csvs/AdditionResultsCsvSink-expected.csv");
	
	@Test
	public void testCase() throws Exception {
		
		Files.deleteIfExists(ACTUAL_TEST_CASE_RESULT);
		
		// setup test case configuration
		BaseCase workflow = new BaseCase();
		workflow.setTerminationTimeout(0);
		workflow.setName("Master-"+UUID.randomUUID().toString());
		
		// run test case configuration
		workflow.run();
		waitFor(workflow);
	
		// assert
		String expectedResult = FileUtils.readFileToString(new File(EXPECTED_TEST_CASE_RESULT.toUri()));
		String actualResult = FileUtils.readFileToString(new File(ACTUAL_TEST_CASE_RESULT.toUri())).replaceAll("(\\r)", "");
		
		assertTrue( actualResult.equals(expectedResult) );
		
	}

}
