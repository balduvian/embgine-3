package game.gameObjects;

import embgine.core.loaders.ObjectLoader;
import embgine.core.renderers.TileRenderer;
import embgine.graphics.Texture;
import game.scripts.PlayerScript;
import game.shapes.Shape_Rect;

public class Entity_Player extends ObjectLoader{

	public Entity_Player() {
		super(
			0, 
			0, 
			true,
			new Object[][] {{ TileRenderer.class, Shape_Rect.class, new Texture("test.png") }},
			PlayerScript.class,
			0
		);
	}

}
