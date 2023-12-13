package Project3;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class controls extends JPanel
{
	private static final int numMaps=5;
	private static final int frameX=800;
	private static final int frameY=800;
	
	public static final File currDir=new File(".");
	public static final String absolutePath=currDir.getAbsolutePath();
	public static final String fileP=absolutePath.substring(0, 
			absolutePath.length()-2)
			+File.separator;	
	public static final String landmarksPath=fileP+File.separator+"src"
			+File.separator+"Project3"+File.separator+"maps"+File.separator;
	
	JFrame frame;
		
	ArrayList<Point2D> landmarks;
	ArrayList<double[]> centerPath=new ArrayList<>();
	
	Point2D[] verts=new Point2D.Double[4];
	
	ddRobot robot=new ddRobot();
	
	public static void main(String[] args)
	{
		controls controls=new controls();
		controls.run();
	}
	
	public boolean collides()
	{
		double maxDistance=80;
		
		Point2D[] vertices=robot.getVertices();

		for(int i=0; i<vertices.length; i++)
		{
			if(vertices[i].getX()<=80
					|| vertices[i].getX()>=720
					|| vertices[i].getY()<=80
					|| vertices[i].getY()>=720)
			{
				//System.out.println("COLLIDES");
				return true;
			}
		}
		
		for(int i=0; i<landmarks.size(); i++)
		{
			for(int j=0; j<vertices.length; j++)
			{
				double A;
				double B;
				double C;
				
				int p=j;
				int q=j+1;
				
				if(q>3)
				{
					q=0;
				}
				
				A=vertices[q].getY()-vertices[p].getY();
				B=vertices[q].getX()-vertices[p].getX();
				C=-vertices[q].getY()*(B)-vertices[p].getX()*(A);
				
				double distance=Math.abs((A*landmarks.get(i).getX())
						+(B*landmarks.get(i).getY())+C)/Math.sqrt((A*A)+(B*B));
				
				if(distance<maxDistance)
				{
					
					double xd=landmarks.get(i).getX()-vertices[p].getX();
					double yd=landmarks.get(i).getY()-vertices[p].getY();

					distance=Math.sqrt(xd*xd+yd*yd);
					
					if(distance<maxDistance)
					{
						return true;
					}
					
					xd=landmarks.get(i).getX()-vertices[q].getX();
					yd=landmarks.get(i).getY()-vertices[q].getY();

					distance=Math.sqrt(xd*xd+yd*yd);
					
					if(distance<maxDistance)
					{
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
	public boolean collides(Point2D[] oldVertices, Point2D[] newVertices)
	{		
		double maxDistance=80;

		for(int i=0; i<newVertices.length; i++)
		{
			if(newVertices[i].getX()<=80
					|| newVertices[i].getX()>=720
					|| newVertices[i].getY()<=80
					|| newVertices[i].getY()>=720)
			{
				return true;
			}
		}
		
		for(int i=0; i<landmarks.size(); i++)
		{
			for(int j=0; j<oldVertices.length; j++)
			{
				double A;
				double B;
				double C;
				
				A=newVertices[j].getY()-oldVertices[j].getY();
				B=newVertices[j].getX()-oldVertices[j].getX();
				C=-newVertices[j].getY()*(B)-oldVertices[j].getX()*(A);
				
				double distance=Math.abs((A*landmarks.get(i).getX())
						+(B*landmarks.get(i).getY())+C)/Math.sqrt((A*A)+(B*B));
				
				if(distance<maxDistance)
				{
					
					double xd=landmarks.get(i).getX()-oldVertices[j].getX();
					double yd=landmarks.get(i).getY()-oldVertices[j].getY();

					distance=Math.sqrt(xd*xd+yd*yd);
					
					if(distance<maxDistance)
					{
						return true;
					}
					
					xd=landmarks.get(i).getX()-newVertices[j].getX();
					yd=landmarks.get(i).getY()-newVertices[j].getY();

					distance=Math.sqrt(xd*xd+yd*yd);
					
					if(distance<maxDistance)
					{
						return true;
					}
				}
			}
			
			for(int j=0; j<newVertices.length; j++)
			{
				double A;
				double B;
				double C;
				
				int p=j;
				int q=j+1;
				
				if(q>3)
				{
					q=0;
				}
				
				A=newVertices[q].getY()-newVertices[p].getY();
				B=newVertices[q].getX()-newVertices[p].getX();
				C=-newVertices[q].getY()*(B)-newVertices[p].getX()*(A);
				
				double distance=Math.abs((A*landmarks.get(i).getX())
						+(B*landmarks.get(i).getY())+C)/Math.sqrt((A*A)+(B*B));
				
				if(distance<maxDistance)
				{
					
					double xd=landmarks.get(i).getX()-newVertices[p].getX();
					double yd=landmarks.get(i).getY()-newVertices[p].getY();

					distance=Math.sqrt(xd*xd+yd*yd);
					
					if(distance<maxDistance)
					{
						return true;
					}
					
					xd=landmarks.get(i).getX()-newVertices[q].getX();
					yd=landmarks.get(i).getY()-newVertices[q].getY();

					distance=Math.sqrt(xd*xd+yd*yd);
					
					if(distance<maxDistance)
					{
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
	public void createDDRobot()
	{
		do {
			double x=Math.random()*800;
			double y=Math.random()*800;
					
			double angle=(Math.random()*360)-180;
			
			robot.setQ(new double[] {x, y, 0});
			
			double W=robot.getW();
			double L=robot.getL();
			
			robot.setVertices(new Point2D.Double[] {
					new Point2D.Double(x-(W/2), y-(L/2)),
					new Point2D.Double(x-(W/2), y+(L/2)),
					new Point2D.Double(x+(W/2), y-(L/2)),
					new Point2D.Double(x+(W/2), y+(L/2))});
			
			robot.vertexHistory.add(robot.getVertices());
			
			/*System.out.println((x-(W/2))+"\t"+(y-(L/2)));
			System.out.println((x-(W/2))+"\t"+(y+(L/2)));
			System.out.println((x+(W/2))+"\t"+(y-(L/2)));
			System.out.println((x+(W/2))+"\t"+(y+(L/2)));*/
			
			rotate(robot.getQ(), angle);
		}while(collides());
	}
	
	public void createMap(int index)
	{
		frame=new JFrame("Assignment 3. Landmark "+index);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(frameX, frameY);
        frame.setVisible(true);
        
        this.setBackground(Color.white);
        this.setLayout(null);
        this.setVisible(true);
        frame.getContentPane().add(this);
	}
	
	public double[][] generateControlSequence()
	{
		double[][] controlSequence=new double[200][2];
		
		// Input startPosition of the center of the robot,
		// Input startDirection of the robot
		// Input width and the height of the robot
		double direction=robot.getQ()[2];
		direction=Math.toRadians(direction);
		
		Point2D center=new Point2D.Double(robot.getQ()[0], robot.getQ()[1]);
		centerPath.add(new double[] {center.getX(), center.getY()});
		// Generate vertices of the robot as offsets from the center point
		// Robot's central point is what we track


		// Generate vertex[i] as an offset from center point;
		Point2D[] vertex=robot.getVertices();
		
		double wheelCenterDistance=Math.sqrt(robot.getW()*robot.getW()
				+robot.getL()*robot.getL())/2;
		double[] wheelCenterAngle=new double[4];
		for(int i=0; i<4; i++)
		{
		    wheelCenterAngle[i]=Math.acos((vertex[i].getX()-center.getX())
		    		/wheelCenterDistance);
		}


		// Start a loop that generates steps
		
		int MAX_NUMBER_OF_TRIALS=20;
		int sequence=0;
		for(int step=0; step<10; step++)
		{
			// Try to make 1 movement step
			int number_of_trials=0;
			while(true)
		    {
				// generate new direction and movement
				double angle=(Math.random()*360)-180;
				angle=Math.toRadians(angle);
				double velocity=(Math.random()*400)-200;
				
				double newDirection=direction+angle;				
				
				// Move CENTER to new location
				Point2D newCenter=new Point2D.Double(center.getX()
						+velocity*Math.cos(newDirection), center.getY()
						+velocity*Math.sin(newDirection));

				// create a new POTENTIAL position of vertices
				Point2D[] newVertex=new Point2D[4];

				// create rotated new vertices
				for(int i=0; i<4; i++)
		        {
					/*System.out.println("Hello1 "+newCenter.getX());
					System.out.println("Hello2 "+wheelCenterAngle[i]);
					System.out.println("Hello3 "+newCenter.getY());*/

					newVertex[i]=new Point2D.Double(newCenter.getX()
							+wheelCenterDistance*Math.cos(newDirection
									+wheelCenterAngle[i]), newCenter.getY()
							+wheelCenterDistance*Math.sin(newDirection
									+wheelCenterAngle[i]));
		        

		        }

				// Check if you can move to new center and new vertices without 
				//bumping into obstacle
				boolean canMove=collides(vertex, newVertex);
				if(canMove)
		        {
					center=newCenter;
					vertex=newVertex;
					direction=newDirection;
					
					robot.vertexHistory.add(vertex);
					
					centerPath.add(new double[] {center.getX(), center.getY()});
					
					direction=Math.toDegrees(direction);
					
					for(int i=0; i<4; i++)
					{
						System.out.println(vertex[i].getX()+", "
					+vertex[i].getY());
					}
					System.out.println("///////////////////////");
					
					for(int seq=sequence*10; seq<(sequence*10)+10; seq++)
					{
						controlSequence[seq]=new double[] 
								{velocity, Math.toDegrees(angle)};
					}

					break;
		        }
				number_of_trials++;
				//System.out.println(number_of_trials);
				
				if(number_of_trials>=MAX_NUMBER_OF_TRIALS)
				{
					throw new RuntimeException("Could not generate valid "
							+ "move from this position: ("+center.getX()
							+", "+center.getY()+")");
				}
					
		    }
		}
		
		return controlSequence;
	}
	
	public void graphRobot(Graphics g)
	{
		double[] q=robot.getQ();
		double[] wheelDims=robot.getWheelDims();
		Point2D[] vertices=robot.getVertices();
		double L=robot.getL(); 
		double W=robot.getW();
				
		g.setColor(Color.blue);
		g.fillPolygon(new int[] {(int) vertices[0].getX(), 
				(int) vertices[1].getX(), (int) vertices[3].getX(), 
				(int) vertices[2].getX()}, new int[] {(int) vertices[0].getY(), 
						(int) vertices[1].getY(), (int) vertices[3].getY(), 
						(int) vertices[2].getY()}, 4);
	}
	
	public void loadLandmarks(int index)
	{
		landmarks=new ArrayList<>();
		String fileName=landmarksPath+"landmarks_"+index+".txt";
		
		try {
			Scanner sc=new Scanner(new File(fileName));
			
			while(sc.hasNextLine())
			{
				String line=sc.nextLine();
				
				landmarks.add(new Point2D.Double(Double.parseDouble(
						line.split(",")[0]), 
						Double.parseDouble(line.split(",")[1])));
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Point2D[] move(double[] u)
	{
		Point2D[] tmpVertices=robot.getVertices();
		
		double vx=u[0]*Math.cos(Math.toRadians(u[1]));
		double vy=u[0]*Math.sin(Math.toRadians(u[1]));
		
		Point2D[] vertices=new Point2D.Double[] {
				new Point2D.Double(tmpVertices[0].getX()+vx, 
						tmpVertices[0].getY()+vy),
				new Point2D.Double(tmpVertices[1].getX()+vx, 
						tmpVertices[1].getY()+vy),
				new Point2D.Double(tmpVertices[2].getX()+vx, 
						tmpVertices[2].getY()+vy),
				new Point2D.Double(tmpVertices[3].getX()+vx, 
						tmpVertices[3].getY()+vy)};
	
		return vertices;
	}
	
	public Point2D[] move(Point2D[] tmpVertices, double[] u)
	{
		//Point2D[] tmpVertices=robot.getVertices();
		
		double vx=u[0]*Math.cos(Math.toRadians(u[1]));
		double vy=u[0]*Math.sin(Math.toRadians(u[1]));
		 
		Point2D[] vertices=new Point2D.Double[] {
				new Point2D.Double(tmpVertices[0].getX()+vx, 
						tmpVertices[0].getY()+vy),
				new Point2D.Double(tmpVertices[1].getX()+vx, 
						tmpVertices[1].getY()+vy),
				new Point2D.Double(tmpVertices[2].getX()+vx, 
						tmpVertices[2].getY()+vy),
				new Point2D.Double(tmpVertices[3].getX()+vx, 
						tmpVertices[3].getY()+vy)};
	
		return vertices;
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		for(int i=0; i<landmarks.size(); i++)
		{
			g.fillOval((int)landmarks.get(i).getX(), 
					(int)landmarks.get(i).getY(), 
					20, 20);
		}
		
		graphRobot(g);
		
		/*if(path.size()>=2)
		{
			for(int i=0; i<path.size()-1; i++)
			{
				//System.out.println(3);
				Graphics2D g2d=(Graphics2D) g;
				g2d.setStroke(new BasicStroke(5));
				g2d.setColor(Color.gray);

				g2d.drawLine((int)path.get(i)[0], (int)path.get(i)[1], 
						(int)path.get(i+1)[0], (int)path.get(i+1)[1]);
			}
		}*/
	}
	
	public void rotate(double[] q, double angle)
	{
		double centerX;
		double centerY;
		
		double rotatedX;
		double rotatedY;
		
		double w=robot.getW();
		double l=robot.getL();
		
		Point2D[] vertices=robot.getVertices();
		
		Point2D center=new Point2D.Double(q[0], q[1]);
		
		Point2D[] newVertices=new Point2D[vertices.length];
		
		//System.out.println(vertices[0].getX());
		
		for(int i=0; i<vertices.length; i++)
		{
			centerX=vertices[i].getX()-center.getX();
			centerY=vertices[i].getY()-center.getY();
			
			rotatedX=centerX*Math.cos(Math.toRadians(angle))
					-centerY*Math.sin(Math.toRadians(angle));
			rotatedY=centerX*Math.sin(Math.toRadians(angle))
					+centerY*Math.cos(Math.toRadians(angle));
			
			newVertices[i]=new Point2D.Double((rotatedX+center.getX()), 
					(rotatedY+center.getY()));
			
		}
		
		q[2]+=angle;
		robot.setVertices(newVertices);
		//System.out.println(robot.getVertices()[0].getX());
	}
	
	public void run()
	{
		for(int i=0; i<numMaps; i++)
		{
			loadLandmarks(i);
			createDDRobot();
			createMap(i);
			robot.setControlSequences(generateControlSequence());
			runControlSequence();
			
			try {
				Thread.sleep(100000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void runControlSequence()
	{
		try {
			Thread.sleep(2000);
			
			for(int i=0; i<200; i++)
			{
				if(i%20==0)
				{
					String tmp=String.valueOf(i);
					int index=Integer.parseInt
							(String.valueOf(tmp.charAt(0)))/2;
					System.out.println(index);
					robot.setVertices(robot.vertexHistory.get(index));
					
					for(int j=0; j<4; j++)
					{
						System.out.println(robot.getVertices()[j].getX()
								+", "+robot.getVertices()[j].getY());
					}
					System.out.println("00000000000000000000");
					
					repaint();
				}
				
				Thread.sleep((long)(1000*robot.getDt()));
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*public void runControlSequence()
	{
		path.add(robot.getQ());
		
		try {
			for(int i=0; i<200; i++)
			{				
				double[] u=robot.getControlSequences()[i];
				
				if(robot.getControlSequences()[i]!=
							robot.getControlSequences()[i+1])
				{
					Point2D[] newVertices=move(u);
					
					robot.setVertices(newVertices);
					
					double newX=newVertices[0].getX()+(robot.getW()/2);
					double newY=newVertices[0].getY()+(robot.getL()/2);
					double newAngle=u[1];
					
					robot.setQ(new double[] {newX, newY, newAngle});
					
					rotate(robot.getQ(), u[1]);
					
					path.add(robot.getQ());
					
					repaint();
				}
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
	
	public class ddRobot //Differential Drive Robot
	{
		double[] q=new double[3]; //Robot state [x, y, angle]
		double[][] controlSequences=new double[200][2]; //Control input
		Point2D[] vertices=new Point2D.Double[4];
		ArrayList<Point2D[]> vertexHistory=new ArrayList<>();
		//Wheel dimensions, x, y, width, height
		final double[] wheelDims=new double[] {}; 
		//Distance between wheels (Length) //Initially reps Y
		final double L=80.0; 
		final double W=40; //Width //Initially represents X
		final double dt=.1; //Timestep
		
		public double[][] getControlSequences()
		{
			return controlSequences;
		}
		
		public double getDt()
		{
			return dt;
		}
		
		public double getL()
		{
			return L;
		}
		
		public double[] getQ()
		{
			return q;
		}
		
		public Point2D[] getVertices()
		{
			return vertices;
		}
		
		public double getW()
		{
			return W;
		}
		
		public double[] getWheelDims()
		{
			return wheelDims;
		}
		
		public void setControlSequences(double[][] controlSequences)
		{
			this.controlSequences=controlSequences;
		}
		
		public void setQ(double[] q)
		{
			this.q=q;
		}
		
		public void setVertices(Point2D[] vertices)
		{
			this.vertices=vertices;
		}
	}
}