package hr.fer.zemris.java.custom.scripting.exec;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

@SuppressWarnings("javadoc")
public class ObjectMultistackTest {

	ObjectMultistack stack;

	@Before
	public void initialization() {
		stack = new ObjectMultistack();
	}

	@Test
	public void testPeekWithOnlyOneValueToKey() {
		stack.push("Marko", new ValueWrapper(17));
		assertEquals(17, stack.peek("Marko").getValue());
	}

	@Test
	public void testPopWithOnlyOneValueToKey() {
		stack.push("Marko", new ValueWrapper(17));
		assertEquals(17, stack.pop("Marko").getValue());
	}

	@Test
	public void testPeekWithPushingMoreValuesToSameKey() {
		stack.push("Ante", new ValueWrapper(12));
		stack.push("Ivan", new ValueWrapper(13));
		stack.push("Ante", new ValueWrapper(14));
		stack.push("Luka", new ValueWrapper(15));
		stack.push("Ante", new ValueWrapper(16));
		stack.push("Ante", new ValueWrapper(17));
		stack.push("Ante", new ValueWrapper(18));
		assertEquals(18, stack.peek("Ante").getValue());
	}

	@Test
	public void testPopWithPushingMoreValuesToSameKey() {
		stack.push("Ante", new ValueWrapper(12));
		stack.push("Ivan", new ValueWrapper(13));
		stack.push("Ante", new ValueWrapper(14));
		stack.push("Luka", new ValueWrapper(15));
		stack.push("Ante", new ValueWrapper(16));
		stack.push("Ante", new ValueWrapper(17));
		stack.push("Ante", new ValueWrapper(18));
		assertEquals(18, stack.pop("Ante").getValue());
	}

	@Test(expected = NoSuchElementException.class)
	public void testPeekOnEmptyStack() {
		stack.peek("Ante");
	}

	@Test(expected = NoSuchElementException.class)
	public void testPopOnEmptyStack() {
		stack.pop("Ante");
	}

	@Test
	public void testIsEmptyOnEmptyStack() {
		stack.push("Marko", new ValueWrapper(17));
		assertTrue(stack.isEmpty("Ante"));
	}

	@Test
	public void testIsEmptyOnNonEmptyStack() {
		stack.push("Marko", new ValueWrapper(17));
		assertFalse(stack.isEmpty("Marko"));
	}

}
