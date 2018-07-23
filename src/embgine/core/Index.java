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
	
	private int[]      mapLoaders;
	private int[]    shapeLoaders;
	private int[]    soundLoaders;
	private int[]   objectLoaders;
	private int[]    levelLoaders;
	
	private int          mapCount;
	private int        shapeCount;
	private int        soundCount;
	private int       objectCount;
	private int        levelCount;
	
	private HashMap<String, Shape> shapeMap;
	
	private        Shape[]    shapeList;
	private          Map[]      mapList;
	private ObjectLoader[]   objectList;
	private        Scene[]    sceneList;
	private     Variable[] variableList;
	private         Font[]     fontList;
	private        Sound[]    soundList;
	private        Block[]    blockList;
	
	private float gameWidth;
	private float gameHeight;
	private String gameName;
	private boolean debug;
	
	private Scene scene;
	
	public void  registerMap(int id) {
		mapLoaders[id] = mapCount;
		++mapCount;
	}
	
	public void  registerShape(int id) {
		shapeLoaders[id] = shapeCount;
		++shapeCount;
	}
	
	public void  registerSound(int id) {
		soundLoaders[id] = soundCount;
		++soundCount;
	}
	
	public void registerObject(int id) {
		objectLoaders[id] = objectCount;
		++objectCount;
	}
	
	public void  registerLevel(int id) {
		levelLoaders[id] = levelCount;
		++levelCount;
	}
	
	public Index(float gw, float gh, String n, boolean db, Camera c, Window w, ALManagement a) {
		gameWidth = gw;
		gameHeight = gh;
		gameName = n;
		debug = db;
		camera = c;
		window = w;
		audio = a;
		
		shapeCount = 0;
		mapLoaders    = new int[BYTE];
		shapeLoaders  = new int[BYTE];
		soundLoaders  = new int[BYTE];
		objectLoaders = new int[BYTE];
		levelLoaders  = new int[BYTE];
		
		loadScenes();
		loadShapes();
	}
	
	public void sceneLoad(Scene sc) {
		mapCount    = 0;
		soundCount  = 0;
		objectCount = 0;
		levelCount  = 0;
		loadSounds(sc.getSounds());
		loadFonts(sc.getFonts());
		loadGameObjects(sc.getObjects());
		loadBlocks(sc.getBlocks());
		loadMaps(sc.getMaps());
	}
	
	//initial loads
	private void loadScenes() {
		
	}

	protected void loadShapes() {
		Class<? extends ShapeLoader>[] loaders = (Class<? extends ShapeLoader>[])getClasses("game/shapes");
		int len = loaders.length;
		shapeList = new Shape[len];
		for(int i = 0; i < len; ++i) {
			try {
				Shape sp = ((ShapeLoader)loaders[i].getConstructors()[0].newInstance()).create(camera);
				shapeMap.put(loaders[i].getSimpleName(), sp);
				shapeList[i] = sp;
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	
	//scene specific loads
	private void loadSounds(Class<? extends SoundLoader>[] sounds) {
		int len = sounds.length;
		soundList = new Sound[len];
		for(int i = 0; i < len; ++i) {
			SoundLoader sl = (SoundLoader)sounds[i].getClass().getConstructors()[0].newInstance();
			GameObject s = objectList[new Entity_Controller(false).getID()].create();
			loaderCounter.registerSound(sl.getID());
			soundList[i] = sl.create();
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
	
	private void loadMaps(Class<? extends MapLoader>[] maps) {
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
	
	//---------------------------------------------------------------------------------//
	
	public Shape getShape(String s) {
		return shapeMap.get(s);
	}
	
	public GameObject getObject(Class<? extends ObjectLoader> ol, Object... objects) {
		try {
			return objectList[objectLoaders[ol.getConstructor(boolean.class).newInstance(true).getID()]].create(scene);
		}catch(Exception ex) {
			return null;
		}
	}
	
	public Map getMap(Class<? extends MapLoader> ml) {
		try {
			return mapList[mapLoaders[ml.getConstructor(boolean.class).newInstance(true).getID()]];
		}catch(Exception ex) {
			return null;
		}
	}
}
