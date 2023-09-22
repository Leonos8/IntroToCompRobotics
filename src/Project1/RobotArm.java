package Project1;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class RobotArm extends JPanel implements KeyListener
{
	static JFrame frame;
	//static JPanel panel;
	
	static int frameX=640;
	static int frameY=480;
	
	//static int bodyX=frameX/2-10;
	//static int bodyY=frameY/2-10;
	
	static int j1X=frameX/2-50;
	static int j1Y=frameY/2-50;
	
	static int[][] joints=new int[][] {{j1X, j1Y}, {j1X+100, j1Y}, {j1X+175, j1Y}};
	
	public static void main(String[] args)
	{
		RobotArm ra=new RobotArm();
		ra.createDisplay();
	}
	
	public void createDisplay()
	{
		frame=new JFrame("Rigid Body");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
        frame.setSize(640, 480);
        frame.setVisible(true);
        frame.addKeyListener(this);
        
        this.setBackground(Color.white);
        this.setLayout(null);
        this.setVisible(true);
        //addKeyListener(this);
        frame.getContentPane().add(this);
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		Graphics2D g2=(Graphics2D)g;
		
		g2.setStroke(new BasicStroke(3));
		
		g2.fillOval(joints[0][0], joints[0][1], 15, 15);
		g2.drawLine(joints[0][0]+7, joints[0][1]+7, joints[1][0]+7, joints[1][1]+7);
		
		g2.fillOval(joints[1][0], joints[1][1], 15, 15);
		g2.drawLine(joints[1][0]+7, joints[1][1]+7, joints[2][0]+7, joints[2][1]+7);
		
		g2.fillOval(joints[2][0], joints[2][1], 15, 15);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		int key=e.getKeyCode();
		
		if(key==KeyEvent.VK_1)
		{
			int angle=-10;
			
			Point2D result = new Point2D.Double();
		    AffineTransform rotation = new AffineTransform();
		    double angleInRadians = (angle * Math.PI / 180);
		    rotation.rotate(angleInRadians, joints[0][0], joints[0][1]);
		    rotation.transform(new Point(joints[1][0], joints[1][1]), result);
		    
		    joints[1][0]=(int)result.getX();
		    joints[1][1]=(int)result.getY();
			
		    Point2D result2 = new Point2D.Double();
		    AffineTransform rotation2 = new AffineTransform();
		    double angleInRadians2 = (angle * Math.PI / 180);
		    rotation2.rotate(angleInRadians2, joints[0][0], joints[0][1]);
		    rotation.transform(new Point(joints[2][0], joints[2][1]), result2);
		    
		    joints[2][0]=(int)result2.getX();
		    joints[2][1]=(int)result2.getY();
		}
		if(key==KeyEvent.VK_2)
		{
			int angle=-10;
			
			Point2D result = new Point2D.Double();
		    AffineTransform rotation = new AffineTransform();
		    double angleInRadians = (angle * Math.PI / 180);
		    rotation.rotate(angleInRadians, joints[1][0], joints[1][1]);
		    rotation.transform(new Point(joints[2][0], joints[2][1]), result);
		    
		    joints[2][0]=(int)result.getX();
		    joints[2][1]=(int)result.getY();
		}
		if(key==KeyEvent.VK_4)
		{
			int angle=10;
			
			Point2D result = new Point2D.Double();
		    AffineTransform rotation = new AffineTransform();
		    double angleInRadians = (angle * Math.PI / 180);
		    rotation.rotate(angleInRadians, joints[0][0], joints[0][1]);
		    rotation.transform(new Point(joints[1][0], joints[1][1]), result);
		    
		    joints[1][0]=(int)result.getX();
		    joints[1][1]=(int)result.getY();
						
		    Point2D result2 = new Point2D.Double();
		    AffineTransform rotation2 = new AffineTransform();
		    double angleInRadians2 = (angle * Math.PI / 180);
		    rotation2.rotate(angleInRadians2, joints[0][0], joints[0][1]);
		    rotation.transform(new Point(joints[2][0], joints[2][1]), result2);
		    
		    joints[2][0]=(int)result2.getX();
		    joints[2][1]=(int)result2.getY();
		}
		if(key==KeyEvent.VK_5)
		{
			int angle=10;
			
			Point2D result = new Point2D.Double();
		    AffineTransform rotation = new AffineTransform();
		    double angleInRadians = (angle * Math.PI / 180);
		    rotation.rotate(angleInRadians, joints[1][0], joints[1][1]);
		    rotation.transform(new Point(joints[2][0], joints[2][1]), result);
		    
		    joints[2][0]=(int)result.getX();
		    joints[2][1]=(int)result.getY();
		}
		
		/*if(key==KeyEvent.VK_LEFT)
		{
			if(bodyX>=10)
			{
				bodyX-=10;
			}
		}
		if(key==KeyEvent.VK_RIGHT)
		{
			if(bodyX<=frameX-50)
			{
				bodyX+=10;
			}
		}
		if(key==KeyEvent.VK_UP)
		{
			if(bodyY>=10)
			{
				bodyY-=10;
			}
		}
		if(key==KeyEvent.VK_DOWN)
		{
			if(bodyY<=frameY-70)
			{
				bodyY+=10;
			}
		}*/

		repaint();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}
}
