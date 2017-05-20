package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import static hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationConstants.ACTION;
import static hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationConstants.PASTE;

import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.KeyStroke;
import javax.swing.text.DefaultEditorKit.PasteAction;

import hr.fer.zemris.java.hw11.jnotepadpp.local.swing.FormLocalizationProvider;

/**
 * Action derived from {@link PasteAction} class, used to paste selected text.
 * 
 * @author Matteo Miloš
 *
 */
public class MyPasteAction extends PasteAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * localization provider
	 */
	private FormLocalizationProvider flp;

	/**
	 * Constructor that creates new instance of {@link MyPasteAction}
	 * 
	 * @param flp
	 *            localization provider
	 */
	public MyPasteAction(FormLocalizationProvider flp) {
		this.flp = flp;
		putValue(Action.NAME, flp.getString(PASTE));
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control V"));
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_P);
		putValue(Action.SHORT_DESCRIPTION, flp.getString(PASTE + ACTION));
		flp.getProvider().addLocalizationListener(() -> update());
	}

	/**
	 * Method used for updating name of the components based on the current
	 * language.
	 */
	private void update() {
		putValue(Action.NAME, flp.getString(PASTE));
		putValue(Action.SHORT_DESCRIPTION, flp.getString(PASTE + ACTION));

	}
}
