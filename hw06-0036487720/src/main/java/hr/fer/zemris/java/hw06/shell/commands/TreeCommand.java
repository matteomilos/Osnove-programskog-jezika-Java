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
import hr.fer.zemris.java.hw06.shell.MyShell;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Command/class <code>TreeCommand</code> is used in {@linkplain MyShell} class
 * for listing directory tree structure for passed directory.
 * 
 * @author Matteo Miloš
 *
 */
public class TreeCommand extends AbstractCommand implements ShellCommand {

	/**
	 * Public constructor used for creating a new tree command
	 */
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
		final Pattern pattern = Pattern.compile(REGEX_FOR_READING_FILEPATH);
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

	/**
	 * Local implementation of the {@linkplain SimpleFileVisitor} class. It
	 * performs structured output of file and directory names to the given
	 * environment.
	 * 
	 * @author Matteo Miloš
	 *
	 */
	private static class FileVisitor extends SimpleFileVisitor<Path> {

		/** Current indentation in directory structure */
		private int indent = 0;
		/** Environment used for information output */
		private Environment env;

		/**
		 * Public constructor that receives reference to environment which will
		 * be used for communication of <code>FileVisitor</code> and
		 * {@linkplain MyShell}
		 * 
		 * @param env
		 *            environment used
		 */
		public FileVisitor(Environment env) {
			this.env = env;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * java.nio.file.SimpleFileVisitor#preVisitDirectory(java.lang.Object,
		 * java.nio.file.attribute.BasicFileAttributes)
		 */
		@Override
		public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
			print(dir);
			indent += 2;
			return FileVisitResult.CONTINUE;
		}

		/**
		 * Method used for printing current directory tree structure.
		 * 
		 * @param directory
		 *            current directory
		 */
		private void print(Path directory) {
			if (indent == 0) {
				env.writeln(directory.getFileName().toString());
			} else {
				env.writeln(String.format("%" + indent + "s%s", "", directory.getFileName()));
			}

		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.nio.file.SimpleFileVisitor#visitFile(java.lang.Object,
		 * java.nio.file.attribute.BasicFileAttributes)
		 */
		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
			print(file);
			return FileVisitResult.CONTINUE;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * java.nio.file.SimpleFileVisitor#postVisitDirectory(java.lang.Object,
		 * java.io.IOException)
		 */
		@Override
		public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
			indent -= 2;
			return FileVisitResult.CONTINUE;
		}

	}

}
