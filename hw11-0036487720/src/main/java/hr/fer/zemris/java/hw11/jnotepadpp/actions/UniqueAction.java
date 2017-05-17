package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.Tab;
import hr.fer.zemris.java.hw11.jnotepadpp.local.swing.FormLocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.swing.LocalizableAction;
import static hr.fer.zemris.java.hw11.jnotepadpp.actions.ActionConstants.*;

public class UniqueAction extends LocalizableAction {

	private JNotepadPP jNotepadPP;

	private FormLocalizationProvider flp;

	public UniqueAction(JNotepadPP jNotepadPP, FormLocalizationProvider flp) {
		super("unique", flp);
		this.jNotepadPP = jNotepadPP;
		this.flp = flp;
		putValue(Action.NAME, flp.getString("unique"));
		flp.getProvider().addLocalizationListener(() -> update());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
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
			Set<String> lines = new LinkedHashSet<>(Arrays.asList(text.split("\\r?\\n")));

			int numOfLines = tab.getLineCount();
			doc.remove(offset, len - offset);

			for (String string : lines) {
				doc.insertString(offset, string + (--numOfLines > 0 ? "\n" : ""), null);
				offset += string.length() + 1;
			}
		} catch (BadLocationException ignorable) {
		}
	}
}
