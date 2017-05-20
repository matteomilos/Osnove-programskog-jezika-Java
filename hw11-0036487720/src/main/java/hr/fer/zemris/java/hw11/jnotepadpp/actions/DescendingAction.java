package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import static hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationConstants.ACTION;
import static hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationConstants.DESCENDING;

import java.awt.event.ActionEvent;
import java.text.Collator;
import java.util.Locale;

import javax.swing.Action;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.local.swing.FormLocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.swing.LocalizableAction;

/**
 * Action derived from {@link LocalizableAction} class, used to sort selected
 * text in descending order.
 * 
 * @author Matteo Miloš
 *
 */
public class DescendingAction extends LocalizableAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * instance of {@link JNotepadPP} class
	 */
	private JNotepadPP jNotepadPP;

	/**
	 * instance of {@link Collator}, used for correct comparing of String
	 * values.
	 */
	private static Collator hrCollator = Collator.getInstance(new Locale("hr"));

	/**
	 * Constructor that creates new instance of {@link DescendingAction}
	 * 
	 * @param jNotepadPP
	 *            instance of {@link JNotepadPP} class
	 * @param flp
	 *            localization provider
	 */
	public DescendingAction(JNotepadPP jNotepadPP, FormLocalizationProvider flp) {
		super(DESCENDING, flp);
		this.jNotepadPP = jNotepadPP;
		putValue(Action.NAME, flp.getString(DESCENDING));
		putValue(Action.SHORT_DESCRIPTION, flp.getString(DESCENDING + ACTION));
		flp.getProvider().addLocalizationListener(() -> update());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Util.sortSelectedText(false, jNotepadPP, hrCollator);
	}

}
