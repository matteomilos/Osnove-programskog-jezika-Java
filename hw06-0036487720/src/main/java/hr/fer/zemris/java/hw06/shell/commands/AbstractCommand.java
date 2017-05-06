package hr.fer.zemris.java.hw06.shell.commands;

import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.ShellCommand;

/**
 * Abstract class <code>AbstractCommand</code> is a wrapper class for a shell
 * command that contains the {@linkplain ShellCommand} name and description.
 * Implementation of this class is in the classes that extend this one.
 * 
 * @author Matteo Miloš
 *
 */
public abstract class AbstractCommand implements ShellCommand {

	/** Name of the command */
	private String name;

	/** Description of the command */
	private List<String> description;

	/**
	 * Regex used for reading filepath (either with or without quotation marks)
	 */

	public static final String REGEX_FOR_READING_FILEPATH = "\\s*((\"(.+)(?<!\\\\)\")|(\\S+))";

	/**
	 * Constructor used for creating new (abstract) command with provided name
	 * and command description lines.
	 * 
	 * @param name
	 *            name of the command
	 * @param description
	 *            description of the command
	 */
	public AbstractCommand(String name, List<String> description) {
		this.name = name;
		this.description = description;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see hr.fer.zemris.java.hw06.shell.ShellCommand#getCommandName()
	 */
	@Override
	public String getCommandName() {
		return name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see hr.fer.zemris.java.hw06.shell.ShellCommand#getCommandDescription()
	 */
	@Override
	public List<String> getCommandDescription() {
		return Collections.unmodifiableList(description);

	}
}