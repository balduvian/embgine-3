package game.scripts;
import embgine.core.Index;
import embgine.core.elements.GameObject;
import embgine.core.elements.Map;
import embgine.core.loaders.MapLoader;
import embgine.core.loaders.ObjectLoader;
import embgine.core.scripts.SceneScript;
import embgine.graphics.Camera;
import embgine.graphics.Transform;

public class GameScript extends SceneScript{

	private Map map;
	private GameObject player;
	private Camera camera;
	
	private float gameHeight;
	private float gameWidth;
	
	private ObjectLoader playerLoader;
	
	public void start(Object... params) { 
		
		playerLoader = parent.getObjectLoader("Player");
		camera = parent.getCamera();
		gameHeight = parent.getGameHeight();
		gameWidth = parent.getGameWidth();
		
	}

	public void update() {
		Transform cTrans = camera.getTransform();
		Transform pTrans = player.getTransform();
		
		float dif = pTrans.getX() - cTrans.getX();
				
		float mov = 0;
		
		if(dif < -16) {
			cTrans.setX(Math.round(pTrans.getX()+16));
		}else if(dif < -8) {
			//cTrans.moveX(1);
		}else if(dif < 8) {
			
		}else if(dif < 16) {
			//cTrans.moveX(-1);
		}else {
			cTrans.setX(Math.round(pTrans.getX()-16));
		}
		
		//cTrans.setX(pTrans.getX());
		cTrans.setY(0);
	}

	public void makePlayer(float x, float y) {
		player = scene.createObject(playerLoader, x, y, true);
	}
	
	public void makeLevelMap(MapLoader ml) {
		map = scene.createMap(ml, 0, -8 * Index.TILE, true);
		((PlayerScript) player.getScript()).setMap(map);
	}
	
}