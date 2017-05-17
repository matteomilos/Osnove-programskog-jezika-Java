package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractLocalizationProvider implements ILocalizationProvider, ILocalizationListener {

	private List<ILocalizationListener> list;

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

	public void fire() {

		list.forEach(l -> l.localizationChanged());
	}

}
