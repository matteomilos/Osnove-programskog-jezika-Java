package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.local.swing.FormLocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.swing.LocalizableAction;
import static hr.fer.zemris.java.hw11.jnotepadpp.actions.ActionConstants.*;

public class OpenDocumentAction extends LocalizableAction {

	private JNotepadPP jNotepadPP;

	private FormLocalizationProvider flp;

	public OpenDocumentAction(JNotepadPP jNotepadPP, FormLocalizationProvider flp) {
		super("open", flp);
		this.flp = flp;
		this.jNotepadPP = jNotepadPP;
		putValue(Action.NAME, flp.getString("open"));
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);
		putValue(Action.SHORT_DESCRIPTION, "Used to open document from disc");
		flp.getProvider().addLocalizationListener(() -> update());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		update();
		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle(flp.getString(OPEN_FILE));
		if (fc.showOpenDialog(jNotepadPP) != JFileChooser.APPROVE_OPTION) {
			return;
		}

		File fileName = fc.getSelectedFile();
		Path filePath = fileName.toPath();

		if (!Files.isReadable(filePath)) {
			String message = String.format(flp.getString(NOT_READABLE), filePath);
			JOptionPane.showMessageDialog(jNotepadPP, message, flp.getString(ERROR), JOptionPane.ERROR_MESSAGE);
			return;
		}

		byte[] data = null;
		try {
			data = Files.readAllBytes(filePath);
		} catch (IOException exc) {
			String message = String.format(flp.getString(LOAD_ERROR), filePath);
			JOptionPane.showMessageDialog(jNotepadPP, message, flp.getString(ERROR), JOptionPane.ERROR_MESSAGE);
			return;
		}

		String text = new String(data, StandardCharsets.UTF_8);

		try {
			jNotepadPP.addNewTab(filePath, text);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}
}
