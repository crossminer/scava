package org.eclipse.scava.crossflow.examples.simple.nbody;

import org.eclipse.scava.crossflow.examples.simple.nbody.NBodyCuboid.CuboidCoordinates;

public class StockCuboidCoordinates implements CuboidCoordinates{
	
	
	/**
     * Serialization version.
     */
	private static final long serialVersionUID = -7141206561194913813L;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(xmax);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(xmin);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(ymax);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(ymin);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(zmax);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(zmin);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StockCuboidCoordinates other = (StockCuboidCoordinates) obj;
		if (Double.doubleToLongBits(xmax) != Double.doubleToLongBits(other.xmax))
			return false;
		if (Double.doubleToLongBits(xmin) != Double.doubleToLongBits(other.xmin))
			return false;
		if (Double.doubleToLongBits(ymax) != Double.doubleToLongBits(other.ymax))
			return false;
		if (Double.doubleToLongBits(ymin) != Double.doubleToLongBits(other.ymin))
			return false;
		if (Double.doubleToLongBits(zmax) != Double.doubleToLongBits(other.zmax))
			return false;
		if (Double.doubleToLongBits(zmin) != Double.doubleToLongBits(other.zmin))
			return false;
		return true;
	}
	
	

}
