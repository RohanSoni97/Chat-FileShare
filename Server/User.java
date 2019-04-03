import java.util.*;
import java.io.*;
class UsersEmployeeBean 
{
	private String name;
	private String password;
	public String toString()
	{
	return this.name+" "+this.password;
	}
	public String getName()
	{
	return this.name;
	}
	public String getPassword()
	{
	return this.password;
	}
	public void setName(String name)
	{
	 this.name=name;
	}
	public void setPassword()
	{
		this.password=password;
	}
}