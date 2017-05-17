package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.local.swing.FormLocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.swing.LocalizableAction;

public class NewDocumentAction extends LocalizableAction {

	JNotepadPP jNotepadPP;

	public NewDocumentAction(JNotepadPP jNotepadPP, FormLocalizationProvider flp) {
		super("new", flp);
		putValue(Action.NAME, flp.getString("new"));
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control N"));
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_N);
		putValue(Action.SHORT_DESCRIPTION, "Used to create new document");
		flp.getProvider().addLocalizationListener(()->update());
		this.jNotepadPP = jNotepadPP;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			jNotepadPP.addNewTab(null, null);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		jNotepadPP.getTabbedPane().setSelectedIndex(jNotepadPP.getnTabs() - 1);
	}
}
