package org.eclipse.scava.crossflow.examples.firstcommitment.ghrepo.forked;

import java.util.EnumSet;

import org.eclipse.scava.crossflow.runtime.Mode;

public class GhRepoExampleMasterWorkers {

	private static final int START_WAITING_TIME = 5000;
	private static final int COMPLETION_WAITING_TIME = 10000;

	public static void main(String[] args) throws Exception {

		GhRepoExample master = new GhRepoExample();
		master.setName("Master");

		// example using a second master connected to original broker
		// workflow (will produce 2 times the input, as expected)
		GhRepoExample master2 = null;
		if (args.length > 0) {
			master2 = new GhRepoExample(Mode.MASTER_BARE);
			master2.createBroker(Boolean.parseBoolean(args[0]));
			master2.setName("Master2");
		}

		GhRepoExample worker1 = new GhRepoExample(Mode.WORKER);
		EnumSet<GhRepoExampleTasks> tasksToExclude = EnumSet.noneOf(GhRepoExampleTasks.class);
		//tasksToExclude.add(GhRepoExampleTasks.GH_REPO_COUNTER2);
		worker1.excludeTasks(tasksToExclude);
		worker1.setName("Worker1");

		GhRepoExample worker2 = new GhRepoExample(Mode.WORKER);
		// worker2.excludeTasks(tasksToExclude);
		worker2.setName("Worker2");

		Thread.sleep(START_WAITING_TIME); // to allow connecting from eclipse

		master.run();
		worker1.run();
		worker2.run();
		if (args.length > 0)
			master2.run();

		Thread.sleep(COMPLETION_WAITING_TIME); // to allow printout after run is completed

		System.out.println("\nPRINTING EXECUTION STATISTICS ...\n");

		printStatistics(master);
		printStatistics(worker1);
		printStatistics(worker2);

		System.out.println("... COMPLETED !");

		System.exit(0);

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
			// XXX this will crash for workers that dont have this task, temporary solution
			// e.printStackTrace();
		}
	}

}
