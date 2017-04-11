package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.SortedMap;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

public class CharsetsCommand extends AbstractCommand implements ShellCommand {

	public CharsetsCommand() {
		super("charsets",
				Arrays.asList("Command charsets takes not arguments and",
						"lists names of supported charsets for this shell.",
						"A single charset name will be written per line."));
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		SortedMap<String, Charset> availableCharsets = Charset.availableCharsets();
		for (String key : availableCharsets.keySet()) {
			env.writeln(availableCharsets.get(key).name());
		}

		return ShellStatus.CONTINUE;

	}

}
