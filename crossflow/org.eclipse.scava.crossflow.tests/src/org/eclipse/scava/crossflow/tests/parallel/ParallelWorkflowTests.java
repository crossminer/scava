package org.eclipse.scava.crossflow.tests.parallel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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
import org.eclipse.scava.crossflow.runtime.utils.StreamMetadata;
import org.eclipse.scava.crossflow.runtime.utils.StreamMetadataSnapshot;
import org.eclipse.scava.crossflow.runtime.utils.TaskStatus;
import org.eclipse.scava.crossflow.runtime.utils.TaskStatus.TaskStatuses;
import org.eclipse.scava.crossflow.tests.WorkflowTests;
import org.eclipse.scava.crossflow.tests.minimal.MinimalWorkflow;
import org.eclipse.scava.crossflow.tests.util.Retry;
import org.eclipse.scava.crossflow.tests.util.RetryRule;
import org.junit.Rule;
import org.junit.Test;

public class ParallelWorkflowTests extends WorkflowTests {

	int parallelization = 4;

	// define the number of retries for tests in this class (annotated with @Retry)
	@Rule
	public RetryRule rule = new RetryRule(3);

	@Test
	public void simpleParallelOutputTestsNP() throws Exception {
		simpleParallelOutputTestActual(false);
	}

	@Test
	@Retry
	public void simpleParallelOutputTestsP() throws Exception {
		simpleParallelOutputTestActual(true);
	}

	public void simpleParallelOutputTestActual(boolean parallel) throws Exception {

		MinimalWorkflow workflow = new MinimalWorkflow(Mode.MASTER, parallel ? parallelization : 1);
		System.out.println("running spota: " + workflow.getParallelization());
		workflow.setName("wf");
		workflow.createBroker(createBroker);
		workflow.setInstanceId("testStreamMetadataTopicWorkflow");

		//
		workflow.setEnablePrefetch(false);
		//

		List<Integer> numbers = new LinkedList<>();
		for (int i = 1; i <= Math.max(4 * parallelization, 8); i++)
			numbers.add(i);

		//
		workflow.setStreamMetadataPeriod(100 * parallelization);
		//

		workflow.getMinimalSource().setNumbers(numbers);
		workflow.getCopierTasks().forEach(c -> c.setDelay(500 * parallelization));
		// workflow.getMinimalSink().setDelay(500);

		//

		StreamMetadataBuiltinStreamConsumer con = new StreamMetadataBuiltinStreamConsumer() {

			boolean done = false;

			@Override
			public void consume(StreamMetadataSnapshot t) {

				if (initialt == 0)
					initialt = System.currentTimeMillis();
				if (!done)
					finalt = System.currentTimeMillis();

				if (!t.getStreams().stream().anyMatch(s -> s.getSize() > 0 || s.getInFlight() > 0))
					done = true;
				
				//
				//t.pruneNames(40);
				//System.out.println(t);

			}

		};

		workflow.getStreamMetadataTopic().addConsumer(con);

		//
		workflow.run();

		waitFor(workflow);

		System.out.println("execution took: " + (con.finalt - con.initialt) / 1000 + "s "
				+ (con.finalt - con.initialt) % 1000 + "ms | expected time ~" + 500 * numbers.size() / 1000 + "s");

		workflow.getCopierTasks().forEach(c -> System.out.print(c.getExecutions() + " : "));
		System.out.println();
		System.out.println(workflow.getMinimalSink().getNumbers());

		assertFalse(workflow.getCopierTasks().stream().anyMatch(e -> e.getExecutions() == 0));
		assertTrue(parallel ? !numbers.equals(workflow.getMinimalSink().getNumbers())
				: numbers.equals(workflow.getMinimalSink().getNumbers()));

	}

	@Test
	public void parallelTestCache() throws Exception {

		int num = Math.max(4 * parallelization, 8);
		List<Integer> numbers = new LinkedList<>();
		for (int i = 1; i <= num; i++)
			numbers.add(i);

		MinimalWorkflow workflow = new MinimalWorkflow(Mode.MASTER, parallelization);
		workflow.setName("wf");
		workflow.createBroker(createBroker);

		workflow.getMinimalSource().setNumbers(numbers);
		final DirectoryCache cache = new DirectoryCache();
		workflow.setCache(cache);
		workflow.run();

		waitFor(workflow);

		assertEquals(num, workflow.getMinimalSink().getNumbers().size());
		assertEquals(num,
				(int) workflow.getCopierTasks().stream().collect(Collectors.summingInt(c -> c.getExecutions())));

		workflow = new MinimalWorkflow(Mode.MASTER, parallelization);
		workflow.setName("wf");
		workflow.createBroker(createBroker);

		workflow.getMinimalSource().setNumbers(numbers);
		workflow.setCache(new DirectoryCache(cache.getDirectory()));
		workflow.run();

		waitFor(workflow);

		assertEquals(num, workflow.getMinimalSink().getNumbers().size());
		assertEquals(0,
				(int) workflow.getCopierTasks().stream().collect(Collectors.summingInt(c -> c.getExecutions())));
	}

