package hr.fer.zemris.java.hw11.jnotepadpp.local.swing;

import javax.swing.JButton;

import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

public class LJButton extends JButton {

	public LJButton(String key, ILocalizationProvider provider) {
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
