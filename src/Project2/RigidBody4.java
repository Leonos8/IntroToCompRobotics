package Project2;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class RigidBody4 extends JPanel
{
	JFrame frame = new JFrame();
	
	double xDim; //.2
	double yDim; //.1
	
	static double angle=0;
	
	double STEP_SIZE = 40;
	double GOAL_RADIUS = 40;
	
	Point2D center;
	Point2D[] vertices;
	
	int frameX=800;
	int frameY=800;
	int maxIterations=1000;
	
	boolean goalFound;
	
	static Point2D startNode;
	static Point2D goalNode;
	
	ArrayList<Point2D> nodes=new ArrayList();
	
	CreateScene cs;
	
	static RigidBody4 rb;
	
	public static void main(String[] args)
	{
		double startX=200;
		double startY=100;
		
		double goalX=700;
		double goalY=600;
		
		startNode=new Point2D.Double(startX, startY);
		goalNode=new Point2D.Double(goalX, goalY);
		
		rb=new RigidBody4();
		rb.run();
	}
	
	public RigidBody4()
	{		
		this.center=new Point2D.Double(startNode.getX(), startNode.getY());
			
		double x=80;
		double y=40;
			
		Point2D[] points=new Point2D[4];
		points[0]=new Point2D.Double(startNode.getX()-(x/2), startNode.getY()-(y/2));
		points[1]=new Point2D.Double(startNode.getX()+(x/2), startNode.getY()-(y/2));
		points[2]=new Point2D.Double(startNode.getX()+(x/2), startNode.getY()+(y/2));
		points[3]=new Point2D.Double(startNode.getX()-(x/2), startNode.getY()+(y/2));
		
		this.vertices=points;
		}
	
	public void createPanel() {
		frame = new JFrame("Assignment 1");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setSize(frameX, frameY);
		// frame.setVisible(true);

		this.setBackground(Color.white);
		this.setLayout(null);
		this.setVisible(true);
		frame.getContentPane().add(this);

		frame.setVisible(true);
	}
	
	public void createRigidBody()
	{
		this.xDim=80;
		this.yDim=40;
	}
	
	public double distance(Point2D a, Point2D b) {
		return Math.sqrt(Math.pow(a.getX() - b.getX(), 2) + Math.pow(a.getY() - b.getY(), 2));
	}
	
	public Point2D findNearestNode(Point2D randomNode) {
		Point2D nearestNode = new Point2D.Double();
		double minDistance = Double.MAX_VALUE;

		for (int i = 0; i < nodes.size(); i++) {
			double dist = distance(nodes.get(i), randomNode);

			if (dist < minDistance) {
				minDistance = dist;
				nearestNode = nodes.get(i);
			}
		}

		return nearestNode;
	}
	
	public double getAngle()
	{
		return angle;
	}
	
	public Point2D[] getPoints()
	{
		return vertices;
	}
	
	public int[] getXPoints()
	{
		int[] xArr=new int[vertices.length];
		for(int i=0; i<vertices.length; i++)
		{
			xArr[i]=(int)vertices[i].getX();
		}
		
		return xArr;
	}
	
	public int[] getYPoints()
	{
		int[] yArr=new int[vertices.length];
		for(int i=0; i<vertices.length; i++)
		{
			yArr[i]=(int)vertices[i].getY();
		}
		
		return yArr;
	}
	
	public void move(double step)
	{
		double xStep=step*Math.cos(Math.toRadians(angle));
		double yStep=step*Math.sin(Math.toRadians(angle));
		
		Point2D[] newVertices=new Point2D[vertices.length];
		
		System.out.println("UP");
		for(int i=0; i<vertices.length; i++)
		{
			System.out.println(xStep);
			System.out.println(yStep);
			newVertices[i]=new Point2D.Double(vertices[i].getX()+xStep, vertices[i].getY()+yStep);
		}
		
		Point2D centerTmp=new Point2D.Double(center.getX()+xStep, center.getY()+yStep);
		
		this.center=centerTmp;
		
		setVertices(newVertices);
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		for(int i=0; i<cs.numOfPolygons; i++)
        {           
            if(cs.obstacles[i].getCollision())
            {
                g.fillPolygon(cs.obstacles[i].getPolygon());
            }
            else
            {
                g.drawPolygon(cs.obstacles[i].getPolygon());
            }
        }
		
		g.setColor(Color.red);
		g.fillOval((int) goalNode.getX(), (int) goalNode.getY(), 30, 30);
		g.setColor(Color.black);
		
		for (int i = 0; i < nodes.size(); i++) 
		{
			g.drawRect((int)(nodes.get(i).getX()-(xDim/2)), (int)(nodes.get(i).getY()-(yDim/2)), (int)xDim, (int)yDim);	
		}
	}
	
	public void rotate(double angle)
	{
		double centerX;
		double centerY;
		
		double rotatedX;
		double rotatedY;
		
		Point2D[] newVertices=new Point2D[vertices.length];
		
		for(int i=0; i<vertices.length; i++)
		{
			centerX=vertices[i].getX()-center.getX();
			centerY=vertices[i].getY()-center.getY();
			
			rotatedX=centerX*Math.cos(Math.toRadians(angle))-centerY*Math.sin(Math.toRadians(angle));
			rotatedY=centerX*Math.sin(Math.toRadians(angle))+centerY*Math.cos(Math.toRadians(angle));
			
			newVertices[i]=new Point2D.Double((rotatedX+center.getX()), (rotatedY+center.getY()));
		}
		
		this.angle+=angle;
		setVertices(newVertices);
	}
	
	public void run()
	{
		nodes.add(startNode);
		
		cs=new CreateScene();
		cs.run();
		createPanel();
		
		if(!cs.fromFile)
		{
			cs.generatePolygonArray();
		}
		cs.checkObstacleCollisions();
		repaint();
		
		for (int i = 0; i < maxIterations; i++) 
		{
			double randX = Math.random() * frameX;
			double randY = Math.random() * frameY;
			Point2D randomNode = new Point2D.Double(randX, randY);

			Point2D nearestNode = findNearestNode(randomNode);

			double angle = Math.atan2(randomNode.getY() - nearestNode.getY(), randomNode.getX() - nearestNode.getX());
			double newX = nearestNode.getX() + STEP_SIZE * Math.cos(angle);
			double newY = nearestNode.getY() + STEP_SIZE * Math.sin(angle);

			Point2D newNode = new Point2D.Double(newX, newY);
			boolean collision=false;
			for(int j=0; j<cs.obstacles.length; j++)
			{
				if(CollisionChecking.circle_PolyCollisionDetection(newNode, cs.obstacles[j]))
				{
					collision=true;
				}
			}
			
			if(!collision)
			{
				nodes.add(newNode);
			}

			if (distance(newNode, goalNode) < GOAL_RADIUS) {
				System.out.println("GOAL NODE FOUND");
				goalFound = true;
				break;
			}

			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			repaint();
		}
	}
	
	public void setAngle(double angle)
	{
		this.angle=angle;
	}
	
	public void setVertices(Point2D[] vertices)
	{
		this.vertices=vertices;
	}
}
