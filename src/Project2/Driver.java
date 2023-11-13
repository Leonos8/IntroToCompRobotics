package Project2;

import java.io.File;

import Project2.CreateScene;

public class Driver 
{
	public static final File currDir=new File(".");
	public static final String absolutePath=currDir.getAbsolutePath();
	public static final String path=absolutePath.substring(0, absolutePath.length()-2)
			+File.separator;	
	public static final String logsPath=path+File.separator+"Logs"+File.separator;
	
	
	public static void main(String[] args)
	{		
		Arm1 a1=new Arm1();
		a1.run();
	}
}
