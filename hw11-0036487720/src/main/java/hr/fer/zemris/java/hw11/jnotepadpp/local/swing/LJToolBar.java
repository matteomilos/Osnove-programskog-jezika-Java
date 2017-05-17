package hr.fer.zemris.java.hw11.jnotepadpp.local.swing;

import javax.swing.JMenu;
import javax.swing.JToolBar;

import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

public class LJToolBar extends JToolBar {

	public LJToolBar(String key, ILocalizationProvider provider) {
		update(key, provider);
		provider.addLocalizationListener(
				() -> {
					update(key, provider);
				}
		);
	}

	private void update(String key, ILocalizationProvider provider) {
		setName(provider.getString(key));
	}
}