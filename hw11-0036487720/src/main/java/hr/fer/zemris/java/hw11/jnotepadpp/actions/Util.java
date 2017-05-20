package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import static hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationConstants.ERROR;
import static hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationConstants.INFORMATION;
import static hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationConstants.SAVED;
import static hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationConstants.SAVE_NOT_SUCCESSFUL;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.Collator;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.Tab;
import hr.fer.zemris.java.hw11.jnotepadpp.local.swing.FormLocalizationProvider;

/**
 * Class used for some helper methods used in classes from actions package.
 * 
 * @author Matteo Miloš
 *
 */
public class Util {

	/**
	 * Method used for sorting selected text ascending or descending, based on
	 * the given flag. Method used Croatian collator for comparing strings.
	 * 
	 * @param isAscending
	 *            <code>true</code> if we want ascending sort, false otherwise
	 * @param jNotepadPP
	 *            instance of {@link JNotepadPP} class
	 * @param hrCollator
	 *            Croatian instance of {@link Collator} class
	 */
	public static void sortSelectedText(boolean isAscending, JNotepadPP jNotepadPP, Collator hrCollator) {
		JScrollPane scrollPane = (JScrollPane) jNotepadPP.getTabbedPane().getSelectedComponent();

		if (scrollPane == null) {
			return;
		}

		Tab tab = (Tab) scrollPane.getViewport().getView();
		Document doc = tab.getDocument();

		int len = Math.abs(tab.getCaret().getDot() - tab.getCaret().getMark());
		int offset = len != 0 ? Math.min(tab.getCaret().getDot(), tab.getCaret().getMark()) : doc.getLength();

		try {
			offset = tab.getLineStartOffset(tab.getLineOfOffset(offset));
			len = tab.getLineEndOffset(tab.getLineOfOffset(len + offset));

			String text = doc.getText(offset, len - offset);
			List<String> sort = Arrays.asList(text.split("\\r?\\n"));
			Comparator<String> myComp = (a, b) -> hrCollator.compare(a, b);
			Collections.sort(sort, isAscending ? myComp : myComp.reversed());

			int lines = tab.getLineCount();
			doc.remove(offset, len - offset);

			for (String string : sort) {
				doc.insertString(offset, string + (--lines > 0 ? "\n" : ""), null);
				offset += string.length() + 1;
			}
		} catch (BadLocationException ignorable) {
		}
	}

	/**
	 * Method used for setting case to the upper or lower, based on given flag.
	 * 
	 * @param isUpper
	 *            <code>true</code> if we want upper case, false otherwise
	 * @param text
	 *            string whose characters are going to be set to the new case
	 * @return changed text
	 */
	public static String setCase(boolean isUpper, String text) {
		StringBuilder sb = new StringBuilder(text.length());

		for (char c : text.toCharArray()) {
			sb.append(isUpper ? Character.toUpperCase(c) : Character.toLowerCase(c));
		}

		return sb.toString();
	}

	/**
	 * Method used for converting case of the selected text to the new case,
	 * based on given flag.
	 * 
	 * @param jNotepadPP
	 *            instance of {@link JNotepadPP} class
	 * @param isUpper
	 *            <code>true</code> if we want upper case, false otherwise
	 */
	public static void convertCase(JNotepadPP jNotepadPP, boolean isUpper) {

		JScrollPane scrollPane = (JScrollPane) jNotepadPP.getTabbedPane().getSelectedComponent();
		if (scrollPane == null) {
			return;
		}
		Tab tab = (Tab) scrollPane.getViewport().getView();
		Document doc = tab.getDocument();

		int offset = 0;
		int len = Math.abs(tab.getCaret().getDot() - tab.getCaret().getMark());

		if (len == 0) {
			len = doc.getLength();
		} else {
			offset = Math.min(tab.getCaret().getDot(), tab.getCaret().getMark());
		}

		try {
			String text = doc.getText(offset, len);
			text = Util.setCase(isUpper, text);

			doc.remove(offset, len);
			doc.insertString(offset, text, null);
		} catch (BadLocationException ignorable) {
		}
	}

	/**
	 * Method called from actions {@link SaveAsDocumentAction} and
	 * {@link SaveDocumentAction} during the saving process, used to avoid
	 * duplicating similar code.
	 * 
	 * @param savingTab
	 *            tab being saved
	 * @param fc
	 *            instance of {@link JFileChooser} class
	 * @param jNotepadPP
	 *            instance of {@link JNotepadPP} class
	 * @param flp
	 *            localization provider
	 * @param scrollPane
	 *            current scrollPane
	 */
	public static void executeSaving(
			Tab savingTab,
			JFileChooser fc,
			JNotepadPP jNotepadPP,
			FormLocalizationProvider flp,
			JScrollPane scrollPane
	) {
		if (fc.getSelectedFile() != null) {
			savingTab.setOpenedFilePath(fc.getSelectedFile().toPath());
		}
		try {
			Files.write(savingTab.getOpenedFilePath(), savingTab.getText().getBytes(StandardCharsets.UTF_8));
		} catch (

		Exception exc) {
			JOptionPane.showMessageDialog(
					jNotepadPP,
					flp.getString(SAVE_NOT_SUCCESSFUL),
					flp.getString(ERROR),
					JOptionPane.ERROR_MESSAGE
			);
			return;
		}

		JOptionPane.showMessageDialog(
				jNotepadPP,
				flp.getString(SAVED),
				flp.getString(INFORMATION),
				JOptionPane.INFORMATION_MESSAGE
		);
		jNotepadPP.getTabbedPane()
				.setTitleAt(jNotepadPP.getTabbedPane().indexOfComponent(scrollPane), savingTab.getSimpleName());
		jNotepadPP.getTabbedPane()
				.setToolTipTextAt(jNotepadPP.getTabbedPane().indexOfComponent(scrollPane), savingTab.getLongName());
		savingTab.refresh(false, scrollPane);

	}

}
