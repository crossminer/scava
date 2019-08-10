package org.eclipse.scava.crossflow.examples.simple.nbody;

import java.io.Serializable;
import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.Callable;

import org.eclipse.scava.crossflow.examples.simple.nbody.CuboidRunner.CuboidRunnerResults;
import org.eclipse.scava.crossflow.examples.simple.nbody.NBodyCuboid.CuboidCoordinates;
import org.jctools.queues.MpscArrayQueue;
import org.jctools.queues.SpmcArrayQueue;

public class CuboidRunner implements Callable<UUID> {

	public interface CuboidRunnerResults extends Serializable {
	
		UUID runnerId();
		CuboidCoordinates coordiantes();
		CuboidSimulationDurations durations();
		Collection<NBody3DBody> bodies();
	}
	
	public class Results implements CuboidRunnerResults {
		private final CuboidSimulationDurations durations;
		private final Collection<NBody3DBody> bodies;
		private final CuboidCoordinates coordinates;
		private final UUID runnerId;
		
		public Results(
			CuboidSimulationDurations durations,
			Collection<NBody3DBody> result,
			CuboidCoordinates coordinates,
			UUID runnerId) {
			super();
			this.durations = durations;
			this.bodies = result;
			this.coordinates = coordinates;
			this.runnerId = runnerId;
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
			System.out.println(String.format("Task %s running", id));
			NBodyCuboid c = new AxisAlignedCuboid(coords, dmp, timeDelta);
			Collection<NBody3DBody> bodies = c.stepSimulation(universe);
			System.out.println(String.format("%s", c));
			resultsQueue.add(new Results(c.durations(), bodies, c.coordinates(), id));
			return id;
		}
		return null;
	}

}
