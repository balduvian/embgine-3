package game.gameObjects;

import embgine.core.Font;
import embgine.core.components.Component;
import embgine.core.components.FonRenderer;
import embgine.core.components.TilRenderer;
import embgine.core.loaders.ObjectLoader;
import embgine.graphics.Texture;
import embgine.graphics.shapes.Shape;
import game.master.CrushyMaster;

public class Object_RecordPanel extends ObjectLoader{

	public Object_RecordPanel() {
		super(
			256, 
			144,
			false,
			new Component[] {
				new TilRenderer(Shape.RECT, true, new Texture("game/textures/recordPanel.png")),
				new FonRenderer(Shape.RECT, false, new Font(new Texture("game/textures/text.png", 16, 8), 9, 6)),
			},
			null,
			CrushyMaster.LAYER_TITLE_BUTTONS
		);
	}

}
