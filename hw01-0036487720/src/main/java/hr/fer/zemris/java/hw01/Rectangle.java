package hr.fer.zemris.java.hw01;

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
		double sirina, visina;

		if (args.length > 0) {
			if (args.length != 2) {
				System.out.println("Upisali ste pogrešan broj argumenata.");
				System.exit(0);
			}

			try {
				sirina = Double.parseDouble(args[0]);
				visina = Double.parseDouble(args[1]);

				if (sirina <= 0 || visina <= 0) {
					System.out.println("Jedan ili oba argumenta nisu valjane vrijednosti.");
					System.exit(0);
				}
				System.out.printf("Pravokutnik širine %.1f i visine %.1f ima površinu %.1f te opseg %.1f", sirina,
						visina, sirina * visina, 2 * (sirina + visina));

			} catch (NumberFormatException exc) {
				System.out.println("Argumenti zadani preko naredbenog retka nisu brojevi.");
				System.exit(0);
			}

		} else {
			Scanner sc = new Scanner(System.in);
			sirina = ucitaj(sc, "širinu");
			visina = ucitaj(sc, "visinu");
			System.out.printf("Pravokutnik širine %.1f i visine %.1f ima površinu %.1f te opseg %.1f", sirina, visina,
					sirina * visina, 2 * (sirina + visina));
			sc.close();
		}
	}

	/**
	 * Pomoćna metoda koja služi za "automatizaciju" učitavanje dimenzija
	 * pravokutnika.
	 * 
	 * @param sc
	 *            primjerak razreda Scanner, koristi se za čitanje sa nekog
	 *            izvora
	 * @param dimenzija
	 *            dimenzija kvadrata koju trenutno učitavamo (visina ili širina)
	 * @return učitana (pozitivna) vrijednost dimenzije pravokutnika
	 * 
	 */
	private static double ucitaj(Scanner sc, String dimenzija) {

		while (true) {
			System.out.printf("Unesite %s > ", dimenzija);
			String uneseniNizZnakova = sc.next();
			try {
				double uneseniBroj = Double.parseDouble(uneseniNizZnakova);
				if (uneseniBroj < 0) {
					System.out.printf("Unijeli ste negativnu vrijednost.%n");
				} else if (uneseniBroj == 0) {
					System.out.printf("Unijeli ste nulu.%n");
				} else {
					return uneseniBroj;
				}
			} catch (NumberFormatException exc) {
				System.out.printf("'%s' se ne može protumačiti kao broj.%n", uneseniNizZnakova);
			}
		}
	}
}
