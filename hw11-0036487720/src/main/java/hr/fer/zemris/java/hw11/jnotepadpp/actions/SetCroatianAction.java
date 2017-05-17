package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;

import javax.swing.Action;

import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.swing.FormLocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.swing.LocalizableAction;

public class SetCroatianAction extends LocalizableAction {

	public SetCroatianAction(FormLocalizationProvider flp) {
		super("hr", flp);
		putValue(Action.NAME, flp.getString("hr"));
		flp.getProvider().addLocalizationListener(() -> update());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		LocalizationProvider.getInstance().setLanguage("hr");
		update();

		System.out.println(LocalizationProvider.getInstance().getLanguage());
	}
}
