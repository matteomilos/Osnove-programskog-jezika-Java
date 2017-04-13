package hr.fer.zemris.java.hw06.crypto;

import static hr.fer.zemris.java.hw06.crypto.Util.bytetohex;
import static hr.fer.zemris.java.hw06.crypto.Util.hextobyte;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

@SuppressWarnings("javadoc")
public class UtilTest {

	@Test
	public void testHexToByte() {
		assertArrayEquals(new byte[] { 1, -82, 34 }, hextobyte("01aE22"));
	}

	@Test
	public void testHexToByte2() {
		assertArrayEquals(new byte[] { 0 }, hextobyte("00"));
	}

	@Test
	public void testByteToHex() {
		assertEquals("01ae22", bytetohex(new byte[] { 1, -82, 34 }));
	}

	@Test
	public void testByteToHex2() {
		assertEquals("00", bytetohex(new byte[] { 0 }));
	}

	@Test
	public void testHexToByteWithZeroLengthCharacters() {
		assertArrayEquals(new byte[] {}, hextobyte(""));
	}

	@Test
	public void testByteToHexWithZeroLengthCharacters() {
		assertEquals("", bytetohex(new byte[] {}));
	}

}
