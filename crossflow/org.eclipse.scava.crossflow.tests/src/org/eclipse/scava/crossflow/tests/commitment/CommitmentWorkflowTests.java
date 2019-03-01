package org.eclipse.scava.crossflow.tests.commitment;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.scava.crossflow.runtime.Mode;
import org.eclipse.scava.crossflow.tests.WorkflowTests;
import org.junit.Test;

public class CommitmentWorkflowTests extends WorkflowTests {

	@Test
	public void testOutput() throws Exception {

		CommitmentWorkflow workflow = new CommitmentWorkflow();
		workflow.createBroker(createBroker);
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
		master.createBroker(createBroker);
		master.getAnimalSource().setAnimalNames(Arrays.asList("cat", "dog", "cat", "horse", "dog", "cat"));

		List<CommitmentWorkflow> workers = new ArrayList<>();

		for (int i = 0; i < 5; i++) {
			CommitmentWorkflow worker = master.createWorker();
			workers.add(worker);
		}

		master.run();
		// since this test does not use CompositeWorkflow to enable parallel execution,
		// starting multiple workers has to be made in an asynchronous manner otherwise
		// the first worker would finish the work before any of the other ones are even
		// initialised
		workers.parallelStream().forEach(w -> {
			try {
				w.run();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		waitFor(master);

		assertEquals(6, workers.stream().mapToInt(w -> w.getAnimalCounter().getOccurences()).sum());
		assertEquals(workers.stream().mapToInt(w -> w.getAnimalCounter().getExecutions()).sum(),
				workers.stream().mapToInt(w -> w.getAnimalCounter().getOccurences()).sum()
						+ workers.stream().mapToInt(w -> w.getAnimalCounter().getRejections()).sum());

	}

}
