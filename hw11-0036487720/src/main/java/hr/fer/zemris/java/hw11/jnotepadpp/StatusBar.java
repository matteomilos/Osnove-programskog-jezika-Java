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

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;

public class StatusBar extends JPanel {

	private JTextArea textArea;

	private JLabel lengthLabel;

	private JLabel lineLabel;

	private JLabel columnLabel;

	private JLabel selectedLabel;

	private MojSat clock;

	public StatusBar(JTextArea textArea) {
		super(new FlowLayout(FlowLayout.LEFT));
		setTextArea(textArea);
		lengthLabel = new JLabel("length: " + textArea.getText().length());
		add(lengthLabel);

		JSeparator separator = new JSeparator(SwingConstants.VERTICAL);
		separator.setPreferredSize(new Dimension(3, 20));
		add(separator);
		JPanel panel = new JPanel();
		lineLabel = new JLabel("Ln: " + textArea.getLineCount());
		panel.add(lineLabel);

		panel.add(new JSeparator());

		columnLabel = new JLabel("Col: " + textArea.getCaretPosition());
		panel.add(columnLabel);

		panel.add(new JSeparator());

		selectedLabel = new JLabel("Sel: " + getSelected());
		panel.add(selectedLabel);
		add(panel);
		JPanel panel2 = new JPanel(new BorderLayout());

		clock = new MojSat();
		panel2.add(clock);
		add(panel2);

	}

	void refresh(JTextArea textArea) {
		lengthLabel.setText("length: " + textArea.getText().length());
		lineLabel.setText("Ln: " + textArea.getLineCount());
		int caretPos = textArea.getCaretPosition();
		int offset;
		int colNum;
		try {
			offset = textArea.getLineOfOffset(caretPos);
			colNum = caretPos - textArea.getLineStartOffset(offset) + 1;
		} catch (BadLocationException e) {
			return;
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

	public MojSat getClock() {
		return clock;
	}

	static class MojSat extends JLabel {

		private volatile boolean stopRequested = false;

		public MojSat() {
			Thread radnik = new Thread(
					() -> {
						while (!stopRequested) {

							SwingUtilities.invokeLater(
									() -> {
										DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
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

		public void terminate() {
			stopRequested = true;
		}
	}

}
