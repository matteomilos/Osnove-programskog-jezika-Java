package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import static hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationConstants.ACTION;
import static hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationConstants.CALCULATE;
import static hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationConstants.STAT_MESSAGE;

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
 * Action derived from {@link LocalizableAction} class, used to get statistical
 * info about opened document.
 * 
 * @author Matteo Miloš
 *
 */
public class StatisticsAction extends LocalizableAction {

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
	 * Constructor that creates new instance of {@link StatisticsAction}
	 * 
	 * @param jNotepadPP
	 *            instance of {@link JNotepadPP} class
	 * @param flp
	 *            localization provider
	 */
	public StatisticsAction(JNotepadPP jNotepadPP, FormLocalizationProvider flp) {
		super(CALCULATE, flp);
		this.flp = flp;

		putValue(Action.NAME, flp.getString(CALCULATE));
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control P"));
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_P);
		putValue(Action.SHORT_DESCRIPTION, flp.getString(CALCULATE+ACTION));

		flp.getProvider().addLocalizationListener(() -> update());
		this.jNotepadPP = jNotepadPP;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int currentIndex = jNotepadPP.getTabbedPane().getSelectedIndex();

		if (currentIndex >= 0) {

			JScrollPane scrollPane = (JScrollPane) jNotepadPP.getTabbedPane().getComponentAt(currentIndex);
			Tab currentTab = (Tab) scrollPane.getViewport().getView();
			String text = currentTab.getText();

			int numOfChars = text.length();
			int numOfNonBlankChars = text.replaceAll("\\s+", "").length();
			int numOfLines = currentTab.getLineCount();

			String message = String.format(flp.getString(STAT_MESSAGE), numOfChars, numOfNonBlankChars, numOfLines);
			JOptionPane.showMessageDialog(jNotepadPP, message);
		}
	}

}
