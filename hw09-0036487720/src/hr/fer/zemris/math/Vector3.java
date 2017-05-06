package hr.fer.zemris.math;

/**
 * Class that models immutable 3-component vector. All operations on vector
 * return new instance of this type which represent result of the calculation.
 * 
 * @author Matteo Miloš
 *
 */
public class Vector3 {

	/** The x. */
	private double x;

	/** The y. */
	private double y;

	/** The z. */
	private double z;

	/**
	 * Method where program starts running.
	 * 
	 * @param args
	 *            command line arguments, not used in this program
	 */
	public static void main(String[] args) {
		Vector3 i = new Vector3(1, 0, 0);
		Vector3 j = new Vector3(0, 1, 0);
		Vector3 k = i.cross(j);
		Vector3 l = k.add(j).scale(5);
		Vector3 m = l.normalized();
		System.out.println(i);
		System.out.println(j);
		System.out.println(k);
		System.out.println(l);
		System.out.println(l.norm());
		System.out.println(m);
		System.out.println(l.dot(j));
		System.out.println(i.add(new Vector3(0, 1, 0)).cosAngle(l));

	}

	/**
	 * Creates new instance of this class with given values.
	 * 
	 * @param x
	 *            x-component
	 * @param y
	 *            y-component
	 * @param z
	 *            z-component
	 */
	public Vector3(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * Calculates length of the vector.
	 *
	 * @return length
	 */
	public double norm() {
		return Math.sqrt(x * x + y * y + z * z);
	}

	/**
	 * Calculates normalized version of this vector.
	 *
	 * @return new instance of {@link Vector3}
	 */
	public Vector3 normalized() {
		double norm = this.norm();

		return new Vector3(x / norm, y / norm, z / norm);
	}

	/**
	 * Adds this vector to given vector. Returns new instance of
	 * {@link Vector3}.
	 * 
	 * @param other
	 *            other vector
	 * @return new instance of complex vector
	 */
	public Vector3 add(Vector3 other) {
		if (other == null) {
			throw new IllegalArgumentException("Other vector mustn't be null.");
		}

		return new Vector3(x + other.x, y + other.y, z + other.z);
	}

	/**
	 * Substitutes this vector from given vector. Returns new instance of
	 * {@link Vector3}.
	 * 
	 * @param other
	 *            other vector
	 * @return new instance of complex vector
	 */
	public Vector3 sub(Vector3 other) {
		if (other == null) {
			throw new IllegalArgumentException("Other vector mustn't be null.");
		}

		return new Vector3(x - other.x, y - other.y, z - other.z);
	}

	/**
	 * Calculates scalar product of this vector and given vector.
	 * 
	 * @param other
	 *            other vector
	 * @return scalar product
	 */
	public double dot(Vector3 other) {
		if (other == null) {
			throw new IllegalArgumentException("Other vector mustn't be null.");
		}

		return (x * other.x) + (y * other.y) + (z * other.z);
	}

	/**
	 * Calculates cross product of this vector and given vector.
	 * 
	 * @param other
	 *            other vector
	 * @return cross product
	 */
	public Vector3 cross(Vector3 other) {
		if (other == null) {
			throw new IllegalArgumentException("Other vector mustn't be null.");
		}

		return new Vector3(y * other.z - z * other.y, z * other.x - x * other.z, x * other.y - y * other.x);
	}

	/**
	 * Calculates cosines of an angle between this vector and given vector.
	 *
	 * @param other
	 *            given vector
	 * @return cosines of angle
	 */
	public double cosAngle(Vector3 other) {
		if (other == null) {
			throw new IllegalArgumentException("Other vector mustn't be null.");
		}

		return this.dot(other) / (this.norm() * other.norm());
	}

	/**
	 * Scales this vector with given parameter.
	 *
	 * @param s
	 *            scaling factor
	 * @return scaled vector
	 */
	public Vector3 scale(double s) {
		return new Vector3(x * s, y * s, z * s);

	}

	/**
	 * Public getter for x-component of vector.
	 *
	 * @return x-component
	 */
	public double getX() {
		return x;
	}

	/**
	 * Public getter for y-component of vector.
	 *
	 * @return y-component
	 */
	public double getY() {
		return y;
	}

	/**
	 * Public getter for z-component of vector.
	 *
	 * @return z-component
	 */
	public double getZ() {
		return z;
	}

	/**
	 * Returns array representation of this vector.
	 *
	 * @return array of vector components
	 */
	public double[] toArray() {
		return new double[] { x, y, z };
	}

	@Override
	public String toString() {
		return String.format("(%.6f, %.6f, %.6f)", x, y, z);
	}

}
