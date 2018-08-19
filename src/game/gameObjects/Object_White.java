package game.gameObjects;

import embgine.core.loaders.ObjectLoader;
import game.master.CrushyMaster;

public class Object_White extends ObjectLoader{

	public Object_White() {
		super(
			256, 
			144,
			null,
			CrushyMaster.LAYER_TITLE_BACKGROUND
		);
	}

}
