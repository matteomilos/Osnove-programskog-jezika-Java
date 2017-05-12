package hr.fer.zemris.java.gui.prim;

import static org.junit.Assert.*;

import org.junit.Test;

import hr.fer.zemris.java.gui.prim.PrimDemo.PrimListModel;

@SuppressWarnings("javadoc")
public class PrimDemoTest {

	PrimListModel model = new PrimListModel();

	@Test
	public void testPrime1() {
		assertTrue(model.isPrime(3));
	}

	@Test
	public void testPrime2() {
		assertTrue(model.isPrime(2));
	}

	@Test
	public void testPrime3() {
		assertTrue(model.isPrime(337));
	}

	@Test
	public void testNotPrime1() {
		assertFalse(model.isPrime(1));
	}

	@Test
	public void testNotPrime2() {
		assertFalse(model.isPrime(4));
	}

	@Test
	public void testNotPrime3() {
		assertFalse(model.isPrime(27));
	}

	@Test
	public void testNotPrime4() {
		assertFalse(model.isPrime(-7));
	}

}
