package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import static hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationConstants.*;

import java.awt.event.ActionEvent;

import javax.swing.Action;

import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.swing.FormLocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.swing.LocalizableAction;

/**
 * Action derived from {@link LocalizableAction} class, used to change current
 * language to the Croatian language.
 * 
 * @author Matteo Miloš
 *
 */
public class SetCroatianAction extends LocalizableAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor that creates new instance of {@link SetCroatianAction}
	 * 
	 * @param flp
	 *            localization provider
	 */
	public SetCroatianAction(FormLocalizationProvider flp) {
		super(HR, flp);
		putValue(Action.NAME, flp.getString(HR));
		putValue(Action.SHORT_DESCRIPTION, flp.getString(HR + ACTION));
		flp.getProvider().addLocalizationListener(() -> update());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		LocalizationProvider.getInstance().setLanguage(HR);
		update();

	}
}
