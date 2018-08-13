package game.scripts;

import embgine.core.Index;
import embgine.core.loaders.BackgroundLoader;
import embgine.core.loaders.MapLoader;
import embgine.core.scripts.StateScript;
import game.master.CrushyMaster;

public class LevelGoBackToTitleScript extends StateScript<GameScript>{
	
	public void start(Object... params) {
		
		CrushyMaster.compareTimes();
		scene.switchScene("Title");
	}

}