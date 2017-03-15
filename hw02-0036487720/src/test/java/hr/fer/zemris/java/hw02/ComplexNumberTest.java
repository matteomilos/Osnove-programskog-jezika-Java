package hr.fer.zemris.java.hw02;

import static hr.fer.zemris.java.hw02.ComplexNumber.findImaginary;
import static hr.fer.zemris.java.hw02.ComplexNumber.fromImaginary;
import static hr.fer.zemris.java.hw02.ComplexNumber.fromMagnitudeAndAngle;
import static hr.fer.zemris.java.hw02.ComplexNumber.fromReal;
import static hr.fer.zemris.java.hw02.ComplexNumber.parse;
import static org.junit.Assert.*;

import org.junit.Test;

public class ComplexNumberTest {

	@Test
	public void testFromRealIfRealIsZero() {
		ComplexNumber complex = fromReal(0);
		assertEquals(0, complex.getReal(), 1e-10);
		assertEquals(0, complex.getImaginary(), 1e-10);
	}

	public void testFromRealIfRealIsDifferentThanZero() {
		ComplexNumber complex = fromReal(-4);
		assertEquals(-4, complex.getReal(), 1e-10);
		assertEquals(0, complex.getImaginary(), 1e-10);
	}

	@Test
	public void testFromImaginaryIfImaginaryIsZero() {
		ComplexNumber complex = fromImaginary(0);
		assertEquals(0, complex.getReal(), 1e-10);
		assertEquals(0, complex.getImaginary(), 1e-10);
	}

	@Test
	public void testFromImaginaryIfImaginaryIsDifferentThanZero() {
		ComplexNumber complex = fromImaginary(4);
		assertEquals(0, complex.getReal(), 1e-10);
		assertEquals(4, complex.getImaginary(), 1e-10);
	}

	@Test
	public void testParseOnlyImaginary() {
		ComplexNumber complex = parse("i");
		assertEquals(0, complex.getReal(), 1e-10);
		assertEquals(1, complex.getImaginary(), 1e-10);
	}

	@Test
	public void testParseOnlyReal() {
		ComplexNumber complex = parse("3");
		assertEquals(3, complex.getReal(), 1e-10);
		assertEquals(0, complex.getImaginary(), 1e-10);
	}

	@Test
	public void testParseImaginaryFirstQuadrant() {
		ComplexNumber complex = parse("3+2i");
		assertEquals(3, complex.getReal(), 1e-10);
		assertEquals(2, complex.getImaginary(), 1e-10);
	}

	@Test
	public void testParseImaginarySecondQuadrant() {
		ComplexNumber complex = parse("-2+i");
		assertEquals(-2, complex.getReal(), 1e-10);
		assertEquals(1, complex.getImaginary(), 1e-10);
	}

	@Test
	public void testParseImaginaryThirdQuadrant() {
		ComplexNumber complex = parse("-3-2i");
		assertEquals(-3, complex.getReal(), 1e-10);
		assertEquals(-2, complex.getImaginary(), 1e-10);
	}

	@Test
	public void testParseImaginaryFourthQuadrant() {
		ComplexNumber complex = parse("5-i");
		assertEquals(5, complex.getReal(), 1e-10);
		assertEquals(-1, complex.getImaginary(), 1e-10);
	}

	@Test
	public void testParseDecimalImaginary() {
		ComplexNumber complex = parse("3.15-2.57i");
		assertEquals(3.15, complex.getReal(), 1e-10);
		assertEquals(-2.57, complex.getImaginary(), 1e-10);
	}

	@Test
	public void testFromMagnitudeAndAngleIfMagnitudeIsZero() {
		ComplexNumber complex = fromMagnitudeAndAngle(0, 4.57);
		assertEquals(0, complex.getReal(), 1e-10);
		assertEquals(0, complex.getImaginary(), 1e-10);
	}

	@Test
	public void testFromMagnitudeAndAngleIfAngleIsZero() {
		ComplexNumber complex = fromMagnitudeAndAngle(5, 0);
		assertEquals(5, complex.getReal(), 1e-10);
		assertEquals(0, complex.getImaginary(), 1e-10);
	}

	@Test
	public void testFindImaginaryOne() {
		assertEquals(1, findImaginary("i"), 1e-10);
	}

	@Test
	public void testFindImaginaryDifferentThanOne() {
		assertEquals(-7, findImaginary("-7i"), 1e-10);
	}

