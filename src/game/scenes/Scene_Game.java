package game.scenes;

import embgine.core.Scene;
import game.block.Block_Bricks;
import game.block.Block_Bush;
import game.block.Block_Ground;
import game.block.Block_Pipe;
import game.block.Block_Pole;
import game.block.Block_Question;
import game.block.Block_X;
import game.fonts.Font_Pixel;
import game.gameObjects.Entity_Player;
import game.mapReferences.MapReference_World1;
import game.maps.Map_Level1;
import game.scripts.GameScript;
import game.scripts.Level1Script;

public class Scene_Game extends Scene{

	@SuppressWarnings("unchecked")
	public Scene_Game() {
		super(
			GameScript.class,
			Level1Script.class,
			4,
			16,
			new String[] {
				"game/sounds/Hex.wav",
			}, 
			new Class[] {
				Font_Pixel.class,
			}, 
			new Class[] {
				Entity_Player.class,
			}, 
			new Class[] {
				Block_Bricks.class,
				Block_Ground.class,
				Block_Bush.class,
				Block_Ground.class,
				Block_Pipe.class,
				Block_Pole.class,
				Block_Question.class,
				Block_X.class
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
