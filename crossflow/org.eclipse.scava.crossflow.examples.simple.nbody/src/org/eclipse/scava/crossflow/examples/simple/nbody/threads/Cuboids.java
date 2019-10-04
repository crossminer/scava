package org.eclipse.scava.crossflow.examples.simple.nbody.threads;

import java.util.Collection;

import org.eclipse.scava.crossflow.examples.simple.nbody.NBodyCuboid.CuboidCoordinates;
import org.eclipse.scava.crossflow.examples.simple.nbody.NBodySimulation.InvalidNumberOfCubesException;

public interface Cuboids {

	/**
	 * Divide the parent cuboid into the given number of contained cuboids. The number of
	 * cuboids must be a power of 2. 
	 *
	 * @param numCubes 				the number cubes
	 * @param parentCuboid 			the parent cuboid
	 * @return the collection of coordinates that define the contained cuboids
	 * @throws InvalidNumberOfCubesException the invalid number of cubes exception
	 */
	Collection<CuboidCoordinates> setupCuboids(int numCubes) throws InvalidNumberOfCubesException;

}