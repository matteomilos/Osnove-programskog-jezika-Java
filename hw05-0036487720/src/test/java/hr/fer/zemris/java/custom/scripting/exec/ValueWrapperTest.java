package hr.fer.zemris.java.custom.scripting.exec;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

@SuppressWarnings("javadoc")
public class ValueWrapperTest {

	@Test
	public void testAddingTwoIntegers() {
		ValueWrapper value = new ValueWrapper(17);
		value.add(14);
		assertTrue(value.getValue() instanceof Integer);
		assertEquals(17 + 14, value.getValue());
	}

	@Test
	public void testAddingIntegerAndDouble() {
		ValueWrapper value = new ValueWrapper(17);
		value.add(14.5);
		assertTrue(value.getValue() instanceof Double);
		assertEquals(17 + 14.5, value.getValue());
	}

	@Test
	public void testAddingIntegerAndStringRepresentingAnInteger() {
		ValueWrapper value = new ValueWrapper(17);
		value.add("14");
		assertTrue(value.getValue() instanceof Integer);
		assertEquals(17 + 14, value.getValue());
	}

	@Test
	public void testAddingIntegerAndStringRepresentingDouble() {
		ValueWrapper value = new ValueWrapper(17);
		value.add("1.5e2");
		assertTrue(value.getValue() instanceof Double);
		assertEquals(17 + 150., value.getValue());
	}

	@Test
	public void testAddingIntegerAndStringRepresentingDouble2() {
		ValueWrapper value = new ValueWrapper(17);
		value.add("14.5");
		assertTrue(value.getValue() instanceof Double);
		assertEquals(17 + 14.5, value.getValue());
	}

	@Test
	public void testAddingIntegerAndNull() {
		ValueWrapper value = new ValueWrapper(17);
		value.add(null);
		assertTrue(value.getValue() instanceof Integer);
		assertEquals(17 + 0, value.getValue());
	}

	@Test(expected = RuntimeException.class)
	public void testAddingIntegerAndNonParsableString() {
		ValueWrapper value = new ValueWrapper(17);
		value.add("Ante");
	}

	@Test
	public void testAddingTwoDoubles() {
		ValueWrapper value = new ValueWrapper(17.5);
		value.add(14.5);
		assertTrue(value.getValue() instanceof Double);
		assertEquals(17.5 + 14.5, value.getValue());
	}

	@Test
	public void testAddingDoubleAndInteger() {
		ValueWrapper value = new ValueWrapper(17.5);
		value.add(14);
		assertTrue(value.getValue() instanceof Double);
		assertEquals(17.5 + 14, value.getValue());
	}

	@Test
	public void testAddingDoubleAndStringRepresentingAnInteger() {
		ValueWrapper value = new ValueWrapper(17.5);
		value.add("14");
		assertTrue(value.getValue() instanceof Double);
		assertEquals(17.5 + 14, value.getValue());
	}

	@Test
	public void testAddingDoubleAndStringRepresentingDouble() {
		ValueWrapper value = new ValueWrapper(17.5);
		value.add("1.5e2");
		assertTrue(value.getValue() instanceof Double);
		assertEquals(17.5 + 150., value.getValue());
	}

	@Test
	public void testAddingDoubleAndStringRepresentingDouble2() {
		ValueWrapper value = new ValueWrapper(17.5);
		value.add("14.5");
		assertTrue(value.getValue() instanceof Double);
		assertEquals(17.5 + 14.5, value.getValue());
	}

	@Test
	public void testAddingDoubleAndNull() {
		ValueWrapper value = new ValueWrapper(17.5);
		value.add(null);
		assertTrue(value.getValue() instanceof Double);
		assertEquals(17.5 + 0, value.getValue());
	}

	@Test(expected = RuntimeException.class)
	public void testAddingDoubleAndNonParsableString() {
		ValueWrapper value = new ValueWrapper(17.5);
		value.add("Ante");
	}

	@Test
	public void testAddingStringAndDouble() {
		ValueWrapper value = new ValueWrapper("17");
		value.add(14.5);
		assertTrue(value.getValue() instanceof Double);
		assertEquals(17 + 14.5, value.getValue());
	}

	@Test
	public void testAddingStringAndInteger() {
		ValueWrapper value = new ValueWrapper("17");
		value.add(14);
		assertTrue(value.getValue() instanceof Integer);
		assertEquals(17 + 14, value.getValue());
	}

