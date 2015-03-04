package Server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import Data.PayloadDataPoint;
import Data.TextParser;


public class PayloadUDP_Socket extends Thread 
{
	
	private DatagramSocket payloadSocket;
	private PayloadDataPointsManager partialDataPoints;
	
	public PayloadUDP_Socket(int port) throws SocketException
	{
		payloadSocket = new DatagramSocket(port);
		partialDataPoints = new PayloadDataPointsManager();
		ServerConsole.getInstance().printSystemMessage("New UDP Socket Created on port: "+port);
	}
	
	public void run()
	{	
		while(true)
		{			
			byte[] buf = new byte[230];
			DatagramPacket packet = new DatagramPacket(buf, buf.length);
			try 
			{
				payloadSocket.receive(packet);			
			} 
			catch (IOException e) 
			{
				ServerConsole.getInstance().printSystemMessage(e.toString());
		    }		
			String sentence = new String(packet.getData());	
			ServerConsole.getInstance().printSystemMessage(packet.getAddress()+":"+sentence); 
			resolveSentence(sentence);
			try { Thread.sleep(100); } catch(InterruptedException e) { }
		}
	}
	
	public void resolveSentence(String sentence)
	{		
		if(sentence.contains("\n"))
		{
			String topSentence = sentence.substring(0,sentence.indexOf("\n"));
			String bottomSentence = sentence.substring(sentence.indexOf("\n")+1, sentence.length());
			resolveSentence(topSentence);
			resolveSentence(bottomSentence);			
		}
		else
		{
			String dataType = TextParser.resolveDataType(sentence);		
			if(dataType == TextParser.GPS)
			{
				PayloadDataPoint partialPoint = new PayloadDataPoint();
				try {
					TextParser.processGPS(sentence, partialPoint);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				PayloadView.getInstance().updatePayloadDataPoint(partialPoint);
				partialDataPoints.AddPartialPayloadDataPoint(partialPoint);
			}
			if(dataType == TextParser.Sensor)
			{
				PayloadDataPoint partialPoint = new PayloadDataPoint();
				try {
					TextParser.processSensors(sentence, partialPoint);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				PayloadView.getInstance().updatePayloadDataPoint(partialPoint);
				partialDataPoints.AddPartialPayloadDataPoint(partialPoint);
			}
		}
	}	
	
}
