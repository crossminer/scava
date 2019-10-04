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

/**
 * The Interface represents a body in a 3D N-body problem that provides color information so it
 * can be represented visually.
 */
public interface OpenGLNBody3DBody extends NBody3DBody {

	/**
	 * Returns a float array containing the color components of the body Color, in the sRGB color
	 * space.
	 *
	 * @return the color components, [r, g, b]
	 */
	float[] getColor();

}
