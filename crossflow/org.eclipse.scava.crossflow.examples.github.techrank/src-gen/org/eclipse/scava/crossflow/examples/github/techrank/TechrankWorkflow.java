package org.eclipse.scava.crossflow.examples.github.techrank;

import java.util.LinkedList;
import java.util.Collection;

import java.util.Timer;
import java.util.TimerTask;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

import org.apache.activemq.broker.BrokerService;
import org.eclipse.scava.crossflow.runtime.Workflow;
import org.eclipse.scava.crossflow.runtime.Mode;

public class TechrankWorkflow extends Workflow {
	
	public static TechrankWorkflow run(String[] args) throws Exception {
		TechrankWorkflow throwAway = new TechrankWorkflow();
		new JCommander(throwAway, args);
		TechrankWorkflow app = new TechrankWorkflow(throwAway.getMode());
		new JCommander(app, args);
		app.run();
		return app;
	}
	
	public static void main(String[] args) throws Exception {
		run(args);
	}
	
	
	public TechrankWorkflow createWorker() {
		TechrankWorkflow worker = new TechrankWorkflow(Mode.WORKER);
		worker.setInstanceId(instanceId);
		return worker;
	}
	
	
	// streams
	protected Technologies technologies;
	protected Repositories repositories;
	protected RepositorySearches repositorySearches;
	protected RepositorySearchResults repositorySearchResults;
	
	// tasks
	protected TechnologySource technologySource;
	protected GitHubCodeSearcher gitHubCodeSearcher;
	protected RepositorySearchDispatcher repositorySearchDispatcher;
	protected RepositorySearcher repositorySearcher;
	protected ResultsSink resultsSink;
	
	public TechrankWorkflow() {
		this(Mode.MASTER);
	}
	
	public TechrankWorkflow(Mode mode) {
		super();
		this.name = "TechrankWorkflow";
		this.mode = mode;
		if (isMaster()) {
		technologySource = new TechnologySource();
		technologySource.setWorkflow(this);
		repositorySearchDispatcher = new RepositorySearchDispatcher();
		repositorySearchDispatcher.setWorkflow(this);
		resultsSink = new ResultsSink();
		resultsSink.setWorkflow(this);
		}
		
		if (isWorker()) {
			if (!tasksToExclude.contains("GitHubCodeSearcher")) {
				gitHubCodeSearcher = new GitHubCodeSearcher();
				gitHubCodeSearcher.setWorkflow(this);
			}
			if (!tasksToExclude.contains("RepositorySearcher")) {
				repositorySearcher = new RepositorySearcher();
				repositorySearcher.setWorkflow(this);
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
	public void run(int delay) throws Exception {
	
		new Thread(new Runnable() {

			@Override
			public void run() {

				try {
					
					if (isMaster()) {
						if (createBroker) {
							brokerService = new BrokerService();
							brokerService.setUseJmx(true);
							brokerService.addConnector(getBroker());
							brokerService.start();
						}
					}

					connect();

					Thread.sleep(delay);
					
					technologies = new Technologies(TechrankWorkflow.this);
					activeChannels.add(technologies);
					repositories = new Repositories(TechrankWorkflow.this);
					activeChannels.add(repositories);
					repositorySearches = new RepositorySearches(TechrankWorkflow.this);
					activeChannels.add(repositorySearches);
					repositorySearchResults = new RepositorySearchResults(TechrankWorkflow.this);
					activeChannels.add(repositorySearchResults);
					
					if (isMaster()) {
							technologySource.setResultsTopic(resultsTopic);
							technologySource.setTechnologies(technologies);
							repositorySearchDispatcher.setResultsTopic(resultsTopic);
							repositories.addConsumer(repositorySearchDispatcher, "RepositorySearchDispatcher");			
							repositorySearchDispatcher.setRepositorySearches(repositorySearches);
							resultsSink.setResultsTopic(resultsTopic);
							repositorySearchResults.addConsumer(resultsSink, "ResultsSink");			
					}
					
					if (isWorker()) {
						if (!tasksToExclude.contains("GitHubCodeSearcher")) {
								gitHubCodeSearcher.setResultsTopic(resultsTopic);
								technologies.addConsumer(gitHubCodeSearcher, "GitHubCodeSearcher");			
								gitHubCodeSearcher.setRepositories(repositories);
						}
						if (!tasksToExclude.contains("RepositorySearcher")) {
								repositorySearcher.setResultsTopic(resultsTopic);
								repositorySearches.addConsumer(repositorySearcher, "RepositorySearcher");			
								repositorySearcher.setRepositorySearchResults(repositorySearchResults);
						}
					}
					
					if (isMaster()){
						new Timer().schedule(new TimerTask() {
						
							@Override
							public void run() {
								try {
									setTaskInProgess(technologySource);
									technologySource.produce();
									setTaskWaiting(technologySource);
								}
								catch (Exception ex) {
									reportInternalException(ex);
									terminate();
								}
							}
						}, 0);
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		
		}).start();
	
	}				
	
	public Technologies getTechnologies() {
		return technologies;
	}
	public Repositories getRepositories() {
		return repositories;
	}
	public RepositorySearches getRepositorySearches() {
		return repositorySearches;
	}
	public RepositorySearchResults getRepositorySearchResults() {
		return repositorySearchResults;
	}
	
	public TechnologySource getTechnologySource() {
		return technologySource;
	}
	public GitHubCodeSearcher getGitHubCodeSearcher() {
		return gitHubCodeSearcher;
	}
	public RepositorySearchDispatcher getRepositorySearchDispatcher() {
		return repositorySearchDispatcher;
	}
	public RepositorySearcher getRepositorySearcher() {
		return repositorySearcher;
	}
	public ResultsSink getResultsSink() {
		return resultsSink;
	}

}

