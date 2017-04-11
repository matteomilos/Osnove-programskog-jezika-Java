package hr.fer.zemris.java.hw06.shell.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

public class LsCommand extends AbstractCommand implements ShellCommand {

	public LsCommand() {
		super("ls", Arrays.asList("descr"));
	}

	public static void main(String[] args) {

	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {

		final Pattern pattern = Pattern.compile("\\s*((\"(.+)\")|(\\S+))\\s*");
		final Matcher matcher = pattern.matcher(arguments.trim());

		Path path = null;
		if (matcher.find()) {
			path = (matcher.group(3) == null) ? Paths.get(matcher.group(1)) : Paths.get(matcher.group(3));
		}
		else{
			env.writeln("Specified path is not valid.");
			return ShellStatus.CONTINUE;
		}
		if (!Files.isDirectory(path)) {
			throw new RuntimeException("Specified path is not directory!");
		}

		File[] files = path.toFile().listFiles();
		for (File file : files) {
			env.writeln(printfile(file));
		}

		return ShellStatus.CONTINUE;

	}

	private String printfile(File file) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Path path = Paths.get(file.getAbsolutePath());
			BasicFileAttributeView faView = Files.getFileAttributeView(path, BasicFileAttributeView.class,
					LinkOption.NOFOLLOW_LINKS);
			BasicFileAttributes attributes;
			attributes = faView.readAttributes();
			FileTime fileTime = attributes.creationTime();
			String formattedDateTime = sdf.format(new Date(fileTime.toMillis()));
			StringBuilder sb = new StringBuilder();
			sb.append(file.isDirectory() ? "d" : "-");
			sb.append(file.canRead() ? "r" : "-");
			sb.append(file.canWrite() ? "w" : "-");
			sb.append(file.canExecute() ? "x" : "-");
			sb.append(" ").append(String.format("%10d", file.length()));
			sb.append(" ").append(formattedDateTime);
			sb.append(" ").append(file.getName());
			return sb.toString();
		} catch (IOException e) {
			throw new RuntimeException("Could not read file attributes");
		}
	}

}
