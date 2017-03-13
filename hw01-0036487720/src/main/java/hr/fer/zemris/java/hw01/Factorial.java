package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * Program that takes an integer value (between 1 and 20), and calculates its
 * factorial value. Program will stop running once user types "kraj".
 * 
 * @author Matteo Miloš
 *
 */
public class Factorial {

	/**
	 * Variable which represents maximal acceptable value of number whose
	 * factorial we want to calculate
	 */
	public static final int MAX = 20;
	/**
	 * Variable which represents minimal acceptable value of number whose
	 * factorial we want to calculate
	 */
	public static final int MIN = 1;

	/**
	 * Method where program starts running.
	 * 
	 * @param args
	 *            command line arguments, not used in this program
	 */
	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		while (true) {
			System.out.print("Unesite broj > ");
			if (sc.hasNextInt()) {
				int inputNumber = sc.nextInt();
				if (inputNumber < MIN || inputNumber > MAX) {
					System.out.printf("'%d' nije broj u dozvoljenom rasponu.%n", inputNumber);
				} else {
					System.out.printf("%d! = %d%n", inputNumber, factorialFunction(inputNumber));
				}
			} else {
				String inputLine = sc.next();
				if (inputLine.equals("kraj")) {
					System.out.println("Doviđenja.");
					break;
				}
				System.out.printf("'%s' nije cijeli broj.%n", inputLine);
			}
		}
		sc.close();

	}

	/**
	 * Method that calculates factorial of an integer between 1 and 20, returns
	 * -1 if argument is out of boundary.
	 * 
	 * @param number
	 *            number whose factorial we want to calculate
	 * @return calculated factorial of an integer
	 */
	public static long factorialFunction(int number) {
		if (number < MIN || number > MAX) { 
			return -1;
		}
		if (number == 1) {
			return number;
		}
		return number * factorialFunction(number - 1);
	}

}