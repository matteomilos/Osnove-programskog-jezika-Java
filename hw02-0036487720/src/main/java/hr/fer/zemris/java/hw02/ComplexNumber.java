package hr.fer.zemris.java.hw02;

/**
 * Class <code>ComplexNumber</code> represents unmodifiable instance of complex
 * number. Class owns methods for creating different types of complex numbers
 * and for mathematical operations between complex numbers.
 * 
 * @author Matteo Miloš
 *
 */
public class ComplexNumber {

	/**
	 * Constant used in method <code>equals</code> as an "allowed" difference
	 * between two complex numbers to still look at them as same numbers.
	 */
	public static final double EPSILON = 1e-10;
	/**
	 * Real part of complex number.
	 */
	private double real;
	/**
	 * Imaginary part of complex number.
	 */
	private double imaginary;

	/**
	 * Public constructor that that creates complex number with given real and
	 * imaginary parts.
	 * 
	 * @param real
	 *            Real part of complex number
	 * @param imaginary
	 *            Imaginary part of complex number
	 */
	public ComplexNumber(double real, double imaginary) {
		this.real = real;
		this.imaginary = imaginary;
	}

	/**
	 * Static factory method that creates complex number from given real part of
	 * complex number. Imaginary part is set to 0.
	 * 
	 * @param real
	 *            Real part of complex number
	 * @return reference to new instance of class <code>ComplexNumber</code>
	 */
	public static ComplexNumber fromReal(double real) {
		return new ComplexNumber(real, 0);
	}

	/**
	 * Static factory method that creates complex number from given imaginary
	 * part of complex number. Real part is set to 0.
	 * 
	 * @param imaginary
	 *            Imaginary part of complex number
	 * @return reference to new instance of class <code>ComplexNumber</code>
	 */
	public static ComplexNumber fromImaginary(double imaginary) {
		return new ComplexNumber(0, imaginary);
	}

	/**
	 * Static factory method that creates complex number from given magnitude
	 * and angle of complex number.
	 * 
	 * @param magnitude
	 *            Magnitude of complex number
	 * @param angle
	 *            Angle of complex number
	 * @return reference to new instance of class <code>ComplexNumber</code>
	 */
	public static ComplexNumber fromMagnitudeAndAngle(double magnitude, double angle) {
		return new ComplexNumber(magnitude * Math.cos(angle), magnitude * Math.sin(angle));
	}

	/**
	 * Static factory method that creates complex number from given string.
	 * String has to be in one of the specified formats which are: "3.14",
	 * "-3.72", "-2.47i", "i", "1", "-2.77+5.82i".
	 * 
	 * @param s
	 *            string representing complex number
	 * @return reference to new instance of class <code>ComplexNumber</code>
	 */
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

	/**
	 * Private static method that is used as helper method, if imaginary number
	 * is in one of there formats: "i", "-i", "+i", it turns it into these
	 * formats respectively: "1", "-1", "+1", and then parses those strings to
	 * double values
	 * 
	 * @param imaginary
	 *            String representing imaginary part of complex number
	 * @return imaginary part of complex number parsed to double
	 */
	protected static double findImaginary(String imaginary) {

		imaginary = imaginary.substring(0, imaginary.length() - 1);

		if (imaginary.matches("[+| -]") || imaginary.isEmpty()) {
			imaginary += 1;
		}

		return Double.parseDouble(imaginary);
	}

	/**
	 * Getter method of real part of complex number.
	 * 
	 * @return real part of complex number
	 */
	public double getReal() {
		return this.real;
	}

	/**
	 * Getter method of imaginary part of complex number.
	 * 
	 * @return imaginary part of complex number
	 */
	public double getImaginary() {
		return this.imaginary;
	}

	/**
	 * Method that calculates magnitude(radius) of complex number.
	 * 
	 * @return magnitude of complex number
	 */
	public double getMagnitude() {
		return Math.sqrt(real * real + imaginary * imaginary);
	}

