package hr.mlinx.fractals;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;

import hr.mlinx.objects.Point;
import hr.mlinx.util.Util;

public class FractalTree4 extends FractalTree {
	
	private static final double BASE_LENGTH = 315 * Util.SCALE;
	private static final double LENGTH_RATIO = 1.61803398875;
	private static final float BASE_STROKE = (float) (6 * Util.SCALE);
	private static final float STROKE_RATIO = 1.3f;
	
	private double outerLeftPhi;
	private double leftPhi;
	private double rightPhi;
	private double outerRightPhi;
	
	private int cycleStart;
	private int cycle;
	
	Point trunkStart;
	Point trunkEnd;
	
	public FractalTree4() {
		super();
		
		outerLeftPhi = Util.R.nextDouble(360);
		leftPhi = Util.R.nextDouble(360);
		rightPhi = Util.R.nextDouble(360);
		outerRightPhi = Util.R.nextDouble(360);
		
		cycleStart = 0;
		numOfBranches = getTotalNumberOfBranches(4);
		
		trunkStart = new Point(Util.RES.getWidth() / 2,
				   Util.RES.getHeight() - 100 * Util.SCALE - 165 * Util.SCALE);
		trunkEnd = new Point(Util.RES.getWidth() / 2, 
				 Util.RES.getHeight() - 100 * Util.SCALE - BASE_LENGTH - 165 * Util.SCALE);
	}

	@Override
	public void draw(Graphics2D g2) {
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
							RenderingHints.VALUE_ANTIALIAS_ON);
		
		cycle = cycleStart += 120;
		
		tree4(trunkStart, trunkEnd, BASE_LENGTH, 0, BASE_STROKE, 1, g2);
	}
	
	private void tree4(Point start, Point end, double length, double angle, float stroke, double depth, Graphics2D g2) {
		g2.setStroke(new BasicStroke(stroke));
		g2.setColor(Color.getHSBColor((float) cycle++ / numOfBranches, 1.0f, 1.0f));
		g2.draw(new Line2D.Double(start, end));
		
		if (depth < this.depth) {
			stroke /= STROKE_RATIO;
			double outerLeftAngle = angle - outerLeftPhi;
			double leftAngle = angle - leftPhi;
			double rightAngle = angle + rightPhi;
			double outerRightAngle = angle + outerRightPhi;
			
			Point outerLeftStart = rotate(start, length / 1.85, angle);
			Point leftStart = rotate(start, length / 1.25, angle);
			Point rightStart = end;
			Point outerRightStart = rotate(start, length / 1.4, angle);
			
			length /= LENGTH_RATIO;
			
			Point outerLeftEnd = rotate(outerLeftStart, length, outerLeftAngle);
			Point leftEnd = rotate(leftStart, length, leftAngle);
			Point rightEnd = rotate(rightStart, length, rightAngle);
			Point outerRightEnd = rotate(outerRightStart, length, outerRightAngle);
			
			tree4(outerLeftStart, outerLeftEnd, length, outerLeftAngle, stroke, depth + 1, g2);
			tree4(leftStart, leftEnd, length, leftAngle, stroke, depth + 1, g2);
			tree4(rightStart, rightEnd, length, rightAngle, stroke, depth + 1, g2);
			tree4(outerRightStart, outerRightEnd, length, outerRightAngle, stroke, depth + 1, g2);
		}
	}

	@Override
	public void move() {
		outerLeftPhi += 0.5;
		++leftPhi;
		++rightPhi;
		outerRightPhi += 0.5;
	}

	@Override
	public void setDepth() {
		this.depth = 8;
	}

}
