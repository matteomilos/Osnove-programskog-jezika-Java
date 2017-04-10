package hr.fer.zemris.java.hw05.demo4;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * This class is used as a demonstration program for manipulation of student's
 * data provided from database. In our case, database is .txt file. Data is
 * manipulated by utilizing <code>stream</code> functionalities.
 * 
 * @author Matteo Miloš
 *
 */
public class StudentDemo {

	/**
	 * Method which is called at the start of this program.
	 * 
	 * @param args
	 *            command line arguments, not used in this method
	 * @throws IOException
	 *             if database can't be loaded
	 */
	@SuppressWarnings("unused")
	public static void main(String[] args) throws IOException {
		List<String> lines = Files.readAllLines(Paths.get("./studenti.txt"), StandardCharsets.UTF_8);
		List<StudentRecord> records = convert(lines);

		long broj = vratiBodovaViseOd25(records);

		long broj5 = vratiBrojOdlikasa(records);

		List<StudentRecord> odlikasi = vratiListuOdlikasa(records);

		List<StudentRecord> odlikasiSortirano = vratiSortiranuListuOdlikasa(records);

		List<String> nepolozeniJMBAGovi = vratiPopisNepolozenih(records);

		Map<Integer, List<StudentRecord>> mapaPoOcjenama = razvrstajStudentePoOcjenama(records);

		Map<Integer, Integer> mapaPoOcjenama2 = vratiBrojStudenataPoOcjenama(records);

		Map<Boolean, List<StudentRecord>> prolazNeprolaz = razvrstajProlazPad(records);

	}

	/**
	 * Private static method that using the stream functionalities creates map
	 * whose key is either true or false. For key==true, value is list of
	 * students whose final grade was greater than 1. For key==false, value is
	 * list of students whose final grade is 1.
	 * 
	 * @param records
	 *            list of student records
	 * @return map with students grouped by their final grade (equal to 1 or
	 *         greater than 1)
	 */
	private static Map<Boolean, List<StudentRecord>> razvrstajProlazPad(List<StudentRecord> records) {

		return records.stream().collect(Collectors.partitioningBy((s1) -> s1.getFinalGrade() > 1));
	}

	/**
	 * Private static method that using the stream functionalities creates map
	 * whose key is student's final grade, and value is number of students whose
	 * final grade was equal to the key.
	 * 
	 * @param records
	 *            list of student records
	 * @return map of students final grades and number of students who had that
	 *         final grade
	 */
	private static Map<Integer, Integer> vratiBrojStudenataPoOcjenama(List<StudentRecord> records) {
		return records.stream().collect(
				Collectors.toMap(StudentRecord::getFinalGrade, s -> 1, (oldCount, newCount) -> oldCount + newCount));
	}

	/**
	 * Private static method that using the stream functionalities creates map
	 * whose key is student's final grade, and value is list of all students
	 * whose final grade was equal to the key.
	 * 
	 * @param records
	 *            list of student records
	 * @return map with students grouped based on their final grade
	 */
	private static Map<Integer, List<StudentRecord>> razvrstajStudentePoOcjenama(List<StudentRecord> records) {
		return records.stream().collect(Collectors.groupingBy(StudentRecord::getFinalGrade));
	}

	/**
	 * Private static method that using the stream functionalities creates list
	 * parameterized by {@linkplain String}. List contains jmbag's of all
	 * students whose final grade was 1. Jmbag's are sorted in ascending order
	 * 
	 * @param records
	 *            list of student records
	 * @return list of jmbag's of students whose final grade was 1
	 */
	private static List<String> vratiPopisNepolozenih(List<StudentRecord> records) {
		return records.stream().filter(s -> s.getFinalGrade() == 1).map(s -> s.getJmbag()).sorted()
				.collect(Collectors.toList());
	}

	/**
	 * Private static method that using the stream functionalities creates list
	 * parameterized by {@linkplain StudentRecord}. List contains record of all
	 * students whose final grade was 5 and is sorted in a way that student who
	 * has most points is on the first place, and student with the least points
	 * is on the last place.
	 * 
	 * @param records
	 *            list of student records
	 * @return sorted list of students whose final grade was 5
	 */
	private static List<StudentRecord> vratiSortiranuListuOdlikasa(List<StudentRecord> records) {
		return records.stream().filter(s -> s.getFinalGrade() == 5)
				.sorted((s1, s2) -> -Double.compare(s1.getSumOfAllPoints(), s2.getSumOfAllPoints()))
				.collect(Collectors.toList());
	}

	/**
	 * Private static method that using the stream functionalities creates list
	 * parameterized by {@linkplain StudentRecord}. List contains record of all
	 * students whose final grade was 5.
	 * 
	 * @param records
	 *            list of student records
	 * @return list of students whose final grade was 5
	 */
	private static List<StudentRecord> vratiListuOdlikasa(List<StudentRecord> records) {
		return records.stream().filter(s -> s.getFinalGrade() == 5).collect(Collectors.toList());
	}

	/**
	 * Private static method that using the stream functionalities determines
	 * number of students whose final grade is 5.
	 * 
	 * @param records
	 *            list of student records
	 * @return number of students whose final grade is 5
	 */
	private static long vratiBrojOdlikasa(List<StudentRecord> records) {
		return records.stream().filter(s -> s.getFinalGrade() == 5).count();
	}

	/**
	 * Private static method that using the stream functionalities determines
	 * number of students whose sum of points is greater than 25.
	 * 
	 * @param records
	 *            list of student records
	 * @return number of students whose sum of points is greater than 25
	 */
	private static long vratiBodovaViseOd25(List<StudentRecord> records) {
		return records.stream().filter(s -> s.getSumOfAllPoints() > 25).count();
	}

	/**
	 * Private static method that analyzes list of strings, with each string
	 * representing one line from student database.
	 * 
	 * @param lines
	 *            lines read from database
	 * @return list of student records
	 */
	private static List<StudentRecord> convert(List<String> lines) {
		List<StudentRecord> records = new ArrayList<>();
		for (String line : lines) {
			String[] splitted = line.split("\t");
			if (splitted.length == 7) {
				try {
					records.add(new StudentRecord(splitted));
				} catch (NumberFormatException exc) {
					System.out.println("Line in your document is invalid.");
				}
			}
		}
		return records;
	}

}
