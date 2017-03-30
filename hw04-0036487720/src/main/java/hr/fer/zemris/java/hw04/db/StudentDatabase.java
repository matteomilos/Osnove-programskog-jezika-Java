package hr.fer.zemris.java.hw04.db;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw04.collections.SimpleHashtable;

/**
 * Class <code>StudentDatabase</code> encapsulates data of database in a way
 * that it stores each record in its hash table(map), which is instance of
 * {@linkplain SimpleHashtable} and in a list.
 * 
 * @author Matteo Miloš
 *
 */
public class StudentDatabase {

	/**
	 * Hash table that represents database, key is represented with students
	 * JMBAG, and value are all of the fields that one instance of
	 * {@linkplain StudentRecord} contains.
	 */
	private SimpleHashtable<String, StudentRecord> studentTable;

	/**
	 * List of objects of type {@linkplain StudentRecord}, contains all data
	 * about students.
	 */
	// protected for purposes of testing(StudentDatabaseTest), normally should be private
	protected List<StudentRecord> studentRecords;

	/**
	 * Public constructor that gets list of objects of type <code>String</code>,
	 * one object represents one row in students database. Row contains 4
	 * fields: JMBAG, last name, first name and his final grade. Each field is
	 * separated by tabulator.
	 * 
	 * @param databaseContent
	 *            all rows of database
	 * @throws IllegalArgumentException
	 *             if database content is null
	 */
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

			StudentRecord record = new StudentRecord(lineContent[0], lineContent[1], lineContent[2],
					Integer.parseInt(lineContent[3]));

			studentTable.put(lineContent[0], record);
			studentRecords.add(record);
		}

	}

	/**
	 * Method that executes searches students by given JMBAG and then returns
	 * reference to an object of type {@linkplain StudentRecord}, representing a
	 * student with given JMBAG.
	 * 
	 * @param jmbag
	 *            JMBAG of wanted student
	 * @return object of type <code>StudentRecord</code>
	 */
	public StudentRecord forJMBAG(String jmbag) {
		return studentTable.get(jmbag);
	}

	/**
	 * Method that filters the list of all the students by given filter and then
	 * returns reference to the new list that contains only students that
	 * satisfied given filter.
	 * 
	 * @param filter
	 *            filter against whom students will be tested
	 * @return reference to the list of students
	 */
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
