package Project1;

import java.awt.Point;
import java.util.ArrayList;

import Project1.CreateScene.Obstacle;

//Use either SAT with voronoi region or BSP for beginning, and then other algorithm for repaint (AABB)? GJK!
public class CollisionChecking 
{
	public void checkCollision(Obstacle[] obstacles)
	{
		
	}
	
	public void findFurthestPoint(int direction)
	{
		
	}
	
	public static boolean separatingAxisTheorem(Obstacle o1, Obstacle o2)
	{
		Point[] p1=o1.getPoints();
		Point[] p2=o2.getPoints();
		
		boolean collision=false;
		
		for(int a=0; a<p1.length; a++)
		{
			int b=(a+1)%p1.length;
			
			double[] axisProj=new double[] {-(p1[b].getY()-p1[a].getY()), p1[b].getX()-p1[a].getX()};
			
			double min1=Integer.MAX_VALUE;
			double max1=Integer.MIN_VALUE;
			for(int p=0; p<p1.length; p++)
			{
				double q=(p1[p].getX()*axisProj[0]+p1[p].getY()*axisProj[1]);
				min1=Math.min(min1, q);
				max1=Math.max(max1, q);
			}
			
			double min2=Integer.MAX_VALUE;
			double max2=Integer.MIN_VALUE;
			for(int p=0; p<p2.length; p++)
			{
				double q=(p2[p].getX()*axisProj[0]+p2[p].getY()*axisProj[1]);
				min2=Math.min(min2, q);
				max2=Math.max(max2, q);
			}
			
			if(!(max2>=min1 && max1>=min2))
			{
				return collision;
			}
		}
		
		collision=true;
		return collision;
	}
	
	public static boolean separatingAxisTheorem(RigidBody2D rb, Obstacle o1)
	{
		Point[] p1=o1.getPoints();
		Point[] p2=rb.getPoints();
		
		boolean collision=false;
		
		for(int a=0; a<p1.length; a++)
		{
			int b=(a+1)%p1.length;
			
			double[] axisProj=new double[] {-(p1[b].getY()-p1[a].getY()), p1[b].getX()-p1[a].getX()};
			
			double min1=Integer.MAX_VALUE;
			double max1=Integer.MIN_VALUE;
			for(int p=0; p<p1.length; p++)
			{
				double q=(p1[p].getX()*axisProj[0]+p1[p].getY()*axisProj[1]);
				min1=Math.min(min1, q);
				max1=Math.max(max1, q);
			}
			
			double min2=Integer.MAX_VALUE;
			double max2=Integer.MIN_VALUE;
			for(int p=0; p<p2.length; p++)
			{
				double q=(p2[p].getX()*axisProj[0]+p2[p].getY()*axisProj[1]);
				min2=Math.min(min2, q);
				max2=Math.max(max2, q);
			}
			
			if(!(max2>=min1 && max1>=min2))
			{
				return collision;
			}
		}
		
		collision=true;
		return collision;
	}
	
	/*public void naiveCollisionCheck(Obstacle[] obstacles)
	{
		ArrayList<Obstacle> collisions=new ArrayList<>();
		for(int i=0; i<obstacles.length-1; i++)
		{
			for(int j=i+1; j<obstacles.length; j++)
			{
				Point[] obstacle1=obstacles[i].getPoints();
				Point[] obstacle2=obstacles[j].getPoints();
				
				for(int k=0; k<obstacle1.length-1; k++)
				{
					for(int l=0; l<obstacle2.length-1; l++)
					{
						
					}
				}
			}
		}
	}*/
}
