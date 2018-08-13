//gatcuddy <emmettglaser@gmail.com>

package embgine.core;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;

import org.joml.Matrix4f;

import embgine.core.elements.Background;
import embgine.core.elements.GameObject;
import embgine.core.scripts.Master;
import embgine.graphics.ALManagement;
import embgine.graphics.Camera;
import embgine.graphics.FBO;
import embgine.graphics.Texture;
import embgine.graphics.Window;
import embgine.graphics.infos.Info;
import embgine.graphics.shaders.Shader;
import embgine.graphics.shapes.Shape;

public class Base{

	public static int fps;
	public static double time;
	public static long nanos;
	
	protected Window window;
	protected Camera camera;
	protected ALManagement audio;
	
	private Scene scene;
	private Index index;
	private Splash splash;
	
	private boolean intro;
	
	private int frameRate;
	
	private Master master;
	private int tileSize;
	private float gameWidth;
	private float gameHeight;
	private String name;
	private String firstScene;
	private boolean debugMode;
	private boolean fullScreen;
	
	private FBO frameBuffer;
	
	private float scan;
	private float scanSpeed;
	
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
			
			tileSize = gd.tileSize;
			gameWidth = gd.width * tileSize;
			gameHeight = gd.height * tileSize;
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
			GameObject.init(camera);
			Shader.init();
			Info.init();
			Background.init(camera);
			
			index = new Index(tileSize, gameWidth, gameHeight, name, debugMode, camera, window, audio, gd.sceneList);
			
			splash = new Splash(camera);
			intro = true;
			
			frameBuffer = new FBO(new Texture((int)gameWidth, (int)gameHeight));
			
			master.beginGame();
			
			rescan();
			
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
				nanos = (now-last);
				time = (nanos)/1000000000d;
				update();
				render();
				window.swap();
				last = now; 
				++frames;
			}
			if(now-lastSec > 1000000000) {
				fps = frames; 
				frames = 0;
				lastSec = now;
				System.out.println(fps);
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
			if(splash.update(window)) {
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
		//window.clear();
		
		frameBuffer.prepareForTexture();
		
		glClearColor(1, 0, 1, 1);
		glClear(GL_COLOR_BUFFER_BIT);
		
		if(intro) {
			splash.render();
		}else {
			scene.render();
		}
		
		FBO.prepareDefaultRender(window);
		
		float wRatio = (float)window.getWidth() / window.getHeight();
		
		float gRatio = (float)gameWidth / gameHeight;
		
		glClearColor(0, 0, 1, 1);
		glClear(GL_COLOR_BUFFER_BIT);
		
		Shape rect = Shape.RECT;
		Shader sd = Shader.VHS2DSHADER;
		Shader bl = Shader.COL2DSHADER;
		
		bl.enable(0, 0, 0, 1);
		bl.setMvp(Shape.getNonCameraMatrix(1, 1));
		rect.getVAO().render();
		bl.disable();
		
		frameBuffer.getBoundTexture().bind();
		sd.enable(Utils.random(), scan);
		
		if(wRatio > gRatio) {
			sd.setMvp(Shape.getNonCameraMatrix( gRatio/wRatio , 1 ));
		}else {
			sd.setMvp(Shape.getNonCameraMatrix( 1 , wRatio/gRatio ));
		}
		
		rect.getVAO().render();
		sd.disable();
		
		scan -= time*scanSpeed;
		
		if(scan < -1.125f) {
			rescan();
		}
		
		frameBuffer.getBoundTexture().unbind();
		
	}
	
	private void rescan() {
		scan = 1.125f;
		scanSpeed = Utils.rand(0.5f, 0.75f);
	}
	
}