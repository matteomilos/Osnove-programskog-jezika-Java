package hr.fer.zemris.java.hw11.jnotepadpp.local.swing;

import java.awt.event.ActionEvent;
import static hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationConstants.ACTION;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;

import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

/**
 * This class is a localized wrapper for {@link AbstractAction} class, so each
 * time language change occurs action name is updated properly.
 * 
 * @author Matteo Miloš
 *
 */
public abstract class LocalizableAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** key of the component */
	private String key;

	/** localization provider */
	private ILocalizationProvider lp;

	/**
	 * Creates a new localizable action with name under the key and and
	 * localization provider that will fetch the value under the key.
	 * 
	 * @param key
	 *            localization key
	 * @param lp
	 *            localization provider
	 */
	public LocalizableAction(String key, ILocalizationProvider lp) {
		this.lp = lp;
		this.key = key;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	}

	/**
	 * Method used for updating name and description of {@link JComponent} item.
	 * 
	 */
	protected void update() {
		putValue(Action.NAME, lp.getString(key));
		putValue(Action.SHORT_DESCRIPTION, lp.getString(key + ACTION));
	}

}
