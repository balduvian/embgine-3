package game.gameObjects;

import embgine.core.loaders.ObjectLoader;
import game.master.CrushyMaster;
import game.scripts.CounterScript;

public class Object_Counter extends ObjectLoader{

	public Object_Counter() {
		super(
			16, 
			16,
			CounterScript.class,
			CrushyMaster.LAYER_GUI
		);
	}

}