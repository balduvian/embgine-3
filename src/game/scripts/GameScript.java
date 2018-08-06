package game.scripts;

import embgine.core.elements.GameObject;
import embgine.core.loaders.ObjectLoader;
import embgine.core.scripts.SceneScript;
import embgine.graphics.Camera;
import embgine.graphics.Transform;

public class GameScript extends SceneScript{

	private GameObject player;
	private Camera camera;
	private float gameHeight;
	private float gameWidth;
	
	private ObjectLoader playerLoader;
	
	public void start(Object... params) { 
		
		playerLoader = parent.getObjectLoader("player");
		camera = parent.getCamera();
		gameHeight = parent.getGameHeight();
		gameWidth = parent.getGameWidth();
		
	}

	public void update() {
		Transform cTrans = camera.getTransform();
		Transform pTrans = player.getTransform();
		
		float dif = pTrans.getX() - cTrans.getX();
				
		float mov = (float)Math.pow(dif, 3);
		
		mov = (Math.abs(mov) > Math.abs(dif)) ? dif : mov;
		
		cTrans.moveX(mov);
		cTrans.setY(player.getTransform().getY());
		
		float bottomLimit = 15f - (gameHeight / 2);
		if(cTrans.getY() > bottomLimit) {
			cTrans.setY(bottomLimit);
		}
		float topLimit = 14f - (gameHeight / 2);
		if(cTrans.getY() < topLimit) {
			cTrans.setY(topLimit);
		}
	}

	public void makePlayer(float x, float y) {
		playerLoader.create(scene, x, y, true);
	}
	
}