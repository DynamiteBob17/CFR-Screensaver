package hr.mlinx.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

public class Loader {
	
	private static double[] RECT_SIZES = new double[] {
			10 * Util.SCALE,
			8 * Util.SCALE,
			6 * Util.SCALE,
			5 * Util.SCALE,
			4 * Util.SCALE,
			3 * Util.SCALE,
			2 * Util.SCALE,
			1 * Util.SCALE,
	};
	
	private List<String> presets;
	
	private double rectSize;
	private int horizLen;
	private int vertLen;
	
	public Loader() {
		super();
		
		fillPresets();
		
		rectSize = 0;
		horizLen = 0;
		vertLen = 0;
	}
	
//	private static final List<String> TO_DELETE = new LinkedList<>();
	
	/*
	 * the method below doesn't work when running the program as a .jar executable;
	 * that's why the presets.txt file was created 
	 */
	
//	private void fillPresets2() {
//		presets = new LinkedList<>();
//		
//		try (BufferedReader br = new BufferedReader(
//								 new InputStreamReader(
//								 Loader.class.getResourceAsStream("/presets")))){
//			String resource;
//			
//			while ((resource = br.readLine()) != null) {
//				try (BufferedReader br2 = new BufferedReader(
//										  new InputStreamReader(
//										  Loader.class.getResourceAsStream("/presets/" + resource)))) {
//					StringBuilder sb = new StringBuilder();
//					String line;
//					boolean invalid = false;
//					
//					while ((line = br2.readLine()) != null && resource.endsWith(".rle")) {
//						line = line.trim();
//						if (line.startsWith("x =")) {
//							if (invalidSize(line)) {
//								invalid = true;
//								break;
//							}
//						}
//						if (!line.startsWith("#") && !line.isEmpty()) {
//							sb.append(line).append("\n");
//						}
//					}
//					if (!resource.endsWith(".rle") || invalid) {
//						System.out.println(resource + " will be deleted...");
//						TO_DELETE.add(resource);
//					} else {
//						presets.add(sb.toString());
//					}
//				}
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		
//		try (PrintWriter writer = new PrintWriter("resources/presets.txt", "UTF-8")) {
//			for (int i = 0; i < presets.size(); ++i) {
//				if (i != 0) {
//					writer.println("NEXT");
//				}
//				writer.print(presets.get(i));
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		
//		for (String resource : TO_DELETE) {
//			try {
//				System.out.println("Deleting " + resource + "...");
//				String path = Loader.class.getResource("/presets/" + resource).toExternalForm();
//				path = path.replaceFirst("file:/", "");
//				path = path.replaceAll("%20", " ");
//				path = path.replaceAll("%5c", "");
//				Files.delete(Path.of(path));
//				path = path.replaceFirst("bin", "resources");
//				Files.delete(Path.of(path));
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//	}
//	
//	private boolean invalidSize(String line) {
//		String[] commaSplit = line.split(",");
//		int x = Integer.parseInt(commaSplit[0].split("=")[1].trim());
//		int y = Integer.parseInt(commaSplit[1].split("=")[1].trim());
//		
//		return x > 1920 || y > 1080 || x <= 0 || y <= 0;
//	}
	
	/*
	 * size of presets shouldn't be bigger than 1920x1080
	 * --------------------------------------------------------------------
	 * .rle files collection downloaded from https://conwaylife.com/patterns/all.zip
	 */
	
