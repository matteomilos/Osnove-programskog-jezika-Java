package hr.fer.zemris.java.hw05.demo2;

public class PrimeDemo2 {

	public static void main(String[] args) {

		PrimesCollection primesCollection = new PrimesCollection(5);

		for (Integer prime : primesCollection) {

			for (Integer prime2 : primesCollection) {
				System.out.println("Got prime pair: " + prime + ", " + prime2);
			}
		}

	}
}
