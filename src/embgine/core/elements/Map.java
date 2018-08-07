package embgine.core.elements;

import org.joml.Vector4f;

import embgine.core.Block;
import embgine.core.Index;
import embgine.core.loaders.BlockLoader;
import embgine.core.scripts.MapScript;
import embgine.graphics.Camera;
import embgine.graphics.Packet;
import embgine.graphics.Texture;
import embgine.graphics.Transform;
import embgine.graphics.shaders.Shader;
import embgine.graphics.shapes.Shape;

public class Map extends Element{
	
	private static Index index;
	private static Camera camera;
	private static Shape mapRect;
	private static Packet packet;
	
	private Block[][] map;
	private BlockLoader edgeTile;
	
	private int mapWidth;
	private int mapHeight;
	
	private boolean edgeMode;
	
	public Map(Transform transform, MapScript script, boolean enabled, int type, Block[][] m, boolean edge, BlockLoader tile) {
		super(transform, script, enabled, type);
		
		if(script != null) {
			script.setParent(this);
		}
		
		map = m;
		
		mapWidth = map.length;
		mapHeight = map[0].length;
		
		edgeMode = edge;
		edgeTile = tile;
	}
	
	public static void setup(Index x) {
		index = x;
		
		camera = index.getCamera();
		
		mapRect = Shape.RECT;
		
		packet = new Packet(1, 1, 1, 1);
	}
	
	public void subRender(int layer) {
		mapRect.getTransform().setSize(1, 1);
		Shader shader = Shader.TIL2DSHADER;
		
		Transform cameraTransform = camera.getTransform();
		int x = Math.round(cameraTransform.getX());
		int y = Math.round(cameraTransform.getY());
		int gwHalf = (int) Math.ceil( index.getGameWidth() / 2);
		int ghHalf = (int) Math.ceil(index.getGameHeight() / 2);
		
		int  left = x - gwHalf;
		int right = x + gwHalf;
		int    up = y - ghHalf;
		int  down = y + ghHalf;
		
		for(int i = left; i <= right; ++i) {
			for(int j = up; j <= down; ++j) {
				Block b = access(i, j);
				if(b != null && b.getLayer() == layer) {
					
					Texture t = b.getTexture();
					Vector4f frame = t.getFrame(b.getValue());
					
					packet.giveFrame(frame.x, frame.y, frame.z, frame.w);
					
					mapRect.getTransform().setPosition(i, j);
					
					t.bind();
					
					shader.enable(packet);
					shader.setMvp(mapRect.getMatrix());
					mapRect.getVAO().render();
					shader.disable();
					
					t.unbind();
				}
			}
		}
	}
	
	public Block access(int x, int y) {
		if(edgeMode) {
			return repeatAccess(x, y);
		}else {
			return edgeAccess(x, y);
		}
	}
	
	private Block edgeAccess(int x, int y) {
		try {
			return map[x][y];
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
		}else if(x >= mapWidth){
			x = mapWidth-1;
		}
		if(y < 0) {
			y = 0;
		}else if(y >= mapHeight){
			y = mapHeight-1;
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
	
}