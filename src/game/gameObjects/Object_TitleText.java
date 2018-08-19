package game.gameObjects;

import embgine.core.loaders.ObjectLoader;
import game.master.CrushyMaster;

public class Object_TitleText extends ObjectLoader{

	public Object_TitleText() {
		super(
			128, 
			32,
			null,
			CrushyMaster.LAYER_TITLE_TEXT
		);
	}

}
