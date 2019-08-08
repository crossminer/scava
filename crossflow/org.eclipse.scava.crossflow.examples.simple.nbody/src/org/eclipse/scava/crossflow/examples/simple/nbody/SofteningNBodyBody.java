/*********************************************************************
* Copyright (c) 2019 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* Contributors:
*     Horacio Hoyos - initial API and implementation
**********************************************************************/
package org.eclipse.scava.crossflow.examples.simple.nbody;

import java.util.Collection;

import org.apache.commons.math3.util.FastMath;

/**
 * The Class SofteningNBodyBody is an implementation of {@link NBody3DBody} that uses a softening
 * parameter to avoid the program crashing if bodies get too close together.
 * 
 * @author Horacio Hoyos Rodriguez
 */
public class SofteningNBodyBody implements NBody3DBody {
	
	/**
	 * The parameter eps is the minimum distance squared for particle-particle interactions and its
	 * main purpose is to keep the simulation from crashing if particles wander too close together.
	 */
	private final double eps = 0.00125;
	
	private final Vector3D position;
	private final Vector3D velocity;
	private final double mass;
	transient private final Vector3D acceleration;
	
	/**
	 * Instantiates a new softening N body body.
	 *
	 * @param position the position
	 * @param velocity the velocity
	 * @param mass the mass
	 */
	public SofteningNBodyBody(
			Vector3D position,
			Vector3D velocity,
			double mass) {
			super();
			this.position = position;
			this.velocity = velocity;
			this.acceleration = null;
			this.mass = mass;
		}
	
	/**
	 * Instantiates a new softening N body body.
	 *
	 * @param position the position
	 * @param velocity the velocity
	 * @param acceleration the acceleration
	 * @param mass the mass
	 */
	private SofteningNBodyBody(
		Vector3D position,
		Vector3D velocity,
		Vector3D acceleration,
		double mass) {
		super();
		this.position = position;
		this.velocity = velocity;
		this.acceleration = acceleration;
		this.mass = mass;
	}

	@Override
	public NBody3DBody prepare() {
		return new SofteningNBodyBody(position, velocity, new StockVector3D(0.0, 0.0, 0.0), mass);
	}
	
	@Override
	public NBody3DBody accelerate(Collection<? extends NBody3DBody> others) {
		NBody3DBody result = this;
		for (NBody3DBody other : others) {
			if (other != this) {
				Vector3D temp = position.duplicate();
				temp = temp.subtract(other.position());
				double r2 = FastMath.sqrt(temp.normSq() + eps);
				double r2inv = 1.0 / r2;
				double r6inv = r2inv*r2inv*r2inv;
				double s = other.mass() * r6inv;
				result = new SofteningNBodyBody(
						position, velocity,
						acceleration.add(temp.scalarMultiply(s)),
						mass);
			}
		}
		return result;
	}

	
	@Override
	public NBody3DBody updateVelocity(double dmp, double timeDelta) {
		return new SofteningNBodyBody(
				position,
				velocity.add(acceleration.scalarMultiply(timeDelta).scalarMultiply(dmp)),
				acceleration, mass);
	}
	
	@Override
	public NBody3DBody updatePostion(double timeDelta) {
		Vector3D newposition = position.add(velocity.scalarMultiply(timeDelta));
		double vx = (newposition.x() >= 1.0 || newposition.x() >= 1.0) ? -1 : 1;
		double vy = (newposition.y() >= 1.0 || newposition.y() >= 1.0) ? -1 : 1;
		double vz = (newposition.z() >= 1.0 || newposition.z() >= 1.0) ? -1 : 1;
		return new SofteningNBodyBody(
				newposition,
				new StockVector3D(velocity.x()*vx, velocity.y()*vy, velocity.z()*vz),
				acceleration, mass);
	}
	

	@Override
	public Vector3D position() {
		return position;
	}

	@Override
	public double mass() {
		return mass;
	}

	@Override
	public boolean insideCube(NBodyCuboid cube) {
		return (cube.xmin() <= position.x() && position.x() <= cube.xmax())
				&& (cube.ymin() <= position.y() && position.y() <= cube.ymax())
				&& (cube.zmin() <= position.z() && position.z() <= cube.zmax());
	}

	@Override
	public String toString() {
		if (acceleration == null) {
			return String.format("p=%s,v=%s,m=%.3f,a=[0,0,0]", position, velocity,mass);
		}
		else {
			return String.format("p=%s,v=%s,m=%.3f,a=%s", position, velocity,mass, acceleration);
		}
	}
	
	
}
