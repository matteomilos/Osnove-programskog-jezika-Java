package hr.fer.zemris.java.hw16.trazilica;

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

/**
 * The class that represents the vocabulary based on all documents that are
 * being searched.
 */
public class Vocabulary {

	/** The Constant UTF_8. */
	private static final String UTF_8 = "UTF-8";

	/** The Constant PATH_STOP_RIJECI. */
	private static final String PATH_STOP_RIJECI = "stoprijeci.txt";

	/** The idf vector of vocabulary. */
	private static Map<String, Double> idfVector;

	/** Set of all the words from vocabulary. */
	private static Set<String> vocabulary;

	/** The stop words. */
	private static Set<String> stopWords;

	/** All the documents. */
	private static List<Document> documents;

	/**
	 * Map whose key is word, and value tells us in how many documents has this
	 * word appeared.
	 */
	private static Map<String, Integer> containingDocuments;

	/** The Constant WORD_REGEX. */
	private static final String WORD_REGEX = "\\P{L}+";

	/**
	 * Gets the vocabulary.
	 *
	 * @return the vocabulary
	 */
	public static Set<String> getVocabulary() {
		return vocabulary;
	}

	/**
	 * Gets the documents.
	 *
	 * @return the documents
	 */
	public static List<Document> getDocuments() {
		return documents;
	}

	/**
	 * Gets the containing documents.
	 *
	 * @return the containing documents
	 */
	public static Map<String, Integer> getContainingDocuments() {
		return containingDocuments;
	}

	/**
	 * Gets the stop words.
	 *
	 * @return the stop words
	 */
	public static Set<String> getStopWords() {
		return stopWords;
	}

	/**
	 * Gets the idf vector.
	 *
	 * @return the idf vector
	 */
	public static Map<String, Double> getIdfVector() {
		return idfVector;
	}

	/**
	 * Initializes vocabulary from all the documents from given path.
	 *
	 * @param path
	 *            the path
	 */
	protected static void initializeVocabulary(Path path) {
		findStopWords();

		vocabulary = new LinkedHashSet<>();
		documents = new ArrayList<>();
		containingDocuments = new HashMap<>();
		try {
			Files.walk(path).filter(Files::isRegularFile).forEach(Vocabulary::updateVocabulary);

		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Error during the initialization of vocabulary.. Exiting");
			System.exit(0);
		}

		System.out.println("\nVeličina rječnika je " + Vocabulary.getVocabulary().size() + " riječi\n");
	}

	/**
	 * Initializes Idf and tfIdf vectors.
	 */
	protected static void initializeVectors() {
		initializeIDFVector();
		initializeTfIdfVector();
	}

	/**
	 * Initializes tfIdf vector.
	 */
	private static void initializeTfIdfVector() {
		for (Document document : Vocabulary.getDocuments()) {
			document.calculateTfIdfVector(idfVector);
		}
	}

	/**
	 * Finds all the stop words from document.
	 */
	protected static void findStopWords() {
		Path path = Paths.get(PATH_STOP_RIJECI);
		List<String> lines;

		try {
			lines = Files.readAllLines(path, Charset.forName(UTF_8));
		} catch (IOException e) {
			return;
		}

		stopWords = new HashSet<>();
		for (String word : lines) {
			word = word.replace(".", "");
			stopWords.add(word.toLowerCase().trim());
		}
	}

	/**
	 * Update vocabulary with words from given file.
	 *
	 * @param path
	 *            the path to the file
	 */
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

				// doesn't do anything if word is stopword
				if (!stopWords.contains(word) && !word.isEmpty()) {

					// vocabulary is set so it takes care about same words
					vocabulary.add(word);

					// if we haven't reached this word already in this document
					if (document.getWords().add(word)) {

						// we add it to the map that stores in how many
						// documents has the word appeared (for IDF component)
						containingDocuments.merge(word, 1, Integer::sum);
					}

					document.getTfVector().merge(word, 1, Integer::sum);
				}
			}
		}
		documents.add(document);
	}

	/**
	 * Initializes IDF vector.
	 */
	private static void initializeIDFVector() {
		idfVector = new HashMap<>();

		for (String word : Vocabulary.getVocabulary()) {
			double value = Math.log(Vocabulary.getDocuments().size() / Vocabulary.getContainingDocuments().get(word));

			idfVector.put(word, value);
		}
	}
}
