package embgine.core;

import java.util.HashMap;

import embgine.core.elements.Map;
import embgine.core.loaders.BackgroundLoader;
import embgine.core.loaders.BlockLoader;
import embgine.core.loaders.MapLoader;
import embgine.core.loaders.ObjectLoader;
import embgine.graphics.ALManagement;
import embgine.graphics.Camera;
import embgine.graphics.Sound;
import embgine.graphics.Window;
import embgine.graphics.shapes.Shape;

public class Index {

	public static int TILE;
	
	private Window window;
	private Camera camera;
	private ALManagement audio;
	
	private HashMap<String, Scene       >        _sceneMap;
	private HashMap<String, Sound       >         soundMap;
	private HashMap<String, Font        >          fontMap;
	private HashMap<String, ObjectLoader>  objectLoaderMap;
	private HashMap<String, BackgroundLoader> backgroundLoaderMap;
	private HashMap<String, BlockLoader >         blockMap;
	private HashMap<String, MapReference>  mapReferenceMap;
	private HashMap<String, MapLoader   >     mapLoaderMap;
	
	private float gameWidth;
	private float gameHeight;
	private String gameName;
	private boolean debug;
	
	public Index(int ts, float gw, float gh, String n, boolean db, Camera c, Window w, ALManagement a, Class<? extends Scene>[] sc) {
		TILE = ts;
		gameWidth = gw;
		gameHeight = gh;
		gameName = n;
		debug = db;
		camera = c;
		window = w;
		audio = a;
		
		Scene.setup(this);
		Map.setup(this);
		
		_loadScenes(sc);
	}
	
	public void sceneLoad(Scene sc) {
		loadSounds(sc.getSounds());
		loadFonts(sc.getFonts());
		loadGameObjects(sc.getObjects());
		loadBackgrounds(sc.getBackgrounds());
		loadBlocks(sc.getBlocks());
		loadMapReferences(sc.getMapReferences());
		loadMaps(sc.getMaps());
		
		sc.start();
	}
	
	/*
	#################################################################################
	################################# BASE LOADS ####################################
	#################################################################################
	*/
	
