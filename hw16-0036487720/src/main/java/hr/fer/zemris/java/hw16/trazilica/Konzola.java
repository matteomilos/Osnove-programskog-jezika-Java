package hr.fer.zemris.java.hw16.trazilica;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Konzola {

	public static void main(String[] args) throws IOException {
		if (args.length != 1) {
			System.out.println("Invalid number of arguments. Provide one argument, path to the folder with files.");
		}

		Path folderPath = Paths.get(args[0]);

		Vocabulary.initializeVocabulary(folderPath);

		Vocabulary.initializeVectors();
		Scanner sc = new Scanner(System.in);

		while (true) {

			System.out.print("\nEnter command > ");
			String line = sc.nextLine().trim();

			if (line.trim().equals("exit")) {
				break;
			}

			else if (line.trim().startsWith("query ")) {
				CalculationUtil.calculateQuery(line, Vocabulary.getIdfVector());
				continue;
			}

			else if (line.trim().startsWith("results")) {
				CalculationUtil.printResults();
				continue;
			}

			else if (line.trim().startsWith("type")) {
				CalculationUtil.typeDocument(line);
				continue;
			}

			else {
				System.out.println("Nepoznata naredba");
			}

		}

		sc.close();
	}

}
