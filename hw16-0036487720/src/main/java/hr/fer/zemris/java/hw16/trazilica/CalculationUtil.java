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
import java.util.StringJoiner;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class CalculationUtil {

	private static Map<String, Double> results = new LinkedHashMap<>();

	protected static void typeDocument(String line) {
		int index;

		try {
			index = Integer.valueOf(line.split("\\s")[1]);
		} catch (NumberFormatException exc) {
			System.out.println("Provided index is not a number.");
			return;
		}

		if (index >= results.keySet().size()) {
			System.out.println("Can't write that document, index too big!");
			return;
		}

		String docName = results.keySet().toArray()[index].toString();

		System.out.println("Dokument: " + docName);
		System.out.println("-----------------------------------------");

		List<String> lines;
		try {
			lines = Files.readAllLines(Paths.get(docName), Charset.forName("UTF-8"));
		} catch (IOException e) {
			return;
		}

		for (String docLine : lines) {
			System.out.println(docLine);
		}

		System.out.println("-----------------------------------------");
	}

	protected static void calculateQuery(String line, Map<String, Double> idfVector) {
		String[] query = line.trim().substring(5).trim().split("\\s");
		List<String> existingWords = new ArrayList<>();

		// remove words that are not in vocabulary
		for (String word : query) {
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

		for (Document document : Vocabulary.getDocuments()) {
			calculateSimilarity(tfIdfVector, document);
		}

		results =
				results.entrySet().stream().sorted(Entry.comparingByValue(Comparator.reverseOrder())).limit(10).collect(Collectors.toMap(Entry::getKey, Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));

		printResults();
	}

	protected static void printResultHeader(List<String> existingWords) {
		StringBuilder sb = new StringBuilder();
		sb.append("Query is: [");

		StringJoiner sj = new StringJoiner(", ");
		existingWords.stream().forEach((k) -> sj.add(k));
		sb.append(sj.toString());

		System.out.println(sb.append("]").toString());
		System.out.println("Najboljih 10 rezultata: ");

	}

	protected static void printResults() {
		final int[] i = {
				0
		};
		results.forEach((path, value) -> System.out.printf("[%d] (%.4f) %s \n", i[0]++, value, path));
	}

	protected static void calculateSimilarity(Map<String, Double> tfIdfVector, Document document) {
		double norm1 = 0.;
		double norm2 = 0.;
		double dotProduct = 0.;

		for (String word : Vocabulary.getVocabulary()) {

			double value1 = tfIdfVector.getOrDefault(word, 0.);
			double value2 = document.getTfIdfVector().getOrDefault(word, 0.);

			norm1 += Math.pow(value1, 2);
			norm2 += Math.pow(value2, 2);
			dotProduct += value1 * value2;
		}

		norm1 = Math.sqrt(norm1);
		norm2 = Math.sqrt(norm2);

		double similarity = 0.;

		if (norm1 != 0 && norm2 != 0) {
			similarity = dotProduct / (norm1 * norm2);
		}

		if (similarity > 0) {
			results.put(document.getPath(), Double.valueOf(String.format("%.4f", similarity)));
		}
	}
}
