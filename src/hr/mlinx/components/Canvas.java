package hr.mlinx.components;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

public abstract class Canvas extends JPanel implements ActionListener {
	private static final long serialVersionUID = 185506439471079314L;
	
	protected Timer timer;
	
	public Canvas() {
		super();
		setBackground(Color.BLACK);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		repaint();
	}

}
