package hr.fer.zemris.math;

import java.util.Arrays;

/**
 * The Class ComplexPolynomial is used to represent unmodifiable polynomial with
 * complex factors shaped : x^n*(zn)+ x^(n-1)*(zn-1)+...z0
 * 
 * @author Matteo Miloš
 */
public class ComplexPolynomial {

	/** The factors of a polynomial. */
	private Complex[] factors;

	/**
	 * Instantiates new complex polynomial.
	 * 
	 * @param factors
	 *            factors of polynomial
	 */
	public ComplexPolynomial(Complex... factors) {
		if (factors == null) {
			throw new IllegalArgumentException("Factors of complex number can't be null");
		}
		this.factors = factors.clone();
	}

	/**
	 * Returns order of polynomial.
	 *
	 * @return order of polynomial
	 */
	public short order() {
		return (short) (factors.length - 1);
	}

	/**
	 * Multiplies this polynomial number with given polynomial number. Returns
	 * new instance of {@link ComplexPolynomial}.
	 * 
	 * @param p
	 *            other polynomial
	 * @return new instance of complex polynomial
	 * @throws IllegalArgumentException
	 *             if other polynomial is null
	 */
	public ComplexPolynomial multiply(ComplexPolynomial p) {
		if (p == null) {
			throw new IllegalArgumentException("Given complex polynomial can't be null.");
		}

		Complex[] newFactors = new Complex[this.factors.length + p.factors.length - 1];
		Arrays.fill(newFactors, Complex.ZERO);

		for (int i = 0; i < factors.length; i++) {
			for (int j = 0; j < p.factors.length; j++) {

				newFactors[i + j] = newFactors[i + j].add(factors[i].multiply(p.factors[j]));
			}
		}

		return new ComplexPolynomial(newFactors);
	}

	/**
	 * Derives this polynomial number . Returns new instance of
	 * {@link ComplexPolynomial}.
	 * 
	 * @return new instance of complex polynomial
	 */
	public ComplexPolynomial derive() {
		Complex[] newFactors = new Complex[factors.length - 1];

		for (int i = 1; i < factors.length; i++) {
			newFactors[i - 1] = factors[i].multiply(new Complex(i, 0));
		}

		return new ComplexPolynomial(newFactors);
	}

	/**
	 * Computes polynomial value at given point z and returns it.
	 *
	 * @param z
	 *            point
	 * @return the complex
	 */
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
			if (factors[i].getReal() != 0 && factors[i].getImaginary() != 0) {
				sb.insert(0, "(" + factors[i].toString() + ")z^" + i + "+");
			} else {
				sb.insert(0, factors[i].toString() + "z" + (i > 1 ? "^" + i : "") + "+");
			}
		}
		return sb.toString();
	}
}