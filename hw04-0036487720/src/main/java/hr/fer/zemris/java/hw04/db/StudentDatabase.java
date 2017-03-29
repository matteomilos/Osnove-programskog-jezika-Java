package hr.fer.zemris.java.hw04.db;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw04.collections.SimpleHashtable;

public class StudentDatabase {

	private SimpleHashtable<String, StudentRecord> studentTable;
	// protected for purposes of testing
	protected List<StudentRecord> studentRecords;

	public StudentDatabase(List<String> databaseContent) {
		if (databaseContent == null) {
			throw new IllegalArgumentException();
		}
		
		studentTable = new SimpleHashtable<>();
		studentRecords = new ArrayList<>();
		
		for (String line : databaseContent) {
			String[] lineContent = line.split("\\t");
			
			if (lineContent.length != 4) {
				throw new IllegalStateException("Database content is in invalid format.");
			}
			//@formatter:off
			StudentRecord record = new StudentRecord(lineContent[0], 
													 lineContent[1], 
													 lineContent[2],
													 Integer.parseInt(lineContent[3])
			);
			//@formatter:on
			studentTable.put(lineContent[0], record);
			studentRecords.add(record);
		}

	}

	public StudentRecord forJMBAG(String jmbag) {
		return studentTable.get(jmbag);
	}

	public List<StudentRecord> filter(IFilter filter) {
		List<StudentRecord> temporaryList = new ArrayList<>();
		
		for (StudentRecord studentRecord : studentRecords) {
			if (filter.accepts(studentRecord)) {
				temporaryList.add(studentRecord);
			}
		}
		return temporaryList;
	}
}
