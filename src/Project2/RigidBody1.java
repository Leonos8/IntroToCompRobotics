package Project2;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Point2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class RigidBody1 extends JPanel implements KeyListener
{
	JFrame frame = new JFrame();
	
	double xDim; //.2
	double yDim; //.1
	
	static double angle;
	
	Point2D center;
	Point2D[] vertices;
	
	int frameX=800;
	int frameY=800;
	
	CreateScene cs;
	
	static RigidBody1 rb;
	
	public static void main(String[] args)
	{
		rb=new RigidBody1();
		rb.rotate(angle);
		rb.run();
	}
	
	public RigidBody1()
	{		
		double centerX=(Math.random()*frameX);
		double centerY=(Math.random()*frameY);
		double angle=Math.random()*360;
		
		this.center=new Point2D.Double(centerX, centerY);
			
		double x=80;
		double y=40;
			
		Point2D[] points=new Point2D[4];
		points[0]=new Point2D.Double(centerX-(x/2), centerY-(y/2));
		points[1]=new Point2D.Double(centerX+(x/2), centerY-(y/2));
		points[2]=new Point2D.Double(centerX+(x/2), centerY+(y/2));
		points[3]=new Point2D.Double(centerX-(x/2), centerY+(y/2));
		
		this.vertices=points;
		this.angle=angle;
		}
	
	public void createPanel() {
		frame = new JFrame("Assignment 1");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setSize(frameX, frameY);
		// frame.setVisible(true);

		this.setBackground(Color.white);
		addKeyListener(this);
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
		
		if(rb!=null)
		{
			g.fillPolygon(getXPoints(), getYPoints(), vertices.length);
		}
		
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
		cs=new CreateScene();
		cs.run();
		createPanel();
		
		if(!cs.fromFile)
		{
			cs.generatePolygonArray();
		}
		cs.checkObstacleCollisions();
		repaint();
	}
	
	public void setAngle(double angle)
	{
		this.angle=angle;
	}
	
	public void setVertices(Point2D[] vertices)
	{
		this.vertices=vertices;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) 
	{
		System.out.println("TEST");
		boolean collision=false;
		int move=-1;
		
		if(e.getKeyCode()==KeyEvent.VK_UP)
		{			
			move=0;
			
			double step=10;
			
			rb.move(step);
			repaint();
		}
		if(e.getKeyCode()==KeyEvent.VK_DOWN)
		{
			move=1;
			
			double step=-10;
			
			rb.move(step);
			repaint();
		}
		if(e.getKeyCode()==KeyEvent.VK_LEFT)
		{
			move=2;
			
			int angle=-15;
				
			System.out.println("LEFT");
				
			rb.rotate(angle);
			repaint();
		}
		if(e.getKeyCode()==KeyEvent.VK_RIGHT)
		{
			move=3;
			
			int angle=15;
				
			System.out.println("RIGHT");
				
			rb.rotate(angle);
			repaint();
		}
		
		/*if(rb.getX()>790 || rb.getX()<10 || rb.getY()>790 || rb.getX()<10)
		{
			collision=true;
		}*/
		
		if(collision!=true)
		{
			for(int i=0; i<cs.obstacles.length; i++)
			{	
				if(CollisionChecking.separatingAxisTheorem(rb, cs.obstacles[i])
						)
				{
					collision=true;
				}
			}
		}
		
		if(collision)
		{
			if(move==0)
			{
				double step=-10;
				
				rb.move(step);
				repaint();
			}
			if(move==1)
			{
				double step=10;
				
				rb.move(step);
				repaint();
			}
			if(move==2)
			{
				int angle=15;
				
				rb.rotate(angle);
				repaint();
			}
			if(move==3)
			{
				int angle=-15;
				
				rb.rotate(angle);
				repaint();
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