	@Test
	public void testAddingStringAndStringRepresentingAnInteger() {
		ValueWrapper value = new ValueWrapper(17);
		value.add("14");
		assertTrue(value.getValue() instanceof Integer);
		assertEquals(17 + 14, value.getValue());
	}

	@Test
	public void testAddingStringAndStringRepresentingDouble() {
		ValueWrapper value = new ValueWrapper(17);
		value.add("1.5e2");
		assertTrue(value.getValue() instanceof Double);
		assertEquals(17 + 150., value.getValue());
	}

	@Test
	public void testAddingStringAndStringRepresentingDouble2() {
		ValueWrapper value = new ValueWrapper("17.5");
		value.add("14.5");
		assertTrue(value.getValue() instanceof Double);
		assertEquals(17.5 + 14.5, value.getValue());
	}

	@Test
	public void testAddingStringAndNull() {
		ValueWrapper value = new ValueWrapper("17.5");
		value.add(null);
		assertTrue(value.getValue() instanceof Double);
		assertEquals(17.5 + 0, value.getValue());
	}

	@Test(expected = RuntimeException.class)
	public void testAddingStringAndNonParsableString() {
		ValueWrapper value = new ValueWrapper("17.5");
		value.add("Ante");
	}

	@Test
	public void testAddingTwoNullValues() {
		ValueWrapper value = new ValueWrapper(null);
		value.add(null);
		assertTrue(value.getValue() instanceof Integer);
		assertEquals(0 + 0, value.getValue());
	}

	@Test
	public void testAddingNullAndInteger() {
		ValueWrapper value = new ValueWrapper(null);
		value.add(14);
		assertTrue(value.getValue() instanceof Integer);
		assertEquals(0 + 14, value.getValue());
	}

	@Test
	public void testAddingNullAndDouble() {
		ValueWrapper value = new ValueWrapper(null);
		value.add(17.5);
		assertTrue(value.getValue() instanceof Double);
		assertEquals(0 + 17.5, value.getValue());
	}

	@Test
	public void testAddingNullAndStringRepresentingAnInteger() {
		ValueWrapper value = new ValueWrapper(null);
		value.add("14");
		assertTrue(value.getValue() instanceof Integer);
		assertEquals(0 + 14, value.getValue());
	}

	@Test
	public void testAddingNullAndStringRepresentingDouble() {
		ValueWrapper value = new ValueWrapper(null);
		value.add("17.5");
		assertTrue(value.getValue() instanceof Double);
		assertEquals(0 + 17.5, value.getValue());
	}

	@Test
	public void testAddingNullAndStringRepresentingDouble2() {
		ValueWrapper value = new ValueWrapper(null);
		value.add("1.5e2");
		assertTrue(value.getValue() instanceof Double);
		assertEquals(0 + 150., value.getValue());
	}

	@Test(expected = RuntimeException.class)
	public void testAddingNullAndNonParseableString() {
		ValueWrapper value = new ValueWrapper(null);
		value.add("Marko");
	}

	@Test
	public void testSubtractingTwoIntegers() {
		ValueWrapper value = new ValueWrapper(17);
		value.subtract(14);
		assertTrue(value.getValue() instanceof Integer);
		assertEquals(17 - 14, value.getValue());
	}

	@Test
	public void testSubtractingIntegerAndDouble() {
		ValueWrapper value = new ValueWrapper(17);
		value.subtract(14.5);
		assertTrue(value.getValue() instanceof Double);
		assertEquals(17 - 14.5, value.getValue());
	}

	@Test
	public void testSubtractingIntegerAndStringRepresentingAnInteger() {
		ValueWrapper value = new ValueWrapper(17);
		value.subtract("14");
		assertTrue(value.getValue() instanceof Integer);
		assertEquals(17 - 14, value.getValue());
	}

	@Test
	public void testSubtractingIntegerAndStringRepresentingDouble() {
		ValueWrapper value = new ValueWrapper(17);
		value.subtract("1.5e2");
		assertTrue(value.getValue() instanceof Double);
		assertEquals(17 - 150., value.getValue());
	}

