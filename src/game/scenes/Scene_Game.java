package game.scenes;

import embgine.core.Scene;
import game.block.Block_Ground;
import game.fonts.Font_Pixel;
import game.gameObjects.Entity_Controller;
import game.maps.Map_Level1;

public class Scene_Game extends Scene{

	@SuppressWarnings("unchecked")
	public Scene_Game() {
		super(
			Map_Level1.class, 
			new String[] {
				"game/sounds/hex.wav",
			}, 
			new Class[] {
				Font_Pixel.class,
			}, 
			new Class[] {
				Entity_Controller.class,
			}, 
			new Class[] {
				Block_Ground.class,
			},
			new Class[] {
				Map_Level1.class,
			} 
		);
	}
	
}
