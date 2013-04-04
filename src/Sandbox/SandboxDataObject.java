package Sandbox;

import java.io.Serializable;
import java.sql.Date;

public class SandboxDataObject implements Serializable 
{
	private static final long serialVersionUID = 1L;

	private String name;
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	
	private Date date;
	public Date getDate()
	{
		return date;
	}
	public void setDate(Date date)
	{
		this.date = date;
	}

}
