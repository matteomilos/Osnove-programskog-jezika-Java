package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * The listener interface for receiving Localization events. The class that is
 * interested in processing a ILocalization event implements this interface, and
 * the object created with that class is registered with a component using the
 * component's <code>addLocalizationListener<code> method. When the Localization
 * event occurs, that object's appropriate method is invoked.
 *
 * @see ILocalizationProvider
 * @author Matteo Miloš
 */
public interface ILocalizationListener {

	/**
	 * Notifies the listener that the localization has changed.
	 */
	void localizationChanged();

}
