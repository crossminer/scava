package org.eclipse.scava.crossflow.examples.firstcommitment.ghrepo;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.scava.crossflow.runtime.Mode;

public class GhRepoExampleMasterWorkers {

	private static final int START_WAITING_TIME = 5000;

	public static void main(String[] args) throws Exception {

		GhRepoExample master = new GhRepoExample();
		// default mode is MASTER
		master.setMode(Mode.MASTER_BARE);
		master.setName("Master" + "-123456789");
		master.setEnableCache(false);
		master.run(START_WAITING_TIME);

		// example using a second master connected to original broker
		// workflow (will produce 2 times the input, as expected)
		GhRepoExample master2 = null;
		if (args.length > 0) {
			master2 = new GhRepoExample();
			// default mode is MASTER
			master2.setMode(Mode.MASTER_BARE);
			master2.createBroker(Boolean.parseBoolean(args[0]));
			master2.setName("Master2");
		}

		GhRepoExample worker1 = new GhRepoExample();
		List<String> tasksToExclude = new LinkedList<String>();
		tasksToExclude = Collections.singletonList("GhRepoCounter");
		worker1.excludeTasks(tasksToExclude);
		worker1.setMode(Mode.WORKER);
		worker1.setName("Worker1");
		worker1.run();

		GhRepoExample worker2 = new GhRepoExample();
		// worker2.excludeTasks(tasksToExclude);
		worker2.setMode(Mode.WORKER);
		worker2.setName("Worker2");
		worker2.run();

		if (args.length > 0)
			master2.run();

		master.onTerminate(new Runnable() {
			
			@Override
			public void run() {

				try {
					worker1.manualTermination();
					worker2.manualTermination();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				System.out.println("\nPRINTING EXECUTION STATISTICS ...\n");

				printStatistics(master);
				printStatistics(worker1);
				printStatistics(worker2);

				System.out.println("... COMPLETED !");

				// System.exit(0);

			}
		});
		
		
		
		
		
		//Thread.sleep(10000);
		//master.manualTermination();
	}

	private static void printStatistics(GhRepoExample ghRepoExample) {
		try {
			System.out.println("[" + ghRepoExample.getName() + "] total # of jobs seen: "
					+ ghRepoExample.getGhRepoCounter().getAlreadySeenJobs().size());
			System.out.println("[" + ghRepoExample.getName() + "] max # of job commitments: "
					+ String.valueOf(ghRepoExample.getGhRepoCounter().MAX_NUMBER_OF_COMMITMENTS));
			System.out.println(
					"[" + ghRepoExample.getName() + "] total # of jobs committed to ( total # of occurrences ): "
							+ ghRepoExample.getGhRepoCounter().getCommittedRepoMap().size() + " ( " + ghRepoExample
									.getGhRepoCounter().getCommittedRepoMap().values().stream().mapToInt(i -> i).sum()
							+ " )\n");
		} catch (Exception e) {
			// XXX this will crash for workers that dont have this task,
			// temporary solution
			// e.printStackTrace();
		}
	}

}
