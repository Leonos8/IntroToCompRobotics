package Project1;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class CreateScene extends JPanel implements KeyListener
{
	File path;
	
	JFrame frame;
	
	int frameX=800;
	int frameY=800;
	int numOfPolygons;
	int minNumVertices;
	int maxNumVertices;
	int minRadius;
	int maxRadius;
	
	Obstacle[] obstacles;
	
	public static void main(String[] args)
	{
		CreateScene cs=new CreateScene();
	}
	
	public CreateScene()
	{
		getInput();
		createPanel();
		generatePolygonArray();
	}
	
	public void createPanel()
	{
		frame=new JFrame("Assignment 1");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
        frame.setSize(frameX, frameY);
        frame.setVisible(true);
        frame.addKeyListener(this);
        
        this.setBackground(Color.white);
        this.setLayout(null);
        this.setVisible(true);
        //addKeyListener(this);
        frame.getContentPane().add(this);
	}
	
	public void generatePolygonArray()
	{
		ArrayList<double[][]> polygons=new ArrayList<>();
		obstacles=new Obstacle[numOfPolygons];
		//double[][] polygons=generateRandomConvexPolygon();
		
		System.out.println(numOfPolygons);
		
		for(int i=0; i<numOfPolygons; i++)
		{
			obstacles[i]=new Obstacle();
			
			Random random=new Random();
			int numVertices=random.nextInt(maxNumVertices-minNumVertices)+minNumVertices;
			
			polygons.add(generateRandomConvexPolygon(numVertices));
			
			Point[] points=new Point[numVertices];
			
			for(int j=0; j<numVertices; j++)
			{
				points[j]=new Point((int)polygons.get(i)[j][0], (int)polygons.get(i)[j][1]);
			}
			obstacles[i].setPoints(points);
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
      	
      	System.out.println(polygon.length);

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
			getInfoFromFile(filePath);
		}
		else
		{
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
	
	public void getInfoFromFile(String filePath)
	{
		//TODO
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		System.out.println(obstacles.length);
		
		for(int i=0; i<obstacles.length; i++)
		{
			g.fillPolygon(obstacles[i].getPolygon());
		}
	}
	
	public class Obstacle
	{
		JComponent obstacle;
		Point[] points;
		
		public Polygon getPolygon()
		{
			int[] xPoints=new int[points.length];
			int[] yPoints=new int[points.length];

			for(int i=0; i<points.length; i++)
			{
				xPoints[i]=(int) points[i].getX();
				yPoints[i]=(int) points[i].getY();
			}
			
			return new Polygon(xPoints, yPoints, points.length);
		}
		
		public void setObstacle(JComponent obstacle)
		{
			this.obstacle=obstacle;
		}
		
		public void setPoints(Point[] points)
		{
			this.points=points;
		}
	}
}
