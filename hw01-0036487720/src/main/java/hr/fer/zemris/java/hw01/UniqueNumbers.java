package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * Program koji služi za izradu binarnog stabla i dodavanje čvorova u njega.
 * 
 * @author Matteo Miloš
 *
 */
public class UniqueNumbers {

	/**
	 * Ugniježđena statička klasa koja predstavlja jedan čvor binarnog stabla,
	 * definirana je s tri varijable, referencom na lijevi i desni čvor koji su
	 * tipa TreeNode te podatkom tipa int koji predstavlja vrijednost.
	 * 
	 * @author Matteo Miloš
	 *
	 */
	static class TreeNode {

		TreeNode left;
		TreeNode right;
		int value;
	}

	/**
	 * Metoda od koje kreće izvođenje programa.
	 * 
	 * @param args
	 *            ne koristimo argumente komandne linije
	 */
	public static void main(String[] args) {

		TreeNode glava = null;
		Scanner sc = new Scanner(System.in);
		glava = ucitajBrojeve(glava, sc);
		System.out.print("Ispis od najmanjeg: ");
		ispisSilazno(glava);
		System.out.printf("%nIspis od najveceg: ");
		ispisUzlazno(glava);
		sc.close();

	}

	/**
	 * Metoda koju koristimo za uzlazni (od manjeg prema većem) ispis
	 * vrijednosti čvorova binarnog stabla.
	 * 
	 * @param glava
	 *            referenca na prvi čvor binarnog stabla
	 */
	private static void ispisUzlazno(TreeNode glava) {
		if (glava.right != null) {
			ispisUzlazno(glava.right);
		}

		System.out.print(glava.value + " ");

		if (glava.left != null) {
			ispisUzlazno(glava.left);
		}
	}

	/**
	 * Metoda koju koristimo za silazni (od većeg prema manjem) ispis
	 * vrijednosti čvorova binarnog stabla.
	 * 
	 * @param glava
	 *            referenca na prvi čvor binarnog stabla
	 */
	private static void ispisSilazno(TreeNode glava) {
		if (glava.left != null) {
			ispisSilazno(glava.left);
		}

		System.out.print(glava.value + " ");

		if (glava.right != null) {
			ispisSilazno(glava.right);
		}

	}

	/**
	 * Metoda koja služi za učitavanje brojeva sa nekog ulaza te njihovo
	 * dodavanje u binarno stablo.
	 * 
	 * @param glava
	 *            referenca na prvi čvor binarnog stabla
	 * @param sc
	 *            primjerak razreda Scanner, koristi se za čitanje sa nekog
	 *            izvora
	 * @return referenca na prvi čvor binarnog stabla
	 */
	private static TreeNode ucitajBrojeve(TreeNode glava, Scanner sc) {

		while (true) {
			System.out.print("Unesite broj > ");
			String uneseniNizZnakova = sc.next();

			try {
				int uneseniBroj = Integer.parseInt(uneseniNizZnakova);

				if (containsValue(glava, uneseniBroj)) {
					System.out.printf("Broj već postoji. Preskačem.%n");
				} else {
					glava = addNode(glava, uneseniBroj);
					System.out.printf("Dodano.%n");
				}

			} catch (NumberFormatException exc) {
				if (uneseniNizZnakova.equals("kraj")) {
					System.out.println("Doviđenja.");
					break;
				}

				System.out.printf("'%s' nije cijeli broj.%n", uneseniNizZnakova);
			}
		}
		return glava;
	}

	/**
	 * Metoda koja rekurzivno provjerava sadrži li binarno stablo neki element.
	 * 
	 * @param glava
	 *            referenca na prvi čvor binarnog stabla
	 * @param provjeravaniBroj
	 *            broj koji provjeravamo postoji li u binarnom stablu
	 * @return <code>true</code> ako stablo sadrži provjeravanu vrijednost,
	 *         odnosno <code>false</code> ako ne sadržava
	 * 
	 */
	public static boolean containsValue(TreeNode glava, int provjeravaniBroj) {
		if (glava == null) {
			return false;
		}
		while (true) {
			if (provjeravaniBroj < glava.value) {
				return containsValue(glava.left, provjeravaniBroj);
			} else if (provjeravaniBroj > glava.value) {
				return containsValue(glava.right, provjeravaniBroj);
			} else {
				return true;
			}
		}
	}

	/**
	 * Metoda koja rekurzivno računa veličinu (broj čvorova) binarnog stabla.
	 * 
	 * @param glava
	 *            referenca na prvi čvor binarnog stabla
	 * @return broj elemenata binarnog stabla
	 */
	public static int treeSize(TreeNode glava) {
		if (glava == null) {
			return 0;
		}

		return 1 + treeSize(glava.right) + treeSize(glava.left);
	}

	/**
	 * Metoda koja rekurzivno dodaje nove čvorove u binarno stablo.
	 * 
	 * @param glava
	 *            referenca na prvi čvor binarnog stabla
	 * @param i
	 *            vrijednost čvora kojeg dodajemo u binarno stablo
	 * @return referenca na prvi čvor binarnog stabla
	 */
	public static TreeNode addNode(TreeNode glava, int i) {

		if (glava == null) {
			TreeNode novi = new TreeNode();
			novi.value = i;
			return novi;
		}

		TreeNode zapamti = glava; // pamtim referencu na prvi element stabla

		while (true) {
			if (i < glava.value) {
				glava.left = addNode(glava.left, i);
				break;
			} else if (i > glava.value) {
				glava.right = addNode(glava.right, i);
				break;
			} else {
				break;
			}
		}

		return zapamti;
	}

}
