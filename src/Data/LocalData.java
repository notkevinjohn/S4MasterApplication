package Data;

public class LocalData {	
	
	private static LocalData instance;	
	public static LocalData getInstance ()
	{
		if(instance==null)
		{
			instance = new LocalData();
		}
		return instance;
	}	
	

}
