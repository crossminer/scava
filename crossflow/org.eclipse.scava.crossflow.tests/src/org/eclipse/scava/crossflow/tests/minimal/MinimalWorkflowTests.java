package org.eclipse.scava.crossflow.tests.minimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

import javax.management.MBeanServerConnection;
import javax.management.MBeanServerInvocationHandler;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import org.apache.activemq.broker.jmx.DestinationViewMBean;
import org.apache.activemq.command.ActiveMQDestination;
import org.eclipse.scava.crossflow.runtime.BuiltinStreamConsumer;
import org.eclipse.scava.crossflow.runtime.DirectoryCache;
import org.eclipse.scava.crossflow.runtime.Mode;
import org.eclipse.scava.crossflow.runtime.utils.CrossflowLogger.SEVERITY;
import org.eclipse.scava.crossflow.runtime.utils.Result;
import org.eclipse.scava.crossflow.runtime.utils.StreamMetadata;
import org.eclipse.scava.crossflow.runtime.utils.StreamMetadataSnapshot;
import org.eclipse.scava.crossflow.runtime.utils.TaskStatus;
import org.eclipse.scava.crossflow.runtime.utils.TaskStatus.TaskStatuses;
import org.eclipse.scava.crossflow.tests.WorkflowTests;
import org.junit.Test;

public class MinimalWorkflowTests extends WorkflowTests {

	@Test
	public void testCache() throws Exception {

		List<Integer> numbers = Arrays.asList(1, 1);

		MinimalWorkflow workflow = new MinimalWorkflow();
		workflow.createBroker(createBroker);
		workflow.getMinimalSource().setNumbers(numbers);
		DirectoryCache cache = new DirectoryCache();
		workflow.setCache(cache);
		workflow.run();

		waitFor(workflow);

		assertEquals(2, workflow.getMinimalSink().getNumbers().size());
		assertEquals(2, workflow.getCopierTask().getExecutions());

		workflow = new MinimalWorkflow();
		workflow.createBroker(createBroker);
		workflow.getMinimalSource().setNumbers(numbers);
		workflow.setCache(new DirectoryCache(cache.getDirectory()));
		workflow.run();

		waitFor(workflow);

		assertEquals(2, workflow.getMinimalSink().getNumbers().size());
		assertEquals(0, workflow.getCopierTask().getExecutions());
	}

	//
	private abstract class InternalQueueMonitor extends TimerTask {
		Map<String, Long> queueSizes = new HashMap<String, Long>();
		boolean queueSizesActive = false;
		Map<String, Long> queuesInFlight = new HashMap<String, Long>();
		boolean queuesInFlightActive = false;

	};

