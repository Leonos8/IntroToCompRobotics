package Project1;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class PlanarArm extends JPanel implements KeyListener, ActionListener
{
	JFrame frame;
	
	JMenuBar mb;
	
	JMenu fileMenu;
	 
	JMenuItem saveAsItem;
	
	Arm arm;
	
	CreateScene cs;
	
	int frameX=800;
	int frameY=800;
	
	public static void main(String[] args)
	{
		PlanarArm pa=new PlanarArm();
		
		pa.run();
	}
	
	public void run()
	{
		cs=new CreateScene();
		
		cs.getInput();
		createPanel();
		if(!cs.fromFile)
		{
			cs.generatePolygonArray();
		}
		cs.checkObstacleCollisions();
		
		createPlanarArm();
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
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		Graphics2D g2=(Graphics2D)g;
		
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
		
		if(arm!=null)
		{
			g2.setColor(Color.blue);
			g2.fillOval((int)(arm.joint1.getX()-(arm.jointRadius)), (int)(arm.joint1.getY()-(arm.jointRadius)), 
					(int)(arm.jointRadius*2), (int)(arm.jointRadius*2));
			
			g2.setColor(Color.green);
			g.fillRect((int)arm.rect1.getX(), (int)(arm.rect1.getY()-(arm.rect1.getHeight()/2)), (int)arm.rect1.getWidth(), (int)arm.rect1.getHeight());
			
			g2.setColor(Color.blue);
			g2.fillOval((int)(arm.joint2.getX()-(arm.jointRadius)), (int)(arm.joint2.getY()-(arm.jointRadius)), 
					(int)(arm.jointRadius*2), (int)(arm.jointRadius*2));
			
			g2.setColor(Color.green);
			g.fillRect((int)arm.rect2.getX(), (int)(arm.rect2.getY()-(arm.rect2.getHeight()/2)), (int)arm.rect2.getWidth(), (int)arm.rect2.getHeight());
			
			g2.setColor(Color.blue);
			g2.fillOval((int)(arm.joint3.getX()-(arm.jointRadius)), (int)(arm.joint3.getY()-(arm.jointRadius)), 
					(int)(arm.jointRadius*2), (int)(arm.jointRadius*2));
		}
		
		//g2.setStroke(new BasicStroke(3));
		
		//g2.fillOval(joints[0][0], joints[0][1], 15, 15);
		//g2.drawLine(joints[0][0]+7, joints[0][1]+7, joints[1][0]+7, joints[1][1]+7);
		
		//g2.fillOval(joints[1][0], joints[1][1], 15, 15);
		//g2.drawLine(joints[1][0]+7, joints[1][1]+7, joints[2][0]+7, joints[2][1]+7);
		
		//g2.fillOval(joints[2][0], joints[2][1], 15, 15);
	}
	
	public void createPlanarArm()
	{
		arm=new Arm();
		
		repaint();
	}
	
	public void rotate(double angle, Rectangle rect)
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

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) 
	{		
		int key=e.getKeyCode();
		
		if(key==KeyEvent.VK_1)
		{
			int angle=-15;
			
			System.out.println('t');
			
			Point2D result = new Point2D.Double();
		    AffineTransform rotation = new AffineTransform();
		    double angleInRadians = (angle * Math.PI / 180);
		    rotation.rotate(angleInRadians, arm.getJoint1().getX(), arm.getJoint1().getY());
		    rotation.transform(new Point2D.Double(arm.getJoint2().getX(), arm.getJoint2().getY()), result);
		    
		    arm.setJoint2(result);
			
		    Point2D result2 = new Point2D.Double();
		    AffineTransform rotation2 = new AffineTransform();
		    double angleInRadians2 = (angle * Math.PI / 180);
		    rotation2.rotate(angleInRadians2, arm.getJoint1().getX(), arm.getJoint1().getY());
		    rotation.transform(new Point2D.Double(arm.getJoint3().getX(), arm.getJoint3().getY()), result2);
		    
		    arm.setJoint3(result2);
		    
		    repaint();
		}
		if(key==KeyEvent.VK_2)
		{
			int angle=-15;
			
			Point2D result = new Point2D.Double();
		    AffineTransform rotation = new AffineTransform();
		    double angleInRadians = (angle * Math.PI / 180);
		    rotation.rotate(angleInRadians, arm.getJoint2().getX(), arm.getJoint2().getY());
		    rotation.transform(new Point2D.Double(arm.getJoint3().getX(), arm.getJoint3().getY()), result);
		    
		    arm.setJoint3(result);
		    
		    repaint();
		}
		if(key==KeyEvent.VK_4)
		{
			int angle=15;
			
			Point2D result = new Point2D.Double();
		    AffineTransform rotation = new AffineTransform();
		    double angleInRadians = (angle * Math.PI / 180);
		    rotation.rotate(angleInRadians, arm.getJoint1().getX(), arm.getJoint1().getY());
		    rotation.transform(new Point2D.Double(arm.getJoint2().getX(), arm.getJoint2().getY()), result);
		    
		    arm.setJoint2(result);
						
		    Point2D result2 = new Point2D.Double();
		    AffineTransform rotation2 = new AffineTransform();
		    double angleInRadians2 = (angle * Math.PI / 180);
		    rotation2.rotate(angleInRadians2, arm.getJoint1().getX(), arm.getJoint1().getY());
		    rotation.transform(new Point2D.Double(arm.getJoint3().getX(), arm.getJoint3().getY()), result2);
		    
		    arm.setJoint3(result2);
		    
		    repaint();
		}
		if(key==KeyEvent.VK_5)
		{
			int angle=15;
			
			Point2D result = new Point2D.Double();
		    AffineTransform rotation = new AffineTransform();
		    double angleInRadians = (angle * Math.PI / 180);
		    rotation.rotate(angleInRadians, arm.getJoint2().getX(), arm.getJoint2().getY());
		    rotation.transform(new Point2D.Double(arm.getJoint3().getX(), arm.getJoint3().getY()), result);
		    
		    arm.setJoint3(result);
		    
		    repaint();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public class Arm
	{
		Point2D joint1=new Point2D.Double(400, 400);
		Point2D joint2=new Point2D.Double(540, 400);
		Point2D joint3=new Point2D.Double(740, 400);
		
		double jointRadius=20;
		
		Rectangle rect1=new Rectangle(420, 400, 100, 40);
		Rectangle rect2=new Rectangle(560, 400, 160, 40);
		
		public Point2D getJoint1()
		{
			return joint1;
		}
		
		public Point2D getJoint2()
		{
			return joint2;
		}
		
		public Point2D getJoint3()
		{
			return joint2;
		}
		
		public double getJointRadius()
		{
			return jointRadius;
		}
		
		public Rectangle getRect1()
		{
			return rect1;
		}
		
		public Rectangle getRect2()
		{
			return rect2;
		}
		
		public void setJoint1(Point2D joint1)
		{
			this.joint1=joint1;
		}
		
		public void setJoint2(Point2D joint2)
		{
			this.joint2=joint2;
		}
		
		public void setJoint3(Point2D joint3)
		{
			this.joint3=joint3;
		}
	}
}
