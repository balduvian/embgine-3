package game.gameObjects;

import embgine.core.Index;
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
			Index.TILE, 
			Index.TILE,
			false,
			new Component[] {
				new TilRenderer(Shape.RECT, true, new Texture("game/textures/player.png", 3)),
				HitBox.createT(0, 0, Index.TILE/2, Index.TILE, true),
			},
			PlayerScript.class,
			MarioMaster.LAYER_GAME
		);
	}

}
