package hr.fer.zemris.java.hw06.crypto;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Paths;
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

public class Crypto {

	public static void main(String[] args) {

		String command = args[0];
		switch (command) {
		case "checksha":
			getSha(args[1]);
			break;
		case "encrypt":
			crypting(true, args[1], args[2]);
			break;
		case "decrypt":
			crypting(false, args[1], args[2]);
		}

	}

	private static void crypting(boolean encrypt, String filename, String cryptedFilename) {
		Scanner sc = new Scanner(System.in);

		System.out.println("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits): ");
		String keyText = sc.nextLine();

		System.out.println("Please provide initialization vector as hex-encoded text (32 hex-digits): ");
		String ivText = sc.nextLine();
		sc.close();

		SecretKeySpec keySpec = new SecretKeySpec(Util.hextobyte(keyText), "AES");
		AlgorithmParameterSpec paramSpec = new IvParameterSpec(Util.hextobyte(ivText));
		Cipher cipher = null;

		try {
			cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);

		} catch (NoSuchAlgorithmException | NoSuchPaddingException e1) {
			System.out.println("Transformation is in invalid format or contains padding scheme that is unavailable");
			System.exit(1);
		} catch (InvalidKeyException | InvalidAlgorithmParameterException e1) {
			System.out.println("Given key or algorithm is invalid for this cipher");
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
					"File does not exist, is a directory rather than a regular file, or cannot be opened for reading because of some other reason");
			System.exit(1);

		} catch (IOException exc) {
			System.out.println("Input stream couldn't be initialized.");
			System.exit(1);

		} catch (IllegalBlockSizeException | BadPaddingException e) {
			System.out.println(
					"Encryption algorithm is unable to process the input data provided or the decrypted data is not bounded by the appropriate padding bytes");
			System.exit(1);
		}

		if (encrypt) {
			System.out.println(
					"Encryption completed. Generated file" + filename + "based on file" + cryptedFilename + ".");
		} else {
			System.out.println(
					"Decryption completed. Generated file " + filename + " based on file " + cryptedFilename + ".");
		}

	}

	private static void getSha(String filename) {

		MessageDigest md = null;

		try (InputStream is = new BufferedInputStream(new FileInputStream(Paths.get(filename).toFile()))) {

			ByteArrayOutputStream buffer = new ByteArrayOutputStream();
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
		String expectedDigest = sc.nextLine();
		sc.close();

		if (sha256Digest.equals(expectedDigest)) {
			System.out.println("Digesting completed. Digest of " + filename + " matches expected digest.");
		} else {
			System.out.println("Digesting completed. Digest of " + filename
					+ " does not match the expected digest. Digest was:" + sha256Digest);
		}

	}

}
