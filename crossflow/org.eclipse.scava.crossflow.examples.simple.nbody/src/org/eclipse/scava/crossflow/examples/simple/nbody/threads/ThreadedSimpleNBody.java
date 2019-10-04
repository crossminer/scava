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
package org.eclipse.scava.crossflow.examples.simple.nbody.threads;

import java.nio.file.Path;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.eclipse.scava.crossflow.examples.simple.nbody.Bodies.CreatingBodiesException;
import org.eclipse.scava.crossflow.examples.simple.nbody.JsonBodies;
import org.eclipse.scava.crossflow.examples.simple.nbody.NBody3DBody;
import org.eclipse.scava.crossflow.examples.simple.nbody.NBodyCuboid;
import org.eclipse.scava.crossflow.examples.simple.nbody.NBodyCuboid.CuboidCoordinates;
import org.eclipse.scava.crossflow.examples.simple.nbody.NBodyMetrics.RequestedDurationNotFound;
import org.eclipse.scava.crossflow.examples.simple.nbody.NBodySimulation;
import org.eclipse.scava.crossflow.examples.simple.nbody.RandomBodies;
import org.eclipse.scava.crossflow.examples.simple.nbody.StockCuboidCoordinates;
import org.jctools.queues.MpscArrayQueue;
import org.jctools.queues.SpmcArrayQueue;

/**
 * An implementation of NBodySimulation that runs in separate threads.
 */
public class ThreadedSimpleNBody implements NBodySimulation {

	private List<NBody3DBody> universe;
	private Duration prprDrtn;
	private Duration calcAccelDrtn;
	private Duration calcVelDrtn;
	private Duration calcPosDrtn;
	private Duration overHeadDrtn;
	private double phi;
	private double flops;
	private double bytes;
	private long memSize = 0;

	/** Thread stuff */
	private final SpmcArrayQueue<CuboidCoordinates> sharedQueue;
	private int maxCuboids;
	private int maxRunners;
	private ExecutorService runnerExecutor;
	private final MpscArrayQueue<CuboidRunnerResults> resultsQueue;

	public static void main(String... args) throws Exception {
		NBodySimulation sim = new ThreadedSimpleNBody();
		sim.populateRandomly(Integer.parseInt(args[0]));
		try {
			sim.runSimulation(Integer.parseInt(args[1]));
		}
		catch (Exception e) {
			System.out.println(e);
		}
	}

	public ThreadedSimpleNBody() {
		int cores = Runtime.getRuntime().availableProcessors();
		maxCuboids = cores * 2;
		maxRunners = cores * 12;
		sharedQueue = new SpmcArrayQueue<NBodyCuboid.CuboidCoordinates>(maxCuboids);
		resultsQueue = new MpscArrayQueue<CuboidRunnerResults>(maxCuboids);
		runnerExecutor = Executors.newFixedThreadPool(maxRunners);
	}

	@Override
	public void populateRandomly(int size) {
		universe = new RandomBodies(size).createBodies();
	}

	@Override
	public void populateFromJson(Path data) throws CreatingBodiesException {
		universe = new JsonBodies(data).createBodies();
	}

	@Override
	public void populateRandomly(int size, double pscale, double vscale, double mscale) throws CreatingBodiesException {
		universe = new RandomBodies(size, pscale, vscale, mscale).createBodies();
	}