	/**
	 * Method that calculates angle of complex number.
	 * 
	 * @return angle of complex number
	 */
	public double getAngle() {
		double angle = Math.atan2(imaginary, real);
		angle = angle >= 0 ? angle : 2 * Math.PI + angle;
		return angle;
	}

	/**
	 * Method that adds two complex numbers, this <code>ComplexNumber</code> and
	 * <code>ComplexNumber</code> given as argument of the method.
	 * 
	 * @param c
	 *            Complex number that will be added to current complex number
	 * @return sum of two complex numbers
	 * @throws NullPointerException
	 *             if <code>null</code> is given as an argument
	 */
	public ComplexNumber add(ComplexNumber c) {

		if (c == null) {
			throw new NullPointerException("Value given can not be null, it has to be complex number");
		}

		return new ComplexNumber(c.real + this.real, c.imaginary + this.imaginary);
	}

	/**
	 * Method that subtracts two complex numbers, this
	 * <code>ComplexNumber</code> and <code>ComplexNumber</code> given as
	 * argument of the method.
	 * 
	 * @param c
	 *            Complex number that will be subtracted from current complex
	 *            number
	 * @return difference of two complex numbers
	 * @throws NullPointerException
	 *             if <code>null</code> is given as an argument
	 */
	public ComplexNumber sub(ComplexNumber c) {

		if (c == null) {
			throw new NullPointerException("Value given can not be null, it has to be complex number");
		}

		return new ComplexNumber(this.real - c.real, this.imaginary - c.imaginary);
	}

	/**
	 * Method that multiplies two complex numbers, this
	 * <code>ComplexNumber</code> and <code>ComplexNumber</code> given as
	 * argument of the method.
	 * 
	 * @param c
	 *            Complex number that will be multiplied by current complex
	 *            number
	 * @return result of multiplication of two complex numbers
	 * @throws NullPointerException
	 *             if <code>null</code> is given as an argument
	 */
	public ComplexNumber mul(ComplexNumber c) {

		if (c == null) {
			throw new NullPointerException("Value given can not be null, it has to be complex number");
		}

		double real = this.real * c.real - this.imaginary * c.imaginary;
		double imaginary = this.imaginary * c.real + this.real * c.imaginary;
		return new ComplexNumber(real, imaginary);
	}

	/**
	 * Method that divides two complex numbers, this <code>ComplexNumber</code>
	 * and <code>ComplexNumber</code> given as argument of the method.
	 * 
	 * @param c
	 *            divisor of the current complex number
	 * @return result of division of two complex numbers
	 * @throws NullPointerException
	 *             if <code>null</code> is given as an argument
	 * @throws ArithmeticException
	 *             if given argument is zero
	 */
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

	/**
	 * Method that calculates this <code>ComplexNumber</code> to the passed
	 * power.
	 * 
	 * @param n
	 *            The power which we want to calculate
	 * @return result of powering complex number
	 * 
	 * @throws IllegalArgumentException
	 *             if passed power is negative number
	 */
	public ComplexNumber power(int n) {

		if (n < 0) {
			throw new IllegalArgumentException();
		}

		double magnitude = Math.pow(this.getMagnitude(), n);
		double angle = n * this.getAngle();
		return fromMagnitudeAndAngle(magnitude, angle);
	}

	/**
	 * Method that calculates n-th root of this <code>ComplexNumber</code>
	 * 
	 * @param n
	 *            The root which we want to calculate
	 * @return array representing result of rooting complex number
	 * 
	 * @throws IllegalArgumentException
	 *             if passed power is non positive number
	 */
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

	/**
	 * String formatted complex number in format as: realPart + imaginaryPart
	 */
	@Override
	public String toString() {
		return String.format("%.2f" + (imaginary > 0 ? "+" : "") + "%.2fi", real, imaginary);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(imaginary);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(real);
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
		ComplexNumber other = (ComplexNumber) obj;
		if (Math.abs(imaginary - other.imaginary) > EPSILON)
			return false;
		if (Math.abs(real - other.real) > EPSILON)
			return false;
		return true;
	}

}
