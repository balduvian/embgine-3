package embgine.core;

import embgine.core.elements.Background;
import embgine.core.elements.Element;
import embgine.core.elements.GameObject;
import embgine.core.elements.Map;
import embgine.core.loaders.BackgroundLoader;
import embgine.core.loaders.BlockLoader;
import embgine.core.loaders.ElementLoader;
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
	
	private int layers;
	
	private String switchValue;
	
	private Class<? extends StateScript<?>> initialClass;
	private StateScript<SceneScript> initialState;
	
	private Class<? extends SceneScript> scriptClass;
	private SceneScript script;
	
	private int maxElements;
	private Manager manager;
	
	@SuppressWarnings("unchecked")
	public Scene(Class<? extends SceneScript> sceneScript, Class<? extends StateScript<?>> stateScript, int numLayers, int maxE, String[] sounds, Class<? extends Font>[] fonts, Class<? extends ObjectLoader>[] objects, Class<? extends BackgroundLoader>[] backgrounds, Class<? extends BlockLoader>[] blocks, Class<? extends MapReference>[] refs, Class<? extends MapLoader>[] maps) {
	
		scriptClass = sceneScript;
		initialClass = stateScript;
		
		layers = numLayers;
		
		maxElements = maxE;
		
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
	
	public void initialStart() {
		start(initialClass);
	}
	
	/**
	 * Starts the scene from a specified statescript
	 * @param s - class of the state script
	 */
	public void start(Class<? extends StateScript<? extends SceneScript>> s) {
		
		manager = new Manager(maxElements);
		
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
	
	/**
	 * gets the manager containing all elements in the scene, maybe you want to clear it idk
	 * 
	 * @return the scene's element manager
	 */
	public Manager getManager() {
		return manager;
	}
	
	/**
	 * updates the scene and all elements within
	 */
	public String update() {
		
		switchValue = null;
		
		manager.onScreenUpdate(camera);
		
		script.preUpdate();
		
		manager.update();
		
		script.update();
		
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
	
	public void destroyElement(Element o) {
		manager.remove(o.getManagerIndex());
	}
	
	public void switchScene(String s) {
		switchValue = s;
	}
	
	/**
     * use this method to create any element type in the scene
     * 
     * @param loader - the loader that creates the element
     * @param x - the x position for the element to be created at
     * @param y - the y posiyion for the element to be created at
     * 
     * @return the element that was created
     */
	public Element createElement(ElementLoader<?> loader, int x, int y, boolean e, Object... params) {
		Element ret = loader.create(this, x, y, e);
		Script<?> s = ret.getScript();
		if(s != null) {
			s.start(params);
		}
		return ret;
	}
	
	/**
	 * plays a sound at a certain volume. You can also set if the song repeats
	 * 
	 * @param sound - the sound to play
	 * @param v - the volume of the sound
	 * @param r - whether the sounds repeats or not
	 */
	public void playSound(Sound sound, float v, boolean r) {
		sound.setVolume(v);
		sound.play(r);
	}
	
	public void stopSound(Sound s) {
		s.stop();
	}
	
	public Script<Index> getScript() {
		return script;
	}
	
	public void centerCamera(int x, int y) {
		Transform cTransform = camera.getTransform();
		cTransform.setTranslation(x - cTransform.getWidth()/2 + Index.TILE/2, y - cTransform.getHeight()/2 + Index.TILE/2);
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