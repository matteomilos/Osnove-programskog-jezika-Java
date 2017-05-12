package hr.fer.zemris.java.gui.layouts;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

@SuppressWarnings("javadoc")
public class CalcLayoutTest {

	public static void main(String[] args) {
		JFrame frame = new JFrame();

		frame.setSize(500, 500);
		JPanel p = new JPanel(new CalcLayout(3));
		p.add(new JButton("x"), "1,1");
		p.add(new JButton("y"), "2,3");
		p.add(new JButton("z"), "2,7");
		p.add(new JButton("w"), "4,2");
		p.add(new JButton("a"), "4,5");
		p.add(new JButton("b"), "4,7");

		frame.add(p);

		frame.setVisible(true);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
}
