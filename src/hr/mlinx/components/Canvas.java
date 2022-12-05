package hr.mlinx.components;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

import hr.mlinx.util.Util;

public abstract class Canvas extends JPanel implements ActionListener {
	private static final long serialVersionUID = 185506439471079314L;
	
	protected Timer timer;
	protected boolean isUnix;
	protected Toolkit tk;
	
	public Canvas() {
		super();
		isUnix = Util.isUnix();
		if (isUnix) tk = Toolkit.getDefaultToolkit();
		setBackground(Color.BLACK);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		repaint();
	}

}
