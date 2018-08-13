package game.scripts;
import embgine.core.Base;
import embgine.core.Utils;
import embgine.core.elements.GameObject;
import embgine.core.elements.Map;
import embgine.core.loaders.BackgroundLoader;
import embgine.core.loaders.MapLoader;
import embgine.core.loaders.ObjectLoader;
import embgine.core.scripts.SceneScript;
import embgine.core.scripts.StateScript;
import embgine.graphics.Camera;
import embgine.graphics.Transform;
import embgine.graphics.Window;
import game.master.CrushyMaster;

public class GameScript extends SceneScript{
	
	private ObjectLoader playerLoader;
	private ObjectLoader gibLoader;
	
	private MapLoader bottomLoader;
	private MapLoader topLoader;
	private BackgroundLoader backgroundLoader;
	
	private GameObject player;
	private Camera camera;
	private Window window;
	
	private Map[][] levelField;
	private int mapStack;
	
	private int gibCount;
	
	private Class<? extends StateScript<? extends SceneScript>> currentState;
	private Class<? extends StateScript<? extends SceneScript>> nextState;
	
	private float scrollY;
	
	private int endX;
	private int endY;
	private boolean ended;
	private boolean scrolling;
	private boolean scrollLock;
	
	private int upTo;
	
	public void start(Object... params) { 
		
		playerLoader = parent.getObjectLoader("Player");
		gibLoader = parent.getObjectLoader("Gib");
		
		bottomLoader = parent.getMapLoader("Bottom");
		topLoader = parent.getMapLoader("Top");
		
		backgroundLoader = parent.getBackgroundLoader("City");
		
		camera = parent.getCamera();
		
		gibCount = -1;
		
		ended = false;
		
		scrollY = 0;
		
		scrolling = false;
		
		scrollLock = false;
		
		window = parent.getWindow();
		
		scene.createObject(parent.getObjectLoader("Counter"), 0, 0, true);
	}

	public void preUpdate() {
		for(int i = 0; i < mapStack && i < upTo; ++i) {
			Transform lt = levelField[i][0].getTransform();
			if(lt.getX() < 0) {
				levelField[i][0].setSpeed(16);
			}else {
				levelField[i][0].setSpeed(0);
			}
			
			Transform rt = levelField[i][1].getTransform();
			if(rt.getX() > 0) {
				levelField[i][1].setSpeed(-16);
			}else {
				levelField[i][1].setSpeed(0);
			}
		}
	}
	
	public void update() {
		Transform pTrans = player.getTransform();
		
		int cx = 0;
		int cy = 0;
		
		cx = pTrans.getX();
		
		cy = pTrans.getY();

		if(!scrollLock) {
			if(scrollY > cy + 16) {
				scrollY = cy + 16;
				scrolling = true;
			}else if(gibCount == -1 && cy > scrollY + camera.getTransform().getHeight()/2){
				die();
				scene.sound("crush.wav", 0.25f, false);
			}
		}
		
		if(scrollY < endY -3 * 16) {
			scrollY = endY - 3 * 16;
		}
		
		scene.centerCamera(cx, (scrollLock) ? cy : (int)scrollY);
		
		upTo = (cy - 128) / (-18* 16) + 1;
		if(scrolling) {
			scrollY -= (16 * Base.time);
		}
		
		Transform t = player.getTransform();
		//System.out.println(t.getX() + " " + endX + " | " + t.getY() + " " + endY);
		if((!ended) && (t.getX() >= endX) && (t.getY() <= endY)) {
			win();
		}
		
		if(ended && t.getX() > endX + 32) {
			scene.start(nextState);
		}
		
		if(gibCount == 0) {
			scene.start(currentState);
		}

		//ESCAPE
		if(window.keyPressed(256)) {
			scene.switchScene("Title");
		}
		
		if(!ended) {
			CrushyMaster.TIMER += Base.nanos;
		}
	}

	public void giveStates(Class<? extends StateScript<? extends SceneScript>> c, Class<? extends StateScript<? extends SceneScript>> n) {
		currentState = c;
		nextState = n;
	}
	
	public void initLevel(int ms) {
		mapStack = ms;
		levelField = new Map[ms][2];
	}
	
	public void makeBackground() {
		scene.createBackground(backgroundLoader, 0, -96, true, 0.5f);
	}
	
	public void makePlayer() {
		player = scene.createObject(playerLoader, 120, -8, true);
	}
	
	public void makePlayer(int offX, int offY) {
		player = scene.createObject(playerLoader, offX, offY, true);
	}
	
	public void makeYouWin() {
		scene.createObject(parent.getObjectLoader("YouWin"), 64, 32, true);
		scrollLock = true;
	}
	
	public void makeBottom() {
		scene.createMap(bottomLoader, 0, 1 * 16, true);
	}
	
	public void makeTop(int high) {
		scene.createMap(topLoader, 0, (-high * 16 * 17 ) + (8 * 16), true);
		endX = 14 * 16;
		endY = (-high * 16 * 17 ) + (15 * 16);
	}
	
	public void makeTop(int high, int off) {
		scene.createMap(topLoader, 0, (-high * 16 * 18 ) + (7 * 16) + off + 32, true);
		endX = 14 * 16;
		endY = (-high * 16 * 17 ) + (15 * 16) + off + 32;
	}
	
	public void makeSection(MapLoader left, MapLoader right, int high) {
		levelField[high - 1][0] = scene.createMap(left, -16 * 16, -high * 16 * 18 + 16, true);
		levelField[high - 1][1] = scene.createMap(right, 16 * 16, -high * 16 * 18 + 16, true);
	}
	
	public void die() {
		Transform t = player.getTransform();
		int x = t.getX() + 6;
		int y = t.getY() + 12;
		gibCount = 10;
		for(int g = 0; g < gibCount; ++g) {
			scene.createObject(gibLoader, x, y, true);
		}
		player.setEnabled(false);
		scene.destroyObject(player);
	}
	
	public void win() {
		((PlayerScript)player.getScript()).setWin(true, endY);
		ended = true;
	}
	
	public void reduceGib() {
		--gibCount;
	}
}