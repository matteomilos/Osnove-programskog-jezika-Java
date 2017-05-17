package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.KeyStroke;
import javax.swing.text.DefaultEditorKit.CutAction;

import hr.fer.zemris.java.hw11.jnotepadpp.local.swing.FormLocalizationProvider;

public class MyCutAction extends CutAction {

	private FormLocalizationProvider flp;

	public MyCutAction(FormLocalizationProvider flp) {
		this.flp = flp;
		putValue(Action.NAME, flp.getString("cut"));
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control X"));
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_U);
		putValue(Action.SHORT_DESCRIPTION, "Used to cut part of the text");
		flp.getProvider().addLocalizationListener(() -> update()

		);
	}

	private void update() {
		putValue(Action.NAME, flp.getString("cut"));
	}
}
