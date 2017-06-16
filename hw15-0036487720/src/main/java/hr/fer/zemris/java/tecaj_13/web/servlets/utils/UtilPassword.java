package hr.fer.zemris.java.tecaj_13.web.servlets.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Helper class for password generation and comparison.
 * 
 * @author Matteo Miloš
 *
 */
public class UtilPassword {

	/**
	 * Transforms a byte array to a hex string.
	 * 
	 * @param mdbytes
	 *            Byte array to transform
	 * @return Hex string representation of a byte array
	 */
	public static String byteToHex(byte[] mdbytes) {
		StringBuilder sb = new StringBuilder(mdbytes.length * 2);
		for (byte b : mdbytes) {
			sb.append(String.format("%02x", b & 0xff));
		}
		return sb.toString();
	}

	/**
	 * Compares two passwords, password entered is in plain text while expected
	 * is a hash.
	 * 
	 * @param entered
	 *            users entered password
	 * @param expected
	 *            users stored password by the nick hes trying to login
	 * @return true if passwords match, false otherwise
	 */
	public static boolean checkPassword(String entered, String expected) {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA-1");
			md.update(entered.getBytes());
			byte[] mdbytes = md.digest();
			String encodedPass = byteToHex(mdbytes);
			if (encodedPass != null) {
				if (encodedPass.equals(expected)) {
					System.out.println("Passwords match.");
					return true;
				} else {
					System.out.println("Passwords dont match");
				}
			} else {
				System.out.println("Couldn't calculate sha");
			}
		} catch (NoSuchAlgorithmException e) {
		}
		return false;
	}

	/**
	 * Generates a SHA-1 password from a provided string.
	 * 
	 * @param pass
	 *            pass to generate hash from
	 * @return generated hashed password
	 */
	public static String hashPassword(String pass) {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA-1");
			md.update(pass.getBytes());
			byte[] mdbytes = md.digest();
			String encodedPass = byteToHex(mdbytes);
			return encodedPass;
		} catch (NoSuchAlgorithmException please) {
		}
		return null;
	}

}
