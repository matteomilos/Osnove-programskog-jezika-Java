package hr.fer.zemris.math;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

@SuppressWarnings("javadoc")
public class ComplexTest {
	private static final double DELTA = 0.001;

	Complex c1;
	Complex c2;
	Complex c4;
	Complex c5;

	@Before
	public void initializeComplexNumbers() {
		c1 = new Complex(4, 2);
		c2 = new Complex(-1.4, 6);
		c4 = new Complex(0, 5);
		c5 = new Complex(0.312, 0);
	}

	@Test
	public void ComplexMagnitudeTest() {
		assertEquals(4.472, c1.module(), DELTA);
		assertEquals(6.161168, c2.module(), DELTA);
		assertEquals(5, c4.module(), DELTA);
		assertEquals(0.312, c5.module(), DELTA);
	}

	@Test
	public void ComplexNumberMultiplication() throws Exception {
		Complex first = c1.multiply(c2);

		assertEquals(first.getReal(), new Complex(-17.6, 21.2).getReal(), DELTA);

		assertEquals(first.getImaginary(), new Complex(-17.6, 21.2).getImaginary(), DELTA);

	}

}
