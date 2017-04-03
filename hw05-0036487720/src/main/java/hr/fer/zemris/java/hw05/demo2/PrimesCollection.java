package hr.fer.zemris.java.hw05.demo2;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class PrimesCollection implements Iterable<Integer> {

	private int numOfConsecutivePrimes;

	public PrimesCollection(int numOfConsecutivePrimes) {
		this.numOfConsecutivePrimes = numOfConsecutivePrimes;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Iterable#iterator()
	 */
	@Override
	public Iterator iterator() {
		return new IteratorImpl();
	}

	private class IteratorImpl implements Iterator<Integer> {

		private int nextPrime = 2;
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
