package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;

import javax.swing.Action;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.local.swing.FormLocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.swing.LocalizableAction;

public class LowerCaseAction extends LocalizableAction {

	private JNotepadPP jNotepadPP;

	private FormLocalizationProvider flp;

	public LowerCaseAction(JNotepadPP jNotepadPP, FormLocalizationProvider flp) {
		super("lower", flp);
		this.jNotepadPP = jNotepadPP;
		this.flp = flp;
		putValue(Action.NAME, flp.getString("lower"));
		flp.getProvider().addLocalizationListener(() -> update());

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Util.convertCase(jNotepadPP, false);
	}
}
