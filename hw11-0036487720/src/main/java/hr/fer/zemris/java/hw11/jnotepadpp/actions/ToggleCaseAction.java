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
import static hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationConstants.*;

/**
 * Action derived from {@link LocalizableAction} class, used to set selected
 * characters to the opposite case of the current one.
 * 
 * @author Matteo Miloš
 *
 */
public class ToggleCaseAction extends LocalizableAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * instance of {@link JNotepadPP} class
	 */
	private JNotepadPP jNotepadPP;

	/**
	 * Constructor that creates new instance of {@link ToggleCaseAction}
	 * 
	 * @param jNotepadPP
	 *            instance of {@link JNotepadPP} class
	 * @param flp
	 *            localization provider
	 */
	public ToggleCaseAction(JNotepadPP jNotepadPP, FormLocalizationProvider flp) {
		super(TOGGLE, flp);
		this.jNotepadPP = jNotepadPP;
		putValue(Action.NAME, flp.getString(TOGGLE));
		putValue(Action.SHORT_DESCRIPTION, flp.getString(TOGGLE+ACTION));
		flp.getProvider().addLocalizationListener(() -> update());
		setEnabled(false);
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

	/**
	 * Method used for inverting case of characters of given text
	 * 
	 * @param text
	 *            text whose characters will be inverted
	 * @return inverted text
	 */
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
