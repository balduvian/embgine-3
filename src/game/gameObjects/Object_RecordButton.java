package game.gameObjects;

import embgine.core.loaders.ObjectLoader;
import game.master.CrushyMaster;

public class Object_RecordButton extends ObjectLoader{

	public Object_RecordButton() {
		super(
			96, 
			32, 
			null, 
			CrushyMaster.LAYER_TITLE_BUTTONS
		);
	}

}
