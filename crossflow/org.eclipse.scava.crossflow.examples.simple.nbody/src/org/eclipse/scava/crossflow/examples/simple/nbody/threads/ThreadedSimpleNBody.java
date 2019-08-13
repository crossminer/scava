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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.apache.commons.math3.util.FastMath;
import org.eclipse.scava.crossflow.examples.simple.nbody.Bodies.CreatingBodiesException;
import org.eclipse.scava.crossflow.examples.simple.nbody.JsonBodies;
import org.eclipse.scava.crossflow.examples.simple.nbody.NBody3DBody;
import org.eclipse.scava.crossflow.examples.simple.nbody.NBodyCuboid;
import org.eclipse.scava.crossflow.examples.simple.nbody.NBodyCuboid.CuboidCoordinates;
import org.eclipse.scava.crossflow.examples.simple.nbody.NBodyMetrics.RequestedDurationNotFound;
import org.eclipse.scava.crossflow.examples.simple.nbody.NBodySimulation;
import org.eclipse.scava.crossflow.examples.simple.nbody.RandomBodies;
import org.eclipse.scava.crossflow.examples.simple.nbody.StockCuboidCoordinates;
import org.eclipse.scava.crossflow.examples.simple.nbody.Vector3D;
import org.eclipse.scava.crossflow.examples.simple.nbody.threads.CuboidRunner.CuboidRunnerResults;
import org.jctools.queues.MpscArrayQueue;
import org.jctools.queues.SpmcArrayQueue;

/**
 * An implementation of NBodySimulation that runs in separate threads.
 */
public class ThreadedSimpleNBody implements NBodySimulation {

	private final double eps = 0.00125;
	private Set<NBody3DBody> universe;
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

		for (int i = 0; i < steps; i++) {
			// x in b=2^x, where b is the next power of 2 greater than a
			// int numCuboids = (int) Math.pow(2, 32 - Integer.numberOfLeadingZeros(maxCuboids - 1));
			int numCuboids = 4;
			Set<CuboidCoordinates> stepCuboids = setupCuboids(numCuboids);
			Set<NBody3DBody> newUniverse = new HashSet<>(universe.size());
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
		printResults(steps);
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
		flops = ((14*N*N + 6*N) * steps)/ 1000000000.0f / getTotalTime();
		// We calculated the mem size from the bumber of cuboids and their individual size
		//bytes = (4.0f * (double) N * 10.0f * (double) steps)/ 1000000000.0f / getTotalTime();
		bytes = memSize / 1000000.0f / getTotalTime();
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
		phi = 0.0f;
		for (NBody3DBody body1 : universe) {
			for (NBody3DBody body2 : universe) {
				Vector3D distance = body2.position().subtract(body1.position());
				double r2 = FastMath.sqrt(distance.normSq() + eps);
				double r2inv = 1.0 / r2;
				double r6inv = r2inv * r2inv * r2inv;
				phi += body2.mass() * r6inv;

			}
		}
	}

	private double getTotalTime() {
		return prprDrtn.plus(calcAccelDrtn).plus(calcVelDrtn).plus(calcPosDrtn).toNanos() / 1.0e9;
	}

	/**
	 * Divide the simulation space into the given number of cubes. The number of
	 * cubes must be a power of 2. This should be called once per simulation, at the
	 * beginning.
	 * 
	 * @param numCubes
	 * @return
	 * @throws InvalidNumberOfCubesException
	 */
	private Set<CuboidCoordinates> setupCuboids(final int numCubes) throws InvalidNumberOfCubesException {
		Set<CuboidCoordinates> cuboids = new HashSet<>();
		if (!((numCubes > 0) && ((numCubes & (numCubes - 1)) == 0))) {
			throw new InvalidNumberOfCubesException();
		}
		if (numCubes == 1) {
			cuboids.add(new StockCuboidCoordinates());
		} else {
			// axis 0 = x, 1 = y, 2 = z;
			int xPoints = 0;
			int yPoints = 0;
			int zPoints = 0;
			int axis = 0;
			int numcubes = numCubes;
			while (numcubes > 1) {
				switch (axis) {
				case 0:
					xPoints += 2;
					break;
				case 1:
					yPoints += 2;
					break;
				case 2:
					zPoints += 2;
				default:
					axis = 0;
				}
				numcubes /= 2;
				axis++;
			}
			double[] xCoords = axisSlices(xPoints);
			double[] yCoords = axisSlices((yPoints == 0) ? 1 : yPoints);
			double[] zCoords = axisSlices((zPoints == 0) ? 1 : zPoints);
			for (int i = 0; i < xCoords.length - 1; i++) {
				for (int j = 0; j < yCoords.length - 1; j++) {
					for (int k = 0; k < zCoords.length - 1; k++) {
						cuboids.add(new StockCuboidCoordinates(xCoords[i], yCoords[j], zCoords[k], xCoords[i + 1],
								yCoords[j + 1], zCoords[k + 1]));
					}
				}
			}
		}

		return cuboids;
	}

	private double[] axisSlices(double points) {
		double slice = 2 / points;
		double point = -1;
		double[] coords = new double[(int) (points + 1)];
		for (int i = 0; i <= points; i++) {
			coords[i] = point;
			point += slice;
		}
		return coords;
	}



}
