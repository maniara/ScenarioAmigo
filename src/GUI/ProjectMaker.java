package GUI;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;

import MySQLDataAccess.ProjectAccessor;

public class ProjectMaker {

	protected Shell shell;
	private boolean forTrain;
	private Text txtProjectId;
	private Text txtProjectName;
	private Text txtProjectDesc;
	private Text txtAuthor;
	
	public ProjectMaker(boolean forTrain)
	{
		this.forTrain = forTrain;
		Display display = Display.getDefault();
		drawControls();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	public void drawControls() {
		shell = new Shell();
		shell.setSize(430, 340);
		shell.setText("Project Maker");
		
		Label lblProjectId = new Label(shell, SWT.NONE);
		lblProjectId.setBounds(10, 10, 56, 15);
		lblProjectId.setText("Project ID");
		
		txtProjectId = new Text(shell, SWT.BORDER);
		txtProjectId.setBounds(132, 7, 102, 25);
		txtProjectId.setTextLimit(10);
		
		Label lblProjectName = new Label(shell, SWT.NONE);
		lblProjectName.setBounds(10, 42, 83, 15);
		lblProjectName.setText("Project Name");
		
		txtProjectName = new Text(shell, SWT.BORDER);
		txtProjectName.setBounds(132, 39, 270, 25);
		txtProjectName.setTextLimit(30);
		
		Label lblProjectDescription = new Label(shell, SWT.NONE);
		lblProjectDescription.setBounds(10, 73, 102, 15);
		lblProjectDescription.setText("Project Description");

		txtProjectDesc = new Text(shell, SWT.BORDER|SWT.MULTI);
		txtProjectDesc.setBounds(132, 70, 270, 162);
		
		Label lblAuthor = new Label(shell, SWT.NONE);
		lblAuthor.setBounds(10, 241, 56, 15);
		lblAuthor.setText("Author");
		
		txtAuthor = new Text(shell, SWT.BORDER);
		txtAuthor.setBounds(132, 238, 162, 25);
		txtAuthor.setTextLimit(30);
		
		Button btnCommit = new Button(shell,SWT.PUSH);
		btnCommit.setText("Confirm");
		btnCommit.setBounds(300, 270, 40, 25);
		btnCommit.pack();
		btnCommit.addSelectionListener(new SelectionListener(){
			public void widgetDefaultSelected(SelectionEvent arg0) {			}
			public void widgetSelected(SelectionEvent arg0) {
				ProjectAccessor pa = new ProjectAccessor();
				pa.addProject(txtProjectId.getText(), txtProjectName.getText(), txtProjectDesc.getText(), txtAuthor.getText(), forTrain);
				shell.close();
			}
		});
		
		Button btnCancle = new Button(shell,SWT.PUSH);
		btnCancle.setText("Cancle");
		btnCancle.setBounds(355, 270, 40, 25);
		btnCancle.pack();
		btnCancle.addSelectionListener(new SelectionListener(){
			public void widgetDefaultSelected(SelectionEvent arg0) {}
			public void widgetSelected(SelectionEvent arg0) {
				shell.close();
			}
		});
	}
}
