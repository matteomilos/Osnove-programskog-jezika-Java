package hr.fer.zemris.math;

import java.util.StringJoiner;

/**
 * The Class ComplexRootedPolynomial is representation of unmodifiable complex
 * polynomial by it's roots.
 * 
 * @author Matteo Miloš
 */
public class ComplexRootedPolynomial {

	/** The roots of polynomial. */
	private Complex[] roots;

	/**
	 * Instantiates new rooted complex polynomial.
	 * 
	 * @param roots
	 *            roots of polynomial
	 */
	public ComplexRootedPolynomial(Complex... roots) {
		if (roots == null) {
			throw new IllegalArgumentException("Roots of complex number can't be null");
		}

		this.roots = roots;
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

		if (roots.length == 0) {
			return Complex.ZERO;
		}

		Complex result = z.sub(roots[0]);
		for (int i = 1; i < roots.length; i++) {
			result = result.multiply(z.sub(roots[i]));
		}

		return result;
	}

	/**
	 * Converts this representation to {@link ComplexPolynomial} type
	 * 
	 * @return new instance of {@link ComplexPolynomial}
	 */
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

	/**
	 * finds index of closest root for given complex number z that is within
	 * threshold; if there is no such root, returns -1
	 * 
	 * @param z
	 *            complex number
	 * @param threshold
	 *            the threshold
	 * @return index of closest root
	 * @throws IllegalArgumentException
	 *             if given complex number is null or threshold is less or equal
	 *             than zero
	 */
	public int indexOfClosestRootFor(Complex z, double threshold) {
		if (z == null) {
			throw new IllegalArgumentException("Given complex number can't be null.");
		}
		if (threshold <= 0) {
			throw new IllegalArgumentException("Threshold has to be integer greater than zero.");
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