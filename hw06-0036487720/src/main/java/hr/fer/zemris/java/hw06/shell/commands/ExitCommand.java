package hr.fer.zemris.java.hw06.shell.commands;

import java.util.Arrays;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.MyShell;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Command/class <code>ExitCommand</code> is used in {@linkplain MyShell} class
 * for terminating shell.
 * 
 * @author Matteo Miloš
 *
 */
public class ExitCommand extends AbstractCommand implements ShellCommand {

	/**
	 * Public constructor used for creating a new exit command
	 */
	public ExitCommand() {
		super("exit", Arrays.asList("The exit command is used for terminating shell."));
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (arguments != null) {
			env.writeln("Command exit doesn't have any arguments");
			return ShellStatus.CONTINUE;
		}
		return ShellStatus.TERMINATE;
	}

}
