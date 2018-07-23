package embgine.core;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Map {
	
	//a map is the world part of the level, without any of the entity or other data
	//it can be used by gameobjects directly and through the maprenderer
	
	private Script scInstance;
	private Class<? extends Script> script;
	private Class<? extends Block>[][] tiles;
	private Block[][] workingCopy;
	private Class<? extends Block> edgeTile;
	
	private int width;
	private int height;
	
	public static final boolean   EDGE_FILL = false;
	public static final boolean EDGE_REPEAT =  true;
	
	private boolean edgeMode;
	
	public Map(String mapPath, MapReference mr, boolean em, Class<? extends Block> et, Class<? extends Script> sc) {
		
		script = sc;
				
		edgeMode = em;
		edgeTile = et;
		
		BufferedImage bi = null;
		try {
			bi = ImageIO.read(new File(mapPath));
		} catch(Exception ex) {}
		
		width = bi.getWidth();
		height = bi.getHeight();
		
		tiles = new Class[width][height];
		
		for(int i = 0; i < width; ++i) {
			for(int j = 0; j < height; ++j) {
				tiles[i][j] = mr.getBlock(bi.getRGB(i, j));
			}
		}
	}
	
	public void refreshWorkingCopy(Scene sc) {
		
		workingCopy = new Block[width][height];
		for(int i = 0; i < width; ++i) {
			for(int j = 0; j < height; ++j) {
				try {
					workingCopy[i][j] = (Block)tiles[i][j].getConstructors()[0].newInstance();
				} catch (Exception ex) {
					workingCopy[i][j] = null;
				}
			}
		}
		
		try {
			scInstance = (Script)script.getClass().getConstructors()[0].newInstance(null, sc);
		}catch(Exception ex) {}
		
		scInstance.start(new float[] {});
	}
	
	public Block access(int x, int y) {
		if(edgeMode) {
			return edgeAccess(x, y);
		}else {
			return repeatAccess(x, y);
		}
	}
	
	private Block edgeAccess(int x, int y) {
		try {
			return workingCopy[x][y];
		}catch(Exception ex) {
			try {
				return (Block)edgeTile.getConstructors()[0].newInstance();
			} catch(Exception ex2) {
				return null;
			}
		}
	}
	
	private Block repeatAccess(int x, int y) {
		if(x < 0) {
			x = 0;
		}else if(x >= width){
			x = width-1;
		}
		if(y < 0) {
			y = 0;
		}else if(y >= height){
			y = height-1;
		}
		return workingCopy[x][y];
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void setBlock(Block b, int x, int y) {
		workingCopy[x][y] = b;
	}
	
}
