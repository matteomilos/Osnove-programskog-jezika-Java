package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
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
import javax.swing.text.BadLocationException;

import hr.fer.zemris.java.hw11.jnotepadpp.local.swing.FormLocalizationProvider;

public class StatusBar extends JPanel {

	private JTextArea textArea;

	private JLabel lengthLabel;

	private JLabel lineLabel;

	private JLabel columnLabel;

	private JLabel selectedLabel;

	private MyClock clock;

	private FormLocalizationProvider flp;

	public StatusBar(Tab tab, FormLocalizationProvider flp) {
		this.flp = flp;
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

	protected void refresh(JTextArea textArea) {
		lengthLabel.setText("length: " + textArea.getText().length());
		lineLabel.setText("Ln: " + textArea.getLineCount());
		int caretPos = textArea.getCaretPosition();
		int offset;
		int colNum = 1;
		try {
			offset = textArea.getLineOfOffset(caretPos);
			colNum = caretPos - textArea.getLineStartOffset(offset) + 1;
		} catch (Exception ignorable) {
		}
		columnLabel.setText("Col: " + colNum);
		selectedLabel.setText("Sel: " + getSelected());
	}

	public void setTextArea(JTextArea textArea) {
		this.textArea = textArea;
		this.textArea.addCaretListener(
				(l) -> {
					refresh(textArea);
				}
		);
	}

	private int getSelected() {
		return Math.abs(textArea.getCaret().getDot() - textArea.getCaret().getMark());
	}

	public MyClock getClock() {
		return clock;
	}

	protected static class MyClock extends JLabel {

		private volatile boolean stopRequested = false;

		DateTimeFormatter dtf;

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

		protected void terminate() {
			stopRequested = true;
		}
	}

}
