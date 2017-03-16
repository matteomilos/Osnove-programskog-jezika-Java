package hr.fer.zemris.java.hw02;

import static hr.fer.zemris.java.hw02.ComplexNumber.findImaginary;
import static hr.fer.zemris.java.hw02.ComplexNumber.fromImaginary;
import static hr.fer.zemris.java.hw02.ComplexNumber.fromMagnitudeAndAngle;
import static hr.fer.zemris.java.hw02.ComplexNumber.fromReal;
import static hr.fer.zemris.java.hw02.ComplexNumber.parse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ComplexNumberTest {

	private static final double EPSILON = 1e-10;

	@Rule
	public ExpectedException thrownExc = ExpectedException.none();

	@Test
	public void testFromRealIfRealIsZero() {
		ComplexNumber complex = fromReal(0);
		assertTrue(complex.equals(new ComplexNumber(0, 0)));
	}

	public void testFromRealIfRealIsDifferentThanZero() {
		ComplexNumber complex = fromReal(-4);
		assertTrue(complex.equals(new ComplexNumber(-4, 0)));
	}

	@Test
	public void testFromImaginaryIfImaginaryIsZero() {
		ComplexNumber complex = fromImaginary(0);
		assertTrue(complex.equals(new ComplexNumber(0, 0)));

	}

	@Test
	public void testFromImaginaryIfImaginaryIsDifferentThanZero() {
		ComplexNumber complex = fromImaginary(4);
		assertTrue(complex.equals(new ComplexNumber(0, 4)));
	}

	@Test
	public void testParseOnlyImaginary() {
		ComplexNumber complex = parse("i");
		assertTrue(complex.equals(new ComplexNumber(0, 1)));

	}

	@Test
	public void testParseOnlyReal() {
		ComplexNumber complex = parse("3");
		assertTrue(complex.equals(new ComplexNumber(3, 0)));

	}

	@Test
	public void testParseImaginaryFirstQuadrant() {
		ComplexNumber complex = parse("3+2i");
		assertTrue(complex.equals(new ComplexNumber(3, 2)));

	}

	@Test
	public void testParseImaginarySecondQuadrant() {
		ComplexNumber complex = parse("-2+i");
		assertTrue(complex.equals(new ComplexNumber(-2, 1)));

	}

	@Test
	public void testParseImaginaryThirdQuadrant() {
		ComplexNumber complex = parse("-3-2i");
		assertTrue(complex.equals(new ComplexNumber(-3, -2)));

	}

	@Test
	public void testParseImaginaryFourthQuadrant() {
		ComplexNumber complex = parse("5-i");
		assertTrue(complex.equals(new ComplexNumber(5, -1)));

	}

	@Test
	public void testParseDecimalImaginary() {
		ComplexNumber complex = parse("3.15-2.57i");
		assertTrue(complex.equals(new ComplexNumber(3.15, -2.57)));

	}

	@Test
	public void testFromMagnitudeAndAngleIfMagnitudeIsZero() {
		ComplexNumber complex = fromMagnitudeAndAngle(0, 1.17);
		assertTrue(complex.equals(new ComplexNumber(0, 0)));

	}

	@Test
	public void testFromMagnitudeAndAngleIfAngleIsZero() {
		ComplexNumber complex = fromMagnitudeAndAngle(5, 0);
		assertTrue(complex.equals(new ComplexNumber(5, 0)));

	}

	@Test
	public void testFindImaginaryOne() {
		assertEquals(1, findImaginary("i"), EPSILON);
	}

	@Test
	public void testFindImaginaryDifferentThanOne() {
		assertEquals(-7, findImaginary("-7i"), EPSILON);
	}

	@Test
	public void testGetRealIfRealIsZero() {
		ComplexNumber complex = new ComplexNumber(0, 5);
		assertEquals(0, complex.getReal(), EPSILON);

	}

	@Test
	public void testGetRealIfRealIsDifferentThanZero() {
		ComplexNumber complex = new ComplexNumber(-2, 5);
		assertEquals(-2, complex.getReal(), EPSILON);

	}

	@Test
	public void testGetImaginaryIfImaginaryIsZero() {
		ComplexNumber complex = new ComplexNumber(-5, 0);
		assertEquals(0, complex.getImaginary(), EPSILON);

	}

	@Test
	public void testGetImaginaryIfImaginaryIsDifferentThanZero() {
		ComplexNumber complex = new ComplexNumber(2, 5);
		assertEquals(5, complex.getImaginary(), EPSILON);

	}

	@Test
	public void testGetAngleIfAngleIsZero() {
		ComplexNumber complex = new ComplexNumber(0, 0);
		assertEquals(0, complex.getAngle(), EPSILON);

	}

	@Test
	public void testGetAngleIfAngleIsPi() {
		ComplexNumber complex = new ComplexNumber(-5, 0);
		assertEquals(Math.PI, complex.getAngle(), EPSILON);

	}

	@Test
	public void testGetAngleIfAngleIsGreaterThanPi() {
		ComplexNumber complex = new ComplexNumber(1, -1);
		assertEquals(7 * Math.PI / 4, complex.getAngle(), EPSILON);

	}

	@Test
	public void testGetMagnitudeIfMagnitudeIsZero() {
		ComplexNumber complex = new ComplexNumber(0, 0);
		assertEquals(0, complex.getMagnitude(), EPSILON);
	}

	@Test
	public void testGetMagnitudeIfMagnitudeIsDifferentThanZero() {
		ComplexNumber complex = new ComplexNumber(4, 3);
		assertEquals(5, complex.getMagnitude(), EPSILON);
	}

	@Test
	public void testAddingNullValue() {
		ComplexNumber complex1 = new ComplexNumber(4, 5);
		ComplexNumber complex2 = null;
		thrownExc.expect(NullPointerException.class);
		complex1.add(complex2);
	}

	@Test
	public void testAddingComplexNumbersIfOneIsZero() {
		ComplexNumber complex1 = new ComplexNumber(0, 0);
		ComplexNumber complex2 = new ComplexNumber(4, 5);
		assertTrue(complex1.add(complex2).equals(new ComplexNumber(4, 5)));
	}

	@Test
	public void testAddingTwoDifferentComplexNumbers() {
		ComplexNumber complex1 = new ComplexNumber(-7, -5);
		ComplexNumber complex2 = new ComplexNumber(4, 5);
		assertTrue(complex1.add(complex2).equals(new ComplexNumber(-3, 0)));

	}

	@Test
	public void testSubtractingComplexNumbersIfOneIsZero() {
		ComplexNumber complex1 = new ComplexNumber(0, 0);
		ComplexNumber complex2 = new ComplexNumber(4, 5);
		assertTrue(complex1.sub(complex2).equals(new ComplexNumber(-4, -5)));

	}

	@Test
	public void testSubtractingTwoDifferentComplexNumbers() {
		ComplexNumber complex1 = new ComplexNumber(-7, -5);
		ComplexNumber complex2 = new ComplexNumber(4, 5);
		assertTrue(complex1.sub(complex2).equals(new ComplexNumber(-11, -10)));

	}

	@Test
	public void testSubtractingNullValue() {
		ComplexNumber complex1 = new ComplexNumber(4, 5);
		ComplexNumber complex2 = null;
		thrownExc.expect(NullPointerException.class);
		complex1.sub(complex2);
	}

	@Test
	public void testMultiplyingComplexNumbersIfOneIsZero() {
		ComplexNumber complex1 = new ComplexNumber(0, 0);
		ComplexNumber complex2 = new ComplexNumber(4, 5);
		assertTrue(complex1.mul(complex2).equals(new ComplexNumber(0, 0)));

	}

	@Test
	public void testMultiplyingTwoDifferentComplexNumbers() {
		ComplexNumber complex1 = new ComplexNumber(-7, -5);
		ComplexNumber complex2 = new ComplexNumber(4, 5);
		assertTrue(complex1.mul(complex2).equals(new ComplexNumber(-3, -55)));

	}

	@Test
	public void testMultiplyingNullValue() {
		ComplexNumber complex1 = new ComplexNumber(4, 5);
		ComplexNumber complex2 = null;
		thrownExc.expect(NullPointerException.class);
		complex1.mul(complex2);
	}

	@Test
	public void testDividingComplexNumbersIfFirstIsZero() {
		ComplexNumber complex1 = new ComplexNumber(0, 0);
		ComplexNumber complex2 = new ComplexNumber(4, 5);
		assertTrue(complex1.div(complex2).equals(new ComplexNumber(0, 0)));

	}

	@Test
	public void testDividingComplexNumbersIfSecondIsZero() {
		ComplexNumber complex1 = new ComplexNumber(4, 5);
		ComplexNumber complex2 = new ComplexNumber(0, 0);
		thrownExc.expect(ArithmeticException.class);
		complex1.div(complex2);
	}

	@Test
	public void testDividingTwoDifferentComplexNumbers() {
		ComplexNumber complex1 = new ComplexNumber(-7, 5);
		ComplexNumber complex2 = new ComplexNumber(1, 3);
		assertTrue(complex1.div(complex2).equals(new ComplexNumber(0.8, 2.6)));

	}

	@Test
	public void testDividingNullValue() {
		ComplexNumber complex1 = new ComplexNumber(4, 5);
		ComplexNumber complex2 = null;
		thrownExc.expect(NullPointerException.class);
		complex1.div(complex2);
	}

	@Test
	public void testIllegalPowerOfComplexNumber() {
		ComplexNumber complex = new ComplexNumber(-5, 8);
		thrownExc.expect(IllegalArgumentException.class);
		complex.power(-5);
	}

	@Test
	public void testZeroPowerOfComplexNumber() {
		ComplexNumber complex = new ComplexNumber(-5, 8);
		assertTrue(complex.power(0).equals(new ComplexNumber(1, 0)));
	}

	@Test
	public void testFirstPowerOfComplexNumber() {
		ComplexNumber complex = new ComplexNumber(-5, 8);
		assertTrue(complex.power(1).equals(new ComplexNumber(-5, 8)));
	}

	@Test
	public void testThirdPowerOfComplexNumber() {
		ComplexNumber complex = new ComplexNumber(-5, 8);
		assertTrue(complex.power(3).equals(new ComplexNumber(835, 88)));

	}

	@Test
	public void testIllegalRootOfComplexNumber() {
		ComplexNumber complex = new ComplexNumber(-5, 8);
		thrownExc.expect(IllegalArgumentException.class);
		complex.root(0);
	}

	@Test
	public void testFirstRootOfComplexNumber() {
		ComplexNumber complex = new ComplexNumber(-5, 8);
		assertTrue(complex.root(1)[0].equals(new ComplexNumber(-5, 8)));
	}

	@Test
	public void testThirdRootOfComplexNumber() {
		ComplexNumber complex = new ComplexNumber(-2, 2);
		assertTrue(complex.root(3)[0].equals(new ComplexNumber(1, 1)));

	}

}
