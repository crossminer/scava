package org.eclipse.scava.crossflow.examples.simple.nbody;

/**
 * Represent a vector in a 3d space
 * @author Horacio Hoyos Rodriguez
 *
 */
public interface Vector3D {
	
	/**
	 * The x coordinate of the vector
	 * 
	 * @return x coordinate
	 */
	double x();
	
	/**
	 * The y coordinate of the vector
	 * 
	 * @return y coordinate
	 */
	
	double y();
	
	/**
	 * The z coordinate of the vector
	 * 
	 * @return z coordinate
	 */
	
	double z();
	
	/**
	 * Calculates the the Euclidean norm of the vector, which gives the ordinary distance from the
	 * origin to the vector, a consequence of the Pythagorean theorem. This operation may also be
	 * referred to as "SRSS" which is an acronym for the square root of the sum of squares.[2]
	 * 
	 * @return the Euclidean norm
	 */
	double norm();
	
	/**
	 * Get the square of the norm for the vector.
	 * 
	 * @see Vector3D#norm()
	 * @return	the square of the Euclidean norm
	 */
	double normSq();
	
	/**
	 * Retrun a copy of the vector
	 * @return a new vector
	 */
	Vector3D duplicate();
	
	/**
	 * Add a vector to the instance.
	 * @param other				vector to add
	 * @return a new vector
	 */
	Vector3D add(Vector3D other);
	
	/**
	 * Subtract a vector from the instance.
	 * @param other					vector to substract
	 * @return a new vector
	 */
	Vector3D subtract(Vector3D other);
	
	/**
	 * Multiply the instance by a scalar.
	 * @param s						the scalar
	 * @return a new vector
	 */
	Vector3D scalarMultiply(double s);
	
}
