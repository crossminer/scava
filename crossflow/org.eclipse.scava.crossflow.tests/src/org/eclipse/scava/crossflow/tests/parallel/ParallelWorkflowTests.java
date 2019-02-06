package org.eclipse.scava.crossflow.tests.parallel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.scava.crossflow.runtime.BuiltinStreamConsumer;
import org.eclipse.scava.crossflow.runtime.DirectoryCache;
import org.eclipse.scava.crossflow.runtime.Mode;
import org.eclipse.scava.crossflow.runtime.utils.Result;
import org.eclipse.scava.crossflow.runtime.utils.StreamMetadata;
import org.eclipse.scava.crossflow.runtime.utils.StreamMetadata.Stream;
import org.eclipse.scava.crossflow.runtime.utils.TaskStatus;
import org.eclipse.scava.crossflow.runtime.utils.TaskStatus.TaskStatuses;
import org.eclipse.scava.crossflow.tests.WorkflowTests;
import org.eclipse.scava.crossflow.tests.minimal.CompositeMinimalWorkflow;
import org.eclipse.scava.crossflow.tests.minimal.MinimalWorkflow;
import org.junit.Test;

public class ParallelWorkflowTests extends WorkflowTests {

	int parallelization = Math.max(Runtime.getRuntime().availableProcessors(), 2);

	@Test
	public void simpleParallelOutputTests() throws Exception {

		simpleParallelOutputTestActual(false);
		simpleParallelOutputTestActual(true);

	}

	public void simpleParallelOutputTestActual(boolean parallel) throws Exception {

		CompositeMinimalWorkflow workflow = new CompositeMinimalWorkflow(Mode.MASTER, parallel ? parallelization : 1,
				"wf");

		//
		workflow.setInstanceId("testStreamMetadataTopicWorkflow");
		//
		workflow.setEnablePrefetch(false);
		//
		List<Integer> numbers = new LinkedList<Integer>();
		for (int i = 1; i <= Math.max(4 * parallelization, 8); i++)
			numbers.add(i);
		//
		// workflow.setStreamMetadataPeriod(500);
		workflow.getElements().get(0).getMinimalSource().setNumbers(numbers);
		workflow.getElements().forEach(e -> e.getCopierTask().setDelay(500));
		// workflow.getMinimalSink().setDelay(500);

		//
		workflow.run();

		waitFor(workflow);

		System.out.println(workflow.getElements().get(0).getMinimalSink().getNumbers());

		assertTrue(parallel ? !numbers.equals(workflow.getElements().get(0).getMinimalSink().getNumbers())
				: numbers.equals(workflow.getElements().get(0).getMinimalSink().getNumbers()));

	}

	@Test
	public void parallelTestCache() throws Exception {

		int num = Math.max(4 * parallelization, 8);
		List<Integer> numbers = new LinkedList<Integer>();
		for (int i = 1; i <= num; i++)
			numbers.add(i);

		CompositeMinimalWorkflow workflow = new CompositeMinimalWorkflow(Mode.MASTER, parallelization, "wf");
		MinimalWorkflow master = workflow.getElements().get(0);
		master.getMinimalSource().setNumbers(numbers);
		final DirectoryCache cache = new DirectoryCache();
		workflow.getElements().forEach(e -> e.setCache(cache));
		workflow.run();

		waitFor(workflow);

		assertEquals(num, master.getMinimalSink().getNumbers().size());
		assertEquals(num, (int) workflow.getElements().stream()
				.collect(Collectors.summingInt(e -> e.getCopierTask().getExecutions())));

		workflow = new CompositeMinimalWorkflow(Mode.MASTER, parallelization, "wf");
		master = workflow.getElements().get(0);
		master.getMinimalSource().setNumbers(numbers);
		workflow.getElements().forEach(e -> e.setCache(new DirectoryCache(cache.getDirectory())));
		workflow.run();

		waitFor(workflow);

		assertEquals(num, master.getMinimalSink().getNumbers().size());
		assertEquals(0, (int) workflow.getElements().stream()
				.collect(Collectors.summingInt(e -> e.getCopierTask().getExecutions())));
	}

