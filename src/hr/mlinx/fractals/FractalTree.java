package hr.mlinx.fractals;

import java.awt.Graphics2D;

import hr.mlinx.objects.Point;

public abstract class FractalTree {
	
	protected int depth;
	protected int numOfBranches;
	
	public FractalTree() {
		super();
		
		setDepth();
	}
	
	protected Point rotate(Point pivot, double dist, double angle) {
		angle = Math.toRadians(angle);
		
		return new Point(pivot.getX() + dist * Math.cos(angle - (Math.PI / 2.0)),
						 pivot.getY() + dist * Math.sin(angle - (Math.PI / 2.0)));
	}
	
	protected int getNumberOfBranches(int branches) {
		int sum = 0;
		
		for (int i = 0; i < this.depth; ++i) {
			sum += Math.pow(branches, i);
		}	
		
		return sum;
	}
	
    public abstract void draw(Graphics2D g2);
	
	public abstract void move();
	
	protected abstract void setDepth();
	
}
