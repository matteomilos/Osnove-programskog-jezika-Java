package hr.fer.zemris.java.hw06.crypto;

public class Util {

	private static final char[] HEX_ARRAY = "0123456789abcdef".toCharArray();

	public static byte[] hextobyte(String keytext) {
		String helper;
		byte[] b = new byte[keytext.length() / 2];
		for (int i = 0; i < keytext.length() / 2; i++) {
			helper = keytext.substring(i * 2, i * 2 + 2);
			b[i] = (byte) (Integer.parseInt(helper, 16) & 0xff);
		}
		return b;
	}

	public static String bytetohex(byte[] bytearray) {
		char[] hexChars = new char[bytearray.length * 2];
		for (int j = 0; j < bytearray.length; j++) {
			int v = bytearray[j] & 0xFF;
			hexChars[j * 2] = HEX_ARRAY[v >>> 4];
			hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
		}
		return new String(hexChars);
	}

	public static void main(String[] args) {
		byte[] bajtovi;

		bajtovi = hextobyte("01aE22");
		String neki;
		neki = bytetohex(bajtovi);

		System.out.println(neki);

		for (byte b : bajtovi) {
			System.out.println(b);
		}

	}
}
