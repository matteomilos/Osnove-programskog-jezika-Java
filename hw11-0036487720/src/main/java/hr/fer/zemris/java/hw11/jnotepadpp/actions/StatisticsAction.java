package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.Tab;
import hr.fer.zemris.java.hw11.jnotepadpp.local.swing.FormLocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.swing.LocalizableAction;

public class StatisticsAction extends LocalizableAction {

	private JNotepadPP jNotepadPP;

	public StatisticsAction(JNotepadPP jnotepadPP, FormLocalizationProvider flp) {
		super("calculate", flp);
		putValue(Action.NAME, flp.getString("calculate"));
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control P"));
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_P);
		putValue(Action.SHORT_DESCRIPTION, "Used to calculate statistical info of document");
		flp.getProvider().addLocalizationListener(() -> update());
		this.jNotepadPP = jnotepadPP;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int currentIndex = jNotepadPP.getTabbedPane().getSelectedIndex();
		if (currentIndex >= 0) {
			JScrollPane scrollPane = (JScrollPane) jNotepadPP.getTabbedPane().getComponentAt(currentIndex);
			Tab currentTab = (Tab) scrollPane.getViewport().getView();
			String text = currentTab.getText();
			int numOfChars = text.length();
			int numOfNonBlankChars = text.replaceAll("\\s+", "").length();
			int numOfLines = currentTab.getLineCount();
			JOptionPane.showMessageDialog(
					jNotepadPP,
					"Your document has " + numOfChars + " characters, " + numOfNonBlankChars
							+ " non-blank characters and " + numOfLines + " lines."
			);

		}
	}

}
