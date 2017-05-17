package hr.fer.zemris.java.hw11.jnotepadpp;

import static hr.fer.zemris.java.hw11.jnotepadpp.icons.Icons.*;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.nio.file.Path;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;

import hr.fer.zemris.java.hw11.jnotepadpp.actions.AscendingAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.CloseDocumentAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.DescendingAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.LowerCaseAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.MyCopyAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.MyCutAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.MyPasteAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.NewDocumentAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.OpenDocumentAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.SaveAsDocumentAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.SaveDocumentAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.SetCroatianAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.SetEnglishAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.SetGermanAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.StatisticsAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.ToggleCaseAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.UniqueAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.UpperCaseAction;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.swing.FormLocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.swing.LJMenu;

public class JNotepadPP extends JFrame {

	private static final String NEW = "new";

	private static final String FILE = "file";

	private static final String EDIT = "edit";

	private static final String STATISTICS = "statistics";

	private static final String LANGUAGE = "language";

	private static final long serialVersionUID = 1L;

	private static final String CLOSING_WINDOW = "closing";

	private static final String INFORMATION = "info";

	private static final String TOOLS = "tools";

	private static final String SORT = "sort";

	private static final String CHANGE_CASE = "changecase";

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
		setSize(1000, 700);
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
								String message = String.format(flp.getString(CLOSING_WINDOW), tab.getSimpleName());
								int selected = JOptionPane.showConfirmDialog(
										JNotepadPP.this,
										message,
										INFORMATION,
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

		addToolBarItem(toolBar, newDocumentAction, NEWFILE_ICON);
		addToolBarItem(toolBar, openDocumentAction, OPEN_EXISTING);
		addToolBarItem(toolBar, closeDocumentAction, CLOSE_ICON);
		toolBar.addSeparator();

		addToolBarItem(toolBar, saveDocumentAction, SAVE_ICON);
		addToolBarItem(toolBar, saveAsDocumentAction, SAVEAS_ICON);
		toolBar.addSeparator();

		addToolBarItem(toolBar, cutAction, CUT_ICON);
		addToolBarItem(toolBar, copyAction, COPY_ICON);
		addToolBarItem(toolBar, pasteAction, PASTE_ICON);
		toolBar.addSeparator();

		addToolBarItem(toolBar, statisticsAction, STATISTICS_ICON);
		getContentPane().add(toolBar, BorderLayout.PAGE_START);
	}

	private void addToolBarItem(JToolBar toolBar, Action action, Icon icon) {
		JButton button = new JButton(action);
		button.setIcon(icon);
		toolBar.add(button);
	}

	private void createMenus() {
		JMenuBar menuBar = new JMenuBar();

		LJMenu fileMenu = new LJMenu(FILE, flp);
		fileMenu.add(new JMenuItem(newDocumentAction));
		fileMenu.add(new JMenuItem(openDocumentAction));
		fileMenu.add(new JMenuItem(saveDocumentAction));
		fileMenu.add(new JMenuItem(saveAsDocumentAction));
		fileMenu.add(new JMenuItem(closeDocumentAction));
		
		menuBar.add(fileMenu);

		LJMenu editMenu = new LJMenu(EDIT, flp);
		editMenu.add(new JMenuItem(cutAction));
		editMenu.add(new JMenuItem(copyAction));
		editMenu.add(new JMenuItem(pasteAction));
		
		menuBar.add(editMenu);

		LJMenu calculateMenu = new LJMenu(STATISTICS, flp);
		calculateMenu.add(new JMenuItem(statisticsAction));
		
		menuBar.add(calculateMenu);

		LJMenu languageMenu = new LJMenu(LANGUAGE, flp);
		languageMenu.add(new JMenuItem(setEnglishAction));
		languageMenu.add(new JMenuItem(setCroatianAction));
		languageMenu.add(new JMenuItem(setGermanAction));
		
		menuBar.add(languageMenu);

		LJMenu toolsMenu = new LJMenu(TOOLS, flp);

		LJMenu sortSubMenu = new LJMenu(SORT, flp);
		sortSubMenu.add(ascendingAction);
		sortSubMenu.add(descendingAction);

		LJMenu changeCasesubMenu = new LJMenu(CHANGE_CASE, flp);
		changeCasesubMenu.add(upperCaseAction);
		changeCasesubMenu.add(lowerCaseAction);
		changeCasesubMenu.add(toggleCaseAction);

		toolsMenu.add(sortSubMenu);
		toolsMenu.add(changeCasesubMenu);
		toolsMenu.add(new JMenuItem(uniqueAction));

		menuBar.add(toolsMenu);
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

	private final Action setGermanAction = new SetGermanAction(flp);

	private final Action ascendingAction = new AscendingAction(this, flp);

	private final Action descendingAction = new DescendingAction(this, flp);

	private final Action upperCaseAction = new UpperCaseAction(this, flp);

	private final Action lowerCaseAction = new LowerCaseAction(this, flp);

	private final Action toggleCaseAction = new ToggleCaseAction(this, flp);

	private final Action uniqueAction = new UniqueAction(this, flp);

	public static void main(String[] args) {
		SwingUtilities.invokeLater(
				() -> {
					LocalizationProvider.getInstance().setLanguage(args.length == 0 ? "en" : args[0]);
					try {
						new JNotepadPP().setVisible(true);
					} catch (IOException e) {
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