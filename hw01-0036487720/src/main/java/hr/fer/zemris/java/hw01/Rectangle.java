package hr.fer.zemris.java.hw01;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Program that calculates area and perimeter of rectangle. It takes two
 * arguments which represent width and height, either from command line
 * arguments or from standard input if they are not given through command line.
 * If user enters value that is not positive number, program will ask for new
 * input.
 * 
 * @author Matteo Miloš
 *
 */
public class Rectangle {

	/**
	 * Method where program starts running.
	 * 
	 * @param args
	 *            command line arguments, representing width and height
	 */
	public static void main(String[] args) {
		double width = 0, height = 0;

		if (args.length > 0) {
			if (args.length != 2) {
				System.out.println("Upisali ste pogrešan broj argumenata.");
				System.exit(0);
			}
			try {
				Scanner sc = new Scanner(args[0] + " " + args[1]);
				width = sc.nextDouble();
				height = sc.nextDouble();
				if (width <= 0 || height <= 0) {
					System.out.println("Jedan ili oba argumenta nisu pozitivne vrijednosti.");
					System.exit(0);
				}
				sc.close();
			} catch (InputMismatchException exc) {
				System.out.println("Argumenti zadani preko naredbenog retka nisu brojevi.");
				System.exit(0);
			}
		} else {
			Scanner sc = new Scanner(System.in);
			width = readInput(sc, "širinu");
			height = readInput(sc, "visinu");

		}
		System.out.printf("Pravokutnik širine %.1f i visine %.1f ima površinu %.1f te opseg %.1f.", width, height,
				calculateArea(width, height), calculatePerimeter(width, height));

	}

	/**
	 * Method that calculates area of rectangle
	 * 
	 * @param width
	 *            variable representing width of an rectangle
	 * @param height
	 *            variable representing height of an rectangle
	 * @return area of rectangle
	 */
	private static double calculateArea(double width, double height) {
		return width * height;
	}

	/**
	 * Method that calculates perimeter of rectangle
	 * 
	 * @param width
	 *            variable representing width of an rectangle
	 * @param height
	 *            variable representing height of an rectangle
	 * @return perimeter of rectangle
	 */
	private static double calculatePerimeter(double width, double height) {
		return 2 * (width + height);
	}

	/**
	 * 
	 * Method that we are using to avoid duplication of same code, it takes
	 * width and height from given scanner
	 * 
	 * @param sc
	 *            instance of Scanner class, used for reading from some type of
	 *            input
	 * @param dimension
	 *            dimension of rectangle we are currently trying to load
	 *            ("širina" or "visina")
	 * @return positive value that was read from scanner
	 * 
	 */
	private static double readInput(Scanner sc, String dimension) {

		while (true) {
			System.out.printf("Unesite %s > ", dimension);
			if (sc.hasNextDouble()) {
				double inputNumber = sc.nextDouble();
				if (inputNumber < 0) {
					System.out.printf("Unijeli ste negativnu vrijednost.%n");
				} else if (inputNumber == 0) {
					System.out.printf("Unijeli ste nulu.%n");
				} else {
					return inputNumber;
				}
			} else {
				System.out.printf("'%s' se ne može protumačiti kao broj.%n", sc.next());
			}

		}
	}
}
