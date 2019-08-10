package org.eclipse.scava.crossflow.examples.simple.nbody;

public interface CuboidSimulationDurations extends NBodyMetrics {

	CuboidSimulationDurations logPos(long start);

	CuboidSimulationDurations logVel(long start);

	CuboidSimulationDurations logAccel(long start);

	CuboidSimulationDurations logPrepare(long start);
	
}