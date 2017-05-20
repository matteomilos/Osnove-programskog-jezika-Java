package hr.fer.zemris.java.hw11.jnotepadpp.local.swing;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationProviderBridge;

/**
 * This class represents a {@link LocalizationProviderBridge} that connects
 * itself to the provided frame, typically main frame of your application and
 * registers itself to main localization provider of application.
 * 
 * @author Matteo Miloš
 *
 */
public class FormLocalizationProvider extends LocalizationProviderBridge {

	/**
	 * Creates a new form localization provider that is responsible for fetching
	 * and translating keys inside a provided frame.
	 * 
	 * @param provider
	 *            localization provider for frame
	 * @param frame
	 *            frame to be localized
	 */
	public FormLocalizationProvider(ILocalizationProvider provider, JFrame frame) {
		super(provider);
		frame.addWindowListener(
				new WindowAdapter() {

					@Override
					public void windowOpened(WindowEvent e) {
						connect();
					}

					@Override
					public void windowClosed(WindowEvent e) {
						disconnect();
					}
				}
		);
	}

}
