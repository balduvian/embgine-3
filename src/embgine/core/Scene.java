package embgine.core;

import embgine.core.elements.Background;
import embgine.core.elements.Element;
import embgine.core.elements.GameObject;
import embgine.core.elements.Map;
import embgine.core.loaders.BackgroundLoader;
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
import embgine.graphics.Transform;

public class Scene {
	
	private static Index index;
	private static Camera camera;
	
	private                        String[]  soundLoads;
	private Class<? extends         Font>[]   fontLoads;
	private Class<? extends ObjectLoader>[] objectLoads;
	private Class<? extends BackgroundLoader>[] backgroundLoads;
	private Class<? extends  BlockLoader>[]  blockLoads;
	private Class<? extends MapReference>[] mapReferenceLoads;
	private Class<? extends    MapLoader>[]    mapLoads;
	
	private Class<? extends StateScript<?>> initialClass;
	private StateScript<SceneScript> initialState;
	
	private Manager objectManager;
	private Manager mapManager;
	private Manager backgroundManager;
	
	private int layers;
	
	private String switchValue;
	
	private Class<? extends SceneScript> scriptClass;
	private SceneScript script;
	
	private Element[] currentObjects;
	private Element[] currentMaps;
	private Element[] currentBackgrounds;
	
	private int maxObjects;
	private int maxMaps;
	private int maxBackgrounds;
	
	@SuppressWarnings("unchecked")
	public Scene(Class<? extends SceneScript> sceneScript, Class<? extends StateScript<?>> stateScript, int numLayers, int maxO, int maxM, int maxB, String[] sounds, Class<? extends Font>[] fonts, Class<? extends ObjectLoader>[] objects, Class<? extends BackgroundLoader>[] backgrounds, Class<? extends BlockLoader>[] blocks, Class<? extends MapReference>[] refs, Class<? extends MapLoader>[] maps) {
	
		try {
			scriptClass = sceneScript;
			script = (SceneScript)scriptClass.newInstance();
			script.setScene(this);
			script.setParent(index);
		} catch (Exception ex) { 
			ex.printStackTrace();
			System.exit(-1);
		}
		
		try {
			initialClass = stateScript;
			initialState = (StateScript<SceneScript>)stateScript.getConstructors()[0].newInstance();
			initialState.setScene(this);
			initialState.setParent(script);
		} catch (Exception ex) { }
		
		layers = numLayers;
		
		maxObjects = maxO;
		maxMaps = maxM;
		maxBackgrounds = maxB;
		
		objectManager = new Manager(maxObjects);
		mapManager = new Manager(maxMaps);
		backgroundManager = new Manager(maxBackgrounds);
		
		soundLoads  = sounds;
		fontLoads   = fonts;
		objectLoads = objects;
		backgroundLoads = backgrounds;
		blockLoads = blocks;
		mapReferenceLoads = refs;
		mapLoads    = maps;
	}
	
	public static void setup(Index x) {
		index = x;
		camera = index.getCamera();
	}
	
	public void start() {
		
		objectManager = new Manager(maxObjects);
		mapManager = new Manager(maxMaps);
		backgroundManager = new Manager(maxBackgrounds);
		
		camera.getTransform().setPosition(0, 0);
		
		script.start();
		
		try {
			initialState = (StateScript<SceneScript>)initialClass.getConstructors()[0].newInstance();
			initialState.setScene(this);
			initialState.setParent(script);
		} catch (Exception ex) { }
		
		if(initialState != null) {
			initialState.start();
		}
	}
	
	/**
	 * Starts the scene from a specified statescript
	 * @param s - class of the state script
	 */
	public void start(Class<? extends StateScript<? extends SceneScript>> s) {
		
		objectManager = new Manager(maxObjects);
		mapManager = new Manager(maxMaps);
		backgroundManager = new Manager(maxBackgrounds);
		
		try {
			script = (SceneScript)scriptClass.newInstance();
			script.setScene(this);
			script.setParent(index);
		} catch (Exception ex) { 
			ex.printStackTrace();
			System.exit(-1);
		}
		script.start();
		
		try {
			StateScript<SceneScript> ss = (StateScript<SceneScript>)s.newInstance();
			ss.setScene(this);
			ss.setParent(script);
			if(ss != null) {
				ss.start();
			}
		} catch (Exception ex) { 
			ex.printStackTrace();
			System.exit(-1);
		}
	}
	
	public void resetObjects() {
		objectManager.clear();
	}
	
	public void resetMaps() {
		mapManager.clear();
	}
	
	public void resetBackgrounds() {
		backgroundManager.clear();
	}
	
	public void resetAll() {
		resetObjects();
		resetMaps();
		resetBackgrounds();
	}
	
	public String update() {
		
		currentObjects = objectManager.onScreenUpdate(camera);
		currentMaps = mapManager.onScreenUpdate(camera);
		currentBackgrounds = backgroundManager.onScreenUpdate(camera);
		
		switchValue = null;
		
		script.preUpdate();
		
		mapManager.update();
		objectManager.update();	
		
		script.update();
		
		return switchValue;
	}
	
	public void render() {
		
		for(int l = 0; l < layers; ++l) {
			mapManager.render(l);
			objectManager.render(l);
			backgroundManager.render(l);
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
	
	public void destroyBackground(Background o) {
		backgroundManager.remove(o.getIndex());
	}
	
	public void switchScene(String s) {
		switchValue = s;
	}
	
	/**
     * gets the fbo handle
     * @return fbo handle
     */
	public GameObject createObject(ObjectLoader loader, float x, float y, boolean e, Object... params) {
		GameObject ret = loader.create(this, x, y, e);
		objectManager.add(ret);
		ObjectScript os = (ObjectScript)ret.getScript();
		if(os != null) {
			ret.getScript().start(params);
		}
		return ret;
	}
	
	public Map createMap(MapLoader loader, int x, int y, boolean e, Object... params) {
		Map ret = loader.create(this, x, y, e);
		mapManager.add(ret);
		MapScript ms = (MapScript)ret.getScript();
		if(ms != null) {
			ret.getScript().start(params);
		}
		return ret;
	}
	
	public Background createBackground(BackgroundLoader loader, int x, int y, boolean e, float p) {
		Background ret = loader.create(x, y, e, p);
		backgroundManager.add(ret);
		return ret;
	}
	
	/*public Element createElement(ElementLoader loader, int x, int y, boolean e, Object... params) {
		Element ret = loader.create(this, x, y, e);
		Script<?> s = ret.getScript();
		if(s != null) {
			
		}
		return ret;
	}*/
	
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
	
	public Element[] getCurrentBackgrounds() {
		return currentBackgrounds;
	}
	
	public void centerCamera(int x, int y) {
		Transform cTransform = camera.getTransform();
		cTransform.setPosition(x - cTransform.getWidth()/2 + Index.TILE/2, y - cTransform.getHeight()/2 + Index.TILE/2);
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
	
	public Class<? extends BackgroundLoader>[] getBackgrounds(){
		return backgroundLoads;
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