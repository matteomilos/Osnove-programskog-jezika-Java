package hr.fer.zemris.java.hw05.demo2;

public class PrimesDemo1 {

	public static void main(String[] args) {
		PrimesCollection primesCollection = new PrimesCollection(10);
		for (Integer prime : primesCollection) {
			System.out.println("Got prime: " + prime);
		}

	}
}
