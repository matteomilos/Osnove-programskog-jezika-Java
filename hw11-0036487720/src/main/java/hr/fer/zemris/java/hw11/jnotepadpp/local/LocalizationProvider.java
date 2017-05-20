package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * In computing, internationalization and localization are means of adapting
 * computer software to different languages, regional differences and technical
 * requirements of a target market (locale).[1] Internationalization is the
 * process of designing a software application so that it can potentially be
 * adapted to various languages and regions without engineering changes.
 * Localization is the process of adapting internationalized software for a
 * specific region or language by adding locale-specific components and
 * translating text. Localization (which is potentially performed multiple
 * times, for different locales) uses the infrastructure or flexibility provided
 * by internationalization (which is ideally performed only once, or as an
 * integral part of ongoing development).
 * <p>
 * This class is a core of that.
 * 
 * @author Matteo Miloš
 *
 */
public class LocalizationProvider extends AbstractLocalizationProvider {

	/** Single instance of Localization provider */
	private static final LocalizationProvider instance = new LocalizationProvider();

	/** Language resource bundle */
	private ResourceBundle bundle;

	/**
	 * Creates a new singleton localization provider, with default language
	 * English.
	 */
	private LocalizationProvider() {
	}

	/**
	 * Gets a singleton instance of LocalizationProvider
	 * 
	 * @return language localization provider
	 */
	public static LocalizationProvider getInstance() {
		return instance;
	}

	/**
	 * Changes the localization language.
	 * <p>
	 * Currently supported languages are only "en" and "hr" so these are only
	 * valid parameters.
	 * 
	 * @param language
	 *            language tag
	 */
	public void setLanguage(String language) {
		bundle = ResourceBundle
				.getBundle("hr.fer.zemris.java.hw11.jnotepadpp.local.lang", Locale.forLanguageTag(language));
		fire();
	}

	@Override
	public String getString(String key) {
		return bundle.getString(key);
	}


}
