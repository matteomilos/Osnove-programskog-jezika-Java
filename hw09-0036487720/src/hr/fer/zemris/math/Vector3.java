package hr.fer.zemris.math;

import java.util.Arrays;
import java.util.StringJoiner;

public class Vector3 {

	double x;
	double y;
	double z;

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

	public Vector3(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public double norm() {
		return Math.sqrt(x * x + y * y + z * z);
	}

	public Vector3 normalized() {
		double norm = this.norm();

		return new Vector3(x / norm, y / norm, z / norm);
	}

	public Vector3 add(Vector3 other) {
		if (other == null) {
			throw new IllegalArgumentException("Other vector mustn't be null.");
		}

		return new Vector3(x + other.x, y + other.y, z + other.z);
	}

	public Vector3 sub(Vector3 other) {
		if (other == null) {
			throw new IllegalArgumentException("Other vector mustn't be null.");
		}

		return new Vector3(x - other.x, y - other.y, z - other.z);
	}

	public double dot(Vector3 other) {
		if (other == null) {
			throw new IllegalArgumentException("Other vector mustn't be null.");
		}

		return (x * other.x) + (y * other.y) + (z * other.z);
	}

	public Vector3 cross(Vector3 other) {
		if (other == null) {
			throw new IllegalArgumentException("Other vector mustn't be null.");
		}

		return new Vector3(y * other.z - z * other.y, z * other.x - x * other.z, x * other.y - y * other.x);
	}

	public double cosAngle(Vector3 other) {
		if (other == null) {
			throw new IllegalArgumentException("Other vector mustn't be null.");
		}

		return this.dot(other) / (this.norm() * other.norm());
	}

	public Vector3 scale(double s) {
		return new Vector3(x * s, y * s, z * s);

	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getZ() {
		return z;
	}

	public double[] toArray() {
		return new double[] { x, y, z };
	}

	@Override
	public String toString() {
		return String.format("(%.6f, %.6f, %.6f)", x, y, z);
	}

}