	private class TaskStatusBuiltinStreamConsumer implements BuiltinStreamConsumer<TaskStatus> {
		public int outputQueueSize = 0;

		@Override
		public void consume(TaskStatus t) {

			// System.out.println(t);

			if (t.getCaller().startsWith("CopierTask:wf") && t.getStatus().equals(TaskStatuses.WAITING))
				outputQueueSize++;
			if (t.getCaller().startsWith("MinimalSink:wf") && t.getStatus().equals(TaskStatuses.WAITING))
				outputQueueSize--;
			//
		}
	}

	private abstract class StreamMetadataBuiltinStreamConsumer implements BuiltinStreamConsumer<StreamMetadataSnapshot> {
		public final List<Map.Entry<Long, Long>> failures = new ArrayList<>();
		public boolean updated = false;
		public long maxQueueSize = 0;
		public int nonZeroMatches = 0;
		//
		public long initialt = 0;
		public long finalt = 0;
	};

	@Test
	public void parallelTestStreamMetadataTopic() throws Exception {
		testStreamMetadataTopicActual(false);
		// ensures metadata checks are valid with prefetching enabled for queues
		testStreamMetadataTopicActual(true);
	}

	public synchronized void testStreamMetadataTopicActual(boolean enablePrefetch) throws Exception {

		MinimalWorkflow workflow = new MinimalWorkflow(Mode.MASTER, parallelization);
		workflow.setName("wf");
		workflow.createBroker(createBroker);

		//
		workflow.setInstanceId("testStreamMetadataTopicWorkflow");
		//
		workflow.setEnablePrefetch(enablePrefetch);
		//
		int num = Math.max(4 * parallelization, 8);
		Set<Integer> numbers = new HashSet<>();
		for (int i = 1; i <= num; i++)
			numbers.add(i);
		//
		workflow.setTerminationTimeout(5000);
		workflow.setStreamMetadataPeriod(500);
		workflow.getMinimalSource().setNumbers(new ArrayList<>(numbers));
		workflow.getCopierTasks().forEach(c -> c.setDelay(10));
		workflow.getMinimalSink().setDelay(500);

		TaskStatusBuiltinStreamConsumer con = new TaskStatusBuiltinStreamConsumer();
		StreamMetadataBuiltinStreamConsumer con2 = new StreamMetadataBuiltinStreamConsumer() {
			public static final String OUTPUT_STREAM_ID = "OutputPost.MinimalSink.testStreamMetadataTopicWorkflow";

			@Override
			public void consume(StreamMetadataSnapshot t) {

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
					if(!workflow.isTerminating()) // during termination the number of subscribers is inconsistent so ignore it
						failures.add(new AbstractMap.SimpleEntry<>(streamSize, statusBasedSize));
				}
			}
		};

		workflow.getTaskStatusTopic().addConsumer(con);
		//
		workflow.getStreamMetadataTopic().addConsumer(con2);
		//
		workflow.run();
		//
		waitFor(workflow);

		System.out.println(workflow.getMinimalSink().getNumbers());

		System.out
				.println("need: " + con2.failures.size() + " < " + con2.nonZeroMatches + " maxQ: " + con2.maxQueueSize);

		assertTrue(con2.failures.size() == 0 || con2.failures.size() + 1 < con2.nonZeroMatches);

		assertTrue(con2.updated);

		assertTrue(con2.maxQueueSize > 0);

	}

	@Test
	public void parallelTestStreamMetadataTopicMultiConsumer() throws Exception {
		List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

		MinimalWorkflow workflow = new MinimalWorkflow(Mode.MASTER, parallelization);
		workflow.setName("wf");
		workflow.createBroker(createBroker);

		workflow.setInstanceId("testStreamMetadataTopicWorkflowMC");

		workflow.setStreamMetadataPeriod(500);
		workflow.getMinimalSource().setNumbers(numbers);
		workflow.getMinimalSink().setDelay(100);

		StreamMetadataBuiltinStreamConsumer con = new StreamMetadataBuiltinStreamConsumer() {
			public static final String INPUT_STREAM_ID = "InputPost.CopierTask.testStreamMetadataTopicWorkflowMC";

			@Override
			public void consume(StreamMetadataSnapshot t) {

				// t.pruneNames(INPUT_STREAM_ID.length());
				// System.out.println(t);

				long subs = -1;
				try {
					StreamMetadata stream = t.getStream(INPUT_STREAM_ID);
					subs = stream.getNumberOfSubscribers();
					System.out.println(subs + " == " + (1 + workflow.getParallelization()));
					assertEquals(subs, 1 + workflow.getParallelization());
				} catch (Throwable e) {
					if(!workflow.isTerminating()) // during termination the number of subscribers is inconsistent so ignore it
						failures.add(new AbstractMap.SimpleEntry<>(subs, ((long) 2)));
				}
			}
		};
		workflow.getStreamMetadataTopic().addConsumer(con);
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
