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

		private int currentPrime = 2;
		private int newPrime = currentPrime;
		private int iteratorNumOfPrimes = numOfConsecutivePrimes;

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.util.Iterator#hasNext()
		 */
		@Override
		public boolean hasNext() {
			if (iteratorNumOfPrimes - 1 >= 0) {
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
			currentPrime = newPrime;
			for (int i = currentPrime + 1; i < 2 * currentPrime; i++) {
				if (isPrime(i)) {
					newPrime = i;
					break;
				}
			}
			iteratorNumOfPrimes--;

			return currentPrime;
		}

		private boolean isPrime(int primeCandidate) {
			if (primeCandidate % 2 == 0)
				return false;

			for (int i = 3; i * i <= primeCandidate; i += 2) {
				if (primeCandidate % i == 0) {
					return false;
				}
			}
			return true;
		}
	}

}
