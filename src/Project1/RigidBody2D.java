package Project1;

import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

public class RigidBody2D
{
	double xDim; //.2
	double yDim; //.1
	Point center;
	Point[] vertices;
	
	int frameX=800;
	int frameY=800;
	
	public RigidBody2D(Point[] vertices, Point center)
	{
		this.vertices=vertices;
		this.center=center;
	}
	
	public void createRigidBody()
	{
		this.xDim=80;
		this.yDim=40;
	}
	
	public Point[] getPoints()
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
	
	public void rotate(int angle)
	{
		
		
		/*double newXTL=vertices[0].getX()-(vertices[0].getX()*Math.cos(Math.toRadians(angle)))
				+(vertices[0].getY()*Math.sin(Math.toRadians(angle)));
		double newYTL=vertices[0].getY()-(vertices[0].getX()*Math.sin(Math.toRadians(angle)))
				-(vertices[0].getY()*Math.cos(Math.toRadians(angle)));
		
		double newXTR=vertices[1].getX()-(vertices[1].getX()*Math.cos(Math.toRadians(angle)))
				+(vertices[1].getY()*Math.sin(Math.toRadians(angle)));
		double newYTR=vertices[1].getY()-(vertices[1].getX()*Math.sin(Math.toRadians(angle)))
				-(vertices[1].getY()*Math.cos(Math.toRadians(angle)));
		
		double newXBL=vertices[2].getX()-(vertices[2].getX()*Math.cos(Math.toRadians(angle)))
				+(vertices[2].getY()*Math.sin(Math.toRadians(angle)));
		double newYBL=vertices[2].getY()-(vertices[2].getX()*Math.sin(Math.toRadians(angle)))
				-(vertices[2].getY()*Math.cos(Math.toRadians(angle)));
		
		double newXBR=vertices[3].getX()-(vertices[3].getX()*Math.cos(Math.toRadians(angle)))
				+(vertices[3].getY()*Math.sin(Math.toRadians(angle)));
		double newYBR=vertices[3].getY()-(vertices[3].getX()*Math.sin(Math.toRadians(angle)))
				-(vertices[3].getY()*Math.cos(Math.toRadians(angle)));
		
		setVertices(new Point[] {new Point((int)newXTL, (int)newYTL), new Point((int)newXTR, (int)newYTR),
				new Point((int)newXBL, (int)newYBL), new Point((int)newXBR, (int)newYBR)});*/
		
		//AffineTransform transform = new AffineTransform();
		//transform.rotate(angle, vertices[0].getX() + xDim/2, vertices[0].getY() + yDim/2);
		//transform.transform(center, center);
		
		
		/*double dx = vertices[0].getX()-center.getX();
		double dy = vertices[0].getY()-center.getY();
		double newXTL = center.getX()-dx*Math.cos(angle)+dy*Math.sin(angle);
		double newYTL = center.getX()-dx*Math.sin(angle)-dy*Math.cos(angle);
		
		dx = vertices[1].getX()-center.getX();
		dy = vertices[1].getY()-center.getY();
		double newXTR = center.getX()-dx*Math.cos(angle)+dy*Math.sin(angle);
		double newYTR = center.getX()-dx*Math.sin(angle)-dy*Math.cos(angle);
		
		dx = vertices[2].getX()-center.getX();
		dy = vertices[2].getY()-center.getY();
		double newXBL = center.getX()-dx*Math.cos(angle)+dy*Math.sin(angle);
		double newYBL = center.getX()-dx*Math.sin(angle)-dy*Math.cos(angle);
		
		dx = vertices[3].getX()-center.getX();
		dy = vertices[3].getY()-center.getY();
		double newXBR = center.getX()-dx*Math.cos(angle)+dy*Math.sin(angle);
		double newYBR = center.getX()-dx*Math.sin(angle)-dy*Math.cos(angle);
		
		setVertices(new Point[] {new Point((int)newXTL, (int)newYTL), new Point((int)newXTR, (int)newYTR),
				new Point((int)newXBL, (int)newYBL), new Point((int)newXBR, (int)newYBR)});*/
		
		/*Point2D result = new Point2D.Double();
	    AffineTransform rotation = new AffineTransform();
	    double angleInRadians = (angle * Math.PI / 180);
	    //rotation.rotate(angleInRadians, vertices[2].getX(), vertices[2].getY());
	    rotation.rotate(angleInRadians, center.getX(), center.getY());
	    rotation.transform(new Point((int)vertices[0].getX(), (int)vertices[0].getY()), result);
	    
	    vertices[0].setLocation((int)result.getX(), (int)result.getY());
	    
	    Point2D result2 = new Point2D.Double();
	    AffineTransform rotation2 = new AffineTransform();
	    double angleInRadians2 = (angle * Math.PI / 180);
	    //rotation2.rotate(angleInRadians2, vertices[3].getX(), vertices[3].getY());
	    rotation2.rotate(angleInRadians, center.getX(), center.getY());
	    rotation2.transform(new Point((int)vertices[1].getX(), (int)vertices[1].getY()), result);
	    
	    vertices[1].setLocation((int)result2.getX(), (int)result2.getY());*/
	}
	
	public void setVertices(Point[] vertices)
	{
		this.vertices=vertices;
	}
}
