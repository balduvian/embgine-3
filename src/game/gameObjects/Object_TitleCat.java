package game.gameObjects;

import embgine.core.loaders.ObjectLoader;
import game.master.CrushyMaster;

public class Object_TitleCat extends ObjectLoader{

	public Object_TitleCat() {
		super(
			256, 
			144,
			null,
			CrushyMaster.LAYER_TITLE_CAT
		);
	}

}
