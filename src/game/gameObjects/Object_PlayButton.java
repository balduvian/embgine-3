package game.gameObjects;

import embgine.core.loaders.ObjectLoader;
import game.master.CrushyMaster;

public class Object_PlayButton extends ObjectLoader{

	public Object_PlayButton() {
		super(
			64, 
			32, 
			null, 
			CrushyMaster.LAYER_TITLE_BUTTONS
		);
	}

}
