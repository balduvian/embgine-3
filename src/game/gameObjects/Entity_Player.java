package game.gameObjects;

import embgine.core.components.Component;
import embgine.core.components.HitBox;
import embgine.core.components.TilRenderer;
import embgine.core.loaders.ObjectLoader;
import embgine.graphics.Texture;
import embgine.graphics.shapes.Shape;
import game.master.MarioMaster;
import game.scripts.PlayerScript;

public class Entity_Player extends ObjectLoader{

	public Entity_Player() {
		super(
			1, 
			1, 
			false,
			new Component[] {
				new TilRenderer(Shape.RECT, true, new Texture("game/textures/player.png", 3)),
				HitBox.createT(0, 0, 0.5f, 1f),
			},
			PlayerScript.class,
			MarioMaster.LAYER_GAME
		);
	}

}
