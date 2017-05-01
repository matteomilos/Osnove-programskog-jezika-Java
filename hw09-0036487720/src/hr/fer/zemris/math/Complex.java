package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

public class Complex {

	private double real;
	private double imaginary;

	public static final Complex ZERO = new Complex(0, 0);
	public static final Complex ONE = new Complex(1, 0);
	public static final Complex ONE_NEG = new Complex(-1, 0);
	public static final Complex IM = new Complex(0, 1);
	public static final Complex IM_NEG = new Complex(0, -1);

	public static class ComplexRootedPolynomial {

		private Complex[] roots;

		public ComplexRootedPolynomial(Complex... roots) {
			if (roots == null) {
				throw new IllegalArgumentException("Roots of complex number can't be null");
			}

			this.roots = roots;
		}

		public Complex apply(Complex z) {
			if (z == null) {
				throw new IllegalArgumentException("Given complex number can't be null");
			}

			if (roots.length == 0) {
				return ZERO;
			}

			Complex result = z.sub(roots[0]);
			for (int i = 1; i < roots.length; i++) {
				result = result.multiply(z.sub(roots[i]));
			}

			return result;
		}

		public ComplexPolynomial toComplexPolynom() {
			ComplexPolynomial result = new ComplexPolynomial(roots[0].negate(), Complex.ONE);
			for (int i = 1; i < roots.length; i++) {
				result = result.multiply(new ComplexPolynomial(roots[i].negate(), Complex.ONE));
			}
			return result;
		}

		@Override
		public String toString() {
			StringJoiner sj = new StringJoiner(")(", "(", ")");
			for (Complex complex : roots) {
				if (complex.module() == 0) {
					continue;
				}
				sj.add("z " + complex.toString());
			}
			return sj.toString();
		}

		public int indexOfClosestRootFor(Complex z, double threshold) {
			if (z == null) {
				throw new IllegalArgumentException("Given complex number can't be null.");
			}
			if (threshold <= 0) {
				throw new IllegalArgumentException("Threshold has to be integere greater than zero.");
			}

			double minDistance = z.sub(roots[0]).module();
			int minIndex = 0;
			for (int i = 1; i < roots.length; i++) {
				double distance = z.sub(roots[i]).module();
				if (distance < minDistance) {
					minDistance = distance;
					minIndex = i;
				}
			}
			return minDistance <= threshold ? minIndex + 1 : -1;
		}

	}

	public static class ComplexPolynomial {

		private Complex[] factors;

		public ComplexPolynomial(Complex... factors) {
			if (factors == null) {
				throw new IllegalArgumentException("Factors of complex number can't be null");
			}
			this.factors = factors;
		}

		public short order() {
			return (short) (factors.length - 1);
		}

		public ComplexPolynomial multiply(ComplexPolynomial p) {
			if (p == null) {
				throw new IllegalArgumentException("Given complex polynomial can't be null.");
			}

			Complex[] newFactors = new Complex[this.factors.length + p.factors.length - 1];
			Arrays.fill(newFactors, ZERO);

			for (int i = 0; i < factors.length; i++) {
				for (int j = 0; j < p.factors.length; j++) {

					newFactors[i + j] = newFactors[i + j].add(factors[i].multiply(factors[j]));
				}
			}

			return new ComplexPolynomial(newFactors);
		}

		public ComplexPolynomial derive() {
			Complex[] newFactors = new Complex[factors.length - 1];

			for (int i = 1; i < factors.length; i++) {
				newFactors[i - 1] = factors[i].multiply(new Complex(i, 0));
				System.out.println(newFactors[i - 1].imaginary);
			}

			return new ComplexPolynomial(newFactors);
		}

		public Complex apply(Complex z) {
			if (z == null) {
				throw new IllegalArgumentException("Given complex number can't be null");
			}

			Complex result = factors[0];

			for (int i = 1; i < factors.length; i++) {
				result = result.add(z.power(i).multiply(factors[i]));
			}

			return result;
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.insert(0, factors[0].toString());

			for (int i = 1; i < factors.length; i++) {
				if (factors[i].real != 0 && factors[i].imaginary != 0) {
					sb.insert(0, "(" + factors[i].toString() + ")z^" + i + "+");
				} else {
					sb.insert(0, factors[i].toString() + "z" + (i > 1 ? "^" + i : "") + "+");
				}
			}
			return sb.toString();
		}
	}

	/*
	 * public static void main(String[] args) { ComplexPolynomial nesto = new
	 * ComplexPolynomial(new Complex(1, 0), new Complex(5, 0), new Complex(2,
	 * 0), new Complex(7, 2)); System.out.println(nesto.order());
	 * System.out.println(nesto.derive()); }
	 */

	public Complex() {
		this(0, 0);
	}

	public Complex(double a, double b) {
		this.real = a;
		this.imaginary = b;
	}

	public double module() {
		return Math.sqrt(real * real + imaginary * imaginary);
	}

	public Complex multiply(Complex c) {
		if (c == null) {
			throw new IllegalArgumentException("Other complex number mustn't be null.");
		}

		double real = this.real * c.real - this.imaginary * c.imaginary;
		double imaginary = this.imaginary * c.real + this.real * c.imaginary;
		return new Complex(real, imaginary);
	}

	public Complex divide(Complex c) {
		if (c == null) {
			throw new IllegalArgumentException("Value given can not be null, it has to be complex number");
		}

		if (c.real == 0 && c.imaginary == 0) {
			throw new ArithmeticException("Division by zero is not allowed");
		}

		double divisor = c.real * c.real + c.imaginary * c.imaginary;
		double real = (this.real * c.real + this.imaginary * c.imaginary) / divisor;
		double imaginary = (this.imaginary * c.real - this.real * c.imaginary) / divisor;
		return new Complex(real, imaginary);
	}

	public Complex add(Complex c) {
		if (c == null) {
			throw new IllegalArgumentException("Other complex number mustn't be null.");
		}

		return new Complex(real + c.real, imaginary + c.imaginary);
	}

	public Complex sub(Complex c) {
		if (c == null) {
			throw new IllegalArgumentException("Other complex number mustn't be null.");
		}

		return new Complex(real - c.real, imaginary - c.imaginary);
	}

	public Complex negate() {
		return new Complex(0 - real, 0 - imaginary);
	}

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
		if (real != 0) {
			sb.append(String.format("%.0f", real));
		}
		if (imaginary != 0) {
			sb.append(String.format((imaginary >= 0 ? "+" : "") + "%.0fi", imaginary));
		}
		return sb.toString();
	}

	private double getAngle() {
		double angle = Math.atan2(imaginary, real);
		angle = angle >= 0 ? angle : 2 * Math.PI + angle;
		return angle;
	}

}
