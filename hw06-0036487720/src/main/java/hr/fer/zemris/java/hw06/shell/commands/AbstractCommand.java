package hr.fer.zemris.java.hw06.shell.commands;

import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.ShellCommand;

public abstract class AbstractCommand implements ShellCommand {

	private String name;

	private List<String> description;

	public AbstractCommand(String name, List<String> description) {
		this.name = name;
		this.description = description;
	}

	@Override
	public String getCommandName() {
		return name;
	}

	@Override
	public List<String> getCommandDescription() {
		return Collections.unmodifiableList(description);
	}

}
