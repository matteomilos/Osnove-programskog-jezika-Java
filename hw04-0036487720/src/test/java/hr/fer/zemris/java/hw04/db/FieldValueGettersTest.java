package hr.fer.zemris.java.hw04.db;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

@SuppressWarnings("javadoc")
public class FieldValueGettersTest {

	StudentRecord record = new StudentRecord("0000000015", "Glavinić Pecotić", "Kristijan", 4);
	StudentRecord record2 = new StudentRecord("0000000003", "Brezović", "Jusufadis", 4);
	StudentRecord record3 = new StudentRecord("0000000015", "Marković", "Ivan Luka", 4);
	StudentRecord record4 = new StudentRecord("0000000015", "Glavinić Pecotić", "Ivan Marko", 4);

	@Test
	public void testTwoLastNames() {
		assertEquals("Kristijan", FieldValueGetters.FIRST_NAME.get(record));
		assertEquals("Glavinić Pecotić", FieldValueGetters.LAST_NAME.get(record));
		assertEquals("0000000015", FieldValueGetters.JMBAG.get(record));
	}

	@Test
	public void LongName() {
		assertEquals("Jusufadis", FieldValueGetters.FIRST_NAME.get(record2));
		assertEquals("Brezović", FieldValueGetters.LAST_NAME.get(record2));
		assertEquals("0000000003", FieldValueGetters.JMBAG.get(record2));
	}

	@Test
	public void testTwoFirstNames() {
		assertEquals("Ivan Luka", FieldValueGetters.FIRST_NAME.get(record3));
		assertEquals("Marković", FieldValueGetters.LAST_NAME.get(record3));
		assertEquals("0000000015", FieldValueGetters.JMBAG.get(record3));
	}

	@Test
	public void testTwoFirstAndTwoLastNames() {
		assertEquals("Ivan Marko", FieldValueGetters.FIRST_NAME.get(record4));
		assertEquals("Glavinić Pecotić", FieldValueGetters.LAST_NAME.get(record4));
		assertEquals("0000000015", FieldValueGetters.JMBAG.get(record4));
	}
}
