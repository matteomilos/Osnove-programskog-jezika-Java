package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import static hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationConstants.ACTION;
import static hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationConstants.ASCENDING;

import java.awt.event.ActionEvent;
import java.text.Collator;
import java.util.Locale;

import javax.swing.Action;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.local.swing.FormLocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.swing.LocalizableAction;

/**
 * Action derived from {@link LocalizableAction} class, used to sort selected
 * text in ascending order.
 * 
 * @author Matteo Miloš
 *
 */
public class AscendingAction extends LocalizableAction {

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
	 * Constructor that creates new instance of {@link AscendingAction}
	 * 
	 * @param jNotepadPP
	 *            instance of {@link JNotepadPP} class
	 * @param flp
	 *            localization provider
	 */
	public AscendingAction(JNotepadPP jNotepadPP, FormLocalizationProvider flp) {
		super(ASCENDING, flp);
		this.jNotepadPP = jNotepadPP;
		putValue(Action.NAME, flp.getString(ASCENDING));
		putValue(Action.SHORT_DESCRIPTION, flp.getString(ASCENDING + ACTION));
		flp.getProvider().addLocalizationListener(() -> update());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Util.sortSelectedText(true, jNotepadPP, hrCollator);
	}

}
