package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import static hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationConstants.ACTION;
import static hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationConstants.NEW;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.local.swing.FormLocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.swing.LocalizableAction;

/**
 * Action derived from {@link LocalizableAction} class, used to open new blank
 * document in our frame.
 * 
 * @author Matteo Miloš
 *
 */
public class NewDocumentAction extends LocalizableAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * instance of {@link JNotepadPP} class
	 */
	private JNotepadPP jNotepadPP;

	/**
	 * Constructor that creates new instance of {@link NewDocumentAction}
	 * 
	 * @param jNotepadPP
	 *            instance of {@link JNotepadPP} class
	 * @param flp
	 *            localization provider
	 */
	public NewDocumentAction(JNotepadPP jNotepadPP, FormLocalizationProvider flp) {
		super(NEW, flp);
		putValue(Action.NAME, flp.getString(NEW));
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control N"));
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_N);
		putValue(Action.SHORT_DESCRIPTION, flp.getString(NEW + ACTION));
		flp.getProvider().addLocalizationListener(() -> update());
		this.jNotepadPP = jNotepadPP;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		jNotepadPP.addNewTab(null, null);
		jNotepadPP.getTabbedPane().setSelectedIndex(jNotepadPP.getnTabs() - 1);
	}
}
