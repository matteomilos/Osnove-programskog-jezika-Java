package hr.fer.zemris.java.hw06.shell.commands;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.UnsupportedCharsetException;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.MyShell;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Command/class <code>CatCommand</code> is used in {@linkplain MyShell} class
 * for printing the content of some file to the standard output. Printing can be
 * executed either by using default charset or by the one that user provided (if
 * it has provided it).
 * 
 * @author Matteo Miloš
 *
 */
public class CatCommand extends AbstractCommand implements ShellCommand {

	/**
	 * Public constructor used for creating a new cat command
	 */
	public CatCommand() {
		super("cat",
				Arrays.asList("Command takes one or two arguments. ",
						"The first argument is path to some file and is mandatory. ",
						"The second argument is charset name that should be used to interpret chars from bytes. ",
						"If not provided, a defaul tplatform charset will be used. ",
						"This command opens given file and writes its content to console."));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * hr.fer.zemris.java.hw06.shell.ShellCommand#executeCommand(hr.fer.zemris.
	 * java.hw06.shell.Environment, java.lang.String)
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (arguments == null) {
			env.writeln("You must provide arguments for this command.");
			return ShellStatus.CONTINUE;
		}

		final String REGEX_FOR_READING_CHARSET_NAME = "(\\s+(\\S*))?\\s*";

		final Pattern pattern = Pattern.compile(REGEX_FOR_READING_FILEPATH + REGEX_FOR_READING_CHARSET_NAME);
		final Matcher matcher = pattern.matcher(arguments.trim());

		Path file = null;
		Charset charset = null;

		if (matcher.find()) {
			try {
				file = matcher.group(3) != null ? Paths.get(matcher.group(3)) : Paths.get(matcher.group(4));
				charset = matcher.group(6) != null ? Charset.forName(matcher.group(6)) : Charset.defaultCharset();
			} catch (IllegalStateException | IndexOutOfBoundsException | InvalidPathException exc) {
				env.writeln("You have specified invalid arguments for cat comand");
				return ShellStatus.CONTINUE;
			} catch (IllegalCharsetNameException | UnsupportedCharsetException exc) {
				env.writeln("Charset is not supported.");
				return ShellStatus.CONTINUE;
			}
		} else {
			env.writeln("You have specified invalid arguments for cat comand");
		}
		try (

				BufferedReader br = new BufferedReader(
						new InputStreamReader(new FileInputStream(file.toFile()), charset))) {
			String line = null;
			while ((line = br.readLine()) != null) {
				env.writeln(line);
			}
		} catch (IOException e) {
			env.writeln("Path specified is invalid.");
		}

		return ShellStatus.CONTINUE;
	}

}
