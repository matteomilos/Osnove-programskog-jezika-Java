package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;

import javax.swing.Action;
import javax.swing.JScrollPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.Tab;
import hr.fer.zemris.java.hw11.jnotepadpp.local.swing.FormLocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.swing.LocalizableAction;

public class ToggleCaseAction extends LocalizableAction {

	private JNotepadPP jNotepadPP;

	private FormLocalizationProvider flp;

	public ToggleCaseAction(JNotepadPP jNotepadPP, FormLocalizationProvider flp) {
		super("toggle", flp);
		this.jNotepadPP = jNotepadPP;
		this.flp = flp;
		putValue(Action.NAME, flp.getString("toggle"));
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

		int offset = 0;
		int len = Math.abs(tab.getCaret().getDot() - tab.getCaret().getMark());

		if (len == 0) {
			len = doc.getLength();
		} else {
			offset = Math.min(tab.getCaret().getDot(), tab.getCaret().getMark());
		}

		try {
			String text = doc.getText(offset, len);
			text = invertText(text);
			doc.remove(offset, len);
			doc.insertString(offset, text, null);
		} catch (BadLocationException ignorable) {
		}

	}

	private String invertText(String text) {
		StringBuilder sb = new StringBuilder(text.length());

		for (char c : text.toCharArray()) {

			if (Character.isUpperCase(c)) {
				sb.append(Character.toLowerCase(c));
			} else if (Character.isLowerCase(c)) {
				sb.append(Character.toUpperCase(c));
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}
}
