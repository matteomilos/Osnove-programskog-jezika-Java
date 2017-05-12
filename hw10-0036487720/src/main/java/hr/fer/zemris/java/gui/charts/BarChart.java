package hr.fer.zemris.java.gui.charts;

import java.util.List;
import java.util.stream.Collectors;

/**
 * This class is model used in {@link BarChartComponent} to represent bar charts
 * and store their data.
 * 
 * @author Matteo Miloš
 *
 */
public class BarChart {

	/** 'Bars' of this chart. */
	private List<XYValue> list;

	/** Description of values on x Axis. */
	private String xDescription;

	/** Description of values on y Axis. */
	private String yDescription;

	/** Lowest value on Y axis. */
	private int minimalY;

	/** Highest value on Y axis. */
	private int maximalY;

	/** Y axis step value. */
	private int step;

	/**
	 * Instatiates a new {@link BarChart}.
	 * 
	 * @param list
	 *            {@link #list}
	 * @param xDescription
	 *            {@link #xDescription}
	 * @param yDescription
	 *            {@link #yDescription}
	 * @param minimalY
	 *            {@link #minimalY}
	 * @param maximalY
	 *            {@link #maximalY}
	 * @param step
	 *            {@link #step}
	 */
	public BarChart(List<XYValue> list, String xDescription, String yDescription, int minimalY, int maximalY,
			int step) {
		list = listEdit(list);
		this.list = list;
		this.xDescription = xDescription;
		this.yDescription = yDescription;
		this.minimalY = minimalY;
		this.maximalY = checkDistinction(minimalY, maximalY, step);
		this.step = step;
	}

	/**
	 * Method used for sorting list sent in constructor by it's x values.
	 * 
	 * @param list
	 *            list of {@link XYValue}
	 * @return sorted list
	 * @throws IllegalArgumentException
	 *             if list contains duplicate x values.
	 */
	private List<XYValue> listEdit(List<XYValue> list) {
		list = list.stream().sorted((a, b) -> Integer.compare(a.getX(), b.getX())).collect(Collectors.toList());
		if (list.size() != list.stream().mapToInt((a) -> a.getX()).distinct().count()) {
			throw new IllegalArgumentException("Duplicate x-es");
		}
		return list;
	}

	/**
	 * Method used for finding new maximalY value.
	 * 
	 * @param minimalY
	 *            minimalY value
	 * @param maximalY
	 *            old maximalY value
	 * @param step
	 *            Y axis step value.
	 * @return new minimalY value
	 */
	private int checkDistinction(int minimalY, int maximalY, int step) {
		while ((maximalY++ - minimalY) % step != 0)
			;
		return maximalY - 1;
	}

	/**
	 * Gets the list.
	 *
	 * @return the list
	 */
	public List<XYValue> getList() {
		return list;
	}

	/**
	 * Gets the x axis description.
	 *
	 * @return the x axis description
	 */
	public String getxDescription() {
		return xDescription;
	}

	/**
	 * Gets the y description.
	 *
	 * @return the y description
	 */
	public String getyDescription() {
		return yDescription;
	}

	/**
	 * Gets the minimal Y value.
	 *
	 * @return the minimal Y
	 */
	public int getMinimalY() {
		return minimalY;
	}

	/**
	 * Gets the maximal Y value.
	 *
	 * @return the maximal Y
	 */
	public int getMaximalY() {
		return maximalY;
	}

	/**
	 * Gets the step.
	 *
	 * @return the step
	 */
	public int getStep() {
		return step;
	}

}
