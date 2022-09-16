package hr.mlinx.fractals;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;

import hr.mlinx.objects.Point;
import hr.mlinx.util.Util;

public class FractalTree3 extends FractalTree {
	
	private static final double BASE_LENGTH = 330 * Util.SCALE;
	private static final double LENGTH_RATIO = 1.61803398875;
	private static final float BASE_STROKE = (float) (10.0 * Util.SCALE);
	private static final float STROKE_RATIO = 1.4f;
	private static final double MAX_ANGLE = 144;
	private static final double MIN_SPEED = 0.1;
	private static final double MAX_SPEED = 0.35;
	
	private double leftPhi;
	private double rightPhi;
	private int leftDir;
	private int rightDir;
	private double leftSpeed;
	private double rightSpeed;
	private int idx;
	
	Point trunkStart;
	Point trunkEnd;
	
	public FractalTree3() {
		super();
		
		if (Util.R.nextInt(2) == 0) {
			leftPhi = 0;
		    rightPhi = 0;
		    leftDir = 1;
		    rightDir = 1;
		    leftSpeed = 0.225;
		    rightSpeed = 0.225;
		}
		else {
			leftPhi = Util.R.nextDouble(MAX_ANGLE);
		    rightPhi = Util.R.nextDouble(MAX_ANGLE);
		    leftDir = Util.R.nextInt(2) == 0 ? -1 : 1;
			rightDir = Util.R.nextInt(2) == 0 ? -1 : 1;
			leftSpeed = Util.R.nextDouble(MIN_SPEED, MAX_SPEED);
			rightSpeed = Util.R.nextDouble(MIN_SPEED, MAX_SPEED);
		}
		
		numOfBranches = getTotalNumberOfBranches(3);
		
		trunkStart = new Point(Util.RES.getWidth() / 2,
							   Util.RES.getHeight() - 100 * Util.SCALE);
		trunkEnd = new Point(Util.RES.getWidth() / 2, 
							 Util.RES.getHeight() - 210 * Util.SCALE - BASE_LENGTH);
	}

	@Override
	public void draw(Graphics2D g2) {
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
							RenderingHints.VALUE_ANTIALIAS_ON);
		
		idx = 0;
		
		tree3(trunkStart, trunkEnd, BASE_LENGTH, 0, BASE_STROKE, 1, g2);
	}
	
	private void tree3(Point start, Point end, double length, double angle, float stroke, int depth, Graphics2D g2) {
		g2.setStroke(new BasicStroke(stroke));
		g2.setColor(Color.getHSBColor((float) idx++ / numOfBranches, 1.0f, 1.0f));
		g2.draw(new Line2D.Double(start, end));
		
		if (depth < this.depth) {
			length /= LENGTH_RATIO;
			stroke /= STROKE_RATIO;
			double leftAngle = angle - leftPhi;
			double middleAngle = angle;
			double rightAngle = angle + rightPhi;
			
			Point leftEnd = rotate(end, length, leftAngle);
			Point middleEnd = rotate(end, length, middleAngle);
			Point rightEnd = rotate(end, length, rightAngle);
			
			tree3(end, leftEnd, length, leftAngle, stroke, depth + 1, g2);
			tree3(end, middleEnd, length, middleAngle, stroke, depth + 1, g2);
			tree3(end, rightEnd, length, rightAngle, stroke, depth + 1, g2);
		}
	}

	@Override
	public void move() {
		leftPhi += leftSpeed * leftDir;
		rightPhi += rightSpeed * rightDir;
		
		if (Math.abs(leftPhi) > MAX_ANGLE) {
			leftDir *= -1;
			leftPhi += leftSpeed * leftDir;
			leftSpeed = Util.R.nextDouble(MIN_SPEED, MAX_SPEED);
		}
		if (Math.abs(rightPhi) > MAX_ANGLE) {
			rightDir *= -1;
			rightPhi += rightSpeed * rightDir;
			rightSpeed = Util.R.nextDouble(MIN_SPEED, MAX_SPEED);
		}
	}

	@Override
	public void setDepth() {
		this.depth = 10;
	}
	
}
