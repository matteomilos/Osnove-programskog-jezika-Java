package hr.fer.zemris.java.gui.layouts;

/**
 * A class that represents a position constraint for {@link CalcLayout}, this
 * class can also be implemented in other layout implementations that are
 * grid-like.
 * 
 * @author Matteo Miloš
 *
 */
public class RCPosition {

	/** grid row. */
	private int row;

	/** grid column. */
	private int column;

	/**
	 * Public constructor that creates a new RCPosition constraint that takes in
	 * two arguments, row and column that must be greater or equal to 1.
	 * 
	 * @param row
	 *            row position
	 * @param column
	 *            column position
	 */
	public RCPosition(int row, int column) {
		this.row = row;
		this.column = column;
	}

	/**
	 * Returns a column of this position.
	 *
	 * @return column of this {@link RCPosition}
	 */
	public int getColumn() {
		return column;
	}

	/**
	 * Returns a row of this position.
	 *
	 * @return row of this {@link RCPosition}
	 */
	public int getRow() {
		return row;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + column;
		result = prime * result + row;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof RCPosition)) {
			return false;
		}
		RCPosition other = (RCPosition) obj;
		if (column != other.column) {
			return false;
		}
		if (row != other.row) {
			return false;
		}
		return true;
	}

}