	private void fillPresets() {
		presets = new LinkedList<>();
		
		try (BufferedReader br = new BufferedReader(
								 new InputStreamReader(
							     Loader.class.getResourceAsStream("/presets.txt")))) {
			StringBuilder sb = new StringBuilder();
			String line = "";
			
			while ((line = br.readLine()) != null) {
				if (line.equals("NEXT")) {
					presets.add(sb.toString());
					sb = new StringBuilder();
				} else {
					sb.append(line).append("\n");
				}
			}
			presets.add(sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean[][] randomArray() {
		rectSize = 4 * Util.SCALE;
		horizLen = (int) (Util.RES.getWidth() / rectSize);
		vertLen = (int) (Util.RES.getHeight() / rectSize);
		
		boolean[][] array = new boolean[horizLen][vertLen];
		
		for (int i = 0; i < horizLen; ++i) {
			for (int j = 0; j < vertLen; ++j) {
				array[i][j] = Util.R.nextBoolean();
			}
		}
		
		return array;
	}
	
	public boolean[][] randomPreset() {
		return createPresetArray(presets.get(Util.R.nextInt(presets.size())));
	}
	
	private boolean[][] createPresetArray(String rle) {
		String[] newlineSplit = rle.split("\n");
		int len = newlineSplit.length;
		
		if (newlineSplit[len - 1].endsWith("!"))
			newlineSplit[len - 1] = newlineSplit[len - 1].substring(0, newlineSplit[len - 1].length() - 1);
		
		String rleCoords = "";;
		for (int i = 1; i < newlineSplit.length; ++i)
			rleCoords += newlineSplit[i];
		
		String[] commaSplit = newlineSplit[0].split(",");
		int x = Integer.parseInt(commaSplit[0].split("=")[1].trim());
		int y = Integer.parseInt(commaSplit[1].split("=")[1].trim());
		
		boolean[][] array = new boolean[x][y];
		
		char currChar;
		String number = "";
		int xTracker = 0, yTracker = 0;
		for (int i = 0; i < rleCoords.length(); ++i) {
			currChar = rleCoords.charAt(i);
			
			if (Character.isDigit(currChar)) {
				number += currChar;
			} else {
				if (currChar == 'b') {
					if (number.isEmpty()) {
						array[xTracker][yTracker] = false;
						++xTracker;
					} else {
						int count = Integer.parseInt(number);
						for (int j = 0; j < count; ++j) {
							array[xTracker][yTracker] = false;
							++xTracker;
						}
					}
				} else if (currChar == 'o') {
					if (number.isEmpty()) {
						array[xTracker][yTracker] = true;
						++xTracker;
					} else {
						int count = Integer.parseInt(number);
						for (int j = 0; j < count; ++j) {
							array[xTracker][yTracker] = true;
							++xTracker;
						}
					}
				} else if (currChar == '$') {
					if (number.isEmpty()) {
						++yTracker;
						xTracker = 0;
					} else {
						int count = Integer.parseInt(number);
						for (int j = 0; j < count; ++j) {
							++yTracker;
						}
						xTracker = 0;
					}
				}
				
				number = "";
			}
		}
		
		return insertSmallerArrayIntoLarger(array, x, y);
	}
	
	private boolean[][] insertSmallerArrayIntoLarger(boolean[][] smaller, int smallX, int smallY) {
		setRectSize(smallX, smallY);
		
		boolean[][] larger = new boolean[horizLen][vertLen];
		
		int startX = (horizLen - smallX) / 2;
		int startY = (vertLen - smallY) / 2;
		
		int is = 0;
		int js = 0;
		for (int i = startX; i < (startX + smallX); ++i) {
			for (int j = startY; j < (startY + smallY); ++j) {
				larger[i][j] = smaller[is][js];
				++js;
			}
			js = 0;
			++is;
		}
		
		return larger;
	}
	
	private void setRectSize(int sx, int sy) {
		int tempHorizLen, tempVertLen;
		for (int i = 0; i < RECT_SIZES.length; ++i) {
			tempHorizLen = (int) (Util.RES.getWidth() / RECT_SIZES[i]);
			tempVertLen = (int) (Util.RES.getHeight() / RECT_SIZES[i]);
			if (tempHorizLen >= sx && tempVertLen >= sy) {
				rectSize = RECT_SIZES[i];
				horizLen = tempHorizLen;
				vertLen = tempVertLen;
				return;
			}
		}
	}
	
	public double getRectSize() {
		return rectSize;
	}

	public int getHorizLen() {
		return horizLen;
	}

	public int getVertLen() {
		return vertLen;
	}
	
}
