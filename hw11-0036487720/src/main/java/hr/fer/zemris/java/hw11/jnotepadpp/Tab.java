package hr.fer.zemris.java.hw11.jnotepadpp;

import static hr.fer.zemris.java.hw11.jnotepadpp.icons.Icons.GREEN_ICON;
import static hr.fer.zemris.java.hw11.jnotepadpp.icons.Icons.RED_ICON;

import java.io.IOException;
import java.nio.file.Path;

import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class Tab extends JTextArea {

	JNotepadPP jNotepadPP;

	private JTextArea editor;

	private Path openedFilePath;

	private String simpleName;

	private String longName;

	private boolean changed;

	public Tab(Path path, String text, String tabName, JNotepadPP jNotepadPP) throws IOException {
		this.jNotepadPP = jNotepadPP;
		openedFilePath = path;
		simpleName = tabName;
		longName = path == null ? simpleName : path.toString();
		setDocument(createDefaultModel());
		setText(text);

	}

	public JTextArea getEditor() {
		return editor;
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
		return changed;
	}

	public void setChanged(boolean changed) {
		this.changed = changed;
	}

	public void setOpenedFilePath(Path path) {
		this.openedFilePath = path;
		setSimpleName(path.getFileName().toString());
		setLongName(path.toString());
	}

	public void setSimpleName(String simpleName) {
		this.simpleName = simpleName;
	}

	public void setLongName(String longName) {
		this.longName = longName;
	}

	public void addIcon() throws IOException {

		jNotepadPP.getTabbedPane().setIconAt(jNotepadPP.getnTabs(), GREEN_ICON);
		this.getDocument().addDocumentListener(
				new DocumentListener() {

					@Override
					public void removeUpdate(DocumentEvent e) {
						jNotepadPP.getTabbedPane().setIconAt(jNotepadPP.getTabbedPane().getSelectedIndex(), RED_ICON);
						setChanged(true);
					}

					@Override
					public void insertUpdate(DocumentEvent e) {
						jNotepadPP.getTabbedPane().setIconAt(jNotepadPP.getTabbedPane().getSelectedIndex(), RED_ICON);
						setChanged(true);
					}

					@Override
					public void changedUpdate(DocumentEvent e) {
					}
				}
		);
	}
}
