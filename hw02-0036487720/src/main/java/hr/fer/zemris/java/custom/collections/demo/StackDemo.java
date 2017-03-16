package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.ObjectStack;

/**
 * Demonstration program used for checking functionalities of class
 * <code>ObjectStack</code>. Class represents simple console program that gets
 * arguments from command line and based on the given operators and numbers,
 * determines final solution of mathematical problem. Argument given from the
 * command line has to be single <code>String</code> put in quotation marks.
 * 
 * @author Matteo Miloš
 *
 */
public class StackDemo {

	/**
	 * Method that starts running when program is executed.
	 * 
	 * @param args
	 *            command line argument, combination of numbers and simple
	 *            mathematical operators
	 */
	public static void main(String[] args) {

		if (args.length != 1) {
			System.out.printf("Invalid number of arguments provided. There should be one argument.\n");
			System.exit(0);
		}

		String[] array = args[0].split(" +");
		ObjectStack stack = new ObjectStack();

		try {

			performCalculations(array, stack);

		} catch (UnsupportedOperationException exc) {
			System.err.println("You provided an unsupported operator.");
			System.exit(0);
			;
		} catch (IllegalArgumentException exc) {
			System.err.println("Division by zero is not allowed");
			System.exit(0);
			;
		}

		if (stack.size() != 1) {
			System.out.println("Stack size is different from 1. Evaluation terminated.");
		} else {
			System.out.printf("Expression evaluates to %d.", stack.peek());
		}

	}

	/**
	 * Private method that checks if given number is equal to zero
	 * 
	 * @param number
	 *            number being checked
	 * @return <code>true</code> if number given is equal to zero,
	 *         <code>false</code> otherwise
	 */
	private static boolean isZero(int number) {
		return number == 0;
	}

	/**
	 * Method that performs simple mathematical operations over the stack
	 * elements. It supports simple mathematical operators which in this case
	 * are +, -, /, * and %. Operands have to be integers. If operators or
	 * operands are incorrect, proper exception will be thrown.
	 * 
	 * @param array
	 *            array of type String, with each element of array representing
	 *            one operand or operator.
	 * @param stack
	 *            stack where current and final results are stored
	 * @throws IllegalArgumentException
	 *             if user tries to perform division by zero
	 * @throws UnsupportedOperationException
	 *             if user tries to perform unsupported operation
	 */
	private static void performCalculations(String[] array, ObjectStack stack) {

		for (String argument : array) {
			if (isInteger(argument)) {

				stack.push(Integer.parseInt(argument));

			} else {

				if (stack.size() < 2) {
					System.out.println("Invalid expression, less than 2 numbers on the stack. Evaluation terminated.");
					System.exit(0);
				}

				int secondNumber = (int) stack.pop();
				int firstNumber = (int) stack.pop();

				switch (argument) {
				case "+":
					stack.push(firstNumber + secondNumber);
					break;
				case "/":
					if (isZero(secondNumber)) {
						throw new IllegalArgumentException();
					}
					stack.push(firstNumber / secondNumber);
					break;
				case "*":
					stack.push(firstNumber * secondNumber);
					break;
				case "-":
					stack.push(firstNumber - secondNumber);
					break;
				case "%":
					if (isZero(secondNumber)) {
						throw new IllegalArgumentException();
					}
					stack.push(firstNumber % secondNumber);
					break;
				default:
					throw new UnsupportedOperationException();
				}
			}
		}
	}

	/**
	 * Method that performs check if given string can be parsed as integer.
	 * 
	 * @param string
	 *            String that is checked if is parseable
	 * @return <code>true</code> if given argument is parseable as an integer,
	 *         <code>false</code> otherwise.
	 */
	private static boolean isInteger(String string) {
		try {
			Integer.parseInt(string);
			return true;
		} catch (NumberFormatException exc) {
			return false;
		}
	}

}
