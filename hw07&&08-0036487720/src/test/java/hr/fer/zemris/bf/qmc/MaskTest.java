package hr.fer.zemris.bf.qmc;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

@SuppressWarnings("javadoc")
public class MaskTest {

	@Test(expected = IllegalArgumentException.class)
	public void testIfArrayOfValuesIsNull() {
		Set<Integer> indexes = new HashSet<>();
		indexes.add(2);
		new Mask(null, indexes, false);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNumberOfVariablesLessThanOne() {
		new Mask(2, 0, false);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testIfSetIsEmpty() {
		Set<Integer> indexes = new HashSet<>();
		new Mask(new byte[] { 0, 1 }, indexes, false);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testIfSetIsNull() {
		new Mask(new byte[] { 0, 1 }, null, false);
	}

	@Test
	public void testCountOfOnesIfIsZero() {
		Set<Integer> indexes = new HashSet<>();
		indexes.add(2);
		Mask mask = new Mask(new byte[] { 0, 2, 0, 2 }, indexes, false);
		assertEquals(0, mask.countOfOnes());
	}

	@Test
	public void testCountOfOnesIfIsOne() {
		Set<Integer> indexes = new HashSet<>();
		indexes.add(2);
		Mask mask = new Mask(new byte[] { 0, 0, 2, 1 }, indexes, false);
		assertEquals(1, mask.countOfOnes());
	}

	@Test
	public void testCountOfOnesIfIsMoreThanOne() {
		Set<Integer> indexes = new HashSet<>();
		indexes.add(2);
		Mask mask = new Mask(new byte[] { 1, 2, 1, 0, 1, 1, 0, 1 }, indexes, false);
		assertEquals(5, mask.countOfOnes());
	}

	@Test
	public void testSize1() {
		Set<Integer> indexes = new HashSet<>();
		indexes.add(2);
		Mask mask = new Mask(new byte[] { 1, 2, 1, 0, 1 }, indexes, false);
		assertEquals(5, mask.size());
	}

	@Test
	public void testSize2() {
		Set<Integer> indexes = new HashSet<>();
		indexes.add(2);
		Mask mask = new Mask(new byte[] { 1, 2, 1, 0, 1, 1, 0, 1 }, indexes, false);
		assertEquals(8, mask.size());
	}

	@Test
	public void testGetValueAt1() {
		Set<Integer> indexes = new HashSet<>();
		indexes.add(2);
		Mask mask = new Mask(new byte[] { 1, 2, 1, 0, 1, 1, 0, 1 }, indexes, false);
		assertEquals(2, mask.getValueAt(1));
	}

	@Test
	public void testGetValueAt2() {
		Set<Integer> indexes = new HashSet<>();
		indexes.add(2);
		Mask mask = new Mask(new byte[] { 1, 2, 1, 0, 1, 1, 0, 1 }, indexes, false);
		assertEquals(0, mask.getValueAt(3));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCombineMasksWithDifferentLengths() {
		Set<Integer> indexes = new HashSet<>();
		indexes.add(2);
		Mask mask = new Mask(new byte[] { 1, 0 }, indexes, false);
		Set<Integer> indexes2 = new HashSet<>();
		indexes2.add(3);
		Mask mask2 = new Mask(new byte[] { 0, 0, 1 }, indexes2, false);
		mask.combineWith(mask2);
	}

	@Test
	public void testCombineUncombineableMasks() {
		Set<Integer> indexes = new HashSet<>();
		indexes.add(2);
		Mask mask = new Mask(new byte[] { 1, 0 }, indexes, false);
		Set<Integer> indexes2 = new HashSet<>();
		indexes2.add(1);
		Mask mask2 = new Mask(new byte[] { 0, 1 }, indexes2, false);
		assertEquals(false, mask.combineWith(mask2).isPresent());
	}

	@Test
	public void testCombineCombineableMasks() {
		Set<Integer> indexes = new HashSet<>();
		indexes.add(2);
		Mask mask = new Mask(new byte[] { 1, 0 }, indexes, false);
		Set<Integer> indexes2 = new HashSet<>();
		indexes2.add(31);
		Mask mask2 = new Mask(new byte[] { 1, 1 }, indexes2, false);
		assertEquals(true, mask.combineWith(mask2).isPresent());
	}

}
