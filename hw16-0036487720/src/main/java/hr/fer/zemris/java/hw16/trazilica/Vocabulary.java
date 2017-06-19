package hr.fer.zemris.java.hw16.trazilica;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Vocabulary {

	private static final String UTF_8 = "UTF-8";

	private static final String PATH_STOP_RIJECI = "stoprijeci.txt";

	private static Map<String, Double> idfVector = new HashMap<>();

	private static Set<String> vocabulary = new LinkedHashSet<>();

	private static Set<String> stopWords = new HashSet<>();

	private static List<Document> documents = new ArrayList<>();

	private static Map<String, Integer> containingDocuments = new HashMap<>();

	private static final String WORD_REGEX = "\\P{L}+";

	public static Set<String> getVocabulary() {
		return vocabulary;
	}

	public static List<Document> getDocuments() {
		return documents;
	}

	public static Map<String, Integer> getContainingDocuments() {
		return containingDocuments;
	}

	public static Set<String> getStopWords() {
		return stopWords;
	}

	public static Map<String, Double> getIdfVector() {
		return idfVector;
	}

	protected static void initializeVocabulary(Path path) {
		findStopWords();

		try {
			Files.walk(path).filter(Files::isRegularFile).forEach(Vocabulary::updateVocabulary);
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Error during the initialization of vocabulary.. Exiting");
			System.exit(0);
		}

		System.out.println("\nVeličina riječnika je " + Vocabulary.getVocabulary().size() + " riječi\n");
	}

	protected static void initializeVectors() {
		initializeIDFVector();
		initializeTfIdfVector();
	}

	private static void initializeTfIdfVector() {
		for (Document document : Vocabulary.getDocuments()) {
			document.calculateTfIdfVector(idfVector);
		}
	}

	protected static void findStopWords() {
		Path path = Paths.get(PATH_STOP_RIJECI);
		List<String> lines;

		try {
			lines = Files.readAllLines(path, Charset.forName(UTF_8));
		} catch (IOException e) {
			return;
		}

		for (String word : lines) {
			stopWords.add(word.toLowerCase().trim());
		}
	}

	public static void updateVocabulary(Path path) {
		List<String> lines;

		try {
			lines = Files.readAllLines(path, Charset.forName(UTF_8));
		} catch (IOException e) {
			return;
		}

		Document document = new Document(path.toString());

		for (String line : lines) {
			String[] words = line.split(WORD_REGEX);
			for (String word : words) {
				word = word.trim().toLowerCase();

				if (!stopWords.contains(word) && !word.isEmpty()) {

					vocabulary.add(word);

					if (document.getWords().add(word)) {
						containingDocuments.merge(word, 1, Integer::sum);
					}

					document.getTfVector().merge(word, 1, Integer::sum);
				}
			}
		}
		documents.add(document);
	}

	private static void initializeIDFVector() {
		for (String word : Vocabulary.getVocabulary()) {
			double value =
					Math.log(Vocabulary.getDocuments().size() / Vocabulary.getContainingDocuments().get(word));
			idfVector.put(word, value);
		}
	}
}
