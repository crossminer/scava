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
 * This interface represents a body in a 3D N-body problem. The body has a mass and a position in a 
 * 3D Euclidean space given by a {@link Vector3D}. 
 * 
 * <p>
 * Instance of this class are guaranteed to be immutable.
 * 
 * @author Horacio Hoyos Rodriguez
 *
 */
public interface NBody3DBody extends Serializable {

	/**
	 * The mass of the body.
	 *
	 * @return the double
	 */
	double mass();
	
	/**
	 * Get the position of the body.
	 *
	 * @return the vector 3 D
	 */
	Vector3D position();
	
	/**
	 * Test if the body is inside the cube.
	 *
	 * @param cube the cube
	 * @return true, if successful
	 */
	boolean insideCube(NBodyCuboid cube);

	/**
	 * Clear the acceleration before computing a new one.
	 *
	 * @return the n body 3 D body
	 */
	NBody3DBody prepare();

	/**
	 * Accelerate the Body given with respect to other bodies in the system.
	 *
	 * @param other the other
	 * @return the n body 3 D body
	 */
	NBody3DBody accelerate(Collection<? extends NBody3DBody> other);
	
	NBody3DBody accelerate(NBody3DBody... other);

	/**
	 * Update the body's velocity according to its acceleration.
	 *
	 * @param dmp the dmp
	 * @param timeDelta the time delta
	 * @return the n body 3 D body
	 */
	NBody3DBody updateVelocity(double dmp, double timeDelta);

	/**
	 * Update the body's position based on its velocity and the time delta.
	 *
	 * @param timeDelta the time delta
	 * @return the n body 3 D body
	 */
	NBody3DBody updatePostion(double timeDelta);

}
