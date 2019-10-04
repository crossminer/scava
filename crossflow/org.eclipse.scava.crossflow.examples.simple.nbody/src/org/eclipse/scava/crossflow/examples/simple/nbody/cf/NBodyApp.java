/*********************************************************************
* Copyright (c) 2019 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* Contributors:
*     Horacio Hoyos - initial API and implementation
**********************************************************************/
package org.eclipse.scava.crossflow.examples.simple.nbody.cf;

import java.io.File;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.eclipse.scava.crossflow.runtime.Mode;
import org.springframework.core.task.TaskTimeoutException;

/**
 * The Class NBodyApp.
 */
public class NBodyApp {

	public static void main(String[] args) throws Exception {

		// Mode -> args[0]
		Mode mode = Mode.valueOf(args[0]);
		NBody runner = new NBody(mode);
		runner.setMaster(args[1]);
		runner.setInstanceId("Example NBody");
		if (Mode.MASTER_BARE.equals(mode)) {
			runner.createBroker(true);
			runner.setEnableTermination(false);
			// master.setParallelization(4);
			runner.setInputDirectory(new File("resources/in"));
			runner.setOutputDirectory(new File("resources/out"));
			runner.setName("NBody");
			System.out.println("Running");
			runner.run(5000L);
			runner.awaitTermination();
			System.out.println("Done");
		} else {
			EnumSet<NBodyTasks> workers = EnumSet.of(NBodyTasks.SIMULATION, NBodyTasks.STEP);
			String taskType = args[2];
			workers.remove(NBodyTasks.valueOf(taskType));
			runner.excludeTasks(workers);
			switch (taskType) {
			case "Simulation": {
				runner.setName(String.format("Simulation_%s", UUID.randomUUID()));
				break;
			}
			case "Step": {
				runner.setName(String.format("Step_%s", UUID.randomUUID()));
				break;
			}
			default: {
				System.err.println("Wrong task type selected");
				System.exit(-1);
			}
			}
			System.out.println("Running");
			runner.run();
			runner.awaitTermination();
			System.out.println("Done");
		}
	}

}
