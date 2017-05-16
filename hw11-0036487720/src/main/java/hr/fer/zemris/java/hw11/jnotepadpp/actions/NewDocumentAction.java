package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.AbstractAction;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;

public class NewDocumentAction extends AbstractAction {

	JNotepadPP jNotepadPP;

	public NewDocumentAction(JNotepadPP jNotepadPP) {
		this.jNotepadPP = jNotepadPP;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			jNotepadPP.addNewTab(null, null);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		jNotepadPP.getTabbedPane().setSelectedIndex(jNotepadPP.getnTabs()-1);
	}
}
