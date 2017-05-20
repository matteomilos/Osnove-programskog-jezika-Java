package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import static hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationConstants.ACTION;
import static hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationConstants.CUT;

import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.KeyStroke;
import javax.swing.text.DefaultEditorKit.CutAction;

import hr.fer.zemris.java.hw11.jnotepadpp.local.swing.FormLocalizationProvider;

/**
 * Action derived from {@link CutAction} class, used to cut selected text.
 * 
 * @author Matteo Miloš
 *
 */
public class MyCutAction extends CutAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * localization provider
	 */
	private FormLocalizationProvider flp;

	/**
	 * Constructor that creates new instance of {@link MyCutAction}
	 * 
	 * @param flp
	 *            localization provider
	 */
	public MyCutAction(FormLocalizationProvider flp) {
		this.flp = flp;
		putValue(Action.NAME, flp.getString(CUT));
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control X"));
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_U);
		putValue(Action.SHORT_DESCRIPTION,flp.getString(CUT+ACTION));
		flp.getProvider().addLocalizationListener(() -> update());
	}

	/**
	 * Method used for updating name of the components based on the current
	 * language.
	 */
	private void update() {
		putValue(Action.NAME, flp.getString(CUT));
		putValue(Action.SHORT_DESCRIPTION, flp.getString(CUT + ACTION));

	}
}
