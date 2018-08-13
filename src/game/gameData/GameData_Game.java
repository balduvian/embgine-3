package game.gameData;

import embgine.core.GameData;
import game.master.CrushyMaster;
import game.scenes.Scene_Game;
import game.scenes.Scene_Title;

public class GameData_Game extends GameData{

	@SuppressWarnings("unchecked")
	public GameData_Game() {
		super(
			CrushyMaster.class,
			16,
			16, 
			9, 
			"Crushy", 
			false, 
			false,
			new Class[] {
				Scene_Title.class,
				Scene_Game.class
			}
		);
	}

}
