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

import java.io.Serializable;
import java.util.Collection;

/**
 * The Interface represents a 3D cuboid in a 3d n-body simulation problem. 
 *
 * @author Horacio Hoyos Rodriguez
 */
public interface NBodyCuboid extends NBodyMetrics {

	public interface CuboidCoordinates extends Serializable {
		/**
		 * The maximum z-axis coordinate of the cuboid
		 * @return the max z-axis coordinate
		 */
		double zmax();

		/**
		 * The maximum y-axis coordinate of the cuboid
		 * @return the max y-axis coordinate
		 */
		double ymax();

		/**
		 * The maximum x-axis coordinate of the cuboid
		 * @return the max x-axis coordinate
		 */	
		double xmax();

		/**
		 * The minimum z-axis coordinate of the cuboid
		 * @return the min z-axis coordinate
		 */
		double zmin();

		/**
		 * The minimum y-axis coordinate of the cuboid
		 * @return the min y-axis coordinate
		 */
		double ymin();

		/**
		 * The minimum x-axis coordinate of the cuboid
		 * @return the min x-axis coordinate
		 */
		double xmin();
	}

	/**
	 * Reconfigure the cuboid with a new set of coordinates.
	 * @param x_min
	 * @param x_max
	 * @param y_min
	 * @param y_max
	 * @param z_min
	 * @param z_max
	 * @return
	 */
//	NBodyCuboid configureCuboid(
//			double x_min, double x_max,
//			double y_min, double y_max,
//			double z_min, double z_max);
//			
//	NBodyCuboid configureCuboid(CuboidCoordinates coords);
//	
	CuboidCoordinates coordinates();
	
	/**
	 * Update the status of all bodies in the universe
	 * @param universe				the current bodies in the universe
	 * @return	the bodies modified by this cuboid
	 */
	Collection<NBody3DBody> stepSimulation(Collection<NBody3DBody> universe);

	CuboidSimulationDurations durations();

}
