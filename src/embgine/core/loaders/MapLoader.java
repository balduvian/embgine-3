package embgine.core.loaders;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import embgine.core.Block;
import embgine.core.Index;
import embgine.core.MapReference;
import embgine.core.Scene;
import embgine.core.elements.Map;
import embgine.core.scripts.MapScript;
import embgine.graphics.Transform;

public class MapLoader {
	
	public static final boolean   EDGE_FILL = false;
	public static final boolean EDGE_REPEAT =  true;
	
	private int mapWidth, mapHeight;
	private Class<? extends MapScript> script;
	private BlockLoader[][] tiles;
	private int[][] tileValues;
	private BlockLoader edgeTile;
	private boolean edgeMode;
	
	private String mapPath;
	private String referenceName;
	private String edgeTileName;

	private int type;
	
	public MapLoader(String mp, String rn, String en, boolean em, Class<? extends MapScript> sc) {
		mapPath = mp;
		referenceName = rn;
		edgeTileName = en;
		edgeMode = em;
		script = sc;
	}
	
	public Map create(Scene scene, float x, float y, boolean enabled) {
		
		Block[][] workingCopy = new Block[mapWidth][mapHeight];
		for(int i = 0; i < mapWidth; ++i) {
			for(int j = 0; j < mapHeight; ++j) {
				
				if(tiles[i][j] == null) {
					workingCopy[i][j] = null;
				}else {
					(workingCopy[i][j] = (tiles[i][j].create())).setValue(tileValues[i][j]);
				}
				
			}
		}
		
		MapScript sInstance = null;
		try {
			sInstance = (MapScript)script.getConstructors()[0].newInstance();
			sInstance.setScene(scene);
		} catch(Exception ex) { 
		}
		
		Transform transform = new Transform(x, y, mapWidth, mapHeight);
		
		return new Map(transform, sInstance, enabled, type, workingCopy, edgeMode, edgeTile);
	}
	
	public void init(Index index, int t) {
		
		type = t;
		
		edgeTile = index.getBlockLoader(edgeTileName);
		
		BufferedImage bi = null;
		try {
			bi = ImageIO.read(this.getClass().getClassLoader().getResource(mapPath));
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		
		mapWidth = bi.getWidth();
		mapHeight = bi.getHeight();
		
		tiles = new BlockLoader[mapWidth][mapHeight];
		tileValues = new int[mapWidth][mapHeight];
		
		MapReference ref = index.getMapReference(referenceName);
		
		int[] keys = ref.getBlockKeys();
		BlockLoader[] blocks = ref.getBlockRefs();
		int len = keys.length;
		Thread[] threadList = new Thread[len];
		
		//CREATE OUR THREADS
		for(int i = 0; i < len; ++i) {
			(threadList[i] = new Thread(new PlacerThread(keys[i], blocks[i], bi))).start();
		}
		
		//WAITS FOR THE THREADS TO DIE
		for(int i = 0; i < len; ++i) {
			try {
				threadList[i].join();
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
		}
		
		threadList = new Thread[len];
		
		//CREATE OUR THREADS
		for(int i = 0; i < len; ++i) {
			(threadList[i] = new Thread(new ValueThread(blocks[i]))).start();
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

	private class PlacerThread implements Runnable {
		
		int key;
		BlockLoader block;
		BufferedImage image;
		
		public PlacerThread(int k, BlockLoader b, BufferedImage i) {
			key = k;
			block = b;
			image = i;
		}
		
		public void run() {
			for(int i = 0; i < mapWidth; ++i) {
				for(int j = 0; j < mapHeight; ++j) {
					if(image.getRGB(i, j) == key) {
						tiles[i][j] = block;
					}
				}
			}
		}
		
	}
	
	private class ValueThread implements Runnable {
		
		private BlockLoader block;
		
		public ValueThread(BlockLoader b) {
			block = b;
		}
		
		public void run() {
			if(block.getTileable()) {
				for(int i = 0; i < mapWidth; ++i) {
					for(int j = 0; j < mapHeight; ++j) {
						if(tiles[i][j] == block) {
							tileValues[i][j] = block.getTile(isTile(i - 1, j, block), isTile(i - 1, j - 1, block), isTile(i, j - 1, block), isTile(i + 1, j - 1, block), isTile(i + 1, j, block), isTile(i + 1, j + 1, block), isTile(i, j + 1, block), isTile(i - 1, j + 1, block));
						}
					}
				}
			}
		}
		
		private int isTile(int x, int y, BlockLoader b) {
			return (tiles[x][y] == b) ? 1 : 0;
		}
		
	}
	
}