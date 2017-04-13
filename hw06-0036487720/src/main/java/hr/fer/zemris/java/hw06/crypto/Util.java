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
		int len = hexString.length();
		byte[] data = new byte[len / 2];

		for (int i = 0; i < len; i += 2) {
			data[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4)
					+ Character.digit(hexString.charAt(i + 1), 16));
		}
		return data;
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
