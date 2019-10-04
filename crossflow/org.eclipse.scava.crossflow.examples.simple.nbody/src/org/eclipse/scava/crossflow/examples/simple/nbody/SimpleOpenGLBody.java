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

import java.awt.Color;
import java.util.Collection;

/**
 * The Class SimpleOpenGLBody is an implementation of {@link OpenGLNBody3DBody} that delegates all
 * body simulation to a {@link NBody3DBody}. The class provides additional fields to presist the
 * color information.
 */
public class SimpleOpenGLBody implements OpenGLNBody3DBody {

	private static final long serialVersionUID = 9067722749307681300L;
	private final Color color;
	private final NBody3DBody delegate;
	
	public SimpleOpenGLBody(
		Vector3D position,
		Vector3D velocity,
		double mass,
		float red,
		float green,
		float blue) {
		super();
		this.delegate = new SofteningNBodyBody(position, velocity, mass);
		this.color = new Color(red, green, blue);
	}
	
	private SimpleOpenGLBody(Color color, NBody3DBody delegate) {
		super();
		this.color = color;
		this.delegate = delegate;
	}

	public NBody3DBody prepare() {
		return new SimpleOpenGLBody(color, delegate.prepare());
	}
	
	public NBody3DBody accelerate(Collection<? extends NBody3DBody> other) {
		return new SimpleOpenGLBody(color, delegate.accelerate(other));
	}
	
	public Vector3D position() {
		return delegate.position();
	}
	
	public double mass() {
		return delegate.mass();
	}
	
	@Override
	public float[] getColor() {
		return color.getColorComponents(null);
	}

	@Override
	public NBody3DBody updateVelocity(double dmp, double timeDelta) {
		return new SimpleOpenGLBody(color, delegate.updateVelocity(dmp, timeDelta));
	}

	@Override
	public NBody3DBody updatePostion(double timeDelta) {
		return new SimpleOpenGLBody(color, delegate.updatePostion(timeDelta));
	}

	@Override
	public boolean insideCube(NBodyCuboid cube) {
		return delegate.insideCube(cube);
	}

	public NBody3DBody accelerate(NBody3DBody... other) {
		return delegate.accelerate(other);
	}

	@Override
	public Vector3D velocity() {
		return delegate.velocity();
	}

	@Override
	public Vector3D accel() {
		return delegate.accel();
	}
	
}