	private void _loadScenes(Class<? extends Scene>[] scenes) {
		int len = scenes.length;
		_sceneMap = new HashMap<String, Scene>(len, 1.0f);
		for(int i = 0; i < len; ++i) {
			
			Class<? extends Scene> fc = scenes[i];
			
			Scene f = null;
			
			try {
				f = (Scene)fc.newInstance();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			
			_sceneMap.put(Utils.getHashName(fc), f);
		}
	}
	
	/*
	#################################################################################
	################################# PER-SCENE LOADS ###############################
	#################################################################################
	*/
	
	private void loadSounds(String[] soundPaths) {
		int len = soundPaths.length;
		soundMap = new HashMap<String, Sound>(len, 1.0f);
		for(int i = 0; i < len; ++i) {
			String name = soundPaths[i];
			
			Sound sound = new Sound(name);
			
			StringBuilder build = new StringBuilder(32);
			int nameLen = name.length();
			
			int j = nameLen - 2;
			
			for( ; j > -1; --j) {
				if(name.charAt(j) == '/') {
					break;
				}
			}
			
			for(++j; j < nameLen; ++j) {
				build.append(name.charAt(j));
			}
			
			System.out.println(build.toString());
			
			soundMap.put(build.toString(), sound);
		}
	}
	
	private void loadFonts(Class<? extends Font>[] fonts) {
		int len = fonts.length;
		fontMap = new HashMap<String, Font>(len, 1.0f);
		for(int i = 0; i < len; ++i) {
			
			Class<? extends Font> fc = fonts[i];
			
			Font f = null;
			
			try {
				f = (Font)fc.getConstructors()[0].newInstance();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			
			fontMap.put(Utils.getHashName(fc), f);
		}
	}
	
	private void loadGameObjects(Class<? extends ObjectLoader>[] objects) {

		int len = objects.length;
		objectLoaderMap = new HashMap<String, ObjectLoader>(len, 1.0f);
		
		for(int i = 0; i < len; ++i) {
			
			Class<? extends ObjectLoader> oc = objects[i];
					
			ObjectLoader loader = null;
			try {
				loader = (ObjectLoader)oc.newInstance();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			
			loader.giveType(i);
			
			objectLoaderMap.put(Utils.getHashName(oc), loader);
		}
	}
	
	private void loadBackgrounds(Class<? extends BackgroundLoader>[] backgrounds) {

		int len = backgrounds.length;
		backgroundLoaderMap = new HashMap<String, BackgroundLoader>(len, 1.0f);
		
		for(int i = 0; i < len; ++i) {
			
			Class<? extends BackgroundLoader> oc = backgrounds[i];
					
			BackgroundLoader loader = null;
			try {
				loader = (BackgroundLoader)oc.newInstance();
			} catch (Exception ex) {
				ex.printStackTrace();
				System.exit(-1);
			}
			
			loader.giveType(i);
			
			backgroundLoaderMap.put(Utils.getHashName(oc), loader);
		}
	}
	
	protected void loadBlocks(Class<? extends BlockLoader>[] blocks) {
		int len = blocks.length;
		blockMap = new HashMap<String, BlockLoader>(len, 1.0f);
		
		for(int i = 0; i < len; ++i) {
			
			Class<? extends BlockLoader> bc = blocks[i];
			
			try {
				BlockLoader b = (BlockLoader)bc.newInstance();
				b.setType(i);
				blockMap.put(Utils.getHashName(bc), b);
			} catch(Exception ex) {
				ex.printStackTrace();
				System.exit(-1);
			}
		}
	}
	
	private void loadMapReferences(Class<? extends MapReference>[] references) {
		int len = references.length;
		mapReferenceMap = new HashMap<String, MapReference>(len, 1.0f);
		for(int i = 0; i < len; ++i) {
			Class<? extends MapReference> cl = references[i];
			try {
				MapReference instance = ((MapReference)cl.getConstructors()[0].newInstance());
				instance.init(this);
				mapReferenceMap.put(Utils.getHashName(cl), instance);
			} catch(Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	
	private void loadMaps(Class<? extends MapLoader>[] maps) {
		int len = maps.length;
		mapLoaderMap = new HashMap<String, MapLoader>(len, 1.0f);
		for(int i = 0; i < len; ++i) {
			try {
				Class<? extends MapLoader> mc = maps[i];
				MapLoader m = (MapLoader)mc.getConstructors()[0].newInstance();
				m.init(this, i);
				mapLoaderMap.put(Utils.getHashName(mc), m);
			} catch(Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	
	/*
	#################################################################################
	################################# BASE GETTERS ##################################
	#################################################################################
	*/
	
	public String getName() {
		return gameName;
	}

	public Camera getCamera() {
		return camera;
	}
	
	public Window getWindow() {
		return window;
	}
	
	public ALManagement getAudio() {
		return audio;
	}
	
	public float getGameWidth() {
		return gameWidth;
	}
	
	public float getGameHeight() {
		return gameHeight;
	}
	
	public boolean getDebug() {
		return debug;
	}
	
	/*
	#################################################################################
	########################### USER TYPE GETTERS ###################################
	#################################################################################
	*/
	
	public Scene _getScene(String str) {
		return _sceneMap.get(str);
	}
	
	public Sound getSound(String str) {
		return soundMap.get(str);
	}
	
	public Font getFont(String str) {
		return fontMap.get(str);
	}
	
	public ObjectLoader getObjectLoader(String str) {
		return objectLoaderMap.get(str);
	}
	
	public BackgroundLoader getBackgroundLoader(String str) {
		return backgroundLoaderMap.get(str);
	}
	
	public Block getBlock(String str) {
		return blockMap.get(str).create();
	}
	
	public BlockLoader getBlockLoader(String str) {
		return blockMap.get(str);
	}
	
	public MapReference getMapReference(String str) {
		return mapReferenceMap.get(str);
	}
	
	public MapLoader getMapLoader(String str) {
		return mapLoaderMap.get(str);
	}
}