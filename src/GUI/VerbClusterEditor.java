package GUI;

import java.util.ArrayList;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;

import Entity.Flow;
import Entity.Pattern;
import Entity.Sentence;
import Entity.VerbCluster;
import MySQLDataAccess.DictionaryAccessor;
import MySQLDataAccess.FlowAccessor;
import MySQLDataAccess.PatternAccessor;
import MySQLDataAccess.SentenceAccessor;
import MySQLDataAccess.VerbClusterAccessor;
import org.eclipse.swt.widgets.Combo;

public class VerbClusterEditor {

	protected Shell shell;
	private VerbCluster cluster;
	private Text verbText;
	private boolean isEditMode; 
	private Text representivesText;
	private Combo combo;
	private VerbClusterList pView;
	
	///Edit mode
	public VerbClusterEditor(VerbCluster vc, VerbClusterList parent)
	{
		pView = parent;
		this.cluster = vc;
		this.isEditMode = true;
		openDisplay();
	}
	
	/**
	 * @wbp.parser.constructor
	 */
	public VerbClusterEditor(VerbClusterList parent)
	{
		pView = parent;
		this.isEditMode = false;
		openDisplay();
	}
	
	protected void finalize()
	{
		
	}
	
	private void openDisplay()
	{
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

	protected void drawControls() {
		shell = new Shell();
		shell.setSize(597, 226);
		shell.setText("Dictionary Editor");
		
		Label lblUseCaseName = new Label(shell, SWT.NONE);
		lblUseCaseName.setBounds(10, 13, 111, 15);
		lblUseCaseName.setText("Representives Verb");
		
		Button btnConfirm = new Button(shell, SWT.NONE);
		btnConfirm.setBounds(425, 152, 76, 25);
		btnConfirm.setText("Confirm");
		btnConfirm.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				DictionaryAccessor vca = new DictionaryAccessor();
				if(isEditMode)
				{
					vca.deleteDictionaryItem(cluster.getRepresentives());
				}
				String type = "";
				if(combo.getSelectionIndex() == 0)
					type = "u";
				else
					type="s";
				
				vca.addDictionaryItem(representivesText.getText(), verbText.getText(), type);
				
				pView.refresh();
				shell.close();
			}
		});
		
		Button btnCancel = new Button(shell, SWT.NONE);
		btnCancel.addSelectionListener(new SelectionListener(){
			public void widgetDefaultSelected(SelectionEvent arg0) {}
			public void widgetSelected(SelectionEvent arg0) {

				shell.close();
			}});
		btnCancel.setBounds(505, 152, 76, 25);
		btnCancel.setText("Cancle");
		
		Label lblMainVerb = new Label(shell, SWT.NONE);
		lblMainVerb.setBounds(10, 47, 56, 15);
		lblMainVerb.setText("Verbs");
		
		verbText = new Text(shell, SWT.BORDER);
		verbText.setBounds(128, 44, 443, 53);
		
		representivesText = new Text(shell, SWT.BORDER);
		representivesText.setBounds(128, 13, 118, 21);
		
		if(this.isEditMode)
		{
			representivesText.setText(this.cluster.getRepresentives());
			verbText.setText(this.cluster.getVerbs());
		}
		
		Label lblEe = new Label(shell, SWT.NONE);
		lblEe.setBounds(128, 103, 443, 15);
		lblEe.setText("Delimeter : \";\" (ex : check;validate;...)");
		
		Label lblSubjectType = new Label(shell, SWT.NONE);
		lblSubjectType.setText("Subject Type");
		lblSubjectType.setBounds(10, 127, 111, 15);
		
		combo = new Combo(shell, SWT.NONE);
		combo.setBounds(128, 124, 88, 23);
		combo.add("User");
		combo.add("System");
		if(this.isEditMode)
		{
			if(this.cluster.getSubjectType().equals("u"))
				combo.select(0);
			else if(this.cluster.getSubjectType().equals("s"))
				combo.select(1);
		}
		else 
			combo.select(0);
	}
}
