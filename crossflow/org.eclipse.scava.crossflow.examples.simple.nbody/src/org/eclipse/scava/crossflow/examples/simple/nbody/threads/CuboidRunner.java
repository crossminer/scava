package org.eclipse.scava.crossflow.examples.simple.nbody.threads;

import java.io.Serializable;
import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.Callable;

import org.eclipse.scava.crossflow.examples.simple.nbody.AxisAlignedCuboid;
import org.eclipse.scava.crossflow.examples.simple.nbody.CuboidSimulationDurations;
import org.eclipse.scava.crossflow.examples.simple.nbody.NBody3DBody;
import org.eclipse.scava.crossflow.examples.simple.nbody.NBodyCuboid;
import org.eclipse.scava.crossflow.examples.simple.nbody.NBodyCuboid.CuboidCoordinates;
import org.jctools.queues.MpscArrayQueue;
import org.jctools.queues.SpmcArrayQueue;

public class CuboidRunner implements Callable<UUID> {

	public interface CuboidRunnerResults extends Serializable {
	
		UUID runnerId();
		CuboidCoordinates coordiantes();
		CuboidSimulationDurations durations();
		Collection<NBody3DBody> bodies();
		long memUsed();
	}
	
	public class Results implements CuboidRunnerResults {
		private final CuboidSimulationDurations durations;
		private final Collection<NBody3DBody> bodies;
		private final CuboidCoordinates coordinates;
		private final UUID runnerId;
		private final long memUsed;
		
		public Results(
			CuboidSimulationDurations durations,
			Collection<NBody3DBody> result,
			CuboidCoordinates coordinates,
			UUID runnerId,
			long memUsed) {
			super();
			this.durations = durations;
			this.bodies = result;
			this.coordinates = coordinates;
			this.runnerId = runnerId;
			this.memUsed = memUsed;
		}

		@Override
		public UUID runnerId() {
			return runnerId;
		}

		@Override
		public CuboidCoordinates coordiantes() {
			return coordinates;
		}

		@Override
		public CuboidSimulationDurations durations() {
			return durations;
		}

		@Override
		public Collection<NBody3DBody> bodies() {
			return bodies;
		}

		@Override
		public long memUsed() {
			return memUsed;
		}
	}

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
			long start = System.nanoTime();
			Collection<NBody3DBody> bodies = c.stepSimulation(universe);
			System.out.println("Th Step " + (System.nanoTime()-start)/1e9);
			start = System.nanoTime();
			resultsQueue.add(new Results(c.durations(), bodies, c.coordinates(), id, c.memSize()));
			System.out.println("Th que results " + (System.nanoTime()-start)/1e9);
			return id;
		}
		return null;
	}

}
