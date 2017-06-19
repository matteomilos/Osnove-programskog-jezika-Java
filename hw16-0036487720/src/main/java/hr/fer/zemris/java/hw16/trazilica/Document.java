package hr.fer.zemris.java.hw16.trazilica;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;

public class Document {

	private String path;

	private Set<String> words = new HashSet<>();

	private Map<String, Integer> tfVector = new HashMap<>();

	private Map<String, Double> tfIdfVector = new HashMap<>();

	public Document(String path) {
		this.path = path;
	}

	public void calculateTfIdfVector(Map<String, Double> idfVector) {
		for (String word : words) {
			tfIdfVector.put(word, idfVector.get(word) * tfVector.get(word));
		}
	}

	public Map<String, Double> getTfIdfVector() {
		return tfIdfVector;
	}

	public String getPath() {
		return path;
	}

	public Set<String> getWords() {
		return words;
	}

	public Map<String, Integer> getTfVector() {
		return tfVector;
	}

}