	@Test
	public void testSubtractingIntegerAndStringRepresentingDouble2() {
		ValueWrapper value = new ValueWrapper(17);
		value.subtract("14.5");
		assertTrue(value.getValue() instanceof Double);
		assertEquals(17 - 14.5, value.getValue());
	}

	@Test
	public void testSubtractingIntegerAndNull() {
		ValueWrapper value = new ValueWrapper(17);
		value.subtract(null);
		assertTrue(value.getValue() instanceof Integer);
		assertEquals(17 - 0, value.getValue());
	}

	@Test(expected = RuntimeException.class)
	public void testSubtractingIntegerAndNonParsableString() {
		ValueWrapper value = new ValueWrapper(17);
		value.subtract("Ante");
	}

	@Test
	public void testSubtractingTwoDoubles() {
		ValueWrapper value = new ValueWrapper(17.5);
		value.subtract(14.5);
		assertTrue(value.getValue() instanceof Double);
		assertEquals(17.5 - 14.5, value.getValue());
	}

	@Test
	public void testSubtractingDoubleAndInteger() {
		ValueWrapper value = new ValueWrapper(17.5);
		value.subtract(14);
		assertTrue(value.getValue() instanceof Double);
		assertEquals(17.5 - 14, value.getValue());
	}

	@Test
	public void testSubtractingDoubleAndStringRepresentingAnInteger() {
		ValueWrapper value = new ValueWrapper(17.5);
		value.subtract("14");
		assertTrue(value.getValue() instanceof Double);
		assertEquals(17.5 - 14, value.getValue());
	}

	@Test
	public void testSubtractingDoubleAndStringRepresentingDouble() {
		ValueWrapper value = new ValueWrapper(17.5);
		value.subtract("1.5e2");
		assertTrue(value.getValue() instanceof Double);
		assertEquals(17.5 - 150., value.getValue());
	}

	@Test
	public void testSubtractingDoubleAndStringRepresentingDouble2() {
		ValueWrapper value = new ValueWrapper(17.5);
		value.subtract("14.5");
		assertTrue(value.getValue() instanceof Double);
		assertEquals(17.5 - 14.5, value.getValue());
	}

	@Test
	public void testSubtractingDoubleAndNull() {
		ValueWrapper value = new ValueWrapper(17.5);
		value.subtract(null);
		assertTrue(value.getValue() instanceof Double);
		assertEquals(17.5 - 0, value.getValue());
	}

	@Test(expected = RuntimeException.class)
	public void testSubtractingDoubleAndNonParsableString() {
		ValueWrapper value = new ValueWrapper(17.5);
		value.subtract("Ante");
	}

	@Test
	public void testSubtractingStringAndDouble() {
		ValueWrapper value = new ValueWrapper("17");
		value.subtract(14.5);
		assertTrue(value.getValue() instanceof Double);
		assertEquals(17 - 14.5, value.getValue());
	}

	@Test
	public void testSubtractingStringAndInteger() {
		ValueWrapper value = new ValueWrapper("17");
		value.subtract(14);
		assertTrue(value.getValue() instanceof Integer);
		assertEquals(17 - 14, value.getValue());
	}

	@Test
	public void testSubtractingStringAndStringRepresentingAnInteger() {
		ValueWrapper value = new ValueWrapper(17);
		value.subtract("14");
		assertTrue(value.getValue() instanceof Integer);
		assertEquals(17 - 14, value.getValue());
	}

	@Test
	public void testSubtractingStringAndStringRepresentingDouble() {
		ValueWrapper value = new ValueWrapper(17);
		value.subtract("1.5e2");
		assertTrue(value.getValue() instanceof Double);
		assertEquals(17 - 150., value.getValue());
	}

	@Test
	public void testSubtractingStringAndStringRepresentingDouble2() {
		ValueWrapper value = new ValueWrapper("17.5");
		value.subtract("14.5");
		assertTrue(value.getValue() instanceof Double);
		assertEquals(17.5 - 14.5, value.getValue());
	}

	@Test
	public void testSubtractingStringAndNull() {
		ValueWrapper value = new ValueWrapper("17.5");
		value.subtract(null);
		assertTrue(value.getValue() instanceof Double);
		assertEquals(17.5 - 0, value.getValue());
	}

	@Test(expected = RuntimeException.class)
	public void testSubtractingStringAndNonParsableString() {
		ValueWrapper value = new ValueWrapper("17.5");
		value.subtract("Ante");
	}

