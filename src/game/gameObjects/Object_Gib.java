package game.gameObjects;

import embgine.core.loaders.ObjectLoader;
import game.master.CrushyMaster;
import game.scripts.GibScript;

public class Object_Gib extends ObjectLoader{

	public Object_Gib() {
		super(
			8, 
			8, 
			GibScript.class, 
			CrushyMaster.LAYER_GAME
		);
	}

}