	@Test
	public void testInternalQueues() throws Exception {

		MinimalWorkflow workflow = new MinimalWorkflow();
		workflow.createBroker(createBroker);

		InternalQueueMonitor monitor = new InternalQueueMonitor() {

			@Override
			public void run() {

				for (ActiveMQDestination c : workflow.getAllJobStreamsInternals()) {
					try {
						// System.out.println(s);

						String url = "service:jmx:rmi:///jndi/rmi://" + workflow.getMaster() + ":1099/jmxrmi";
						JMXConnector connector = JMXConnectorFactory.connect(new JMXServiceURL(url));
						MBeanServerConnection connection = connector.getMBeanServerConnection();

						ObjectName destination = new ObjectName("org.apache.activemq:type=Broker,brokerName="
								+ workflow.getMaster() + ",destinationType=" + (c.isQueue() ? "Queue" : "Topic")
								+ ",destinationName=" + c.getPhysicalName());

						DestinationViewMBean mbView = MBeanServerInvocationHandler.newProxyInstance(connection,
								destination, DestinationViewMBean.class, true);

//							System.err.println(destinationName + ":" 
//									+ destinationType + " " 
//									+ mbView.getQueueSize() + " "
//									+ mbView.getInFlightCount());

						try {
							long qSize = mbView.getQueueSize();
							queueSizes.put(c.toString(), qSize);
							if (qSize > 0)
								queueSizesActive = true;
							long qIFC = mbView.getInFlightCount();
							queuesInFlight.put(c.toString(), qIFC);
							if (qIFC > 0)
								queuesInFlightActive = true;
						} catch (Exception ex) {
							// Ignore exception as we will be getting these during workflow termination as
							// we keep trying to get queue info on queues that will be shutting down
						}

						connector.close();

						// streamSizes.put(s.getName(), s.getSize());
						// streamsInFlight.put(s.getName(), s.getInFlight());

					} catch (Exception e) {
						// Ignore exception as we will be getting these during workflow termination as
						// we keep trying to get queue info on queues that will be shutting down
					}
				}
				//
				// System.out.println(queueSizes + "\r\n" + queuesInFlight);
				// System.out.println("" +
				// queueSizes.entrySet().stream().collect(Collectors.summingLong(e ->
				// e.getValue())));
				// System.out.println(queueSizesActive);
				// System.out.println("" +
				// queuesInFlight.entrySet().stream().collect(Collectors.summingLong(e ->
				// e.getValue())));
				// System.out.println(queuesInFlightActive);
				//
			}
		};

		List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

		workflow.setInstanceId("testInternalQueues");
		workflow.getMinimalSource().setNumbers(numbers);
		workflow.getCopierTask().setDelay(100);
		workflow.getMinimalSink().setDelay(200);
		// workflow.setStreamMetadataPeriod(10);

		workflow.run();

		long delay = 5;
		Timer timer = new Timer();
		timer.schedule(monitor, 0, delay);

		waitFor(workflow);

		assertEquals(10, workflow.getMinimalSink().getNumbers().size());
		assertTrue(monitor.queueSizesActive && monitor.queuesInFlightActive);
		assertEquals(0L,
				monitor.queueSizes.entrySet().stream().collect(Collectors.summingLong(e -> e.getValue())).longValue());
		assertEquals(0L, monitor.queuesInFlight.entrySet().stream().collect(Collectors.summingLong(e -> e.getValue()))
				.longValue());
	}

	@Test
	public void testResultsTopic() throws Exception {
		testResultsTopicActual(0);
		// ensures result topic is accurate with an initial delay on master
		testResultsTopicActual(4000);
	}

	public void testResultsTopicActual(int initialDelay) throws Exception {
		List<Integer> numbers = Arrays.asList(1, 2);
		MinimalWorkflow workflow = new MinimalWorkflow();
		workflow.createBroker(createBroker);
		List<Integer> results = new LinkedList<Integer>();
		workflow.getMinimalSource().setNumbers(numbers);
		//
		workflow.getMinimalSource().setSendToResults(true);
		workflow.getResultsTopic().addConsumer(new BuiltinStreamConsumer<Result>() {
			@Override
			public void consume(Result t) {
				results.add((Integer) t.get(0));
			}
		});
		//
		workflow.run(initialDelay);
		//
		waitFor(workflow);
		assertEquals(numbers, results);
	}

	private class TaskStatusBuiltinStreamConsumer implements BuiltinStreamConsumer<TaskStatus> {
		public int outputQueueSize = 0;

		@Override
		public void consume(TaskStatus t) {
			// System.out.println(t);
			if (t.getCaller().equals("CopierTask:MinimalWorkflow") && t.getStatus().equals(TaskStatuses.WAITING))
				outputQueueSize++;
			if (t.getCaller().equals("MinimalSink:MinimalWorkflow") && t.getStatus().equals(TaskStatuses.WAITING))
				outputQueueSize--;
			//
		}
	}

	private abstract class StreamMetadataBuiltinStreamConsumer implements BuiltinStreamConsumer<StreamMetadataSnapshot> {
		public List<Map.Entry<Long, Long>> failures = new ArrayList<Map.Entry<Long, Long>>();
		public boolean updated = false;
		public long maxQueueSize = 0;
		public int nonZeroMatches = 0;
	};

