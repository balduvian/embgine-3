package game.scenes;

import embgine.core.Scene;
import game.block.Block_Ground;
import game.fonts.Font_Pixel;
import game.gameObjects.Entity_Controller;
import game.gameObjects.Entity_Player;
import game.mapReferences.MapReference_World1;
import game.maps.Map_Level1;

public class Scene_Game extends Scene{

	@SuppressWarnings("unchecked")
	public Scene_Game() {
		super(
			"Level1",
			new String[] {
				"game/sounds/Hex.wav",
			}, 
			new Class[] {
				Font_Pixel.class,
			}, 
			new Class[] {
				Entity_Controller.class,
				Entity_Player.class,
			}, 
			new Class[] {
				Block_Ground.class,
			},
			new Class[] {
				MapReference_World1.class,
			},
			new Class[] {
				Map_Level1.class,
			} 
		);
	}
	
}
