package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.Tab;
import hr.fer.zemris.java.hw11.jnotepadpp.local.swing.FormLocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.swing.LocalizableAction;

public class SaveDocumentAction extends LocalizableAction {

	private JNotepadPP jNotepadPP;

	public SaveDocumentAction(JNotepadPP jNotepadPP, FormLocalizationProvider flp) {
		super("save", flp);
		this.jNotepadPP = jNotepadPP;
		putValue(Action.NAME, flp.getString("save"));
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
		putValue(Action.SHORT_DESCRIPTION, "Used to save document from disc");
		flp.getProvider().addLocalizationListener(() -> update());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		saveDocument(-1);

	}

	public void saveDocument(int i) {
		JScrollPane scrollPane = i == -1 ? (JScrollPane) jNotepadPP.getTabbedPane().getSelectedComponent()
				: (JScrollPane) jNotepadPP.getTabbedPane().getComponentAt(i);
		if (scrollPane == null) {
			JOptionPane.showMessageDialog(
					jNotepadPP,
					"No tab is currently opened!",
					"Save",
					JOptionPane.INFORMATION_MESSAGE
			);
			return;
		}
		Tab closingTab = (Tab) scrollPane.getViewport().getView();

		if (closingTab.getOpenedFilePath() == null) {
			JFileChooser fc = new JFileChooser();
			fc.setDialogTitle("Save file");

			if (fc.showSaveDialog(jNotepadPP) != JFileChooser.APPROVE_OPTION) {
				JOptionPane.showMessageDialog(
						jNotepadPP,
						"File is not saved",
						"Information",
						JOptionPane.INFORMATION_MESSAGE
				);
				return;
			}
			closingTab.setOpenedFilePath(fc.getSelectedFile().toPath());
		}

		try {
			Files.write(closingTab.getOpenedFilePath(), closingTab.getText().getBytes(StandardCharsets.UTF_8));
		} catch (Exception exc) {
			JOptionPane.showMessageDialog(jNotepadPP, "Saving unsuccessful", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		closingTab.setOldText(closingTab.getText());

		JOptionPane.showMessageDialog(jNotepadPP, "File is saved!", "Information", JOptionPane.INFORMATION_MESSAGE);

		jNotepadPP.getTabbedPane()
				.setTitleAt(jNotepadPP.getTabbedPane().getSelectedIndex(), closingTab.getSimpleName());
		jNotepadPP.getTabbedPane()
				.setToolTipTextAt(jNotepadPP.getTabbedPane().getSelectedIndex(), closingTab.getLongName());
	}
}
