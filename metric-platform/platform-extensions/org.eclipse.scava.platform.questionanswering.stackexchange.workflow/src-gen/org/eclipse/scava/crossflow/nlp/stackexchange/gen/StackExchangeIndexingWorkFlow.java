package org.eclipse.scava.crossflow.nlp.stackexchange.gen;

import java.util.List;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import org.apache.activemq.broker.BrokerFactory;
import org.apache.activemq.broker.BrokerService;
import org.eclipse.scava.crossflow.runtime.utils.ParallelTaskList;
import org.eclipse.scava.crossflow.runtime.utils.ControlSignal;
import org.eclipse.scava.crossflow.runtime.utils.ControlSignal.ControlSignals;
import org.eclipse.scava.crossflow.runtime.utils.CrossflowLogger.SEVERITY;
import org.eclipse.scava.crossflow.runtime.Workflow;
import org.eclipse.scava.crossflow.runtime.Mode;
import org.eclipse.scava.crossflow.nlp.stackexchange.CodeDetection;
import org.eclipse.scava.crossflow.nlp.stackexchange.StackExchangeIndexing;
import org.eclipse.scava.crossflow.nlp.stackexchange.PlainText;
import org.eclipse.scava.crossflow.nlp.stackexchange.SourceStackExchangeReader;
import org.eclipse.scava.crossflow.runtime.BuiltinStream;

public class StackExchangeIndexingWorkFlow extends Workflow {

	public static StackExchangeIndexingWorkFlow run(String[] args) throws Exception {
		StackExchangeIndexingWorkFlow throwAway = new StackExchangeIndexingWorkFlow();
		new JCommander(throwAway, args);
		StackExchangeIndexingWorkFlow app = new StackExchangeIndexingWorkFlow(throwAway.getMode(),throwAway.getParallelization());
		new JCommander(app, args);
		app.run();
		return app;
	}
	
	public static void main(String[] args) throws Exception {
		run(args);
	}
	
	
	public StackExchangeIndexingWorkFlow createWorker() {
		StackExchangeIndexingWorkFlow worker = new StackExchangeIndexingWorkFlow(Mode.WORKER,parallelization);
		worker.setInstanceId(instanceId);
		return worker;
	}
	
	
	// streams
	protected PlainTextQueue plainTextQueue;
	protected CodeDetectorQueue codeDetectorQueue;
	protected IndexingQueue indexingQueue;
	
	// tasks

	protected SourceStackExchangeReader stackExchangeReader;
	protected StackExchangeIndexing indexer;

	protected ParallelTaskList<PlainText> plainTexts = new ParallelTaskList<>();
	protected ParallelTaskList<CodeDetection> codeDetectors = new ParallelTaskList<>();

	//

	public StackExchangeIndexingWorkFlow() {
		this(Mode.MASTER, 1);
	}
	
	public StackExchangeIndexingWorkFlow(Mode m) {
		this(m, 1);
	}
	
	public StackExchangeIndexingWorkFlow(Mode mode, int parallelization) {
		super();
			
		this.parallelization = parallelization;	
			
		this.name = "StackExchangeIndexingWorkFlow";
		this.mode = mode;
		
		if (isMaster()) {
			stackExchangeReader = new SourceStackExchangeReader();
			stackExchangeReader.setWorkflow(this);
			tasks.add(stackExchangeReader);
			indexer = new StackExchangeIndexing();
			indexer.setWorkflow(this);
			tasks.add(indexer);
		}
		
		if (isWorker()) {
			if (!tasksToExclude.contains("PlainText")) {
				for(int i=1;i<=parallelization;i++){
					PlainText task = new PlainText();
					task.setWorkflow(this);
					tasks.add(task);
					plainTexts.add(task);
				}
			}
			if (!tasksToExclude.contains("CodeDetector")) {
				for(int i=1;i<=parallelization;i++){
					CodeDetection task = new CodeDetection();
					task.setWorkflow(this);
					tasks.add(task);
					codeDetectors.add(task);
				}
			}
		}

	}
	
