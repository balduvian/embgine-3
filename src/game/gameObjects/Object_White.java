package game.gameObjects;

import embgine.core.components.ColRenderer;
import embgine.core.components.Component;
import embgine.core.components.TilRenderer;
import embgine.core.loaders.ObjectLoader;
import embgine.graphics.Texture;
import embgine.graphics.shapes.Shape;
import game.master.CrushyMaster;

public class Object_White extends ObjectLoader{

	public Object_White() {
		super(
			256, 
			144,
			false,
			new Component[] {
				new ColRenderer(Shape.RECT, true),
			},
			null,
			CrushyMaster.LAYER_TITLE_BACKGROUND
		);
	}

}