	@Test
	public void testStreamMetadataTopic() throws Exception {
		testStreamMetadataTopicActual(false);
		// ensures metadata checks are valid with prefetching enabled for queues
		testStreamMetadataTopicActual(true);
	}

	public synchronized void testStreamMetadataTopicActual(boolean enablePrefetch) throws Exception {

		MinimalWorkflow workflow = new MinimalWorkflow();
		workflow.createBroker(createBroker);
		//
		workflow.setInstanceId("testStreamMetadataTopicWorkflow");
		//
		workflow.setEnablePrefetch(enablePrefetch);
		//
		List<Integer> numbers = new LinkedList<Integer>();
		for (int i = 1; i <= 10; i++)
			numbers.add(i);
		//
		workflow.setStreamMetadataPeriod(500);
		workflow.getMinimalSource().setNumbers(numbers);
		workflow.getCopierTask().setDelay(10);
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
						// ?
						// logger.log(Level.INFO, "stream: " + OUTPUT_STREAM_ID + " not found!");
						// logger.log(this.getClass().getName(), "testStreamMetadataTopicActual",
						// Level.INFO, "stream: " + OUTPUT_STREAM_ID + " not found!");
						workflow.log(SEVERITY.INFO, "stream: " + OUTPUT_STREAM_ID + " not found!");
					}
					statusBasedSize = (long) con.outputQueueSize;

					//
					workflow.log(SEVERITY.INFO, streamSize + " (" + streamInFlight + ") | " + statusBasedSize);
					if (!updated && streamSize < statusBasedSize) {
						// mbean has not yet updated
						workflow.log(SEVERITY.INFO, "mbean not up to date yet");
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

		workflow.getTaskStatusTopic().addConsumer(con);
		//
		workflow.getStreamMetadataTopic().addConsumer(con2);
		//
		workflow.run();
		//
		waitFor(workflow);

		workflow.log(SEVERITY.INFO, "" + workflow.getMinimalSink().getNumbers());

		workflow.log(SEVERITY.INFO,
				"need: " + con2.failures.size() + " < " + con2.nonZeroMatches + " maxQ: " + con2.maxQueueSize);

		assertTrue(con2.failures.size() == 0 || con2.failures.size() + 1 < con2.nonZeroMatches);

		assertTrue(con2.updated);

		assertTrue(con2.maxQueueSize > 0);
	}

	@Test
	public void testStreamMetadataTopicMultiConsumer() throws Exception {
		List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
		MinimalWorkflow workflow = new MinimalWorkflow();
		//
		workflow.createBroker(createBroker);
		workflow.setInstanceId("testStreamMetadataTopicWorkflowMC");
		workflow.setName("master");

		workflow.setStreamMetadataPeriod(500);
		workflow.getMinimalSource().setNumbers(numbers);
		workflow.getMinimalSink().setDelay(100);

		workflow.getCopierTask().setVerbose(true);

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
					workflow.log(SEVERITY.INFO, subs + " == " + "2");
					assertEquals(t.getStream(INPUT_STREAM_ID).getNumberOfSubscribers(), 2);
				} catch (Throwable e) {
					if (failures == null)
						failures = new ArrayList<Map.Entry<Long, Long>>();
					failures.add(new AbstractMap.SimpleEntry<Long, Long>(subs, ((long) 2)));
				}
			}
		};
		workflow.getStreamMetadataTopic().addConsumer(con);
		//

		MinimalWorkflow workflow2 = new MinimalWorkflow(Mode.WORKER);
		workflow2.setInstanceId("testStreamMetadataTopicWorkflowMC");
		workflow2.setMaster("localhost");
		workflow2.setName("worker");

		workflow.run();
		workflow2.run();

		//

		waitFor(workflow);

		assertEquals(0, con.failures.size());
	}

}
