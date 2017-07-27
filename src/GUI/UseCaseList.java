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
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import Entity.UseCase;
import MySQLDataAccess.UseCaseAccessor;

public class UseCaseList {

	protected Shell shell;
	private Table usecaseTable;
	private ArrayList<UseCase> UCList;
	private String projectID;

	public UseCaseList(String projectID, String projectName) 
	{
		this.projectID = projectID;
		Display display = Display.getDefault();
		
		shell = new Shell();
		shell.setSize(725, 650);
		shell.setText("Use Case List");
		
		//usecaseTable = new Table(this.shell, SWT.HORIZONTAL);
		
		this.getUseCaseList();
		this.initUseCaseTable(projectName);
		this.makeUseCaseTableContents();
		
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
		this.usecaseTable.clearAll();
		this.usecaseTable.removeAll();
		this.UCList.clear();
		this.getUseCaseList();
		this.makeUseCaseTableContents();
	}
	
	private void makeUseCaseTableContents() 
	{
		for(final UseCase uc : UCList)
		{
			TableItem item = new TableItem(usecaseTable,SWT.NONE);
			item.setText(0,uc.getUseCaseID());
			item.setText(1,uc.getUseCaseName());
			item.setText(2,uc.getUseCaseDescription());
			if(uc.getIncludedOf() != null)
				item.setText(3,uc.getIncludedOf());
			if(uc.getExtendsOf() != null)
				item.setText(4,uc.getExtendsOf());
			Button btn_start = new Button(usecaseTable,SWT.PUSH);
			//btn_start
			btn_start.setText("¢º");
			btn_start.pack();
			btn_start.addSelectionListener(new SelectionListener()
			{
				public void widgetSelected(SelectionEvent arg0)
				{
					new UseCaseEditor(uc,UCList);
				}
				public void widgetDefaultSelected(SelectionEvent arg0) {}
			});
			TableEditor editor = new TableEditor(usecaseTable);
			editor.grabHorizontal = true;
			editor.setEditor(btn_start, item, 5);
		}
		
	}

	private void getUseCaseList() 
	{
		UCList = new ArrayList<UseCase>();
		UseCaseAccessor UCA = new UseCaseAccessor();
		UCList = UCA.getUseCaseList(projectID);
	}

	private void initUseCaseTable(String projectName) 
	{
		//made by WindowBuilder
		Label lblProjectId = new Label(shell, SWT.NONE);
		lblProjectId.setBounds(21, 10, 70, 15);
		lblProjectId.setText("Project ID : ");
		
		Label label = new Label(shell, SWT.NONE);
		label.setText(projectID);
		label.setBounds(90, 10, 83, 15);
		
		Label lblProjectName = new Label(shell, SWT.NONE);
		lblProjectName.setBounds(191, 10, 83, 15);
		lblProjectName.setText("Project Name : ");
		
		Label label_1 = new Label(shell, SWT.NONE);
		label_1.setText(projectName);
		label_1.setBounds(280, 10, 291, 15);
		
		usecaseTable = new Table(shell, SWT.VERTICAL);
		usecaseTable.setBounds(21, 45, 665, 530);
		usecaseTable.setHeaderVisible(true);
		usecaseTable.setLinesVisible(true);
		
		TableColumn tblclmnUseCaseId = new TableColumn(usecaseTable, SWT.NONE);
		tblclmnUseCaseId.setWidth(82);
		tblclmnUseCaseId.setText("Use Case ID");
		
		TableColumn tblclmnUseCaseName = new TableColumn(usecaseTable, SWT.NONE);
		tblclmnUseCaseName.setWidth(153);
		tblclmnUseCaseName.setText("Use Case Name");
		
		TableColumn tblclmnUseCaseDescription = new TableColumn(usecaseTable, SWT.NONE);
		tblclmnUseCaseDescription.setWidth(247);
		tblclmnUseCaseDescription.setText("Use Case Description");
		
		TableColumn tblclmnIncluded = new TableColumn(usecaseTable, SWT.NONE);
		tblclmnIncluded.setWidth(68);
		tblclmnIncluded.setText("Included of ");
		
		TableColumn tblclmnExtends = new TableColumn(usecaseTable, SWT.NONE);
		tblclmnExtends.setWidth(71);
		tblclmnExtends.setText("Extends of");
		
		TableColumn tblclmnEdit = new TableColumn(usecaseTable, SWT.NONE);
		tblclmnEdit.setWidth(40);
		tblclmnEdit.setText("Edit");
		
		//menu
		Menu menu = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menu);
		MenuItem UsecaseMenuitem = new MenuItem(menu,SWT.CASCADE);
		UsecaseMenuitem.setText("New Use Case");
		
		/*Menu projectMenu = new Menu(shell,SWT.DROP_DOWN);
		ProjectMenuitem.setMenu(projectMenu);
		
		MenuItem newProjectMenuItem = new MenuItem(projectMenu,SWT.CASCADE);
		newProjectMenuItem.setText("New Project");*/
		UsecaseMenuitem.addSelectionListener(new SelectionListener()
		{
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}

			@Override
			public void widgetSelected(SelectionEvent arg0) 
			{
				String UCID = projectID+(UCList.size()+1);
				@SuppressWarnings("unused")
				UseCaseEditor pm = new UseCaseEditor(UCID,projectID, UCList);
				refresh();
				//t.open();
			}
		});
	}
}
