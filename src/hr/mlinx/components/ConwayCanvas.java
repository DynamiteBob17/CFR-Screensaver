package hr.mlinx.components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import javax.swing.Timer;

import hr.mlinx.util.Loader;
import hr.mlinx.util.Util;

public class ConwayCanvas extends Canvas {
	private static final long serialVersionUID = -145219924509325330L;
	
	private static final int RANDOM_PRESET_LIFE_TIME = 90;
	private static final int RANDOM_ARRAY_LIFE_TIME = 180;
	private static final int RANDOM_PRESET_FPS = 30;
	private static final int RANDOM_ARRAY_FPS = 30;
	private static final int COLOR_CHANGE = 1;
	
	private Loader loader;
	
	private boolean[][] activeArray;
	private int fps;
	private int lifeTime;
	private int badStopwatch;
	
	private int colorIdx;
	
	private int noChanges;
	
	public ConwayCanvas() {
		super();
		
		loader = new Loader();
		
		timer = new Timer(-1, this);
		timer.setInitialDelay(0);
		setArray();
		
		colorIdx = 0;
		
		timer.start();
	}
	
	private void setArray() {
		if (Util.R.nextInt(10) == 0) {
			activeArray = loader.randomArray();
			fps = RANDOM_ARRAY_FPS;
			timer.setDelay(getDelay());
			lifeTime = RANDOM_ARRAY_LIFE_TIME;
		} else {
			activeArray = loader.randomPreset();
			fps = RANDOM_PRESET_FPS;
			timer.setDelay(getDelay());
			lifeTime = RANDOM_PRESET_LIFE_TIME;
		}
		badStopwatch = 0;
	}
	
	private int getDelay() {
		return (int) ((1.0 / fps) * 1000);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		colorIdx += COLOR_CHANGE;
		
		++badStopwatch;
		if (badStopwatch >= lifeTime * fps) {
			setArray();
		}
		
		draw((Graphics2D) g);
	}
	
	private void draw(Graphics2D g2) {
		int w = loader.getHorizLen(), h = loader.getVertLen();
		double rs = loader.getRectSize();
		
		boolean changed = false;
		
		for (int i = 0; i < w; ++i) {
			for (int j = 0; j < h; ++j) {
				if (activeArray[i][j]) {
					g2.setColor(Color.getHSBColor((float) colorIdx / (255 * 6), 1.0f, 1.0f));
					g2.fill(new Rectangle2D.Double(i * rs,
												   j * rs,
												   rs,
												   rs));
				}
			}
		}
		
		boolean[][] nextArray = new boolean[w][h];
		for (int i = 0; i < w; ++i) {
			for (int j = 0; j < h; ++j) {
				int neighbors = countNeighbors(i, j);
				if (activeArray[i][j]) {
					if (neighbors == 2 || neighbors == 3) {
						nextArray[i][j] = true;
					}
				} else {
					if (neighbors == 3) {
						nextArray[i][j] = true;
					}
				}
				
				if (!changed && nextArray[i][j] != activeArray[i][j]) {
					changed = true;
				}
			}
		}

		activeArray = nextArray;
		
		if (!changed) {
			++noChanges;
			if (noChanges >= fps * 3) { // new preset if unchanged for FPS * 3 frames
				badStopwatch = lifeTime * fps;
				noChanges = 0;
			}
		}
	}
	
	private int countNeighbors(int x, int y) {
		int count = 0;
		int w = loader.getHorizLen(), h = loader.getVertLen();

		for (int i = -1; i <= 1; ++i) {
			for (int j = -1; j <= 1; ++j) {
				if (!(i == 0 && j == 0)) {
					int i2 = (x + i + w) % w;
					int j2 = (y + j + h) % h;
					if (activeArray[i2][j2]) {
						++count;
					}
				}
			}
		}

		return count;
	}

}
