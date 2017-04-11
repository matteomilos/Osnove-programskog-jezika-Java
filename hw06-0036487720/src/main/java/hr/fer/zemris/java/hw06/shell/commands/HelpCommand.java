package hr.fer.zemris.java.hw06.shell.commands;

import java.util.Arrays;
import java.util.SortedMap;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

public class HelpCommand extends AbstractCommand implements ShellCommand {

	public HelpCommand() {
		super("help", Arrays.asList("descr"));
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {

		SortedMap<String, ShellCommand> commands = env.commands();

		if (arguments == null) {
			for (String commandName : commands.keySet()) {
				env.writeln(commandName);
			}
		} else {
			String command = arguments.trim();

			ShellCommand shellCommand = commands.get(command);

			if (shellCommand != null) {

				env.write(command + "\t");

				for (String string : shellCommand.getCommandDescription()) {
					env.writeln(string);
				}
			}

		}

		return ShellStatus.CONTINUE;
	}

}
