package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.ObjectStack;

public class StackDemo {

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

	private static boolean isZero(int number) {
		return number == 0;
	}

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

	private static boolean isInteger(String string) {
		try {
			Integer.parseInt(string);
			return true;
		} catch (NumberFormatException exc) {
			return false;
		}
	}

}
