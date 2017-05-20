package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class AbstractLocalizationProvider is responsible only for storing and
 * notifying listeners of a provider.
 * 
 * @author Matteo Miloš
 * 
 */
public abstract class AbstractLocalizationProvider implements ILocalizationProvider {

	/** The listeners. */
	private List<ILocalizationListener> list;

	/**
	 * Instantiates a new abstract localization provider.
	 */
	public AbstractLocalizationProvider() {
		list = new ArrayList<>();
	}

	@Override
	public void addLocalizationListener(ILocalizationListener listener) {
		list.add(listener);
	}

	@Override
	public void removeLocalizationListener(ILocalizationListener listener) {
		list.remove(listener);
	}

	/**
	 * Notifies all listeners of a change in a provider.
	 */
	public void fire() {
		list.forEach(l -> l.localizationChanged());
	}

}
