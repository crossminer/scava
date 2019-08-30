package org.eclipse.scava.crossflow.examples.simple.nbody;

import java.io.Serializable;

public interface CuboidSimulationDurations extends NBodyMetrics, Serializable {

	CuboidSimulationDurations logPos(long start);

	CuboidSimulationDurations logVel(long start);

	CuboidSimulationDurations logAccel(long start);

	CuboidSimulationDurations logPrepare(long start);
	
}