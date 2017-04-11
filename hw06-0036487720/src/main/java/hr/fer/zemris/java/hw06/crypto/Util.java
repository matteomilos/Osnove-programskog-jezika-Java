package hr.fer.zemris.java.hw06.crypto;

/**
 * The class <code>Util</code> is used as a container for two public methods,
 * {@linkplain Util#hextobyte(String)} and {@linkplain Util#bytetohex(byte[])}.
 */
public class Util {

	/**
	 * Constant representing all characters that hexadecimal representation of
	 * number can contain.
	 */
	private static final char[] HEX_ARRAY = "0123456789abcdef".toCharArray();

	/**
	 * Private method uses for transforming hex string to a byte array.
	 *
	 * @param hexString
	 *            hex string to be transformed into byte array
	 * @return byte array that represents given hex string
	 */
	public static byte[] hextobyte(String hexString) {
		String helper;
		byte[] b = new byte[hexString.length() / 2];
		for (int i = 0; i < hexString.length() / 2; i++) {
			helper = hexString.substring(i * 2, i * 2 + 2);
			b[i] = (byte) (Integer.parseInt(helper, 16) & 0xff);
		}
		return b;
	}

	/**
	 * Private method uses for transforming byte array to a hex string.
	 *
	 * @param bytearray
	 *            byte array to be transformed into hex string
	 * @return hex string representation of byte array
	 */
	public static String bytetohex(byte[] bytearray) {
		char[] hexChars = new char[bytearray.length * 2];
		for (int j = 0; j < bytearray.length; j++) {
			int v = bytearray[j] & 0xFF;
			hexChars[j * 2] = HEX_ARRAY[v >>> 4];
			hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
		}
		return new String(hexChars);
	}
}
