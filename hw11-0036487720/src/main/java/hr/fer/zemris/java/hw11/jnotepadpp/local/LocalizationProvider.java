package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.Locale;
import java.util.ResourceBundle;

public class LocalizationProvider extends AbstractLocalizationProvider {

	private static final LocalizationProvider instance = new LocalizationProvider();

	private ResourceBundle bundle;

	private String language;

	private LocalizationProvider() {
		language = "en";
	}

	public static LocalizationProvider getInstance() {
		return instance;
	}

	public void setLanguage(String language) {
		// ResourceBundle.clearCache();
		this.language = language;
		bundle = ResourceBundle
				.getBundle("hr.fer.zemris.java.hw11.jnotepadpp.prijevodi", Locale.forLanguageTag(language));
		fire();
	}

	@Override
	public String getString(String key) {
		return bundle.getString(key);
	}

	public String getLanguage() {
		return language;
	}

	@Override
	public void localizationChanged() {
	}

}
