package Project2;

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

public class Arm1 extends JPanel implements KeyListener, ActionListener
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
		Arm1 pa=new Arm1();
		//TODO take file input
		pa.run();
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
		createPlanarArm();
		repaint();
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
	
	private void drawLink(Graphics2D g2, Point2D position1, Point2D position2, Rectangle rectangle, double radius, double angle)
	{
		g2.setColor(Color.green);

		double delta_X=position2.getX()-position1.getX();
		double delta_Y=position2.getY()-position1.getY();
		double d=Math.sqrt(delta_X*delta_X+delta_Y*delta_Y);

		AffineTransform rotation = new AffineTransform();
		
		angle=Math.toRadians(angle);
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
        	Point2D joint0_position=arm.joint0;
        	
        	Point2D joint11_position=arm.joint11;
        	Point2D joint12_position=arm.joint12;
        	
        	Point2D joint21_position=arm.joint21;
        	Point2D joint22_position=arm.joint22;
        	
        	Point2D joint31_position=arm.joint31;
        	Point2D joint32_position=arm.joint32;
        	
        	//TODO add rest of arms

        	drawJoint(g2, joint0_position, arm.jointRadius);
        	drawLink(g2, joint0_position, joint11_position, arm.rect0_11, arm.jointRadius);
        	
        	drawJoint(g2, joint11_position, arm.jointRadius);
        	drawLink(g2, joint11_position, joint12_position, arm.rect11_12, arm.jointRadius);

        	drawJoint(g2, joint12_position, arm.jointRadius);
        	/////////////////////
        	drawLink(g2, joint0_position, joint21_position, arm.rect0_21, arm.jointRadius, arm.arm21Angle);
        	
        	drawJoint(g2, joint21_position, arm.jointRadius);
        	drawLink(g2, joint21_position, joint22_position, arm.rect21_22, arm.jointRadius, arm.arm22Angle);

        	drawJoint(g2, joint22_position, arm.jointRadius);
        	/////////////////////
        	drawLink(g2, joint0_position, joint31_position, arm.rect0_31, arm.jointRadius, arm.arm31Angle);
        	
        	drawJoint(g2, joint31_position, arm.jointRadius);
        	drawLink(g2, joint31_position, joint32_position, arm.rect31_32, arm.jointRadius, arm.arm32Angle);

        	drawJoint(g2, joint32_position, arm.jointRadius);
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
  
            arm.setArm11Angle(arm.getArm11Angle()+angle);
            
            Point2D joint0_position=new Point2D.Double(arm.getJoint0().getX(), arm.getJoint0().getY());
            Point2D joint11_position=new Point2D.Double(arm.getJoint11().getX(), arm.getJoint11().getY());
            Point2D joint12_position=new Point2D.Double(arm.getJoint12().getX(), arm.getJoint12().getY());

            AffineTransform rotation = new AffineTransform();
            rotation.rotate(angleInRadians, joint0_position.getX(), joint0_position.getY());

            Point2D new_joint11_position = rotation.transform(joint11_position, null);
            Point2D new_joint12_position = rotation.transform(joint12_position, null);

            if(isPossiblePosition(joint0_position, new_joint11_position, new_joint12_position))
            {
            	arm.setJoint11(new_joint11_position);
            	arm.setJoint12(new_joint12_position);
            }
            
            repaint();
        }

        if(key==KeyEvent.VK_2)
        {
        	int angle=-15;
        	double angleInRadians = Math.toRadians(angle);

        	arm.setArm12Angle(arm.getArm12Angle()+angle);
        	
        	Point2D joint0_position=new Point2D.Double(arm.getJoint0().getX(), arm.getJoint0().getY());
        	Point2D joint11_position=new Point2D.Double(arm.getJoint11().getX(), arm.getJoint11().getY());
        	Point2D joint12_position=new Point2D.Double(arm.getJoint12().getX(), arm.getJoint12().getY());

        	AffineTransform rotation = new AffineTransform();
        	rotation.rotate(angleInRadians, joint11_position.getX(), joint11_position.getY());

        	Point2D new_joint3_position = rotation.transform(joint12_position, null);

        	if(isPossiblePosition(joint0_position, joint11_position, new_joint3_position))
        	{
        		arm.setJoint12(new_joint3_position);
        	}

        	repaint();
        }
        if(key==KeyEvent.VK_3)
        {
            int angle=15;
            double angleInRadians = Math.toRadians(angle);
            
            arm.setArm11Angle(arm.getArm11Angle()+angle);
            
            System.out.println(arm.getArm11Angle());

            Point2D joint0_position=new Point2D.Double(arm.getJoint0().getX(), arm.getJoint0().getY());
            Point2D joint11_position=new Point2D.Double(arm.getJoint11().getX(), arm.getJoint11().getY());
            Point2D joint12_position=new Point2D.Double(arm.getJoint12().getX(), arm.getJoint12().getY());

            AffineTransform rotation = new AffineTransform();
            rotation.rotate(angleInRadians, joint0_position.getX(), joint0_position.getY());

            Point2D new_joint2_position = rotation.transform(joint11_position, null);
            Point2D new_joint3_position = rotation.transform(joint12_position, null);

            if(isPossiblePosition(joint0_position, new_joint2_position, new_joint3_position))
            {
            	arm.setJoint11(new_joint2_position);
            	arm.setJoint12(new_joint3_position);
            }
            
            repaint();
        }

        if(key==KeyEvent.VK_4)
        {
        	int angle=15;
        	double angleInRadians = Math.toRadians(angle);
        	
        	arm.setArm12Angle(arm.getArm12Angle()+angle);

        	Point2D joint0_position=new Point2D.Double(arm.getJoint0().getX(), arm.getJoint0().getY());
        	Point2D joint11_position=new Point2D.Double(arm.getJoint11().getX(), arm.getJoint11().getY());
        	Point2D joint12_position=new Point2D.Double(arm.getJoint12().getX(), arm.getJoint12().getY());

        	AffineTransform rotation = new AffineTransform();
        	rotation.rotate(angleInRadians, joint11_position.getX(), joint11_position.getY());

        	Point2D new_joint3_position = rotation.transform(joint12_position, null);

        	if(isPossiblePosition(joint0_position, joint11_position, new_joint3_position))
        	{
        		arm.setJoint12(new_joint3_position);
        	}

        	repaint();
        }
        if(key==KeyEvent.VK_Q)
        {
            int angle=-15;
            double angleInRadians = Math.toRadians(angle);
            
            arm.setArm21Angle(arm.getArm21Angle()+angle);
            arm.setArm22Angle(arm.getArm22Angle()+angle);
            
            Point2D joint0_position=new Point2D.Double(arm.getJoint0().getX(), arm.getJoint0().getY());
            Point2D joint21_position=new Point2D.Double(arm.getJoint21().getX(), arm.getJoint21().getY());
            Point2D joint22_position=new Point2D.Double(arm.getJoint22().getX(), arm.getJoint22().getY());

            AffineTransform rotation = new AffineTransform();
            rotation.rotate(angleInRadians, joint0_position.getX(), joint0_position.getY());

            Point2D new_joint21_position = rotation.transform(joint21_position, null);
            Point2D new_joint22_position = rotation.transform(joint22_position, null);

            if(isPossiblePosition(joint0_position, new_joint21_position, new_joint22_position))
            {
            	arm.setJoint21(new_joint21_position);
            	arm.setJoint22(new_joint22_position);
            }
            
            repaint();
        }

        if(key==KeyEvent.VK_W)
        {
        	int angle=-15;
        	double angleInRadians = Math.toRadians(angle);
        	
        	arm.setArm22Angle(arm.getArm22Angle()+angle);

        	Point2D joint0_position=new Point2D.Double(arm.getJoint0().getX(), arm.getJoint0().getY());
        	Point2D joint21_position=new Point2D.Double(arm.getJoint21().getX(), arm.getJoint21().getY());
        	Point2D joint22_position=new Point2D.Double(arm.getJoint22().getX(), arm.getJoint22().getY());

        	AffineTransform rotation = new AffineTransform();
        	rotation.rotate(angleInRadians, joint21_position.getX(), joint21_position.getY());

        	Point2D new_joint22_position = rotation.transform(joint22_position, null);

        	if(isPossiblePosition(joint0_position, joint21_position, new_joint22_position))
        	{
        		arm.setJoint22(new_joint22_position);
        	}

        	repaint();
        }
        if(key==KeyEvent.VK_E)
        {
            int angle=+15;
            double angleInRadians = Math.toRadians(angle);
            
            arm.setArm21Angle(arm.getArm21Angle()+angle);
            arm.setArm22Angle(arm.getArm22Angle()+angle);
            
            Point2D joint0_position=new Point2D.Double(arm.getJoint0().getX(), arm.getJoint0().getY());
            Point2D joint21_position=new Point2D.Double(arm.getJoint21().getX(), arm.getJoint21().getY());
            Point2D joint22_position=new Point2D.Double(arm.getJoint22().getX(), arm.getJoint22().getY());

            AffineTransform rotation = new AffineTransform();
            rotation.rotate(angleInRadians, joint0_position.getX(), joint0_position.getY());

            Point2D new_joint21_position = rotation.transform(joint21_position, null);
            Point2D new_joint22_position = rotation.transform(joint22_position, null);

            if(isPossiblePosition(joint0_position, new_joint21_position, new_joint22_position))
            {
            	arm.setJoint21(new_joint21_position);
            	arm.setJoint22(new_joint22_position);
            }
            
            repaint();
        }

        if(key==KeyEvent.VK_R)
        {
        	int angle=+15;
        	double angleInRadians = Math.toRadians(angle);
        	
        	arm.setArm22Angle(arm.getArm22Angle()+angle);

        	Point2D joint0_position=new Point2D.Double(arm.getJoint0().getX(), arm.getJoint0().getY());
        	Point2D joint21_position=new Point2D.Double(arm.getJoint21().getX(), arm.getJoint21().getY());
        	Point2D joint22_position=new Point2D.Double(arm.getJoint22().getX(), arm.getJoint22().getY());

        	AffineTransform rotation = new AffineTransform();
        	rotation.rotate(angleInRadians, joint21_position.getX(), joint21_position.getY());

        	Point2D new_joint22_position = rotation.transform(joint22_position, null);

        	if(isPossiblePosition(joint0_position, joint21_position, new_joint22_position))
        	{
        		arm.setJoint22(new_joint22_position);
        	}

        	repaint();
        }
        if(key==KeyEvent.VK_A)
        {
            int angle=-15;
            double angleInRadians = Math.toRadians(angle);
            
            arm.setArm31Angle(arm.getArm31Angle()+angle);
            arm.setArm32Angle(arm.getArm32Angle()+angle);
            
            Point2D joint0_position=new Point2D.Double(arm.getJoint0().getX(), arm.getJoint0().getY());
            Point2D joint31_position=new Point2D.Double(arm.getJoint31().getX(), arm.getJoint31().getY());
            Point2D joint32_position=new Point2D.Double(arm.getJoint32().getX(), arm.getJoint32().getY());

            AffineTransform rotation = new AffineTransform();
            rotation.rotate(angleInRadians, joint0_position.getX(), joint0_position.getY());

            Point2D new_joint31_position = rotation.transform(joint31_position, null);
            Point2D new_joint32_position = rotation.transform(joint32_position, null);

            if(isPossiblePosition(joint0_position, new_joint31_position, new_joint32_position))
            {
            	arm.setJoint31(new_joint31_position);
            	arm.setJoint32(new_joint32_position);
            }
            
            repaint();
        }

        if(key==KeyEvent.VK_S)
        {
        	int angle=-15;
        	double angleInRadians = Math.toRadians(angle);
        	
        	arm.setArm32Angle(arm.getArm32Angle()+angle);

        	Point2D joint0_position=new Point2D.Double(arm.getJoint0().getX(), arm.getJoint0().getY());
        	Point2D joint31_position=new Point2D.Double(arm.getJoint31().getX(), arm.getJoint31().getY());
        	Point2D joint32_position=new Point2D.Double(arm.getJoint32().getX(), arm.getJoint32().getY());

        	AffineTransform rotation = new AffineTransform();
        	rotation.rotate(angleInRadians, joint31_position.getX(), joint31_position.getY());

        	Point2D new_joint32_position = rotation.transform(joint32_position, null);

        	if(isPossiblePosition(joint0_position, joint31_position, new_joint32_position))
        	{
        		arm.setJoint32(new_joint32_position);
        	}

        	repaint();
        }
        if(key==KeyEvent.VK_D)
        {
            int angle=+15;
            double angleInRadians = Math.toRadians(angle);
            
            arm.setArm31Angle(arm.getArm31Angle()+angle);
            arm.setArm32Angle(arm.getArm32Angle()+angle);
            
            Point2D joint0_position=new Point2D.Double(arm.getJoint0().getX(), arm.getJoint0().getY());
            Point2D joint31_position=new Point2D.Double(arm.getJoint31().getX(), arm.getJoint31().getY());
            Point2D joint32_position=new Point2D.Double(arm.getJoint32().getX(), arm.getJoint32().getY());

            AffineTransform rotation = new AffineTransform();
            rotation.rotate(angleInRadians, joint0_position.getX(), joint0_position.getY());

            Point2D new_joint31_position = rotation.transform(joint31_position, null);
            Point2D new_joint32_position = rotation.transform(joint32_position, null);

            if(isPossiblePosition(joint0_position, new_joint31_position, new_joint32_position))
            {
            	arm.setJoint31(new_joint31_position);
            	arm.setJoint32(new_joint32_position);
            }
            
            repaint();
        }

        if(key==KeyEvent.VK_F)
        {
        	int angle=+15;
        	double angleInRadians = Math.toRadians(angle);
        	
        	arm.setArm32Angle(arm.getArm32Angle()+angle);

        	Point2D joint0_position=new Point2D.Double(arm.getJoint0().getX(), arm.getJoint0().getY());
        	Point2D joint31_position=new Point2D.Double(arm.getJoint31().getX(), arm.getJoint31().getY());
        	Point2D joint32_position=new Point2D.Double(arm.getJoint32().getX(), arm.getJoint32().getY());

        	AffineTransform rotation = new AffineTransform();
        	rotation.rotate(angleInRadians, joint31_position.getX(), joint31_position.getY());

        	Point2D new_joint32_position = rotation.transform(joint32_position, null);

        	if(isPossiblePosition(joint0_position, joint31_position, new_joint32_position))
        	{
        		arm.setJoint32(new_joint32_position);
        	}

        	repaint();
        }
        
        repaint();
    }
    
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public class Arm
	{
		Point2D joint0=new Point2D.Double(400, 400);
		
		Point2D joint11=new Point2D.Double(400, 560);
		Point2D joint12=new Point2D.Double(400, 660);
		
		Point2D joint21=new Point2D.Double(286.86, 286.86);
		Point2D joint22=new Point2D.Double(216.15, 216.15);
		
		Point2D joint31=new Point2D.Double(513.14, 286.86);
		Point2D joint32=new Point2D.Double(583.85, 216.15);
		
		double jointRadius=20;
		
		Rectangle rect0_11=new Rectangle(420, 400, 120, 40);
		Rectangle rect11_12=new Rectangle(420, 400, 60, 40);
		
		Rectangle rect0_21=new Rectangle(420, 400, 120, 40);
		Rectangle rect21_22=new Rectangle(420, 400, 60, 40);
		
		Rectangle rect0_31=new Rectangle(420, 400, 120, 40);
		Rectangle rect31_32=new Rectangle(420, 400, 60, 40);
		
		double arm11Angle=90;
		double arm12Angle=90;
		
		double arm21Angle=225;
		double arm22Angle=225;
		
		double arm31Angle=315;
		double arm32Angle=315;
		
		public double getArm11Angle()
		{
			return arm11Angle;
		}
		
		public double getArm12Angle()
		{
			return arm12Angle;
		}
		
		public double getArm21Angle()
		{
			return arm21Angle;
		}
		
		public double getArm22Angle()
		{
			return arm22Angle;
		}
		
		public double getArm31Angle()
		{
			return arm31Angle;
		}
		
		public double getArm32Angle()
		{
			return arm32Angle;
		}
		
		public Point2D getJoint0()
		{
			return joint0;
		}
		
		public Point2D getJoint11()
		{
			return joint11;
		}
		
		public Point2D getJoint12()
		{
			return joint12;
		}
		
		public Point2D getJoint21()
		{
			return joint21;
		}
		
		public Point2D getJoint22()
		{
			return joint22;
		}
		
		public Point2D getJoint31()
		{
			return joint31;
		}
		
		public Point2D getJoint32()
		{
			return joint32;
		}
		
		public double getJointRadius()
		{
			return jointRadius;
		}
		
		public Rectangle getRect0_11()
		{
			return rect0_11;
		}
		
		public Rectangle getRect11_12()
		{
			return rect11_12;
		}
		
		public Rectangle getRect0_21()
		{
			return rect0_21;
		}
		
		public Rectangle getRect21_22()
		{
			return rect21_22;
		}
		
		public Rectangle getRect0_31()
		{
			return rect0_31;
		}
		
		public Rectangle getRect31_32()
		{
			return rect31_32;
		}
		
		public void setArm11Angle(double arm11Angle)
		{
			this.arm11Angle=arm11Angle;
		}
		
		public void setArm12Angle(double arm12Angle)
		{
			this.arm12Angle=arm12Angle;
		}
		
		public void setArm21Angle(double arm21Angle)
		{
			this.arm21Angle=arm21Angle;
		}
		
		public void setArm22Angle(double arm22Angle)
		{
			this.arm22Angle=arm22Angle;
		}
		
		public void setArm31Angle(double arm31Angle)
		{
			this.arm31Angle=arm31Angle;
		}
		
		public void setArm32Angle(double arm32Angle)
		{
			this.arm32Angle=arm32Angle;
		}
		
		public void setJoint0(Point2D joint0)
		{
			this.joint0=joint0;
		}
		
		public void setJoint11(Point2D joint11)
		{
			this.joint11=joint11;
		}
		
		public void setJoint12(Point2D joint12)
		{
			this.joint12=joint12;
		}
		
		public void setJoint21(Point2D joint21)
		{
			this.joint21=joint21;
		}
		
		public void setJoint22(Point2D joint22)
		{
			this.joint22=joint22;
		}
		
		public void setJoint31(Point2D joint31)
		{
			this.joint31=joint31;
		}
		
		public void setJoint32(Point2D joint32)
		{
			this.joint32=joint32;
		}
	}
}