	/**
	 * Run with initial delay in ms before starting execution (after creating broker
	 * if master)
	 * 
	 * @param delay
	 */
	@Override
	public void run(long delay) throws Exception {
	
	plainTexts.init(this);
	codeDetectors.init(this);
	
	this.delay=delay;

	try {
					
		if (isMaster()) {
			if (createBroker) {
				if (activeMqConfig != null && activeMqConfig != "") {
					brokerService = BrokerFactory.createBroker(new URI("xbean:" + activeMqConfig));
				} else {
					brokerService = new BrokerService();
				}
			
				//activeMqConfig
				brokerService.setUseJmx(true);
				brokerService.addConnector(getBroker());
				if(enableStomp)
					brokerService.addConnector(getStompBroker());
				if(enableWS)	
					brokerService.addConnector(getWSBroker());
				brokerService.start();
			}
		}

		connect();

		Thread.sleep(delay);
		
		plainTextQueue = new PlainTextQueue(StackExchangeIndexingWorkFlow.this, enablePrefetch);
		activeStreams.add(plainTextQueue);
		codeDetectorQueue = new CodeDetectorQueue(StackExchangeIndexingWorkFlow.this, enablePrefetch);
		activeStreams.add(codeDetectorQueue);
		indexingQueue = new IndexingQueue(StackExchangeIndexingWorkFlow.this, enablePrefetch);
		activeStreams.add(indexingQueue);
		
			if (isMaster()) {
					stackExchangeReader.setResultsTopic(resultsTopic);
					stackExchangeReader.setPlainTextQueue(plainTextQueue);
					indexer.setResultsTopic(resultsTopic);
					indexingQueue.addConsumer(indexer, "Indexer");			
			}
			
			if (isWorker()) {
				if (!tasksToExclude.contains("PlainText")) {
						for(int i = 1; i <=plainTexts.size(); i++){
							PlainText task = plainTexts.get(i-1);
							task.setResultsTopic(resultsTopic);
							plainTextQueue.addConsumer(task, "PlainText");			
							task.setCodeDetectorQueue(codeDetectorQueue);
						}
				}
				if (!tasksToExclude.contains("CodeDetector")) {
						for(int i = 1; i <=codeDetectors.size(); i++){
							CodeDetection task = codeDetectors.get(i-1);
							task.setResultsTopic(resultsTopic);
							codeDetectorQueue.addConsumer(task, "CodeDetector");			
							task.setIndexingQueue(indexingQueue);
						}
				}
			}
			
			if (isMaster()){
				// run all sources in parallel threads
				new Thread(() -> {
					try {
						setTaskInProgess(stackExchangeReader);
						stackExchangeReader.produce();
						setTaskWaiting(stackExchangeReader);
					} catch (Exception ex) {
						reportInternalException(ex);
						terminate();
					}
				}).start();	
			}
				
			// delay non-master connections to allow master to create the relevant listeners
			// (in a multi-threaded parallel execution) to facilitate termination, by
			// re-sending worker_added message
			if (!isMaster()) {
				Thread.sleep(1000);
				controlTopic.send(new ControlSignal(ControlSignals.WORKER_ADDED, getName()));
			}	
					
		} catch (Exception e) {
			log(SEVERITY.ERROR, e.getMessage());
		}
	}				
	
	public PlainTextQueue getPlainTextQueue() {
		return plainTextQueue;
	}
	public CodeDetectorQueue getCodeDetectorQueue() {
		return codeDetectorQueue;
	}
	public IndexingQueue getIndexingQueue() {
		return indexingQueue;
	}
	
	public SourceStackExchangeReader getStackExchangeReader() {
		return stackExchangeReader;
	}
	public PlainText getPlainText() {
		if(plainTexts.size()>0)
			return plainTexts.get(0);
		else 
			return null;
	}
	public ParallelTaskList<PlainText> getPlainTexts() {
		return plainTexts;	
	}	
	public CodeDetection getCodeDetector() {
		if(codeDetectors.size()>0)
			return codeDetectors.get(0);
		else 
			return null;
	}
	public ParallelTaskList<CodeDetection> getCodeDetectors() {
		return codeDetectors;	
	}	
	public StackExchangeIndexing getIndexer() {
		return indexer;
	}
	
}	
