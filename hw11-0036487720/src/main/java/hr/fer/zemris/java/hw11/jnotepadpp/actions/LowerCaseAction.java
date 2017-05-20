package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import static hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationConstants.ACTION;
import static hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationConstants.LOWER;

import java.awt.event.ActionEvent;

import javax.swing.Action;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.local.swing.FormLocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.swing.LocalizableAction;

/**
 * Action derived from {@link LocalizableAction} class, used to set selected
 * characters to the lower case.
 * 
 * @author Matteo Miloš
 *
 */
public class LowerCaseAction extends LocalizableAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * instance of {@link JNotepadPP} class
	 */
	private JNotepadPP jNotepadPP;

	/**
	 * Constructor that creates new instance of {@link LowerCaseAction}
	 * 
	 * @param jNotepadPP
	 *            instance of {@link JNotepadPP} class
	 * @param flp
	 *            localization provider
	 */
	public LowerCaseAction(JNotepadPP jNotepadPP, FormLocalizationProvider flp) {
		super(LOWER, flp);
		this.jNotepadPP = jNotepadPP;
		putValue(Action.NAME, flp.getString(LOWER));
		putValue(Action.SHORT_DESCRIPTION, flp.getString(LOWER + ACTION));
		flp.getProvider().addLocalizationListener(() -> update());
		setEnabled(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Util.convertCase(jNotepadPP, false);
	}
}
