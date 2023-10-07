package Project1;

import java.io.File;

public class Driver 
{
	public static final File currDir=new File(".");
	public static final String absolutePath=currDir.getAbsolutePath();
	public static final String path=absolutePath.substring(0, absolutePath.length()-2)
			+File.separator;	
	public static final String logsPath=path+File.separator+"Logs"+File.separator;
	
	public static Logging log=new Logging(logsPath+"logs.txt");
	
	public static void main(String[] args)
	{
		long startTime=System.currentTimeMillis();
		
		CreateScene cs=new CreateScene();
		cs.run();
		
		long overallRunTime=System.currentTimeMillis()-log.getStartTime();
		
		log.setOverallRunTime(overallRunTime);
		
		log.log();
	}
}