	@Test
	public void testSubtractingTwoNullValues() {
		ValueWrapper value = new ValueWrapper(null);
		value.subtract(null);
		assertTrue(value.getValue() instanceof Integer);
		assertEquals(0 - 0, value.getValue());
	}

	@Test
	public void testSubtractingNullAndInteger() {
		ValueWrapper value = new ValueWrapper(null);
		value.subtract(14);
		assertTrue(value.getValue() instanceof Integer);
		assertEquals(0 - 14, value.getValue());
	}

	@Test
	public void testSubtractingNullAndDouble() {
		ValueWrapper value = new ValueWrapper(null);
		value.subtract(17.5);
		assertTrue(value.getValue() instanceof Double);
		assertEquals(0 - 17.5, value.getValue());
	}

	@Test
	public void testSubtractingNullAndStringRepresentingAnInteger() {
		ValueWrapper value = new ValueWrapper(null);
		value.subtract("14");
		assertTrue(value.getValue() instanceof Integer);
		assertEquals(0 - 14, value.getValue());
	}

	@Test
	public void testSubtractingNullAndStringRepresentingDouble() {
		ValueWrapper value = new ValueWrapper(null);
		value.subtract("17.5");
		assertTrue(value.getValue() instanceof Double);
		assertEquals(0 - 17.5, value.getValue());
	}

	@Test
	public void testSubtractingNullAndStringRepresentingDouble2() {
		ValueWrapper value = new ValueWrapper(null);
		value.subtract("1.5e2");
		assertTrue(value.getValue() instanceof Double);
		assertEquals(0 - 150., value.getValue());
	}

	@Test(expected = RuntimeException.class)
	public void testSubtractingNullAndNonParseableString() {
		ValueWrapper value = new ValueWrapper(null);
		value.subtract("Marko");
	}

	@Test
	public void testMultiplyingTwoIntegers() {
		ValueWrapper value = new ValueWrapper(17);
		value.multiply(14);
		assertTrue(value.getValue() instanceof Integer);
		assertEquals(17 * 14, value.getValue());
	}

	@Test
	public void testMultiplyingIntegerAndDouble() {
		ValueWrapper value = new ValueWrapper(17);
		value.multiply(14.5);
		assertTrue(value.getValue() instanceof Double);
		assertEquals(17 * 14.5, value.getValue());
	}

	@Test
	public void testMultiplyingIntegerAndStringRepresentingAnInteger() {
		ValueWrapper value = new ValueWrapper(17);
		value.multiply("14");
		assertTrue(value.getValue() instanceof Integer);
		assertEquals(17 * 14, value.getValue());
	}

	@Test
	public void testMultiplyingIntegerAndStringRepresentingDouble() {
		ValueWrapper value = new ValueWrapper(17);
		value.multiply("1.5e2");
		assertTrue(value.getValue() instanceof Double);
		assertEquals(17 * 150., value.getValue());
	}

	@Test
	public void testMultiplyingIntegerAndStringRepresentingDouble2() {
		ValueWrapper value = new ValueWrapper(17);
		value.multiply("14.5");
		assertTrue(value.getValue() instanceof Double);
		assertEquals(17 * 14.5, value.getValue());
	}

	@Test
	public void testMultiplyingIntegerAndNull() {
		ValueWrapper value = new ValueWrapper(17);
		value.multiply(null);
		assertTrue(value.getValue() instanceof Integer);
		assertEquals(17 * 0, value.getValue());
	}

	@Test(expected = RuntimeException.class)
	public void testMultiplyingIntegerAndNonParsableString() {
		ValueWrapper value = new ValueWrapper(17);
		value.multiply("Ante");
	}

	@Test
	public void testMultiplyingTwoDoubles() {
		ValueWrapper value = new ValueWrapper(17.5);
		value.multiply(14.5);
		assertTrue(value.getValue() instanceof Double);
		assertEquals(17.5 * 14.5, value.getValue());
	}

	@Test
	public void testMultiplyingDoubleAndInteger() {
		ValueWrapper value = new ValueWrapper(17.5);
		value.multiply(14);
		assertTrue(value.getValue() instanceof Double);
		assertEquals(17.5 * 14, value.getValue());
	}

