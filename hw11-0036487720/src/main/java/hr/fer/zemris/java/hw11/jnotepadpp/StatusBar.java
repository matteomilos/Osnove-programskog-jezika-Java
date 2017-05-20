package hr.fer.zemris.java.hw11.jnotepadpp;

import static hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationConstants.COL;
import static hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationConstants.LENGTH;
import static hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationConstants.SEL;

import java.awt.Dimension;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;

import hr.fer.zemris.java.hw11.jnotepadpp.local.swing.FormLocalizationProvider;

/**
 * Class derived from {@link JPanel} class, representing status bar of
 * {@link JNotepadPP}.
 * 
 * @author Matteo Miloš
 *
 */
public class StatusBar extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** Current text area. */
	private JTextArea textArea;

	/** Label for length of the text. */
	private JLabel lengthLabel;

	/** Label for number of current line. */
	private JLabel lineLabel;

	/** Label for number of current column. */
	private JLabel columnLabel;

	/** Label for number of currently selected characters. */
	private JLabel selectedLabel;

	/** instance of {@link MyClock} class, added to the status bar */
	private MyClock clock;

	/**
	 * instance of class {@link FormLocalizationProvider}, represents
	 * localization provider for this panel
	 */
	private FormLocalizationProvider flp;

	/**
	 * Constructor that initializes status bar for the current tab.
	 * 
	 * @param tab
	 *            current tab
	 * @param flp
	 *            localization provider
	 */
	public StatusBar(Tab tab, FormLocalizationProvider flp) {
		this.flp = flp;
		initStatusBar(tab);
		flp.getProvider().addLocalizationListener(() -> refresh(this.textArea));
	}

	/**
	 * Method called from constructor of {@link StatusBar}, initializes status
	 * bar.
	 * 
	 * @param tab
	 *            current tab
	 */
	private void initStatusBar(Tab tab) {
		setBorder(new BevelBorder(BevelBorder.LOWERED));
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		setTextArea(tab);

		lengthLabel = new JLabel();
		add(lengthLabel);
		add(Box.createHorizontalGlue());

		JPanel panel = new JPanel();

		JSeparator separator = new JSeparator(SwingConstants.VERTICAL);
		separator.setPreferredSize(new Dimension(3, 20));
		panel.add(separator);

		lineLabel = new JLabel();
		panel.add(lineLabel);
		columnLabel = new JLabel();
		panel.add(columnLabel);
		selectedLabel = new JLabel();
		panel.add(selectedLabel);
		add(panel);

		add(Box.createHorizontalGlue());

		clock = new MyClock();
		add(clock);

		refresh(textArea);
	}

	/**
	 * Method called when user changes tab.
	 * 
	 * @param textArea
	 *            text area of the tab.
	 */
	protected void refresh(JTextArea textArea) {
		lengthLabel.setText(flp.getString(LENGTH) + ": " + textArea.getText().length());
		lineLabel.setText("Ln: " + textArea.getLineCount());
		int caretPos = textArea.getCaretPosition();
		int offset;
		int colNum = 1;
		try {
			offset = textArea.getLineOfOffset(caretPos);
			colNum = caretPos - textArea.getLineStartOffset(offset) + 1;
		} catch (Exception ignorable) {
		}
		columnLabel.setText(flp.getString(COL) + ": " + colNum);
		selectedLabel.setText(flp.getString(SEL) + ": " + getSelected());
	}

	/**
	 * Method for setting new text area.
	 * 
	 * @param textArea
	 *            new text area
	 */
	public void setTextArea(JTextArea textArea) {
		this.textArea = textArea;
		this.textArea.addCaretListener(
				(l) -> {
					refresh(textArea);
				}
		);
	}

	/**
	 * Method that returns length of currently selected text.
	 * 
	 * @return length of selected text
	 */
	private int getSelected() {
		return Math.abs(textArea.getCaret().getDot() - textArea.getCaret().getMark());
	}

	/**
	 * Method that returns clock of this status bar
	 * 
	 * @return the clock
	 */
	public MyClock getClock() {
		return clock;
	}

	/**
	 * Class used for instantiation of clock for this status bar
	 * 
	 * @author Matteo Miloš
	 *
	 */
	protected static class MyClock extends JLabel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1274043934361755595L;

		/**
		 * Flag that marks if main frame is closed
		 */
		private volatile boolean stopRequested = false;

		/**
		 * Instance of {@link DateTimeFormatter}, used for formatting date and
		 * time.
		 */
		private DateTimeFormatter dtf;

		/**
		 * Constructor that creates and starts the clock.
		 */
		public MyClock() {
			dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
			Thread radnik = new Thread(
					() -> {
						while (!stopRequested) {

							SwingUtilities.invokeLater(
									() -> {
										LocalDateTime now = LocalDateTime.now();
										this.setText(dtf.format(now));
									}
							);

							try {
								Thread.sleep(1000);
							} catch (Exception ignorable) {
							}
						}
					}
			);
			radnik.setDaemon(true);
			radnik.start();
		}

		/**
		 * Method called when main frame is closed.
		 */
		protected void terminate() {
			stopRequested = true;
		}
	}

}
