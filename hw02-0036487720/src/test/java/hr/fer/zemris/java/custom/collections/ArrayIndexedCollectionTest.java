package hr.fer.zemris.java.custom.collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ArrayIndexedCollectionTest {
	ArrayIndexedCollection emptyArray;
	ArrayIndexedCollection fullyFilledArray;
	ArrayIndexedCollection nonFullyFilledArray;

	@Before
	public void initializationOfArrayIndexedCollections() {
		emptyArray = new ArrayIndexedCollection();
		fullyFilledArray = new ArrayIndexedCollection(5);
		nonFullyFilledArray = new ArrayIndexedCollection();

		fullyFilledArray.add("Zagreb");
		fullyFilledArray.add("Osijek");
		fullyFilledArray.add("Rijeka");
		fullyFilledArray.add("Split");
		fullyFilledArray.add("Sesvete");

		nonFullyFilledArray.add("Zagreb");
		nonFullyFilledArray.add("Osijek");
		nonFullyFilledArray.add("Rijeka");
		nonFullyFilledArray.add("Split");
		nonFullyFilledArray.add("Sesvete");
	}

	@Rule
	public ExpectedException thrownExc = ExpectedException.none();

	@Test
	public void testAddToEmptyArray() {
		emptyArray.add("Zagreb");
		assertTrue(emptyArray.contains("Zagreb"));
		assertEquals(1, emptyArray.size());
	}

	@Test
	public void testAddToFullArray() {
		fullyFilledArray.add("Varaždin");
		assertTrue(fullyFilledArray.contains("Varaždin"));
		assertEquals(6, fullyFilledArray.size());
		assertEquals(10, fullyFilledArray.getCapacity());
	}

	@Test
	public void testAddNullValue() {
		thrownExc.expect(IllegalArgumentException.class);
		nonFullyFilledArray.add(null);
	}

	@Test
	public void testGetIfIndexIsZero() {
		assertEquals("Zagreb", nonFullyFilledArray.get(0));
	}

	@Test
	public void testGetIfIndexIsGreaterThanZero() {
		assertEquals("Rijeka", nonFullyFilledArray.get(2));
	}

	@Test
	public void testGetIfIndexIsSizeMinusOne() {
		assertEquals("Sesvete", nonFullyFilledArray.get(nonFullyFilledArray.size() - 1));
	}

	@Test
	public void testGetIfIndexIsOutOfBounds() {
		thrownExc.expect(IndexOutOfBoundsException.class);
		nonFullyFilledArray.get(nonFullyFilledArray.size() + 2);
	}

	@Test
	public void testClearEmptyArray() {
		emptyArray.clear();
		assertTrue(emptyArray.isEmpty());
		assertEquals(0, emptyArray.size());
	}

	@Test
	public void testClearArrayContainingElements() {
		fullyFilledArray.clear();
		assertTrue(fullyFilledArray.isEmpty());
		assertEquals(0, fullyFilledArray.size());
	}

	@Test
	public void testInsertNullValue() {
		thrownExc.expect(IllegalArgumentException.class);
		nonFullyFilledArray.insert(null, 3);
	}

	@Test
	public void testInsertAtIndexOutOfBounds() {
		thrownExc.expect(IndexOutOfBoundsException.class);
		nonFullyFilledArray.insert("Z", nonFullyFilledArray.size() + 2);
	}

	@Test
	public void testInsertToEmptyArray() {
		emptyArray.insert("Zagreb", 0);
		assertTrue(emptyArray.contains("Zagreb"));
		assertEquals(1, emptyArray.size());
	}

	@Test
	public void testInsertToFullArray() {
		fullyFilledArray.insert("Zadar", 2);
		assertTrue(fullyFilledArray.contains("Zadar"));
		assertEquals("Zadar", fullyFilledArray.get(2));
		assertEquals(6, fullyFilledArray.size());
		assertEquals(10, fullyFilledArray.getCapacity());
	}

	@Test
	public void testIndexOfContainingElement() {
		assertEquals(0, fullyFilledArray.indexOf("Zagreb"));
		assertEquals(2, fullyFilledArray.indexOf("Rijeka"));
	}

	@Test
	public void testIndexOfNonContainingElement() {
		assertEquals(-1, fullyFilledArray.indexOf("Pula"));
	}

	@Test
	public void testRemoveIfIndexIsZero() {
		fullyFilledArray.remove(0);
		assertEquals(4, fullyFilledArray.size());
		assertFalse(fullyFilledArray.contains("Zagreb"));
	}

	@Test
	public void testRemoveIfIndexIsSizeIsMinusOne() {
		fullyFilledArray.remove(fullyFilledArray.size() - 1);
		assertEquals(4, fullyFilledArray.size());
		assertFalse(fullyFilledArray.contains("Sesvete"));
	}

	@Test
	public void testRemoveIfIndexIsOutOfBounds() {
		thrownExc.expect(IndexOutOfBoundsException.class);
		fullyFilledArray.remove(fullyFilledArray.size() + 2);
	}

}
