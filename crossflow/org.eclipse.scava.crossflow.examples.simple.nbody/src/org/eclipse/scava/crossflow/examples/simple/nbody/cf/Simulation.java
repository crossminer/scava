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

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.eclipse.scava.crossflow.examples.simple.nbody.AxisAlignedCuboid;
import org.eclipse.scava.crossflow.examples.simple.nbody.NBody3DBody;
import org.eclipse.scava.crossflow.examples.simple.nbody.NBodyCuboid;
import org.eclipse.scava.crossflow.examples.simple.nbody.NBodyCuboid.CuboidCoordinates;
import org.eclipse.scava.crossflow.examples.simple.nbody.NBodyMetrics.RequestedDurationNotFound;
import org.eclipse.scava.crossflow.examples.simple.nbody.NBodySimulation.InvalidNumberOfCubesException;
import org.eclipse.scava.crossflow.examples.simple.nbody.StockCuboidSimulationDurations;
import org.eclipse.scava.crossflow.examples.simple.nbody.threads.CuboidRunner;
import org.eclipse.scava.crossflow.examples.simple.nbody.threads.CuboidRunnerResults;
import org.eclipse.scava.crossflow.examples.simple.nbody.threads.StockCuboids;
import org.jctools.queues.MpscArrayQueue;
import org.jctools.queues.SpmcArrayQueue;

/**
 * A simulation job is responsible for executing one step of a simulation for a given cuboid.
 * Further, the step can specify that the simulation job should take advantage of multiple cores
 * (if present), i.e. run the step in several threads.
 * @author Horacio Hoyos Rodriguez
 *
 */
public class Simulation extends OpinionatedSimulationBase {

	private Duration prprDrtn;
	private Duration calcAccelDrtn;
	private Duration calcVelDrtn;
	private Duration calcPosDrtn;
	private long memSize = 0;
	private SpmcArrayQueue<CuboidCoordinates> sharedQueue;
	private int maxCuboids = -1;
	private int maxRunners = -1;
	private ExecutorService runnerExecutor;
	private MpscArrayQueue<CuboidRunnerResults> resultsQueue;
	
	@Override
	public StepResults consumeUniverse(StepData stepData) throws Exception {
		
		if (stepData.getThreaded()) {
			return runWithTreahds(stepData);
		}
		else {
			System.out.println("Simultaion single-thread running");
			NBodyCuboid c = new AxisAlignedCuboid(
					stepData.getCoordiantes(),
					stepData.getVelDmpFactor(),
					stepData.getTimeDelta());
			// System.out.println("Simulation consumeUniverse");
			Collection<NBody3DBody> bodies = c.stepSimulation(stepData.universe);
			return new StepResults(
					c.coordinates(),
					c.durations(),
					bodies,
					c.memSize());
		}
	
	}

	@Override
	public boolean acceptInput(StepData input) {
		// TODO: logic for when to accept tasks for this instance of Simulation goes here.
		return true;
	}
	
	// TODO maxCuboids should be configurable so users can find a good value
	private StepResults runWithTreahds(StepData stepData) throws InvalidNumberOfCubesException {
		if (maxCuboids == -1) {
			maxCuboids = Runtime.getRuntime().availableProcessors();
			maxRunners = maxCuboids * 2;
			sharedQueue = new SpmcArrayQueue<NBodyCuboid.CuboidCoordinates>(maxCuboids);
			resultsQueue = new MpscArrayQueue<CuboidRunnerResults>(maxCuboids);
			runnerExecutor = Executors.newFixedThreadPool(maxRunners);
		}
		prprDrtn = Duration.ZERO;
		calcAccelDrtn = Duration.ZERO;
		calcVelDrtn = Duration.ZERO;
		calcPosDrtn = Duration.ZERO;
		memSize = 0;
		// x in b=2^x, where b is the next power of 2 greater than a
		int numCuboids = (int) Math.pow(2, 32 - Integer.numberOfLeadingZeros(maxCuboids - 1));
		Collection<CuboidCoordinates> stepCuboids = new StockCuboids(stepData.getCoordiantes()).setupCuboids(numCuboids);
		List<NBody3DBody> newUniverse = new ArrayList<>(stepData.getUniverse().size());
		while (!stepCuboids.isEmpty()) {
			sharedQueue.addAll(stepCuboids);
			List<CuboidRunner> runners = new ArrayList<CuboidRunner>();
			for (int r = 0; r < numCuboids; r++) {
				CuboidRunner runner = new CuboidRunner(0.995, 0.001, sharedQueue, resultsQueue, stepData.getUniverse());
				runners.add(runner);
			}

			List<Future<UUID>> futures = null;
			try {
				futures = runnerExecutor.invokeAll(runners);
			} catch (InterruptedException e1) {
				System.err.println("ex " + e1.getMessage());
			} finally {
				if (futures != null) {
					resultsQueue.drain(r -> {
//						boolean found = checkIds(futures, r);
//						if (!found) {
//							throw new IllegalStateException("Finished thread did not queue results");
//						}
						try {
							prprDrtn = prprDrtn.plus(r.durations().prepareDrtn());
							calcAccelDrtn = calcAccelDrtn.plus(r.durations().calcAccelDrtn());
							calcVelDrtn = calcVelDrtn.plus(r.durations().calcVelDrtn());
							calcPosDrtn = calcPosDrtn.plus(r.durations().calcPosDrtn());
							// Only count if durations are OK
							stepCuboids.remove(r.coordiantes());
							newUniverse.addAll(r.bodies());
							memSize += r.memUsed();
						} catch (RequestedDurationNotFound e) {
							// Retry
							System.err.println("ex " + e.getMessage());
						}
					});
				}
			}
		}
		System.out.println("Simultaion multi-thread finished " + LocalDateTime.now());
		return new StepResults(
				stepData.getCoordiantes(),
				new StockCuboidSimulationDurations(
						prprDrtn.toNanos(),
						calcAccelDrtn.toNanos(),
						calcVelDrtn.toNanos(),
						calcPosDrtn.toNanos()),
				newUniverse,
				memSize);
	}

}
