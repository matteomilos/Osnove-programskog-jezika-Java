package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import javax.swing.Action;
import javax.swing.KeyStroke;
import javax.swing.text.DefaultEditorKit.CopyAction;

import hr.fer.zemris.java.hw11.jnotepadpp.local.swing.FormLocalizationProvider;

public class MyCopyAction extends CopyAction {

	FormLocalizationProvider flp;

	public MyCopyAction(FormLocalizationProvider flp) {
		this.flp = flp;
		putValue(Action.NAME, flp.getString("copy"));
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control C"));
		putValue(Action.SHORT_DESCRIPTION, "Used to copy part of the text");
		flp.getProvider().addLocalizationListener(() -> update()

		);
	}

	private void update() {
		putValue(Action.NAME, flp.getString("copy"));
	}
}
