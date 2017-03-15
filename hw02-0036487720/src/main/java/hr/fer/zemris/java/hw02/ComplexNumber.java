package hr.fer.zemris.java.hw02;

public class ComplexNumber {

	private double real;
	private double imaginary;

	public ComplexNumber(double real, double imaginary) {
		this.real = real;
		this.imaginary = imaginary;
	}

	public static ComplexNumber fromReal(double real) {
		return new ComplexNumber(real, 0);
	}

	public static ComplexNumber fromImaginary(double imaginary) {
		return new ComplexNumber(0, imaginary);
	}

	public static ComplexNumber fromMagnitudeAndAngle(double magnitude, double angle) {
		return new ComplexNumber(magnitude * Math.cos(angle), magnitude * Math.sin(angle));
	}

	public static ComplexNumber parse(String s) {

		String[] arguments = s.split("(?=[-+])");

		if (arguments.length == 2) {

			double real = Double.parseDouble(arguments[0]);
			double imaginary = findImaginary(arguments[1]);
			return new ComplexNumber(real, imaginary);

		} else {

			try {
				double real = Double.parseDouble(arguments[0]);
				return fromReal(real);

			} catch (NumberFormatException exc) {

				double imaginary = findImaginary(arguments[0]);
				return fromImaginary(imaginary);
			}
		}
	}

	public static double findImaginary(String imaginary) {

		imaginary = imaginary.substring(0, imaginary.length() - 1);

		if (imaginary.matches("[+| -]") || imaginary.isEmpty()) {
			imaginary += 1;
		}

		return Double.parseDouble(imaginary);
	}

	public double getReal() {
		return this.real;
	}

	public double getImaginary() {
		return this.imaginary;
	}

	public double getMagnitude() {
		return Math.sqrt(real * real + imaginary * imaginary);
	}

	public double getAngle() {
		double angle = Math.atan2(imaginary, real);
		angle = angle >= 0 ? angle : 2 * Math.PI + angle;
		return angle;
	}

	public ComplexNumber add(ComplexNumber c) {

		if (c == null) {
			throw new NullPointerException("Value given can not be null, it has to be complex number");
		}

		return new ComplexNumber(c.real + this.real, c.imaginary + this.imaginary);
	}

	public ComplexNumber sub(ComplexNumber c) {

		if (c == null) {
			throw new NullPointerException("Value given can not be null, it has to be complex number");
		}

		return new ComplexNumber(this.real - c.real, this.imaginary - c.imaginary);
	}

	public ComplexNumber mul(ComplexNumber c) {

		if (c == null) {
			throw new NullPointerException("Value given can not be null, it has to be complex number");
		}

		double real = this.real * c.real - this.imaginary * c.imaginary;
		double imaginary = this.imaginary * c.real + this.real * c.imaginary;
		return new ComplexNumber(real, imaginary);
	}

	public ComplexNumber div(ComplexNumber c) {

		if (c == null) {
			throw new NullPointerException("Value given can not be null, it has to be complex number");
		}

		if (c.real == 0 && c.imaginary == 0) {
			throw new ArithmeticException("Division by zero is not allowed");
		}

		double divisor = c.real * c.real + c.imaginary * c.imaginary;
		double real = (this.real * c.real + this.imaginary * c.imaginary) / divisor;
		double imaginary = (this.imaginary * c.real - this.real * c.imaginary) / divisor;
		return new ComplexNumber(real, imaginary);
	}

	public ComplexNumber power(int n) {

		if (n < 0) {
			throw new IllegalArgumentException();
		}

		double magnitude = Math.pow(this.getMagnitude(), n);
		double angle = n * this.getAngle();
		return fromMagnitudeAndAngle(magnitude, angle);
	}

	public ComplexNumber[] root(int n) {

		if (n <= 0) {
			throw new IllegalArgumentException();
		}

		double magnitude = Math.pow(this.getMagnitude(), 1. / n);
		double angle = this.getAngle() / n;

		ComplexNumber[] roots = new ComplexNumber[n];

		for (int i = 0; i < n; i++) {
			double real = magnitude * Math.cos(angle + (2 * i * Math.PI) / n);
			double imaginary = magnitude * Math.sin(angle + (2 * i * Math.PI) / n);
			roots[i] = new ComplexNumber(real, imaginary);
		}

		return roots;
	}

	@Override
	public String toString() {
		return String.format("%.2f" + (imaginary > 0 ? "+" : "") + "%.2fi", real, imaginary);
	}

}
