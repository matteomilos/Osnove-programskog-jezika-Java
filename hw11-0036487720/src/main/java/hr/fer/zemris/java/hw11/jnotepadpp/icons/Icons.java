package hr.fer.zemris.java.hw11.jnotepadpp.icons;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public class Icons {

	/** The Icon SAVE_ICON. */
	public static final Icon SAVE_ICON = getIcon("saveFile.png");

	/** The Icon COPY_ICON. */
	public static final Icon COPY_ICON = getIcon("copy.png");

	/** The Icon NEWFILE_ICON. */
	public static final Icon NEWFILE_ICON = getIcon("newFile.png");

	/** The Icon SAVEAS_ICON. */
	public static final Icon SAVEAS_ICON = getIcon("saveAs.png");

	/** The Icon CLOSE_ICON. */
	public static final Icon CLOSE_ICON = getIcon("closeFile.png");

	/** The Icon CUT_ICON. */
	public static final Icon CUT_ICON = getIcon("cut.png");

	/** The Icon EXIT. */
	public static final Icon EXIT = getIcon("exit.png");

	/** The Icon PASTE. */
	public static final Icon PASTE_ICON = getIcon("paste.png");

	/** The OPEN_EXISTING icon. */
	public static final Icon OPEN_EXISTING = getIcon("openFile.png");

	/** The STATISTICS icon. */
	public static final Icon STATISTICS_ICON = getIcon("stats.png");

	public static final Icon GREEN_ICON = getIcon("green.png");

	public static final Icon RED_ICON = getIcon("red.png");

	private static Icon getIcon(String name) {
		Icons icons = new Icons();
		InputStream is = icons.getInputStream(name);
		if (is == null) {
			throw new IllegalArgumentException("No icon provided");
		}
		byte[] green;
		green = readAllBytes(is);
		try {
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ImageIcon(green);
	}

	private InputStream getInputStream(String name) {
		return this.getClass().getResourceAsStream(name);
	}

	private static byte[] readAllBytes(InputStream is) {
		int nRead;
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		byte[] data = new byte[4096];

		try {
			while ((nRead = is.read(data, 0, data.length)) != -1) {
				buffer.write(data, 0, nRead);
			}
			buffer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return buffer.toByteArray();
	}
}
