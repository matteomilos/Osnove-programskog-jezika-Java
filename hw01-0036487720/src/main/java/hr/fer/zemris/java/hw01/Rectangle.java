package hr.fer.zemris.java.hw01;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Program koji računa površinu i opseg pravokutnika, sadrži dvije varijable
 * tipa double koje predstavljaju širinu i visinu pravokutnika.
 * 
 * @author Matteo Miloš
 *
 */
public class Rectangle {

	/**
	 * Metoda od koje kreće izvođenje programa. Argumenti se mogu zadati preko
	 * komandne linije, a ako nisu zadani na taj način, traži se upis korisnika
	 * preko standardnog ulaza
	 * 
	 * @param args
	 *            argumenti komandne linije, širina i visina pravokutnika
	 */
	public static void main(String[] args) {
		double width, height;

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
				System.out.printf("Pravokutnik širine %.1f i visine %.1f ima površinu %.1f te opseg %.1f.", width,
						height, width * height, 2 * (width + height));
				sc.close();
			} catch (InputMismatchException exc) {
				System.out.println("Argumenti zadani preko naredbenog retka nisu brojevi.");
				System.exit(0);
			}
		} else {
			Scanner sc = new Scanner(System.in);
			width = readInput(sc, "širinu");
			height = readInput(sc, "visinu");
			System.out.printf("Pravokutnik širine %.1f i visine %.1f ima površinu %.1f te opseg %.1f.", width, height,
					width * height, 2 * (width + height));
		}

	}

	/**
	 * Pomoćna metoda koja služi za "automatizaciju" učitavanje dimenzija
	 * pravokutnika.
	 * 
	 * @param sc
	 *            primjerak razreda Scanner, koristi se za čitanje sa nekog
	 *            izvora
	 * @param dimension
	 *            dimenzija kvadrata koju trenutno učitavamo (visina ili širina)
	 * @return učitana (pozitivna) vrijednost dimenzije pravokutnika
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
