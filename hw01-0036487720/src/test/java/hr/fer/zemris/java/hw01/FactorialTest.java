package hr.fer.zemris.java.hw01;

import static hr.fer.zemris.java.hw01.Factorial.faktorijela;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class FactorialTest {

	@Test
	public void ispravnoRacunanjeFaktorijele() {
		long faktorijela1 = faktorijela(1);
		assertEquals(1, faktorijela1);
		long faktorijela2 = faktorijela(7);
		assertEquals(5040, faktorijela2);
		long faktorijela3 = faktorijela(12);
		assertEquals(479001600, faktorijela3);
	}

}
