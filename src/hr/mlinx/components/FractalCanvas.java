package hr.mlinx.components;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.Timer;

import hr.mlinx.fractals.FractalTree;
import hr.mlinx.fractals.FractalTree2;
import hr.mlinx.fractals.FractalTree3;
import hr.mlinx.fractals.FractalTree4;
import hr.mlinx.util.Util;

public class FractalCanvas extends Canvas {
	private static final long serialVersionUID = 3656970158683044949L;
	
	private static final int PRESET_TIME = 60;
	private static final int FPS = 60;
	private static final int TIMER_DELAY = (int) ((1.0 / FPS) * 1000);
	
	private FractalTree fractal;
	private int badStopwatch;
	
	public FractalCanvas() {
		super();
		
		timer = new Timer(TIMER_DELAY, this);
		timer.setInitialDelay(0);
		
		randomFractal();
		badStopwatch = 0;
		
		timer.start();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		++badStopwatch;
		if (badStopwatch >= PRESET_TIME * FPS) {
			randomFractal();
			badStopwatch = 0;
		}
		
		fractal.draw((Graphics2D) g);
		fractal.move();
	}
	
	private void randomFractal() {
		switch (Util.R.nextInt(3)) {
		case 0:
			fractal = new FractalTree2();
			break;
		case 1:
			fractal = new FractalTree3();
			break;
		case 2:
			fractal = new FractalTree4();
			break;
		default:
			fractal = new FractalTree2();
			break;
		}
	}

}
