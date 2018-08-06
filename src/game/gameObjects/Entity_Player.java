package game.gameObjects;

import embgine.core.loaders.ObjectLoader;
import embgine.core.renderers.TileRenderer;
import embgine.graphics.Texture;
import game.scripts.PlayerScript;

public class Entity_Player extends ObjectLoader{

	public Entity_Player() {
		super(
			1, 
			1, 
			false,
			new Object[][] {{ TileRenderer.class, "Rect", new Texture("game/textures/player.png", 3) }},
			PlayerScript.class,
			2
		);
	}

}
