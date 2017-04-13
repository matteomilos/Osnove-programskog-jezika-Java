package hr.fer.zemris.java.hw06.shell.commands;

import java.util.Arrays;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.MyShell;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Command/class <code>SymbolCommand</code> is used in {@linkplain MyShell}
 * class for displaying and modifying current command line symbols used by
 * shell.
 * 
 * <ul>
 * <li>PROMPT symbol is displayed at the beginning of each command.</li>
 * <li>MORELINES symbol informs shell that more lines of command are expected.
 * </li>
 * <li>MULTILINE symbol is displayed for each line that is part of multi-line
 * command (except for the first one) at the beginning, followed by a single
 * whitespace.</li>
 * </ul>
 * 
 * @author Matteo Miloš
 *
 */
public class SymbolCommand extends AbstractCommand implements ShellCommand {

	/**
	 * Public constructor used for creating a new symbol command
	 */
	public SymbolCommand() {
		super("symbol", Arrays.asList("The symbol command takes either one or two arguments.",
				"If it takes one argument, it has to be one of these:", "PROMPT", "MORELINES", "MULTILINE",
				"Then it will display symbol that represents one of those values.", "If there are two arguments given",
				"first argument has to be the same", "and second argument which has to be single character",
				"will be used as a new symbol for value from the first argument."));
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {

		if (arguments == null) {
			env.writeln("You must provide arguments for this command.");
			return ShellStatus.CONTINUE;
		}

		String[] args = arguments.split("\\s+");

		if (args.length < 1 || args.length > 2) {
			env.writeln("Invalid arguments for symbol command");
			return ShellStatus.CONTINUE;
		}

		switch (args[0]) {
		case "PROMPT":
			if (args.length == 1) {
				env.writeln("Symbol for PROMPT is '" + env.getPromptSymbol() + "'");
			} else if (args.length == 2 && args[1].length() == 1) {
				char oldPrompt = env.getPromptSymbol();
				char newPrompt = args[1].charAt(0);
				env.setPromptSymbol(newPrompt);
				env.writeln("Symbol for PROMPT changed from '" + oldPrompt + "' to '" + newPrompt + "'");
			} else {
				env.writeln("Invalid arguments for symbol command.");
			}
			break;

		case "MORELINES":
			if (args.length == 1) {
				env.writeln("Symbol for MORELINES is '" + env.getMorelinesSymbol() + "'");
			} else if (args.length == 2 && args[1].length() == 1) {
				char oldMorelines = env.getMorelinesSymbol();
				char newMorelines = args[1].charAt(0);
				env.setMorelinesSymbol(newMorelines);
				env.writeln("Symbol for MORELINES changed from '" + oldMorelines + "' to '" + newMorelines + "'");
			} else {
				env.writeln("Invalid arguments for symbol command.");
			}
			break;
		case "MULTILINE":
			if (args.length == 1) {
				env.writeln("Symbol for MULTILINE is '" + env.getMultilineSymbol() + "'");
			} else if (args.length == 2 && args[1].length() == 1) {
				char oldMultiline = env.getMultilineSymbol();
				char newMultiline = args[1].charAt(0);
				env.setMultilineSymbol(newMultiline);
				env.writeln("Symbol for MULTILINE changed from " + oldMultiline + " to " + newMultiline + ".");
			} else {
				env.writeln("Invalid arguments for symbol command.");
			}
			break;
		default:
			env.writeln("Invalid symbol identificator.");
		}

		return ShellStatus.CONTINUE;
	}

}
