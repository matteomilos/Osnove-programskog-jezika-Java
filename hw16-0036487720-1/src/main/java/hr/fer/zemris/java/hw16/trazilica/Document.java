package hr.fer.zemris.java.hw16.trazilica;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * The class that represents one document.
 * 
 * @author Matteo Miloš
 */
public class Document {

	/** The path to the document. */
	private String path;

	/** The words that are in document. */
	private Set<String> words;

	/** The tf vector of the document. */
	private Map<String, Integer> tfVector;

	/** The tf idf vector of the document. */
	private Map<String, Double> tfIdfVector;

	/**
	 * Instantiates a new document.
	 *
	 * @param path
	 *            path to the document
	 */
	public Document(String path) {
		this.path = path;
		tfVector = new HashMap<>();
		words = new HashSet<>();
	}

	/**
	 * Calculate tf idf vector for document using passed idf vector.
	 *
	 * @param idfVector
	 *            the idf vector
	 */
	public void calculateTfIdfVector(Map<String, Double> idfVector) {
		tfIdfVector = new HashMap<>();
		for (String word : words) {
			tfIdfVector.put(word, idfVector.get(word) * tfVector.get(word));
		}
	}

	/**
	 * Gets the tfIdf vector.
	 *
	 * @return the tfIdf vector
	 */
	public Map<String, Double> getTfIdfVector() {
		return tfIdfVector;
	}

	/**
	 * Gets the path.
	 *
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * Gets the words.
	 *
	 * @return the words
	 */
	public Set<String> getWords() {
		return words;
	}

	/**
	 * Gets the tf vector.
	 *
	 * @return the tf vector
	 */
	public Map<String, Integer> getTfVector() {
		return tfVector;
	}

}
