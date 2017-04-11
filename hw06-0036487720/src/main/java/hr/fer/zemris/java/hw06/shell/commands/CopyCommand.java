package hr.fer.zemris.java.hw06.shell.commands;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

public class CopyCommand extends AbstractCommand implements ShellCommand {

	public CopyCommand() {
		super("copy", Arrays.asList("The copy command expects two arguments:", "source file name", "and",
				"destination file name.", "If destination file exists, you must choose do you want to overwrite it.",
				"First argument mustn't be directory.", "Second argument can be directory and",
				"it will be assumed that you want to copy", "the original file into that directory using",
				"the original file name."));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * hr.fer.zemris.java.hw06.shell.ShellCommand#executeCommand(hr.fer.zemris.
	 * java.hw06.shell.Environment, java.lang.String)
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {

		final Pattern pattern = Pattern.compile("\\s*((\"(.+)\")|(\\S+))\\s+((\"(.+)\")|(\\S+))");
		final Matcher matcher = pattern.matcher(arguments.trim());

		File file1 = null;
		File file2 = null;

		if (matcher.find()) {
			file1 = (matcher.group(3) == null) ? new File(matcher.group(1)) : new File(matcher.group(3));
			file2 = (matcher.group(7) == null) ? new File(matcher.group(5)) : new File(matcher.group(7));
		}

		if (!file1.isFile()) {
			env.writeln("Origin path needs to lead to a file");
		}

		if (file1.equals(file2)) {
			env.writeln("Origin and destination paths are equals");
		}

		if (file2.isDirectory()) {
			file2 = new File(file2.getAbsolutePath().toString(), file1.getName());
		}

		if (file2.exists()) {
			env.writeln("Do you want to overwrite destination file? (YES\\NO)");
			env.write(env.getPromptSymbol() + " ");
			String answer = env.readLine();
			if (!answer.toLowerCase().equals("yes")) {
				return ShellStatus.CONTINUE;
			}
		}

		try (InputStream inStream = new BufferedInputStream(Files.newInputStream(file1.toPath()));
				OutputStream outStream = new BufferedOutputStream(Files.newOutputStream(file2.toPath()))) {
			byte[] buffer = new byte[4096];
			int length;
			while ((length = inStream.read(buffer)) > 0) {
				outStream.write(buffer, 0, length);
			}
			env.writeln("File has been copied successfully!");
		} catch (IOException exception) {
			env.writeln("Copying unsuccesful. Couldn't acces given files.");
		}

		return ShellStatus.CONTINUE;
	}

}
