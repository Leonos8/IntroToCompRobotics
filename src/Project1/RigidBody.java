package Project1;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class RigidBody extends JPanel implements KeyListener
{
	static JFrame frame;
	//static JPanel panel;
	
	static int frameX=640;
	static int frameY=480;
	
	static int bodyX=frameX/2-10;
	static int bodyY=frameY/2-10;
	
	public static void main(String[] args)
	{
		RigidBody rb=new RigidBody();
		rb.createDisplay();
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
		
		g.fillOval(bodyX, bodyY, 15, 15);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		int key=e.getKeyCode();
		
		if(key==KeyEvent.VK_LEFT)
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
		}

		repaint();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}
}
