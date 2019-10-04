package org.eclipse.scava.crossflow.examples.simple.nbody;

import java.time.Duration;

public class StockCuboidSimulationDurations implements CuboidSimulationDurations {
	
	/**
     * Serialization version.
     */
	private static final long serialVersionUID = 6331799019481689051L;
	
//	private final Duration prprDrtn;
//	private final Duration calcAccelDrtn;
//	private final Duration calcVelDrtn;
//	private final Duration calcPosDrtn;
	private final Long prprDrtnNanos;
	private final Long calcAccelDrtnNanos;
	private final Long calcVelDrtnNanos;
	private final Long calcPosDrtnNanos;

	public StockCuboidSimulationDurations(
			Long prprDrtn,
			Long calcAccelDrtn,
			Long calcVelDrtn,
			Long calcPosDrtn) {
		super();
		this.prprDrtnNanos = prprDrtn;
		this.calcAccelDrtnNanos = calcAccelDrtn;
		this.calcVelDrtnNanos = calcVelDrtn;
		this.calcPosDrtnNanos = calcPosDrtn;
	}

	public StockCuboidSimulationDurations() {
		this(null,null,null,null);
	}

	@Override
	public CuboidSimulationDurations logPrepare(long start) {
		return new StockCuboidSimulationDurations(System.nanoTime() - start,
				calcAccelDrtnNanos, calcVelDrtnNanos, calcPosDrtnNanos);
	}

	@Override
	public CuboidSimulationDurations logAccel(long start) {
		return new StockCuboidSimulationDurations(prprDrtnNanos,
				(System.nanoTime() - start),
				calcVelDrtnNanos, calcPosDrtnNanos);
	}

	@Override
	public CuboidSimulationDurations logVel(long start) {
		return new StockCuboidSimulationDurations(prprDrtnNanos, calcAccelDrtnNanos,
				(System.nanoTime() - start),
				calcPosDrtnNanos);
	}

	@Override
	public CuboidSimulationDurations logPos(long start) {
		return new StockCuboidSimulationDurations(prprDrtnNanos, calcAccelDrtnNanos, calcVelDrtnNanos,
				(System.nanoTime() - start));
	}

	@Override
	public Duration prepareDrtn() throws RequestedDurationNotFound {
		if (prprDrtnNanos == null) {
			throw new RequestedDurationNotFound();
		}
		return Duration.ofNanos(prprDrtnNanos);
	}

	@Override
	public Duration calcAccelDrtn() throws RequestedDurationNotFound {
		if (calcAccelDrtnNanos == null) {
			throw new RequestedDurationNotFound();
		}
		return Duration.ofNanos(calcAccelDrtnNanos);
	}

	@Override
	public Duration calcVelDrtn() throws RequestedDurationNotFound {
		if (calcVelDrtnNanos == null) {
			throw new RequestedDurationNotFound();
		}
		return Duration.ofNanos(calcVelDrtnNanos);
	}

	@Override
	public Duration calcPosDrtn() throws RequestedDurationNotFound {
		if (calcPosDrtnNanos == null) {
			throw new RequestedDurationNotFound();
		}
		return Duration.ofNanos(calcPosDrtnNanos);
	}

	@Override
	public String toString() {
		return String.format("prep=%s, accel=%s, vel=%s, pos=%s",
				prprDrtnNanos == null ? "??" : Duration.ofNanos(prprDrtnNanos),
				calcAccelDrtnNanos == null ? "??" : Duration.ofNanos(calcAccelDrtnNanos),
				calcVelDrtnNanos == null ? "??" : Duration.ofNanos(calcVelDrtnNanos),
				calcPosDrtnNanos == null ? "??" : Duration.ofNanos(calcPosDrtnNanos));
	}

	@Override
	public long memSize() {
		// TODO Implement NBodyMetrics.memSize
		throw new RuntimeException("Unimplemented Method NBodyMetrics.memSize invoked.");
	}

}