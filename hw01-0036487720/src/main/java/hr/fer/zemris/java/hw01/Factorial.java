package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * Program koji računa faktorijelu cijelog broja između 1 i 20.
 * 
 * @author Matteo Miloš
 *
 */
public class Factorial {

	public static final int MAX = 20;
	public static final int MIN = 1;

	/**
	 * Metoda od koje kreće izvođenje programa.
	 * 
	 * @param args
	 *            ne koristimo argumente komandne linije
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
	 * Metoda koja računa faktorijelu cijelog broja u granicama od 1 do 20.
	 * 
	 * @param number
	 *            broj čiju faktorijelu želimo izračunati
	 * @return izračunata faktorijela željenog broja
	 */
	public static long factorialFunction(int number) {
		if(number < MIN || number > MAX){   			//izbjegavanje overflowa odnosno beskonačne petlje
			return -1;
		}
		if (number == 1) {
			return number; 
		}
		return number * factorialFunction(number - 1);
	}

}