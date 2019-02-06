package org.eclipse.scava.crossflow.tests.addition;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.scava.crossflow.runtime.DirectoryCache;
import org.eclipse.scava.crossflow.runtime.Mode;
import org.eclipse.scava.crossflow.runtime.utils.TaskStatus;
import org.eclipse.scava.crossflow.runtime.utils.TaskStatus.TaskStatuses;
import org.eclipse.scava.crossflow.tests.WorkflowTests;
import org.eclipse.scava.crossflow.tests.util.BuiltinStreamRecorder;
import org.junit.Test;

public class AdditionWorkflowTests extends WorkflowTests {

	@Test
	public void testOutput() throws Exception {
		AdditionWorkflow workflow = new AdditionWorkflow();
		workflow.getNumberPairSource().setNumbers(Arrays.asList(1, 2));
		workflow.setTerminationTimeout(0);
		workflow.run();
		waitFor(workflow);
		assertArrayEquals(new Integer[] { 2, 4 }, workflow.getAdditionResultsSink().getNumbers().toArray());
	}

	@Test
	public void testCachingWithCache() throws Exception {

		AdditionWorkflow workflow = new AdditionWorkflow();
		workflow.getAdder().setCaching(true);
		workflow.setCache(new DirectoryCache());
		workflow.getNumberPairSource().setNumbers(Arrays.asList(1, 2));
		workflow.run();
		waitFor(workflow);

		AdditionWorkflow fresh = new AdditionWorkflow();
		fresh.getAdder().setCaching(true);
		fresh.setCache(new DirectoryCache(((DirectoryCache) workflow.getCache()).getDirectory()));
		fresh.getNumberPairSource().setNumbers(Arrays.asList(1, 2, 3));
		fresh.run();
		waitFor(fresh);

		assertEquals(1, fresh.getAdder().getExecutions());

	}

	@Test
	public void testUncacheable() throws Exception {
		AdditionWorkflow workflow = new AdditionWorkflow();
		workflow.getAdder().setCaching(true);
		workflow.setCache(new DirectoryCache());
		workflow.getNumberPairSource().setNumbers(Arrays.asList(1, 2));
		workflow.getAdder().setCacheable(false);
		workflow.run();
		waitFor(workflow);

		AdditionWorkflow fresh = new AdditionWorkflow();
		fresh.getAdder().setCaching(true);
		fresh.getAdder().setCacheable(false);
		fresh.setCache(new DirectoryCache(((DirectoryCache) workflow.getCache()).getDirectory()));
		fresh.getNumberPairSource().setNumbers(Arrays.asList(1, 2, 3));
		fresh.run();
		waitFor(fresh);

		assertEquals(3, fresh.getAdder().getExecutions());
	}

	@Test
	public void testCachingWithoutCache() throws Exception {

		AdditionWorkflow workflow = new AdditionWorkflow();
		workflow.getAdder().setCaching(true);
		workflow.getNumberPairSource().setNumbers(Arrays.asList(1, 2));
		workflow.run();
		waitFor(workflow);

		AdditionWorkflow fresh = new AdditionWorkflow();
		fresh.getAdder().setCaching(true);
		fresh.getNumberPairSource().setNumbers(Arrays.asList(1, 2, 3));
		fresh.run();
		waitFor(fresh);

		assertEquals(3, fresh.getAdder().getExecutions());
	}

	@Test
	public void testMasterWorker() throws Exception {
		AdditionWorkflow master = new AdditionWorkflow(Mode.MASTER);
		master.setTerminationTimeout(5000);
		List<Integer> input = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
		master.getNumberPairSource().setNumbers(input);
		master.setEnablePrefetch(false);

		AdditionWorkflow worker = master.createWorker();

		// ensure that the queue is producing quicker than being consumed else all
		// elements will go to the first subscriber, as he will be always free to to the
		// work
		master.getNumberPairSource().setInterval(100);
		master.getAdder().setInterval(500);
		worker.getAdder().setInterval(500);
		//

		master.run();
		worker.run();

		waitFor(master);

		// System.out.println("m:" + master.getAdder().getExecutions());
		// System.out.println("w:" + worker.getAdder().getExecutions());

		assertEquals(true, master.getAdder().getExecutions() < 10);
		assertEquals(10, worker.getAdder().getExecutions() + master.getAdder().getExecutions());

		HashSet<Integer> expected = new HashSet<Integer>();
		input.stream().map(i -> i * 2).forEach(i -> expected.add(i));
		assertEquals(expected, new HashSet<Integer>(master.getAdditionResultsSink().getNumbers()));
	}

	@Test
	public void testBareMasterWorker() throws Exception {
		AdditionWorkflow master = new AdditionWorkflow(Mode.MASTER_BARE);
		master.setTerminationTimeout(5000);
		master.getNumberPairSource().setNumbers(Arrays.asList(1, 2, 3, 4, 5));

		AdditionWorkflow worker = master.createWorker();

		master.run();
		worker.run();

		waitFor(master);

		assertNull(master.getAdder());
		assertEquals(5, worker.getAdder().getExecutions());

		assertArrayEquals(new Integer[] { 2, 4, 6, 8, 10 }, master.getAdditionResultsSink().getNumbers().toArray());
	}

	@Test
	public void testTaskStatusNotifications() throws Exception {

		AdditionWorkflow workflow = new AdditionWorkflow();
		List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
		workflow.getNumberPairSource().setNumbers(numbers);

		BuiltinStreamRecorder<TaskStatus> recorder = new BuiltinStreamRecorder<>();
		workflow.getTaskStatusTopic().addConsumer(recorder);

		workflow.run();
		waitFor(workflow);

		// one for each task and one from the producer
		assertEquals(numbers.size() * 2 + 1, recorder.getRecorded().stream()
				.filter(r -> (r.getStatus() == TaskStatuses.INPROGRESS)).collect(Collectors.toList()).size());

		assertEquals(numbers.size() * 2 + 1, recorder.getRecorded().stream()
				.filter(r -> (r.getStatus() == TaskStatuses.WAITING)).collect(Collectors.toList()).size());

	}

	@Test
	public void testWithExistingBroker() throws Exception {

		startBroker();

		AdditionWorkflow workflow = new AdditionWorkflow();
		workflow.createBroker(false);
		workflow.getNumberPairSource().setNumbers(Arrays.asList(1, 2));
		workflow.setTerminationTimeout(0);
		workflow.run();

		waitFor(workflow);
		assertArrayEquals(new Integer[] { 2, 4 }, workflow.getAdditionResultsSink().getNumbers().toArray());

		stopBroker();
	}

	@Test
	public void testParallelWorkflows() throws Exception {

		startBroker();

		AdditionWorkflow workflow1 = new AdditionWorkflow();
		workflow1.createBroker(false);
		workflow1.getNumberPairSource().setNumbers(Arrays.asList(1, 2));

		AdditionWorkflow workflow2 = new AdditionWorkflow();
		workflow2.createBroker(false);
		workflow2.getNumberPairSource().setNumbers(Arrays.asList(3, 4));

		workflow1.run();
		workflow2.run();

		waitFor(workflow1);
		waitFor(workflow2);

		assertArrayEquals(new Integer[] { 2, 4 }, workflow1.getAdditionResultsSink().getNumbers().toArray());
		assertArrayEquals(new Integer[] { 6, 8 }, workflow2.getAdditionResultsSink().getNumbers().toArray());

		stopBroker();
	}

}
