package embgine.core;

import embgine.core.elements.Element;
import embgine.core.elements.GameObject;
import embgine.core.elements.Map;
import embgine.core.loaders.BlockLoader;
import embgine.core.loaders.MapLoader;
import embgine.core.loaders.ObjectLoader;
import embgine.core.scripts.MapScript;
import embgine.core.scripts.ObjectScript;
import embgine.core.scripts.SceneScript;
import embgine.core.scripts.Script;
import embgine.core.scripts.StateScript;
import embgine.graphics.Camera;
import embgine.graphics.Sound;

public class Scene {
	
	private static Index index;
	private static Camera camera;
	
	private                        String[]  soundLoads;
	private Class<? extends         Font>[]   fontLoads;
	private Class<? extends ObjectLoader>[] objectLoads;
	private Class<? extends  BlockLoader>[]  blockLoads;
	private Class<? extends MapReference>[] mapReferenceLoads;
	private Class<? extends    MapLoader>[]    mapLoads;
	
	private StateScript<SceneScript> initialState;
	
	private Manager objectManager;
	private Manager mapManager;
	
	private int layers;
	
	private String switchValue;
	
	private SceneScript script;
	
	private Element[] currentObjects;
	private Element[] currentMaps;
	
	@SuppressWarnings("unchecked")
	public Scene(Class<? extends SceneScript> sceneScript, Class<? extends StateScript<?>> stateScript, int numLayers, int maxObjects, int maxMaps, String[] sounds, Class<? extends Font>[] fonts, Class<? extends ObjectLoader>[] objects, Class<? extends BlockLoader>[] blocks, Class<? extends MapReference>[] refs, Class<? extends MapLoader>[] maps) {
	
		try {
			script = (SceneScript)sceneScript.getConstructors()[0].newInstance();
			script.setScene(this);
			script.setParent(index);
		} catch (Exception ex) { }
		
		try {
			initialState = (StateScript<SceneScript>)stateScript.getConstructors()[0].newInstance();
			initialState.setScene(this);
			initialState.setParent(script);
		} catch (Exception ex) { }
		
		layers = numLayers;
		
		objectManager = new Manager(maxObjects);
		mapManager = new Manager(maxMaps);
		
		soundLoads  = sounds;
		fontLoads   = fonts;
		objectLoads = objects;
		blockLoads = blocks;
		mapReferenceLoads = refs;
		mapLoads    = maps;
	}
	
	public static void setup(Index x) {
		index = x;
		camera = index.getCamera();
	}
	
	public void start() {
		script.start();
		initialState.start();
	}
	
	public void resetObjects() {
		objectManager.clear();
	}
	
	public void resetMaps() {
		mapManager.clear();
	}
	
	public void resetAll() {
		resetObjects();
		resetMaps();
	}
	
	public String update() {
		
		currentObjects = objectManager.onScreenUpdate(camera);
		currentMaps = mapManager.onScreenUpdate(camera);
		
		switchValue = null;
		
		script.update();
		
		objectManager.update();	
		mapManager.update();
		
		return switchValue;
	}
	
	public void render() {
		for(int l = 0; l < layers; ++l) {
			mapManager.render(l);
			objectManager.render(l);
		}
	}
	
	public Index getIndex() {
		return index;
	}
	
	public void destroyObject(GameObject o) {
		objectManager.remove(o.getIndex());
	}
	
	public void destroyMap(Map o) {
		mapManager.remove(o.getIndex());
	}
	
	public void switchScene(String s) {
		switchValue = s;
	}
	
	public GameObject createObject(ObjectLoader loader, float x, float y, boolean e, Object... params) {
		GameObject ret = loader.create(this, x, y, e);
		objectManager.add(ret);
		ObjectScript os = (ObjectScript)ret.getScript();
		if(os != null) {
			ret.getScript().start(params);
		}
		return ret;
	}
	
	public Map createMap(MapLoader loader, float x, float y, boolean e, Object... params) {
		Map ret = loader.create(this, x, y, e);
		mapManager.add(ret);
		MapScript ms = (MapScript)ret.getScript();
		if(ms != null) {
			ret.getScript().start(params);
		}
		return ret;
	}
	
	public Sound sound(String s, float v, boolean r) {
		Sound sound = index.getSound(s);
		sound.setVolume(v);
		sound.play(r);
		return sound;
	}
	
	public void stopSound(Sound s) {
		s.stop();
	}
	
	public Script<Index> getScript() {
		return script;
	}
	
	public Element[] getCurrentObjects() {
		return currentObjects;
	}
	
	public Element[] getCurrentMaps() {
		return currentMaps;
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
	
	public Class<? extends MapLoader>[] getMaps() {
		return mapLoads;
	}
	
}