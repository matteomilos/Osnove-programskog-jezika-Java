package hr.fer.zemris.java.gui.charts;

/**
 * The class that represents coordinates inside a barchart.
 * 
 * @author Matteo Miloš
 *
 */
public class XYValue {

	/** The x coordinate. */
	private int x;

	/** The y coordinate. */
	private int y;

	/**
	 * Instantiates a new XY value.
	 *
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 */
	public XYValue(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Gets the x.
	 *
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * Gets the y.
	 *
	 * @return the y
	 */
	public int getY() {
		return y;
	}

}
