package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;
import java.util.function.BinaryOperator;
import java.util.function.Function;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import hr.fer.zemris.java.gui.layouts.CalcLayout;

/**
 * An electronic calculator is a small, portable electronic device used to
 * perform calculations, ranging from basic arithmetic to complex mathematics.
 * This is implementation of the simple calculator.
 * 
 * @author Matteo Miloš
 *
 */
public class Calculator extends JFrame {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/** Label where current result is stored */
	private JLabel result;

	/** Flag which signals that last pressed button was '=' */
	private boolean calculated = false;

	/** Variable used for storing cached result */
	private double oldResult;

	/** Last binary operator that was pressed */
	private BinaryOperator<Double> binaryOperator;

	/** Panel used for storing components */
	private JPanel p;

	/** Flag which signals if invert is selected */
	private boolean isSelected = false;

	/** Flag which signals if binary operator was clicked */
	private boolean operatorClicked = false;

	/** Stack used for storing numbers */
	private Stack<String> stack = new Stack<>();

	/** Flag which signals button '.' was clicked last */
	public boolean dotClicked = false;

	/** Flag which signals if number was typed after operator */
	private boolean newAdded = false;

	/**
	 * Constructor used for initialization of calculator.
	 */
	public Calculator() {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocation(20, 20);
		setTitle("Calculator v1.0");
		initGUI();
		setMinimumSize(getContentPane().getMinimumSize());
		setMaximumSize(getContentPane().getMaximumSize());
		pack();

	}

	/**
	 * Method used for initialization of graphical user interface.
	 */
	private void initGUI() {
		Container cp = getContentPane();
		p = new JPanel();
		p.setLayout(new CalcLayout(10));

		result = new JLabel("0", SwingConstants.RIGHT);
		result.setBackground(new Color(255, 211, 32));
		result.setOpaque(true);
		result.setFont(new Font("Result", Font.BOLD, 20));
		p.add(result, "1,1");

		addButtons();

		cp.add(p);
	}

	/**
	 * Method used for adding buttons to the calculator panel.
	 */
	private void addButtons() {
		addInvertible();
		addNumbers();
		addBinaryOperators();
		addOtherButtons();

		JCheckBox invertBox = new JCheckBox("Inv");
		invertBox.setHorizontalAlignment(SwingConstants.CENTER);
		invertBox.addActionListener((e) -> isSelected = !isSelected);
		invertBox.setBackground(new Color(114, 159, 207));
		invertBox.setFont(new Font("Button", Font.PLAIN, 15));

		p.add(invertBox, "5,7");
	}

	/**
	 * Method used for adding buttons other than numbers, invertible buttons and
	 * binary operations.
	 */
	private void addOtherButtons() {
		addButton(
				"=",
				"1,6",
				(e) -> {
					if (!calculated) {
						calculate();
						operatorClicked = false;
						calculated = true;
					}
				}
		);

		addButton("clr", "1,7", (e) -> result.setText("0"));

		addButton(
				"1/x",
				"2,1",
				(e) -> {
					if (result.getText() != "0") {
						result.setText(Double.toString(1. / Double.parseDouble(result.getText())));
					}
				}
		);

		addButton(
				"+/-",
				"5,4",
				(e) -> {
					if (Double.parseDouble(result.getText()) != 0.0) {
						if (result.getText().startsWith("-")) {
							result.setText(result.getText().substring(1));
						} else {
							result.setText("-" + result.getText());
						}
					}
				}
		);

		addButton(
				"res",
				"2,7",
				(e) -> {
					resetting();
				}
		);

		addButton("push", "3,7", (e) -> stack.push(result.getText()));

		addButton(
				"pop",
				"4,7",
				(e) -> {
					if (stack.size() < 1) {
						JOptionPane.showMessageDialog(p, "Stack is empty!");
					} else {
						oldResult = Double.parseDouble(result.getText());
						result.setText(stack.pop());
						calculated = false;
					}
				}
		);
	}

	/**
	 * Method used for resetting calculator.
	 */
	private void resetting() {
		result.setText("0");
		oldResult = 0;
		binaryOperator = null;
		isSelected = false;
		stack.clear();
		calculated = false;
		dotClicked = false;
		operatorClicked = false;
	}

	/**
	 * Method used for adding buttons representing binary operators.
	 */
	private void addBinaryOperators() {
		addButton(
				"/",
				"2,6",
				(e) -> {
					binaryOperation();
					binaryOperator = (a, b) -> a / b;
				}
		);

		addButton(
				"*",
				"3,6",
				(e) -> {
					binaryOperation();
					binaryOperator = (a, b) -> a * b;
				}
		);

		addButton(
				"-",
				"4,6",
				(e) -> {
					binaryOperation();
					binaryOperator = (a, b) -> a - b;
				}
		);

		addButton(
				"+",
				"5,6",
				(e) -> {
					binaryOperation();
					binaryOperator = (a, b) -> a + b;
				}
		);
	}

