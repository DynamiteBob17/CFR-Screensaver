package hr.mlinx.components;

import java.awt.Cursor;
import java.awt.GraphicsDevice;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import hr.mlinx.util.Util;

public class Main extends JFrame {
	
private static final long serialVersionUID = 4494172472747131232L;
	
	public Main() {
		super();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		addListeners();
		
		Canvas canvas;
		switch (Util.R.nextInt(3)) {
		case 0:
			canvas = new ConwayCanvas();
			break;
		case 1:
			canvas = new FractalCanvas();
			break;
		case 2:
			canvas = new RainCanvas();
			break;
		default:
			canvas = new ConwayCanvas();
			break;
		}
		add(canvas);
		
		setFullScreen();
		
		BufferedImage cursorImg = new BufferedImage(16, 16, 
									  BufferedImage.TYPE_INT_ARGB);
	    Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
	    					         cursorImg, new Point(0, 0), "blank cursor");
	    getContentPane().setCursor(blankCursor);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() ->{
			new Main();
		});
	}
	
	private void setFullScreen() {
		GraphicsDevice device = getGraphicsConfiguration().getDevice();
		boolean isSupported = device.isFullScreenSupported();
		
		if (isSupported) {
			setUndecorated(true);
			setResizable(true);
			
			addFocusListener(new FocusListener() {
				@Override
				public void focusGained(FocusEvent e) {
					setAlwaysOnTop(true);
				}

				@Override
				public void focusLost(FocusEvent e) {
					setAlwaysOnTop(false);
				}
			});
			
			device.setFullScreenWindow(this);
		} else {
			setUndecorated(true);
			setResizable(false);
			
			setExtendedState(JFrame.MAXIMIZED_BOTH);
			setVisible(true);
		}
	}
	
	private void addListeners() {
		addMouseListener(new MouseAdapter() {
			@Override
        	public void mousePressed(MouseEvent e) {
        		exit();
        	}
		});
		addMouseWheelListener(new MouseAdapter() {
        	@Override
        	public void mouseWheelMoved(MouseWheelEvent e) {
        		exit();
        	}
        });
		addMouseMotionListener(new MouseAdapter() {
			private int mouseCounter = 0;
        	@Override
        	public void mouseMoved(MouseEvent e) {
        		++mouseCounter;
        		if (mouseCounter > 1)
        			exit();
        	}
        });
		addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				exit();
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}
        });
	}
	
	private void exit() {
		dispose();
		System.exit(0);
	}
	
}
