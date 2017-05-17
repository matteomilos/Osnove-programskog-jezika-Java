package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.swing.FormLocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.swing.LocalizableAction;

public class SetEnglishAction extends LocalizableAction {

	public SetEnglishAction(FormLocalizationProvider flp) {
		super("en", flp);
		putValue(Action.NAME, flp.getString("en"));
		flp.getProvider().addLocalizationListener(() -> update());
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		LocalizationProvider.getInstance().setLanguage("en");
		update();
	}
}
