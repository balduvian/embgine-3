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
	private int[][][] tileValues;
	private boolean edgeMode;
	private BlockLoader edgeTile;
	
	private String mapPath;
	private String referenceName;
	private String edgeTileName;

	private boolean repeatUp;
	private boolean repeatRight;
	private boolean repeatDown;
	private boolean repeatLeft;
	
	private int type;
	
	public MapLoader(String mp, String rn, String en, boolean em, boolean ru, boolean rr, boolean rd, boolean rl, Class<? extends MapScript> sc) {
		mapPath = mp;
		referenceName = rn;
		edgeTileName = en;
		edgeMode = em;
		repeatUp = ru;
		repeatRight = rr;
		repeatDown = rd;
		repeatLeft = rl;
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
		
		return new Map(transform, sInstance, enabled, type, workingCopy, edgeMode, repeatUp, repeatRight, repeatDown, repeatLeft, edgeTile);
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
		tileValues = new int[mapWidth][mapHeight][2];
		
		MapReference ref = index.getMapReference(referenceName);
		
		int[] keys = ref.getBlockKeys();
		BlockLoader[] blocks = ref.getBlockRefs();
		int[][] values = ref.getBlockValues();
		
		int len = keys.length;
		Thread[] threadList = new Thread[len];
		
		//CREATE OUR THREADS
		for(int i = 0; i < len; ++i) {
			(threadList[i] = new Thread(new PlacerThread(keys[i], blocks[i], values[i], bi))).start();
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
		int[] value;
		
		public PlacerThread(int k, BlockLoader b, int[] v, BufferedImage i) {
			key = k;
			block = b;
			value = v;
			image = i;
		}
		
		public void run() {
			for(int i = 0; i < mapWidth; ++i) {
				for(int j = 0; j < mapHeight; ++j) {
					if(image.getRGB(i, j) == key) {
						tiles[i][j] = block;
						tileValues[i][j] = value;
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
							if(block.getClass().getSimpleName().equals("Block_Door")) {
								System.out.println(tileValues[i][j][0] + " " + tileValues[i][j][1]);
							}
						}
					}
				}
			}
		}
		
		private int isTile(int x, int y, BlockLoader b) {
			int widthMinus = mapWidth - 1;
			int heightMinus = mapHeight - 1;
			if(x < 0) {
				if(y < 0) {
					return (repeatLeft && repeatUp && tiles[0][0] == b) ? 1 : 0;
				} else if(y > heightMinus) {
					return (repeatLeft && repeatDown && tiles[0][heightMinus] == b) ? 1 : 0;
				} else {
					return (repeatLeft && tiles[0][y] == b) ? 1 : 0;
				}
			}else if(x > widthMinus) {
				if(y < 0) {
					return (repeatRight && repeatUp && tiles[widthMinus][0] == b) ? 1 : 0;
				} else if(y > heightMinus) {
					return (repeatRight && repeatDown && tiles[widthMinus][heightMinus] == b) ? 1 : 0;
				} else {
					return (repeatRight && tiles[widthMinus][y] == b) ? 1 : 0;
				}
			}else {
				if(y < 0) {
					return (repeatUp && tiles[x][0] == b) ? 1 : 0;
				} else if(y > heightMinus) {
					return (repeatDown && tiles[x][heightMinus] == b) ? 1 : 0;
				} else {
					return (tiles[x][y] == b) ? 1 : 0;
				}
			}
		}
		
	}
	
}