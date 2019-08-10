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

import java.util.Set;

/**
 * Class to create the bodies used in the simulation.
 *
 * @author Horacio Hoyos Rodriguez
 */
public interface Bodies {

	public class CreatingBodiesException extends Exception {

		private static final long serialVersionUID = 5198791122657218404L;

		public CreatingBodiesException(String message) {
			super(message);
		}
		
	}
	
	Set<NBody3DBody> createBodies() throws CreatingBodiesException;

}
