package hr.fer.zemris.java.hw06.shell.commands;

import java.util.Arrays;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

public class SymbolCommand extends AbstractCommand implements ShellCommand {

	public SymbolCommand() {
		super("symbol", Arrays.asList("The symbol command takes either one or two arguments.",
				"If it takes one argument, it has to be one of these:", "PROMPT", "MORELINES", "MULTILINE",
				"Then it will display symbol that represents one of those values.", "If there are two arguments given",
				"first argument has to be the same", "and second argument which has to be single character",
				"will be used as a new symbol for value from the first argument."));
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {

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
