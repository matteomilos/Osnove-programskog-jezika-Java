package hr.fer.zemris.java.hw06.shell;

import java.util.List;

/**
 * Public interface ShellCommand defines methods any shell command of MyShell
 * has to have. Methods are:
 * <ul>
 * <li>{@linkplain ShellCommand#getCommandName()}</li>
 * <li>{@linkplain ShellCommand#getCommandDescription()}</li>
 * <li>{@linkplain ShellCommand#executeCommand(Environment, String)}</li>
 * </ul>
 * 
 * @author Matteo Miloš
 */
public interface ShellCommand {

	/**
	 * Method that executes command and returns it's {@linkplain ShellStatus}
	 * and with that status defines what should {@linkplain MyShell} do after
	 * executing a command.
	 * 
	 * @param env
	 *            environment implementation on which a command is executed
	 * @param arguments
	 *            arguments passed to method (not necessary)
	 * @return status of shell
	 */
	ShellStatus executeCommand(Environment env, String arguments);

	/**
	 * Method used as getter for current commands name
	 * 
	 * @return command name
	 */
	String getCommandName();

	/**
	 * Method used as getter for current commands description. Description is
	 * returned as List parameterized by Strings.
	 * 
	 * @return description of command
	 */
	List<String> getCommandDescription();

}