	@Test
	public void testMultiplyingDoubleAndStringRepresentingAnInteger() {
		ValueWrapper value = new ValueWrapper(17.5);
		value.multiply("14");
		assertTrue(value.getValue() instanceof Double);
		assertEquals(17.5 * 14, value.getValue());
	}

	@Test
	public void testMultiplyingDoubleAndStringRepresentingDouble() {
		ValueWrapper value = new ValueWrapper(17.5);
		value.multiply("1.5e2");
		assertTrue(value.getValue() instanceof Double);
		assertEquals(17.5 * 150., value.getValue());
	}

	@Test
	public void testMultiplyingDoubleAndStringRepresentingDouble2() {
		ValueWrapper value = new ValueWrapper(17.5);
		value.multiply("14.5");
		assertTrue(value.getValue() instanceof Double);
		assertEquals(17.5 * 14.5, value.getValue());
	}

	@Test
	public void testMultiplyingDoubleAndNull() {
		ValueWrapper value = new ValueWrapper(17.5);
		value.multiply(null);
		assertTrue(value.getValue() instanceof Double);
		assertEquals(17.5 * 0, value.getValue());
	}

	@Test(expected = RuntimeException.class)
	public void testMultiplyingDoubleAndNonParsableString() {
		ValueWrapper value = new ValueWrapper(17.5);
		value.multiply("Ante");
	}

	@Test
	public void testMultiplyingStringAndDouble() {
		ValueWrapper value = new ValueWrapper("17");
		value.multiply(14.5);
		assertTrue(value.getValue() instanceof Double);
		assertEquals(17 * 14.5, value.getValue());
	}

	@Test
	public void testMultiplyingStringAndInteger() {
		ValueWrapper value = new ValueWrapper("17");
		value.multiply(14);
		assertTrue(value.getValue() instanceof Integer);
		assertEquals(17 * 14, value.getValue());
	}

	@Test
	public void testMultiplyingStringAndStringRepresentingAnInteger() {
		ValueWrapper value = new ValueWrapper(17);
		value.multiply("14");
		assertTrue(value.getValue() instanceof Integer);
		assertEquals(17 * 14, value.getValue());
	}

	@Test
	public void testMultiplyingStringAndStringRepresentingDouble() {
		ValueWrapper value = new ValueWrapper(17);
		value.multiply("1.5e2");
		assertTrue(value.getValue() instanceof Double);
		assertEquals(17 * 150., value.getValue());
	}

	@Test
	public void testMultiplyingStringAndStringRepresentingDouble2() {
		ValueWrapper value = new ValueWrapper("17.5");
		value.multiply("14.5");
		assertTrue(value.getValue() instanceof Double);
		assertEquals(17.5 * 14.5, value.getValue());
	}

	@Test
	public void testMultiplyingStringAndNull() {
		ValueWrapper value = new ValueWrapper("17.5");
		value.multiply(null);
		assertTrue(value.getValue() instanceof Double);
		assertEquals(17.5 * 0, value.getValue());
	}

	@Test(expected = RuntimeException.class)
	public void testMultiplyingStringAndNonParsableString() {
		ValueWrapper value = new ValueWrapper("17.5");
		value.multiply("Ante");
	}

	@Test
	public void testMultiplyingTwoNullValues() {
		ValueWrapper value = new ValueWrapper(null);
		value.multiply(null);
		assertTrue(value.getValue() instanceof Integer);
		assertEquals(0 * 0, value.getValue());
	}

	@Test
	public void testMultiplyingNullAndInteger() {
		ValueWrapper value = new ValueWrapper(null);
		value.multiply(14);
		assertTrue(value.getValue() instanceof Integer);
		assertEquals(0 * 14, value.getValue());
	}

	@Test
	public void testMultiplyingNullAndDouble() {
		ValueWrapper value = new ValueWrapper(null);
		value.multiply(17.5);
		assertTrue(value.getValue() instanceof Double);
		assertEquals(0 * 17.5, value.getValue());
	}

	@Test
	public void testMultiplyingNullAndStringRepresentingAnInteger() {
		ValueWrapper value = new ValueWrapper(null);
		value.multiply("14");
		assertTrue(value.getValue() instanceof Integer);
		assertEquals(0 * 14, value.getValue());
	}

