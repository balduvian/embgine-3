package embgine.core;

import embgine.core.elements.Map;
import embgine.core.scripts.Master;
import embgine.graphics.ALManagement;
import embgine.graphics.Camera;
import embgine.graphics.Window;
import embgine.graphics.shaders.Shader;
import embgine.graphics.shapes.Shape;

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
	
	private Master master;
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
			Class<?> gameDataClass = this.getClass().getClassLoader().loadClass("game.gameData.GameData_Game"); 
			GameData gd = (GameData)gameDataClass.newInstance();
			
			try {
				master = gd.master.newInstance();
			} catch(Exception ex) {
				ex.printStackTrace();
				System.exit(-1);
			}
			
			gameWidth = gd.width;
			gameHeight = gd.height;
			name = gd.gameName;
			firstScene = Utils.getHashName(gd.sceneList[0]);
			debugMode = gd.debugMode;
			fullScreen = gd.fullScreen;
			
			//after we load the index.txt, create the graphics stuff
			camera = new Camera(gameWidth, gameHeight);
			
			window = new Window(fullScreen, name, true);
			
			frameRate = window.getRefreshRate();
			
			audio = new ALManagement();
			
			//static inits b4 index
			Shape.init(camera);
			Shader.init();
			
			index = new Index(gameWidth, gameHeight, name, debugMode, camera, window, audio, gd.sceneList);
			
			splash = new Splash();
			intro = true;
			
			master.beginGame();
			
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