package game.scenes;

import embgine.core.Font;
import embgine.core.Scene;
import embgine.core.loaders.ObjectLoader;
import embgine.graphics.Sound;
import game.block.Block_Ground;
import game.fonts.Font_Pixel;
import game.gameObjects.Entity_Controller;
import game.levels.Map_Level1;
import game.sounds.Sound_Hex;

public class Scene_Game extends Scene{

	@SuppressWarnings("unchecked")
	public Scene_Game() {
		super(
			Map_Level1.class, 
			new Class[] {
				Sound_Hex.class,
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
