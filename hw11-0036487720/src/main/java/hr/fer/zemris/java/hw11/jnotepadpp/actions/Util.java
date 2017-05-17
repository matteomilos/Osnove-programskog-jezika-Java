package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import static hr.fer.zemris.java.hw11.jnotepadpp.actions.ActionConstants.NO_TAB_OPENED;
import static hr.fer.zemris.java.hw11.jnotepadpp.actions.ActionConstants.SAVE_AS;

import java.text.Collator;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.Tab;
import hr.fer.zemris.java.hw11.jnotepadpp.local.swing.FormLocalizationProvider;

public class Util {

	public static void sortSelectedText(boolean ascending, JNotepadPP jNotepadPP, Collator hrCollator) {
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
			Collections.sort(sort, ascending ? myComp : myComp.reversed());

			int lines = tab.getLineCount();
			doc.remove(offset, len - offset);

			for (String string : sort) {
				doc.insertString(offset, string + (--lines > 0 ? "\n" : ""), null);
				offset += string.length() + 1;
			}
		} catch (BadLocationException ignorable) {
		}
	}

	public static String setCase(boolean isUpper, String text) {
		StringBuilder sb = new StringBuilder(text.length());

		for (char c : text.toCharArray()) {
			sb.append(isUpper ? Character.toUpperCase(c) : Character.toLowerCase(c));
		}

		return sb.toString();
	}

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

}
