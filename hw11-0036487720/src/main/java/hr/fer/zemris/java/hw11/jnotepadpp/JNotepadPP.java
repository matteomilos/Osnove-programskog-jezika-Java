package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.nio.file.Path;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.text.DefaultEditorKit.CopyAction;
import javax.swing.text.DefaultEditorKit.CutAction;
import javax.swing.text.DefaultEditorKit.PasteAction;

import hr.fer.zemris.java.hw11.jnotepadpp.actions.CalculateInfoAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.CloseDocumentAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.NewDocumentAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.OpenDocumentAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.SaveAsDocumentAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.SaveDocumentAction;

public class JNotepadPP extends JFrame {

	private static final long serialVersionUID = 1L;

	private JTabbedPane tabbedPane;

	public JTabbedPane getTabbedPane() {
		return tabbedPane;
	}

	private int nTabs = 0;

	public JNotepadPP() throws IOException {
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(
				new WindowAdapter() {

					@Override
					public void windowClosing(WindowEvent e) {
						closingWindow();
					}

					private void closingWindow() {
						JTabbedPane tabbedPane = JNotepadPP.this.getTabbedPane();
						int totalTabs = tabbedPane.getTabCount();

						for (int i = 0; i < totalTabs; i++) {
							JScrollPane scrollPane = (JScrollPane) tabbedPane.getComponentAt(i);
							Tab tab = (Tab) scrollPane.getViewport().getView();
							if (tab.isChanged()) {
								int selected = JOptionPane.showConfirmDialog(
										JNotepadPP.this,
										"You have unsaved changes, do you want to save document \""
												+ tab.getSimpleName() + "\"",
										"Information",
										JOptionPane.YES_NO_CANCEL_OPTION
								);
								switch (selected) {
								case JOptionPane.YES_OPTION:
									((SaveAsDocumentAction) saveAsDocumentAction).saveDocument(i);
									break;
								case JOptionPane.NO_OPTION:
									continue;
								case JOptionPane.CANCEL_OPTION:
									return;
								}
							}
						}

						dispose();
					}
				}
		);
		setTitle("JNotepad++");
		setSize(700, 700);
		setLocation(100, 100);

		initGUI();

	}

	private void initGUI() throws IOException {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());

		tabbedPane = new JTabbedPane();
		tabbedPane.addChangeListener(
				(l) -> {
					JScrollPane scrollPane = (JScrollPane) tabbedPane.getSelectedComponent();
					if (scrollPane == null) {
						return;
					}
					Tab tab = (Tab) scrollPane.getViewport().getView();
					JNotepadPP.this.setTitle(tab.getLongName() + " - JNotepad++");
				}
		);

		addNewTab(null, null);

