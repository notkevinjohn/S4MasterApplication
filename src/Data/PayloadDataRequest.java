package Data;

import java.io.Serializable;
import java.sql.Timestamp;

public class PayloadDataRequest implements Serializable {

	
	private static final long serialVersionUID = 1L;	
	
	public String payloadName;	
	public Timestamp timestamp;

	public PayloadDataRequest(String payloadName, Timestamp timestamp) 
	{
		this.payloadName = payloadName;
		this.timestamp = timestamp;
	}

}
