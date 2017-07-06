package hr.fer.zemris.java.hw16.trazilica;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * This class represents simple search engine that receives user queries through
 * command line. Search engine performs search through files present in root
 * directory whose absolute path is given as one and only one command line
 * argument. Top 10 search results are displayed in order of similarity to asked
 * query, along with similarity in form of real number in range [0,1], and
 * absolute path to file. Search engine can also display files mentioned in
 * result list and repeat previously asked query. Keywords are as following:
 * 
 * <ul>
 * <li>query - searches through available files for similarity to query</li>
 * <li>type - displays file mentioned in result list, receives one argument -
 * index of mentioned result file</li>
 * <li>results - repeats previously asked query</li>
 * <li>exit - terminates current search engine session</li>
 * </ul>
 * 
 * @author Matteo Miloš
 */
public class Console {

	/** The Constant TYPE. */
	private static final String TYPE = "type ";

	/** The Constant RESULTS. */
	private static final String RESULTS = "results";

	/** The Constant QUERY. */
	private static final String QUERY = "query ";

	/**
	 * Entry point of a program.
	 *
	 * @param args
	 *            absolute path to directory which contains files that can be
	 *            searched by search engine
	 * 
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static void main(String[] args) throws IOException {
		if (args.length != 1) {
			System.out.println("Invalid number of arguments. Provide one argument, path to the folder with files.");
		}

		Path folderPath = Paths.get(args[0]);
		Scanner sc = new Scanner(System.in);

		Vocabulary.initializeVocabulary(folderPath);

		Vocabulary.initializeVectors();

		while (true) {

			System.out.print("\nEnter command > ");
			String line = sc.nextLine();

			if (line.trim().equals("exit")) {
				break;
			}

			else if (line.trim().startsWith(QUERY)) {
				String query = line.trim().substring(QUERY.length()).trim();
				CalculationUtil.calculateQuery(query, Vocabulary.getIdfVector());
				continue;
			}

			else if (line.trim().equals(RESULTS)) {
				CalculationUtil.printResults();
				continue;
			}

			else if (line.trim().startsWith(TYPE)) {

				try {
					int index = Integer.valueOf(line.split("\\s")[1]);
					CalculationUtil.typeDocument(index);
				} catch (NumberFormatException exc) {
					System.out.println("Provided index is not a number.");
				}

				continue;
			}

			else {
				System.out.println("Invalid command");
			}

		}

		sc.close();
	}

}
