package game.gameObjects;

import embgine.core.components.Component;
import embgine.core.components.TilRenderer;
import embgine.core.loaders.ObjectLoader;
import embgine.graphics.Texture;
import embgine.graphics.shapes.Shape;
import game.master.CrushyMaster;

public class Object_TitleCat extends ObjectLoader{

	public Object_TitleCat() {
		super(
			256, 
			144,
			false,
			new Component[] {
				new TilRenderer(Shape.RECT, true, new Texture("game/textures/cat.png", 1)),
			},
			null,
			CrushyMaster.LAYER_TITLE_CAT
		);
	}

}
