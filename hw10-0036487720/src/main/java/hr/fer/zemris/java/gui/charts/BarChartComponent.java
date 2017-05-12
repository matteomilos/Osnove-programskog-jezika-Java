package hr.fer.zemris.java.gui.charts;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.List;

import javax.swing.JComponent;

/**
 * Component used to view content stored in {@link BarChart} data model.
 * 
 * @author Matteo Miloš
 *
 */
public class BarChartComponent extends JComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** chart that this component displays. */
	private BarChart chart;

	/** Default inset between two elements */
	private int DEFAULT_ELEM_DIST;

	/** Default inset between edge and element */
	private int DEFAULT_EDGE_DIST;

	/** Size of the arrow */
	private int ARROW_SIZE;

	/**
	 * 
	 * @param list
	 *            'Bars' of this chart.
	 * @param xDescription
	 *            Description of values on x Axis.
	 * @param yDescription
	 *            Description of values on y Axis.
	 * @param minimalY
	 *            Lowest value on Y axis
	 * @param maximalY
	 *            Highest value on Y axis.
	 * @param step
	 *            Y axis step value.
	 * @param parent
	 *            parent component
	 */
	public BarChartComponent(
			List<XYValue> list, String xDescription, String yDescription, int minimalY, int maximalY, int step,
			Container parent
	) {

		this.chart = new BarChart(list, xDescription, yDescription, minimalY, maximalY, step);

		setLocation(20, 20);
		setSize(620, 620);

		ARROW_SIZE = 5;
		DEFAULT_EDGE_DIST = 50;
		DEFAULT_ELEM_DIST = 20;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;

		AffineTransform defaultAt = g2d.getTransform();
		AffineTransform at = new AffineTransform();
		Font numbers = new Font("numbers", Font.BOLD, 15);
		Font lines = new Font("lines", Font.PLAIN, 15);

		drawEverythingOnXAxis(g2d, numbers, lines);

		drawEverythingOnYAxis(g2d, numbers, lines);

		drawDescriptions(g2d, defaultAt, at);
	}

	/**
	 * Method used for drawing all components on Y axis.
	 * 
	 * @param g2d
	 *            graphics component
	 * @param numbers
	 *            font for numbers
	 * @param lines
	 *            font for lines
	 */
	private void drawEverythingOnYAxis(Graphics2D g2d, Font numbers, Font lines) {
		int startingY = this.getHeight() - 3 * DEFAULT_EDGE_DIST / 2;
		int step = (startingY - 2 * DEFAULT_EDGE_DIST)
				/ ((chart.getMaximalY() - chart.getMinimalY()) / chart.getStep());

		drawYAxis(g2d);

		boolean first = true;
		for (int i = this.chart.getMinimalY(); i <= this.chart.getMaximalY(); i += chart.getStep()) {

			drawYAxisNumber(g2d, startingY, numbers, i);
			drawLeftParallelLine(g2d, startingY, lines);

			if (first) {
				first = false;
				drawXAxis(g2d, startingY);
			} else {
				drawRightParallelLine(g2d, startingY);
			}

			startingY -= step;
		}
	}

	/**
	 * Method used for drawing all components on X axis.
	 * 
	 * @param g2d
	 *            graphics component
	 * @param numbers
	 *            font for numbers
	 * @param lines
	 *            font for lines
	 */
	private void drawEverythingOnXAxis(Graphics2D g2d, Font numbers, Font lines) {
		int xSize = chart.getList().size();
		int xStep = (this.getWidth() - 3 * DEFAULT_EDGE_DIST) / (xSize);
		int startingX = DEFAULT_EDGE_DIST + 2 * DEFAULT_ELEM_DIST + xStep / 2;

		for (int i = 0; i < xSize; i++) {

			drawXAxisNumber(g2d, numbers, startingX, i);
			drawDownVerticalLine(g2d, xStep, lines, startingX);
			drawUpVerticalLine(g2d, xStep, startingX);
			fillRectangles(g2d, xStep, startingX, i);

			startingX += xStep;
		}
	}

	/**
	 * Method used for drawing charts.
	 * 
	 * @param g2d
	 *            the graphics component
	 * @param xStep
	 *            distance on x axis
	 * @param startingX
	 *            starting x axis value
	 * @param i
	 *            which bar chart is currently being drawn
	 */
	private void fillRectangles(Graphics2D g2d, int xStep, int startingX, int i) {
		XYValue current = chart.getList().get(i);

		if (current.getY() <= chart.getMinimalY()) {
			return;
		}

		int startingY = this.getHeight() - 3 * DEFAULT_EDGE_DIST / 2;
		int step = (startingY - 2 * DEFAULT_EDGE_DIST)
				/ ((chart.getMaximalY() - chart.getMinimalY()) / chart.getStep());
		int coef = Math.min(chart.getMaximalY() - chart.getMinimalY(), current.getY() - chart.getMinimalY());

		int x = startingX - xStep / 2;
		int y = startingY - this.getFont().getSize() / 2 - step * coef / chart.getStep();
		int width = xStep;
		int height = step * coef / chart.getStep();

		/*-draw each chart*/
		g2d.setColor(new Color(244, 119, 72));
		g2d.fillRect(x, y, width, height);

		/*-draw white line between each chart*/
		g2d.setColor(Color.WHITE);
		g2d.drawLine(x + width - 1, y, x + width - 1, y + height);

		/*-draw shade between each chart*/
		g2d.setPaint(new Color(0f, 0f, 0, 0.2f));
		g2d.fillRect(x + width, y + 4, 5, height - 4);

	}

	/**
	 * Method used for drawing descriptions on the component.
	 * 
	 * @param g2d
	 *            graphics component
	 * @param defaultAt
	 *            default affine transform
	 * @param at
	 *            rotated affine transform
	 */
	private void drawDescriptions(Graphics2D g2d, AffineTransform defaultAt, AffineTransform at) {
		at.rotate(-Math.PI / 2);
		g2d.setTransform(at);

		g2d.setColor(Color.BLACK);
		g2d.setFont(new Font("description", Font.PLAIN, 15));
		String description = chart.getyDescription();

		g2d.drawString(
				description,
				-(this.getHeight() + description.length() * this.getFont().getSize() / 2) / 2,
				DEFAULT_EDGE_DIST
		);

		g2d.setTransform(defaultAt);
		description = chart.getxDescription();

		g2d.drawString(
				description,
				(this.getWidth() - description.length() * this.getFont().getSize() / 2) / 2,
				this.getHeight() - 3 * DEFAULT_EDGE_DIST / 4
		);
	}

	/**
	 * Method used for drawing vertical line above x axis
	 * 
	 * @param g2d
	 *            x graphics component
	 * @param step
	 *            step between each line
	 * @param startingX
	 *            position of first element
	 */
	private void drawUpVerticalLine(Graphics2D g2d, int step, int startingX) {
		g2d.setPaint(new Color(1f, 0.5f, 0, 0.3f)); // orange
													// transparent
		g2d.setStroke(new BasicStroke(0));

		g2d.drawLine(
				startingX + step / 2,
				this.getHeight() - 3 * DEFAULT_EDGE_DIST / 2 - this.getFont().getSize() / 2,
				startingX + step / 2,
				3 * DEFAULT_EDGE_DIST / 2
		);
	}

	/**
	 * Method used for drawing vertical line under x axis
	 * 
	 * @param g2d
	 *            x graphics component
	 * @param step
	 *            step between each line
	 * @param lines
	 *            font for printing lines
	 * @param startingX
	 *            position of first element
	 */
	private void drawDownVerticalLine(Graphics2D g2d, int step, Font lines, int startingX) {
		g2d.setFont(lines);
		g2d.setPaint(Color.GRAY);
		g2d.setStroke(new BasicStroke(2));

		g2d.drawLine(
				startingX + step / 2,
				this.getHeight() - 3 * DEFAULT_EDGE_DIST / 2 - this.getFont().getSize() / 2,
				startingX + step / 2,
				this.getHeight() - 3 * DEFAULT_EDGE_DIST / 2
		);
	}

	/**
	 * Method used for drawing numbers on the xAxis
	 * 
	 * @param g2d
	 *            the graphics components
	 * @param numbers
	 *            font for numbers
	 * @param startingX
	 *            position of first element
	 * @param i
	 *            which number is being drawn
	 */
	private void drawXAxisNumber(Graphics2D g2d, Font numbers, int startingX, int i) {
		g2d.setPaint(Color.BLACK);
		g2d.setFont(numbers);

		g2d.drawString(chart.getList().get(i).getX() + "", startingX, this.getHeight() - 3 * DEFAULT_ELEM_DIST);
	}

	/**
	 * Method used for drawing parallel line on the right side of y axis
	 * 
	 * @param g2d
	 *            the graphics component
	 * @param startingY
	 *            position of first element
	 */
	private void drawRightParallelLine(Graphics2D g2d, int startingY) {
		g2d.setPaint(new Color(1f, 0.5f, 0, 0.3f)); // orange transparent
		g2d.setStroke(new BasicStroke(0));

		g2d.drawLine(
				DEFAULT_EDGE_DIST + 2 * DEFAULT_ELEM_DIST,
				startingY - this.getFont().getSize() / 2,
				this.getWidth() - DEFAULT_EDGE_DIST,
				startingY - this.getFont().getSize() / 2
		);
	}

	/**
	 * Method used for drawing parallel line on the left side of y axis
	 * 
	 * @param g2d
	 *            the graphics component
	 * @param startingY
	 *            position of first element
	 * @param lines
	 *            font used for drawing lines
	 */
	private void drawLeftParallelLine(Graphics2D g2d, int startingY, Font lines) {
		g2d.setFont(lines);
		g2d.setPaint(Color.GRAY);
		g2d.setStroke(new BasicStroke(2));

		g2d.drawLine(
				DEFAULT_EDGE_DIST + DEFAULT_ELEM_DIST + this.getFont().getSize(),
				startingY - this.getFont().getSize() / 2,
				DEFAULT_EDGE_DIST + 2 * DEFAULT_ELEM_DIST,
				startingY - this.getFont().getSize() / 2
		);
	}

	/**
	 * Method used for drawing numbers on the xAxis
	 * 
	 * @param g2d
	 *            the graphics components
	 * @param numbers
	 *            font for numbers
	 * @param startingY
	 *            position of first element
	 * @param i
	 *            which number is being drawn
	 */
	private void drawYAxisNumber(Graphics2D g2d, int startingY, Font numbers, int i) {
		g2d.setPaint(Color.BLACK);
		g2d.setFont(numbers);

		g2d.drawString(
				i + "",
				DEFAULT_EDGE_DIST + 3 * DEFAULT_ELEM_DIST / 2 - g2d.getFontMetrics().stringWidth(i + ""),
				startingY
		);
	}

	/**
	 * Method used for drawing Y axis line.
	 * 
	 * @param g2d
	 *            the graphics component
	 */
	private void drawYAxis(Graphics2D g2d) {
		g2d.setPaint(Color.GRAY);
		g2d.setStroke(new BasicStroke(2));

		int x1 = DEFAULT_EDGE_DIST + 2 * DEFAULT_ELEM_DIST;
		int x2 = x1;
		int y1 = 3 * DEFAULT_EDGE_DIST / 2 + DEFAULT_ELEM_DIST / 2;
		int y2 = this.getHeight() - 3 * DEFAULT_EDGE_DIST / 2;

		g2d.drawLine(x1, y1, x2, y2);

		g2d.fillPolygon(
				new int[] {
						x1 - ARROW_SIZE, x1, x1 + ARROW_SIZE
				}, new int[] {
						y1, y1 - ARROW_SIZE, y1
				}, 3
		);
	}

	/**
	 * Method used for drawing X axis line.
	 * 
	 * @param g2d
	 *            the graphics component
	 * @param startingY
	 *            beginning of the line
	 */
	private void drawXAxis(Graphics2D g2d, int startingY) {
		g2d.setPaint(Color.GRAY);
		g2d.setStroke(new BasicStroke(2));
		int x1 = DEFAULT_EDGE_DIST + 2 * DEFAULT_ELEM_DIST;
		int x2 = this.getWidth() - DEFAULT_EDGE_DIST;
		int y1 = startingY - this.getFont().getSize() / 2;
		int y2 = y1;
		g2d.drawLine(x1, y1, x2, y2);
		g2d.fillPolygon(
				new int[] {
						x2, x2 + ARROW_SIZE, x2
				}, new int[] {
						y1 - ARROW_SIZE, y1, y1 + ARROW_SIZE
				}, 3
		);
	}
}
