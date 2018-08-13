package game.gameObjects;

import embgine.core.components.Component;
import embgine.core.components.TilRenderer;
import embgine.core.loaders.ObjectLoader;
import embgine.graphics.Texture;
import embgine.graphics.shapes.Shape;
import game.master.CrushyMaster;

public class Object_TitleText extends ObjectLoader{

	public Object_TitleText() {
		super(
			128, 
			32,
			false,
			new Component[] {
				new TilRenderer(Shape.RECT, true, new Texture("game/textures/crushy.png", 1)),
			},
			null,
			CrushyMaster.LAYER_TITLE_TEXT
		);
	}

}
