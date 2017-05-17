package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;

import javax.swing.Action;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.local.swing.FormLocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.swing.LocalizableAction;

public class UpperCaseAction extends LocalizableAction {

	private JNotepadPP jNotepadPP;

	private FormLocalizationProvider flp;

	public UpperCaseAction(JNotepadPP jNotepadPP, FormLocalizationProvider flp) {
		super("upper", flp);
		this.jNotepadPP = jNotepadPP;
		this.flp = flp;
		putValue(Action.NAME, flp.getString("upper"));
		flp.getProvider().addLocalizationListener(() -> update());

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Util.convertCase(jNotepadPP, true);
	}


}
