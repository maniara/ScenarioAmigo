package GUI;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import Validator.ValidatorController;

public class ValidateController {
	protected Shell shell;
	
	public ValidateController()
	{
		shell = new Shell();
		shell.setSize(500, 200);
		shell.setText("Validation Controller");

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

	private void drawControls() {
		// TODO Auto-generated method stub
		Label targetLabel = new Label(shell, SWT.NONE);
		targetLabel.setBounds(10, 10, 150, 15);
		targetLabel.setText("Choose Target Project ID");
		
		Text targetProjectText = new Text(shell, SWT.NONE);
		targetProjectText.setBounds(170,10,50,15);
		targetProjectText.setText("SKP");
		
		Button targetProjectButton = new Button(shell, SWT.NONE);
		targetProjectButton.setText("One Sentence Validation");
		targetProjectButton.setBounds(250, 7, 200, 25);
		targetProjectButton.addSelectionListener(new SelectionListener(){

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				ValidatorController vc =new ValidatorController();
				vc.doSentenceValidation(targetProjectText.getText(),false, false);
			}});
		
	}

}
