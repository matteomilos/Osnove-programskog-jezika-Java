package hr.fer.zemris.java.hw06.shell.commands;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

public class CatCommand extends AbstractCommand implements ShellCommand {

	public CatCommand() {
		super("cat", Arrays.asList("descr"));

	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {

		final Pattern pattern = Pattern.compile("\\s*((\"(.+)\")|(\\S+))(\\s+(\\S*))?\\s*");
		final Matcher matcher = pattern.matcher(arguments);

		Path file = null;
		Charset charset = null;

		if (matcher.find()) {
			file = matcher.group(3) != null ? Paths.get(matcher.group(3)) : Paths.get(matcher.group(4));
			try {
				charset = matcher.group(6) != null ? Charset.forName(matcher.group(6)) : Charset.defaultCharset();
			} catch (Exception exc) {
				env.writeln("Charset is not supported.");
				return ShellStatus.CONTINUE;
			}
		} else {
			throw new RuntimeException("You have specified invalid arguments for cat comand");
		}
		try (BufferedReader br = new BufferedReader(
				new InputStreamReader(new FileInputStream(file.toFile()), charset))) {
			String line = null;
			while ((line = br.readLine()) != null) {
				env.writeln(line);
			}
		} catch (IOException e) {
			env.writeln("Path specified is invalid.");
		}

		return ShellStatus.CONTINUE;
	}

}
