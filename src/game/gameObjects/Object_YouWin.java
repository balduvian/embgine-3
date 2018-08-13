package game.gameObjects;

import embgine.core.components.Component;
import embgine.core.components.TilRenderer;
import embgine.core.loaders.ObjectLoader;
import embgine.graphics.Texture;
import embgine.graphics.shapes.Shape;
import game.master.CrushyMaster;

public class Object_YouWin extends ObjectLoader{

	public Object_YouWin() {
		super(
			128, 
			32,
			false,
			new Component[] {
				new TilRenderer(Shape.RECT, true, new Texture("game/textures/you-win.png", 1)),
			},
			null,
			CrushyMaster.LAYER_GAME
		);
	}

}
