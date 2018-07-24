package embgine.core;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;

import embgine.core.loaders.MapLoader;
import embgine.core.loaders.ObjectLoader;
import embgine.core.loaders.ShapeLoader;
import embgine.core.loaders.SoundLoader;
import embgine.graphics.ALManagement;
import embgine.graphics.Camera;
import embgine.graphics.Shape;
import embgine.graphics.Sound;
import embgine.graphics.Window;
import game.gameObjects.Entity_Controller;
import game.gameObjects.Entity_Player;

public class Index {
	
	public static final int BYTE = 256;
	
	public static final int RENDERER_COLOR_RENDERER = 0;
	public static final int RENDERER_TILE_RENDERER = 1;
	public static final int RENDERER_FONT_RENDERER = 2;
	
	private Window window;
	private Camera camera;
	private ALManagement audio;
	
	private HashMap<String, Scene       >  _sceneMap;
	private HashMap<String, Shape       >  _shapeMap;
	private HashMap<String, Global<?>   > _globalMap;
	private HashMap<String, Sound       >   soundMap;
	private HashMap<String, Font        >    fontMap;
	private HashMap<String, ObjectLoader>  objectMap;
	private HashMap<String, Block       >   blockMap;
	private HashMap<String, Map         >     mapMap;
	
	private float gameWidth;
	private float gameHeight;
	private String gameName;
	private boolean debug;
	
	private Scene scene;
	
	public Index(float gw, float gh, String n, boolean db, Camera c, Window w, ALManagement a) {
		gameWidth = gw;
		gameHeight = gh;
		gameName = n;
		debug = db;
		camera = c;
		window = w;
		audio = a;
		
		Scene.giveIndex(this);
		
		loadScenes();
		loadShapes();
		loadGlobals();
	}
	
	public void sceneLoad(Scene sc) {
		loadSounds(sc.getSounds());
		loadFonts(sc.getFonts());
		loadGameObjects(sc.getObjects());
		loadBlocks(sc.getBlocks());
		loadMaps(sc.getMaps());
	}
	
	@SuppressWarnings("unchecked")
	private <type> void baseLoad(String classPath, HashMap<String, type> map) {
		Class<? extends type>[] classes = (Class<? extends type>[])getClasses(classPath);
		int len = classes.length;
		map = new HashMap<String, type>(len, 1.0f);
		for(int i = 0; i < len; ++i) {
			Class<? extends type> cl = classes[i];
			try {
				type instance = (type)cl.getConstructors()[0].newInstance();
				map.put(getHashName(cl), instance);
			} catch(Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	
	//initial loads
	private void loadScenes() {
		baseLoad("game/scenes", _sceneMap);
	}

	protected void loadShapes() {
		baseLoad("game/shapes", _shapeMap);
	}
	
	private void loadGlobals() {
		baseLoad("game/globals", _globalMap);
	}
	
	//scene specific loads
	private void loadSounds(String[] soundPaths) {
		int len = soundPaths.length;
		soundMap = new HashMap<String, Sound>();
		for(int i = 0; i < len; ++i) {
			String name = soundPaths[i];
			
			Sound sound = new Sound(name);
			
			StringBuilder build = new StringBuilder(32);
			int nameLen = name.length();
			boolean found = false;
			for(int j = 0; j < nameLen; ++j) {
				char c = name.charAt(j);
				if(found) {
					build.append(c);
				}else {
					found = (c == '/');
				}
			}
			
			soundMap.put(build.toString(), sound);
		}
	}
	
	private void loadFonts(Class<? extends Font>[] fonts) {
		int len = fonts.length;
		fontList = new Font[fonts.length];
		for(int i = 0; i < len; ++i) {
			try {
				fontList[i] = (Font)fonts[i].getConstructors()[0].newInstance();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	
	private void loadGameObjects(Class<? extends ObjectLoader>[] objects) {
		new Entity_Player();
		int len = objects.length;
		objectList = new ObjectLoader[len];
		for(int i = 0; i < len; ++i) {
			try {
				objectList[i] = (ObjectLoader)objects[i].getConstructors()[0].newInstance();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			Object[][] templates = objectList[i].getTemplates();
			int len2 = templates.length;
			Renderer[] rList = new Renderer[len2];
			for(int j = 0; j < len2; ++j) {
				Object[] template = templates[j];
				
				@SuppressWarnings("unchecked")
				Class<Shape> shapeC = (Class<Shape>)template[1];
				for(Shape sp : shapeList) {
					if(sp.getClass() == shapeC) {
						template[1] = sp; //replace the shape class in templates with the shape instance we have in index
					}
				}
				
				if(template.length > 2 && ((Class)template[2]) == Font.class) {
					@SuppressWarnings("unchecked")
					Class<Font> fontC = (Class<Font>)template[2];
					for(Font fn : fontList) {
						if(fn.getClass() == fontC) {
							template[2] = fn; //replace the font class in templates with the font instance we have in index
						}
					}
				}
				
				try {
					rList[j] = ((Class<Renderer>)templates[j][0]).getDeclaredConstructor(Object[].class).newInstance(templates[j]);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			objectList[i].giveRenderers(rList);
		}
	}
	
	protected void loadBlocks(Class<? extends Block>[] blocks) {
		int len = blocks.length;
		blockList = new Block[len];
		for(int i = 0; i < len; ++i) {
			try {
				blockList[i] = (Block)blocks[i].getConstructors()[0].newInstance();
			}
			catch(Exception ex) {}
		}
	}
	
	private void loadMaps(Class<? extends Map>[] maps) {
		int len = maps.length;
		mapList = new Map[len];
		for(int i = 0; i < len; ++i) {
			try {
				MapLoader m = (MapLoader)maps[i].getConstructors()[0].newInstance();
				registerMap(m.getID());
				mapList[i] = m.create();
			}
			catch(Exception ex) {}
		}
	}
	
	public String name() {
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
	################################# TOOLS  ########################################
	#################################################################################
	*/
	
	private Class[] getClasses(String packageName){
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        assert classLoader != null;
       // String path = packageName.replace('.', '/');
        File directory = new File(classLoader.getResource(packageName).getFile());
        ArrayList<Class> classes = new ArrayList<Class>();

        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.getName().endsWith(".class")) {
                try {
					classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
				} catch (ClassNotFoundException ex) {
					ex.printStackTrace();
				}
            }
        }
        
        return classes.toArray(new Class[classes.size()]);
    }
	
	private String getHashName(Class<?> cl) {
		String nam = cl.getSimpleName();
		StringBuilder build = new StringBuilder(32);
		int len = nam.length();
		boolean found = false;
		for(int i = 0; i < len; ++i) {
			char c = nam.charAt(i);
			if(found) {
				build.append(c);
			}else {
				found = (c == '_');
			}
		}
		return build.toString();
	}
	
	/*
	#################################################################################
	################################# GETTERS #######################################
	#################################################################################
	*/
	
	public Shape getShape(String str) {
		return _shapeMap.get(str);
	}
	
	public Sound getSound(String str) {
		return soundMap.get(str);
	}
	
	public GameObject getObject(Scene sc, String str) {
		return objectMap.get(str).create(sc);
	}
	
	public Map getMap(String str) {
		return mapMap.get(str);
	}
}
