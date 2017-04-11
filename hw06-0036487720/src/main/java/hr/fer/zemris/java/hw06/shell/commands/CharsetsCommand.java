package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Map;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

public class CharsetsCommand extends AbstractCommand implements ShellCommand {

	public CharsetsCommand() {
		super("charsets", Arrays.asList("descr"));
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		Map<String, Charset> availableCharsets = Charset.availableCharsets();
		for (String key : availableCharsets.keySet()) {
			env.writeln(availableCharsets.get(key).name());
		}

		return ShellStatus.CONTINUE;

	}

}
