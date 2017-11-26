package mabufudyne.gbdesigner.gui;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.ToolItem;

import mabufudyne.gbdesigner.core.EventHandler;
import mabufudyne.gbdesigner.core.StoryPiece;

import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.TableColumn;

public class MainWindow {

	private static MainWindow instance = new MainWindow();
	protected Shell shell;
	private Text textTitle;
	private Text textStory;
	private Table tableStoryPieces;
	private Table tableChoices;
	private ToolItem tItemAddStoryPiece;
	private ToolItem tItemRemoveStoryPiece;
	private int itemNumber;

	/**
	 * Launch the application.
	 * @param args
	 */
	
	protected MainWindow() {
		this.itemNumber = 1;
	}
	
	public static MainWindow getInstance() {
		return instance;
	}
	
	public static void main(String[] args) {
		try {
			MainWindow.getInstance().open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		
		shell = new Shell();
		shell.setMinimumSize(new Point(700, 550));
		shell.setSize(450, 300);
		shell.setText("SWT Application");
		shell.setLayout(new GridLayout(1, false));
		
		ToolBar mainToolBar = new ToolBar(shell, SWT.FLAT | SWT.RIGHT);
		mainToolBar.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		
		ToolItem tItemNew = new ToolItem(mainToolBar, SWT.NONE);
		tItemNew.setText("New");
		
		ToolItem tItemQuickSave = new ToolItem(mainToolBar, SWT.NONE);
		tItemQuickSave.setText("Save");
		
		SashForm sashMain = new SashForm(shell, SWT.NONE);
		sashMain.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		SashForm sashFields = new SashForm(sashMain, SWT.VERTICAL);
		
		Composite cTitle = new Composite(sashFields, SWT.NONE);
		cTitle.setLayout(new GridLayout(1, false));
		
		Label lblTitle = new Label(cTitle, SWT.NONE);
		lblTitle.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		lblTitle.setText("Title");
		
		textTitle = new Text(cTitle, SWT.BORDER);
		textTitle.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1));
		
		Composite cStory = new Composite(sashFields, SWT.NONE);
		cStory.setLayout(new GridLayout(1, false));
		
		Label lblStory = new Label(cStory, SWT.NONE);
		lblStory.setText("Story");
		
		textStory = new Text(cStory, SWT.BORDER | SWT.MULTI);
		textStory.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		Composite cChoices = new Composite(sashFields, SWT.NONE);
		cChoices.setLayout(new GridLayout(1, false));
		
		Label lblNewLabel = new Label(cChoices, SWT.NONE);
		lblNewLabel.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		lblNewLabel.setText("Choices");
		
		tableChoices = new Table(cChoices, SWT.BORDER | SWT.FULL_SELECTION);
		tableChoices.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		tableChoices.setHeaderVisible(true);
		tableChoices.setLinesVisible(true);
		sashFields.setWeights(new int[] {1, 3, 3});
		
		Composite cViews = new Composite(sashMain, SWT.NONE);
		GridLayout gl_cViews = new GridLayout(2, false);
		gl_cViews.marginHeight = 0;
		gl_cViews.horizontalSpacing = 0;
		cViews.setLayout(gl_cViews);
		
		Composite cOverview = new Composite(cViews, SWT.NONE);
		cOverview.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		cOverview.setLayout(new GridLayout(1, false));
		
		Label lblOverview = new Label(cOverview, SWT.NONE);
		lblOverview.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 1, 1));
		lblOverview.setText("Overview");
		
		tableStoryPieces = new Table(cOverview, SWT.BORDER | SWT.FULL_SELECTION);
		tableStoryPieces.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				EventHandler.performUICheck();
			}
		});
		tableStoryPieces.setHeaderVisible(true);
		tableStoryPieces.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		tableStoryPieces.setLinesVisible(true);
		
		TableColumn tblclmnNewColumn = new TableColumn(tableStoryPieces, SWT.CENTER);
		tblclmnNewColumn.setWidth(30);
		tblclmnNewColumn.setText("#");
		
		TableColumn tblclmnStoryPieceTitle = new TableColumn(tableStoryPieces, SWT.CENTER);
		tblclmnStoryPieceTitle.setWidth(100);
		tblclmnStoryPieceTitle.setText("Title");
		
		ToolBar sideToolBar = new ToolBar(cViews, SWT.FLAT | SWT.RIGHT | SWT.VERTICAL);
		sideToolBar.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 1, 1));
		
		tItemAddStoryPiece = new ToolItem(sideToolBar, SWT.NONE);
		tItemAddStoryPiece.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				EventHandler.createNewStoryPiece();
			}
		});
		tItemAddStoryPiece.setToolTipText("Create a new Story Piece");
		tItemAddStoryPiece.setText("+");
		
		tItemRemoveStoryPiece = new ToolItem(sideToolBar, SWT.NONE);
		tItemRemoveStoryPiece.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				TableItem item = getSelectedTableItem(tableStoryPieces);
				if (item.getData() instanceof StoryPiece) {
					EventHandler.deleteStoryPiece((StoryPiece) item.getData());
				}
			}
		});
		tItemRemoveStoryPiece.setText("-");
		sashMain.setWeights(new int[] {2, 1});
		
		buttonCheck();

	}

	public void displayStoryPiece(StoryPiece sp) {
		TableItem item = new TableItem(tableStoryPieces, SWT.CENTER);
		item.setData(sp);
		// Column 0 - order, column 1 - StoryPiece title
		item.setText(0, String.valueOf(getItemNumber()));
		item.setText(1, sp.getTitle());
		
		incrementItemNumber();
	}
	
	private int getItemNumber() {
		return itemNumber;
	}
	
	private void incrementItemNumber() {
		itemNumber++;
	}
	
	private void decrementItemNumber() {
		itemNumber--;
	}

	private TableItem getSelectedTableItem(Table tableSource) {
		return tableSource.getSelection()[0];
	}
	
	/** Reorders StoryPiece TableItems in tableStoryPieces. 
	 * @argument sp The StoryPiece which has been removed
	 */
	public void reorderStoryPieces(StoryPiece sp) {
		int deletedItemNumber = 1;
		for (TableItem item : tableStoryPieces.getItems()) {
			if (item.getData() == sp) {
				deletedItemNumber = Integer.valueOf(item.getText(0));
				tableStoryPieces.remove(tableStoryPieces.indexOf(item));
				decrementItemNumber();
			}
		}
		
		for (TableItem item : tableStoryPieces.getItems()) {
			int tItemNumber = Integer.valueOf(item.getText(0));
			if (tItemNumber > deletedItemNumber) {
				tItemNumber--;
				item.setText(0,String.valueOf(tItemNumber));
			}
		}
	}
	
	public void buttonCheck() {
		if (tableStoryPieces.getItemCount() > 0 && tableStoryPieces.getSelection()[0] != null) {
			tItemRemoveStoryPiece.setEnabled(true);
		}
		else {
			tItemRemoveStoryPiece.setEnabled(false);
		}
	}
}


