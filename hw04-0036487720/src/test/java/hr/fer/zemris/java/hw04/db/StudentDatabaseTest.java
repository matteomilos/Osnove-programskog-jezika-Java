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

public class StudentDatabaseTest {

	StudentDatabase database;

	@Before
	public void init() throws IOException {
		List<String> lines = Files.readAllLines(Paths.get("./database.txt"), StandardCharsets.UTF_8);
		database = new StudentDatabase(lines);
	}

	@Test
	public void testCheckExistingJmbag() {
		StudentRecord expected = new StudentRecord("0000000001", "Akšamović", "Marin", 2);
		assertEquals("Akšamović Marin", database.forJMBAG("0000000001").toString());
	}

	@Test
	public void testCheckExistingJmbagWithTwoSurnames() {
		StudentRecord bzvz = new StudentRecord("0000000031", "Krušelj Posavec", "Bojan", 4);
		assertEquals(bzvz, database.forJMBAG("0000000031"));
	}

	@Test
	public void testFilterFullList() {
		assertEquals(database.studentRecords, database.filter(t -> true));
	}
	
	@Test
	public void testFilterEmptyList() {
		assertEquals(new ArrayList<StudentRecord>(), database.filter(t -> false));
	}

}
