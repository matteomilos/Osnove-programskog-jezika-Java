package hr.fer.zemris.java.hw11.jnotepadpp.local.swing;

import javax.swing.JLabel;

import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

public class LJLabel extends JLabel {

	public LJLabel(String key, ILocalizationProvider provider) {
		update(key, provider);
		provider.addLocalizationListener(
				() -> {
					update(key, provider);
				}
		);
	}

	private void update(String key, ILocalizationProvider provider) {
		setText(provider.getString(key));
	}

}
