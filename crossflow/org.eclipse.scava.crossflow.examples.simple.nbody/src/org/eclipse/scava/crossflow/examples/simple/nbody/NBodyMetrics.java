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

import java.time.Duration;

/**
 * This interface represents the metrics we are interested from a simluation of the N-body problem.
 *
 * @author Horacio Hoyos Rodriguez
 */
public interface NBodyMetrics {

	public class RequestedDurationNotFound extends Exception {
		private static final long serialVersionUID = -8323910256299084L;
	}

	// loop0
	/**
	 * Return the duration of reseting the body acceleration information
	 * @return the duration of the phase
	 */
	Duration prepareDrtn() throws RequestedDurationNotFound;
	
	// loop1
	/**
	 * Return the duration of calculating the acceleration of the body due to interactions
	 * @return the duration of the phase
	 */
	Duration calcAccelDrtn() throws RequestedDurationNotFound;
	
	//loop2
	/**
	 * Return the duration of calculating the new velocity of the body
	 * @return the duration of the phase
	 */
	Duration calcVelDrtn() throws RequestedDurationNotFound;
	
	//loop3 
	/**
	 * Return the duration of calculating the final position of the body
	 * @return the duration of the phase
	 */
	Duration calcPosDrtn() throws RequestedDurationNotFound;
	
	long memSize();

}
