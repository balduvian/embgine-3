package game.gameObjects;

import embgine.core.Font;
import embgine.core.components.Component;
import embgine.core.components.FonRenderer;
import embgine.core.components.TilRenderer;
import embgine.core.loaders.ObjectLoader;
import embgine.graphics.Texture;
import embgine.graphics.shapes.Shape;
import game.master.CrushyMaster;
import game.scripts.CounterScript;

public class Object_Counter extends ObjectLoader{

	public Object_Counter() {
		super(
			16, 
			16,
			true,
			new Component[] {
				new FonRenderer(Shape.RECT, true, new Font(new Texture("game/textures/text.png", 16, 8), 9, 6)),
			},
			CounterScript.class,
			CrushyMaster.LAYER_GUI
		);
	}

}