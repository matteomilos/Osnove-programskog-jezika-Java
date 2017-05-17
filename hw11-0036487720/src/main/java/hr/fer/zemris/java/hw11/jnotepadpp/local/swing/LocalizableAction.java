package hr.fer.zemris.java.hw11.jnotepadpp.local.swing;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationListener;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

public abstract class LocalizableAction extends AbstractAction  {

	private long serialVersionUID = 1L;

	private String key;

	ILocalizationProvider lp;

	public LocalizableAction(String key, ILocalizationProvider lp) {
		this.lp = lp;
		this.key = key;
		update();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		update();
	}

	protected void update() {
		putValue(Action.NAME, lp.getString(key));
	}

}
