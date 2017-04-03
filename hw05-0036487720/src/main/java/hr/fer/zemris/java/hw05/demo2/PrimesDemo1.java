package hr.fer.zemris.java.hw05.demo2;

/**
 * Class used as a simple demonstration program for class
 * {@linkplain PrimesCollection}. It checks if class that gives us prime numbers
 * behaves correctly in for-each loop.
 * 
 * @author Matteo Miloš
 *
 */
public class PrimesDemo1 {

	/**
	 * Method which is called at the start of this program.
	 * 
	 * @param args
	 *            command line arguments, not used in this method
	 */
	public static void main(String[] args) {
		PrimesCollection primesCollection = new PrimesCollection(10);
		for (Integer prime : primesCollection) {
			System.out.println("Got prime: " + prime);
		}

	}
}
