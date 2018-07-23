package game.gameObjects;

import embgine.core.loaders.ObjectLoader;
import embgine.core.renderers.TileRenderer;
import embgine.graphics.Texture;
import game.scripts.ControllerScript;
import game.shapes.Shape_Rect;

public class Entity_Controller extends ObjectLoader{
	
	public Entity_Controller() {
	
	}
	
	public Entity_Controller(boolean a) {
		super(
			0, 
			0, 
			true, 
			new Object[][] {{ }},
			ControllerScript.class,
			0
		);
	}

	public int getID() {
		return 34;
	}

}
