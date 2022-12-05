package hr.mlinx.util;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Random;

public class Util {
	
	public static final Dimension RES = Toolkit.getDefaultToolkit().getScreenSize();
	public static final double SCALE = RES.getWidth() / 1920.0;
	public static final Random R = new Random();
	
	public static boolean isUnix() {
		return System.getProperty("os.name").startsWith("Linux");
	}
	
}
