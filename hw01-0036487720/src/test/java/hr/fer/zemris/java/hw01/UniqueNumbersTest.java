package hr.fer.zemris.java.hw01;

import static hr.fer.zemris.java.hw01.UniqueNumbers.addNode;
import static hr.fer.zemris.java.hw01.UniqueNumbers.containsValue;
import static hr.fer.zemris.java.hw01.UniqueNumbers.treeSize;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import hr.fer.zemris.java.hw01.UniqueNumbers.TreeNode;;

public class UniqueNumbersTest {

	@Test
	public void brojElemenataPraznogStabla() {
		TreeNode glava = null;
		int brojElemenata = treeSize(glava);
		assertEquals(0, brojElemenata);
	}

	@Test
	public void brojElemenataStabla() {
		TreeNode glava = null;
		glava = addNode(glava, 42);
		glava = addNode(glava, 76);
		glava = addNode(glava, 21);
		glava = addNode(glava, 76);
		glava = addNode(glava, 35);
		glava = addNode(glava, 28);
		glava = addNode(glava, 39);
		glava = addNode(glava, 72);
		glava = addNode(glava, 13);
		glava = addNode(glava, 39);
		int brojElemenata = treeSize(glava);
		assertEquals(8, brojElemenata); // ocekujemo 8 jer smo dva puta pokusali
										// dodati element koji vec postoji
	}

	@Test
	public void sadrziPostojeciElement() {
		TreeNode glava = null;
		glava = addNode(glava, 42);
		glava = addNode(glava, 76);
		glava = addNode(glava, 21);
		glava = addNode(glava, 76);
		glava = addNode(glava, 35);
		glava = addNode(glava, 28);
		boolean sadrzi = containsValue(glava, 21);
		assertEquals(true, sadrzi);
	}

	@Test
	public void sadrziNepostojeciElement() {
		TreeNode glava = null;
		glava = addNode(glava, 42);
		glava = addNode(glava, 76);
		glava = addNode(glava, 21);
		glava = addNode(glava, 76);
		glava = addNode(glava, 35);
		glava = addNode(glava, 28);
		boolean sadrzi = containsValue(glava, 41);
		assertEquals(false, sadrzi);
	}

	@Test
	public void sadrziElementUPraznomStablu() {
		TreeNode glava = null;
		boolean sadrzi = containsValue(glava, 41);
		assertEquals(false, sadrzi);
	}

	@Test
	public void provjeraIspravnogDodavanjaElemenata() {
		TreeNode glava = null;
		glava = addNode(glava, 42);
		glava = addNode(glava, 76);
		glava = addNode(glava, 21);
		glava = addNode(glava, 76);
		glava = addNode(glava, 35);
		glava = addNode(glava, 28);
		glava = addNode(glava, 39);
		glava = addNode(glava, 72);
		glava = addNode(glava, 13);
		glava = addNode(glava, 39);
		int ocekivani = glava.left.right.left.value;
		assertEquals(28, ocekivani);
		int ocekivani2 = glava.right.left.value;
		assertEquals(72, ocekivani2);
		int ocekivani3 = glava.value;
		assertEquals(42, ocekivani3);

	}

}
