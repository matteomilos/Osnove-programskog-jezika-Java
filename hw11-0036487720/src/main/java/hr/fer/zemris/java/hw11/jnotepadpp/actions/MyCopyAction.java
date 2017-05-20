package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import static hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationConstants.ACTION;
import static hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationConstants.COPY;

import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.KeyStroke;
import javax.swing.text.DefaultEditorKit.CopyAction;

import hr.fer.zemris.java.hw11.jnotepadpp.local.swing.FormLocalizationProvider;

/**
 * Action derived from {@link CopyAction} class, used to copy selected text.
 * 
 * @author Matteo Miloš
 *
 */
public class MyCopyAction extends CopyAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * localization provider
	 */
	private FormLocalizationProvider flp;

	/**
	 * Constructor that creates new instance of {@link MyCopyAction}
	 * 
	 * @param flp
	 *            localization provider
	 */
	public MyCopyAction(FormLocalizationProvider flp) {
		this.flp = flp;
		putValue(Action.NAME, flp.getString(COPY));
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control C"));
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);
		putValue(Action.SHORT_DESCRIPTION, flp.getString(COPY+ACTION));
		flp.getProvider().addLocalizationListener(() -> update());
	}

	/**
	 * Method used for updating name of the components based on the current
	 * language.
	 */
	private void update() {
		putValue(Action.NAME, flp.getString(COPY));
		putValue(Action.SHORT_DESCRIPTION, flp.getString(COPY + ACTION));
	}
}
