package hr.fer.zemris.java.hw04.db;

import static org.junit.Assert.*;

import org.junit.Test;

@SuppressWarnings("javadoc")
public class ComparisionOperatorsTest {

	IComparisonOperator less = ComparisonOperators.LESS;
	IComparisonOperator lessOrEquals = ComparisonOperators.LESS_OR_EQUALS;
	IComparisonOperator greater = ComparisonOperators.GREATER;
	IComparisonOperator greaterOrEquals = ComparisonOperators.GREATER_OR_EQUALS;
	IComparisonOperator equals = ComparisonOperators.EQUALS;
	IComparisonOperator notEquals = ComparisonOperators.NOT_EQUALS;
	IComparisonOperator like = ComparisonOperators.LIKE;

	@Test
	public void testLessWithFirstFewCharachtersSame(){
		assertTrue(less.satisfied("Ante", "Anto"));
	}

	@Test
	public void testLessWithCompletelyDifferentStrings(){
		assertTrue(less.satisfied("Ante", "Tomo"));
	}
	
	@Test
	public void testLessWithFirstFewCharachtersSame2(){
		assertFalse(less.satisfied("Anto", "Ante"));
	}

	@Test
	public void testLessWithCompletelyDifferentStrings2(){
		assertFalse(less.satisfied("Tomo", "Ante"));
	}
	
	@Test
	public void testLessCompletelyIdenticalStrings(){
		assertFalse(less.satisfied("Ante", "Ante"));
	}

	@Test
	public void testLessCompletelyIdenticalStrings2(){
		assertFalse(less.satisfied("Željko", "Željko"));
	}
	
	@Test
	public void testLessWithCroatianCharacters(){
		assertTrue(less.satisfied("čarape", "dan"));
	}

	@Test
	public void testLessWithCroatianCharacters2(){
		assertFalse(less.satisfied("župa", "šipka"));
	}
	
	@Test
	public void testGreaterWithFirstFewCharachtersSame(){
		assertFalse(greater.satisfied("Ante", "Anto"));
	}

	@Test
	public void testGreaterWithCompletelyDifferentStrings(){
		assertFalse(greater.satisfied("Ante", "Tomo"));
	}
	
	@Test
	public void testGreaterWithFirstFewCharachtersSame2(){
		assertTrue(greater.satisfied("Anto", "Ante"));
	}

	@Test
	public void testGreaterWithCompletelyDifferentStrings2(){
		assertTrue(greater.satisfied("Tomo", "Ante"));
	}
	
	@Test
	public void testGreaterCompletelyIdenticalStrings(){
		assertFalse(greater.satisfied("Ante", "Ante"));
	}

	@Test
	public void testGreaterCompletelyIdenticalStrings2(){
		assertFalse(greater.satisfied("Željko", "Željko"));
	}
	
	@Test
	public void testGreaterWithCroatianCharacters(){
		assertFalse(greater.satisfied("čarape", "dan"));
	}

	@Test
	public void testGreaterWithCroatianCharacters2(){
		assertTrue(greater.satisfied("župa", "šipka"));
	}
	
	@Test
	public void testLessOrEqualsWithFirstFewCharachtersSame(){
		assertTrue(lessOrEquals.satisfied("Ante", "Anto"));
	}

	@Test
	public void testLessOrEqualsWithCompletelyDifferentStrings(){
		assertTrue(lessOrEquals.satisfied("Ante", "Tomo"));
	}
	
	@Test
	public void testLessOrEqualsWithFirstFewCharachtersSame2(){
		assertFalse(lessOrEquals.satisfied("Anto", "Ante"));
	}

	@Test
	public void testLessOrEqualsWithCompletelyDifferentStrings2(){
		assertFalse(lessOrEquals.satisfied("Tomo", "Ante"));
	}
	
	@Test
	public void testLessOrEqualsCompletelyIdenticalStrings(){
		assertTrue(lessOrEquals.satisfied("Ante", "Ante"));
	}

	@Test
	public void testLessOrEqualsCompletelyIdenticalStrings2(){
		assertTrue(lessOrEquals.satisfied("Željko", "Željko"));
	}
	
	@Test
	public void testLessOrEqualsWithCroatianCharacters(){
		assertTrue(lessOrEquals.satisfied("čarape", "dan"));
	}

	@Test
	public void testLessOrEqualsWithCroatianCharacters2(){
		assertFalse(lessOrEquals.satisfied("župa", "šipka"));
	}
	
	@Test
	public void testGreaterOrEqualsWithFirstFewCharachtersSame(){
		assertFalse(greaterOrEquals.satisfied("Ante", "Anto"));
	}

	@Test
	public void testGreaterOrEqualsWithCompletelyDifferentStrings(){
		assertFalse(greaterOrEquals.satisfied("Ante", "Tomo"));
	}
	
	@Test
	public void testGreaterOrEqualsWithFirstFewCharachtersSame2(){
		assertTrue(greaterOrEquals.satisfied("Anto", "Ante"));
	}

	@Test
	public void testGreaterOrEqualsWithCompletelyDifferentStrings2(){
		assertTrue(greaterOrEquals.satisfied("Tomo", "Ante"));
	}
	
	@Test
	public void testGreaterOrEqualsCompletelyIdenticalStrings(){
		assertTrue(greaterOrEquals.satisfied("Ante", "Ante"));
	}

