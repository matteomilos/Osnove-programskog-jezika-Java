package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * Localization provider bridge is a intermediate class between a localizable
 * application component and localization provider to isolate the localizable
 * elements of the application so when a language change occurs replacement
 * elements are created for appropriate language. This class creates an island
 * of components that can be collected by GARBAGE COLLECTOR.
 * 
 * @author Matteo Miloš
 *
 */
public class LocalizationProviderBridge extends AbstractLocalizationProvider {

	/** The connected flag. */
	private boolean connected;

	/** The parent component. */
	private ILocalizationProvider provider;

	/**
	 * Instantiates a new localization provider bridge.
	 *
	 * @param provider
	 *            the parent localization provider
	 */
	public LocalizationProviderBridge(ILocalizationProvider provider) {
		this.provider = provider;
	}

	/**
	 * Disconnects the component from localization.
	 */
	public void disconnect() {
		if (connected) {
			connected = false;
			provider.addLocalizationListener(() -> fire());
		}
	}

	/**
	 * Connects the component to localization.
	 */
	public void connect() {
		if (!connected) {
			connected = true;
			provider.removeLocalizationListener(() -> fire());
		}
	}

	@Override
	public String getString(String key) {
		return provider.getString(key);
	}

	/**
	 * Method that returns current provider
	 * 
	 * @return current provider
	 */
	public ILocalizationProvider getProvider() {
		return provider;
	}

}
