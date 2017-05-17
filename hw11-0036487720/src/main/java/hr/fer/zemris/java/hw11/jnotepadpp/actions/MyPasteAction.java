package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import javax.swing.Action;
import javax.swing.KeyStroke;
import javax.swing.text.DefaultEditorKit.PasteAction;

import hr.fer.zemris.java.hw11.jnotepadpp.local.swing.FormLocalizationProvider;

public class MyPasteAction extends PasteAction {

	FormLocalizationProvider flp;

	public MyPasteAction(FormLocalizationProvider flp) {
		this.flp = flp;
		putValue(Action.NAME, flp.getString("paste"));
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control V"));
		putValue(Action.SHORT_DESCRIPTION, "Used to paste part of the text");
		flp.getProvider().addLocalizationListener(() -> update()

		);
	}

	private void update() {
		putValue(Action.NAME, flp.getString("paste"));
	}
}
