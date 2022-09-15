package hr.mlinx.fractals;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Path2D;

import hr.mlinx.objects.Point;
import hr.mlinx.util.Util;

public class FractalTree2 extends FractalTree {
	
	private static final double BASE_LENGTH = 365 * Util.SCALE;
	private static final double LENGTH_RATIO = 1.61803398875;
	private static final float BASE_STROKE = (float) (30 * Util.SCALE);
	private static final float STROKE_RATIO = 1.3f;
	private static final double MIN_ANGLE = 0;
	private static final double MAX_ANGLE = 90;
	private static final double MIN_SPEED = 0.1;
	private static final double MAX_SPEED = 0.35;
	private static final double CURVE_ANGLE = 30;
	private static final int GLOW_ALPHA = 128;
	
	private double leftPhi;
	private double rightPhi;
	private int leftDir;
	private int rightDir;
	private double leftSpeed;
	private double rightSpeed;
	private int colorChanges;
	private Color branchColor;
	private Color leafColor;
	
	private int max;
	private int lvl;
	
	Point trunkStart;
	Point trunkEnd;	
	
	public FractalTree2() {
		super();
		
		leftPhi = Util.R.nextDouble(MIN_ANGLE, MAX_ANGLE);
		rightPhi = Util.R.nextDouble(MIN_ANGLE, MAX_ANGLE);
		leftDir = Util.R.nextInt(2) == 0 ? -1 : 1;
		rightDir = Util.R.nextInt(2) == 0 ? -1 : 1;
		leftSpeed = Util.R.nextDouble(MIN_SPEED, MAX_SPEED);
		rightSpeed = Util.R.nextDouble(MIN_SPEED, MAX_SPEED);
		colorChanges = 1;
		branchColor = randomColor();
		leafColor = randomColor();
		
		max = 1;
		lvl = 0;
		numOfBranches = getNumberOfBranches(2);
		
		trunkStart = new Point(Util.RES.getWidth() / 2,
							   Util.RES.getHeight() - 100 * Util.SCALE);
		trunkEnd = new Point(Util.RES.getWidth() / 2, 
							 Util.RES.getHeight() - 100 * Util.SCALE - BASE_LENGTH);
	}

	@Override
	public void draw(Graphics2D g2) {
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
							RenderingHints.VALUE_ANTIALIAS_ON);
		
		if (max <= numOfBranches)
			++max;
		lvl = 0;
		
		tree2(trunkStart, trunkEnd, BASE_LENGTH, 0, BASE_STROKE, 1, g2);
	}
	
	private void tree2(Point start, Point end, double length, double angle, float stroke, int depth, Graphics2D g2) {
		if (lvl >= max)
			return;
		else
			++lvl;
		
		Path2D curve = new Path2D.Double();
		curve.moveTo(start.getX(), start.getY());
		double bezierLength;
		Point bezier1, bezier2;
		
		if (depth == this.depth) { // draw leaves
			double len = length + 17 * Util.SCALE;
			int div = 3;
			double dAngle = 90;
			bezierLength = Math.sqrt(Math.pow(len / div, 2) 
 	 				 				 + Math.pow(len / div, 2));
			end = rotate(end, len, angle);
			bezier1 = rotate(start, bezierLength, angle + dAngle);
			bezier2 = rotate(end, bezierLength, angle + 180 - dAngle);
			curve.curveTo(bezier1.getX(), bezier1.getY(),
						  bezier2.getX(), bezier2.getY(), 
						  end.getX(), end.getY());
			curve.closePath();
			g2.setStroke(new BasicStroke(stroke));
			g2.setColor(new Color (Color.DARK_GRAY.getRed(),
								   Color.DARK_GRAY.getGreen(),
								   Color.DARK_GRAY.getBlue(), 
								   GLOW_ALPHA));
			g2.draw(curve);
			g2.setColor(leafColor);
			g2.fill(curve);
		} else { // draw branches
			bezierLength = Math.sqrt(Math.pow(length / 3, 2) 
				  	 				 + Math.pow(length / 3, 2));
			bezier1 = rotate(start, bezierLength, angle + CURVE_ANGLE);
			bezier2 = rotate(end, bezierLength, angle + 180 + CURVE_ANGLE);
			curve.curveTo(bezier1.getX(), bezier1.getY(),
					 	  bezier2.getX(), bezier2.getY(), 
					 	  end.getX(), end.getY());
			g2.setColor(new Color (leafColor.getRed(),
								   leafColor.getGreen(),
								   leafColor.getBlue(), 
								   GLOW_ALPHA));
			g2.setStroke(new BasicStroke(stroke * 1.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER));
			g2.draw(curve);
			
			g2.setColor(branchColor);
			g2.setStroke(new BasicStroke(stroke, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER));
			g2.draw(curve);
		}
		
		if (depth < this.depth) {
			length /= LENGTH_RATIO;
			stroke /= STROKE_RATIO;
			double leftAngle = angle - leftPhi;
			double rightAngle = angle + rightPhi;
			
			Point leftEnd = rotate(end, length, leftAngle);
			Point rightEnd = rotate(end, length, rightAngle);
			
			tree2(end, leftEnd, length, leftAngle, stroke, depth + 1, g2);
			tree2(end, rightEnd, length, rightAngle, stroke, depth + 1, g2);
		}
	}
	
	private Color randomColor() {
		return new Color(Util.R.nextInt(256),
						 Util.R.nextInt(256),
						 Util.R.nextInt(256));
	}
	
	@Override
	public void move() {
		leftPhi += leftSpeed * leftDir;
		rightPhi += rightSpeed * rightDir;
		
		if (leftPhi >= MAX_ANGLE || leftPhi <= MIN_ANGLE) {
			leftDir *= -1;
			leftPhi += leftSpeed * leftDir;
			++colorChanges;
		}
		if (rightPhi >= MAX_ANGLE || rightPhi <= MIN_ANGLE) {
			rightDir *= -1;
			rightPhi += rightSpeed * rightDir;
			++colorChanges;
		}
		
		if (colorChanges % 5 == 0) {
			branchColor = randomColor();
			leafColor = randomColor();
			leftSpeed = Util.R.nextDouble(MIN_SPEED, MAX_SPEED);
			rightSpeed = Util.R.nextDouble(MIN_SPEED, MAX_SPEED);
			++colorChanges;
		}
	}

	@Override
	public void setDepth() {
		 this.depth = 8;
	}
	
}