	@Override
	public void runSimulation(int steps) throws InvalidNumberOfCubesException {
		prprDrtn = Duration.ZERO;
		calcAccelDrtn = Duration.ZERO;
		calcVelDrtn = Duration.ZERO;
		calcPosDrtn = Duration.ZERO;
		//Set<NBody3DBody> newUniverse;
		long start = System.nanoTime();
		// Map<UUID, CuboidCoordinates> takenCoordiantes = new HashMap<>();
		CuboidCoordinates parentCuboid = new StockCuboidCoordinates();
		for (int i = 0; i < steps; i++) {
			// x in b=2^x, where b is the next power of 2 greater than a
			// int numCuboids = (int) Math.pow(2, 32 - Integer.numberOfLeadingZeros(maxCuboids - 1));
			int numCuboids = 4;
			Collection<CuboidCoordinates> stepCuboids = new StockCuboids(parentCuboid).setupCuboids(numCuboids);
			List<NBody3DBody> newUniverse = new ArrayList<>(universe.size());
			while (!stepCuboids.isEmpty()) {

				sharedQueue.addAll(stepCuboids);

				List<CuboidRunner> runners = new ArrayList<CuboidRunner>();
				for (int r = 0; r < numCuboids; r++) {
					CuboidRunner runner = new CuboidRunner(0.995, 0.001, sharedQueue, resultsQueue, universe);
					runners.add(runner);
				}

				List<Future<UUID>> futures = null;
				try {
					futures = runnerExecutor.invokeAll(runners);
				} catch (InterruptedException e1) {
					System.out.println("ex " + e1.getMessage());
				} finally {
					if (futures != null) {
						resultsQueue.drain(r -> {
//							boolean found = checkIds(futures, r);
//							if (!found) {
//								throw new IllegalStateException("Finished thread did not queue results");
//							}
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
								System.out.println("ex " + e.getMessage());
							}
						});
					}
				}
			}
			universe = newUniverse;
		}
		calculatePerformance(universe.size(), steps);
		overHeadDrtn = Duration.ofNanos(System.nanoTime() - start);
		//printResults(steps);
		shutdownAndAwaitTermination(runnerExecutor);
	}
	
	@Override
	public String getMetrics() {
		return String.format("%f,%f,%f,%f,%f,%f,%f,%f",
				prprDrtn.toNanos()/1e9, 
				calcAccelDrtn.toNanos()/1e9, 
				calcVelDrtn.toNanos()/1e9,
				calcPosDrtn.toNanos()/1e9,
				getTotalTime(), flops, bytes, overHeadDrtn.toNanos()/1.0e9);
	}

	@Override
	public double getPhi() {
		return phi;
	}

//	private boolean checkIds(List<Future<UUID>> futures, CuboidRunnerResults r) {
//		// Check future just for sanity?
//		boolean found = false;
//		for (Future<UUID> f : futures) {
//			try {
//				if (r.runnerId().equals(f.get())) {
//					found = true;
//					break;
//				}
//			} catch (InterruptedException | ExecutionException e) {
//				continue;
//			}
//		}
//		return found;
//	}

	private void shutdownAndAwaitTermination(ExecutorService pool) {
		pool.shutdown(); // Disable new tasks from being submitted
		try {
			// Wait a while for existing tasks to terminate
			if (!pool.awaitTermination(60, TimeUnit.SECONDS)) {
				pool.shutdownNow(); // Cancel currently executing tasks
				// Wait a while for tasks to respond to being cancelled
				if (!pool.awaitTermination(60, TimeUnit.SECONDS))
					System.err.println("Pool did not terminate");
			}
		} catch (InterruptedException ie) {
			// (Re-)Cancel if current thread also interrupted
			pool.shutdownNow();
			// Preserve interrupt status
			Thread.currentThread().interrupt();
		}
	}

	private void calculatePerformance(int N, int steps) {
		//flops = (20.0f * (double) N * (double) (N - 1) * (double) steps) / 1000000000.0f / getTotalTime();
		// 20 floating point operations
		// 14 to calculate acceleration
		// 6 for velocity and position
		flops = ((14.0*N*N + 6.0*N) * steps)/ 1.0e9 / getTotalTime();
		// We calculated the mem size from the bumber of cuboids and their individual size
		//bytes = (4.0f * (double) N * 10.0f * (double) steps)/ 1000000000.0f / getTotalTime();
		bytes = memSize / 1.0e6 / getTotalTime();
		// Verify solution.
		verify();
	}

	private void printResults(int steps) {
		// Print results and stuff.
		System.out.print("\n");
		System.out.print(String.format(" Loop 0 = %f seconds.\n", prprDrtn.toNanos() / 1.0e9));
		System.out.print(String.format(" Loop 1 = %f seconds.\n", calcAccelDrtn.toNanos() / 1.0e9));
		System.out.print(String.format(" Loop 2 = %f seconds.\n", calcVelDrtn.toNanos() / 1.0e9));
		System.out.print(String.format(" Loop 3 = %f seconds.\n", calcPosDrtn.toNanos() / 1.0e9));
		System.out.print(String.format(" Total  = %f seconds.\n", getTotalTime()));
		System.out.print("\n");
		System.out.print(String.format(" GFLOP/s = %f\n", flops));
		System.out.print(String.format(" GB/s = %f\n", bytes));
		System.out.print("\n");
		System.out.print(String.format(" Total time = %f seconds.\n", overHeadDrtn.toNanos() / 1.0e9));
		System.out.print(String.format(" Answer = %f\n", phi));
		System.out.print("\n");
	}

	private void verify() {
		
	}

	private double getTotalTime() {
		return prprDrtn.plus(calcAccelDrtn).plus(calcVelDrtn).plus(calcPosDrtn).toNanos() / 1.0e9;
	}


}
