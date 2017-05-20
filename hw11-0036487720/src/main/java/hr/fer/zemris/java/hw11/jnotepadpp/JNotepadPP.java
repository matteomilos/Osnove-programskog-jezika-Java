package hr.fer.zemris.java.hw11.jnotepadpp;

import static hr.fer.zemris.java.hw11.jnotepadpp.icons.Icons.CLOSE_ICON;
import static hr.fer.zemris.java.hw11.jnotepadpp.icons.Icons.COPY_ICON;
import static hr.fer.zemris.java.hw11.jnotepadpp.icons.Icons.CUT_ICON;
import static hr.fer.zemris.java.hw11.jnotepadpp.icons.Icons.NEWFILE_ICON;
import static hr.fer.zemris.java.hw11.jnotepadpp.icons.Icons.OPEN_EXISTING;
import static hr.fer.zemris.java.hw11.jnotepadpp.icons.Icons.PASTE_ICON;
import static hr.fer.zemris.java.hw11.jnotepadpp.icons.Icons.SAVEAS_ICON;
import static hr.fer.zemris.java.hw11.jnotepadpp.icons.Icons.SAVE_ICON;
import static hr.fer.zemris.java.hw11.jnotepadpp.icons.Icons.STATISTICS_ICON;
import static hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationConstants.CHANGE_CASE;
import static hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationConstants.CLOSING_WINDOW;
import static hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationConstants.EDIT;
import static hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationConstants.FILE;
import static hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationConstants.INFORMATION;
import static hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationConstants.LANGUAGE;
import static hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationConstants.SORT;
import static hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationConstants.STATISTICS;
import static hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationConstants.TOOLS;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
import hr.fer.zemris.java.hw11.jnotepadpp.actions.ExitAction;
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

/**
 * This is main frame for application JNotepadPP, simple text editor that has
 * some similar functionalities as famous application Notepad++. Functionalities
 * that this app supports are: few editing commands, saving and opening
 * documents, getting statistical data about document.
 * 
 * @author Matteo Miloš
 *
 */
