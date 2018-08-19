package embgine.core.elements;

import org.joml.Vector4f;

import embgine.core.Base;
import embgine.core.Block;
import embgine.core.Index;
import embgine.core.loaders.BlockLoader;
import embgine.core.scripts.MapScript;
import embgine.graphics.Camera;
import embgine.graphics.Texture;
import embgine.graphics.Transform;
import embgine.graphics.shaders.Shader;
import embgine.graphics.shapes.Shape;

public class Map extends Element{
	
	private static Index index;
	private static Shader shader;
	private static Camera camera;
	private static Shape mapRect;
	
	private Block[][] map;
	private BlockLoader edgeTile;
	
	private int mapWidth;
	private int mapHeight;
	
	private boolean repeatUp;
	private boolean repeatRight;
	private boolean repeatDown;
	private boolean repeatLeft;
	
	public Map(Transform transform, MapScript script, boolean enabled, int type, Block[][] m, boolean edgeUp, boolean edgeRight, boolean edgeDown, boolean edgeLeft, BlockLoader tile, int layer) {
		super(transform, script, enabled, type, layer);
		
		if(script != null) {
			script.setParent(this);
		}
		
		map = m;
		
		mapWidth = map.length;
		mapHeight = map[0].length;
		
		edgeTile = tile;
		
		repeatUp = edgeUp;
		repeatRight = edgeRight;
		repeatDown = edgeDown;
		repeatLeft = edgeLeft;
	}
	
	public static void setup(Index x) {
		index = x;
		
		camera = index.getCamera();
		
		mapRect = Shape.RECT;
		
		shader = Shader.TIL2DSHADER;
	}
	
	public void subRender(int layer) {
		
		float mapX = transform.abcissa;
		float mapY = transform.ordinate;
		
		Transform blockTransform = new Transform(Index.TILE * transform.wScale, Index.TILE * transform.hScale);
		
		Transform cameraTransform = camera.getTransform();
		float x = cameraTransform.abcissa;
		float y = cameraTransform.hScale;
		float gw = index.getGameWidth();
		float gh = index.getGameHeight();
		
		int  left = (int) Math.floor( (x - mapX     ) / Index.TILE );
		int right = (int) Math.ceil ( (x + gw - mapX) / Index.TILE );
		int    up = (int) Math.floor( (y - mapY     ) / Index.TILE );
		int  down = (int) Math.ceil ( (y + gh - mapY) / Index.TILE );
		
		for(int i = left; i <= right; ++i) {
			for(int j = up; j <= down; ++j) {
				Block b = edgeAccess(i, j);
				if(b != null && b.getLayer() == layer) {
					
					Texture tex = b.getTexture();
					Vector4f frame = tex.getFrame(b.getValueX(), b.getValueY());
					
					blockTransform.setTranslation(mapX + (i * Index.TILE * transform.wScale), mapY + (j * Index.TILE * transform.hScale));

					tex.bind();
					
					shader.enable(new float[] {frame.x, frame.y, frame.z, frame.w, 1f, 1f, 1f, 1f});
					
					shader.setMvp(camera.getModelViewProjectionMatrix(camera.getModelMatrix(blockTransform)));
					
					mapRect.render();
					
					shader.disable();
					
					tex.unbind();
				}
			}
		}
	}
	
	public boolean onScreenUpdate(Camera camera) {
		if(enabled) {
			float ex = transform.abcissa;
			float ey = transform.ordinate;
			float ew = mapWidth * Index.TILE;
			float eh = mapHeight * Index.TILE;
			
			Transform cTransform = camera.getTransform();
			
			float cx = cTransform.abcissa;
			float cy = cTransform.ordinate;
			float cw = cTransform.getWidth();
			float ch = cTransform.getHeight();
			
			onScreen = (ex + ew > cx || repeatRight) && (ex < cx + cw || repeatLeft) && (ey + eh > cy || repeatDown) && (ey < cy + ch || repeatUp);
			return onScreen;
		}
		return false;
	}
	
