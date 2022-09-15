package hr.mlinx.components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.util.LinkedList;
import java.util.List;

import javax.swing.Timer;

import hr.mlinx.objects.Droplet;
import hr.mlinx.util.Util;

public class RainCanvas extends Canvas {
	private static final long serialVersionUID = -2767749303429087516L;
	
	private static final int FPS = 60;
	private static final int TIMER_DELAY = (int) ((1.0 / FPS) * 1000);
	private static final int COLOR_CHANGE = (int) (5 * Util.SCALE);
	
	private static final int RAIN_DENSITY = 25;
	
	private List<Droplet> rain;
	private int colorIdx;
	
	public RainCanvas() {
		super();
		
		timer = new Timer(TIMER_DELAY, this);
		timer.setInitialDelay(0);
		
		rain = new LinkedList<>();
		colorIdx = 0;
		
		timer.start();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		addToRain();
		colorIdx += COLOR_CHANGE;
		
		draw((Graphics2D) g);
	}
	
	private void addToRain() {
		int d = Util.R.nextInt(RAIN_DENSITY + 1);
		for (int i = 0; i < d; ++i ) {
			rain.add(new Droplet(Color.getHSBColor((float) colorIdx / (255 * 6), 1.0f, 1.0f)));
		}
	}
	
	private void draw(Graphics2D g2) {
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		
		Droplet droplet;
		for (int i = 0; i < rain.size(); ++i) {
			droplet = rain.get(i);
			if (droplet.hasFallen()) {
				rain.remove(droplet);
				--i;
				continue;
			}
			g2.setColor(droplet.getColor());
			g2.fill(new Ellipse2D.Double(droplet.getX(), droplet.getY(),
										   droplet.getWidth(), droplet.getHeight()));
			droplet.fall();
		}
	}

}
