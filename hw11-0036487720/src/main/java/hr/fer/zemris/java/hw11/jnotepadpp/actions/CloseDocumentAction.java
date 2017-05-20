package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import static hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationConstants.ACTION;
import static hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationConstants.CLOSE;
import static hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationConstants.SAVE;
import static hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationConstants.SAVE_FILE;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.Tab;
import hr.fer.zemris.java.hw11.jnotepadpp.local.swing.FormLocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.swing.LocalizableAction;

/**
 * Action derived from {@link LocalizableAction} class, used to close current
 * tab in our frame.
 * 
 * @author Matteo Miloš
 *
 */
public class CloseDocumentAction extends LocalizableAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * instance of {@link JNotepadPP} class
	 */
	private JNotepadPP jNotepadPP;

	/**
	 * localization provider
	 */
	private FormLocalizationProvider flp;

	/**
	 * Constructor that creates new instance of {@link CloseDocumentAction}
	 * 
	 * @param jNotepadPP
	 *            instance of {@link JNotepadPP} class
	 * @param flp
	 *            localization provider
	 */
	public CloseDocumentAction(JNotepadPP jNotepadPP, FormLocalizationProvider flp) {
		super(CLOSE, flp);
		this.jNotepadPP = jNotepadPP;
		this.flp = flp;
		putValue(Action.NAME, flp.getString(CLOSE));
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control W"));
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_W);
		putValue(Action.SHORT_DESCRIPTION, flp.getString(CLOSE + ACTION));
		flp.getProvider().addLocalizationListener(() -> update());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int closingIndex = jNotepadPP.getTabbedPane().getSelectedIndex();

		if (closingIndex >= 0) {
			JScrollPane scrollPane = (JScrollPane) jNotepadPP.getTabbedPane().getComponentAt(closingIndex);
			Tab closingTab = (Tab) scrollPane.getViewport().getView();

			if (closingTab.isChanged()) {

				String message = String.format(flp.getString(SAVE_FILE), closingTab.getSimpleName());
				int result = JOptionPane
						.showConfirmDialog(jNotepadPP, message, flp.getString(SAVE), JOptionPane.YES_NO_CANCEL_OPTION);

				switch (result) {
				case JOptionPane.YES_OPTION:
					jNotepadPP.getSaveDocumentAction().actionPerformed(e);

				case JOptionPane.NO_OPTION:
					jNotepadPP.getTabbedPane().removeTabAt(closingIndex);
					jNotepadPP.setnTabs(jNotepadPP.getnTabs() - 1);

				case JOptionPane.CANCEL_OPTION:
					return;
				}

			} else {
				jNotepadPP.getTabbedPane().removeTabAt(closingIndex);
				jNotepadPP.setnTabs(jNotepadPP.getnTabs() - 1);
			}
		}
	}

}
