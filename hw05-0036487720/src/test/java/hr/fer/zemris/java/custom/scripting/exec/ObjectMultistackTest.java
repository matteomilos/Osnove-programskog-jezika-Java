package hr.fer.zemris.java.custom.scripting.exec;

import static org.junit.Assert.*;

import org.junit.Test;

public class ObjectMultistackTest {

	@Test
	public void test() {
		ObjectMultistack stack = new ObjectMultistack();
		stack.push("ante", new ValueWrapper(1));
		stack.push("ante", new ValueWrapper(2));
		stack.push("ante", new ValueWrapper(3));
		stack.push("ante", new ValueWrapper(4));
		stack.push("ante", new ValueWrapper(5));
		stack.push("ante", new ValueWrapper(6));
		while(!stack.isEmpty("ante")){
			System.out.println(stack.pop("ante").toString());
		}

		
	}

}
