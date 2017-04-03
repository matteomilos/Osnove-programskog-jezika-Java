package hr.fer.zemris.java.hw05.demo2;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Class <code>PrimesCollection</code> represents collection of prime numbers
 * which are provided only if and when they are needed. This means that numbers
 * are not stored locally, there are calculated only at the time of the call.
 * 
 * @author Matteo Miloš
 *
 */
public class PrimesCollection implements Iterable<Integer> {

	/**
	 * Number of consecutive primes that are going to be calculated.
	 */
	private int numOfConsecutivePrimes;

	/**
	 * Public constructor that gets number of consecutive primes that will be
	 * calculated.
	 * 
	 * @param numOfConsecutivePrimes
	 *            number of primes to be calculated
	 * @throws IllegalArgumentException
	 *             if number given is less than zero
	 */
	public PrimesCollection(int numOfConsecutivePrimes) {
		if (numOfConsecutivePrimes < 0) {
			throw new IllegalArgumentException(
					"Number of consecutive primes has to be number equal or greater than 0.");
		}
		this.numOfConsecutivePrimes = numOfConsecutivePrimes;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Iterable#iterator()
	 */
	@Override
	public Iterator<Integer> iterator() {
		return new IteratorImpl();
	}

	/**
	 * Private nested class that is used for implementation of iterator over
	 * prime numbers. This iterator gives us prime numbers when we "ask" for
	 * them.
	 * 
	 * @author Matteo Miloš
	 *
	 */
	private class IteratorImpl implements Iterator<Integer> {

		/**
		 * Number that is candidate for the next prime number.
		 */
		private int nextPrime = 2;

		/**
		 * Number of prime numbers that will be produced
		 */
		private int numOfPrimesToProduce = numOfConsecutivePrimes;

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.util.Iterator#hasNext()
		 */
		@Override
		public boolean hasNext() {
			if (numOfPrimesToProduce - 1 >= 0) {
				return true;
			}

			return false;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.util.Iterator#next()
		 */
		@Override
		public Integer next() {

			if (!hasNext()) {
				throw new NoSuchElementException("No more elements");
			}

			int currentPrime = nextPrime;

			while (!isPrime(++nextPrime))
				; // samo vrtimo while petlju i povećavamo kandidat za prostog
					// sve dok broj ne bude prosti broj, kada isPrime() konačno
					// vrati true, iskačemo iz petlje

			numOfPrimesToProduce--;
			return currentPrime;
		}

		/**
		 * Private method that is used for checking if number passed as argument
		 * is prime number.
		 * 
		 * @param primeCandidate
		 *            candidate for prime number
		 * @return <code>true</code> if number is prime, <code>false</code>
		 *         otherwise
		 */
		private boolean isPrime(int primeCandidate) {

			for (int i = 2; i * i <= primeCandidate; i += 2) {

				if (primeCandidate % i == 0) {
					return false;
				}
			}

			return true;
		}
	}

}
