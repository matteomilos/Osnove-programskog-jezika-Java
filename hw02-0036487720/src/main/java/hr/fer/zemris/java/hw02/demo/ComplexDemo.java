package hr.fer.zemris.java.hw02.demo;

import hr.fer.zemris.java.hw02.ComplexNumber;

/**
 * Demonstration class used for checking functionalities of class
 * <code>ComplexNumber</code>
 * 
 * @author Matteo Miloš
 *
 */
public class ComplexDemo {

	/**
	 * Method being called when program is executed.
	 * 
	 * @param args
	 *            command line arguments, not used here
	 */
	public static void main(String[] args) {

		ComplexNumber c1 = new ComplexNumber(2, 3);
		ComplexNumber c2 = ComplexNumber.parse("2.5-3i");

		ComplexNumber c3 = c1.add(ComplexNumber.fromMagnitudeAndAngle(2, 1.57)).div(c2).power(3).root(2)[1];

		System.out.println(c3);
	}

}
