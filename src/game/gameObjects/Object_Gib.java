package game.gameObjects;

import embgine.core.components.Component;
import embgine.core.components.TilRenderer;
import embgine.core.loaders.ObjectLoader;
import embgine.core.scripts.ObjectScript;
import embgine.graphics.Texture;
import embgine.graphics.shapes.Shape;
import game.master.CrushyMaster;
import game.scripts.GibScript;

public class Object_Gib extends ObjectLoader{

	public Object_Gib() {
		super(
			8, 
			8, 
			false, 
			new Component[] {
				new TilRenderer(Shape.RECT, true, new Texture("game/textures/gibs.png", 2, 2))
			}, 
			GibScript.class, 
			CrushyMaster.LAYER_GAME
		);
	}

}
