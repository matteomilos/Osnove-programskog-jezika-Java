package hr.fer.zemris.java.hw04.db;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StudentDB {

	public static void main(String[] args) throws IOException {
		List<String> lines = Files.readAllLines(Paths.get("./database.txt"), StandardCharsets.UTF_8);
		//
		StudentDatabase db = new StudentDatabase(lines);

		Scanner sc = new Scanner(System.in);

		while (true) {
			System.out.println(">");
			String query = sc.nextLine();

			if (query == "exit") {
				System.out.println("Goodbye!");
				System.exit(0);
			}
			List<StudentRecord> list = new ArrayList<>();
			QueryParser parser = new QueryParser(query);
			try {
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
			} catch (QueryParserException exc) {
				System.out.println(exc.toString());
			}

		}
	}

	private static void print(List<StudentRecord> list) {
		int jmbagLen = 0;
		int firstNameLen = 0;
		int lastNameLen = 0;
		int gradeLen = 1;
		for (StudentRecord student : list) {
			jmbagLen = Math.max(jmbagLen, student.getJmbag().length());
			lastNameLen = Math.max(lastNameLen, student.getLastName().length());
			firstNameLen = Math.max(firstNameLen, student.getFirstName().length());
		}

		printFirstOrLastLine(jmbagLen, firstNameLen, lastNameLen, gradeLen);
		for (StudentRecord student : list) {
			StringBuilder forPrint = new StringBuilder();
			forPrint.append("|");
			forPrint.append(String.format(" %-" + jmbagLen + "s |", student.getJmbag()));
			forPrint.append(String.format(" %-" + lastNameLen + "s |", student.getLastName()));
			forPrint.append(String.format(" %-" + firstNameLen + "s |", student.getFirstName()));
			forPrint.append(String.format(" %-" + gradeLen + "s |", student.getFinalGrade()));
			System.out.println(forPrint);
		}
		printFirstOrLastLine(jmbagLen, firstNameLen, lastNameLen, gradeLen);
		System.out.println("Records selected: " + list.size());
	}

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
