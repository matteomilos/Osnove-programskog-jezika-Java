package hr.fer.zemris.java.hw06.shell;

import java.util.SortedMap;

/**
 * Interface <code>Environment</code> represents an environment that stands as a
 * layer between operating system and shell, which enables the user to use
 * certain commands and execute them.
 * 
 * @author Matteo Miloš
 *
 */
public interface Environment {

	/**
	 * Method that reads one line from standard input.
	 * 
	 * @return String read from input
	 * @throws ShellIOException
	 *             if failure occurs during the reading
	 */
	String readLine() throws ShellIOException;

	/**
	 * Method that writes text to standard output.
	 * 
	 * @param text
	 *            text that will be written
	 * @throws ShellIOException
	 *             if failure occurs during the writing
	 */
	void write(String text) throws ShellIOException;

	/**
	 * Method that writes one line of text to standard output and then starts a
	 * new line.
	 * 
	 * @param text
	 *            text that will be written
	 * @throws ShellIOException
	 *             if failure occurs during the writing
	 */
	void writeln(String text) throws ShellIOException;

	/**
	 * Method that returns sorted map of commands that can be executed in
	 * environment that implements this interface.
	 * 
	 * @return sorted map of commands that are available
	 */
	SortedMap<String, ShellCommand> commands();

	/**
	 * Method uses as getter for 'multiline' symbol that is written in the
	 * environment when user writes command through multiple lines.
	 * 
	 * @return current 'multiline' symbol
	 */
	Character getMultilineSymbol();

	/**
	 * Method used as setter for 'multiline' symbol that is written in the
	 * environment when user writes command through multiple lines.
	 * 
	 * @param symbol
	 *            new 'multiline' symbol
	 */
	void setMultilineSymbol(Character symbol);

	/**
	 * Method uses as getter for 'prompt' symbol that is displayed at the
	 * beginning of each prompt line(if that line is first in users command).
	 * 
	 * @return current 'prompt' symbol
	 */
	Character getPromptSymbol();

	/**
	 * Method uses as setter for 'prompt' symbol that is displayed at the
	 * beginning of each prompt line(if that line is first in users command).
	 * 
	 * @param symbol
	 *            new 'prompt' symbol
	 */
	void setPromptSymbol(Character symbol);

	/**
	 * Method used as getter for 'morelines' symbol that lets user write command
	 * through more than one line.
	 * 
	 * @return current 'more-lines' symbol
	 */
	Character getMorelinesSymbol();

	/**
	 * Method used as setter for 'morelines' symbol that lets user write command
	 * through more than one line.
	 * 
	 * @param symbol
	 *            new 'morelines' symbol
	 */
	void setMorelinesSymbol(Character symbol);

}
