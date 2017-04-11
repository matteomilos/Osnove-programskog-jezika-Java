package hr.fer.zemris.java.hw06.shell.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

public class TreeCommand extends AbstractCommand implements ShellCommand {

	public TreeCommand() {
		super("tree",
				Arrays.asList("The tree command expects a single argument:", "directory name",
						"and prints a tree in a way that", "each directory level shifts output",
						"two characters to the right."));
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {

		if (arguments == null) {
			env.writeln("You have to specify path for tree command.");
			return ShellStatus.CONTINUE;
		}
		final Pattern pattern = Pattern.compile("\\s*((\"(.+)\")|(\\S+))\\s*");
		final Matcher matcher = pattern.matcher(arguments.trim());

		File file = null;
		if (matcher.find()) {
			try {
				file = (matcher.group(3) == null) ? new File(matcher.group(1)) : new File(matcher.group(3));
			} catch (IllegalStateException | IndexOutOfBoundsException | NullPointerException exc) {
				env.writeln("Invalid arguments for tree comand.");
			}
		} else {
			env.writeln("Specified path is not valid.");
			return ShellStatus.CONTINUE;
		}

		if (!file.isDirectory()) {
			env.writeln("Specified path is not directory!");
			return ShellStatus.CONTINUE;
		}

		FileVisitor visitor = new FileVisitor(env);

		try {
			Files.walkFileTree(file.toPath(), visitor);
		} catch (IOException e) {
			env.writeln(e.getMessage());
		}

		return ShellStatus.CONTINUE;
	}

	private static class FileVisitor extends SimpleFileVisitor<Path> {

		private int indent = 0;
		private Environment env;

		public FileVisitor(Environment env) {
			this.env = env;
		}

		@Override
		public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
			print(dir);
			indent += 2;
			return FileVisitResult.CONTINUE;
		}

		private void print(Path dir) {
			if (indent == 0) {
				env.writeln(dir.getFileName().toString());
			} else {
				env.writeln(String.format("%" + indent + "s%s", "", dir.getFileName()));
			}

		}

		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
			print(file);
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
			indent -= 2;
			return FileVisitResult.CONTINUE;
		}

	}

}