	/**
	 * accesses the map array, assuming that you know that you're accessing within the map bounds
	 * 
	 * @param x - the x position you're accessing
	 * @param y - the y position you're acessing
	 * 
	 * @return the block instance at the acessed x,y position
	 */
	public Block safeAccess(int x, int y) {
		return map[x][y];
	}
	
	/**
	 * accesses the map array, if the accessed location is off the map array, it wraps based on the repeat modes on the edges
	 * 
	 * @param x - the x position you're accessing
	 * @param y - the y position you're acessing
	 * 
	 * @return the block instance at the acessed x,y position, or what the block would be based on edge information
	 */
	public Block edgeAccess(int x, int y) {
		int minusWidth = mapWidth - 1;
		int minusHeight = mapHeight - 1;
		
		if(x < 0) {
			if(y < 0) {
				if(repeatLeft && repeatUp) {
					return map[0][0];
				}else {
					return edgeTile.create();
				}
			}else if(y > minusHeight) {
				if(repeatLeft && repeatDown) {
					return map[0][minusHeight];
				}else {
					return edgeTile.create();
				}
			}else {
				if(repeatLeft) {
					return map[0][y];
				}else {
					return edgeTile.create();
				}
			}
		}else if(x > minusWidth) {
			if(y < 0) {
				if(repeatRight && repeatUp) {
					return map[minusWidth][0];
				}else {
					return edgeTile.create();
				}
			}else if(y > minusHeight) {
				if(repeatRight && repeatDown) {
					return map[minusWidth][minusHeight];
				}else {
					return edgeTile.create();
				}
			}else {
				if(repeatRight) {
					return map[minusWidth][y];
				}else {
					return edgeTile.create();
				}
			}
		}else {
			if(y < 0) {
				if(repeatUp) {
					return map[x][0];
				}else {
					return edgeTile.create();
				}
			}else if(y > minusHeight) {
				if(repeatDown) {
					return map[x][minusHeight];
				}else {
					return edgeTile.create();
				}
			}else {
				return map[x][y];
			}
		}
	
	}
	
	/**
	 * finds the internal array x location of the map from a certain abcissa position in the world
	 * 
	 * @param wx - the world abcissa coordinate
	 * 
	 * @return the internal map x position at the world abcissa position 
	 */
	public int worldToMapX(float wx) {
		float ret = ( (wx - transform.abcissa) / (Index.TILE) );
		if(ret < 0) {
			--ret;
		}
		return (int) ret;
	}
	
	/**
	 * finds the internal array y location of the map from a certain ordinate position in the world
	 * 
	 * @param wy - the world abcissa coordinate
	 * 
	 * @return the internal map y position at the world ordinate position 
	 */
	public int worldToMapY(float wy) {
		float ret = ( (wy - transform.ordinate ) / (Index.TILE) );
		if(ret < 0) {
			--ret;
		}
		return (int) ret;
	}
	
	/**
	 * finds the world abcissa position of a certain internal map x position 
	 * 
	 * @param mx - the world abcissa coordinate
	 * 
	 * @return the world abcissa position at the map x position
	 */
	public float mapToWorldX(int mx) {
		return (int) (Index.TILE * mx + transform.abcissa);
	}
	
	/**
	 * finds the world abcissa position of a certain internal map x position 
	 * 
	 * @param mx - the world abcissa coordinate
	 * 
	 * @return the world abcissa position at the map x position
	 */
	public float mapToWorldY(int my) {
		return (int) (Index.TILE * my + transform.ordinate);
	}
	
	public int getWidth() {
		return mapWidth;
	}
	
	public int getHeight() {
		return mapHeight;
	}
	
	/**
	 * replaces the block at a certain index with a new block instance 
	 * 
	 * @param b - the block instance to place in the map
	 * @param x - the x position you place the block in
	 * @param y - the y position you place the block in
	 */
	public void setBlock(Block b, int x, int y) {
		map[x][y] = b;
	}

	public void subUpdate() {
	
	}
	
}