package hr.fer.zemris.java.hw06.shell.commands;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.MyShell;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Command/class <code>HexdumpCommand</code> is used in {@linkplain MyShell}
 * class for producing hex-output of given file.
 * 
 * @author Matteo Miloš
 *
 */
public class HexdumpCommand extends AbstractCommand implements ShellCommand {

	/**
	 * Public constructor used for creating a new help command
	 */
	public HexdumpCommand() {
		super("hexdump",
				Arrays.asList("The hexdump command expects a single argument:", "file name,",
						"It produces hex-output of this file in this way:", "In the first column, there will be",
						"hexadecimal line values.", "In the second and third column, there will be",
						"hexadecimal representation of each charahter.", "In the fourth, last column, each charahter",
						"will be shown in its normal representation. Characters",
						"whose value is either less than 32 or greater than 127", "will be replaced with dots."));
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {

		if (arguments == null) {
			env.writeln("You must provide arguments for this command.");
			return ShellStatus.CONTINUE;
		}

		final int BASE = 16;

		final Pattern pattern = Pattern.compile(REGEX_FOR_READING_FILEPATH);
		final Matcher matcher = pattern.matcher(arguments.trim());

		File file = null;
		if (matcher.find()) {
			file = (matcher.group(3) == null) ? new File(matcher.group(1)) : new File(matcher.group(3));
		} else {
			env.writeln("Specified path is not valid.");
			return ShellStatus.CONTINUE;
		}

		if (file.isDirectory()) {
			env.writeln("Given path can't be represented as file.");
			return ShellStatus.CONTINUE;
		}

		try (InputStream is = new BufferedInputStream(new FileInputStream(file))) {
			byte[] buffer = new byte[BASE];
			StringBuilder sb = new StringBuilder();

			int nRead;
			int printNum = 0;
			while ((nRead = is.read(buffer)) > 0) {

				sb.append(String.format("%08x: ", printNum));

				sb.append(bytesToHex(buffer, nRead));

				sb.append(createText(buffer, nRead));

				printNum += BASE;

				sb.append("\n");

			}

			env.writeln(sb.toString());

		} catch (IOException e) {
			env.writeln("File does not exist.");
		}

		return ShellStatus.CONTINUE;
	}

	/**
	 * Private method used to create text representation of the given byte
	 * array. All the bytes whose value is either less than 32 or greater than
	 * 127 will be represented as '.'.
	 * 
	 * @param buffer
	 *            byte array
	 * @param nRead
	 *            number of characters to be printed.
	 * @return text
	 */
	private String createText(byte[] buffer, int nRead) {
		StringBuilder sb = new StringBuilder();

		for (int i = 0, value; i < nRead; i++) {
			value = buffer[i] & 0xff;
			if (value < 32 || value > 127) {
				sb.append('.');
			} else {
				sb.append((char) value);
			}
		}

		return sb.toString();
	}

	/**
	 * Private method used for getting hexadecimal representation of given
	 * array.
	 * 
	 * @param buffer
	 *            byte array
	 * @param nRead
	 *            number of characters to be printed.
	 * @return text
	 */
	private String bytesToHex(byte[] buffer, int nRead) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < buffer.length; i++) {

			if (i < nRead) {
				if ((buffer[i] & 0xff) < 0x10) {
					sb.append('0');
				}
				sb.append(Integer.toHexString(buffer[i] & 0xff).toUpperCase());
			} else {
				sb.append("  ");
			}

			if (i == buffer.length / 2 - 1) {
				sb.append("|");
			} else {
				sb.append(" ");
			}

		}
		return sb.append("| ").toString();
	}

}
