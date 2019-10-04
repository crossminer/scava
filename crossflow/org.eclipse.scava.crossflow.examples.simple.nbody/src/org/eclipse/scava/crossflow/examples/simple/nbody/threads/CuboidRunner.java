package org.eclipse.scava.crossflow.examples.simple.nbody.threads;

import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.Callable;

import org.eclipse.scava.crossflow.examples.simple.nbody.AxisAlignedCuboid;
import org.eclipse.scava.crossflow.examples.simple.nbody.NBody3DBody;
import org.eclipse.scava.crossflow.examples.simple.nbody.NBodyCuboid;
import org.eclipse.scava.crossflow.examples.simple.nbody.NBodyCuboid.CuboidCoordinates;
import org.jctools.queues.MpscArrayQueue;
import org.jctools.queues.SpmcArrayQueue;

public class CuboidRunner implements Callable<UUID> {

	private final UUID id;
	private final double dmp;
	private final double timeDelta;
	
	private final SpmcArrayQueue<CuboidCoordinates> cuboidQueue;
	private final Collection<NBody3DBody> universe;
	private final MpscArrayQueue<CuboidRunnerResults> resultsQueue;
	
	
	public CuboidRunner(double dmp, double timeDelta,
		SpmcArrayQueue<CuboidCoordinates> cuboidQueue,
		MpscArrayQueue<CuboidRunnerResults> resultsQueue,
		Collection<NBody3DBody> universe) {
		this.id = UUID.randomUUID();
		this.dmp = dmp;
		this.timeDelta = timeDelta;
		this.cuboidQueue = cuboidQueue;
		this.universe = universe;
		this.resultsQueue = resultsQueue;
	}

	@Override
	public UUID call() {
		CuboidCoordinates coords = cuboidQueue.poll();
		if (coords != null) {
			NBodyCuboid c = new AxisAlignedCuboid(coords, dmp, timeDelta);
			Collection<NBody3DBody> bodies = c.stepSimulation(universe);
			resultsQueue.add(new Results(c.durations(), bodies, c.coordinates(), id, c.memSize()));
			return id;
		}
		return null;
	}

}
