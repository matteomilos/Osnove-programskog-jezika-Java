package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import static hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationConstants.ACTION;
import static hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationConstants.UPPER;

import java.awt.event.ActionEvent;

import javax.swing.Action;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.local.swing.FormLocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.swing.LocalizableAction;

/**
 * Action derived from {@link LocalizableAction} class, used to set selected
 * characters to the upper case.
 * 
 * @author Matteo Miloš
 *
 */
public class UpperCaseAction extends LocalizableAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * instance of {@link JNotepadPP} class
	 */
	private JNotepadPP jNotepadPP;

	/**
	 * Constructor that creates new instance of {@link UpperCaseAction}
	 * 
	 * @param jNotepadPP
	 *            instance of {@link JNotepadPP} class
	 * @param flp
	 *            localization provider
	 */
	public UpperCaseAction(JNotepadPP jNotepadPP, FormLocalizationProvider flp) {
		super(UPPER, flp);
		this.jNotepadPP = jNotepadPP;
		putValue(Action.NAME, flp.getString(UPPER));
		putValue(Action.SHORT_DESCRIPTION, flp.getString(UPPER + ACTION));
		flp.getProvider().addLocalizationListener(() -> update());

		setEnabled(false);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Util.convertCase(jNotepadPP, true);
	}

}
