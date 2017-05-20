package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import static hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationConstants.ACTION;
import static hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationConstants.ERROR;
import static hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationConstants.LOAD_ERROR;
import static hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationConstants.NOT_READABLE;
import static hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationConstants.OPEN;
import static hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationConstants.OPEN_FILE;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.Tab;
import hr.fer.zemris.java.hw11.jnotepadpp.local.swing.FormLocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.swing.LocalizableAction;

/**
 * Action derived from {@link LocalizableAction} class, used to open existing
 * document in our frame.
 * 
 * @author Matteo Miloš
 *
 */
public class OpenDocumentAction extends LocalizableAction {

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
	 * Constructor that creates new instance of {@link OpenDocumentAction}
	 * 
	 * @param jNotepadPP
	 *            instance of {@link JNotepadPP} class
	 * @param flp
	 *            localization provider
	 */
	public OpenDocumentAction(JNotepadPP jNotepadPP, FormLocalizationProvider flp) {
		super(OPEN, flp);
		this.flp = flp;
		this.jNotepadPP = jNotepadPP;

		putValue(Action.NAME, flp.getString(OPEN));
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);
		putValue(Action.SHORT_DESCRIPTION, flp.getString(OPEN + ACTION));

		flp.getProvider().addLocalizationListener(() -> update());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle(flp.getString(OPEN_FILE));

		if (fc.showOpenDialog(jNotepadPP) != JFileChooser.APPROVE_OPTION) {
			return;
		}

		JTabbedPane tabbedPane = jNotepadPP.getTabbedPane();
		for (Component component : tabbedPane.getComponents()) { /*-checks if file is already opened, 
																	if it is, then selected tab becomes wanted file*/
			JScrollPane scrollPane = (JScrollPane) component;
			Tab newTab = (Tab) scrollPane.getViewport().getView();
			if (newTab.getOpenedFilePath() != null
					&& newTab.getOpenedFilePath().equals(fc.getSelectedFile().toPath())) {
				jNotepadPP.getTabbedPane().setSelectedComponent(scrollPane);
				return;
			}

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

		jNotepadPP.addNewTab(filePath, text);

	}
}
