package game.gameObjects;

import embgine.core.loaders.ObjectLoader;
import embgine.core.renderers.TileRenderer;
import embgine.graphics.Texture;
import game.scripts.PlayerScript;
import game.shapes.Shape_Rect;

public class Entity_Player extends ObjectLoader{
	
	public Entity_Player() {
		
	}
	
	public Entity_Player(boolean a) {
		super(
				0, 
				0, 
				true, 
				new Object[][] {{ TileRenderer.class, Shape_Rect.class, new Texture("test.png") }},
				PlayerScript.class,
				0
		);
	}
	
	@Override
	public int getID() {
		return 13;
	}

	@Override
	public void setup() {
		width = 0;
		height = 0;
		gui = true;
		rTemplates = new Object[][] {{ TileRenderer.class, Shape_Rect.class, new Texture("test.png") }};
	}

}
