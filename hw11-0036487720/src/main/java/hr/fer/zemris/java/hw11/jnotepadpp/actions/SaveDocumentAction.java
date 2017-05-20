package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import static hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationConstants.ACTION;
import static hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationConstants.SAVE;
import static hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationConstants.SAVE_FILE_DIALOG;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.Tab;
import hr.fer.zemris.java.hw11.jnotepadpp.local.swing.FormLocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.swing.LocalizableAction;

/**
 * Action derived from {@link LocalizableAction} class, used to save our
 * document as an existing document.
 * 
 * @author Matteo Miloš
 *
 */
public class SaveDocumentAction extends LocalizableAction {

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
	 * Constructor that creates new instance of {@link SaveDocumentAction}
	 * 
	 * @param jNotepadPP
	 *            instance of {@link JNotepadPP} class
	 * @param flp
	 *            localization provider
	 */
	public SaveDocumentAction(JNotepadPP jNotepadPP, FormLocalizationProvider flp) {
		super(SAVE, flp);
		this.flp = flp;
		this.jNotepadPP = jNotepadPP;

		putValue(Action.NAME, flp.getString(SAVE));
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
		putValue(Action.SHORT_DESCRIPTION, flp.getString(SAVE + ACTION));

		flp.getProvider().addLocalizationListener(() -> update());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		saveDocument(-1);

	}

	/**
	 * Method used for saving document on the specified index of the tabbed
	 * pane. If index is -1, then selected component is saved.
	 * 
	 * @param index
	 *            index of the tab to save
	 */
	private void saveDocument(int index) {
		JScrollPane scrollPane = (index == -1) ? (JScrollPane) jNotepadPP.getTabbedPane().getSelectedComponent()
				: (JScrollPane) jNotepadPP.getTabbedPane().getComponentAt(index);

		if (scrollPane == null) {
			return;
		}

		Tab closingTab = (Tab) scrollPane.getViewport().getView();

		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle(flp.getString(SAVE_FILE_DIALOG));

		if (closingTab.getOpenedFilePath() == null) {
			((SaveAsDocumentAction) jNotepadPP.getSaveAsDocumentAction()).saveDocument(index);
			return;
		}

		Util.executeSaving(closingTab, fc, jNotepadPP, flp, scrollPane);
	}
}
