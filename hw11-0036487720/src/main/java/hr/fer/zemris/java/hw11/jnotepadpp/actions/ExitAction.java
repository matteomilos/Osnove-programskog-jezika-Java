package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import static hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationConstants.ACTION;
import static hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationConstants.EXIT;

import java.awt.event.ActionEvent;

import javax.swing.Action;
import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.local.swing.FormLocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.swing.LocalizableAction;

/**
 * Action derived from {@link LocalizableAction} class, used to exit
 * application.
 * 
 * @author Matteo Miloš
 *
 */
public class ExitAction extends LocalizableAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * instance of {@link JNotepadPP} class
	 */
	private JNotepadPP jNotepadPP;

	/**
	 * Constructor that creates new instance of {@link ExitAction}
	 * 
	 * @param jNotepadPP
	 *            instance of {@link JNotepadPP} class
	 * @param flp
	 *            localization provider
	 */
	public ExitAction(JNotepadPP jNotepadPP, FormLocalizationProvider flp) {
		super(EXIT, flp);
		this.jNotepadPP = jNotepadPP;
		putValue(Action.NAME, flp.getString(EXIT));
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("alt f4"));
		putValue(Action.SHORT_DESCRIPTION, flp.getString(EXIT + ACTION));
		flp.getProvider().addLocalizationListener(() -> update());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		jNotepadPP.closingWindow();
	}

}
