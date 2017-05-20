package hr.fer.zemris.java.hw11.jnotepadpp.local.swing;

import javax.swing.JToolBar;

import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

/**
 * This class is a localized wrapper for {@link JToolBar} class, so each time
 * language change occurs toolbar name is updated properly.
 * 
 * @author Matteo Miloš
 *
 */
public class LJToolBar extends JToolBar {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a localizable {@link JToolBar}.
	 * 
	 * @param key
	 *            key that holds localizable text
	 * @param provider
	 *            provider for languages
	 */
	public LJToolBar(String key, ILocalizationProvider provider) {
		update(key, provider);
		provider.addLocalizationListener(() -> update(key, provider));
	}

	/**
	 * Method used for updating name of toolbar component.
	 * 
	 * @param key
	 *            toolbar key
	 * @param provider
	 *            localization provider
	 */
	private void update(String key, ILocalizationProvider provider) {
		setName(provider.getString(key));
	}
}