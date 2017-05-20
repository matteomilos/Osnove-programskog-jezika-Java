package hr.fer.zemris.java.hw11.jnotepadpp.icons;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * Class used for getting icons for toolbars.
 * 
 * @author Matteo Miloš
 *
 */
public class Icons {

	/** The Icon SAVE_ICON. */
	public static final Icon SAVE_ICON = getIcon("saveFile");

	/** The Icon COPY_ICON. */
	public static final Icon COPY_ICON = getIcon("copy");

	/** The Icon NEWFILE_ICON. */
	public static final Icon NEWFILE_ICON = getIcon("newFile");

	/** The Icon SAVEAS_ICON. */
	public static final Icon SAVEAS_ICON = getIcon("saveAs");

	/** The Icon CLOSE_ICON. */
	public static final Icon CLOSE_ICON = getIcon("closeFile");

	/** The Icon CUT_ICON. */
	public static final Icon CUT_ICON = getIcon("cut");

	/** The Icon PASTE. */
	public static final Icon PASTE_ICON = getIcon("paste");

	/** The OPEN_EXISTING icon. */
	public static final Icon OPEN_EXISTING = getIcon("openFile");

	/** The STATISTICS icon. */
	public static final Icon STATISTICS_ICON = getIcon("stats");

	/** The green diskette icon */
	public static final Icon GREEN_ICON = getIcon("green");

	/** The red diskette icon */
	public static final Icon RED_ICON = getIcon("red");

	/**
	 * Method used for loading icon with the given name
	 * 
	 * @param name
	 *            name of the icon
	 * @return {@link Icon} instance
	 */
	private static Icon getIcon(String name) {
		InputStream is = Icons.class.getResourceAsStream(name + ".png");
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

	/**
	 * Method used for reading all the bytes from the given input stream.
	 * 
	 * @param is
	 *            input stream
	 * @return byte array
	 */
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
