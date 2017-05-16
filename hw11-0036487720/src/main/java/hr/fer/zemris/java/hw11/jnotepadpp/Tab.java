package hr.fer.zemris.java.hw11.jnotepadpp;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;

import javax.swing.ImageIcon;
import javax.swing.JTextArea;

public class Tab extends JTextArea {

	JNotepadPP jNotepadPP;

	private JTextArea editor;

	private String oldText;

	private Path openedFilePath;

	private String simpleName;

	private String longName;

	private boolean changed;

	public Tab(Path path, String text, String tabName, JNotepadPP jNotepadPP) throws IOException {
		this.jNotepadPP = jNotepadPP;
		openedFilePath = path;
		simpleName = tabName;
		longName = path == null ? simpleName : path.toString();
		oldText = text == null ? "" : text;
		setDocument(createDefaultModel());
		setText(text);

	}

	private byte[] readAllBytes(InputStream is) throws IOException {
		int nRead;
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		byte[] data = new byte[4096];

		while ((nRead = is.read(data, 0, data.length)) != -1) {
			buffer.write(data, 0, nRead);
		}

		buffer.flush();
		return buffer.toByteArray();
	}

	public JTextArea getEditor() {
		return editor;
	}

	public String getOldText() {
		return oldText;
	}

	public Path getOpenedFilePath() {
		return openedFilePath;
	}

	public String getSimpleName() {
		return simpleName;
	}

	public String getLongName() {
		return longName;
	}

	public boolean isChanged() {
		return !this.getOldText().equals(this.getText());
	}

	public void setOpenedFilePath(Path path) {
		this.openedFilePath = path;
		setSimpleName(path.getFileName().toString());
		setLongName(path.toString());
	}

	public void setOldText(String oldText) {
		this.oldText = oldText;
	}

	public void setSimpleName(String simpleName) {
		this.simpleName = simpleName;
	}

	public void setLongName(String longName) {
		this.longName = longName;
	}

	public void addIcon() throws IOException {
		InputStream is = this.getClass().getResourceAsStream("icons/red.png");
		if (is == null) {
			throw new NoSuchFileException("icon");
		}
		byte[] red = readAllBytes(is);
		is = this.getClass().getResourceAsStream("icons/green.png");
		if (is == null) {
			throw new NoSuchFileException("icon");
		}
		byte[] green = readAllBytes(is);
		jNotepadPP.getTabbedPane().setIconAt(jNotepadPP.getnTabs(), new ImageIcon(green));
		this.addCaretListener(
				(l) -> {
					jNotepadPP.getTabbedPane().setIconAt(
							jNotepadPP.getTabbedPane().getSelectedIndex(),
							new ImageIcon(this.isChanged() ? red : green)
					);
				}
		);
	}
}
