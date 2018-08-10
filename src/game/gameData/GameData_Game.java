package game.gameData;

import embgine.core.GameData;
import game.master.MarioMaster;
import game.scenes.Scene_Game;

public class GameData_Game extends GameData{

	@SuppressWarnings("unchecked")
	public GameData_Game() {
		super(
			MarioMaster.class,
			16,
			26.66666667f, 
			15, 
			"Mario Demo", 
			true, 
			false,
			new Class[] {
				Scene_Game.class
			}
		);
	}

}
