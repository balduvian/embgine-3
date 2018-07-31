package embgine.core;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import embgine.core.loaders.BlockLoader;

public class Map {
	
	private String refName;
	private String mapPath;
	
	private MapScript scInstance;
	private Class<? extends MapScript> script;
	private BlockLoader[][] tiles;
	private Block[][] workingCopy;
	private BlockLoader edgeTile;
	
	private int width;
	private int height;
	
	public static final boolean   EDGE_FILL = false;
	public static final boolean EDGE_REPEAT =  true;
	
	private boolean edgeMode;
	
	public Map(String mp, String rn, boolean em, BlockLoader et, Class<? extends MapScript> sc) {
		
		mapPath = mp;
		refName = rn;
		
		script = sc;
				
		edgeMode = em;
		edgeTile = et;
	}
	
	public void init(Index index) {
		
		BufferedImage bi = null;
		try {
			bi = ImageIO.read(this.getClass().getClassLoader().getResource(mapPath));
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		
		width = bi.getWidth();
		height = bi.getHeight();
		
		tiles = new BlockLoader[width][height];
		
		MapReference ref = index.getMapReference(refName);
		
		int[] keys = ref.getBlockKeys();
		BlockLoader[] blocks = ref.getBlockRefs();
		int len = keys.length;
		Thread[] threadList = new Thread[len];
		
		//CREATE OUR THREADS
		for(int i = 0; i < len; ++i) {
			(threadList[i] = new Thread(new LoaderThread(keys[i], blocks[i], bi))).start();
		}
		
		//WAITS FOR THE THREADS TO DIE
		for(int i = 0; i < len; ++i) {
			try {
				threadList[i].join();
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
		}
		
	}
	
	private class LoaderThread implements Runnable{
		
		int key;
		BlockLoader block;
		BufferedImage image;
		
		public LoaderThread(int k, BlockLoader b, BufferedImage i) {
			key = k;
			block = b;
			image = i;
		}
		
		public void run() {
			for(int i = 0; i < width; ++i) {
				for(int j = 0; j < height; ++j) {
					if(image.getRGB(i, j) == key) {
						tiles[i][j] = block;
					}
				}
			}
		}
		
	}
	
	public void refreshWorkingCopy(Scene sc) {
		
		workingCopy = new Block[width][height];
		for(int i = 0; i < width; ++i) {
			for(int j = 0; j < height; ++j) {
				
				if(tiles[i][j] == null) {
					workingCopy[i][j] = null;
				}else {
					workingCopy[i][j] = tiles[i][j].create();
				}
				
			}
		}
		
		try {
			scInstance = (MapScript)script.getConstructors()[0].newInstance(sc);
		}catch(Exception ex) {
			ex.printStackTrace();
			System.exit(-1);
		}
		
		scInstance.start();
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
				return (Block)edgeTile.create();
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