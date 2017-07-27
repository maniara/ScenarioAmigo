package GUI;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import Entity.Flow;

public class AlternativeFlowTabItem 
{
	private String flowID;  
	private CTabItem tabItem;
	private Text txt_startOrder;
	private Text txt_stasrtCondition;
	private EUCTable AFTable;
	Composite composite;
	
	public AlternativeFlowTabItem(Composite comp, boolean isPattern)
	{
		initTabItem(comp);
		
		AFTable = new EUCTable(composite,isPattern);
		AFTable.setBounds(5, 72, 513, 166);
	}
	
	public String getFlowID()
	{
		return this.flowID;
	}
	
	public void setTitle()
	{
		tabItem.setText(txt_startOrder.getText());
	}
	
	public AlternativeFlowTabItem(Composite comp, Flow flow, boolean isPattern)
	{
		this.flowID = flow.getFlowID();
		initTabItem(comp);
		
		tabItem.setText(flow.getStartOrder());
		txt_startOrder.setText(flow.getStartOrder());
		txt_stasrtCondition.setText(flow.getStartCondition());
		
		AFTable = new EUCTable(composite,flow.getSentenceList(),isPattern);
		AFTable.setBounds(5, 72, 513, 166);
	}
	
	private void initTabItem(Composite comp)
	{
		tabItem = new CTabItem((CTabFolder) comp, SWT.NONE);
		tabItem.setText("New AF");
		
		composite = new Composite((CTabFolder) comp, SWT.NONE);
		tabItem.setControl(composite);
		
		Label lblStartOrder = new Label(composite, SWT.NONE);
		lblStartOrder.setBounds(10, 13, 66, 15);
		lblStartOrder.setText("Start Order");
		
		Label lblStartCondition = new Label(composite, SWT.NONE);
		lblStartCondition.setBounds(10, 41, 82, 15);
		lblStartCondition.setText("Start Condition");
		
		txt_startOrder = new Text(composite, SWT.BORDER);
		txt_startOrder.setBounds(101, 10, 73, 21);
		
		txt_stasrtCondition = new Text(composite, SWT.BORDER);
		txt_stasrtCondition.setBounds(101, 38, 407, 21);
		
	}

	public String getStartOrder()
	{
		return this.txt_startOrder.getText();
	}
	
	public String getStartCondition()
	{
		return this.txt_stasrtCondition.getText();
	}
	
	public EUCTable getAlternativeFlowTable()
	{
		return this.AFTable;
	}
}
