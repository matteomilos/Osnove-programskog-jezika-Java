package hr.fer.zemris.java.hw01;

import static hr.fer.zemris.java.hw01.Factorial.factorialFunction;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class FactorialTest {

	@Test
	public void factorialOf0() {
		long factorial = factorialFunction(1);
		assertEquals(1, factorial);

	}

	@Test
	public void factorialOf21() {
		long factorial = factorialFunction(12);
		assertEquals(479001600, factorial);
	}

	@Test
	public void factorialOfMinus5() {
		long factorial4 = factorialFunction(-5);
		assertEquals(-1, factorial4);
	}

	@Test
	public void factorialOf20() {
		long factorial = factorialFunction(20);
		assertEquals((long) 2.43290200817664e18, factorial);
	}

	@Test
	public void factorialOf30() {
		long factorial = factorialFunction(30);
		assertEquals(-1, factorial);
	}

}
