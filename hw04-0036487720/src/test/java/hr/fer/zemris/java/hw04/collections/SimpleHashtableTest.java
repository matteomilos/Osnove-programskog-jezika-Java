package hr.fer.zemris.java.hw04.collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

@SuppressWarnings("javadoc")
public class SimpleHashtableTest {

	SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);

	@Before
	public void initialization() {
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5);
	}

	@Test
	public void testPut() {
		assertEquals(4, examMarks.size());
	}

	@Test
	public void testPutExistingKeyDifferentValue() {
		assertEquals(5, (int) examMarks.get("Ivana"));
	}

	@Test
	public void testGettingNonExistingKey() {
		assertNull(examMarks.get("Marko"));
	}

	@Test
	public void testGettingNullKey() {
		assertNull(examMarks.get(null));
	}

	@Test
	public void testGettingExistingKey() {
		assertEquals(5, (int) examMarks.get("Kristina"));
	}

	@Test
	public void testContainsNonExistingKey() {
		assertFalse(examMarks.containsKey("Marko"));
	}

	@Test
	public void testContainsNullKey() {
		assertFalse(examMarks.containsKey(null));
	}

	@Test
	public void testContainsExistingKey() {
		assertTrue(examMarks.containsKey("Jasna"));
	}

	@Test
	public void testSizeWithPuttingSameKeys() {
		assertEquals(4, examMarks.size());
	}

	@Test
	public void testContainsNonExistingValue() {
		assertFalse(examMarks.containsValue(4));
	}

	@Test
	public void testContainsNonExistingNullValue() {
		assertFalse(examMarks.containsValue(null));
	}

	@Test
	public void testContainsExistingNullValue() {
		examMarks.put("Matteo", null);
		assertTrue(examMarks.containsValue(null));
	}

	@Test
	public void testContainsExistingValue() {
		assertTrue(examMarks.containsValue(5));
	}

	@Test
	public void testRemovalNonExistingKey() {
		examMarks.remove("Luka");
		assertEquals(4, examMarks.size());
	}

	@Test
	public void testRemovalNullKey() {
		examMarks.remove(null);
		assertEquals(4, examMarks.size());
	}

	@Test
	public void testRemovalExistingKey() {
		examMarks.remove("Jasna");
		assertEquals(3, examMarks.size());
		assertFalse(examMarks.containsKey("Jasna"));
	}

	@Test
	public void testIsEmptyWithEmptyTable() {
		examMarks.remove("Jasna");
		examMarks.remove("Ante");
		examMarks.remove("Ivana");
		examMarks.remove("Kristina");
		assertTrue(examMarks.isEmpty());
	}

	@Test
	public void testIsEmptyWithNonEmptyTable() {
		assertFalse(examMarks.isEmpty());
	}
}