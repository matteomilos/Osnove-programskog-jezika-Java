package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import static hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationConstants.ACTION;
import static hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationConstants.EN;

import java.awt.event.ActionEvent;

import javax.swing.Action;

import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.swing.FormLocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.swing.LocalizableAction;

/**
 * Action derived from {@link LocalizableAction} class, used to change current
 * language to the English language.
 * 
 * @author Matteo Miloš
 *
 */
public class SetEnglishAction extends LocalizableAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor that creates new instance of {@link SetEnglishAction}
	 * 
	 * @param flp
	 *            localization provider
	 */
	public SetEnglishAction(FormLocalizationProvider flp) {
		super(EN, flp);
		putValue(Action.NAME, flp.getString(EN));
		putValue(Action.SHORT_DESCRIPTION, flp.getString(EN + ACTION));
		flp.getProvider().addLocalizationListener(() -> update());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		LocalizationProvider.getInstance().setLanguage(EN);
		update();
	}
}
