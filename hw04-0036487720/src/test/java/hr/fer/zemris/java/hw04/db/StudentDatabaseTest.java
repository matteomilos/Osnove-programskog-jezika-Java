package hr.fer.zemris.java.hw04.db;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

@SuppressWarnings("javadoc")
public class StudentDatabaseTest {

	StudentDatabase database;

	@Before
	public void init() throws IOException {
		List<String> lines = Files.readAllLines(Paths.get("./prva.txt"), StandardCharsets.UTF_8);
		database = new StudentDatabase(lines);
	}

	@Test
	public void testCheckExistingJmbag() {
		assertEquals("Akšamović Marin", database.forJMBAG("0000000001").toString());
	}

	@Test
	public void testCheckExistingJmbagWithTwoSurnames() {
		StudentRecord expected = new StudentRecord("0000000031", "Krušelj Posavec", "Bojan", 4);
		assertEquals(expected, database.forJMBAG("0000000031"));
	}

	@Test
	public void testFilterFullList() {
		assertEquals(database.studentRecords, database.filter(t -> true));
	}
	
	@Test
	public void testFilterEmptyList() {
		assertEquals(new ArrayList<StudentRecord>(), database.filter(t -> false));
	}

	@Test
	public void testIndexQuery() {
		assertEquals("Akšamović Marin", database.forJMBAG("0000000001").toString());
		assertEquals("Kos-Grabar Ivo", database.forJMBAG("0000000029").toString());
		assertEquals("Krušelj Posavec Bojan", database.forJMBAG("0000000031").toString());
	}
	
	@Test(expected=QueryParserException.class)
	public void testWrongQuery() {
		@SuppressWarnings("unused")
		QueryParser parser = new QueryParser(" nesto =\"Marin\"");
	}
	
	@Test(expected=QueryParserException.class)
	public void testWrongQuery2() {
		@SuppressWarnings("unused")
		QueryParser parser = new QueryParser(" firstname =\"Marin\"");
	}
	@Test(expected=QueryParserException.class)
	public void testWrongQuery3() {
		@SuppressWarnings("unused")
		QueryParser parser = new QueryParser(" firstName liKe \"M* \"");
	}
	
	@Test(expected=QueryParserException.class)
	public void testWrongQuery4() {
		@SuppressWarnings("unused")
		QueryParser parser = new QueryParser(" jmbag =lastName");
	}
	
}
