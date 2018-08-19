package game.gameObjects;

import embgine.core.Index;
import embgine.core.loaders.ObjectLoader;
import game.master.MarioMaster;
import game.scripts.PlayerScript;

public class Entity_Player extends ObjectLoader{

	public Entity_Player() {
		super(
			Index.TILE, 
			Index.TILE,
			PlayerScript.class,
			MarioMaster.LAYER_GAME
		);
	}

}
