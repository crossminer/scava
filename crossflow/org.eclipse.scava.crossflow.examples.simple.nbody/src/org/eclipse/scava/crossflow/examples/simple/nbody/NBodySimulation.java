/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
**********************************************************************/
package org.eclipse.scava.crossflow.examples.simple.nbody;

import java.nio.file.Path;

import org.eclipse.scava.crossflow.examples.simple.nbody.Bodies.CreatingBodiesException;

public interface NBodySimulation {

	public class InvalidNumberOfCubesException extends Exception {

		private static final long serialVersionUID = -5717755901597764901L;
	}

	
	/**
	 * Populate the Simulation with bodies defines in a JSON file.
	 *
	 * @param data 					the path to the JSON file
	 * @throws CreatingBodiesException if there is an error generating the bodies
	 */
	void populateFromJson(Path data) throws CreatingBodiesException;

	/**
	 * Populate the Simulation with randomly generated bodies. The size parameter determines
	 * the number of bodies to generate.
	 *
	 * @param size 					the number of bodies to create
	 * @throws CreatingBodiesException if there is an error generating the bodies
	 */
	void populateRandomly(int size) throws CreatingBodiesException;
	
	/**
	 * Populate the Simulation with randomly generated bodies. The size parameter determines
	 * the number of bodies to generate. Additionally, the scale of the position, velocity
	 * and mass of the bodies can be adjusted.
	 * 
	 * @param size					the number of bodies to create
	 * @param pscale				the scale for the position
	 * @param vscale				the scale for the velocity
	 * @param mscale				the scale for the mass
	 * @throws CreatingBodiesException
	 */
	void populateRandomly(int size, double pscale, double vscale, double mscale) throws CreatingBodiesException;
	
	/**
	 * Run the simulation for the given number of steps
	 * @param steps
	 * @throws InvalidNumberOfCubesException
	 */
	void runSimulation(int steps) throws InvalidNumberOfCubesException;
	
	/**
	 * Return a CSV of the timing values for the last step in the form:
	 * acc,vel,pos,total,GFLOP/s,GB/s,global
	 * @return
	 */
	String getMetrics();
	
	/**
	 * Return the final value of the simulation.
	 * @return
	 */
	double getPhi();
	
}
