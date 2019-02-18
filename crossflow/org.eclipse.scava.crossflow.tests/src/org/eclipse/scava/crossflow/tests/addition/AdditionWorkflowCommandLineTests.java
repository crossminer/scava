package org.eclipse.scava.crossflow.tests.addition;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.Collectors;

import org.eclipse.scava.crossflow.tests.WorkflowTests;
import org.junit.Test;

public class AdditionWorkflowCommandLineTests extends WorkflowTests {

	@Test
	public void testMasterWorker() throws Exception {

		String[] broker = new String[] { "-instance", "aw1" };
		String[] noBroker = new String[] { "-createBroker", "false", "-instance", "aw1" };

		AdditionWorkflow master;
		if (singleBroker)
			master = AdditionWorkflow.run(noBroker);
		else
			master = AdditionWorkflow.run(broker);
		AdditionWorkflow worker = AdditionWorkflow.run(new String[] { "-mode", "worker", "-instance", "aw1" });

		waitFor(master);
		HashSet<Integer> expected = new HashSet<Integer>(Arrays.asList(new Integer[] { 2, 4, 6, 8, 10 }));
		System.out.println(expected);
		HashSet<Integer> actual = new HashSet<Integer>(master.getAdditionResultsSink().getNumbers());
		System.out.println(actual);
		assertEquals(expected, actual);
		assertTrue(master.getAdder().getExecutions() < 5);
		assertTrue(worker.getAdder().getExecutions() > 0);

	}

	@Test
	public void parallelTestMasterWorker() throws Exception {
		parallelTestMasterWorkerActual(1);
		// uses default parallelization
		parallelTestMasterWorkerActual(Runtime.getRuntime().availableProcessors());
	}

	public void parallelTestMasterWorkerActual(int p) throws Exception {

		String[] broker = new String[] { "-parallelization", "" + p, "-instance", "aw1", "-name", "m1" };
		String[] noBroker = new String[] { "-createBroker", "false", "-parallelization", "" + p, "-instance", "aw1",
				"-name", "m1" };

		CompositeAdditionWorkflow master;
		if (singleBroker)
			master = CompositeAdditionWorkflow.run(noBroker);
		else
			master = CompositeAdditionWorkflow.run(broker);
		CompositeAdditionWorkflow worker = CompositeAdditionWorkflow
				.run(new String[] { "-parallelization", "" + p, "-mode", "worker", "-instance", "aw1", "-name", "w1" });

		AdditionWorkflow m1 = master.getElements().get(0);

		waitFor(master);

		assertEquals(p, master.getElements().size());
		assertEquals(p, worker.getElements().size());

		// System.out.println(master.getElements().stream().map(e ->
		// e.getName()).collect(Collectors.toCollection(LinkedList::new)));
		// System.out.println(worker.getElements().stream().map(e ->
		// e.getName()).collect(Collectors.toCollection(LinkedList::new)));

		// System.out.println(m1.getAdditionResultsSink().getNumbers());

		Integer[] results = new Integer[] { 2, 4, 6, 8, 10 };

		assertEquals(new HashSet<Integer>(Arrays.asList(results)),
				new HashSet<>(m1.getAdditionResultsSink().getNumbers()));

		// System.out.println((int)
		// master.getElements().stream().collect(Collectors.summingInt(e ->
		// e.getAdder().getExecutions())));
		// System.out.println((int)
		// worker.getElements().stream().collect(Collectors.summingInt(e ->
		// e.getAdder().getExecutions())));

		assertTrue(master.getElements().stream().collect(Collectors.summingInt(e -> e.getAdder().getExecutions())) < 5);
		assertTrue(worker.getElements().stream().collect(Collectors.summingInt(e -> e.getAdder().getExecutions())) > 0);

	}

	/*
	 * @Test(expected = Exception.class) public void testWorkerWithoutMaster()
	 * throws Exception { AdditionWorkflow worker = AdditionWorkflow.run(new
	 * String[] {"-mode", "worker"}); waitFor(worker); }
	 */

}
