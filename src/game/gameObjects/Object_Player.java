package game.gameObjects;

import embgine.core.Index;
import embgine.core.components.CliRenderer;
import embgine.core.components.Component;
import embgine.core.components.HitBox;
import embgine.core.components.TilRenderer;
import embgine.core.loaders.ObjectLoader;
import embgine.graphics.Texture;
import embgine.graphics.shapes.Shape;
import game.master.CrushyMaster;
import game.scripts.PlayerScript;

public class Object_Player extends ObjectLoader{

	public Object_Player() {
		super(
			24, 
			24,
			false,
			new Component[] {
				new CliRenderer(Shape.RECT, true, new Texture("game/textures/player.png", 4, 4)),
				HitBox.createB(8, 15, 11, 23, true),
			},
			PlayerScript.class,
			CrushyMaster.LAYER_GAME
		);
	}

}
