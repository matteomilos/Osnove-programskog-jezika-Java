package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class Complex is a representation of unmodifiable complex number and it's
 * basic operations.
 * 
 * @author Matteo Miloš
 */
public class Complex {

	/** The Constant ZERO. */
	public static final Complex ZERO = new Complex(0, 0);

	/** The Constant ONE. */
	public static final Complex ONE = new Complex(1, 0);

	/** The Constant ONE_NEG. */
	public static final Complex ONE_NEG = new Complex(-1, 0);

	/** The Constant IM. */
	public static final Complex IM = new Complex(0, 1);

	/** The Constant IM_NEG. */
	public static final Complex IM_NEG = new Complex(0, -1);

	/** The real part of complex number. */
	private double real;

	/** The imaginary part of complex number. */
	private double imaginary;

	/**
	 * Instantiates new complex number with default (0,0) values.
	 */
	public Complex() {
		this(0, 0);
	}

	/**
	 * 
	 * Instantiates new complex number with given values.
	 *
	 * @param real
	 *            real part of complex number
	 * @param imaginary
	 *            imaginary part of complex number
	 */
	public Complex(double real, double imaginary) {
		this.real = real;
		this.imaginary = imaginary;
	}

	/**
	 * Calculates module of complex number.
	 * 
	 * @return module
	 */
	public double module() {
		return Math.sqrt(getReal() * getReal() + getImaginary() * getImaginary());
	}

	/**
	 * Multiplies this complex number with given complex number. Returns new
	 * instance of {@link Complex}.
	 * 
	 * @param c
	 *            other complex number
	 * @return new instance of complex number
	 */
	public Complex multiply(Complex c) {
		if (c == null) {
			throw new IllegalArgumentException("Other complex number mustn't be null.");
		}

		double real = this.getReal() * c.getReal() - this.getImaginary() * c.getImaginary();
		double imaginary = this.getImaginary() * c.getReal() + this.getReal() * c.getImaginary();
		return new Complex(real, imaginary);
	}

	/**
	 * Divides this complex number with given complex number. Returns new
	 * instance of {@link Complex}.
	 * 
	 * @param c
	 *            other complex number
	 * @return new instance of complex number
	 */
	public Complex divide(Complex c) {
		if (c == null) {
			throw new IllegalArgumentException("Value given can not be null, it has to be complex number");
		}

		if (c.getReal() == 0 && c.getImaginary() == 0) {
			throw new ArithmeticException("Division by zero is not allowed");
		}

		double divisor = c.getReal() * c.getReal() + c.getImaginary() * c.getImaginary();
		double real = (this.getReal() * c.getReal() + this.getImaginary() * c.getImaginary()) / divisor;
		double imaginary = (this.getImaginary() * c.getReal() - this.getReal() * c.getImaginary()) / divisor;
		return new Complex(real, imaginary);
	}

	/**
	 * Adds this complex number to given complex number. Returns new instance of
	 * {@link Complex}.
	 * 
	 * @param c
	 *            other complex number
	 * @return new instance of complex number
	 */
	public Complex add(Complex c) {
		if (c == null) {
			throw new IllegalArgumentException("Other complex number mustn't be null.");
		}

		return new Complex(getReal() + c.getReal(), getImaginary() + c.getImaginary());
	}

	/**
	 * Substitutes this complex number from given complex number. Returns new
	 * instance of {@link Complex}.
	 * 
	 * @param c
	 *            other complex number
	 * @return new instance of complex number
	 */
	public Complex sub(Complex c) {
		if (c == null) {
			throw new IllegalArgumentException("Other complex number mustn't be null.");
		}

		return new Complex(getReal() - c.getReal(), getImaginary() - c.getImaginary());
	}

	/**
	 * Negates this complex number. Return new instance.
	 * 
	 * @return new instance of complex number
	 */
	public Complex negate() {
		return new Complex(-getReal(), -getImaginary());
	}

	/**
	 * Calculates power of this complex number if argument is valid.
	 * 
	 * @param n
	 *            power
	 * @return new instance of complex number
	 * @throws IllegalArgumentException
	 *             if given argument is less than zero
	 */
	public Complex power(int n) {
		if (n < 0) {
			throw new IllegalArgumentException("Complex number power has to be greater or equal than zero");
		}
		double oldMagnitude = this.module();
		double newMagnitude = Math.pow(oldMagnitude, n);
		double oldAngle = this.getAngle();
		double newAngle = oldAngle * n;

		return new Complex(newMagnitude * Math.cos(newAngle), newMagnitude * Math.sin(newAngle));
	}

	/**
	 * Calculates list of roots of this complex number if argument is valid.
	 * 
	 * @param n
	 *            root
	 * @return list of roots
	 * @throws IllegalArgumentException
	 *             if given argument is less or equal than zero
	 */
	public List<Complex> root(int n) {
		if (n <= 0) {
			throw new IllegalArgumentException("Complex number root has to be greater than zero");
		}

		double magnitude = Math.pow(this.module(), 1. / n);
		double angle = this.getAngle() / n;

		List<Complex> roots = new ArrayList<>();

		for (int i = 0; i < n; i++) {
			double real = magnitude * Math.cos(angle + (2 * i * Math.PI) / n);
			double imaginary = magnitude * Math.sin(angle + (2 * i * Math.PI) / n);
			roots.add(new Complex(real, imaginary));
		}

		return roots;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (getReal() != 0) {
			sb.append(String.format("%.0f", getReal()));
		}
		if (getImaginary() != 0) {
			sb.append(String.format((getImaginary() >= 0 ? "+" : "") + "%fi", getImaginary()));
		}
		return sb.toString();
	}

	/**
	 * Private getter method for angle of complex number
	 * 
	 * @return angle of complex number
	 */
	public double getAngle() {
		double angle = Math.atan2(getImaginary(), getReal());
		return angle;
	}

	/**
	 * Public getter method for imaginary part of complex number
	 * 
	 * @return imaginary part of complex number
	 */
	public double getImaginary() {
		return imaginary;
	}

	/**
	 * Public getter method for real part of complex number
	 * 
	 * @return real part of complex number
	 */
	public double getReal() {
		return real;
	}

}