	@Test
	public void testGetRealIfRealIsZero() {
		ComplexNumber complex = new ComplexNumber(0, 5);
		assertEquals(0, complex.getReal(), 1e-10);

	}

	@Test
	public void testGetRealIfRealIsDifferentThanZero() {
		ComplexNumber complex = new ComplexNumber(-2, 5);
		assertEquals(-2, complex.getReal(), 1e-10);

	}

	@Test
	public void testGetImaginaryIfImaginaryIsZero() {
		ComplexNumber complex = new ComplexNumber(-5, 0);
		assertEquals(0, complex.getImaginary(), 1e-10);

	}

	@Test
	public void testGetImaginaryIfImaginaryIsDifferentThanZero() {
		ComplexNumber complex = new ComplexNumber(2, 5);
		assertEquals(5, complex.getImaginary(), 1e-10);

	}

	@Test
	public void testGetAngleIfAngleIsZero() {
		ComplexNumber complex = new ComplexNumber(0, 0);
		assertEquals(0, complex.getAngle(), 1e-10);

	}

	@Test
	public void testGetAngleIfAngleIsPi() {
		ComplexNumber complex = new ComplexNumber(-5, 0);
		assertEquals(Math.PI, complex.getAngle(), 1e-10);

	}

	@Test
	public void testGetAngleIfAngleIsGreaterThanPi() {
		ComplexNumber complex = new ComplexNumber(1, -1);
		assertEquals(7 * Math.PI / 4, complex.getAngle(), 1e-10);

	}

	@Test
	public void testGetMagnitudeIfMagnitudeIsZero() {
		ComplexNumber complex = new ComplexNumber(0, 0);
		assertEquals(0, complex.getMagnitude(), 1e-10);
	}

	@Test
	public void testGetMagnitudeIfMagnitudeIsDifferentThanZero() {
		ComplexNumber complex = new ComplexNumber(4, 3);
		assertEquals(5, complex.getMagnitude(), 1e-10);
	}

	@Test
	public void testAddingNullValue() {
		ComplexNumber complex1 = new ComplexNumber(4, 5);
		ComplexNumber complex2 = null;
		boolean prepoznat = false;
		try {
			complex1.add(complex2);
		} catch (NullPointerException exc) {
			prepoznat = true;
		}
		assertTrue(prepoznat);
	}

	@Test
	public void testAddingComplexNumbersIfOneIsZero() {
		ComplexNumber complex1 = new ComplexNumber(0, 0);
		ComplexNumber complex2 = new ComplexNumber(4, 5);
		assertEquals(4, complex1.add(complex2).getReal(), 1e-10);
		assertEquals(5, complex1.add(complex2).getImaginary(), 1e-10);
	}

	@Test
	public void testAddingTwoDifferentComplexNumbers() {
		ComplexNumber complex1 = new ComplexNumber(-7, -5);
		ComplexNumber complex2 = new ComplexNumber(4, 5);
		assertEquals(-3, complex1.add(complex2).getReal(), 1e-10);
		assertEquals(0, complex1.add(complex2).getImaginary(), 1e-10);
	}

	@Test
	public void testSubtractingComplexNumbersIfOneIsZero() {
		ComplexNumber complex1 = new ComplexNumber(0, 0);
		ComplexNumber complex2 = new ComplexNumber(4, 5);
		assertEquals(-4, complex1.sub(complex2).getReal(), 1e-10);
		assertEquals(-5, complex1.sub(complex2).getImaginary(), 1e-10);
	}

	@Test
	public void testSubtractingTwoDifferentComplexNumbers() {
		ComplexNumber complex1 = new ComplexNumber(-7, -5);
		ComplexNumber complex2 = new ComplexNumber(4, 5);
		assertEquals(-11, complex1.sub(complex2).getReal(), 1e-10);
		assertEquals(-10, complex1.sub(complex2).getImaginary(), 1e-10);
	}
	
	@Test
	public void testSubtractingNullValue() {
		ComplexNumber complex1 = new ComplexNumber(4, 5);
		ComplexNumber complex2 = null;
		boolean prepoznat = false;
		try {
			complex1.sub(complex2);
		} catch (NullPointerException exc) {
			prepoznat = true;
		}
		assertTrue(prepoznat);
	}

	@Test
	public void testMultiplyingComplexNumbersIfOneIsZero() {
		ComplexNumber complex1 = new ComplexNumber(0, 0);
		ComplexNumber complex2 = new ComplexNumber(4, 5);
		assertEquals(0, complex1.mul(complex2).getReal(), 1e-10);
		assertEquals(0, complex1.mul(complex2).getImaginary(), 1e-10);
	}

