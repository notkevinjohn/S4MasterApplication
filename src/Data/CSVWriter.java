package Data;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class CSVWriter 
{
	private FileWriter outFile;
	private PrintWriter out;
	
	public void WriteCSV (String filePath, ArrayList<PayloadDataPoint> dataPoints)
	{
		filePath = filePath.replace("\\", "/");
		filePath += ".csv";				
		try 
		{
			outFile = new FileWriter(filePath);
			out = new PrintWriter(outFile);
			
			String headers = "Time, Lon, Lat, Alt";
			for(int i=0; i<dataPoints.get(0).SensorData.size(); i++)
			{
				headers += ", "+dataPoints.get(0).SensorData.get(i).Key;
			}
			
			out.println(headers);
			for(int i=0; i<dataPoints.size(); i++)
			{
				writePayloadDataPointToLine(dataPoints.get(i));
			}
			out.close();
		} 
		catch (IOException e){e.printStackTrace();}
	}
	private void writePayloadDataPointToLine(PayloadDataPoint point)
	{
		String line = point.GPS_TimeSpan +", "+point.GPS_Lon+", "+point.GPS_Lat+", "+point.GPS_Alt;
		for(int i=0; i<point.SensorData.size(); i++)
		{
			line += ", "+point.SensorData.get(i).Value;
		}
		out.println(line);
	}
}
