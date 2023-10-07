package Project1;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Logging 
{
	long overallRunTime;
	long startTime;
	
	File logFile;
	
	public Logging(String logFile)
	{
		this.logFile=new File(logFile);
		
		if(!this.logFile.exists())
		{
			try {
				this.logFile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public long getStartTime()
	{
		return this.startTime;
	}
	
	public void log()
	{
		FileWriter writer;
		try {
			writer = new FileWriter(this.logFile, true);
			
			writer.write("Overall Runtime: "+String.valueOf(this.overallRunTime));
			
			writer.write("\n");
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setOverallRunTime(long runTime)
	{
		this.overallRunTime=runTime;
	}
	
	public void setStartTime(long startTime)
	{
		this.startTime=startTime;
	}
}
