package game.scripts;

import embgine.core.Index;
import embgine.core.loaders.BackgroundLoader;
import embgine.core.loaders.MapLoader;
import embgine.core.scripts.StateScript;

public class LevelWinScript extends StateScript<GameScript>{
	
	public void start(Object... params) {
		
		Index x = scene.getIndex();
		
		parent.giveStates(this.getClass(), LevelGoBackToTitleScript.class);
		
		parent.initLevel(0);
		
		parent.makeBackground();
		
		parent.makeBottom();
		
		parent.makeTop(1, 16);
		
		parent.makePlayer(120, -16);
		
		parent.makeYouWin();
	}

}