	@Test
	public void parallelTestResultsTopic() throws Exception {
		testResultsTopicActual(0, true);
		// ensures result topic is accurate with an initial delay on master
		testResultsTopicActual(4000, true);
		// ensures result topic is accurate with an initial delay on all (parallel)
		// workflows
		testResultsTopicActual(4000, false);
	}

	public void testResultsTopicActual(int initialDelay, boolean masterOnlyDelay) throws Exception {

		int num = Math.max(4 * parallelization, 8);
		Set<Integer> numbers = new HashSet<Integer>();
		for (int i = 1; i <= num; i++)
			numbers.add(i);

		CompositeMinimalWorkflow workflow = new CompositeMinimalWorkflow(Mode.MASTER, parallelization, "wf");
		Set<Integer> results = new HashSet<Integer>();
		MinimalWorkflow master = workflow.getElements().get(0);
		master.getMinimalSource().setNumbers(new ArrayList<>(numbers));
		//
		master.getMinimalSource().setSendToResults(true);
		master.getResultsTopic().addConsumer(new BuiltinStreamConsumer<Result>() {
			@Override
			public void consume(Result t) {
				results.add((Integer) t.get(0));
			}
		});
		//
		workflow.run(initialDelay, masterOnlyDelay);
		//
		waitFor(workflow);
		System.out.println(numbers);
		System.out.println(results);
		assertEquals(numbers, results);
	}

	private class TaskStatusBuiltinStreamConsumer implements BuiltinStreamConsumer<TaskStatus> {
		public int outputQueueSize = 0;

		@Override
		public void consume(TaskStatus t) {

			//System.out.println(t);

			if (t.getCaller().startsWith("CopierTask:wf:CompositeMinimalWorkflow")
					&& t.getStatus().equals(TaskStatuses.WAITING))
				outputQueueSize++;
			if (t.getCaller().startsWith("MinimalSink:wf:CompositeMinimalWorkflow")
					&& t.getStatus().equals(TaskStatuses.WAITING))
				outputQueueSize--;
			//
		}
	}

	private abstract class StreamMetadataBuiltinStreamConsumer implements BuiltinStreamConsumer<StreamMetadata> {
		public List<Map.Entry<Long, Long>> failures = new ArrayList<Map.Entry<Long, Long>>();;
		public boolean updated = false;
		public long maxQueueSize = 0;
		public int nonZeroMatches = 0;
	};

	@Test
	public void parallelTestStreamMetadataTopic() throws Exception {
		testStreamMetadataTopicActual(false);
		// ensures metadata checks are valid with prefetching enabled for queues
		testStreamMetadataTopicActual(true);
	}

