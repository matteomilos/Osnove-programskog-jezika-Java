package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import static hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationConstants.ACTION;
import static hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationConstants.UNIQUE;

import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.swing.Action;
import javax.swing.JScrollPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.Tab;
import hr.fer.zemris.java.hw11.jnotepadpp.local.swing.FormLocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.swing.LocalizableAction;

/**
 * Action derived from {@link LocalizableAction} class, used to remove identical
 * lines from the selected text.
 * 
 * @author Matteo Miloš
 *
 */
public class UniqueAction extends LocalizableAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * instance of {@link JNotepadPP} class
	 */
	private JNotepadPP jNotepadPP;

	/**
	 * Constructor that creates new instance of {@link UniqueAction}
	 * 
	 * @param jNotepadPP
	 *            instance of {@link JNotepadPP} class
	 * @param flp
	 *            localization provider
	 */
	public UniqueAction(JNotepadPP jNotepadPP, FormLocalizationProvider flp) {
		super(UNIQUE, flp);
		this.jNotepadPP = jNotepadPP;

		putValue(Action.NAME, flp.getString(UNIQUE));
		putValue(Action.SHORT_DESCRIPTION, flp.getString(UNIQUE + ACTION));
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
