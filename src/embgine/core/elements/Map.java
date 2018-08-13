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
	
	private boolean edgeMode;
	private boolean repeatUp;
	private boolean repeatRight;
	private boolean repeatDown;
	private boolean repeatLeft;
	
	private float speed;
	private float movement;
	
	private int lastX;
	private int move;
	
	public Map(Transform transform, MapScript script, boolean enabled, int type, Block[][] m, boolean edge, boolean edgeUp, boolean edgeRight, boolean edgeDown, boolean edgeLeft, BlockLoader tile) {
		super(transform, script, enabled, type);
		
		if(script != null) {
			script.setParent(this);
		}
		
		map = m;
		
		mapWidth = map.length;
		mapHeight = map[0].length;
		
		edgeMode = edge;
		edgeTile = tile;
		
		repeatUp = edgeUp;
		repeatRight = edgeRight;
		repeatDown = edgeDown;
		repeatLeft = edgeLeft;
		
		lastX = transform.getX();
	}
	
	public static void setup(Index x) {
		index = x;
		
		camera = index.getCamera();
		
		mapRect = Shape.RECT;
		
		shader = Shader.TIL2DSHADER;
	}
	
	public void subRender(int layer) {
		
		float mapX = transform.getX();
		float mapY = transform.getY();
		
		mapRect.getTransform().setSize(Index.TILE * transform.getXScale(), Index.TILE * transform.getYScale());
		
		Transform cameraTransform = camera.getTransform();
		int x = cameraTransform.getX();
		int y = cameraTransform.getY();
		float gw = index.getGameWidth();
		float gh = index.getGameHeight();
		
		int  left = (int) Math.floor( (x - mapX     ) / Index.TILE );
		int right = (int) Math.ceil ( (x + gw - mapX) / Index.TILE );
		int    up = (int) Math.floor( (y - mapY     ) / Index.TILE );
		int  down = (int) Math.ceil ( (y + gh - mapY) / Index.TILE );
		
		for(int i = left; i <= right; ++i) {
			for(int j = up; j <= down; ++j) {
				Block b = access(i, j);
				if(b != null && b.getLayer() == layer) {
					
					Texture t = b.getTexture();
					Vector4f frame = t.getFrame(b.getValueX(), b.getValueY());
					
					mapRect.getTransform().setPosition(mapX + (i * Index.TILE), mapY + (j * Index.TILE));

					t.bind();
					
					shader.enable(new float[] {frame.x, frame.y, frame.z, frame.w, 1f, 1f, 1f, 1f});
					shader.setMvp(mapRect.getMatrix());
					mapRect.getVAO().render();
					shader.disable();
					
					t.unbind();
				}
			}
		}
	}
	
	public boolean onScreenUpdate(Camera camera) {
		if(enabled) {
			float ex = transform.     getX();
			float ey = transform.     getY();
			float ew = mapWidth * Index.TILE;
			float eh = mapHeight * Index.TILE;
			
			Transform cTransform = camera.getTransform();
			
			float cx = cTransform.     getX();
			float cy = cTransform.     getY();
			float cw = cTransform. getWidth();
			float ch = cTransform.getHeight();
			
			onScreen = (ex + ew > cx || repeatRight) && (ex < cx + cw || repeatLeft) && (ey + eh > cy || repeatDown) && (ey < cy + ch || repeatUp);
			return onScreen;
		}
		return false;
	}
	
	public int accessX(float lx) {
		float ret = ( (lx - transform.getX() ) / (Index.TILE) );
		if(ret < 0) {
			--ret;
		}
		return (int) ret;
	}
	
	public int accessY(float ly) {
		float ret = ( (ly - transform.getY() ) / (Index.TILE) );
		if(ret < 0) {
			--ret;
		}
		return (int) ret;
	}
	
	public Block access(int x, int y) {
		if(edgeMode) {
			return repeatAccess(x, y);
		}else {
			return edgeAccess(x, y);
		}
	}
	
	public int positionX(int mx) {
		return Index.TILE * mx + transform.getX();
	}

	public int positionY(int my) {
		return Index.TILE * my + transform.getY();
	}

	private Block edgeAccess(int x, int y) {
		try {
			return map[x][y];
		}catch(Exception ex) {
			try {
				if(!repeatUp && y < 0) {
					throw new Exception();
				}
				if(!repeatDown && y >= mapHeight) {
					throw new Exception();
				}
				if(!repeatLeft && x < 0) {
					throw new Exception();
				}
				if(!repeatRight && x >= mapHeight) {
					throw new Exception();
				}
				return (Block)edgeTile.create();
			} catch(Exception ex2) {
				return null;
			}
		}
	}
	
	private Block repeatAccess(int x, int y) {
		if(x < 0) {
			if(repeatLeft) {
				x = 0;
			}else {
				return null;
			}
		}else if(x >= mapWidth){
			if(repeatRight) {
				x = mapWidth-1;
			}else {
				return null;
			}
		}
		if(y < 0) {
			if(repeatUp) {
				y = 0;
			}else {
				return null;
			}
		}else if(y >= mapHeight){
			if(repeatDown) {
				y = mapHeight-1;
			}else {
				return null;
			}
		}
		return map[x][y];
	}
	
	public int getWidth() {
		return mapWidth;
	}
	
	public int getHeight() {
		return mapHeight;
	}
	
	public void setBlock(Block b, int x, int y) {
		map[x][y] = b;
	}

	public void subUpdate() {
		movement = (float) (speed * Base.time);
		transform.moveX(movement);
		int nowX = transform.getX();
		move = nowX-lastX;
		lastX = nowX;
	}
	
	public void setSpeed(float s) {
		speed = s;
	}
	
	public int getMove() {
		return move;
	}
	
}