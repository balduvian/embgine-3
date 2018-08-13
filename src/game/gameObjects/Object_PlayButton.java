package game.gameObjects;

import embgine.core.components.Component;
import embgine.core.components.SelRenderer;
import embgine.core.components.TilRenderer;
import embgine.core.loaders.ObjectLoader;
import embgine.core.scripts.ObjectScript;
import embgine.graphics.Texture;
import embgine.graphics.shapes.Shape;
import game.master.CrushyMaster;

public class Object_PlayButton extends ObjectLoader{

	public Object_PlayButton() {
		super(
			64, 
			32, 
			false, 
			new Component[] {
					new SelRenderer(Shape.RECT, true, new Texture("game/textures/play.png", true, true))
			}, 
			null, 
			CrushyMaster.LAYER_TITLE_BUTTONS
		);
	}

}
