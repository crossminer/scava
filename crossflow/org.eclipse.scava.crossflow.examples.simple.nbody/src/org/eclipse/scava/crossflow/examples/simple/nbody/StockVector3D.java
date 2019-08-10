/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
**********************************************************************/
package org.eclipse.scava.crossflow.examples.simple.nbody;

import org.apache.commons.math3.util.FastMath;

/**
 * Stock implementation of Vector3D
 *
 * @author Horacio Hoyos Rodriguez
 */
public class StockVector3D implements Vector3D {
	
	
	private final double x;
	private final double y;
	private final double z;
	
	public StockVector3D(double x, double y, double z) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public double x() {
		return x;
	}
	
	@Override
	public double y() {
		return y;
	}

	@Override
	public double z() {
		return z;
	}
	
	@Override
	public double norm() {
		return FastMath.sqrt (x * x + y * y + z * z);
	}
	
	@Override
	public double normSq() {
		return x * x + y * y + z * z;
	}

	@Override
	public Vector3D add(Vector3D other) {
		return new StockVector3D(x + other.x(), y + other.y(), z + other.z());
	}

	@Override
	public Vector3D subtract(Vector3D other) {
		return new StockVector3D(x - other.x(), y - other.y(), z - other.z());
	}

	@Override
	public Vector3D scalarMultiply(double s) {
		return new StockVector3D(x*s, y*s, z*s);
	}


	@Override
	public Vector3D duplicate() {
		return new StockVector3D(x, y, z);
	}

	@Override
	public String toString() {
		return String.format("[%.3f, %.3f, %.3f]", x,y,z);
	}

}
