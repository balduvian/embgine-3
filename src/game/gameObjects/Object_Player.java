package game.gameObjects;

import embgine.core.loaders.ObjectLoader;
import game.master.CrushyMaster;
import game.scripts.PlayerScript;

public class Object_Player extends ObjectLoader{

	public Object_Player() {
		super(
			24, 
			24,
			PlayerScript.class,
			CrushyMaster.LAYER_GAME
		);
	}

}