		add(tabbedPane);
		createActions();
		createMenus();
		createToolbars();

	}

	public void addNewTab(Path path, String text) throws IOException {

		String tabName = path == null ? "new " + getnTabs() : path.getFileName().toString();
		Tab tab = new Tab(path, text, tabName, this);
		JScrollPane component = new JScrollPane(tab);
		tabbedPane.addTab(tab.getSimpleName(), component);
		tab.addIcon();
		tabbedPane.setTitleAt(getnTabs(), tab.getSimpleName());
		tabbedPane.setToolTipTextAt(getnTabs(), tab.getLongName());
		tabbedPane.setSelectedIndex(getnTabs());
		setnTabs(getnTabs() + 1);

	}

	private void createToolbars() {
		JToolBar toolBar = new JToolBar("Toolbar");

		toolBar.add(new JButton(openDocumentAction));
		toolBar.add(new JButton(saveDocumentAction));
		toolBar.add(new JButton(saveAsDocumentAction));
		toolBar.add(new JButton(newDocumentAction));
		toolBar.add(new JButton(closeDocumentAction));
		toolBar.add(new JButton(cutAction));
		toolBar.add(new JButton(copyAction));
		toolBar.add(new JButton(pasteAction));
		toolBar.add(new JButton(calculateInfoAction));
		getContentPane().add(toolBar, BorderLayout.PAGE_START);

	}

	private void createMenus() {
		JMenuBar menuBar = new JMenuBar();

		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		fileMenu.add(new JMenuItem(newDocumentAction));
		fileMenu.add(new JMenuItem(openDocumentAction));
		fileMenu.add(new JMenuItem(saveDocumentAction));
		fileMenu.add(new JMenuItem(saveAsDocumentAction));
		fileMenu.add(new JMenuItem(closeDocumentAction));

		JMenu editMenu = new JMenu("Edit");
		editMenu.add(new JMenuItem(cutAction));
		editMenu.add(new JMenuItem(copyAction));
		editMenu.add(new JMenuItem(pasteAction));
		menuBar.add(editMenu);

		JMenu calculateMenu = new JMenu("Calculate");
		calculateMenu.add(new JMenuItem(calculateInfoAction));
		menuBar.add(calculateMenu);

		setJMenuBar(menuBar);
	}

	private void createActions() {
		openDocumentAction.putValue(Action.NAME, "Open");
		openDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
		openDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);
		openDocumentAction.putValue(Action.SHORT_DESCRIPTION, "Used to open document from disc");

		saveDocumentAction.putValue(Action.NAME, "Save");
		saveDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
		saveDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
		saveDocumentAction.putValue(Action.SHORT_DESCRIPTION, "Used to save document from disc");

		saveAsDocumentAction.putValue(Action.NAME, "Save as");
		saveAsDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control alt S"));
		saveAsDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S | InputEvent.ALT_DOWN_MASK);
		saveAsDocumentAction.putValue(Action.SHORT_DESCRIPTION, "Used to save as new document from disc");

		newDocumentAction.putValue(Action.NAME, "New");
		newDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control N"));
		newDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_N);
		newDocumentAction.putValue(Action.SHORT_DESCRIPTION, "Used to create new document");

		closeDocumentAction.putValue(Action.NAME, "Close");
		closeDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control W"));
		closeDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_W);
		closeDocumentAction.putValue(Action.SHORT_DESCRIPTION, "Used to close opened document");

		copyAction.putValue(Action.NAME, "Copy");
		copyAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control C"));
		copyAction.putValue(Action.SHORT_DESCRIPTION, "Used to copy part of the text");

		pasteAction.putValue(Action.NAME, "Paste");
		pasteAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control V"));
		pasteAction.putValue(Action.SHORT_DESCRIPTION, "Used to paste part of the text");

		cutAction.putValue(Action.NAME, "Cut");
		cutAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control X"));
		cutAction.putValue(Action.SHORT_DESCRIPTION, "Used to cut part of the text");

		calculateInfoAction.putValue(Action.NAME, "Calculate");
		calculateInfoAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control P"));
		calculateInfoAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_P);
		calculateInfoAction.putValue(Action.SHORT_DESCRIPTION, "Used to calculate statistical info of document");
	}

	private final Action openDocumentAction = new OpenDocumentAction(this);

	private final Action saveDocumentAction = new SaveDocumentAction(this);

	private final Action saveAsDocumentAction = new SaveAsDocumentAction(this);

	private final Action newDocumentAction = new NewDocumentAction(this);

	private final Action closeDocumentAction = new CloseDocumentAction(this);

	private final Action copyAction = new CopyAction();

	private final Action cutAction = new CutAction();

	private final Action pasteAction = new PasteAction();

	private final Action calculateInfoAction = new CalculateInfoAction(this);

	public static void main(String[] args) {
		SwingUtilities.invokeLater(
				() -> {
					try {
						new JNotepadPP().setVisible(true);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
		);
	}

	public Action getSaveDocumentAction() {
		return saveDocumentAction;
	}

	public int getnTabs() {
		return nTabs;
	}

	public void setnTabs(int nTabs) {
		this.nTabs = nTabs;
	}

}
