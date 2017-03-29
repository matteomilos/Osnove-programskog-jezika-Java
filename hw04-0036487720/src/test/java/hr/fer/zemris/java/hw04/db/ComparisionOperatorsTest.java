package hr.fer.zemris.java.hw04.db;

import static org.junit.Assert.*;

import org.junit.Test;

public class ComparisionOperatorsTest {

	@Test
	public void test() {
		IComparisonOperator oper = ComparisonOperators.LIKE;
		System.out.println(oper.satisfied("Zagreb", "Aba*")); // false
		System.out.println(oper.satisfied("AAA", "AA*AA")); // false
		System.out.println(oper.satisfied("AAAA", "AAAA"));
		System.out.println(oper.satisfied("AAkAA", "AA*AA"));
		System.out.println(oper.satisfied("AAčAA", "AA*AA"));
		System.out.println(oper.satisfied("AA8AA", "AA*AA"));
		System.out.println(oper.satisfied("AAasfsafsAA", "AA*AA"));
		System.out.println(oper.satisfied("AAAA", "AA*AA"));
		oper = ComparisonOperators.LESS;
		System.out.println(oper.satisfied("Ana", "Jasna")); 
		String[] array = "nesto and ali je AnD ovo ipak AND ovako".split("[aA][nN][dD]");
		for (String string : array) {
			System.out.println(string);
		}
		boolean jel = "jmbag   =  \"05464354\"      ".matches("jmbag\\s*=\\s*\\\"[0-9]+\\\"");
		System.out.println(jel);
		System.out.println();
	}

}
