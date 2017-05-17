package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.nio.charset.StandardCharsets;
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

public class SaveAsDocumentAction extends LocalizableAction {

	private JNotepadPP jNotepadPP;

	private FormLocalizationProvider flp;

	private static final String NO_TAB_OPENED = "notab";

	private static final String SAVE_AS = "saveas";

	private static final String EXISTS = "exists";

	private static final String SAVING = "saving";

	private static final String NOT_SAVED = "notsaved";

	private static final String INFORMATION = "info";

	private static final String SAVE_NOT_SUCCESSFUL = "saveunsuccess";

	private static final String SAVED = "saved";

	private static final String ERROR = "error";

	public SaveAsDocumentAction(JNotepadPP jNotepadPP, FormLocalizationProvider flp) {
		super("saveas", flp);
		this.flp = flp;
		putValue(Action.NAME, flp.getString("saveas"));
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control alt S"));
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S | InputEvent.ALT_DOWN_MASK);
		putValue(Action.SHORT_DESCRIPTION, "Used to save as new document from disc");
		flp.getProvider().addLocalizationListener(() -> update());
		this.jNotepadPP = jNotepadPP;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		saveDocument(-1);
	}

	public void saveDocument(int i) {
		JScrollPane scrollPane = i == -1 ? (JScrollPane) jNotepadPP.getTabbedPane().getSelectedComponent()
				: (JScrollPane) jNotepadPP.getTabbedPane().getComponentAt(i);
		if (scrollPane == null) {
			return;
		}
		Tab closingTab = (Tab) scrollPane.getViewport().getView();

		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle("Save file");

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

		closingTab.setOpenedFilePath(fc.getSelectedFile().toPath());
		try {
			Files.write(closingTab.getOpenedFilePath(), closingTab.getText().getBytes(StandardCharsets.UTF_8));
		} catch (

		Exception exc) {
			JOptionPane.showMessageDialog(
					jNotepadPP,
					flp.getString(SAVE_NOT_SUCCESSFUL),
					flp.getString(ERROR),
					JOptionPane.ERROR_MESSAGE
			);
			return;
		}

		JOptionPane.showMessageDialog(
				jNotepadPP,
				flp.getString(SAVED),
				flp.getString(INFORMATION),
				JOptionPane.INFORMATION_MESSAGE
		);

		jNotepadPP.getTabbedPane()
				.setTitleAt(jNotepadPP.getTabbedPane().getSelectedIndex(), closingTab.getSimpleName());
		jNotepadPP.getTabbedPane()
				.setToolTipTextAt(jNotepadPP.getTabbedPane().getSelectedIndex(), closingTab.getLongName());
	}

}
