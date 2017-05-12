package hr.fer.zemris.java.gui.prim;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * The Class PrimDemo that is used to demonstrate prime number list generator.
 */
public class PrimDemo extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates and initializes a new Prime numbers frame.
	 */
	public PrimDemo() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setLocation(20, 20);
		setSize(500, 200);
		setTitle("PrimDemo");
		initGUI();
	}

	/**
	 * The primes list model that provides a <code>List</code> with its
	 * contents.
	 * 
	 * @author Matteo Miloš
	 *
	 */
	// public for purposes of testing
	public static class PrimListModel implements ListModel<Integer> {

		/** current prime */
		private static int currPrime = 2;

		/** List of prime numbers */
		private List<Integer> primeList = new ArrayList<>();

		/** List of observers */
		private List<ListDataListener> observers = new ArrayList<>();

		@Override
		public void addListDataListener(ListDataListener l) {
			observers.add(l);
		}

		@Override
		public Integer getElementAt(int index) {
			return primeList.get(index);
		}

		@Override
		public int getSize() {
			return primeList.size();
		}

		@Override
		public void removeListDataListener(ListDataListener l) {
			observers.remove(l);
		}

		/**
		 * Generates next prime number that is to a displayed list and notifies
		 * all listeners about it.
		 */
		public void next() {
			while (!isPrime(currPrime++))
				;
			int position = primeList.size();
			primeList.add(currPrime - 1);
			ListDataEvent event = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, position, position);

			for (ListDataListener l : observers) {
				l.intervalAdded(event);
			}

		}

		/**
		 * Method for checking if current number is prime number-
		 * 
		 * @param currPrime
		 *            current number
		 * @return true if is prime number, false otherwise
		 */
		public boolean isPrime(int currPrime) {
			if (currPrime < 2) {
				return false;
			}
			if (currPrime == 2) {
				return true;
			}
			if (currPrime % 2 == 0) {
				return false;
			}
			for (int i = 3; i * i <= currPrime; i += 2) {
				if (currPrime % i == 0) {
					return false;
				}
			}
			return true;
		}
	}

	/**
	 * Initializes all needed components for graphical user interface.
	 */
	private void initGUI() {
		Container cp = getContentPane();

		cp.setLayout(new BorderLayout());

		PrimListModel model = new PrimListModel();
		model.primeList.add(1);

		JButton button = new JButton("sljedeći");
		cp.add(button, BorderLayout.PAGE_END);
		button.addActionListener((e) -> {
			model.next();
		});

		JList<Integer> firstList = new JList<>(model);
		JList<Integer> secondList = new JList<>(model);
		JPanel grid = new JPanel(new GridLayout(1, 2));
		cp.add(grid, BorderLayout.CENTER);
		grid.add(new JScrollPane(firstList));
		grid.add(new JScrollPane(secondList));

	}

	/**
	 * Entry point to the program, creates a new window for prime number
	 * generator.
	 * 
	 * @param args
	 *            unused command line arguments
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new PrimDemo().setVisible(true));
	}
}