public class JNotepadPP extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3616826431201398391L;

	/** tabs of this frame */
	private JTabbedPane tabbedPane;

	/** status bar with information about current tab */
	private StatusBar statusBar;

	/**
	 * instance of class {@link FormLocalizationProvider}, represents
	 * localization provider for this frame
	 */
	private FormLocalizationProvider flp = new FormLocalizationProvider(LocalizationProvider.getInstance(), this);

	/**
	 * Getter method for tabs of this frame.
	 * 
	 * @return this frame's tabs
	 */
	public JTabbedPane getTabbedPane() {
		return tabbedPane;
	}

	/**
	 * Number of tabs currently opened.
	 */
	private int nTabs = 0;

	/**
	 * Constructor that creates and initializes this frame
	 */
	public JNotepadPP() {
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

		setTitle("new 0 - JNotepad++");
		setSize(1000, 700);
		setLocation(100, 100);
		initGUI();

		addWindowListener(
				new WindowAdapter() {

					@Override
					public void windowClosing(WindowEvent e) {
						closingWindow();
					}

				}
		);
	}

	/**
	 * Method responsible for initializing graphical user interface.
	 */
	private void initGUI() {
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

	/**
	 * Method used for adding new tab to the {@link #tabbedPane} component.
	 * 
	 * @param path
	 *            path of the file on the tab
	 * @param text
	 *            text that file contains
	 * @return added tab
	 */
	public Tab addNewTab(Path path, String text) {

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

	/**
	 * Method used for creating toolbars that are added to this frame.
	 */
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

	/**
	 * Method used for adding each button to the toolBar.
	 * 
	 * @param toolBar
	 *            instance of the {@link JToolBar}
	 * @param action
	 *            action being activated on click on this button
	 * @param icon
	 *            icon that this button contains
	 */
	private void addToolBarItem(JToolBar toolBar, Action action, Icon icon) {
		JButton button = new JButton(action);
		button.setIcon(icon);
		toolBar.add(button);
	}

	/**
	 * Method responsible for creating menus on the frame.
	 */
	private void createMenus() {
		JMenuBar menuBar = new JMenuBar();

		LJMenu fileMenu = new LJMenu(FILE, flp);
		fileMenu.add(new JMenuItem(newDocumentAction));
		fileMenu.add(new JMenuItem(openDocumentAction));
		fileMenu.add(new JMenuItem(saveDocumentAction));
		fileMenu.add(new JMenuItem(saveAsDocumentAction));
		fileMenu.add(new JMenuItem(closeDocumentAction));
		fileMenu.addSeparator();
		fileMenu.add(new JMenuItem(exitAction));

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
		JMenuItem upperCase = new JMenuItem(getUpperCaseAction());
		changeCasesubMenu.add(upperCase);
		JMenuItem lowerCase = new JMenuItem(lowerCaseAction);
		changeCasesubMenu.add(lowerCase);
		JMenuItem toggleCase = new JMenuItem(toggleCaseAction);
		changeCasesubMenu.add(toggleCase);

		toolsMenu.add(sortSubMenu);
		toolsMenu.add(changeCasesubMenu);
		toolsMenu.add(new JMenuItem(uniqueAction));

		menuBar.add(toolsMenu);
		setJMenuBar(menuBar);
	}

	/**
	 * Action for exiting the frame.
	 */
	private final Action exitAction = new ExitAction(this, flp);

	/**
	 * Action for opening existing document.
	 */
	private final Action openDocumentAction = new OpenDocumentAction(this, flp);

	/**
	 * Action for saving document as existing document.
	 */
	private final Action saveDocumentAction = new SaveDocumentAction(this, flp);

	/**
	 * Action for saving document as a new document.
	 */
	private final Action saveAsDocumentAction = new SaveAsDocumentAction(this, flp);

	/**
	 * Action for creating new blank document.
	 */
	private final Action newDocumentAction = new NewDocumentAction(this, flp);

	/**
	 * Action for closing current tab of the frame.
	 */
	private final Action closeDocumentAction = new CloseDocumentAction(this, flp);

	/**
	 * Action for copying selected part of the text.
	 */
	private final Action copyAction = new MyCopyAction(flp);

	/**
	 * Action for cutting selected part of the text.
	 */
	private final Action cutAction = new MyCutAction(flp);

	/**
	 * Action for pasting selected part of the text.
	 */
	private final Action pasteAction = new MyPasteAction(flp);

	/**
	 * Action for getting statistical data about document.
	 */
	private final Action statisticsAction = new StatisticsAction(this, flp);

	/**
	 * Action for setting English as current language.
	 */
	private final Action setEnglishAction = new SetEnglishAction(flp);

	/**
	 * Action for setting Croatian as current language.
	 */
	private final Action setCroatianAction = new SetCroatianAction(flp);

	/**
	 * Action for setting German as current language.
	 */
	private final Action setGermanAction = new SetGermanAction(flp);

	/**
	 * Action for sorting selected text in ascending order.
	 */
	private final Action ascendingAction = new AscendingAction(this, flp);

	/**
	 * Action for sorting selected text in descending order.
	 */
	private final Action descendingAction = new DescendingAction(this, flp);

	/**
	 * Action for setting the letters of the selected text to the upper case.
	 */
	private final Action upperCaseAction = new UpperCaseAction(this, flp);

	/**
	 * Action for setting the letters of the selected text to the lower case.
	 */
	private final Action lowerCaseAction = new LowerCaseAction(this, flp);

	/**
	 * Action for setting the letters of the opposite case of the current.
	 */
	private final Action toggleCaseAction = new ToggleCaseAction(this, flp);

	/**
	 * Action for removing similar lines from selected text.
	 */
	private final Action uniqueAction = new UniqueAction(this, flp);

	/**
	 * Invoking this method starts the program
	 * 
	 * @param args
	 *            if provided, first argument represents default language of the
	 *            application, otherwise sets English as default.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(
				() -> {
					LocalizationProvider.getInstance().setLanguage(args.length == 0 ? "en" : args[0]);
					new JNotepadPP().setVisible(true);
				}
		);
	}

	/**
	 * Getter for instance of {@link SaveDocumentAction}.
	 * 
	 * @return instance of {@link SaveDocumentAction}.
	 */
	public Action getSaveDocumentAction() {
		return saveDocumentAction;
	}

	/**
	 * Getter for instance of {@link SaveAsDocumentAction}.
	 * 
	 * @return instance of {@link SaveAsDocumentAction}.
	 */
	public Action getSaveAsDocumentAction() {
		return saveAsDocumentAction;
	}

	/**
	 * Getter for current number of opened tabs.
	 * 
	 * @return number of tabs
	 */
	public int getnTabs() {
		return nTabs;
	}

	/**
	 * Setter for current number of opened tabs.
	 * 
	 * @param nTabs
	 *            number of tabs
	 */
	public void setnTabs(int nTabs) {
		this.nTabs = nTabs;
	}

	/**
	 * Getter for instance of {@link FormLocalizationProvider}, localization
	 * provider of this program.
	 * 
	 * @return localization provider
	 */
	public FormLocalizationProvider getFlp() {
		return flp;
	}

	/**
	 * Method invoked when user tries to exit application.
	 */
	public void closingWindow() {
		JTabbedPane tabbedPane = JNotepadPP.this.getTabbedPane();
		int totalTabs = tabbedPane.getTabCount();

		for (int i = 0; i < totalTabs; i++) {
			JScrollPane scrollPane = (JScrollPane) tabbedPane.getComponentAt(i);
			Tab tab = (Tab) scrollPane.getViewport().getView();
			if (tab.isChanged()) {
				String message = String.format(flp.getString(CLOSING_WINDOW), tab.getSimpleName());
				int selected = JOptionPane
						.showConfirmDialog(JNotepadPP.this, message, INFORMATION, JOptionPane.YES_NO_CANCEL_OPTION);
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

	/**
	 * Getter for instance of {@link UpperCaseAction}.
	 * 
	 * @return instance of {@link UpperCaseAction}.
	 */
	public Action getUpperCaseAction() {
		return upperCaseAction;
	}

	/**
	 * Getter for instance of {@link LowerCaseAction}.
	 * 
	 * @return instance of {@link LowerCaseAction}.
	 */
	public Action getLowerCaseAction() {
		return lowerCaseAction;
	}

	/**
	 * Getter for instance of {@link ToggleCaseAction}.
	 * 
	 * @return instance of {@link ToggleCaseAction}.
	 */
	public Action getToggleCaseAction() {
		return toggleCaseAction;
	}
}