package game.gameData;

import embgine.core.GameData;
import game.master.MarioMaster;

public class GameData_MarioDemo extends GameData{

	public GameData_MarioDemo() {
		super(
			MarioMaster.class,
			26.66666667f, 
			15, 
			"Mario Demo", 
			"Game", 
			true, 
			false
		);
	}

}
