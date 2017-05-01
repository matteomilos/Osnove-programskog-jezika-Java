package hr.fer.zemris.bf.qmc;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.StringJoiner;

import hr.fer.zemris.bf.utils.Util;

/**
 * Class <code>Mask</code> is used as specification of boolean function
 * products. It is used for storing each products basic information, list of
 * indexes it covers, it's boolean values and flag which symbolizes if product
 * is or isn't of don't care type.
 * 
 * @author Matteo Miloš
 *
 */
public class Mask {

	/** value of the each variable. */
	private byte[] values;

	/** indexes that are covered with this product. */
	private Set<Integer> indexes;

	/** dontCare flag. */
	private boolean dontCare;

	/** products hashcode. */
	private int hashCode;

	/**
	 * flag which represents if product was combined with some other product.
	 */
	private boolean combined = false;

	/**
	 * Public constructor used for creating new product from index of the
	 * minterm, number of function variables and dontcare flag.
	 *
	 * @param index
	 *            index of the minterm
	 * @param numberOfVariables
	 *            number of function variables
	 * @param dontCare
	 *            dontCare flag
	 * @throws IllegalArgumentException
	 *             if given number of variable is less than 1
	 */
	public Mask(int index, int numberOfVariables, boolean dontCare) {
		if (numberOfVariables < 1) {
			throw new IllegalArgumentException("Number of variables has to be integer greater than zero.");
		}

		this.values = Util.indexToByteArray(index, numberOfVariables);
		this.indexes = new HashSet<>();
		indexes.add(index);
		this.dontCare = dontCare;
		this.hashCode = Arrays.hashCode(values);
	}

	/**
	 * Public constructor used for creating new product from array of values of
	 * each variable, set of minterm indexes that are covered with this product
	 * and dontcare flag.
	 * 
	 * @param values
	 *            array of values of each variable
	 * @param indexes
	 *            set of minterm indexes
	 * @param dontCare
	 *            dontcare flag
	 * @throws IllegalArgumentException
	 *             if given array of values is null or list of indexes is empty
	 */
	public Mask(byte[] values, Set<Integer> indexes, boolean dontCare) {
		if (values == null || indexes == null || indexes.isEmpty()) {
			throw new IllegalArgumentException("Invalid arguments given to the constructor.");
		}

		this.values = Arrays.copyOf(values, values.length);
		this.indexes = indexes;
		this.dontCare = dontCare;
		this.hashCode = Arrays.hashCode(values);
	}

	/**
	 * Public getter method that is used for checking if product is combined.
	 *
	 * @return true, if is combined, false otherwise
	 */
	public boolean isCombined() {
		return combined;
	}

	/**
	 * Public setter method that sets {@link #combined} to the given value.
	 *
	 * @param combined
	 *            new combined value
	 */
	public void setCombined(boolean combined) {
		this.combined = combined;
	}

	/**
	 * Public getter method that is used for checking if product is of type
	 * dontcare.
	 *
	 * @return true, if is dontcare, false otherwise
	 */
	public boolean isDontCare() {
		return dontCare;
	}

	/**
	 * Public getter method used for getting all the indexes that are covered by
	 * this product.
	 *
	 * @return indexes that are covered
	 */
	public Set<Integer> getIndexes() {
		return indexes;
	}

	/**
	 * Method used for counting number of ones in {@link #values}.
	 *
	 * @return the number of ones
	 */
	public int countOfOnes() {
		int counter = 0;
		for (byte value : values) {
			counter = (value == 1) ? ++counter : counter;
		}
		return counter;
	}

	/**
	 * Method that is used for combining two product using theory of
	 * simplification in boolean algebra. Result is an instance of optional
	 * class.
	 *
	 * @param other
	 *            second product for combining
	 * @return instance of optional class, contains new Mask if combination is
	 *         possible, null otherwise
	 * @throws IllegalArgumentException
	 *             if products are of different lenghts or other product is null
	 */
	public Optional<Mask> combineWith(Mask other) {
		if (other == null || other.values.length != this.values.length) {
			throw new IllegalArgumentException("Masks are of different lengths or given mask is null");
		}

		int counter = 0;
		int simplified = 0;

		for (int i = 0; i < this.values.length && counter < 2; i++) {
			if (this.values[i] != other.values[i]) {
				simplified = i;
				counter++;
			}
		}

		if (counter == 1) {
			byte[] newValues = Arrays.copyOf(this.values, this.values.length);
			newValues[simplified] = 2;
			Set<Integer> newIndexes = new HashSet<>(this.indexes);
			newIndexes.addAll(other.indexes);
			boolean newDontCare = this.dontCare && other.dontCare;
			return Optional.of(new Mask(newValues, newIndexes, newDontCare));

		} else {
			return Optional.empty();
		}

	}

	/**
	 * Returns size of {@link #values}.
	 *
	 * @return size of values array
	 */
	public int size() {
		return this.values.length;
	}

	/**
	 * Gets the value at <code>position</code> in the {@link #values}.
	 *
	 * @param position
	 *            position of the element
	 * @return the value at given position
	 */
	public byte getValueAt(int position) {
		if (position < 0 || position > this.size() - 1) {
			throw new IllegalArgumentException("Position is out of bounds.");
		}

		return this.values[position];
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (byte value : values) {
			sb.append((value == 2) ? "-" : value);
		}

		sb.append(dontCare ? " D " : " . ").append(isCombined() ? "* " : "  ");
		StringJoiner sj = new StringJoiner(", ", "[", "]");

		for (Integer minterm : indexes) {
			sj.add(minterm.toString());
		}
		sb.append(sj.toString());

		return sb.toString();
	}

	@Override
	public int hashCode() {
		return this.hashCode;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Mask other = (Mask) obj;
		if (this.hashCode != other.hashCode) {
			return false;
		}
		if (!Arrays.equals(values, other.values)) {
			return false;
		}
		return true;
	}

}
