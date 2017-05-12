package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Custom calculator layout that is primarily used for Calculators because of
 * its unique design.
 * 
 * @author Matteo Miloš
 *
 */
public class CalcLayout implements LayoutManager2 {

	/** Distinction between two components */
	private int distinction;

	/** Default number of rows */
	private static final int ROWS = 5;

	/** Default number of rows */
	private static final int COLUMNS = 7;

	/** Default number of components */
	private static final int MAX_COMPONENTS = 31;

	/** Map with components. */
	private Map<Component, RCPosition> constraintsMap;

	/**
	 * Creates new instance of {@link CalcLayout} with no distinction between
	 * elements
	 */
	public CalcLayout() {
		this(0);
	}

	/**
	 * Constructor that creates a new CalcLayout with preset gap between
	 * components.
	 * 
	 * @param distinction
	 *            gap between elements
	 */
	public CalcLayout(int distinction) {
		this.distinction = distinction;
		this.constraintsMap = new HashMap<>();
	}

	@Override
	public void addLayoutComponent(String name, Component comp) {
		addLayoutComponent(comp, name);
	}

	@Override
	public void removeLayoutComponent(Component comp) {
		constraintsMap.remove(comp);
	}

	/**
	 * Layout used for determining which layout size is being calculated
	 * 
	 * @author Matteo Miloš
	 *
	 */
	private enum LayoutSize {
		/** Preffered layout size */
		PREFFERED,
		/** Minimum layout size */
		MINIMUM,
		/** Maximum layout size */
		MAXIMUM;
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		return getLayoutSize(parent, LayoutSize.PREFFERED);
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return getLayoutSize(parent, LayoutSize.MINIMUM);

	}

	@Override
	public Dimension maximumLayoutSize(Container parent) {
		return getLayoutSize(parent, LayoutSize.MAXIMUM);
	}

	/**
	 * Method used for calculating size of the layout based on given argument
	 * 
	 * @param parent
	 *            parent component
	 * @param sizeType
	 *            which size is being calculated
	 * @return dimension of layout
	 */
	private Dimension getLayoutSize(Container parent, LayoutSize sizeType) {
		Dimension dimension = calcWidthAndHeight(parent, sizeType);
		Insets insets = parent.getInsets();
		int width = dimension.width;
		int height = dimension.height;
		return new Dimension(
				COLUMNS * width + (COLUMNS - 1) * distinction + insets.right + insets.left,
				ROWS * height + (ROWS - 1) * distinction + insets.top + insets.bottom
		);
	}

	/**
	 * Method used for calculation width and height of the component.
	 * 
	 * @param parent
	 *            parent component
	 * @param sizeType
	 *            type of size that is calculated
	 * @return dimension of the component
	 */
	private Dimension calcWidthAndHeight(Container parent, LayoutSize sizeType) {
		int width = 0;
		int height = 0;

		for (Entry<Component, RCPosition> entry : constraintsMap.entrySet()) {
			Dimension componentSize = null;
			Component component = entry.getKey();
			RCPosition position = entry.getValue();

			switch (sizeType) {
			case MAXIMUM:
				componentSize = component.getMaximumSize();
				break;
			case MINIMUM:
				componentSize = component.getMinimumSize();
				break;
			case PREFFERED:
				componentSize = component.getPreferredSize();
			}

			if (componentSize != null) {
				if (!position.equals(new RCPosition(1, 1))) {
					width = Math.max(width, componentSize.width);
				}

				height = Math.max(height, componentSize.height);
			}
		}
		return new Dimension(width, height);
	}

	@Override
	public void layoutContainer(Container parent) {
		Dimension dim = calcWidthAndHeight(parent, LayoutSize.PREFFERED);
		int width = dim.width;
		int height = dim.height;

		double xRatio = parent.getWidth() * 1.0 / preferredLayoutSize(parent).getWidth();
		double yRatio = parent.getHeight() * 1.0 / preferredLayoutSize(parent).getHeight();

		width *= xRatio;
		height *= yRatio;

		for (Map.Entry<Component, RCPosition> entry : constraintsMap.entrySet()) {
			Component component = entry.getKey();
			RCPosition position = entry.getValue();
			if (position.equals(new RCPosition(1, 1))) {
				component.setBounds(0, 0, 5 * width + 4 * distinction, height);
			} else {
				int row = position.getRow();
				int column = position.getColumn();
				component.setBounds(
						(column - 1) * (width + distinction),
						(height + distinction) * (row - 1),
						width,
						height
				);
			}
		}
	}

	@Override
	public void addLayoutComponent(Component comp, Object constraints) {
		RCPosition pos;
		if (constraints instanceof RCPosition) {
			pos = (RCPosition) constraints;
		} else if (constraints instanceof String) {
			String splitted[] = ((String) constraints).split(",");
			try {
				pos = new RCPosition(Integer.parseInt(splitted[0]), Integer.parseInt(splitted[1]));
			} catch (NumberFormatException exc) {
				throw new IllegalArgumentException("String is invalid");
			}
		} else {
			throw new IllegalArgumentException("Given argument is invalid");
		}
		if (this.constraintsMap.size() >= MAX_COMPONENTS) {
			throw new IllegalArgumentException("No more space in layout");
		}
		if (!checkPosition(pos)) {
			throw new IllegalArgumentException("Invalid position");
		}
		if (constraintsMap.containsKey(constraints)) {
			throw new IllegalArgumentException("That position is already occupied");
		}
		constraintsMap.put(comp, pos);
	}

	/**
	 * Method used for checking if position of element is legal.
	 * 
	 * @param pos
	 *            position of element
	 * @return true if position is legal, false otherwise
	 */
	private boolean checkPosition(RCPosition pos) {
		int row = pos.getRow();
		int col = pos.getColumn();

		if (row > ROWS || row < 1) {
			return false;
		}

		if (col > COLUMNS || col < 1) {
			return false;
		}

		if (row == 1 && col > 1 && col < 6) {
			return false;
		}

		return true;
	}

	@Override
	public float getLayoutAlignmentX(Container target) {
		return 0.5f;
	}

	@Override
	public float getLayoutAlignmentY(Container target) {
		return 0.5f;

	}

	@Override
	public void invalidateLayout(Container target) {
	}

}
