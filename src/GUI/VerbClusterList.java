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
import Entity.VerbCluster;
import MySQLDataAccess.DictionaryAccessor;
import MySQLDataAccess.PatternAccessor;
import MySQLDataAccess.SentenceAccessor;
import MySQLDataAccess.VerbClusterAccessor;

public class VerbClusterList {

	protected Shell shell;
	private Table verbTable;
	private ArrayList<VerbCluster> verbClusterList;
	private boolean isCustomerMode;
	public VerbClusterList thisView;
	ArrayList<Button> editButtonList;

	public VerbClusterList(boolean isCustomer) 
	{
		thisView = this;
		editButtonList = new ArrayList<Button>(); 
		isCustomerMode = isCustomer;
		Display display = Display.getDefault();
		
		shell = new Shell();
		shell.setSize(653, 625);
		shell.setText("Verb Cluster List");
		
		//usecaseTable = new Table(this.shell, SWT.HORIZONTAL);
		this.initPatternTable();
		this.getClusterList();
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
		for(Button b: this.editButtonList)
		{
			b.dispose();
		}
		editButtonList.clear();
		//verbTable = null;
		this.verbTable.clearAll();
		this.verbTable.removeAll();
		this.verbClusterList.clear();
		this.getClusterList();
		this.makePatternTableContents();
	}
	
	private void getClusterList()
	{
		if(isCustomerMode){
			DictionaryAccessor da = new DictionaryAccessor(); 
			this.verbClusterList = da.getDictionary();
		}
			
		else{
			VerbClusterAccessor vca = new VerbClusterAccessor();
			this.verbClusterList = vca.getAllClusters();
		}
			
	}
	
	private void makePatternTableContents() 
	{
		for(final VerbCluster vc : verbClusterList)
		{
			TableItem item = new TableItem(verbTable,SWT.NONE);
			item.setText(0,vc.getSubjectType());
			item.setText(1,vc.getRepresentives());
			String verbs = vc.getVerbs();
			if (vc.getVerbs() == null)
				verbs = "";
			item.setText(2,verbs);
			
			String edited = "";
			boolean isEdited = vc.isCustom(); 
			if(isEdited)
				edited = "O";
			else
				edited = "X";
			item.setText(3,edited);
			
			if(this.isCustomerMode){
				Button btn_start = null;
				btn_start = new Button(verbTable,SWT.PUSH);
				editButtonList.add(btn_start);
			//btn_start
				btn_start.setText("¢º");
				btn_start.pack();
				btn_start.addSelectionListener(new SelectionListener()
				{
					public void widgetSelected(SelectionEvent arg0)
				{	
					new VerbClusterEditor(vc,thisView);
				}
					public void widgetDefaultSelected(SelectionEvent arg0) {}
				});
				TableEditor editor = new TableEditor(verbTable);
				editor.grabHorizontal = true;
				editor.setEditor(btn_start, item, 4);
			}
			else{
				TableEditor editor = new TableEditor(verbTable);
				editor.grabHorizontal = true;
			}
		}
		
	}

	private void initPatternTable() 
	{
		verbTable = new Table(shell, SWT.VERTICAL);
		verbTable.setBounds(10, 41, 617, 530);//45
		verbTable.setHeaderVisible(true);
		verbTable.setLinesVisible(true);
		
		TableColumn tblclmnType = new TableColumn(verbTable, SWT.NONE);
		tblclmnType.setWidth(87);
		tblclmnType.setText("Subject Type");
		
		TableColumn tblclmnUseCaseId = new TableColumn(verbTable, SWT.NONE);
		tblclmnUseCaseId.setWidth(111);
		tblclmnUseCaseId.setText("Pepresentive Verb");
		
		TableColumn tblclmnUseCaseName = new TableColumn(verbTable, SWT.NONE);
		tblclmnUseCaseName.setWidth(302);
		tblclmnUseCaseName.setText("VerbList");
		
		TableColumn tblclmnEdited = new TableColumn(verbTable, SWT.NONE);
		tblclmnEdited.setWidth(47);
		tblclmnEdited.setText("Custom");
		
		TableColumn tblclmnEdit = new TableColumn(verbTable, SWT.NONE);
		tblclmnEdit.setWidth(40);
		tblclmnEdit.setText("Edit");
		
		if(this.isCustomerMode){
		Button btnNewButton = new Button(shell, SWT.NONE);
		btnNewButton.setBounds(10, 10, 150, 25);
		btnNewButton.setText("New Verb Cluster");
		btnNewButton.addSelectionListener(new SelectionListener()
		{
			public void widgetSelected(SelectionEvent arg0)
			{
				new VerbClusterEditor(thisView);
			}
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
		}
	}
}
