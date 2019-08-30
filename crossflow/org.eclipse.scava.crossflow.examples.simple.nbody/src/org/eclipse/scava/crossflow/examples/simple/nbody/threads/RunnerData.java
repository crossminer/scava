package org.eclipse.scava.crossflow.examples.simple.nbody.threads;

import java.io.Serializable;
import java.util.Collection;

import org.eclipse.scava.crossflow.examples.simple.nbody.NBody3DBody;
import org.eclipse.scava.crossflow.examples.simple.nbody.NBodyCuboid.CuboidCoordinates;

public interface RunnerData extends Serializable {
	
	/** Dampening factor for the particles velocities */
	double velocityDampeningFactor();
	/** The time delta of each simulation step */
	double timeDelta();
	/** The universe */
	Collection<NBody3DBody> universe();
	/** The cuboid coordinates */
	CuboidCoordinates coordiantes();

}
