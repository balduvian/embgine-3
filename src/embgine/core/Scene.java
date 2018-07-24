package embgine.core;

import org.joml.Vector4f;

import embgine.core.loaders.MapLoader;
import embgine.core.loaders.ObjectLoader;
import embgine.core.loaders.SoundLoader;
import embgine.graphics.Packet;
import embgine.graphics.Shader;
import embgine.graphics.Shape;
import embgine.graphics.Texture;
import game.shapes.Shape_Rect;

public class Scene {
	
	private static Index index;
	
	private static Shape mapRect;
	
	private SortLayer[] sortLayers;
	public static final int LAYERS = 5;
	
	private Map map;
	
	private Class<? extends Map> startMap;
	
	private int switchValue;
	
	protected                        String[]  soundLoads;
	protected Class<? extends         Font>[]   fontLoads;
	protected Class<? extends ObjectLoader>[] objectLoads;
	protected Class<? extends        Block>[]  blockLoads;
	protected Class<? extends          Map>[]    mapLoads;
	
	public Scene(Class<? extends Map> sl, String[] sounds, Class<? extends Font>[] fonts, Class<? extends ObjectLoader>[] objects, Class<? extends Block>[] blocks, Class<? extends Map>[] maps) {
		startMap  = sl;
		soundLoads  = sounds;
		fontLoads   = fonts;
		objectLoads = objects;
		mapLoads    = maps;
		
		sortLayers = new SortLayer[LAYERS];
		sortLayers[0] = new SortLayer();
		sortLayers[1] = new SortLayer();
		sortLayers[2] = new SortLayer();
		sortLayers[3] = new SortLayer();
		sortLayers[4] = new SortLayer();
	}
	
	public static void giveIndex(Index x) {
		index = x;
		
		mapRect = new Shape(
			index.getCamera(),
			new float[] {
	           0.5f, -0.5f, 0,
	           0.5f,  0.5f, 0,
	           -0.5f,  0.5f, 0,
	           -0.5f, -0.5f, 0
			}, new int[] {
				0, 1, 3,
				1, 2, 3
			}, new float[] {
		        1, 0,
		        1, 1,
		        0, 1,
		        0, 0
			}
		);
	}
	
	public String[] getSounds() {
		return soundLoads;
	}
	
	public Class<? extends Font>[] getFonts() {
		return fontLoads;
	}
	
	public Class<? extends ObjectLoader>[] getObjects() {
		return objectLoads;
	}
	
	public Class<? extends Block>[] getBlocks() {
		return blockLoads;
	}
	
	public Class<? extends Map>[] getMaps() {
		return mapLoads;
	}
	
	public void start(String mapName) {
		sortLayers[0].clear();
		sortLayers[1].clear();
		sortLayers[2].clear();
		sortLayers[3].clear();
		sortLayers[4].clear();
		
		map = index.getMap(mapName);
		
		map.refreshWorkingCopy(this);
	}
	
	public int update() {
		
		switchValue = -1;
		
		for(int i = LAYERS-1; i > -1; --i) {
			sortLayers[i].update();
		}		
		
		return switchValue;
	}
	
	public void render() {
		
		sortLayers[0].render();
		sortLayers[1].render();
		
		int width = map.getWidth();
		int height = map.getHeight();
		
		Packet packet = new Packet(0);
		
		mapRect.getTransform().setSize(1, 1);
		Shader shader = Shader.TIL2DSHADER;
		
		for(int i = 0; i < width; ++i) {
			for(int j = 0; j < height; ++j) {
				Block b = map.access(i, j);
				Texture t = b.getTexture();
				Vector4f frame = t.getFrame(b.getValue());
				
				packet.giveFrame(frame.x, frame.y, frame.z, frame.w);
				t.bind();
				mapRect.getTransform().setPosition(i, j);
				
				shader.enable(packet);
				shader.setMvp(mapRect.getGuiMatrix());
				mapRect.getVAO().render();
				shader.disable();
				
				t.unbind();
			}
		}
		
		sortLayers[2].render();
		sortLayers[3].render();
		sortLayers[4].render();
	}
	
	public Index getIndex() {
		return index;
	}
	
	public Map getMap() {
		return map;
	}
	
	public void destroy(GameObject o) {
		sortLayers[o.getLayer()].remove(o.getIndex());
	}
	
	public void switchScene(int s) {
		switchValue = s;
	}
	
	public GameObject createEntity(String o, float x, float y, float[] params) {
		GameObject ret = index.getObject(this, o);
		sortLayers[ret.getLayer()].add(ret);
		ret.getTransform().setPosition(x, y);
		ret.getScript().start(params);
		return ret;
	}
	
	public void sendForward(GameObject o) {
		sortLayers[o.getLayer()].sendForward(o.getIndex());
	}
	
	public void soundEffect(String s, float v) {
		index.getSound(s).setVolume(v);
		index.getSound(s).play(false);
	}

}
