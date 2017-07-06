package hr.fer.zemris.java.hw16.trazilica;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/**
 * Class where are situated all the methods for possible user inputs in the
 * search console.
 * 
 * @author Matteo Miloš
 *
 */
public class CalculationUtil {

	/** The Constant UTF_8. */
	private static final String UTF_8 = "UTF-8";

	/** The results of the search. */
	private static Map<String, Double> results;

	/**
	 * Writes document content to the standard output.
	 *
	 * @param index
	 *            index of document in results
	 */
	protected static void typeDocument(int index) {
		
		if (results == null) {
			System.out.println("No results calculated. Type your query");
			return;
		}
		
		if (index >= results.keySet().size() || index < 0) {
			System.out.println("Can't write that document, index too big!");
			return;
		}

		String docName = results.keySet().toArray()[index].toString();

		System.out.println("-----------------------------------------");
		System.out.println("Document: " + docName);
		System.out.println("-----------------------------------------");

		List<String> lines;
		try {
			lines = Files.readAllLines(Paths.get(docName), Charset.forName(UTF_8));
		} catch (IOException e) {
			return;
		}

		for (String docLine : lines) {
			System.out.println(docLine);
		}

		System.out.println("-----------------------------------------");
	}

	/**
	 * Calculates which documents mostly satisfy user query.
	 *
	 * @param query
	 *            the query of the user
	 * @param idfVector
	 *            the idf vector
	 */
	protected static void calculateQuery(String query, Map<String, Double> idfVector) {
		String[] queryParts = query.split("\\s");
		List<String> existingWords = new ArrayList<>();

		// remove words that are not in vocabulary
		for (String word : queryParts) {
			word = word.toLowerCase();
			if (Vocabulary.getVocabulary().contains(word)) {
				existingWords.add(word);
			}
		}
		if (existingWords.size() == 0) {
			System.out.println("Given words are not in vocabulary, therefore no files can be found");
			return;
		}

		printResultHeader(existingWords);

		// tfVector for query
		Map<String, Integer> tfVector = new HashMap<>();
		for (String word : existingWords) {
			tfVector.merge(word, 1, Integer::sum);
		}

		// tfIdfVector for query
		Map<String, Double> tfIdfVector = new HashMap<>();
		for (String word : existingWords) {
			tfIdfVector.put(word, idfVector.get(word) * tfVector.get(word));
		}

		results = new LinkedHashMap<>();
		for (Document document : Vocabulary.getDocuments()) {
			calculateSimilarity(tfIdfVector, document);
		}

		results = results.entrySet()
				.stream()
				.sorted(Entry.comparingByValue(Comparator.reverseOrder()))
				.limit(10)
				.collect(Collectors.toMap(Entry::getKey, Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));

		printResults();
	}

	/**
	 * Prints the header before listing the results.
	 *
	 * @param searchedWords
	 *            words that are searched
	 */
	protected static void printResultHeader(List<String> searchedWords) {
		StringBuilder sb = new StringBuilder();
		sb.append("Query is: [");

		StringJoiner sj = new StringJoiner(", ");
		searchedWords.stream().forEach(k -> sj.add(k));
		sb.append(sj.toString());

		System.out.println(sb.append("]").toString());
		System.out.println("Best 10 results: ");

	}

	/**
	 * Prints the results.
	 */
	protected static void printResults() {
		final int[] i = {
				0
		};
		if (results == null) {
			System.out.println("No results calculated. Type your query");
			return;
		}
		results.forEach((path, value) -> System.out.printf("[%d] (%.4f) %s \n", i[0]++, value, path));
	}

	/**
	 * Calculate similarity between two documents.
	 *
	 * @param tfIdfVector
	 *            the tfIdf vector of first document
	 * @param document
	 *            the second document
	 */
	protected static void calculateSimilarity(Map<String, Double> tfIdfVector, Document document) {
		double norm1 = 0.;
		double norm2 = 0.;
		double dotProduct = 0.;

		for (String word : Vocabulary.getVocabulary()) {

			double value1 = tfIdfVector.getOrDefault(word, 0.);
			double value2 = document.getTfIdfVector().getOrDefault(word, 0.);

			norm1 += value1 * value1;
			norm2 += value2 * value2;
			dotProduct += value1 * value2;
		}

		double similarity = 0.;

		if (norm1 != 0 && norm2 != 0) {
			similarity = dotProduct / Math.sqrt(norm1 * norm2);
		}

		if (similarity > 0) {
			results.put(document.getPath(), Double.valueOf(String.format("%.4f", similarity)));
		}
	}
}
