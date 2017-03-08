package hr.fer.zemris.java.hw01;

import static hr.fer.zemris.java.hw01.Factorial.factorialFunction;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class FactorialTest {

	@Test
	public void correctCalculating1() {
		long factorial = factorialFunction(1);
		assertEquals(1, factorial);

	}

	public void correctCalculating2() {
		long factorial = factorialFunction(12);
		assertEquals(479001600, factorial);
	}

	public void correctCalculating3() {
		long factorial4 = factorialFunction(-5);
		assertEquals(-1, factorial4);
	}

	public void correctCalculating4() {
		long factorial = factorialFunction(20);
		assertEquals((long) 2.43290200817664e18, factorial);
	}

	public void correctCalculating5() {
		long factorial = factorialFunction(30);
		assertEquals(-1, factorial);
	}

}
