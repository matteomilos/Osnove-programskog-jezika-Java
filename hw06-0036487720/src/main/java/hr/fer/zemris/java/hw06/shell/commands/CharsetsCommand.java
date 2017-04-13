package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.SortedMap;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.MyShell;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Command/class <code>CharsetsCommand</code> is used in {@linkplain MyShell}
 * class for listing names of all supported charsets for running Java platform.
 * 
 * @author Matteo Miloš
 *
 */
public class CharsetsCommand extends AbstractCommand implements ShellCommand {

	/**
	 * Public constructor used for creating a new charsets command
	 */
	public CharsetsCommand() {
		super("charsets",
				Arrays.asList("Command charsets takes not arguments and",
						"lists names of supported charsets for this shell.",
						"A single charset name will be written per line."));
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

		if (arguments != null) {
			env.writeln("Command charsets doesn't have any arguments");
			return ShellStatus.CONTINUE;
		}

		SortedMap<String, Charset> availableCharsets = Charset.availableCharsets();
		for (String key : availableCharsets.keySet()) {
			env.writeln(availableCharsets.get(key).name());
		}

		return ShellStatus.CONTINUE;

	}

}
