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
import MySQLDataAccess.FlowAccessor;
import MySQLDataAccess.PatternAccessor;
import MySQLDataAccess.SentenceAccessor;

public class MainVerbEditor {

	protected Shell shell;
	private Sentence sentence;
	private Text verbText;
	
	///Edit mode
	public MainVerbEditor(Sentence s)
	{
		this.sentence = s;
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
		shell.setSize(597, 176);
		shell.setText("Main Verb Editor");
		
		Label lblUseCaseName = new Label(shell, SWT.NONE);
		lblUseCaseName.setBounds(10, 13, 94, 15);
		lblUseCaseName.setText("Sentence");
		
		Button btnConfirm = new Button(shell, SWT.NONE);
		btnConfirm.setBounds(424, 112, 76, 25);
		btnConfirm.setText("Confirm");
		btnConfirm.addSelectionListener(new SelectionListener(){
			public void widgetDefaultSelected(SelectionEvent arg0) {}
			public void widgetSelected(SelectionEvent arg0) {
				SentenceAccessor SA = new SentenceAccessor();
				sentence.setVerb(verbText.getText());
				SA.updateVerb(sentence);
				shell.close();
			}});
		
		Button btnCancel = new Button(shell, SWT.NONE);
		btnCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				shell.close();
			}
		});
		btnCancel.setBounds(505, 112, 76, 25);
		btnCancel.setText("Cancle");
		
		Label lblSentenceContentsLabel = new Label(shell, SWT.NONE);
		lblSentenceContentsLabel.setBounds(99, 13, 472, 54);
		lblSentenceContentsLabel.setText(" "+sentence.getSentenceContents());
		
		Label lblMainVerb = new Label(shell, SWT.NONE);
		lblMainVerb.setBounds(10, 73, 56, 15);
		lblMainVerb.setText("Main Verb");
		
		verbText = new Text(shell, SWT.BORDER);
		verbText.setBounds(99, 67, 202, 21);
		if(sentence.getVerb() != null)
			verbText.setText(sentence.getVerb());
	}
}
