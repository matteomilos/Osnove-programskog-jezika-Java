package hr.fer.zemris.java.hw06.crypto;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * This program gives user the option to encrypt/decrypt given file using "AES"
 * encryption algorithm and the 128-bit encryption key or calculate and check
 * the SHA-256 file digest. It is expected that user provides encryption keys
 * and initialization vectors using the standard input. Keys and vectors are
 * expected to be hex-encoded texts.
 * 
 * @author Matteo Miloš
 */
public class Crypto {

	/**
	 * User provides arguments through command line so program can know what
	 * should it do. Possible arguments are:
	 * <ul>
	 * <li>encrypt - encrypts given document using given arguments</li>
	 * <li>decrypt - decrypts given document using given arguments</li>
	 * <li>checksha - checks if expected and actual SHA-256 file digests are
	 * equal.</li>
	 * </ul>
	 * 
	 * @param args
	 *            arguments given through command line
	 */
	public static void main(String[] args) {

		if (args.length != 2 && args.length != 3) {
			System.out.println("Invalid number of arguments, you have to specify two or three arguments.");
		}

		String command = args[0];
		switch (command) {
		case "checksha":
			compareSha(args[1]);
			break;
		case "encrypt":
			crypting(true, args[1], args[2]);
			break;
		case "decrypt":
			crypting(false, args[1], args[2]);
			break;
		default:
			System.out.println("Invalid command. Supported commands are: 'encrypt', 'decrypt' and 'checksha'");
		}

	}

	/**
	 * Method used for either encrypting or decrypting given file, based on
	 * users choice. It encrypts/decrypts given file and writes it to another
	 * file preserving the input file as it was given. Also, if crypted file is
	 * decrypted, it will be completely the same as original file that was
	 * crypted.
	 *
	 * @param encrypt
	 *            <code>true</code> if user wants encryption, <code>false</code>
	 *            is user wants decryption
	 * @param filename
	 *            path to the file to be crypted/decrypted
	 * @param cryptedFilename
	 *            path to the new file that will be created by
	 *            crypting/decrypting
	 */
	private static void crypting(boolean encrypt, String filename, String cryptedFilename) {
		Scanner sc = new Scanner(System.in);

		System.out.println("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits): ");
		System.out.print(">");
		String keyText = sc.nextLine();

		System.out.println("Please provide initialization vector as hex-encoded text (32 hex-digits): ");
		System.out.print(">");
		String ivText = sc.nextLine();
		sc.close();

		SecretKeySpec keySpec = new SecretKeySpec(Util.hextobyte(keyText), "AES");
		AlgorithmParameterSpec paramSpec = new IvParameterSpec(Util.hextobyte(ivText));
		Cipher cipher = null;

		try {
			cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);

		} catch (NoSuchAlgorithmException | NoSuchPaddingException exc) {
			System.out.println("Transformation is in invalid format or contains padding scheme that is unavailable. ");
			System.exit(1);
		} catch (InvalidKeyException | InvalidAlgorithmParameterException exc) {
			System.out.println("Given key or algorithm is invalid for this cipher.");
			System.exit(1);

		}

		try (InputStream inputStream = new BufferedInputStream(new FileInputStream(filename));
				OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(cryptedFilename))) {

			int nRead;
			byte[] buffer = new byte[4096];
			byte[] inputfile = null;

			while ((nRead = inputStream.read(buffer, 0, buffer.length)) != -1) {
				inputfile = cipher.update(buffer, 0, nRead);
				outputStream.write(inputfile);
			}

			inputfile = cipher.doFinal();
			outputStream.write(inputfile);

		} catch (FileNotFoundException exc) {
			System.out.println(
					"File does not exist, is a directory rather than a regular file, or cannot be opened for reading because of some other reason. ");
			System.exit(1);

		} catch (IOException exc) {
			System.out.println("Input stream couldn't be initialized.");
			System.exit(1);

		} catch (IllegalBlockSizeException | BadPaddingException e) {
			System.out.println(
					"Encryption algorithm is unable to process the input data provided or the decrypted data is not bounded by the appropriate padding bytes. ");
			System.exit(1);
		}

		if (encrypt) {
			System.out.printf("Encryption completed. Generated file %s based on file %s.\n", cryptedFilename, filename);
		} else {
			System.out.printf("Decryption completed. Generated file %s based on file %s.\n", cryptedFilename, filename);
		}

	}

	/**
	 * Private method that is used for calculation SHA-256 file digest of given
	 * file and then comparing it with digest that was given by user.
	 *
	 * @param filename
	 *            path to the file whose SHA-256 will be calculated
	 */
	private static void compareSha(String filename) {

		MessageDigest md = null;

		try (InputStream is = new BufferedInputStream(new FileInputStream(filename))) {

			byte[] data = new byte[4096];
			int nRead;
			md = MessageDigest.getInstance("SHA-256");

			while ((nRead = is.read(data)) != -1) {
				md.update(data, 0, nRead);
			}

		} catch (FileNotFoundException exc) {
			System.out.println(
					"File does not exist, is a directory rather than a regular file, or cannot be opened for reading because of some other reason");
			System.exit(1);

		} catch (IOException exc) {
			System.out.println("Input stream couldn't be initialized.");
			System.exit(1);

		} catch (NoSuchAlgorithmException e) {
			System.out.println("No provider supports a MessageDigestSpi implementation for the specified algorithm.");
			System.exit(1);
		}

		String sha256Digest = Util.bytetohex(md.digest());

		Scanner sc = new Scanner(System.in);
		System.out.println("Please provide expected sha-256 digest for hw06part2.pdf: ");
		System.out.print(">");
		String expectedDigest = sc.nextLine();
		sc.close();

		if (sha256Digest.equals(expectedDigest)) {
			System.out.printf("Digesting completed. Digest of %s matches expected digest.\n", filename);

		} else {
			System.out.printf("Digesting completed. Digest of %s does not match the expected digest. Digest was: %s\n",
					filename, sha256Digest);
		}

	}

}
