package hr.fer.zemris.java.hw06.shell.commands;

import java.util.Arrays;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

public class SymbolCommand extends AbstractCommand implements ShellCommand {

	public SymbolCommand() {
		super("symbol", Arrays.asList("descr"));
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {

		String[] args = arguments.split("\\s+");

		if (args.length < 1 || args.length > 2) {
			throw new RuntimeException("Invalid arguments for symbol command");
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
				throw new RuntimeException("Invalid arguments for symbol command.");
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
				throw new RuntimeException("Invalid arguments for symbol command.");
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
				throw new RuntimeException("Invalid arguments for symbol command.");
			}
			break;
		default:
			throw new RuntimeException("Invalid symbol identificator.");
		}

		return ShellStatus.CONTINUE;
	}

}
