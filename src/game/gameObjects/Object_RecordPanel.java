package game.gameObjects;

import embgine.core.loaders.ObjectLoader;
import game.master.CrushyMaster;

public class Object_RecordPanel extends ObjectLoader{

	public Object_RecordPanel() {
		super(
			256, 
			144,
			null,
			CrushyMaster.LAYER_TITLE_BUTTONS
		);
	}

}
