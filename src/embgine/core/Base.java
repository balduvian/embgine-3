package embgine.core;

import embgine.graphics.ALManagement;
import embgine.graphics.Camera;
import embgine.graphics.Shader;
import embgine.graphics.Window;

public class Base{

	public static int fps;
	public static double time;
	
	protected Window window;
	protected Camera camera;
	protected ALManagement audio;
	
	private Scene scene;
	private Index index;
	private Splash splash;
	
	private boolean intro;
	
	private int frameRate;
	
	private float gameWidth;
	private float gameHeight;
	private String name;
	private String firstScene;
	private boolean debugMode;
	private boolean fullScreen;
	
	public static void main(String args[]) {
		new Base();
	}
	
	private Base() {
		
		try {
			//make a bufferedReader to read the index.txt at its designated location
			
			@SuppressWarnings("unchecked")
			Class<? extends GameData>[] gameDataClass = (Class<? extends GameData>[])Utils.getClasses("game/gameData"); 
			GameData gd = (GameData)gameDataClass[0].getConstructors()[0].newInstance();
			
			gameWidth = gd.width;
			gameHeight = gd.height;
			name = gd.gameName;
			firstScene = gd.firstScene;
			debugMode = gd.debugMode;
			fullScreen = gd.fullScreen;
			
			//after we load the index.txt, create the graphics stuff
			camera = new Camera(gameWidth, gameHeight);
			
			window = new Window(fullScreen, name, true);
			
			frameRate = window.getRefreshRate();
			
			audio = new ALManagement();
			
			Shader.init();
				
			index = new Index(gameWidth, gameHeight, name, debugMode, camera, window, audio);
			
			splash = new Splash();
			intro = true;
			
			//start the game loop
			gameLoop();
			
		//if shit goes wrong	
		} catch (Exception ex) {
			ex.printStackTrace();
			System.exit(-1);
		}	
	}
	
	public Base(Index in, int fs) {
		
		window = in.getWindow();
		camera = in.getCamera();
		audio = in.getAudio();
		
		index = in;
		
	}
	
	public void gameLoop() {
		long usingFPS = 1000000000 / frameRate;
		long last = System.nanoTime();
		long lastSec = last;
		int frames = 0;
		while(!window.shouldClose()) {
			long now = System.nanoTime();
			if(now-last > usingFPS) {
				time = (now-last)/1000000000d;
				render();
				update();
				window.swap();
				last = now; 
				++frames;
			}
			if(now-lastSec > 1000000000) {
				fps = frames; 
				frames = 0;
				lastSec = now;
				//System.out.println(fps);
			}
		}
		audio.destroy();
	}
	
	public void switchScene(String s) {
		scene = index._getScene(s);
		index.sceneLoad(scene);
	}
	
	private void update() {
		window.update();
		
		if(intro) {
			if(splash.update()) {
				switchScene(firstScene);
				intro = false;
			}
		}else {
			String sceneSwitch = scene.update();
			if(sceneSwitch != null) {
				switchScene(sceneSwitch);
			}
		}
		
		camera.update();
	}
	
	private void render() {
		window.clear();
		
		if(intro) {
			splash.render();
		}else {
			scene.render();
		}
		
	}
	
}