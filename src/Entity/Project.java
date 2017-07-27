package Entity;

import java.util.Date;

public class Project {
	private String ProjectID;
	private String ProjectName;
	private Date CreateTime;
	private String CreatePerson;
	private String Description;
	
		public Project(String id, String name, Date createTime, String createPerson, String desc)
	{
		this.ProjectID = id;
		this.ProjectName = name;
		this.CreateTime = createTime;
		this.CreatePerson = createPerson;
		this.Description = desc;
	}
	
	public String getProjectId()
	{
		return this.ProjectID;
	}
	
	public String getProjectName()
	{
		return this.ProjectName;
	}
	
	public Date getCreateTime()
	{
		return this.CreateTime;
	}
	
	public String getCreatePerson()
	{
		return this.CreatePerson;
	}
	
	public String getDescription()
	{
		return this.Description;
	}
	
	public void saveProject()
	{
		
	}
}
