package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.text.Collator;
import java.util.Locale;

import javax.swing.Action;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.local.swing.FormLocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.swing.LocalizableAction;

public class AscendingAction extends LocalizableAction {

	private static JNotepadPP jNotepadPP;

	private static FormLocalizationProvider flp;

	private static Collator hrCollator = Collator.getInstance(new Locale("hr"));

	public AscendingAction(JNotepadPP jNotepadPP, FormLocalizationProvider flp) {
		super("ascending", flp);
		this.flp = flp;
		this.jNotepadPP = jNotepadPP;
		putValue(Action.NAME, flp.getString("ascending"));
		flp.getProvider().addLocalizationListener(() -> update());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Util.sortSelectedText(true, jNotepadPP, hrCollator);

	}

}
