package Project1;

import java.awt.geom.Point2D;

public class RigidBody2D
{
	double xDim; //.2
	double yDim; //.1
	
	double angle;
	
	Point2D center;
	Point2D[] vertices;
	
	int frameX=800;
	int frameY=800;
	
	public RigidBody2D(Point2D[] vertices, Point2D center, double angle)
	{
		this.vertices=vertices;
		this.center=center;
		this.angle=angle;
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
	
	public double getX()
	{
		return xDim;
	}
	
	public double getY()
	{
		return yDim;
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
	
	public void setAngle(double angle)
	{
		this.angle=angle;
	}
	
	public void setVertices(Point2D[] vertices)
	{
		this.vertices=vertices;
	}
}
