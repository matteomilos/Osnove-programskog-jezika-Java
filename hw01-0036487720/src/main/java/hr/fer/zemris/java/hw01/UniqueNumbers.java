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

		TreeNode head = null;
		Scanner sc = new Scanner(System.in);
		head = readInput(head, sc);
		System.out.print("Ispis od najmanjeg: ");
		printAscending(head);
		System.out.printf("%nIspis od najveceg: ");
		printDescending(head);
		sc.close();

	}

	/**
	 * Metoda koja služi za učitavanje brojeva sa nekog ulaza te njihovo
	 * dodavanje u binarno stablo.
	 * 
	 * @param head
	 *            referenca na prvi čvor binarnog stabla
	 * @param sc
	 *            primjerak razreda Scanner, koristi se za čitanje sa nekog
	 *            izvora
	 * @return referenca na prvi čvor binarnog stabla
	 */
	private static TreeNode readInput(TreeNode head, Scanner sc) { 			// pomoćna metoda za učitavanje brojeva sa standardnog (ili nekog drugog) ulaza, koristi se u mainu
		while (true) {
			System.out.print("Unesite broj > ");
			if (sc.hasNextInt()) {
				head = addNode(head, sc.nextInt());
			} else {
				String inputLine = sc.next();
				if (inputLine.equals("kraj")) {
					System.out.println("Doviđenja.");
					break;
				}
				System.out.printf("'%s' nije cijeli broj.%n", inputLine);
			}
		}
		return head;
	}

	/**
	 * Metoda koju koristimo za uzlazni (od manjeg prema većem) ispis
	 * vrijednosti čvorova binarnog stabla.
	 * 
	 * @param head
	 *            referenca na prvi čvor binarnog stabla
	 */
	private static void printDescending(TreeNode head) {
		if (head == null) {
			return;
		}
		printDescending(head.right);
		System.out.print(head.value + " ");
		printDescending(head.left);
	}

	/**
	 * Metoda koju koristimo za silazni (od većeg prema manjem) ispis
	 * vrijednosti čvorova binarnog stabla.
	 * 
	 * @param head
	 *            referenca na prvi čvor binarnog stabla
	 */
	private static void printAscending(TreeNode head) {
		if (head == null) {
			return;
		}
		printAscending(head.left);
		System.out.print(head.value + " ");
		printAscending(head.right);

	}

	/**
	 * Metoda koja rekurzivno provjerava sadrži li binarno stablo neki element.
	 * 
	 * @param head
	 *            referenca na prvi čvor binarnog stabla
	 * @param possibleDuplicate
	 *            broj koji provjeravamo postoji li u binarnom stablu
	 * @return <code>true</code> ako stablo sadrži provjeravanu vrijednost,
	 *         odnosno <code>false</code> ako ne sadržava
	 * 
	 */
	public static boolean containsValue(TreeNode head, int possibleDuplicate) {
		if (head == null) {
			return false;
		}
		if (possibleDuplicate < head.value) {						//provjera je li manji ili veći kako bi se smanjila složenost funkcije (izbjegavanje obilaska cijelog stabla)
			return containsValue(head.left, possibleDuplicate);
		} else if (possibleDuplicate > head.value) {				
			return containsValue(head.right, possibleDuplicate);
		} else {													//ako nije ni manji ni veći onda je jednak, što znači da već postoji u stablu
			return true;
		}
	}

	/**
	 * Metoda koja rekurzivno računa veličinu (broj čvorova) binarnog stabla.
	 * 
	 * @param head
	 *            referenca na prvi čvor binarnog stabla
	 * @return broj elemenata binarnog stabla
	 */
	public static int treeSize(TreeNode head) {
		if (head == null) {
			return 0;
		}

		return 1 + treeSize(head.right) + treeSize(head.left);
	}

	/**
	 * Metoda koja rekurzivno dodaje nove čvorove u binarno stablo.
	 * 
	 * @param head
	 *            referenca na prvi čvor binarnog stabla
	 * @param newElement
	 *            vrijednost čvora kojeg dodajemo u binarno stablo
	 * @return referenca na prvi čvor binarnog stabla
	 */
	public static TreeNode addNode(TreeNode head, int newElement) {

		if (head == null) {
			head = new TreeNode();
			head.value = newElement;
			System.out.printf("Dodano.%n");
			return head;
		}

		if (newElement < head.value) {
			head.left = addNode(head.left, newElement);
		} else if (newElement > head.value) {
			head.right = addNode(head.right, newElement);
		} else {
			System.out.printf("Broj već postoji. Preskačem.%n");
		}

		return head;
	}

}
