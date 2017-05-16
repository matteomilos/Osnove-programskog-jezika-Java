package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.Tab;

public class CloseDocumentAction extends AbstractAction {

	private JNotepadPP jNotepadPP;

	public CloseDocumentAction(JNotepadPP jNotepadPP) {
		this.jNotepadPP = jNotepadPP;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int closingIndex = jNotepadPP.getTabbedPane().getSelectedIndex();
		if (closingIndex >= 0) {
			JScrollPane scrollPane = (JScrollPane) jNotepadPP.getTabbedPane().getComponentAt(closingIndex);
			Tab closingTab = (Tab) scrollPane.getViewport().getView();
			if (closingTab.isChanged()) {
				int result = JOptionPane.showConfirmDialog(
						jNotepadPP,
						"Save file \"" + closingTab.getSimpleName() + "\"?",
						"Save",
						JOptionPane.YES_NO_CANCEL_OPTION
				);

				switch (result) {
				case JOptionPane.YES_OPTION:
					jNotepadPP.getSaveDocumentAction().actionPerformed(e);
				case JOptionPane.NO_OPTION:
					jNotepadPP.getTabbedPane().removeTabAt(closingIndex);
					jNotepadPP.setnTabs(jNotepadPP.getnTabs() - 1);
				case JOptionPane.CANCEL_OPTION:
					return;
				}
			} else {
				jNotepadPP.getTabbedPane().removeTabAt(closingIndex);
				jNotepadPP.setnTabs(jNotepadPP.getnTabs() - 1);
			}
		}
	}

}
