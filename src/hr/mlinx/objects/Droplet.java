package hr.mlinx.objects;

import java.awt.Color;

import hr.mlinx.util.Util;

public class Droplet {
	
	private static final double MIN_WIDTH = 1.5 * Util.SCALE;
	private static final double MAX_WIDTH = 6.0 * Util.SCALE;
	private static final double MIN_HEIGHT = 15.0 * Util.SCALE;
	private static final double MAX_HEIGHT = 60.0 * Util.SCALE;
	private static final double MIN_VELOCITY = 3.0 * Util.SCALE;
	private static final double MAX_VELOCITY = 12.0 * Util.SCALE;
	private static final double MIN_GRAVITY = 0.2 * Util.SCALE;
	private static final double MAX_GRAVITY = 0.8 * Util.SCALE;
	
//	private static final double MIN_WIND = 0.05 * Util.SCALE;
//	private static final double MAX_WIND = 0.2 * Util.SCALE;
	
	private static final double RAIN_START = MAX_HEIGHT * 2;
	
	private Color color;
	
	private double x;
	private double y;
	private double width;
	private double height;
	private double velocity;
	private double gravity;
	
//	private double wind;
//	private int windDir;
	
	public Droplet(Color color) {
		super();
		this.color = color;
		
		width = Util.R.nextDouble(MIN_WIDTH, MAX_WIDTH);
		height = map(width, MIN_WIDTH, MAX_WIDTH, MIN_HEIGHT, MAX_HEIGHT);
		x = Util.R.nextDouble(0.0, Util.RES.getWidth() - width);
		y = -RAIN_START - height;
		velocity = map(width, MIN_WIDTH, MAX_WIDTH, MIN_VELOCITY, MAX_VELOCITY);
		gravity = map(width, MIN_WIDTH, MAX_WIDTH, MIN_GRAVITY, MAX_GRAVITY);
		
//		wind = map(width, MIN_WIDTH, MAX_WIDTH, MIN_WIND, MAX_WIND);
//		if (r.nextInt(2) == 0) {
//			windDir = 1;
//		} else {
//			windDir = -1;
//		}
	}
	
	public void fall() {
//		x += wind * windDir;
		
		y += velocity;
		velocity += gravity;
	}
	
	public boolean hasFallen() {
		return y > Util.RES.getHeight();
	}
	
	private double map(double val, double valLow, double valHigh, double returnValLow, double returnValHigh) {
		double ratio = (val - valLow) / (valHigh - valLow);
		
		return ratio * (returnValHigh - returnValLow) + returnValLow;
	}
	
	public Color getColor() {
		return color;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}

	public double getWidth() {
		return width;
	}

	public double getHeight() {
		return height;
	}
	
}
