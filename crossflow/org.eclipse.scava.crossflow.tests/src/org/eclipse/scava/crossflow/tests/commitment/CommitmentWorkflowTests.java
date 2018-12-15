package org.eclipse.scava.crossflow.tests.commitment;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.scava.crossflow.runtime.DirectoryCache;
import org.eclipse.scava.crossflow.runtime.Mode;
import org.eclipse.scava.crossflow.tests.WorkflowTests;
import org.junit.Test;

public class CommitmentWorkflowTests extends WorkflowTests {
		
	@Test
	public void testOutput() throws Exception {
		
		CommitmentWorkflow workflow = new CommitmentWorkflow();
		workflow.getAnimalSource().setAnimalNames(Arrays.asList("cat", "dog", "cat", "horse", "dog", "cat"));
		workflow.run();
		
		waitFor(workflow);
		
		assertEquals(12, workflow.getAnimalCounter().getExecutions());
		assertEquals(6, workflow.getAnimalCounter().getOccurences());
		assertEquals(6, workflow.getAnimalCounter().getRejections());
		
	}
	
	@Test
	public void testMasterWorkers() throws Exception {
		
		CommitmentWorkflow master = new CommitmentWorkflow(Mode.MASTER_BARE);
		master.getAnimalSource().setAnimalNames(
				Arrays.asList("cat", "dog", "cat", "horse", "dog", "cat"));
		master.run();
		
		List<CommitmentWorkflow> workers = new ArrayList<>();
		
		for (int i=0;i<5;i++) {
			CommitmentWorkflow worker = new CommitmentWorkflow(Mode.WORKER);
			workers.add(worker);
			worker.run();
		}
		
		waitFor(master);
		
		assertEquals(6, workers.stream().mapToInt(w -> w.getAnimalCounter().getOccurences()).sum());
		assertEquals(workers.stream().mapToInt(w -> w.getAnimalCounter().getExecutions()).sum(), 
				workers.stream().mapToInt(w -> w.getAnimalCounter().getOccurences()).sum() + 
				workers.stream().mapToInt(w -> w.getAnimalCounter().getRejections()).sum());
		
	}
	
}
