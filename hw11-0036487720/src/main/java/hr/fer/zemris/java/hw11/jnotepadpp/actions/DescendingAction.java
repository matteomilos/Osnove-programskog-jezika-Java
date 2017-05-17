package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.text.Collator;
import java.util.Locale;

import javax.swing.Action;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.local.swing.FormLocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.swing.LocalizableAction;

public class DescendingAction extends LocalizableAction {

	private JNotepadPP jNotepadPP;

	private FormLocalizationProvider flp;

	private Collator hrCollator = Collator.getInstance(new Locale("hr"));

	public DescendingAction(JNotepadPP jNotepadPP, FormLocalizationProvider flp) {
		super("descending", flp);
		this.flp = flp;
		this.jNotepadPP = jNotepadPP;
		putValue(Action.NAME, flp.getString("descending"));
		flp.getProvider().addLocalizationListener(() -> update());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Util.sortSelectedText(false, jNotepadPP, hrCollator);
	}

}