	@Test
	public void testMultiplyingNullAndStringRepresentingDouble() {
		ValueWrapper value = new ValueWrapper(null);
		value.multiply("17.5");
		assertTrue(value.getValue() instanceof Double);
		assertEquals(0 * 17.5, value.getValue());
	}

	@Test
	public void testMultiplyingNullAndStringRepresentingDouble2() {
		ValueWrapper value = new ValueWrapper(null);
		value.multiply("1.5e2");
		assertTrue(value.getValue() instanceof Double);
		assertEquals(0 * 150., value.getValue());
	}

	@Test(expected = RuntimeException.class)
	public void testMultiplyingNullAndNonParseableString() {
		ValueWrapper value = new ValueWrapper(null);
		value.multiply("Marko");
	}

	@Test
	public void testDividingTwoIntegers() {
		ValueWrapper value = new ValueWrapper(17);
		value.divide(14);
		assertTrue(value.getValue() instanceof Integer);
		assertEquals(17 / 14, value.getValue());
	}

	@Test
	public void testDividingIntegerAndDouble() {
		ValueWrapper value = new ValueWrapper(17);
		value.divide(14.5);
		assertTrue(value.getValue() instanceof Double);
		assertEquals(17 / 14.5, value.getValue());
	}

	@Test
	public void testDividingIntegerAndStringRepresentingAnInteger() {
		ValueWrapper value = new ValueWrapper(17);
		value.divide("14");
		assertTrue(value.getValue() instanceof Integer);
		assertEquals(17 / 14, value.getValue());
	}

	@Test
	public void testDividingIntegerAndStringRepresentingDouble() {
		ValueWrapper value = new ValueWrapper(17);
		value.divide("1.5e2");
		assertTrue(value.getValue() instanceof Double);
		assertEquals(17 / 150., value.getValue());
	}

	@Test
	public void testDividingIntegerAndStringRepresentingDouble2() {
		ValueWrapper value = new ValueWrapper(17);
		value.divide("14.5");
		assertTrue(value.getValue() instanceof Double);
		assertEquals(17 / 14.5, value.getValue());
	}

	@Test(expected = ArithmeticException.class)
	public void testDividingIntegerAndNull() {
		ValueWrapper value = new ValueWrapper(17);
		value.divide(null);
	}

	@Test(expected = RuntimeException.class)
	public void testDividingIntegerAndNonParsableString() {
		ValueWrapper value = new ValueWrapper(17);
		value.divide("Ante");
	}

	@Test
	public void testDividingTwoDoubles() {
		ValueWrapper value = new ValueWrapper(17.5);
		value.divide(14.5);
		assertTrue(value.getValue() instanceof Double);
		assertEquals(17.5 / 14.5, value.getValue());
	}

	@Test
	public void testDividingDoubleAndInteger() {
		ValueWrapper value = new ValueWrapper(17.5);
		value.divide(14);
		assertTrue(value.getValue() instanceof Double);
		assertEquals(17.5 / 14, value.getValue());
	}

	@Test
	public void testDividingDoubleAndStringRepresentingAnInteger() {
		ValueWrapper value = new ValueWrapper(17.5);
		value.divide("14");
		assertTrue(value.getValue() instanceof Double);
		assertEquals(17.5 / 14, value.getValue());
	}

	@Test
	public void testDividingDoubleAndStringRepresentingDouble() {
		ValueWrapper value = new ValueWrapper(17.5);
		value.divide("1.5e2");
		assertTrue(value.getValue() instanceof Double);
		assertEquals(17.5 / 150., value.getValue());
	}

	@Test
	public void testDividingDoubleAndStringRepresentingDouble2() {
		ValueWrapper value = new ValueWrapper(17.5);
		value.divide("14.5");
		assertTrue(value.getValue() instanceof Double);
		assertEquals(17.5 / 14.5, value.getValue());
	}

	@Test(expected = ArithmeticException.class)
	public void testDividingDoubleAndNull() {
		ValueWrapper value = new ValueWrapper(17.5);
		value.divide(null);
	}

	@Test(expected = RuntimeException.class)
	public void testDividingDoubleAndNonParsableString() {
		ValueWrapper value = new ValueWrapper(17.5);
		value.divide("Ante");
	}

