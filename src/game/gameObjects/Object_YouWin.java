package game.gameObjects;

import embgine.core.loaders.ObjectLoader;
import game.master.CrushyMaster;

public class Object_YouWin extends ObjectLoader{

	public Object_YouWin() {
		super(
			128, 
			32,
			null,
			CrushyMaster.LAYER_GAME
		);
	}

}
