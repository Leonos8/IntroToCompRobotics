package Project2;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JFrame;
import javax.swing.JPanel;

//TODO Show the path

public class Arm4 extends JPanel {
	JFrame frame = new JFrame();

	int frameX = 800;
	int frameY = 800;

	double STEP_SIZE = 30.0;
	double GOAL_RADIUS = 30;

	boolean goalFound = false;

	ArrayList<Point2D> nodes = new ArrayList<>();
	ArrayList<Point2D> path = new ArrayList<>();

	Point2D startNode;
	Point2D goalNode;

	public static void main(String[] args) {
		int startX = 300;
		int startY = 300;
		int goalX = 150;
		int goalY = 400;

		Arm4 arm = new Arm4();

		arm.startNode = new Point2D.Double(startX, startY);
		arm.goalNode = new Point2D.Double(goalX, goalY);

		arm.nodes.add(arm.startNode);

		int maxIterations = 1000;

		arm.run(maxIterations);
	}

	public ArrayList<Point2D> backtrackPath(Point2D newNode) 
	{
		ArrayList<Point2D> path = new ArrayList<>();
		Point2D current = goalNode;

		while (current != null) {
			path.add(current);
			current = findParent(current);
		}

		Collections.reverse(path);

		return path;
	}

	public void createPanel() {
		frame = new JFrame("Assignment 1");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setSize(frameX, frameY);
		// frame.setVisible(true);

		this.setBackground(Color.white);
		// this.setLayout(new BorderLayout());
		this.setLayout(null);
		this.setVisible(true);
		frame.getContentPane().add(this);

		frame.setVisible(true);
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

	public Point2D findParent(Point2D node) {
		for (Point2D n : nodes) {
			if (n.getX() == node.getX() && n.getY() == node.getY()) {
				return n;
			}
		}

		return null;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.red);
		g.fillOval((int) goalNode.getX(), (int) goalNode.getY(), 30, 30);
		g.setColor(Color.black);

		for (int i = 0; i < nodes.size(); i++) {
			g.fillOval((int) nodes.get(i).getX(), (int) nodes.get(i).getY(), 30, 30);
		}

		if (path.size() != 0) {
			System.out.println("Entered");
			for (int i = 0; i < path.size(); i++) 
			{
				System.out.println(i);
				g.setColor(Color.red);
				g.fillOval((int) path.get(i).getX(), (int) path.get(i).getY(), 30, 30);
			}
		}
	}

	public void run(int maxIterations) {
		createPanel();

		for (int i = 0; i < maxIterations; i++) {
			double randX = Math.random() * frameX;
			double randY = Math.random() * frameY;
			Point2D randomNode = new Point2D.Double(randX, randY);

			Point2D nearestNode = findNearestNode(randomNode);

			double angle = Math.atan2(randomNode.getY() - nearestNode.getY(), randomNode.getX() - nearestNode.getX());
			double newX = nearestNode.getX() + STEP_SIZE * Math.cos(angle);
			double newY = nearestNode.getY() + STEP_SIZE * Math.sin(angle);

			Point2D newNode = new Point2D.Double(newX, newY);
			nodes.add(newNode);

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

		if (goalFound) {
			path = backtrackPath(nodes.get(nodes.size() - 1));
		}
	}
}
