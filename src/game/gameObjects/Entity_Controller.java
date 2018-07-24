package game.gameObjects;

import embgine.core.loaders.ObjectLoader;
import game.scripts.ControllerScript;

public class Entity_Controller extends ObjectLoader{

	public Entity_Controller() {
		super(0, 0, true, new Object[][] {{}}, ControllerScript.class, 0);
	}
	
}
