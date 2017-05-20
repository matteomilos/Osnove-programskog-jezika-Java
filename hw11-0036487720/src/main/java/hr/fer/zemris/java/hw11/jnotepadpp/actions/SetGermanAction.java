package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import static hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationConstants.*;

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
public class SetGermanAction extends LocalizableAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor that creates new instance of {@link SetGermanAction}
	 * 
	 * @param flp
	 *            localization provider
	 */
	public SetGermanAction(FormLocalizationProvider flp) {
		super(DE, flp);
		putValue(Action.NAME, flp.getString(DE));
		putValue(Action.SHORT_DESCRIPTION, flp.getString(DE + ACTION));
		flp.getProvider().addLocalizationListener(() -> update());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		LocalizationProvider.getInstance().setLanguage(DE);
		update();

	}
}
