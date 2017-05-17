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
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.text.DefaultEditorKit.PasteAction;

import hr.fer.zemris.java.hw11.jnotepadpp.actions.CloseDocumentAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.MyCopyAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.MyCutAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.MyPasteAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.NewDocumentAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.OpenDocumentAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.SaveAsDocumentAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.SaveDocumentAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.SetCroatianAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.SetEnglishAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.StatisticsAction;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.swing.FormLocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.swing.LJMenu;

public class JNotepadPP extends JFrame {

	private static final long serialVersionUID = 1L;

	private JTabbedPane tabbedPane;

	private StatusBar statusBar;

	private FormLocalizationProvider flp = new FormLocalizationProvider(LocalizationProvider.getInstance(), this);

	public JTabbedPane getTabbedPane() {
		return tabbedPane;
	}

	private int nTabs = 0;

	public JNotepadPP() throws IOException {
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

		setTitle("JNotepad++");
		setSize(700, 700);
		setLocation(100, 100);
		initGUI();

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
						statusBar.getClock().terminate();
						dispose();
					}
				}
		);
	}

	private void initGUI() throws IOException {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());

		tabbedPane = new JTabbedPane();

		statusBar = new StatusBar(addNewTab(null, null), flp);

		tabbedPane.addChangeListener(
				(l) -> {
					JScrollPane scrollPane = (JScrollPane) tabbedPane.getSelectedComponent();
					if (scrollPane == null) {
						return;
					}
					Tab tab = (Tab) scrollPane.getViewport().getView();
					JNotepadPP.this.setTitle(tab.getLongName() + " - JNotepad++");
					statusBar.setTextArea(tab);
					statusBar.refresh(tab);
				}
		);

		add(tabbedPane, BorderLayout.CENTER);

		add(statusBar, BorderLayout.AFTER_LAST_LINE);
		createMenus();
		createToolbars();

	}

	public Tab addNewTab(Path path, String text) throws IOException {

		String tabName = path == null ? "new " + getnTabs() : path.getFileName().toString();
		Tab tab = new Tab(path, text, tabName, this);
		JScrollPane component = new JScrollPane(tab);
		tabbedPane.addTab(tab.getSimpleName(), component);
		tab.addIcon();
		tabbedPane.setTitleAt(getnTabs(), tab.getSimpleName());
		tabbedPane.setToolTipTextAt(getnTabs(), tab.getLongName());
		tabbedPane.setSelectedIndex(getnTabs());
		nTabs++;
		return tab;

	}

	private void createToolbars() {
		JToolBar toolBar = new JToolBar("Toolbar");
		toolBar.add(new JButton(newDocumentAction));
		toolBar.add(new JButton(openDocumentAction));
		toolBar.add(new JButton(closeDocumentAction));
		toolBar.addSeparator();
		toolBar.add(new JButton(saveDocumentAction));
		toolBar.add(new JButton(saveAsDocumentAction));
		toolBar.addSeparator();
		toolBar.add(new JButton(cutAction));
		toolBar.add(new JButton(copyAction));
		toolBar.add(new JButton(pasteAction));
		toolBar.addSeparator();
		toolBar.add(new JButton(statisticsAction));
		getContentPane().add(toolBar, BorderLayout.PAGE_START);

	}

	private void createMenus() {
		JMenuBar menuBar = new JMenuBar();

		LJMenu fileMenu = new LJMenu("file", flp);
		menuBar.add(fileMenu);
		fileMenu.add(new JMenuItem(newDocumentAction));
		fileMenu.add(new JMenuItem(openDocumentAction));
		fileMenu.add(new JMenuItem(saveDocumentAction));
		fileMenu.add(new JMenuItem(saveAsDocumentAction));
		fileMenu.add(new JMenuItem(closeDocumentAction));

		LJMenu editMenu = new LJMenu("edit", flp);
		editMenu.add(new JMenuItem(cutAction));
		editMenu.add(new JMenuItem(copyAction));
		editMenu.add(new JMenuItem(pasteAction));
		menuBar.add(editMenu);

		LJMenu calculateMenu = new LJMenu("statistics", flp);
		calculateMenu.add(new JMenuItem(statisticsAction));
		menuBar.add(calculateMenu);

		LJMenu languageMenu = new LJMenu("language", flp);
		languageMenu.add(new JMenuItem(setEnglishAction));
		languageMenu.add(new JMenuItem(setCroatianAction));
		menuBar.add(languageMenu);

		setJMenuBar(menuBar);
	}

	private final Action openDocumentAction = new OpenDocumentAction(this, flp);

	private final Action saveDocumentAction = new SaveDocumentAction(this, flp);

	private final Action saveAsDocumentAction = new SaveAsDocumentAction(this, flp);

	private final Action newDocumentAction = new NewDocumentAction(this, flp);

	private final Action closeDocumentAction = new CloseDocumentAction(this, flp);

	private final Action copyAction = new MyCopyAction(flp);

	private final Action cutAction = new MyCutAction(flp);

	private final Action pasteAction = new MyPasteAction(flp);

	private final Action statisticsAction = new StatisticsAction(this, flp);

	private final Action setEnglishAction = new SetEnglishAction(flp);

	private final Action setCroatianAction = new SetCroatianAction(flp);

	public static void main(String[] args) {
		SwingUtilities.invokeLater(
				() -> {
					LocalizationProvider.getInstance().setLanguage(args[0]);
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

	public FormLocalizationProvider getFlp() {
		return flp;
	}
}