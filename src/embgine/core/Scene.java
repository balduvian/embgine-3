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
	
	private Manager manager;
	
	private int layers;
	
	private String switchValue;
	
	private SceneScript script;
	
	@SuppressWarnings("unchecked")
	public Scene(Class<? extends SceneScript> sceneScript, Class<? extends StateScript<?>> stateScript, int numLayers, int managerSize, String[] sounds, Class<? extends Font>[] fonts, Class<? extends ObjectLoader>[] objects, Class<? extends BlockLoader>[] blocks, Class<? extends MapReference>[] refs, Class<? extends MapLoader>[] maps) {
	
		try {
			script = (SceneScript)sceneScript.getConstructors()[0].newInstance(this);
			script.setScene(this);
			script.setParent(index);
		} catch (Exception ex) { }
		
		try {
			initialState = (StateScript<SceneScript>)stateScript.getConstructors()[0].newInstance(this);
			initialState.setScene(this);
			initialState.setParent(script);
		} catch (Exception ex) { }
		
		layers = numLayers;
		
		manager = new Manager(managerSize);
		
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
		manager.clearType(GameObject.class);
	}
	
	public void resetMaps() {
		manager.clearType(Map.class);
	}
	
	public void resetAll() {
		resetObjects();
		resetMaps();
	}
	
	public String update() {
		switchValue = null;
		
		script.update();
		
		manager.update(camera);	
		
		return switchValue;
	}
	
	public void render() {
		for(int l = 0; l < layers; ++l) {
			manager.render(l);
		}
	}
	
	public Index getIndex() {
		return index;
	}
	
	public Manager getManager() {
		return manager;
	}
	
	public void destroy(Element o) {
		manager.remove(o.getIndex());
	}
	
	public void switchScene(String s) {
		switchValue = s;
	}
	
	public GameObject createObject(ObjectLoader loader, float x, float y, int layer, boolean e, Object... params) {
		GameObject ret = loader.create(this, x, y, e);
		manager.add(ret);
		ret.setLayer(layer);
		ObjectScript os = (ObjectScript)ret.getScript();
		if(os != null) {
			ret.getScript().start(params);
		}
		return ret;
	}
	
	public Map createMap(MapLoader loader, float x, float y, boolean e, Object... params) {
		Map ret = loader.create(this, x, y, e);
		manager.add(ret);
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