	/**
	 * Method used for calculating binary operation.
	 */
	private void binaryOperation() {
		if (operatorClicked && newAdded) {
			calculate();
		}

		operatorClicked = true;
		oldResult = Double.parseDouble(result.getText());
		newAdded = false;
		dotClicked = false;
	}

	/**
	 * Method used for calculation of some expression.
	 */
	private void calculate() {
		if (!operatorClicked) {
			return;
		}
		result.setText(binaryOperator.apply(oldResult, Double.parseDouble(result.getText())).toString());
		operatorClicked = false;
	}

	/**
	 * Method used for adding buttons that represent numbers.
	 */
	private void addNumbers() {
		addButton("0", "5,3", new NumberListener("0"));
		addButton("1", "4,3", new NumberListener("1"));
		addButton("2", "4,4", new NumberListener("2"));
		addButton("3", "4,5", new NumberListener("3"));
		addButton("4", "3,3", new NumberListener("4"));
		addButton("5", "3,4", new NumberListener("5"));
		addButton("6", "3,5", new NumberListener("6"));
		addButton("7", "2,3", new NumberListener("7"));
		addButton("8", "2,4", new NumberListener("8"));
		addButton("9", "2,5", new NumberListener("9"));
		addButton(".", "5,5", new NumberListener("."));
	}

	/**
	 * Method used for adding buttons that have invertible meaning.F
	 */
	private void addInvertible() {
		addButton("sin", "2,2", new InvertListener(Math::sin, Math::asin));
		addButton("log", "3,1", new InvertListener(Math::log10, (a) -> Math.pow(10, a)));
		addButton("cos", "3,2", new InvertListener(Math::cos, Math::acos));
		addButton("ln", "4,1", new InvertListener(Math::log, Math::exp));
		addButton("tan", "4,2", new InvertListener(Math::tan, Math::atan));
		addButton("x^n", "5,1", new InvertListener((a) -> Math.pow(oldResult, a), (a) -> Math.pow(oldResult, 1 / a)));
		addButton("ctg", "5,2", new InvertListener((a) -> 1 / Math.tan(a), (a) -> Math.PI / 2 - Math.atan(a)));
	}

	/**
	 * Method used for adding one button to the panel.
	 * 
	 * @param name
	 *            name of the button
	 * @param position
	 *            position of the button on the panel
	 * @param l
	 *            button listener
	 */
	private void addButton(String name, String position, ActionListener l) {
		JButton button = new JButton(name);
		button.addActionListener(l);
		button.setBackground(new Color(114, 159, 207));
		button.setFont(new Font("Button", Font.BOLD, 15));
		p.add(button, position);
	}

	/**
	 * Entry point to the program, summons the window.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new Calculator().setVisible(true));
	}

	/**
	 * Implementation of listener interface, used for buttons that can have two
	 * meanings, normal and inverted. If button "Inv" is not selected, normal
	 * operation will be executed, otherwise, inverted operation will be
	 * executed.
	 * 
	 * @author Matteo Miloš
	 *
	 */
	private class InvertListener implements ActionListener {

		/** Normal operation */
		private Function<Double, Double> normal;

		/** Inverted operation */
		private Function<Double, Double> inverted;

		/**
		 * Instantiates a new invert listener.
		 *
		 * @param normal
		 *            normal action
		 * @param inverted
		 *            inverted action
		 */
		public InvertListener(Function<Double, Double> normal, Function<Double, Double> inverted) {
			this.normal = normal;
			this.inverted = inverted;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (!isSelected) {
				result.setText(normal.apply(Double.parseDouble(result.getText())).toString());
			} else {
				result.setText(inverted.apply(Double.parseDouble(result.getText())).toString());
			}
			dotClicked = false;
		}

	}

	/**
	 * Implementation of listener interface, used for buttons that represent
	 * numbers. When some button is pressed, appropriate method is invoked.
	 * 
	 * @author Matteo Miloš
	 *
	 */
	private class NumberListener implements ActionListener {

		/** The buttons number */
		private String number;

		/**
		 * Instantiates a new number listener.
		 *
		 * @param number
		 *            the number
		 */
		public NumberListener(String number) {
			this.number = number;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (dotClicked && this.number.equals(".")) {
				return;
			}

			if ((result.getText().equals("0") || calculated || !newAdded || result.getText().matches("[a-zA-Z]+"))) {
				result.setText("");
				if (this.number.equals(".")) {
					result.setText("0");
				}
				calculated = false;
			}

			result.setText(result.getText() + number);
			if (this.number.equals(".")) {
				dotClicked = true;
			}

			newAdded = true;
		}
	}

}
