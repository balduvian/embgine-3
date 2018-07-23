package embgine.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

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
	
	//-------------------index values--------------------//
		private int gameWidth;
		private int gameHeight;
		private String name;
		private int frameRate;
		private int firstScene;
		private boolean debugMode;
		private boolean fullScreen;
	//---------------------------------------------------//
	
	public static void main(String args[]) {
		new Base();
	}
	
	private Base() {
		
		try {
			//make a bufferedReader to read the index.txt at its designated location
			BufferedReader br = new BufferedReader(new InputStreamReader(this.getClass().getClassLoader().getResource("index.txt").openStream()));
		
			//now go through the index.txt and gather neccesary information
			
			char c;//the character being read currently
			int cc;//temporary integer character
			int mode = 0;//0: not reading characters yet, 1: white space in between value, 2:reading the value
			int vMode = 0;//which value to be writing to
			StringBuilder temp = new StringBuilder();//temporary value being read
			
			while((cc = br.read()) != -1) {//set character and go
				c = (char)cc;
				switch(mode){
					case 0:
						if(c == ':') {
							++mode;
						}
					break;
					default:
						if(mode == 1 && c != ' '){
							++mode;
						}
						if(mode == 2){
							if(c == ';') {//when we hit endline, set in the value, then reset
								switch(vMode) {
									case 0:
										gameWidth = Integer.parseInt(temp.toString());
										break;
									case 1:
										gameHeight = Integer.parseInt(temp.toString());
										break;
									case 2:
										name = temp.toString();
										break;
									case 3:
										String test = temp.toString();
										if(test.equals("D")) {
											frameRate = -1;
										}else {
											frameRate = Integer.parseInt(test);
										}
										break;
									case 4:
										firstScene = Integer.parseInt(temp.toString());
										break;
									case 5:
										debugMode = temp.toString().equals("1");
										break;
									case 6:
										fullScreen = temp.toString().equals("1");
										break;
								}
								mode = 0;
								temp.setLength(0);
								++vMode;
							}else {
								temp.append(c);
							}
						}
					break;
				}
			}
			
			//after we load the index.txt, create the graphics stuff
			camera = new Camera(gameWidth, gameHeight);
			
			window = new Window(fullScreen, name, true, frameRate);
			if(frameRate == -1) {
				frameRate = window.getRefreshRate();
			}
			
			audio = new ALManagement();
			
			Shader.init();
				
			index = (Index)this.getClass().getClassLoader().loadClass("game.index.gameIndex").getConstructors()[0].newInstance(gameWidth, gameHeight, name, debugMode, camera, window, audio);
			
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
	
    private Class[] getClasses(String packageName) throws ClassNotFoundException, IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        assert classLoader != null;
        String path = packageName.replace('.', '/');
        File directory = new File(classLoader.getResource(path).getFile());
        ArrayList<Class> classes = new ArrayList<Class>();

        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.getName().endsWith(".class")) {
                classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
            }
        }
        
        return classes.toArray(new Class[classes.size()]);
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
	
	public void switchScene(int i) {
		scene = index.getScene(i);
		scene.start();
	}
	
	private void update() {
		window.update();
		
		if(intro) {
			if(splash.update()) {
				switchScene(firstScene);
				intro = false;
			}
		}else {
			int sc = scene.update();
			if(sc != -1) {
				switchScene(sc);
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
		
		//used to swap buffers here, now it's in the gameloop
	}
	
}