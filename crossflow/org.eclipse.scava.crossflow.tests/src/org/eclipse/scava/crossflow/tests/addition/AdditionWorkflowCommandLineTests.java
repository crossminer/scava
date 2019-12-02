package org.eclipse.scava.crossflow.tests.addition;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.scava.crossflow.tests.WorkflowTests;
import org.junit.Test;

public class AdditionWorkflowCommandLineTests extends WorkflowTests {

	@Test
	public void testMasterWorker() throws Exception {

		String[] broker = new String[] { "-instance", "aw1" };
		String[] noBroker = new String[] { "-createBroker", "false", "-instance", "aw1" };

		AdditionWorkflow master;
		if (!createBroker)
			master = AdditionWorkflow.run(noBroker);
		else
			master = AdditionWorkflow.run(broker);
		Thread.sleep(500);
		AdditionWorkflow worker = AdditionWorkflow.run(new String[] { "-mode", "worker", "-instance", "aw1" });

		waitFor(master);
		HashSet<Integer> expected = new HashSet<>(Arrays.asList(new Integer[] { 2, 4, 6, 8, 10 }));
		System.out.println(expected);
		HashSet<Integer> actual = new HashSet<>(master.getAdditionResultsSink().getNumbers());
		System.out.println(actual);
		assertEquals(expected, actual);
		assertTrue(master.getAdder().getExecutions() < 5);
		assertTrue(worker.getAdder().getExecutions() > 0);

	}

	@Test
	public void parallelTestMasterWorker() throws Exception {
		parallelTestMasterWorkerActual(1);
		parallelTestMasterWorkerActual(4);
	}

	public void parallelTestMasterWorkerActual(int p) throws Exception {

		String[] broker = new String[] { "-parallelization", "" + p, "-instance", "aw1", "-name", "m1" };
		String[] noBroker = new String[] { "-createBroker", "false", "-parallelization", "" + p, "-instance", "aw1",
				"-name", "m1" };

		AdditionWorkflow master = AdditionWorkflow.run(createBroker ? broker : noBroker);
		Thread.sleep(500);
		AdditionWorkflow worker = AdditionWorkflow
				.run(new String[] { "-parallelization", "" + p, "-mode", "worker", "-instance", "aw1", "-name", "w1" });

		waitFor(master);

		assertEquals(p, master.getAdders().size());
		assertEquals(p, worker.getAdders().size());

		// System.out.println(master.getElements().stream().map(e ->
		// e.getName()).collect(Collectors.toCollection(LinkedList::new)));
		// System.out.println(worker.getElements().stream().map(e ->
		// e.getName()).collect(Collectors.toCollection(LinkedList::new)));

		// System.out.println(m1.getAdditionResultsSink().getNumbers());

		List<Integer> results = new ArrayList<>();
		for (int i = 1; i <= master.getParallelization(); i++)
			results.addAll(Arrays.asList(1 * i * 2, 2 * i * 2, 3 * i * 2, 4 * i * 2, 5 * i * 2));

		assertEquals(new HashSet<>(results), new HashSet<>(master.getAdditionResultsSink().getNumbers()));

		System.out.println("" + master.getAdders().stream().collect(Collectors.summingInt(a -> a.getExecutions())));
		System.out.println("" + worker.getAdders().stream().collect(Collectors.summingInt(a -> a.getExecutions())));

		assertTrue(master.getAdders().stream().collect(Collectors.summingInt(a -> a.getExecutions())) < 5
				* master.getParallelization());
		assertTrue(worker.getAdders().stream().collect(Collectors.summingInt(a -> a.getExecutions())) > 0);

	}

	/*
	 * @Test(expected = Exception.class) public void testWorkerWithoutMaster()
	 * throws Exception { AdditionWorkflow worker = AdditionWorkflow.run(new
	 * String[] {"-mode", "worker"}); waitFor(worker); }
	 */

}
