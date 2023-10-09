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
	
	private void drawJoint(Graphics2D g2, Point2D position, double radius)
    {
		g2.setColor(Color.blue);
		g2.fillOval((int)(position.getX()-radius),
            		(int)(position.getY()-radius),
            		(int)(radius*2),
            		(int)(radius*2));     
    }

	private void drawLink(Graphics2D g2, Point2D position1, Point2D position2, Rectangle rectangle, double radius)
	{
		g2.setColor(Color.green);

		double delta_X=position2.getX()-position1.getX();
		double delta_Y=position2.getY()-position1.getY();
		double d=Math.sqrt(delta_X*delta_X+delta_Y*delta_Y);
		double angle=Math.acos(delta_X/d);
    
		if(delta_Y<0)
			angle=Math.PI*2-angle;

		AffineTransform rotation = new AffineTransform();

		rotation.rotate(angle, position1.getX(), position1.getY());

		Rectangle link=new Rectangle((int)(position1.getX()+radius),
									(int)(position1.getY()-rectangle.height/2),
									rectangle.width,
									rectangle.height);

		Point2D[] points= {new Point2D.Double(link.x, link.y),
                         	new Point2D.Double(link.x+link.width, link.y),
                         	new Point2D.Double(link.x+link.width, link.y+link.height),
                         	new Point2D.Double(link.x, link.y+link.height)};

		Point2D[] rotated_points= new Point2D[4];
		int[] X_points=new int[4];
		int[] Y_points=new int[4];

		for(int i=0; i<4; i++)
        {
			rotated_points[i]=rotation.transform(points[i], null);
			X_points[i]=(int)rotated_points[i].getX();
			Y_points[i]=(int)rotated_points[i].getY();
        }

		g2.fillPolygon(X_points, Y_points, 4);
    }
	
	private boolean isPossiblePosition(Point2D joint1_position, Point2D new_joint2_position, Point2D new_joint3_position)
	{
		// TODO !!!

		// Add Collision Checking here !!!
		return true;  // empty default checking allowing every position. Change it !
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
        	Point2D joint1_position=arm.joint1;
        	Point2D joint2_position=arm.joint2;
        	Point2D joint3_position=arm.joint3;

        	drawJoint(g2, joint1_position, arm.jointRadius);
        	drawLink(g2, joint1_position, joint2_position, arm.rect1, arm.jointRadius);
        	
        	drawJoint(g2, joint2_position, arm.jointRadius);
        	drawLink(g2, joint2_position, joint3_position, arm.rect2, arm.jointRadius);

        	drawJoint(g2, joint3_position, arm.jointRadius);
        }
    }
	
	public void createPlanarArm()
	{
		arm=new Arm();
		
		repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void keyPressed(KeyEvent e)
    {
		int key=e.getKeyCode();
    
        if(key==KeyEvent.VK_1)
        {
            int angle=-15;
            double angleInRadians = Math.toRadians(angle);

            Point2D joint1_position=new Point2D.Double(arm.getJoint1().getX(), arm.getJoint1().getY());
            Point2D joint2_position=new Point2D.Double(arm.getJoint2().getX(), arm.getJoint2().getY());
            Point2D joint3_position=new Point2D.Double(arm.getJoint3().getX(), arm.getJoint3().getY());

            AffineTransform rotation = new AffineTransform();
            rotation.rotate(angleInRadians, joint1_position.getX(), joint1_position.getY());

            Point2D new_joint2_position = rotation.transform(joint2_position, null);
            Point2D new_joint3_position = rotation.transform(joint3_position, null);

            if(isPossiblePosition(joint1_position, new_joint2_position, new_joint3_position))
            {
            	arm.setJoint2(new_joint2_position);
            	arm.setJoint3(new_joint3_position);
            }
            
            repaint();
        }

        if(key==KeyEvent.VK_2)
        {
        	int angle=-15;
        	double angleInRadians = Math.toRadians(angle);

        	Point2D joint1_position=new Point2D.Double(arm.getJoint1().getX(), arm.getJoint1().getY());
        	Point2D joint2_position=new Point2D.Double(arm.getJoint2().getX(), arm.getJoint2().getY());
        	Point2D joint3_position=new Point2D.Double(arm.getJoint3().getX(), arm.getJoint3().getY());

        	AffineTransform rotation = new AffineTransform();
        	rotation.rotate(angleInRadians, joint2_position.getX(), joint2_position.getY());

        	Point2D new_joint3_position = rotation.transform(joint3_position, null);

        	if(isPossiblePosition(joint1_position, joint2_position, new_joint3_position))
        	{
        		arm.setJoint3(new_joint3_position);
        	}

        	repaint();
        }
        if(key==KeyEvent.VK_3)
        {
        	Point2D joint1_position=new Point2D.Double(arm.getJoint1().getX(), arm.getJoint1().getY());
        	Point2D joint2_position=new Point2D.Double(arm.getJoint2().getX(), arm.getJoint2().getY());
        	Point2D joint3_position=new Point2D.Double(arm.getJoint3().getX(), arm.getJoint3().getY());
        	
        	int angle=15;
        	double angleInRadians = Math.toRadians(angle);

        	AffineTransform rotation1 = new AffineTransform();     
        	rotation1.rotate(angleInRadians, joint2_position.getX(), joint2_position.getY());

        	Point2D new_joint3_position = rotation1.transform(joint3_position, null);
        	
        	angle=-angle;
        	angleInRadians = Math.toRadians(angle);

        	AffineTransform rotation2 = new AffineTransform();     
        	rotation2.rotate(angleInRadians, joint1_position.getX(), joint1_position.getY());

        	Point2D new_joint2_position = rotation2.transform(joint2_position, null);
        	new_joint3_position = rotation2.transform(new_joint3_position, null);

        	if(isPossiblePosition(joint1_position, new_joint2_position, new_joint3_position))
        	{
        		arm.setJoint2(new_joint2_position);
        		arm.setJoint3(new_joint3_position);
        	}

        	repaint();

        }

        if(key==KeyEvent.VK_4)
        {
        	int angle=15;
        	double angleInRadians = Math.toRadians(angle);

        	Point2D joint1_position=new Point2D.Double(arm.getJoint1().getX(), arm.getJoint1().getY());
        	Point2D joint2_position=new Point2D.Double(arm.getJoint2().getX(), arm.getJoint2().getY());
        	Point2D joint3_position=new Point2D.Double(arm.getJoint3().getX(), arm.getJoint3().getY());

        	AffineTransform rotation = new AffineTransform();
        	rotation.rotate(angleInRadians, joint1_position.getX(), joint1_position.getY());

        	Point2D new_joint2_position = rotation.transform(joint2_position, null);
        	Point2D new_joint3_position = rotation.transform(joint3_position, null);

        	if(isPossiblePosition(joint1_position, new_joint2_position, new_joint3_position))
        	{
        		arm.setJoint2(new_joint2_position);
        		arm.setJoint3(new_joint3_position);
        	}

        	repaint();

        }
        if(key==KeyEvent.VK_5)
        {

        	int angle=15;
        	double angleInRadians = Math.toRadians(angle);

        	Point2D joint1_position=new Point2D.Double(arm.getJoint1().getX(), arm.getJoint1().getY());
        	Point2D joint2_position=new Point2D.Double(arm.getJoint2().getX(), arm.getJoint2().getY());
        	Point2D joint3_position=new Point2D.Double(arm.getJoint3().getX(), arm.getJoint3().getY());

        	AffineTransform rotation = new AffineTransform();
        	rotation.rotate(angleInRadians, joint2_position.getX(), joint2_position.getY());

        	Point2D new_joint3_position = rotation.transform(joint3_position, null);

        	if(isPossiblePosition(joint1_position, joint2_position, new_joint3_position))
        	{
        		arm.setJoint3(new_joint3_position);
        	}

        	repaint();
        }

        if(key==KeyEvent.VK_6)
        {
        	Point2D joint1_position=new Point2D.Double(arm.getJoint1().getX(), arm.getJoint1().getY());
        	Point2D joint2_position=new Point2D.Double(arm.getJoint2().getX(), arm.getJoint2().getY());
        	Point2D joint3_position=new Point2D.Double(arm.getJoint3().getX(), arm.getJoint3().getY());

        	int angle=-15;
        	double angleInRadians = Math.toRadians(angle);

        	AffineTransform rotation1 = new AffineTransform();     
        	rotation1.rotate(angleInRadians, joint2_position.getX(), joint2_position.getY());

        	Point2D new_joint3_position = rotation1.transform(joint3_position, null);

        	angle=-angle;
        	angleInRadians = Math.toRadians(angle);

        	AffineTransform rotation2 = new AffineTransform();     
        	rotation2.rotate(angleInRadians, joint1_position.getX(), joint1_position.getY());

        	Point2D new_joint2_position = rotation2.transform(joint2_position, null);
        	new_joint3_position = rotation2.transform(new_joint3_position, null);

        	if(isPossiblePosition(joint1_position, new_joint2_position, new_joint3_position))
        	{
        		arm.setJoint2(new_joint2_position);
        		arm.setJoint3(new_joint3_position);
        	}

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
