package EntryUI;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

//import GUI.PatternList;
import GUI.ProjectList;
import GUI.ProjectMaker;
import GUI.ValidateController;
import GUI.PatternGenerator;

public class Sofar {
	
	@SuppressWarnings("unused")
	//private static ProjectList PList;
	
	public static void main(String args[])
	{
		final Display display = new Display();
		final Shell shell = new Shell(display);
		shell.setText("ScenarioAmigo");
		//shell.setMaximized(true);
		GridLayout gl = new GridLayout(1,false);
		shell.setLayout(gl);
		
		GridData data = new GridData(GridData.FILL_BOTH);
		data.horizontalAlignment = GridData.FILL;

		
		//Project list
		final ProjectList TargetProjectList = new ProjectList(shell,data,false);
		
		//Project list
		final ProjectList TrainProjectList = new ProjectList(shell,data,true);
		
		//menu
		Menu menu = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menu);
		MenuItem ProjectMenuitem = new MenuItem(menu,SWT.CASCADE);
		ProjectMenuitem.setText("New Evaluation Scenario Set");

		ProjectMenuitem.addSelectionListener(new SelectionListener()
		{
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}

			@Override
			public void widgetSelected(SelectionEvent arg0) 
			{
				@SuppressWarnings("unused")
				ProjectMaker pm = new ProjectMaker(false);
				TargetProjectList.refresh();
				//t.open();
			}
		});
		
		/*MenuItem PatternMenuitem = new MenuItem(menu,SWT.CASCADE);
		PatternMenuitem.setText("Pattern Custom List");
		
		PatternMenuitem.addSelectionListener(new SelectionListener()
		{
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}

			@Override
			public void widgetSelected(SelectionEvent arg0) 
			{
				@SuppressWarnings("unused")
				PatternList pm = new PatternList();
			}
		});*/
		
		MenuItem TrainingProjectMenuitem = new MenuItem(menu,SWT.CASCADE);
		TrainingProjectMenuitem.setText("New Training Senario Set");

		TrainingProjectMenuitem.addSelectionListener(new SelectionListener()
		{
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}

			@Override
			public void widgetSelected(SelectionEvent arg0) 
			{
				@SuppressWarnings("unused")
				ProjectMaker pm = new ProjectMaker(true);
				TrainProjectList.refresh();
				//t.open();
			}
		});
		
		MenuItem VerbClusteringMenuitem = new MenuItem(menu,SWT.CASCADE);
		VerbClusteringMenuitem.setText("Scenario Patterns");

		VerbClusteringMenuitem.addSelectionListener(new SelectionListener()
		{
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}

			@Override
			public void widgetSelected(SelectionEvent arg0) 
			{
				new PatternGenerator();

			}
		});
		
		MenuItem ValidationMenuitem = new MenuItem(menu,SWT.CASCADE);
		ValidationMenuitem.setText("Evaluation");

		ValidationMenuitem.addSelectionListener(new SelectionListener()
		{
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}

			@Override
			public void widgetSelected(SelectionEvent arg0) 
			{
				new ValidateController();

			}
		});
		
		shell.open();
		while(!shell.isDisposed())
		{
			if(!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}
}
