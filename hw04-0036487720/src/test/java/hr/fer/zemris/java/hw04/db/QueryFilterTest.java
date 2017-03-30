package hr.fer.zemris.java.hw04.db;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

@SuppressWarnings("javadoc")
public class QueryFilterTest {

	List<String> lines;
	StudentDatabase database;
	QueryParser parser;

	@Before
	public void init() {
		try {
			lines = Files.readAllLines(Paths.get("./prva.txt"), StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}

		database = new StudentDatabase(lines);
	}

	private List<StudentRecord> getRecords(StudentDatabase database, QueryParser parser) {
		List<StudentRecord> list = new ArrayList<>();

		if (parser.isDirectQuery()) {
			StudentRecord r = database.forJMBAG(parser.getQueriedJMBAG());
			list.add(r);
		} else {
			for (StudentRecord r : database.filter(new QueryFilter(parser.getQuery()))) {
				list.add(r);
			}
		}

		return list;
	}

	@Test
	public void testSimpleDirectQuery() {
		parser = new QueryParser("jmbag=\"0000000027\"");
		assertEquals(getRecords(database, parser).get(0).getJmbag(), "0000000027");
	}
	
	@Test
	public void testMoreComplexDirectQuery() {
		parser = new QueryParser("query     jmbag    =  \"0000000024\"		");
		assertEquals(getRecords(database, parser).get(0).getJmbag(), "0000000024");
	}
	
	@Test
	public void testComplexQuery() {
		parser = new QueryParser("jmbag<\"0000000027\" AnD lastName	LIKE	\"De*\"");
		assertEquals(getRecords(database, parser).get(0).getJmbag(), "0000000009");
	}
	
	@Test
	public void testReallyComplexQuery() {
		parser = new QueryParser("jmbag>\"0000000027\" AND jmbag LIKE \"000000003*\"  and  lastName>\"L\" and firstName LIKE \"Tom*\" ");
		assertEquals(getRecords(database, parser).get(0).getJmbag(), "0000000032");
	}
}
