package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import static hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationConstants.*;
import static hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationConstants.EXISTS;
import static hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationConstants.INFORMATION;
import static hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationConstants.NOT_SAVED;
import static hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationConstants.SAVE_AS;
import static hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationConstants.SAVING;

import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.nio.file.Files;

import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.Tab;
import hr.fer.zemris.java.hw11.jnotepadpp.local.swing.FormLocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.swing.LocalizableAction;

/**
 * Action derived from {@link LocalizableAction} class, used to save our
 * document as a new document.
 * 
 * @author Matteo Miloš
 *
 */
public class SaveAsDocumentAction extends LocalizableAction {

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
	 * Constructor that creates new instance of {@link SaveAsDocumentAction}
	 * 
	 * @param jNotepadPP
	 *            instance of {@link JNotepadPP} class
	 * @param flp
	 *            localization provider
	 */
	public SaveAsDocumentAction(JNotepadPP jNotepadPP, FormLocalizationProvider flp) {
		super(SAVE_AS, flp);
		this.flp = flp;

		putValue(Action.NAME, flp.getString(SAVE_AS));
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control alt S"));
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S | InputEvent.ALT_DOWN_MASK);
		putValue(Action.SHORT_DESCRIPTION, flp.getString(SAVE_AS + ACTION));

		flp.getProvider().addLocalizationListener(() -> update());
		this.jNotepadPP = jNotepadPP;
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
	public void saveDocument(int index) {
		JScrollPane scrollPane = index == -1 ? (JScrollPane) jNotepadPP.getTabbedPane().getSelectedComponent()
				: (JScrollPane) jNotepadPP.getTabbedPane().getComponentAt(index);

		if (scrollPane == null) {
			return;
		}
		Tab closingTab = (Tab) scrollPane.getViewport().getView();

		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle(flp.getString(SAVE_FILE_DIALOG));

		boolean isFirst = true;
		LOOP: do {

			if (!isFirst) {
				int result = JOptionPane.showConfirmDialog(
						jNotepadPP,
						flp.getString(EXISTS),
						flp.getString(SAVING),
						JOptionPane.YES_NO_CANCEL_OPTION
				);

				switch (result) {
				case JOptionPane.YES_OPTION:
					break LOOP;

				case JOptionPane.NO_OPTION:
					break;

				case JOptionPane.CANCEL_OPTION:
					return;
				}
			}

			isFirst = false;

			if (fc.showSaveDialog(jNotepadPP) != JFileChooser.APPROVE_OPTION) {
				JOptionPane.showMessageDialog(
						jNotepadPP,
						flp.getString(NOT_SAVED),
						flp.getString(INFORMATION),
						JOptionPane.INFORMATION_MESSAGE
				);
				return;
			}

		} while (Files.exists(fc.getSelectedFile().toPath()));

		Util.executeSaving(closingTab, fc, jNotepadPP, flp, scrollPane);
	}

}
