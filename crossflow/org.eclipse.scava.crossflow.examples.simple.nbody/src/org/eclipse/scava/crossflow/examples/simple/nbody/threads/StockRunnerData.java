package org.eclipse.scava.crossflow.examples.simple.nbody.threads;

import java.util.Collection;

import org.eclipse.scava.crossflow.examples.simple.nbody.NBody3DBody;
import org.eclipse.scava.crossflow.examples.simple.nbody.NBodyCuboid.CuboidCoordinates;

public class StockRunnerData implements RunnerData {

	private final double dmp;
	private final double timeDelta;
	private final CuboidCoordinates coordiantes;
	private final Collection<NBody3DBody> universe;

	public StockRunnerData(
		double dmp,
		double timeDelta,
		CuboidCoordinates coordiantes,
		Collection<NBody3DBody> universe) {
		super();
		this.dmp = dmp;
		this.timeDelta = timeDelta;
		this.coordiantes = coordiantes;
		this.universe = universe;
	}

	@Override
	public double velocityDampeningFactor() {
		return dmp;
	}

	@Override
	public double timeDelta() {
		return timeDelta;
	}

	@Override
	public Collection<NBody3DBody> universe() {
		return universe;
	}

	@Override
	public CuboidCoordinates coordiantes() {
		return coordiantes;
	}

}
