package hr.fer.zemris.java.hw11.jnotepadpp.local;

public class LocalizationProviderBridge extends AbstractLocalizationProvider {

	private boolean connected;

	private ILocalizationProvider provider;

	public LocalizationProviderBridge(ILocalizationProvider provider) {
		this.provider = provider;
	}

	public void disconnect() {
		if (connected) {
			connected = false;
			provider.addLocalizationListener(() -> fire());
		}
	}

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

	public ILocalizationProvider getProvider() {
		return provider;
	}

	@Override
	public void localizationChanged() {
	}
}
