package hr.fer.zemris.java.hw06.shell.commands;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

public class HexdumpCommand extends AbstractCommand implements ShellCommand {

	public HexdumpCommand() {
		super("hexdump", Arrays.asList("descr"));
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {

		final int BASE = 16;

		final Pattern pattern = Pattern.compile("\\s*((\"(.+)\")|(\\S+))\\s*");
		final Matcher matcher = pattern.matcher(arguments.trim());

		File file = null;
		if (matcher.find()) {
			file = (matcher.group(3) == null) ? new File(matcher.group(1)) : new File(matcher.group(3));
		} else {
			env.writeln("Specified path is not valid.");
			return ShellStatus.CONTINUE;
		}

		if (file.isDirectory()) {
			throw new RuntimeException("Given path can't be represented as file");
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
			throw new RuntimeException("File couldn't be read");
		}

		return ShellStatus.CONTINUE;
	}

	private String createText(byte[] buffer, int nRead) {
		StringBuilder buff = new StringBuilder();

		for (int i = 0, value; i < nRead; i++) {
			value = buffer[i] & 0xff;
			if (value < 32 || value > 127) {
				buff.append('.');
			} else {
				buff.append((char) value);
			}
		}

		return buff.toString();
	}

	private String bytesToHex(byte[] buffer, int nRead) {
		StringBuilder buff = new StringBuilder();
		for (int i = 0; i < buffer.length; i++) {

			if (i < nRead) {
				if ((buffer[i] & 0xff) < 0x10) {
					buff.append('0');
				}
				buff.append(Integer.toHexString(buffer[i] & 0xff).toUpperCase());
			} else {
				buff.append("  ");
			}

			if (i == buffer.length / 2 - 1) {
				buff.append("|");
			} else {
				buff.append(" ");
			}

		}
		return buff.append("| ").toString();
	}

}
