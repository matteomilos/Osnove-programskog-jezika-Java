package hr.fer.zemris.java.hw06.shell.commands;

import java.io.File;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.MyShell;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Command/class <code>MkdirCommand</code> is used in {@linkplain MyShell} class
 * for creating new directory and appropriate directory structure from given
 * arguments.
 * 
 * @author Matteo Miloš
 *
 */
public class MkdirCommand extends AbstractCommand implements ShellCommand {

	/**
	 * Public constructor used for creating a new mkdir command
	 */
	public MkdirCommand() {
		super("mkdir", Arrays.asList("The mkdir command takes a single argument:", "directory name",
				"and creates appropriate directory structure."));
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {

		if (arguments == null) {
			env.writeln("You must provide arguments for this command.");
			return ShellStatus.CONTINUE;
		}

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
			env.writeln("Specified directory already exists");
			return ShellStatus.CONTINUE;
		}

		if (file.mkdirs()) {
			env.writeln("Directory structure successfully created.");
		} else {
			env.writeln("Specified directory structure could not be created.");
		}

		return ShellStatus.CONTINUE;
	}

}
