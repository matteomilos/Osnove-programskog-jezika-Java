package hr.fer.zemris.java.gui.charts;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * The class that is used to demonstrate BarChart component. Try out by using
 * "demo1.txt", "demo2.txt" and "demo3.txt" files that are in main folder of the
 * project.
 * 
 * @author Matteo Miloš
 */
public class BarChartDemo extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new bar chart window.
	 *
	 * @param lines
	 *            the lines from the loaded file with bar char description
	 * @param name
	 *            the name name of window
	 */
	public BarChartDemo(List<String> lines, String name) {
		super();
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Bar chart display");
		setLocation(getContentPane().getLocation());
		initGUI(lines, name);
		setSize(getContentPane().getPreferredSize());
	}

	/**
	 * Initializes the graphical user interface with new BarChartComponent.
	 *
	 * @param lines
	 *            the lines
	 * @param name
	 *            name of the file
	 */
	private void initGUI(List<String> lines, String name) {
		Container cp = getContentPane();

		List<XYValue> values = null;
		String xAxisDesc = null;
		String yAxisDesc = null;
		int maxY = 0;
		int minY = 0;
		int step = 0;
		try {
			xAxisDesc = lines.get(0);
			yAxisDesc = lines.get(1);
			values = prepareXYValues(lines.get(2));
			minY = Integer.parseInt(lines.get(3));
			maxY = Integer.parseInt(lines.get(4));
			step = Integer.parseInt(lines.get(5));
		} catch (Exception e) {
			System.out.println("Invalid file format");
			System.exit(1);
		}
		cp.setLayout(new BorderLayout());
		cp.add(new BarChartComponent(values, xAxisDesc, yAxisDesc, minY, maxY, step, this), BorderLayout.CENTER);
		JLabel label = new JLabel(name);
		label.setHorizontalAlignment(JLabel.CENTER);
		cp.add(label, BorderLayout.NORTH);
		cp.setBackground(Color.WHITE);
	}

	/**
	 * Prepare xy values.
	 *
	 * @param string
	 *            the string
	 * @return the list
	 */
	private List<XYValue> prepareXYValues(String string) {
		String[] pairs = string.split("\\s+");
		List<XYValue> list = new ArrayList<>();
		for (int i = 0; i < pairs.length; i++) {
			String[] pair = pairs[i].split(",");
			int x = Integer.parseInt(pair[0]);
			int y = Integer.parseInt(pair[1]);
			list.add(new XYValue(x, y));
		}
		return list;
	}

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static void main(String[] args) throws IOException {
		if (args.length != 1) {
			System.out.println("Expected a single argument: path to file with information");
			System.exit(1);
		}
		String p = args[0].trim();
		Path path = Paths.get(p);
		List<String> lines = Files.readAllLines(path);
		SwingUtilities.invokeLater(() -> new BarChartDemo(lines, path.toAbsolutePath().toString()).setVisible(true));

	}
}
