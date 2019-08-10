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
package org.eclipse.scava.crossflow.examples.simple.nbody;

import java.nio.file.Path;
import java.time.Duration;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.math3.util.FastMath;
import org.eclipse.scava.crossflow.examples.simple.nbody.Bodies.CreatingBodiesException;
import org.eclipse.scava.crossflow.examples.simple.nbody.NBodyMetrics.RequestedDurationNotFound;

/**
 * The Class SimpleNBody.
 */
public class SimpleNBody implements NBodySimulation {

	private final double eps = 0.00125;

	private Set<NBody3DBody> universe;
	private Set<NBodyCuboid> cuboides;
	private int numCubes = 0;
	private Duration prprDrtn;
	private Duration calcAccelDrtn;
	private Duration calcVelDrtn;
	private Duration calcPosDrtn;
	private Duration overHeadDrtn;
	private double phi;
	private double flops;
	private double bytes;
	
	public static void main(String... args) throws Exception {
		SimpleNBody sim = new SimpleNBody();
		sim.populateRandomly(Integer.parseInt(args[0]));
		sim.setupCubes(Integer.parseInt(args[2]));
		sim.runSimulation(Integer.parseInt(args[1]));
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

	/**
	 * Divide the simulation space into the given number of cubes. The number of
	 * cubes must be a power of 2. This should be called once per simulation, at the beginning.
	 * 
	 * @param numCubes
	 * @return
	 * @throws InvalidNumberOfCubesException
	 */
	public void setupCubes(final int numCubes) throws InvalidNumberOfCubesException {
		if (!((numCubes > 0) && ((numCubes & (numCubes - 1)) == 0))) {
			throw new InvalidNumberOfCubesException();
		}
		// No need to change?
		if (this.numCubes != numCubes) {
			this.numCubes = numCubes;
			if (numCubes == 1) {
				cuboides = Collections.singleton(new AxisAlignedCuboid());
			}
			else {
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
				cuboides = new HashSet<>();
				double[] xCoords = axisSlices(xPoints);
				double[] yCoords = axisSlices((yPoints == 0) ? 1 : yPoints);
				double[] zCoords = axisSlices((zPoints == 0) ? 1 : zPoints);
				for (int i = 0; i < xCoords.length-1; i++) {
					for (int j = 0; j < yCoords.length-1; j++) {
						for (int k = 0; k < zCoords.length-1; k++) {
							cuboides.add(new AxisAlignedCuboid(new StockCuboidCoordinates(xCoords[i], yCoords[j], zCoords[k], xCoords[i+1], yCoords[j+1], zCoords[k+1])));
						}
					}
				}
			}
		}
	}

	public void runSimulation(int steps) {
		prprDrtn = Duration.ZERO;
		calcAccelDrtn = Duration.ZERO;
		calcVelDrtn = Duration.ZERO;
		calcPosDrtn = Duration.ZERO;
		Set<NBody3DBody> newUniverse;
		long start = System.nanoTime();
		for (int i = 0; i < steps; i++) {
			newUniverse = new HashSet<>(universe.size());
			for (NBodyCuboid c : cuboides) {
				newUniverse.addAll(c.stepSimulation(universe));
				try {
					prprDrtn = prprDrtn.plus(c.prepareDrtn());
					calcAccelDrtn = calcAccelDrtn.plus(c.calcAccelDrtn());
					calcVelDrtn = calcVelDrtn.plus(c.calcVelDrtn());
					calcPosDrtn = calcPosDrtn.plus(c.calcPosDrtn());
				} catch (RequestedDurationNotFound e) {
					throw new IllegalStateException(e);
				}
				
			}
			universe = newUniverse;
		}
		overHeadDrtn = Duration.ofNanos(System.nanoTime() - start);
		calculatePerformance(universe.size(), steps);
		printResults(steps);
	}
	
	private void calculatePerformance(int N, int steps) {
		flops = (20.0f * (double) N * (double) (N-1) * (double) steps)/ 1000000000.0f / getTotalTime();
		bytes = (4.0f * (double) N * 10.0f * (double) steps)/ 1000000000.0f / getTotalTime();
	    // Verify solution.
	    verify();
	}


	public void printResults(int steps) {
		// Print results and stuff.
	    System.out.print("\n");
	    System.out.print(String.format(" Loop 0 = %f seconds.\n", prprDrtn.toNanos()/1.0e9));
	    System.out.print(String.format(" Loop 1 = %f seconds.\n", calcAccelDrtn.toNanos()/1.0e9));
	    System.out.print(String.format(" Loop 2 = %f seconds.\n", calcVelDrtn.toNanos()/1.0e9));
	    System.out.print(String.format(" Loop 3 = %f seconds.\n", calcPosDrtn.toNanos()/1.0e9));
	    System.out.print(String.format(" Total  = %f seconds.\n", getTotalTime()));
	    System.out.print("\n");
	    System.out.print(String.format(" GFLOP/s = %f\n", flops / 1000000000.0f / (getTotalTime())));
	    System.out.print(String.format(" GB/s = %f\n", bytes / 1000000000.0f / (getTotalTime())));
	    System.out.print("\n");
	    System.out.print(String.format(" Total time = %f seconds.\n", overHeadDrtn.toNanos()/1.0e9));
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
				double r6inv = r2inv*r2inv*r2inv;
				phi += body2.mass() * r6inv;
				
			}	
		}
	}

	private double getTotalTime() {
		return prprDrtn.plus(calcAccelDrtn).plus(calcVelDrtn).plus(calcPosDrtn).toNanos()/1.0e9;
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