	@Test
	public void testMultiplyingTwoDifferentComplexNumbers() {
		ComplexNumber complex1 = new ComplexNumber(-7, -5);
		ComplexNumber complex2 = new ComplexNumber(4, 5);
		assertEquals(-3, complex1.mul(complex2).getReal(), 1e-10);
		assertEquals(-55, complex1.mul(complex2).getImaginary(), 1e-10);
	}
	
	@Test
	public void testMultiplyingNullValue() {
		ComplexNumber complex1 = new ComplexNumber(4, 5);
		ComplexNumber complex2 = null;
		boolean prepoznat = false;
		try {
			complex1.mul(complex2);
		} catch (NullPointerException exc) {
			prepoznat = true;
		}
		assertTrue(prepoznat);
	}

	@Test
	public void testDividingComplexNumbersIfFirstIsZero() {
		ComplexNumber complex1 = new ComplexNumber(0, 0);
		ComplexNumber complex2 = new ComplexNumber(4, 5);
		assertEquals(0, complex1.div(complex2).getReal(), 1e-10);
		assertEquals(0, complex1.div(complex2).getImaginary(), 1e-10);
	}
	
	@Test
	public void testDividingComplexNumbersIfSecondIsZero() {
		ComplexNumber complex1 = new ComplexNumber(4, 5);
		ComplexNumber complex2 = new ComplexNumber(0, 0);
		boolean prepoznat = false;
		try {
			complex1.div(complex2);
		} catch (ArithmeticException exc) {
			prepoznat = true;
		}
		assertTrue(prepoznat);
	}

	@Test
	public void testDividingTwoDifferentComplexNumbers() {
		ComplexNumber complex1 = new ComplexNumber(-7, 5);
		ComplexNumber complex2 = new ComplexNumber(1, 3);
		assertEquals(0.8, complex1.div(complex2).getReal(), 1e-10);
		assertEquals(2.6, complex1.div(complex2).getImaginary(), 1e-10);
	}

	@Test
	public void testDividingNullValue() {
		ComplexNumber complex1 = new ComplexNumber(4, 5);
		ComplexNumber complex2 = null;
		boolean prepoznat = false;
		try {
			complex1.div(complex2);
		} catch (NullPointerException exc) {
			prepoznat = true;
		}
		assertTrue(prepoznat);
	}
	
	@Test
	public void testIllegalPowerOfComplexNumber() {
		ComplexNumber complex = new ComplexNumber(-5, 8);
		boolean prepoznat = false;
		try {
			complex.power(-5);
		} catch (IllegalArgumentException exc) {
			prepoznat = true;
		}
		assertTrue(prepoznat);
	}
	
	@Test
	public void testZeroPowerOfComplexNumber() {
		ComplexNumber complex = new ComplexNumber(-5, 8);
		assertEquals(1, complex.power(0).getReal(), 1e-10);
		assertEquals(0, complex.power(0).getImaginary(), 1e-10);
	}

	@Test
	public void testFirstPowerOfComplexNumber() {
		ComplexNumber complex = new ComplexNumber(-5, 8);
		assertEquals(-5, complex.power(1).getReal(), 1e-10);
		assertEquals(8, complex.power(1).getImaginary(), 1e-10);
	}

	@Test
	public void testThirdPowerOfComplexNumber() {
		ComplexNumber complex = new ComplexNumber(-5, 8);
		assertEquals(835, complex.power(3).getReal(), 1e-10);
		assertEquals(88, complex.power(3).getImaginary(), 1e-10);
	}

	@Test
	public void testIllegalRootOfComplexNumber() {
		ComplexNumber complex = new ComplexNumber(-5, 8);
		boolean prepoznat = false;
		try {
			complex.root(0);
		} catch (IllegalArgumentException exc) {
			prepoznat = true;
		}
		assertTrue(prepoznat);
	}
	
	@Test
	public void testFirstRootOfComplexNumber() {
		ComplexNumber complex = new ComplexNumber(-5, 8);
		assertEquals(-5, complex.root(1)[0].getReal(), 1e-10);
		assertEquals(8, complex.root(1)[0].getImaginary(), 1e-10);
	}

	@Test
	public void testThirdRootOfComplexNumber() {
		ComplexNumber complex = new ComplexNumber(-2, 2);
		assertEquals(1, complex.root(3)[0].getReal(), 1e-10);
		assertEquals(1, complex.root(3)[0].getImaginary(), 1e-10);
	}

}