	public synchronized void testStreamMetadataTopicActual(boolean enablePrefetch) throws Exception {

		CompositeMinimalWorkflow workflow = new CompositeMinimalWorkflow(Mode.MASTER, parallelization, "wf");

		//
		workflow.setInstanceId("testStreamMetadataTopicWorkflow");
		//
		workflow.setEnablePrefetch(enablePrefetch);
		//
		int num = Math.max(4 * parallelization, 8);
		Set<Integer> numbers = new HashSet<Integer>();
		for (int i = 1; i <= num; i++)
			numbers.add(i);
		//
		MinimalWorkflow master = workflow.getElements().get(0);
		master.setTerminationTimeout(5000);
		master.setStreamMetadataPeriod(500);
		master.getMinimalSource().setNumbers(new ArrayList<>(numbers));
		workflow.getElements().forEach(e -> e.getCopierTask().setDelay(10));
		master.getMinimalSink().setDelay(500);

		TaskStatusBuiltinStreamConsumer con = new TaskStatusBuiltinStreamConsumer();
		StreamMetadataBuiltinStreamConsumer con2 = new StreamMetadataBuiltinStreamConsumer() {
			public static final String OUTPUT_STREAM_ID = "OutputPost.MinimalSink.testStreamMetadataTopicWorkflow";

			@Override
			public void consume(StreamMetadata t) {

				// t.pruneNames(OUTPUT_STREAM_ID.length());
				// System.out.println(t);

				long streamSize = -1;
				long statusBasedSize = -1;
				long streamInFlight = -1;
				try {
					//
					try {
						streamSize = t.getStream(OUTPUT_STREAM_ID).getSize();
						streamInFlight = t.getStream(OUTPUT_STREAM_ID).getInFlight();
					} catch (Exception e) {
						System.out.println("stream: " + OUTPUT_STREAM_ID + " not found!");
					}
					statusBasedSize = (long) con.outputQueueSize;

					//
					System.out.println(streamSize + " (" + streamInFlight + ") | " + statusBasedSize);
					if (!updated && streamSize < statusBasedSize) {
						// mbean has not yet updated
						System.out.println("mbean not up to date yet");
						return;
					}
					// mbean has updated
					updated = true;
					assertEquals(streamSize, (long) statusBasedSize);
					if (streamSize > 0)
						nonZeroMatches++;
					maxQueueSize = streamSize > maxQueueSize ? streamSize : maxQueueSize;
				} catch (Throwable e) {
					// e.printStackTrace();
					failures.add(new AbstractMap.SimpleEntry<Long, Long>(streamSize, statusBasedSize));
				}
			}
		};

		master.getTaskStatusTopic().addConsumer(con);
		//
		master.getStreamMetadataTopic().addConsumer(con2);
		//
		workflow.run();
		//
		waitFor(workflow);

		System.out.println(master.getMinimalSink().getNumbers());

		System.out
				.println("need: " + con2.failures.size() + " < " + con2.nonZeroMatches + " maxQ: " + con2.maxQueueSize);

		assertTrue(con2.failures.size() == 0 || con2.failures.size() + 1 < con2.nonZeroMatches);

		assertTrue(con2.updated);

		assertTrue(con2.maxQueueSize > 0);

	}

	@Test
	public void parallelTestStreamMetadataTopicMultiConsumer() throws Exception {
		List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
		CompositeMinimalWorkflow workflow = new CompositeMinimalWorkflow(Mode.MASTER, parallelization, "wf");
		workflow.setInstanceId("testStreamMetadataTopicWorkflowMC");

		MinimalWorkflow master = workflow.getElements().get(0);
		master.setStreamMetadataPeriod(500);
		master.getMinimalSource().setNumbers(numbers);
		master.getMinimalSink().setDelay(100);

		StreamMetadataBuiltinStreamConsumer con = new StreamMetadataBuiltinStreamConsumer() {
			public static final String INPUT_STREAM_ID = "InputPost.CopierTask.testStreamMetadataTopicWorkflowMC";

			@Override
			public void consume(StreamMetadata t) {

				// t.pruneNames(INPUT_STREAM_ID.length());
				// System.out.println(t);

				long subs = -1;
				try {
					Stream stream = t.getStream(INPUT_STREAM_ID);
					subs = stream.getNumberOfSubscribers();
					System.out.println(subs + " == " + (1 + workflow.getParallelization()));
					assertEquals(subs, 1 + workflow.getParallelization());
				} catch (Throwable e) {
					if (failures == null)
						failures = new ArrayList<Map.Entry<Long, Long>>();
					failures.add(new AbstractMap.SimpleEntry<Long, Long>(subs, ((long) 2)));
				}
			}
		};
		master.getStreamMetadataTopic().addConsumer(con);
		//
		workflow.run();

		MinimalWorkflow workflow2 = new MinimalWorkflow(Mode.WORKER);
		workflow2.setInstanceId("testStreamMetadataTopicWorkflowMC");
		workflow2.setMaster(workflow.getMaster());
		workflow2.setName("worker");
		workflow2.run();

		//

		waitFor(workflow);

		assertEquals(0, con.failures.size());
	}

}
