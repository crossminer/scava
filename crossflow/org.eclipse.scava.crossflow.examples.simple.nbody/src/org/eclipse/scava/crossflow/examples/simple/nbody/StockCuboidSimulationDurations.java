package org.eclipse.scava.crossflow.examples.simple.nbody;

import java.time.Duration;

public class StockCuboidSimulationDurations implements CuboidSimulationDurations {
	private final Duration prprDrtn;
	private final Duration calcAccelDrtn;
	private final Duration calcVelDrtn;
	private final Duration calcPosDrtn;

	public StockCuboidSimulationDurations(
		Duration prprDrtn,
		Duration calcAccelDrtn,
		Duration calcVelDrtn,
		Duration calcPosDrtn) {
		super();
		this.prprDrtn = prprDrtn;
		this.calcAccelDrtn = calcAccelDrtn;
		this.calcVelDrtn = calcVelDrtn;
		this.calcPosDrtn = calcPosDrtn;
	}

	public StockCuboidSimulationDurations() {
		this(null, null, null, null);
	}

	@Override
	public CuboidSimulationDurations logPrepare(long start) {
		return new StockCuboidSimulationDurations(Duration.ofNanos(System.nanoTime() - start), calcAccelDrtn,
				calcVelDrtn, calcPosDrtn);
	}

	@Override
	public CuboidSimulationDurations logAccel(long start) {
		return new StockCuboidSimulationDurations(prprDrtn, Duration.ofNanos(System.nanoTime() - start), calcVelDrtn,
				calcPosDrtn);
	}

	@Override
	public CuboidSimulationDurations logVel(long start) {
		return new StockCuboidSimulationDurations(prprDrtn, calcAccelDrtn, Duration.ofNanos(System.nanoTime() - start),
				calcPosDrtn);
	}

	@Override
	public CuboidSimulationDurations logPos(long start) {
		return new StockCuboidSimulationDurations(prprDrtn, calcAccelDrtn, calcVelDrtn,
				Duration.ofNanos(System.nanoTime() - start));
	}

	@Override
	public Duration prepareDrtn() throws RequestedDurationNotFound {
		if (prprDrtn == null) {
			throw new RequestedDurationNotFound();
		}
		return prprDrtn;
	}

	@Override
	public Duration calcAccelDrtn() throws RequestedDurationNotFound {
		if (calcAccelDrtn == null) {
			throw new RequestedDurationNotFound();
		}
		return calcAccelDrtn;
	}

	@Override
	public Duration calcVelDrtn() throws RequestedDurationNotFound {
		if (calcVelDrtn == null) {
			throw new RequestedDurationNotFound();
		}
		return calcVelDrtn;
	}

	@Override
	public Duration calcPosDrtn() throws RequestedDurationNotFound {
		if (calcPosDrtn == null) {
			throw new RequestedDurationNotFound();
		}
		return calcPosDrtn;
	}

	@Override
	public String toString() {
		return String.format("prep=%s, accel=%s, vel=%s, pos=%s",
				prprDrtn == null ? "??" : prprDrtn.toString(),
				calcAccelDrtn == null ? "??" : calcAccelDrtn.toString(),
				calcVelDrtn == null ? "??" : calcVelDrtn.toString(),
				calcPosDrtn == null ? "??" : calcPosDrtn.toString());
	}

	@Override
	public long memSize() {
		// TODO Implement NBodyMetrics.memSize
		throw new RuntimeException("Unimplemented Method NBodyMetrics.memSize invoked.");
	}

}