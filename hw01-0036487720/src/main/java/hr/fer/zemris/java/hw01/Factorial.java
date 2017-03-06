package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * Program koji računa faktorijelu cijelog broja između 1 i 20.
 * 
 * @author Matteo Miloš
 *
 */
public class Factorial {

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
			String uneseniNizZnakova = sc.next();
			try {
				int uneseniBroj = Integer.parseInt(uneseniNizZnakova);

				if (uneseniBroj < 1 || uneseniBroj > 20) {
					System.out.printf("'%d' nije broj u dozvoljenom rasponu.%n", uneseniBroj);
				} else {
					System.out.printf("%d! = %d%n", uneseniBroj, faktorijela(uneseniBroj));
				}

			} catch (NumberFormatException exc) {
				if (uneseniNizZnakova.equals("kraj")) {
					System.out.println("Doviđenja.");
					break;
				}
				System.out.printf("'%s' nije cijeli broj.%n", uneseniNizZnakova);
			}
		}
		sc.close();

	}

	/**
	 * Metoda koja računa faktorijelu cijelog broja.
	 * 
	 * @param broj
	 *            broj čiju faktorijelu želimo izračunati
	 * @return izračunata faktorijela željenog broja
	 */
	public static long faktorijela(int broj) {
		if (broj == 1) {
			return (long) broj;
		}
		return broj * faktorijela(broj - 1);
	}

}
