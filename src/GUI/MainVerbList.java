package GUI;

import java.util.ArrayList;

import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import Entity.Pattern;
import Entity.Sentence;
import Entity.UseCase;
import MySQLDataAccess.PatternAccessor;
import MySQLDataAccess.SentenceAccessor;

public class MainVerbList {

	protected Shell shell;
	private Table verbTable;
	private ArrayList<Sentence> sentenceList;

	public MainVerbList() 
	{
		Display display = Display.getDefault();
		
		shell = new Shell();
		shell.setSize(653, 625);
		shell.setText("Main Verb List");
		
		//usecaseTable = new Table(this.shell, SWT.HORIZONTAL);
		
		this.getPatternList();
		this.initPatternTable();
		this.makePatternTableContents();
		
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
	
	public MainVerbList(UseCase usecase) {
		Display display = Display.getDefault();
		
		shell = new Shell();
		shell.setSize(653, 625);
		shell.setText("Main Verb List");
		this.sentenceList = usecase.getBasicFlowSentences();

		this.initPatternTable();
		this.makePatternTableContents();
		
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	public void refresh()
	{
		this.verbTable.clearAll();
		this.verbTable.removeAll();
		this.sentenceList.clear();
		this.getPatternList();
		this.makePatternTableContents();
	}
	
	private void makePatternTableContents() 
	{
		for(final Sentence s : sentenceList)
		{
			TableItem item = new TableItem(verbTable,SWT.NONE);
			item.setText(0,s.getSentenceContents());
			String verb = s.getVerb();
			if (s.getVerb() == null)
				verb = "";
			
			item.setText(1,verb);
			Button btn_start = new Button(verbTable,SWT.PUSH);
			//btn_start
			btn_start.setText("¢º");
			btn_start.pack();
			btn_start.addSelectionListener(new SelectionListener()
			{
				public void widgetSelected(SelectionEvent arg0)
				{
					new MainVerbEditor(s);
				}
				public void widgetDefaultSelected(SelectionEvent arg0) {}
			});
			TableEditor editor = new TableEditor(verbTable);
			editor.grabHorizontal = true;
			editor.setEditor(btn_start, item, 2);
		}
		
	}

	private void getPatternList() 
	{
		SentenceAccessor PA = new SentenceAccessor();
		sentenceList = PA.getAllTrainingSentenceList();
	}

	private void initPatternTable() 
	{
		verbTable = new Table(shell, SWT.VERTICAL);
		verbTable.setBounds(21, 20, 594, 530);//45
		verbTable.setHeaderVisible(true);
		verbTable.setLinesVisible(true);
		
		TableColumn tblclmnUseCaseId = new TableColumn(verbTable, SWT.NONE);
		tblclmnUseCaseId.setWidth(427);
		tblclmnUseCaseId.setText("Sentence");
		
		TableColumn tblclmnUseCaseName = new TableColumn(verbTable, SWT.NONE);
		tblclmnUseCaseName.setWidth(98);
		tblclmnUseCaseName.setText("Main verb");
		
		TableColumn tblclmnEdit = new TableColumn(verbTable, SWT.NONE);
		tblclmnEdit.setWidth(40);
		tblclmnEdit.setText("Edit");
	}
}
