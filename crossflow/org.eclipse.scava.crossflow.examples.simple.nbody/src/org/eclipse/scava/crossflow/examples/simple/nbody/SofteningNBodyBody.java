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

import java.time.Duration;
import java.util.Collection;
import java.util.UUID;

import org.apache.commons.math3.util.FastMath;

/**
 * The Class SofteningNBodyBody is an implementation of {@link NBody3DBody} that uses a softening
 * parameter to avoid the program crashing if bodies get too close together.
 * 
 * Bodies are uniquely identified via an UUID. This is required as the same star might be processed
 * to by different cuboids and, when collecting them after each step, we need to make sure we don't
 * add duplicates to the simulation.
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
	private final UUID id;
	
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
			this(position, velocity, null, mass);
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
		this(position, velocity, acceleration, mass, UUID.randomUUID());
	}

	public SofteningNBodyBody(Vector3D position, Vector3D velocity, Vector3D acceleration, double mass, UUID _id) {
		super();
		this.position = position;
		this.velocity = velocity;
		this.acceleration = acceleration;
		this.mass = mass;
		this.id = _id;
	}

	@Override
	public NBody3DBody prepare() {
		return new SofteningNBodyBody(
				position, velocity,
				new StockVector3D(0.0, 0.0, 0.0),
				mass, id);
	}
	
	@Override
	public NBody3DBody accelerate(Collection<? extends NBody3DBody> others) {
		Vector3D temp;
		Vector3D accel = acceleration.duplicate();
		for (NBody3DBody other : others) {
			if (other != this) {
				temp = position.subtract(other.position());
				double r2 = FastMath.sqrt(temp.normSq() + eps);
				double r2inv = 1.0 / r2;
				double r6inv = r2inv*r2inv*r2inv;
				double s = other.mass() * r6inv;
				accel = accel.add(temp.scalarMultiply(s));
			}
		}
		return new SofteningNBodyBody(
				position, velocity,
				accel,
				mass, id);
		
		/*
		 for (int i=0; i<other.length;i++) {
			Vector3D temp; 
			if (!other[i].equals(this)) {
				temp = position.subtract(other[i].position());
				double r2 = FastMath.sqrt(temp.normSq() + eps);
				double r2inv = 1.0 / r2;
				double r6inv = r2inv*r2inv*r2inv;
				double s = other[i].mass() * r6inv;
				acceleration.add(temp.scalarMultiply(s));
			}
			
		}
		return new SofteningArrayNBodyBody(
				position, velocity,
				acceleration,
				mass, id);
		 */
	}

	
	@Override
	public NBody3DBody updateVelocity(double dmp, double timeDelta) {
		return new SofteningNBodyBody(
				position,
				velocity.add(acceleration.scalarMultiply(timeDelta).scalarMultiply(dmp)),
				acceleration, mass, id);
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
				acceleration, mass, id);
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
		return (cube.coordinates().xmin() <= position.x() && position.x() <= cube.coordinates().xmax())
				&& (cube.coordinates().ymin() <= position.y() && position.y() <= cube.coordinates().ymax())
				&& (cube.coordinates().zmin() <= position.z() && position.z() <= cube.coordinates().zmax());
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		SofteningNBodyBody other = (SofteningNBodyBody) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public NBody3DBody accelerate(NBody3DBody... other) {
		// TODO Implement NBody3DBody.accelerate
		throw new RuntimeException("Unimplemented Method NBody3DBody.accelerate invoked.");
	}

}
