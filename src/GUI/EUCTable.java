package GUI;

import java.util.ArrayList;
import java.util.Collections;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import Entity.Sentence;

public class EUCTable
{
	private Table table;
	boolean isPattern;
	private ArrayList<Button> repeatableList = null;
	private ArrayList<Button> optionalList;
	private ArrayList<TableItem> tableItemList;
	
	public void setBounds(int x, int y, int width, int height)
	{
		table.setBounds(x, y, width, height);
	}
	
	
	
	public ArrayList<TableItem> getTableItemList() {
		return tableItemList;
	}



	public void addLine()
	{
		//System.out.println(this.isPattern);
		TableItem item = new TableItem(table, SWT.NONE);
		this.tableItemList.add(item);
		
		if(this.isPattern)
		{
			Button btn_Repeatable = new Button(table,SWT.CHECK|SWT.CENTER);
			Button btn_Optional = new Button(table,SWT.CHECK|SWT.CENTER);
			
			this.repeatableList.add(btn_Repeatable);
			this.optionalList.add(btn_Optional);
		
			TableEditor editor = new TableEditor(table);
			TableEditor editor2 = new TableEditor(table);
			editor.grabHorizontal = true;
			editor2.grabHorizontal = true;
			editor.horizontalAlignment = SWT.CENTER;
			editor2.horizontalAlignment = SWT.CENTER;
			
			editor.setEditor(btn_Repeatable, item, 4);
			editor2.setEditor(btn_Optional, item, 5);
		}
	}
	
	public Table getTable()
	{
		return this.table;
	}
	
	public ArrayList<Button> getRepeatableList()
	{
		return this.repeatableList;
	}
	
	public ArrayList<Button> getOptionalList()
	{
		return this.optionalList;
	}
	
	private void addListener()
	{
		final TableEditor editor = new TableEditor(table);
		editor.horizontalAlignment = SWT.LEFT;
		editor.grabHorizontal = true;
		
		table.addListener(SWT.MouseDown, new Listener()
		{
		      public void handleEvent(Event event) 
		      {
		        Rectangle clientArea = table.getClientArea();
		        Point pt = new Point(event.x, event.y);
		        int index = table.getTopIndex();
		        while (index < table.getItemCount()) 
		        {
		          boolean visible = false;
		          final TableItem item = table.getItem(index);
		          for (int i = 0; i < table.getColumnCount(); i++) 
		          {
		            Rectangle rect = item.getBounds(i);
		            if (rect.contains(pt)) 
		            {
		              final int column = i;
		              final Text text = new Text(table, SWT.NONE);
		              Listener textListener = new Listener() 
		              {
		                public void handleEvent(final Event e) 
		                {
		                  switch (e.type) {
		                  case SWT.FocusOut:
		                    item.setText(column, text.getText());
		                    text.dispose();
		                    break;
		                  case SWT.Traverse:
		                    switch (e.detail) {
		                    case SWT.TRAVERSE_RETURN:
		                      item
		                          .setText(column, text
		                              .getText());
		                    // FALL THROUGH
		                    case SWT.TRAVERSE_ESCAPE:
		                      text.dispose();
		                      e.doit = false;
		                    }
		                    break;
		                  }
		                }
		              };
		              text.addListener(SWT.FocusOut, textListener);
		              text.addListener(SWT.Traverse, textListener);
		              editor.setEditor(text, item, i);
		              text.setText(item.getText(i));
		              text.selectAll();
		              text.setFocus();
		              return;
		            }
		            if (!visible && rect.intersects(clientArea)) {
		              visible = true;
		            }
		          }
		          if (!visible)
		            return;
		          index++;
		        }
		      }

		}
		);
		
	}
	
	private void initTable(Composite comp)
	{
		table = new Table(comp, SWT.BORDER | SWT.FULL_SELECTION | SWT.V_SCROLL);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		
		TableColumn tblclmnUINum = new TableColumn(table, SWT.NONE);
		tblclmnUINum.setWidth(35);
		tblclmnUINum.setText("Seq");
				
		TableColumn tblclmnUserIntention = new TableColumn(table, SWT.NONE);
		tblclmnUserIntention.setWidth(140);
		tblclmnUserIntention.setText("User");
		
		TableColumn tblclmnSRNum = new TableColumn(table, SWT.NONE);
		tblclmnSRNum.setWidth(35);
		tblclmnSRNum.setText("Seq");
		
		TableColumn tblclmnSystemResponse = new TableColumn(table, SWT.NONE);
		tblclmnSystemResponse.setWidth(140);
		tblclmnSystemResponse.setText("System");
		
		if(this.isPattern)
		{
			tblclmnUserIntention.setWidth(140);
			tblclmnSystemResponse.setWidth(140);
			
			TableColumn tblclmIsPepeatable= new TableColumn(table, SWT.CHECK);
			tblclmIsPepeatable.setWidth(70);
			tblclmIsPepeatable.setText("Repeatable");
			
			TableColumn tblclmIsOptional= new TableColumn(table, SWT.CHECK);
			tblclmIsOptional.setWidth(70);
			tblclmIsOptional.setText("Optional");
		}
		else
		{
			tblclmnUserIntention.setWidth(140+70);
			tblclmnSystemResponse.setWidth(140+70);
		}
	}
	
	public EUCTable(Composite comp, boolean isPattern) 
	{
		this.isPattern = isPattern;
		this.tableItemList = new ArrayList<TableItem>();
		if(this.isPattern)
		{
			this.repeatableList = new ArrayList<Button>();
			this.optionalList = new ArrayList<Button>();
		}
		initTable(comp);
		
	    for (int i = 0; i < 10; i++) 
	    {
			this.addLine();
	    }
		addListener();
	}

	public EUCTable(Composite comp, ArrayList<Sentence> sentenceList, boolean isPattern) 
	{
		this.isPattern = isPattern;
		this.tableItemList = new ArrayList<TableItem>();
		initTable(comp);
		//System.out.println(sentenceList.size());
		Collections.sort(sentenceList);
		
		if(this.isPattern)
		{				
			this.repeatableList = new ArrayList<Button>();
			this.optionalList = new ArrayList<Button>();
		}
		
		for(Sentence s : sentenceList)
		{
			TableItem item = new TableItem(table, SWT.NONE);
			if(s.getSentenceType() == 'u')
			{
				item.setText(0, s.getSentenceOrder());
				item.setText(1, s.getSentenceContents());
			}
			
			else if(s.getSentenceType() == 's')
			{
				item.setText(2, s.getSentenceOrder());
				item.setText(3, s.getSentenceContents());
			}
			
			if(this.isPattern)
			{
				Button btn_Repeatable = new Button(table,SWT.CHECK|SWT.CENTER);
				Button btn_Optional = new Button(table,SWT.CHECK|SWT.CENTER);
				
				this.repeatableList.add(btn_Repeatable);
				this.optionalList.add(btn_Optional);
				
				TableEditor editor = new TableEditor(table);
				TableEditor editor2 = new TableEditor(table);
				editor.grabHorizontal = true;
				editor2.grabHorizontal = true;
				
				editor.setEditor(btn_Repeatable, item, 4);
				editor2.setEditor(btn_Optional, item, 5);
				
				//System.out.println(s.isRepeatable());
				
				if(s.isRepeatable())
				{
					btn_Repeatable.setSelection(true);
				}
				
				if(s.isOptional())
				{
					btn_Optional.setSelection(true);
				}
			}
		}
		addListener();
	}

}
