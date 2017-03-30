package hr.fer.zemris.java.hw04.db;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Class <code>StudentDB</code> represents simple program that manages database
 * with records of the type {@linkplain StudentRecord}. Data is loaded from text
 * file named "database.txt" and queries are given from standard input.
 * 
 * Some of the examples of valid queries are:
 * 
 * query jmbag = "0000000003" query jmbag = "0000000003" AND lastName LIKE "L*"
 * query lastName LIKE "B*" query jmbag="0000000003" query lastName = "Blažić"
 * query firstName>"A" and lastName LIKE "B*ć" query firstName>"A" and
 * firstName<"C" and lastName LIKE "B*ć" and jmbag>"0000000002"
 * 
 * @author Matteo Miloš
 *
 */
public class StudentDB {

	/**
	 * Method being called when program is executed.
	 * 
	 * @param args
	 *            command line arguments, not used here
	 * @throws IOException
	 *             in case of wrong file
	 */
	public static void main(String[] args) throws IOException {
		List<String> lines = Files.readAllLines(Paths.get("./prva.txt"), StandardCharsets.UTF_8);
		StudentDatabase db = new StudentDatabase(lines);

		Scanner sc = new Scanner(System.in);

		while (true) {
			System.out.println(">");
			String query = sc.nextLine();

			if (query.equals("exit")) {
				System.out.println("Goodbye!");
				sc.close();
				System.exit(0);
			}
			try {
				List<StudentRecord> list = new ArrayList<>();

				if (!query.trim().startsWith("query")) {
					throw new IllegalArgumentException("Your query has to start with the word query!");
				}
				QueryParser parser = new QueryParser(query.trim().substring(5));

				if (parser.isDirectQuery()) {
					String jmbag = parser.getQueriedJMBAG();
					StudentRecord r = db.forJMBAG(jmbag);
					list.add(r);
				} else {
					for (StudentRecord r : db.filter(new QueryFilter(parser.getQuery()))) {
						list.add(r);
					}
				}
				print(list);
			} catch (QueryParserException | IllegalArgumentException exc) {
				System.out.println(exc.toString());
			}

		}
	}

	/**
	 * Private helper static method that is used for formatting print of student
	 * records.
	 * 
	 * @param list
	 *            list of student records
	 */
	private static void print(List<StudentRecord> list) {
		if (list.size() == 0) {
			printNumberOfRecords(list);
			return;
		}
		int jmbagLen = 0;
		int firstNameLen = 0;
		int lastNameLen = 0;
		int gradeLen = 0;
		for (StudentRecord student : list) {
			jmbagLen = Math.max(jmbagLen, student.getJmbag().length());
			lastNameLen = Math.max(lastNameLen, student.getLastName().length());
			firstNameLen = Math.max(firstNameLen, student.getFirstName().length());
			gradeLen = Math.max(gradeLen, (int) (Math.log10(student.getFinalGrade()) + 1));
		}

		printFirstOrLastLine(jmbagLen, lastNameLen, firstNameLen, gradeLen);
		for (StudentRecord student : list) {
			StringBuilder forPrint = new StringBuilder();
			forPrint.append("|");
			forPrint.append(String.format(" %-" + jmbagLen + "s |", student.getJmbag()));
			forPrint.append(String.format(" %-" + lastNameLen + "s |", student.getLastName()));
			forPrint.append(String.format(" %-" + firstNameLen + "s |", student.getFirstName()));
			forPrint.append(String.format(" %-" + gradeLen + "s |", student.getFinalGrade()));
			System.out.println(forPrint);
		}
		printFirstOrLastLine(jmbagLen, lastNameLen, firstNameLen, gradeLen);
		printNumberOfRecords(list);
	}

	/**
	 * Private helper static method, used for printing number of records that
	 * are printed in table.
	 * 
	 * @param list
	 *            list of student records
	 */
	private static void printNumberOfRecords(List<StudentRecord> list) {
		System.out.println("Records selected: " + list.size());
	}

	/**
	 * Private helper static method, used for printing first and last line of
	 * the table.
	 * 
	 * @param lenghts
	 *            length of the each field
	 */
	private static void printFirstOrLastLine(int... lenghts) {
		System.out.print('+');
		for (Integer len : lenghts) {
			for (int i = 0; i < len + 2; i++) {
				System.out.print('=');
			}
			System.out.print('+');
		}
		System.out.print('\n');
	}

}
