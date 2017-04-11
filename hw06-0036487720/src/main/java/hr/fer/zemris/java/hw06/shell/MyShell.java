package hr.fer.zemris.java.hw06.shell;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Collections;
import java.util.Map;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

import hr.fer.zemris.java.hw06.shell.commands.CatCommand;
import hr.fer.zemris.java.hw06.shell.commands.CharsetsCommand;
import hr.fer.zemris.java.hw06.shell.commands.CopyCommand;
import hr.fer.zemris.java.hw06.shell.commands.ExitCommand;
import hr.fer.zemris.java.hw06.shell.commands.HelpCommand;
import hr.fer.zemris.java.hw06.shell.commands.HexdumpCommand;
import hr.fer.zemris.java.hw06.shell.commands.LsCommand;
import hr.fer.zemris.java.hw06.shell.commands.MkdirCommand;
import hr.fer.zemris.java.hw06.shell.commands.SymbolCommand;
import hr.fer.zemris.java.hw06.shell.commands.TreeCommand;

public class MyShell {

	private static SortedMap<String, ShellCommand> commands;

	private static Environment env = new EnvironmentImpl();

	static {
		commands = new TreeMap<>();
		ShellCommand[] cc = { new CatCommand(), new CharsetsCommand(), new LsCommand(), new TreeCommand(),
				new ExitCommand(), new HexdumpCommand(), new MkdirCommand(), new HelpCommand(), new CopyCommand(),
				new SymbolCommand() };
		for (ShellCommand c : cc) {
			commands.put(c.getCommandName(), c);
		}
	}

	public static void main(String[] args) {

		env.writeln("Welcome to MyShell v 1.0");
		while (true) {
			env.write(env.getPromptSymbol().toString()+" ");
			String line = env.readLine().trim();

			while (line.endsWith(env.getMorelinesSymbol().toString())) {
				env.write(env.getMultilineSymbol().toString()+ " ");
				line = line.substring(0, line.length() - 1) + env.readLine().trim();
			}
			String command = null;
			String argument = null;
			if (line.contains(" ")) {
				String[] split = line.split(" ", 2);
				command = split[0];
				argument = split[1];
			} else {
				command = line;
			}

			ShellCommand shellCommand = commands.get(command.toLowerCase());
			if (shellCommand == null) {
				env.writeln("Invalid command");
				continue;
			}

			try {
				if (shellCommand.executeCommand(env, argument).equals(ShellStatus.TERMINATE)) {
					break;
				}
			} catch (RuntimeException exc) {
				env.writeln(exc.getMessage());
			}

		}

		env.writeln("Goodbye");

	}

	private static class EnvironmentImpl implements Environment {

		private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		private BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

		private char promptSymbol = '>';

		private char moreLinesSymbol = '\\';

		private char multilineSymbol = '|';

		@Override
		public String readLine() throws ShellIOException {
			String read;
			try {
				read = reader.readLine();
			} catch (IOException e) {
				throw new ShellIOException();
			}
			return read;
		}

		@Override
		public void write(String text) throws ShellIOException {
			try {
				writer.write(text);
				writer.flush();
			} catch (IOException e) {
				throw new ShellIOException(e.getMessage());
			}

		}

		@Override
		public void writeln(String text) throws ShellIOException {
			try {
				writer.write(text);
				writer.newLine();
				writer.flush();
			} catch (IOException e) {
				throw new ShellIOException(e.getMessage());
			}

		}

		@Override
		public SortedMap<String, ShellCommand> commands() {
			return Collections.unmodifiableSortedMap(commands);
		}

		@Override
		public Character getMultilineSymbol() {
			return multilineSymbol;
		}

		@Override
		public void setMultilineSymbol(Character symbol) {
			this.multilineSymbol = symbol;

		}

		@Override
		public Character getPromptSymbol() {
			return promptSymbol;
		}

		@Override
		public void setPromptSymbol(Character symbol) {
			this.promptSymbol = symbol;

		}

		@Override
		public Character getMorelinesSymbol() {
			return moreLinesSymbol;
		}

		@Override
		public void setMorelinesSymbol(Character symbol) {
			this.moreLinesSymbol = symbol;

		}

	}

}
