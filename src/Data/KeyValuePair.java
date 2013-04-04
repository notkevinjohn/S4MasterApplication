package Data;

import java.io.Serializable;

public class KeyValuePair implements Serializable 
{
	private static final long serialVersionUID = 1L;
	
	public String Key;
	public String Value;
	
	public KeyValuePair(String key, String value)
	{
		this.Key = key;
		this.Value = value;
	}
	
}