	@Test
	public void testDividingStringAndDouble() {
		ValueWrapper value = new ValueWrapper("17");
		value.divide(14.5);
		assertTrue(value.getValue() instanceof Double);
		assertEquals(17 / 14.5, value.getValue());
	}

	@Test
	public void testDividingStringAndInteger() {
		ValueWrapper value = new ValueWrapper("17");
		value.divide(14);
		assertTrue(value.getValue() instanceof Integer);
		assertEquals(17 / 14, value.getValue());
	}

	@Test
	public void testDividingStringAndStringRepresentingAnInteger() {
		ValueWrapper value = new ValueWrapper(17);
		value.divide("14");
		assertTrue(value.getValue() instanceof Integer);
		assertEquals(17 / 14, value.getValue());
	}

	@Test
	public void testDividingStringAndStringRepresentingDouble() {
		ValueWrapper value = new ValueWrapper(17);
		value.divide("1.5e2");
		assertTrue(value.getValue() instanceof Double);
		assertEquals(17 / 150., value.getValue());
	}

	@Test
	public void testDividingStringAndStringRepresentingDouble2() {
		ValueWrapper value = new ValueWrapper("17.5");
		value.divide("14.5");
		assertTrue(value.getValue() instanceof Double);
		assertEquals(17.5 / 14.5, value.getValue());
	}

	@Test(expected = ArithmeticException.class)
	public void testDividingStringAndNull() {
		ValueWrapper value = new ValueWrapper("17.5");
		value.divide(null);
	}

	@Test(expected = RuntimeException.class)
	public void testDividingStringAndNonParsableString() {
		ValueWrapper value = new ValueWrapper("17.5");
		value.divide("Ante");
	}

	@Test(expected = ArithmeticException.class)
	public void testDividingTwoNullValues() {
		ValueWrapper value = new ValueWrapper(null);
		value.divide(null);
	}

	@Test
	public void testDividingNullAndInteger() {
		ValueWrapper value = new ValueWrapper(null);
		value.divide(14);
		assertTrue(value.getValue() instanceof Integer);
		assertEquals(0 / 14, value.getValue());
	}

	@Test
	public void testDividingNullAndDouble() {
		ValueWrapper value = new ValueWrapper(null);
		value.divide(17.5);
		assertTrue(value.getValue() instanceof Double);
		assertEquals(0 / 17.5, value.getValue());
	}

	@Test
	public void testDividingNullAndStringRepresentingAnInteger() {
		ValueWrapper value = new ValueWrapper(null);
		value.divide("14");
		assertTrue(value.getValue() instanceof Integer);
		assertEquals(0 / 14, value.getValue());
	}

	@Test
	public void testDividingNullAndStringRepresentingDouble() {
		ValueWrapper value = new ValueWrapper(null);
		value.divide("17.5");
		assertTrue(value.getValue() instanceof Double);
		assertEquals(0 / 17.5, value.getValue());
	}

	@Test
	public void testDividingNullAndStringRepresentingDouble2() {
		ValueWrapper value = new ValueWrapper(null);
		value.divide("1.5e2");
		assertTrue(value.getValue() instanceof Double);
		assertEquals(0 / 150., value.getValue());
	}

	@Test(expected = RuntimeException.class)
	public void testDividingNullAndNonParseableString() {
		ValueWrapper value = new ValueWrapper(null);
		value.divide("Marko");
	}

	@Test
	public void testComparingTwoIntegers() {
		ValueWrapper value = new ValueWrapper(17);
		assertTrue(value.numCompare(14) > 0);
	}

	@Test
	public void testComparingIntegerAndDouble() {
		ValueWrapper value = new ValueWrapper(14);
		assertTrue(value.numCompare(17.5) < 0);

	}

	@Test
	public void testComparingIntegerAndStringRepresentingAnInteger() {
		ValueWrapper value = new ValueWrapper(14);
		assertTrue(value.numCompare("14") == 0);
	}

	@Test
	public void testComparingIntegerAndStringRepresentingDouble() {
		ValueWrapper value = new ValueWrapper(14);
		assertTrue(value.numCompare("1e2") < 0);
	}

	@Test
	public void testComparingIntegerAndStringRepresentingDouble2() {
		ValueWrapper value = new ValueWrapper(17);
		assertTrue(value.numCompare("14.5") > 0);
	}

