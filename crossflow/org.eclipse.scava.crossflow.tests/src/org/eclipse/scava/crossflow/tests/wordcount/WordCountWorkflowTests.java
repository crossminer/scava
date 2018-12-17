package org.eclipse.scava.crossflow.tests.wordcount;

import java.io.File;

import org.eclipse.scava.crossflow.tests.WorkflowTests;
import org.junit.Test;

public class WordCountWorkflowTests extends WorkflowTests {
	
	@Test
	public void testOutput() throws Exception {
		WordCountWorkflow workflow = new WordCountWorkflow();
		workflow.setInputDirectory(new File("inputs/wordcount"));
		workflow.setOutputDirectory(new File("outputs/wordcount"));
		workflow.run();
		
		waitFor(workflow);
		
	}
	
}
