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

		try {
			env.writeln("Welcome to MyShell v 1.0");
			while (true) {
				env.write(env.getPromptSymbol().toString() + " ");
				String line = env.readLine().trim();

				while (line.endsWith(env.getMorelinesSymbol().toString())) {
					env.write(env.getMultilineSymbol().toString() + " ");
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
					env.writeln("Command " + command + " does not exist. Type help to list existing commands.");
					continue;
				}
				if (shellCommand.executeCommand(env, argument).equals(ShellStatus.TERMINATE)) {
					break;
				}
			}

			env.writeln("Goodbye");
		} catch (ShellIOException exc) {
			System.out.println("ShellIOException, terminating.");
			System.exit(0);
		}

	}

	private static class EnvironmentImpl implements Environment {

		private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		private BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

		private char promptSymbol = '>';

		private char moreLinesSymbol = '\\';

		private char multilineSymbol = '|';

		/*
		 * (non-Javadoc)
		 * 
		 * @see hr.fer.zemris.java.hw06.shell.Environment#readLine()
		 */
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

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * hr.fer.zemris.java.hw06.shell.Environment#write(java.lang.String)
		 */
		@Override
		public void write(String text) throws ShellIOException {
			try {
				writer.write(text);
				writer.flush();
			} catch (IOException e) {
				throw new ShellIOException(e.getMessage());
			}

		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * hr.fer.zemris.java.hw06.shell.Environment#writeln(java.lang.String)
		 */
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

		/*
		 * (non-Javadoc)
		 * 
		 * @see hr.fer.zemris.java.hw06.shell.Environment#commands()
		 */
		@Override
		public SortedMap<String, ShellCommand> commands() {
			return Collections.unmodifiableSortedMap(commands);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see hr.fer.zemris.java.hw06.shell.Environment#getMultilineSymbol()
		 */
		@Override
		public Character getMultilineSymbol() {
			return multilineSymbol;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * hr.fer.zemris.java.hw06.shell.Environment#setMultilineSymbol(java.
		 * lang.Character)
		 */
		@Override
		public void setMultilineSymbol(Character symbol) {
			this.multilineSymbol = symbol;

		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see hr.fer.zemris.java.hw06.shell.Environment#getPromptSymbol()
		 */
		@Override
		public Character getPromptSymbol() {
			return promptSymbol;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * hr.fer.zemris.java.hw06.shell.Environment#setPromptSymbol(java.lang.
		 * Character)
		 */
		@Override
		public void setPromptSymbol(Character symbol) {
			this.promptSymbol = symbol;

		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see hr.fer.zemris.java.hw06.shell.Environment#getMorelinesSymbol()
		 */
		@Override
		public Character getMorelinesSymbol() {
			return moreLinesSymbol;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * hr.fer.zemris.java.hw06.shell.Environment#setMorelinesSymbol(java.
		 * lang.Character)
		 */
		@Override
		public void setMorelinesSymbol(Character symbol) {
			this.moreLinesSymbol = symbol;

		}

	}

}
