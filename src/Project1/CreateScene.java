package Project1;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class CreateScene extends JPanel implements KeyListener, ActionListener
{
	//This has a 4cm per pixel, 400 cm per meter. 2 meters 
	File path;
	
	JFrame frame;
	
	JMenuBar mb;
	
	 JMenu fileMenu;
	 
	 JMenuItem saveAsItem;
	
	int frameX=800;
	int frameY=800;
	int numOfPolygons;
	int minNumVertices;
	int maxNumVertices;
	int minRadius;
	int maxRadius;
	
	boolean fromFile;
	
	Obstacle[] obstacles;
	
	RigidBody2D rb;
	
	/*public static void main(String[] args)
	{
		CreateScene cs=new CreateScene();
		
		cs.run();
	}*/
	
	public void run()
	{
		//numOfPolygons=10;
		//minNumVertices=3;
		//maxNumVertices=5;
		//minRadius=50;
		//maxRadius=100;
		getInput();
		createPanel();
		if(!fromFile)
		{
			generatePolygonArray();
		}
		checkObstacleCollisions();
		createRB();
		rb.createRigidBody();
		repaint();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource()==saveAsItem)
		{
			//System.out.println("SAI");
			saveAsAction();
		}
	}
	
	public void checkObstacleCollisions()
	{
		for(int i=0; i<obstacles.length-1; i++)
		{
			for(int j=i+1; j<obstacles.length; j++)
			{
				if(CollisionChecking.separatingAxisTheorem(obstacles[i], obstacles[j]))
				{
					System.out.println("test");
					obstacles[i].setCollision(true);
					obstacles[j].setCollision(true);
				}
			}
		}
	}
	
	public void createPanel()
	{
		frame=new JFrame("Assignment 1");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
        frame.setSize(frameX, frameY);
        //frame.setVisible(true);
        frame.addKeyListener(this);
        
        this.setBackground(Color.white);
        //this.setLayout(new BorderLayout());
        this.setLayout(null);
        this.setVisible(true);
        addKeyListener(this);
        frame.getContentPane().add(this);
        
        mb = new JMenuBar();
        fileMenu = new JMenu("File");
        saveAsItem = new JMenuItem("Save As");
        saveAsItem.addActionListener(this);
        fileMenu.add(saveAsItem);
        mb.add(fileMenu);
        frame.setJMenuBar(mb);
        
        frame.setVisible(true);
	}
	
	public void createRB() //TODO put in random orientation and location
	{
		int centerX=(int)(Math.random()*frameX);
		int centerY=(int)(Math.random()*frameY);
		
		int x=80;
		int y=40;
		
		Point[] points=new Point[] {new Point(centerX-(x/2), centerY-(y/2)), 
				new Point(centerX+(x/2), centerY-(y/2)), new Point(centerX-(x/2), centerY+(y/2)),
				new Point(centerX+(x/2), centerY+(y/2))};
		
		this.rb=new RigidBody2D(points, new Point(centerX, centerY));
	}
	
	public void generatePolygonArray()
	{
		ArrayList<double[][]> polygons=new ArrayList<>();
		obstacles=new Obstacle[numOfPolygons];
		//double[][] polygons=generateRandomConvexPolygon();
		for(int i=0; i<numOfPolygons; i++)
		{
			obstacles[i]=new Obstacle();
			
			Random random=new Random();
			int numVertices=minNumVertices+(int)(Math.random()*(maxNumVertices-minNumVertices+1));
			System.out.println(numVertices);
			//int numVertices=random.nextInt(maxNumVertices-minNumVertices)+minNumVertices; //TODO
			//int numVertices=5; //TODO
			//System.out.println(numVertices);
			int centerX=(int)(Math.random()*frameX);
			int centerY=(int)(Math.random()*frameY);
			obstacles[i].setCenter(new Point(centerX, centerY));
			double[][] p=generateRandomConvexPolygon(numVertices, maxRadius, minRadius);
			//System.out.println(p.length);
			
			double[] center={0, 0};
			for(int j=0; j<numVertices; j++)
			{
				center[0]+=p[j][0];
				center[1]+=p[j][1];
			}
			center[0]=center[0]/numVertices;
			center[1]=center[1]/numVertices;
			double maxDistance=0;
			
			
			for(int j=0; j<numVertices; j++)
			{
				double x=p[j][0]-center[0];
				double y=p[j][1]-center[1];
				double d=Math.sqrt(x*x+y*y);
				if(maxDistance<d)
				{
					maxDistance=d;
				}
			}
			double scale=maxRadius/maxDistance;
			for(int j=0; j<numVertices; j++)
			{
				p[j][0]*=scale;
				p[j][1]*=scale;
			}
			polygons.add(p);
			
			Point[] points=new Point[numVertices];
			//System.out.println(numVertices+"\t"+points.length);
			for(int j=0; j<numVertices; j++)
			{
				//System.out.println(polygons.get(i)[j][0]);
				//points[j]=new Point(500, 500);
				points[j]=new Point((int)(polygons.get(i)[j][0])+centerX, (int)(polygons.get(i)[j][1])
						+centerY);
				//System.out.println(polygons.get(i)[j][0]);
			}
			/*System.out.println(numVertices+"\t"+points.length);
			for(int j=0; j<numVertices; j++)
			{
				points[j]=new Point((int)(polygons.get(i)[j][0])+centerX, (int)(polygons.get(i)[j][1])
				+centerY);
				System.out.println(points[j].getX()+"\t"+points[j].getY());
			}
			//System.out.println(points[0].getX());*/
			obstacles[i].setPoints(points);
			//System.out.println(obstacles[i].getPoints()[1].getX());
			//g.fillPolygon(new int[] {0, 50, 50, 0}, new int[] {0, 0, 50, 50}, 4);
			//g.fillPolygon(obstacles[i].getPolygon());
		}
	}
	
	private static double[][] generateRandomConvexPolygon(int number_of_vertices)
    {
		int len;
		double value;
		double[] vector;
		double[] vector_angle;
      	double[][] vector_pair;
      	int j;
     
      	// X and Y coordinates of vertices
      	ArrayList<Double>vertex_x=new ArrayList<Double>(number_of_vertices);
      	ArrayList<Double>vertex_y=new ArrayList<Double>(number_of_vertices);

      	// Generate X and Y coordinates of vertices from [(0,0),(1,1)) box
      	for(int i=0; i<number_of_vertices; i++)
   		{
      		vertex_x.add(Math.random());
      		vertex_y.add(Math.random());
        }
      	
      	// Sort lists of X and Y coordinates
      	Collections.sort(vertex_x);
      	Collections.sort(vertex_y);

      	// Randomly split each of the lists of X and Y coordinates into two sublists A and B,
      	// while preserving the endpoints (min and max)
      	ArrayList<Double>vertex_x_A=new ArrayList<Double>(number_of_vertices);
      	ArrayList<Double>vertex_x_B=new ArrayList<Double>(number_of_vertices);
      	ArrayList<Double>vertex_y_A=new ArrayList<Double>(number_of_vertices);
      	ArrayList<Double>vertex_y_B=new ArrayList<Double>(number_of_vertices);

      	int number_of_vertices_2=number_of_vertices-2;

      	value=vertex_x.remove(0);
      	vertex_x_A.add(value);
      	vertex_x_B.add(value);
      	value=vertex_y.remove(0);
      	vertex_y_A.add(value);
      	vertex_y_B.add(value);
      	
      	for(int i=0; i<number_of_vertices_2; i++)
        {
      		if(Math.random()<0.5)
      			vertex_x_A.add(vertex_x.get(i));
      		else
      			vertex_x_B.add(vertex_x.get(i));

      		if(Math.random()<0.5)
      			vertex_y_A.add(vertex_y.get(i));
      		else
      			vertex_y_B.add(vertex_y.get(i));
        }

      	value=vertex_x.get(number_of_vertices_2);
      	vertex_x_A.add(value);
      	vertex_x_B.add(value);
      	value=vertex_y.get(number_of_vertices_2);
      	vertex_y_A.add(value);
      	vertex_y_B.add(value);

      	// Extract vector components from subsequent pairs in the list
      	ArrayList<double[]>x_chain=new ArrayList<double[]>(number_of_vertices);
      	ArrayList<double[]>y_chain=new ArrayList<double[]>(number_of_vertices);

      	// X
      	len=vertex_x_A.size()-1;
      	for(int i=0; i<len;i++)
        {
      		vector=new double[2];
      		vector[0]=vertex_x_A.get(i);
      		vector[1]=vertex_x_A.get(i+1);
      		x_chain.add(vector);
        }

      	// Direction is inverted
      	len=vertex_x_B.size()-1;
      	for(int i=0; i<len;i++)
        {
      		vector=new double[2];
      		vector[1]=vertex_x_B.get(i);
      		vector[0]=vertex_x_B.get(i+1);
      		x_chain.add(vector);
        }

      	// Y
      	len=vertex_y_A.size()-1;
      	for(int i=0; i<len;i++)
        {
      		vector=new double[2];
      		vector[0]=vertex_y_A.get(i);
      		vector[1]=vertex_y_A.get(i+1);
      		y_chain.add(vector);
        }

      	// Direction is inverted
      	len=vertex_y_B.size()-1;
      	for(int i=0; i<len;i++)
        {
      		vector=new double[2];
      		vector[1]=vertex_y_B.get(i);
      		vector[0]=vertex_y_B.get(i+1);
      		y_chain.add(vector);
        }

      	// Randomly pair X and Y vector components
      	// Then combine pair of vectors (add) into new vector
      	// Finaly compute the angle of the combined vector to the X axis
      	ArrayList<double[]>vectors=new ArrayList<double[]>(number_of_vertices);
      	len=number_of_vertices;

      	for(int i=0; i<number_of_vertices; i++)
      	{
      		vector_angle=new double[3];
      		vector_pair=new double[2][];

      		// Pair
      		// X
      		j=(int)(Math.random()*len);
      		vector_pair[0]=x_chain.remove(j);

      		// Y
      		j=(int)(Math.random()*len);           
      		vector_pair[1]=y_chain.remove(j);

      		// Combine (add)
      		vector_angle[0]=vector_pair[0][1]-vector_pair[0][0];  // X
      		vector_angle[1]=vector_pair[1][1]-vector_pair[1][0];  // Y

      		// Calculate the angle
      		vector_angle[2]=Math.acos(vector_angle[0]/Math.sqrt(vector_angle[0]*vector_angle[0]+
                                              vector_angle[1]*vector_angle[1]));

      		if(vector_angle[1]<0)
      			vector_angle[2]=2*Math.PI-vector_angle[2];

      		// Store in the list
      		vectors.add(vector_angle);

      		len--;
      	}

      	// Sort vectors by angle
      	vectors.sort(new Comparator<double[]>()
        {
      		@Override
            public int compare(double[] A, double[] B)
            {
      			if(A[2]>B[2])
      				return 1;

                else if(A[2]<B[2])
                	return -1;
                else
                	return 0;
     	}});
      	
      	// Construct Polygon
      	double[][] polygon=new double[number_of_vertices][2];
      	// First point is in origin (0,0)
      	polygon[0][0]=0;
      	polygon[0][1]=0;

      	// Second point is the first vector
      	vector_angle=vectors.get(0);
      	polygon[1][0]=vector_angle[0];
      	polygon[1][1]=vector_angle[1];

      	// Rest are added to the previous one.
      	// Skip the last vector as it returns to the origin
      	for(int i=1; i<number_of_vertices-1; i++)
        {
      		vector_angle=vectors.get(i);
      		polygon[i+1][0]=polygon[i][0]+vector_angle[0];
      		polygon[i+1][1]=polygon[i][1]+vector_angle[1];
        }
      	//System.out.println(polygon[0][1]);
      	return polygon;

    }
	
	public static double[][] generateRandomConvexPolygon(int number_of_vertices, double max_radius, 
			double min_radius)

    {

      if(max_radius<=0)

        throw new RuntimeException("Max radius can't be 0 or negative");

      if(min_radius<=0)

        throw new RuntimeException("Min radius can't be 0 or negative");

      if(max_radius<min_radius)

        throw new RuntimeException("Max radius can't be smaller them Min radius");


      double[][] polygon;



      if(min_radius==max_radius)

        {

          double max_angle=Math.PI*2;

          double[] angles=new double[number_of_vertices];

          double angle;

          for(int i=0; i<number_of_vertices;)

            {

              angle=Math.random()*max_angle;

              if(angle>0)

                {

                  int j=i-1;

                  while(j>=0)

                    {

                      if(angles[j]>angle)

                        {

                          angles[j+1]=angles[j];

                          j--;

                        }

                      else

                        break;

                    }

                  if(j<0)

                    {

                      angles[0]=angle;

                      i++;

                    }

                  else

                    {

                      if(angles[j]<angle)

                        {

                          angles[j+1]=angle;

                          i++;

                        }

                      else

                        {

                          for(int k=j+1; k<i; k++)

                            angles[k]=angles[k+1];

                        }

                    }

                }

            }



          polygon=new double[number_of_vertices][2];

          for(int i=0; i<number_of_vertices; i++)

            {

              polygon[i][0]=max_radius*Math.cos(angles[i]);

              polygon[i][1]=max_radius*Math.sin(angles[i]);

            }

         

          return polygon;

        }



      polygon=generateRandomConvexPolygon(number_of_vertices);



      // Find the center

      double[] center = {0, 0};

      for(int i=0; i<number_of_vertices; i++)

        {

          center[0]+=polygon[i][0];

          center[1]+=polygon[i][1];

        }



      center[0]=center[0]/number_of_vertices;

      center[1]=center[1]/number_of_vertices;



      double[] distance=new double[number_of_vertices];

      double max_distance=0;

      for(int i=0; i<number_of_vertices; i++)

        {

          // Center the polygon

          polygon[i][0]-=center[0];

          polygon[i][1]-=center[1];

          // Calculate the distance of the vertex and min and max distances

          distance[i]=Math.sqrt(polygon[i][0]*polygon[i][0]+polygon[i][1]*polygon[i][1]);

          if(max_distance<distance[i])

            max_distance=distance[i];

        }



      // Scale Polygon to the Maximum Radius, so that all vertices fit in it

      double scale=max_radius/max_distance;

      double min_distance=Double.POSITIVE_INFINITY;

      for(int i=0; i<number_of_vertices; i++)

        {

          polygon[i][0]*=scale;

          polygon[i][1]*=scale;



          // scale the distance as well

          distance[i]=distance[i]*scale;

          if(min_distance>distance[i])

            min_distance=distance[i];

        }



      // If Polygon is not inside min radius, stretch it so that all vertices

      // go outside min radius but not outside Max radius

      if(min_distance<min_radius)

        {

          double delta_radius=max_radius-min_radius;

          double delta_distance=max_radius-min_distance;

          double delta_radius_delta_distance=delta_radius/delta_distance; 
          // delta_distance can be 0 only if min idistance is max_radius 
          //which is if the vertex is in the center

          double stretch;



          for(int i=0; i<number_of_vertices; i++)

            {

              stretch=(max_radius-(max_radius-distance[i])*delta_radius_delta_distance)/distance[i];



              polygon[i][0]*=stretch;

              polygon[i][1]*=stretch;

            }

        }

     

      return polygon;
    }
	
	public void getInput()
	{
		Scanner sc=new Scanner(System.in);
		
		System.out.print("Are you loading from a file? (Type 'File' if yes) ");
		String isFile=sc.nextLine();
		
		if(isFile.equalsIgnoreCase("File"))
		{
			System.out.print("What is the path of the file? ");
			String filePath=sc.nextLine();
			Driver.log.setStartTime(System.currentTimeMillis());
			if(filePath.charAt(0)=='\"')
			{
				filePath=filePath.substring(1);
			}
			if(filePath.charAt(filePath.length()-1)=='\"')
			{
				filePath=filePath.substring(0, filePath.length()-1);
			}
			getInfoFromFile(filePath);
			this.fromFile=true;
		}
		else
		{
			this.fromFile=false;
			do
			{
				try
				{
					System.out.print("How many polygons? ");
					this.numOfPolygons=Integer.parseInt(sc.nextLine());
				}catch(NumberFormatException ex)
				{
					System.out.println("Improper Number Format");
					this.numOfPolygons=-1;
				}
			}while(!(this.numOfPolygons>=0));
			
			do
			{
				try
				{
					System.out.print("What is the minimum number of vertices? ");
					this.minNumVertices=Integer.parseInt(sc.nextLine());
				}catch(NumberFormatException ex)
				{
					System.out.println("Improper Number Format");
					this.minNumVertices=-1;
				}
			}while(!(this.numOfPolygons>0));
			
			do
			{
				try
				{
					System.out.print("What is the maximum number of vertices? ");
					this.maxNumVertices=Integer.parseInt(sc.nextLine());
				}catch(NumberFormatException ex)
				{
					System.out.println("Improper Number Format");
					this.maxNumVertices=-1;
				}
			}while(!(this.maxNumVertices>0) && maxNumVertices>=minNumVertices);
			
			do
			{
				try
				{
					System.out.print("What is the minimum radius? ");
					this.minRadius=Integer.parseInt(sc.nextLine());
				}catch(NumberFormatException ex)
				{
					System.out.println("Improper Number Format");
					this.minRadius=-1;
				}
			}while(!(this.minRadius>0));
			
			do
			{
				try
				{
					System.out.print("What is the maximum radius? ");
					this.maxRadius=Integer.parseInt(sc.nextLine());
				}catch(NumberFormatException ex)
				{
					System.out.println("Improper Number Format");
					this.maxRadius=-1;
				}
			}while(!(this.maxRadius>0) && maxRadius>minRadius);
		}
	}
	
	public void getInfoFromFile(String filePath) //TODO will adjust to account for start and goal in first
	{
		try {
			Scanner sc=new Scanner(new File(filePath));
			
			String infoString=sc.nextLine();
			String[] info=infoString.split(",");
			
			this.numOfPolygons=Integer.parseInt(info[0]);
			this.minNumVertices=Integer.parseInt(info[1]);
			this.maxNumVertices=Integer.parseInt(info[2]);
			this.minRadius=Integer.parseInt(info[3]);
			this.maxRadius=Integer.parseInt(info[4]);
			
			obstacles=new Obstacle[numOfPolygons];
			
			int i=0;
			while(sc.hasNext())
			{
				obstacles[i]=new Obstacle();
				
				String line=sc.nextLine();
				String obstacleInfoString=line.split(":")[0];
				String[] obstacleInfo=obstacleInfoString.split(",");
				//0 is centerX, 1 is centerY
				obstacles[i].setCenter(new Point(Integer.parseInt(obstacleInfo[0]), 
						Integer.parseInt(obstacleInfo[1])));
				
				String[] spaceSplit=line.split(":")[1].split(" ");
				String[][] pointString=new String[spaceSplit.length][2];
				
				Point[] points=new Point[spaceSplit.length];
				
				for(int j=0; j<spaceSplit.length; j++)
				{
					pointString[j]=spaceSplit[j].split(",");
					
					points[j]=new Point(Integer.parseInt(pointString[j][0]), 
							Integer.parseInt(pointString[j][1]));
				}
				
				obstacles[i].setPoints(points);
				
				i++;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) 
	{
		if(e.getKeyCode()==KeyEvent.VK_UP)
		{
			
		}
		if(e.getKeyCode()==KeyEvent.VK_DOWN)
		{
			
		}
		if(e.getKeyCode()==KeyEvent.VK_LEFT)
		{
			int angle=-10;
			
			System.out.println("LEFT");
			
			rb.rotate(angle);
			repaint();
		}
		if(e.getKeyCode()==KeyEvent.VK_RIGHT)
		{
			
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		//System.out.println("1\t"+obstacles[0].getPolygon().xpoints[0]);
		
		//g.fillPolygon(new int[] {579, 572, 475, 438, 437}, new int[] {440, 564, 550, 503, 442}, 5);
		
		for(int i=0; i<numOfPolygons; i++)
		{
			/*if(obstacles[i].getCollision())
			{
				g.setColor(Color.red);
			}
			else
			{
				g.setColor(Color.black);
			}
			g.fillPolygon(obstacles[i].getPolygon());*/
			
			if(obstacles[i].getCollision())
			{
				g.fillPolygon(obstacles[i].getPolygon());
			}
			else
			{
				g.drawPolygon(obstacles[i].getPolygon());
			}
			
			if(rb!=null)
			{
				g.fillRect((int)rb.getPoints()[0].getX(), (int)rb.getPoints()[0].getY(), 
						(int)rb.getX(), (int)rb.getY());
			}
		}
	}
	
	public void saveAsAction()
	{
		JFileChooser fileChooser=new JFileChooser();
		int result=fileChooser.showSaveDialog(this);
		
		File file=fileChooser.getSelectedFile();
		try {
			String fileName=file.getName();
			if(!fileName.endsWith(".txt"))
			{
				String newName=file.getAbsolutePath()+".txt";
				file=new File(newName);
				//System.out.println(newName);
			}
					
			if(!file.exists())
			{
				file.createNewFile();
				
				saveWorld(file);
			}
			else
			{
				JOptionPane.showMessageDialog(this, "This file name already exists.\nFile not saved.");
			}
		} catch (IOException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
	}
	
	public void saveWorld(File file) //TODO will adjust to account for start and goal in first line
	{
		try 
		{
			FileWriter writer = new FileWriter(file);
			
			writer.write(String.valueOf(this.numOfPolygons)+','+String.valueOf(this.minNumVertices)+','
					+String.valueOf(this.maxNumVertices)+','+String.valueOf(this.minRadius)+','
					+String.valueOf(this.maxRadius)+'\n');
			
			for(int i=0; i<this.numOfPolygons; i++)
			{
				Point[] points=obstacles[i].getPoints();
				
				writer.write(String.valueOf(obstacles[i].getCenter().getX())+','
						+String.valueOf(obstacles[i].getCenter().getY())+':');
				
				for(int j=0; j<points.length; j++)
				{
					writer.write(String.valueOf((int)points[j].getX())+','
							+String.valueOf((int)points[j].getY())+' ');
				}
				writer.write('\n');
			}
			
			
			writer.close();
		      
			//System.out.println("Successfully wrote to the file.");
		} catch (IOException ex) {
			//System.out.println("An error occurred.");
			ex.printStackTrace();
		}
	}
	
	public class Obstacle
	{		
		Point[] points;
		
		Polygon polygon;
		
		Point center;
				
		boolean collision=false;
		
		public Point getCenter()
		{
			return center;
		}
		
		public boolean getCollision()
		{
			return collision;
		}
		
		public Point[] getPoints()
		{
			return points;
		}
		
		public Polygon getPolygon()
		{
			return polygon;
		}
		
		public void setCenter(Point center)
		{
			this.center=center;
		}
		
		public void setCollision(boolean collision)
		{
			this.collision=collision;
		}
		
		public void setPoints(Point[] points)
		{
			this.points=points;
			
			int[] xPoints=new int[points.length];
			int[] yPoints=new int[points.length];
			
			//System.out.println();
			
			for(int i=0; i<points.length; i++)
			{
				//System.out.println(points[i].getX()+"\t"+points[i].getY());
				xPoints[i]=(int) points[i].getX();
				yPoints[i]=(int) points[i].getY();
			}
			
			this.polygon=new Polygon(xPoints, yPoints, points.length);;
		}
	}
}