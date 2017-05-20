package hr.fer.zemris.java.hw11.jnotepadpp;

import static hr.fer.zemris.java.hw11.jnotepadpp.icons.Icons.GREEN_ICON;
import static hr.fer.zemris.java.hw11.jnotepadpp.icons.Icons.RED_ICON;

import java.awt.Component;
import java.nio.file.Path;

import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Class representing one tab of our application. It is derived from
 * {@link JTextArea} class.
 * 
 * @author Matteo Miloš
 *
 */
public class Tab extends JTextArea {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Instance of main frame of this application.
	 */
	private JNotepadPP jNotepadPP;

	/**
	 * Text area of this tab
	 */
	private JTextArea editor;

	/**
	 * Path of file that is opened in this tab.
	 */
	private Path openedFilePath;

	/**
	 * Name of file that is opened in this tab.
	 */
	private String simpleName;

	/**
	 * String representation of the path of file that is opened in this tab.
	 */
	private String longName;

	/** Flag that marks if current tab is changed from the last save. */
	private boolean changed;

	/**
	 * Constructor that initializes new tab in our application.
	 * 
	 * @param path
	 *            path of the file
	 * @param text
	 *            text in the file
	 * @param tabName
	 *            name of the file
	 * @param jNotepadPP
	 *            instance of {@link JNotepadPP} class
	 */
	public Tab(Path path, String text, String tabName, JNotepadPP jNotepadPP) {
		this.jNotepadPP = jNotepadPP;
		openedFilePath = path;
		simpleName = tabName;
		longName = path == null ? simpleName : path.toString();
		setDocument(createDefaultModel());
		setText(text);
		this.addCaretListener((e) -> actionEnabled(e.getDot() != e.getMark()));
	}

	/**
	 * Method used for enabling or disabling action buttons for case changing on
	 * the selected text.
	 * 
	 * @param enabled
	 *            should buttons be enabled or disabled
	 */
	private void actionEnabled(boolean enabled) {
		jNotepadPP.getUpperCaseAction().setEnabled(enabled);
		jNotepadPP.getLowerCaseAction().setEnabled(enabled);
		jNotepadPP.getToggleCaseAction().setEnabled(enabled);
	}

	/**
	 * Getter method for text area of this tab.
	 * 
	 * @return text area
	 */
	public JTextArea getEditor() {
		return editor;
	}

	/**
	 * Getter method for path of file opened in this tab.
	 * 
	 * @return path of the file
	 */
	public Path getOpenedFilePath() {
		return openedFilePath;
	}

	/**
	 * Getter method for name of file opened in this tab.
	 * 
	 * @return name of file
	 */
	public String getSimpleName() {
		return simpleName;
	}

	/**
	 * Getter method for {@link String} representation of path of file opened in
	 * this tab.
	 * 
	 * @return path of the file
	 */
	public String getLongName() {
		return longName;
	}

	/**
	 * Method that checks if current tab is changed
	 * 
	 * @return <code>true</code> if it changed, <code>false</code> otherwise.
	 */
	public boolean isChanged() {
		return changed;
	}

	/**
	 * Method that sets {@link #changed} property of this tab
	 * 
	 * @param changed
	 *            flag that says if tab is changed
	 */
	public void setChanged(boolean changed) {
		this.changed = changed;
	}

	/**
	 * Method that sets path of this tab to the given path
	 * 
	 * @param path
	 *            new path
	 */
	public void setOpenedFilePath(Path path) {
		this.openedFilePath = path;
		setSimpleName(path.getFileName().toString());
		setLongName(path.toString());
	}

	/**
	 * Method that sets name of this tab to the given name
	 * 
	 * @param simpleName
	 *            new name
	 */
	public void setSimpleName(String simpleName) {
		this.simpleName = simpleName;
	}

	/**
	 * Method that sets {@link String} representation of path of this tab to the
	 * given stringF
	 * 
	 * @param longName
	 *            new string representation of the path
	 */
	public void setLongName(String longName) {
		this.longName = longName;
	}

	/**
	 * Method used for adding icon to the tab component of tabbed pane.
	 */
	public void addIcon() {

		jNotepadPP.getTabbedPane().setIconAt(jNotepadPP.getnTabs(), GREEN_ICON);
		this.getDocument().addDocumentListener(
				new DocumentListener() {

					@Override
					public void removeUpdate(DocumentEvent e) {
						refresh(true, jNotepadPP.getTabbedPane().getSelectedComponent());
					}

					@Override
					public void insertUpdate(DocumentEvent e) {
						refresh(true, jNotepadPP.getTabbedPane().getSelectedComponent());
					}

					@Override
					public void changedUpdate(DocumentEvent e) {
					}
				}
		);
	}

	/**
	 * Method called when file is changed, used to set icon depending on
	 * {@link #isChanged()} property.
	 * 
	 * @param isChanged
	 *            flag that signals if file is changed
	 * @param scrollPane
	 *            current scrollpane
	 */
	public void refresh(boolean isChanged, Component scrollPane) {
		setChanged(isChanged);
		jNotepadPP.getTabbedPane().setIconAt(
				jNotepadPP.getTabbedPane().indexOfComponent(scrollPane),
				isChanged() ? RED_ICON : GREEN_ICON
		);
	}

}
