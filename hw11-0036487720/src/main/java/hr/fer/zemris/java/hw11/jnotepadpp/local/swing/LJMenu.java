package hr.fer.zemris.java.hw11.jnotepadpp.local.swing;

import javax.swing.JMenu;

import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

/**
 * This class is a localized wrapper for {@link JMenu} class, so each time
 * language change occurs text in menu is updated properly.
 * 
 * @author Matteo Miloš
 *
 */
public class LJMenu extends JMenu {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a localizable {@link JMenu}
	 * 
	 * @param key
	 *            key for localizable menu name
	 * @param provider
	 *            localization provider
	 */
	public LJMenu(String key, FormLocalizationProvider provider) {
		update(key, provider);
		provider.getProvider().addLocalizationListener(() -> update(key, provider));
	}

	/**
	 * Method used for updating name of menu component.
	 * 
	 * @param key
	 *            menu key
	 * @param provider
	 *            localization provider
	 */
	private void update(String key, ILocalizationProvider provider) {
		setText(provider.getString(key));
	}
}