	@Test
	public void testComparingIntegerAndNull() {
		ValueWrapper value = new ValueWrapper(14);
		assertTrue(value.numCompare(null) > 0);
	}

	@Test(expected = RuntimeException.class)
	public void testComparingIntegerAndNonParsableString() {
		ValueWrapper value = new ValueWrapper(17);
		value.numCompare("Ankica");
	}

	@Test
	public void testComparingTwoDoubles() {
		ValueWrapper value = new ValueWrapper(14.5);
		assertTrue(value.numCompare(12.3) > 0);
	}

	@Test
	public void testComparingDoubleAndInteger() {
		ValueWrapper value = new ValueWrapper(17.5);
		assertTrue(value.numCompare(14) > 0);

	}

	@Test
	public void testComparingDoubleAndStringRepresentingAnInteger() {
		ValueWrapper value = new ValueWrapper(17.5);
		assertTrue(value.numCompare("20") < 0);
	}

	@Test
	public void testComparingDoubleAndStringRepresentingDouble() {
		ValueWrapper value = new ValueWrapper(17.5);
		assertTrue(value.numCompare("20e2") < 0);
	}

	@Test
	public void testComparingDoubleAndStringRepresentingDouble2() {
		ValueWrapper value = new ValueWrapper(17.5);
		assertTrue(value.numCompare("20.2") < 0);
	}

	@Test
	public void testComparingDoubleAndNull() {
		ValueWrapper value = new ValueWrapper(17.5);
		assertTrue(value.numCompare(null) > 0);
	}

	@Test(expected = RuntimeException.class)
	public void testComparingDoubleAndNonParsableString() {
		ValueWrapper value = new ValueWrapper(17.5);
		value.numCompare("Luka");
	}

	@Test
	public void testComparingStringAndDouble() {
		ValueWrapper value = new ValueWrapper("17");
		assertTrue(value.numCompare(14.5) > 0);
	}

	@Test
	public void testComparingStringAndInteger() {
		ValueWrapper value = new ValueWrapper("17");
		assertTrue(value.numCompare(18) < 0);
	}

	@Test
	public void testComparingStringAndStringRepresentingAnInteger() {
		ValueWrapper value = new ValueWrapper("17");
		assertTrue(value.numCompare("14") > 0);

	}

	@Test
	public void testComparingStringAndStringRepresentingDouble() {
		ValueWrapper value = new ValueWrapper("17");
		assertTrue(value.numCompare("1.7e1") == 0);
	}

	@Test
	public void testComparingStringAndStringRepresentingDouble2() {
		ValueWrapper value = new ValueWrapper("17.5");
		assertTrue(value.numCompare("20.3") < 0);
	}

	@Test
	public void testComparingStringAndNull() {
		ValueWrapper value = new ValueWrapper("17.5");
		assertTrue(value.numCompare("20.3") < 0);
	}

	@Test(expected = RuntimeException.class)
	public void testComparingStringAndNonParsableString() {
		ValueWrapper value = new ValueWrapper("17.5");
		value.numCompare("Lovro");
	}

	@Test
	public void testComparingTwoNullValues() {
		ValueWrapper value = new ValueWrapper(null);
		assertTrue(value.numCompare(null) == 0);
	}

	@Test
	public void testComparingNullAndInteger() {
		ValueWrapper value = new ValueWrapper(null);
		assertTrue(value.numCompare(14) < 0);
	}

	@Test
	public void testComparingNullAndDouble() {
		ValueWrapper value = new ValueWrapper(null);
		assertTrue(value.numCompare(-7.5) > 0);
	}

	@Test
	public void testComparingNullAndStringRepresentingAnInteger() {
		ValueWrapper value = new ValueWrapper(null);
		assertTrue(value.numCompare("-12") > 0);
	}

	@Test
	public void testComparingNullAndStringRepresentingDouble() {
		ValueWrapper value = new ValueWrapper(null);
		assertTrue(value.numCompare("12.5") < 0);
	}

	@Test
	public void testComparingNullAndStringRepresentingDouble2() {
		ValueWrapper value = new ValueWrapper(null);
		assertTrue(value.numCompare("1.5e2") < 0);
	}

	@Test(expected = RuntimeException.class)
	public void testComparingNullAndNonParseableString() {
		ValueWrapper value = new ValueWrapper(null);
		value.numCompare("Matteo");
	}

}
