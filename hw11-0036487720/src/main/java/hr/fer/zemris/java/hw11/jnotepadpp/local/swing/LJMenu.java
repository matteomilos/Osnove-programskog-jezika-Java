package hr.fer.zemris.java.hw11.jnotepadpp.local.swing;

import javax.swing.JMenu;

import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

public class LJMenu extends JMenu {

	public LJMenu(String key, FormLocalizationProvider provider) {
		update(key, provider);
		provider.getProvider().addLocalizationListener(
				() -> {
					update(key, provider);
				}
		);
	}

	private void update(String key, ILocalizationProvider provider) {
		setText(provider.getString(key));
	}
}
