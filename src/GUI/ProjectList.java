package GUI;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import Entity.Project;
import MySQLDataAccess.ProjectAccessor;

public class ProjectList{
	
	private ArrayList<Project> projectList;
	private Table projectTable;
	private Composite composite;
	private boolean forTraining;
	
	public ProjectList(Composite comp, GridData data,boolean isTraining)
	{
		this.composite = comp;
		this.forTraining = isTraining;
		getProjectList();
		GridData titleGD = new GridData(GridData.FILL_HORIZONTAL);
		Label label = new Label(composite,SWT.MIN);
		if(isTraining)
			label.setText("Traning Scenario Set");
		else
			label.setText("Evaluation Scenario Set");
		label.setLayoutData(titleGD);
		label.setSize(50, 50);
		projectTable = new Table(this.composite, SWT.HORIZONTAL);
		projectTable.setLayoutData(data);
		this.initProjectTable();
		this.makeProjectTableContents();
	}
	
	private void initProjectTable() {
		projectTable.setHeaderVisible(true);
		TableColumn col_id = new TableColumn(projectTable,SWT.NULL);
		col_id.setText("ID");
		col_id.pack();
		col_id.setWidth(150);
		TableColumn col_name = new TableColumn(projectTable,SWT.NULL);
		col_name.setText("Project Name");
		col_name.pack();
		col_name.setWidth(150);
		TableColumn col_desc = new TableColumn(projectTable,SWT.NULL);
		col_desc.setText("Description");
		col_desc.pack();
		col_desc.setWidth(150);
		TableColumn col_date = new TableColumn(projectTable,SWT.NULL);
		col_date.setText("Create Date");
		col_date.pack();
		col_date.setWidth(100);
		TableColumn col_person = new TableColumn(projectTable,SWT.NULL);
		col_person.setText("Create Person");
		col_person.pack();
		col_person.setWidth(200);
		TableColumn col_btn = new TableColumn(projectTable,SWT.NULL);
		col_btn.setText("Start");
		col_btn.pack();
	}

	public void refresh()
	{
		this.projectTable.clearAll();
		this.projectTable.removeAll();
		this.projectList.clear();
		this.getProjectList();
		this.makeProjectTableContents();
	}
	
	private void getProjectList()
	{
		ProjectAccessor dp = new ProjectAccessor();
		projectList = dp.getProjectList(forTraining);
	}
	
	private void makeProjectTableContents()
	{
	//	projectTable = new Table(shell, SWT.HORIZONTAL);

		for(int i=0; i<projectList.size();i++)
		{
			TableItem item = new TableItem(projectTable,SWT.NONE);
			final String pid = projectList.get(i).getProjectId();
			final String pname = projectList.get(i).getProjectName();
			item.setText(0, pid);
			item.setText(1, pname);
			if (projectList.get(i).getDescription() != null)
				item.setText(2, projectList.get(i).getDescription());
			if (projectList.get(i).getCreateTime() != null)
				item.setText(3, projectList.get(i).getCreateTime().toString());
			if (projectList.get(i).getCreatePerson() != null)
				item.setText(4, projectList.get(i).getCreatePerson());
			Button btn_start = new Button(projectTable, SWT.PUSH);
			btn_start.setText("¢º");
			btn_start.pack();
			TableEditor editor = new TableEditor(projectTable);
			editor.grabHorizontal = true;
			editor.setEditor(btn_start, item, 5);
			btn_start.addSelectionListener(new SelectionListener(){
				public void widgetDefaultSelected(SelectionEvent arg0) {}

				public void widgetSelected(SelectionEvent arg0) {
					@SuppressWarnings("unused")
					UseCaseList UCL = new UseCaseList(pid, pname);
				}
			});
		}
		
		//GridData gd = new GridData(SWT.FILL, SWT.BOTTOM, true, false, 1, 1);
	//	gd.heightHint = 500;
	//	projectTable.setLayoutData(data);
	}

}
