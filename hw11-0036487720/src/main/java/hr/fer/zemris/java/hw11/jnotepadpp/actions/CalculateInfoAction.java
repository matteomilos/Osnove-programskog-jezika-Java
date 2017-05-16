package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.Tab;

public class CalculateInfoAction extends AbstractAction {

	private JNotepadPP jNotepadPP;

	public CalculateInfoAction(JNotepadPP jnotepadPP) {
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
			int numOfLines = text.split("\r\n|\r|\n", -1).length;
			JOptionPane.showMessageDialog(
					jNotepadPP,
					"Your document has " + numOfChars + " characters, " + numOfNonBlankChars
							+ " non-blank characters and " + numOfLines + " lines."
			);

		}
	}

}