	@Test
	public void testGreaterOrEqualsCompletelyIdenticalStrings2(){
		assertTrue(greaterOrEquals.satisfied("Željko", "Željko"));
	}
	
	@Test
	public void testGreaterOrEqualsWithCroatianCharacters(){
		assertFalse(greaterOrEquals.satisfied("čarape", "dan"));
	}

	@Test
	public void testGreaterOrEqualsWithCroatianCharacters2(){
		assertTrue(greaterOrEquals.satisfied("župa", "šipka"));
	}
	
	@Test
	public void testEqualsWithFirstFewCharachtersSame(){
		assertFalse(equals.satisfied("Ante", "Anto"));
	}

	@Test
	public void testEqualsWithCompletelyDifferentStrings(){
		assertFalse(equals.satisfied("Ante", "Tomo"));
	}
	
	@Test
	public void testEqualsWithFirstFewCharachtersSame2(){
		assertFalse(equals.satisfied("Anto", "Ante"));
	}

	@Test
	public void testEqualsWithCompletelyDifferentStrings2(){
		assertFalse(equals.satisfied("Tomo", "Ante"));
	}
	
	@Test
	public void testEqualsCompletelyIdenticalStrings(){
		assertTrue(equals.satisfied("Ante", "Ante"));
	}

	@Test
	public void testEqualsCompletelyIdenticalStrings2(){
		assertTrue(equals.satisfied("Željko", "Željko"));
	}
	
	@Test
	public void testEqualsWithCroatianCharacters(){
		assertFalse(equals.satisfied("čarape", "dan"));
	}

	@Test
	public void testEqualsWithCroatianCharacters2(){
		assertFalse(equals.satisfied("župa", "šipka"));
	}
	
	@Test
	public void testNotEqualsWithFirstFewCharachtersSame(){
		assertTrue(notEquals.satisfied("Ante", "Anto"));
	}

	@Test
	public void testNotEqualsWithCompletelyDifferentStrings(){
		assertTrue(notEquals.satisfied("Ante", "Tomo"));
	}
	
	@Test
	public void testNotEqualsWithFirstFewCharachtersSame2(){
		assertTrue(notEquals.satisfied("Anto", "Ante"));
	}

	@Test
	public void testNotEqualsWithCompletelyDifferentStrings2(){
		assertTrue(notEquals.satisfied("Tomo", "Ante"));
	}
	
	@Test
	public void testNotEqualsCompletelyIdenticalStrings(){
		assertFalse(notEquals.satisfied("Ante", "Ante"));
	}

	@Test
	public void testNotEqualsCompletelyIdenticalStrings2(){
		assertFalse(notEquals.satisfied("Željko", "Željko"));
	}
	
	@Test
	public void testNotEqualsWithCroatianCharacters(){
		assertTrue(notEquals.satisfied("čarape", "dan"));
	}

	@Test
	public void testNotEqualsWithCroatianCharacters2(){
		assertTrue(notEquals.satisfied("župa", "šipka"));
	}
	
	@Test
	public void testLikeWithoutAsterisk(){
		assertFalse(like.satisfied("župa", "šipka"));
	}
	
	@Test
	public void testLikeWithoutAsterisk2(){
		assertTrue(like.satisfied("Ante", "Ante"));
	}
	
	@Test
	public void testLikeWithAsteriskInTheBeginning(){
		assertTrue(like.satisfied("Ante", "*Ante"));
	}
	
	@Test
	public void testLikeWithAsteriskInTheBeginning2(){
		assertTrue(like.satisfied("Ante", "*nte"));
	}
	
	@Test
	public void testLikeWithAsteriskInTheBeginning3(){
		assertFalse(like.satisfied("Ante", "*Anto"));
	}
	
	@Test
	public void testLikeWithAsteriskInTheMiddle(){
		assertTrue(like.satisfied("Ante", "An*te"));
	}
	
	@Test
	public void testLikeWithAsteriskInTheMiddle2(){
		assertTrue(like.satisfied("Ante", "A*te"));
	}
	
	@Test
	public void testLikeWithAsteriskInTheMiddle3(){
		assertFalse(like.satisfied("Ante", "An*to"));
	}
	
	@Test
	public void testLikeWithAsteriskInTheEnd(){
		assertTrue(like.satisfied("Ante", "Ante*"));
	}
	
	@Test
	public void testLikeWithAsteriskInTheEnd2(){
		assertTrue(like.satisfied("Ante", "Ant*"));
	}
	
	@Test
	public void testLikeWithAsteriskInTheEnd3(){
		assertFalse(like.satisfied("Ante", "Anto*"));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testMoreThanOneAsterisk(){
		assertTrue(like.satisfied("Ante", "**Ante"));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testMoreThanOneAsterisk2(){
		assertTrue(like.satisfied("Ante", "*A*nte"));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testMoreThanOneAsterisk3(){
		assertTrue(like.satisfied("Ante", "*Ante*"));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testMoreThanOneAsterisk4(){
		assertTrue(like.satisfied("Ante", "An**te"));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testMoreThanOneAsterisk5(){
		assertTrue(like.satisfied("Ante", "*An*te*"));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testMoreThanOneAsterisk6(){
		assertTrue(like.satisfied("Ante", "Ante**"));
	}
}
