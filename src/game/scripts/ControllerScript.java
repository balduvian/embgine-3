package game.scripts;

import embgine.core.GameObject;
import embgine.core.Scene;
import embgine.core.Script;
import embgine.graphics.Camera;

public class ControllerScript extends Script{
	
	public ControllerScript(GameObject p, Scene s) {
		super(p, s);
	}

	private GameObject player;
	private Camera camera;
	
	public void start(Object... params) {
		player = (GameObject)params[0];
		
		camera = scene.getCamera();
	}

	public void update() {
		
		//camera.getTransform().setPosition(5, 11);
		
		camera.getTransform().setPosition(player.getTransform().getX(), player.getTransform().getY());
		
		//System.out.println(camera.getTransform().getPosition());
	}

}
