package org.eclipse.scava.crossflow.examples.simple.nbody;

import org.eclipse.scava.crossflow.examples.simple.nbody.NBodyCuboid.CuboidCoordinates;

public class StockCuboidCoordinates implements CuboidCoordinates{
	
	private final double xmin;
	private final double ymin;
	private final double zmin;
	private final double xmax;
	private final double ymax;
	private final double zmax;
	
	public StockCuboidCoordinates() {
		this(-1,-1,-1,1,1,1);
	}

	public StockCuboidCoordinates(double xmin, double ymin, double zmin, double xmax, double ymax, double zmax) {
		super();
		this.xmin = xmin;
		this.ymin = ymin;
		this.zmin = zmin;
		this.xmax = xmax;
		this.ymax = ymax;
		this.zmax = zmax;
	}

	@Override
	public double xmin() {
		return xmin;
	}

	@Override
	public double ymin() {
		return ymin;
	}

	@Override
	public double zmin() {
		return zmin;
	}

	@Override
	public double xmax() {
		return xmax;
	}

	@Override
	public double ymax() {
		return ymax;
	}

	@Override
	public double zmax() {
		return zmax;
	}

}
