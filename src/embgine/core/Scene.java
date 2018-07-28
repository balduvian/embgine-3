package embgine.core;

import org.joml.Vector4f;

import embgine.core.loaders.BlockLoader;
import embgine.core.loaders.ObjectLoader;
import embgine.graphics.Packet;
import embgine.graphics.Shape;
import embgine.graphics.Texture;
import embgine.graphics.shaders.Shader;

public class Scene {
	
	private static Index index;
	
	private static Shape mapRect;
	
	private SortLayer[] sortLayers;
	public static final int LAYERS = 5;
	
	private Map currentMap;
	
	private String startMapName;
	private Map startMap;
	
	private String switchValue;
	
	private                        String[]  soundLoads;
	private Class<? extends         Font>[]   fontLoads;
	private Class<? extends ObjectLoader>[] objectLoads;
	private Class<? extends  BlockLoader>[]  blockLoads;
	private Class<? extends MapReference>[] mapReferenceLoads;
	private Class<? extends          Map>[]    mapLoads;
	
	public Scene(String sl, String[] sounds, Class<? extends Font>[] fonts, Class<? extends ObjectLoader>[] objects, Class<? extends BlockLoader>[] blocks, Class<? extends MapReference>[] refs, Class<? extends Map>[] maps) {
		startMapName = sl;
		soundLoads  = sounds;
		fontLoads   = fonts;
		objectLoads = objects;
		mapReferenceLoads = refs;
		mapLoads    = maps;
		
		sortLayers = new SortLayer[LAYERS];
		sortLayers[0] = new SortLayer();
		sortLayers[1] = new SortLayer();
		sortLayers[2] = new SortLayer();
		sortLayers[3] = new SortLayer();
		sortLayers[4] = new SortLayer();
	}
	
	public void loadStartMap() {
		startMap  = index.getMap(startMapName);
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
	
	public void start(String mapName) {
		sortLayers[0].clear();
		sortLayers[1].clear();
		sortLayers[2].clear();
		sortLayers[3].clear();
		sortLayers[4].clear();
		
		currentMap = index.getMap(mapName);
		
		currentMap.refreshWorkingCopy(this);
	}
	
	public void start() {
		sortLayers[0].clear();
		sortLayers[1].clear();
		sortLayers[2].clear();
		sortLayers[3].clear();
		sortLayers[4].clear();
		
		currentMap = startMap;
		
		currentMap.refreshWorkingCopy(this);
	}
	
	public String update() {
		
		switchValue = null;
		
		for(int i = LAYERS-1; i > -1; --i) {
			sortLayers[i].update();
		}		
		
		return switchValue;
	}
	
	public void render() {
		
		sortLayers[0].render();
		sortLayers[1].render();
		
		int width = currentMap.getWidth();
		int height = currentMap.getHeight();
		
		Packet packet = new Packet(0);
		
		mapRect.getTransform().setSize(1, 1);
		Shader shader = Shader.TIL2DSHADER;
		
		for(int i = 0; i < width; ++i) {
			for(int j = 0; j < height; ++j) {
				Block b = currentMap.access(i, j);
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
	
	public Map getMap() {
		return currentMap;
	}
	
	public void destroy(GameObject o) {
		sortLayers[o.getLayer()].remove(o.getIndex());
	}
	
	public void switchScene(String s) {
		switchValue = s;
	}
	
	public GameObject createEntity(String o, float x, float y, Object... params) {
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
	
	/*
	######################################################################################
	########################### GETTERS ##################################################
	###################################################################################### 
	*/
	
	public String[] getSounds() {
		return soundLoads;
	}
	
	public Class<? extends Font>[] getFonts() {
		return fontLoads;
	}
	
	public Class<? extends ObjectLoader>[] getObjects() {
		return objectLoads;
	}
	
	public Class<? extends BlockLoader>[] getBlocks() {
		return blockLoads;
	}
	
	public Class<? extends MapReference>[] getMapReferences(){
		return mapReferenceLoads;
	}
	
	public Class<? extends Map>[] getMaps() {
		return mapLoads;
	}

}