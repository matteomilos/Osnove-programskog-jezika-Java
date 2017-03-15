package hr.fer.zemris.java.custom.collections;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

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
		boolean prepoznat = false;
		try {
			nonFullyFilledArray.add(null);
		} catch (IllegalArgumentException exc) {
			prepoznat = true;
		}
		assertTrue(prepoznat);
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
		boolean prepoznat = false;
		try {
			nonFullyFilledArray.get(nonFullyFilledArray.size()+2);
		} catch (IndexOutOfBoundsException exc) {
			prepoznat = true;
		}
		assertTrue(prepoznat);
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
		boolean prepoznat = false;
		try {
			nonFullyFilledArray.insert(null, 3);
		} catch (IllegalArgumentException exc) {
			prepoznat = true;
		}
		assertTrue(prepoznat);
	}

	@Test
	public void testInsertAtIndexOutOfBounds() {
		boolean prepoznat = false;
		try {
			nonFullyFilledArray.insert("Z", nonFullyFilledArray.size()+2);
		} catch (IndexOutOfBoundsException exc) {
			prepoznat = true;
		}
		assertTrue(prepoznat);
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
		fullyFilledArray.remove(fullyFilledArray.size()-1);
		assertEquals(4, fullyFilledArray.size());
		assertFalse(fullyFilledArray.contains("Sesvete"));
	}
	
	@Test
	public void testRemoveIfIndexIsOutOfBounds() {
		boolean prepoznat = false;
		try{
			fullyFilledArray.remove(fullyFilledArray.size()+2);
		}
		catch(IndexOutOfBoundsException exc){
			prepoznat = true;

		}
		assertTrue(prepoznat);
	}

}
