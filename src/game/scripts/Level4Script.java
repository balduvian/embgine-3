package game.scripts;

import embgine.core.Index;
import embgine.core.loaders.BackgroundLoader;
import embgine.core.loaders.MapLoader;
import embgine.core.scripts.StateScript;

public class Level4Script extends StateScript<GameScript>{

	public void start(Object... params) {
		
		Index x = scene.getIndex();
		
		parent.giveStates(this.getClass(), LevelWinScript.class);
		
		parent.initLevel(3);
		
		parent.makeBackground();
		
		parent.makeBottom();
		
		parent.makeSection(x.getMapLoader("Left7"), x.getMapLoader("Right7"), 1);
		parent.makeSection(x.getMapLoader("Left8"), x.getMapLoader("Right8"), 2);
		parent.makeSection(x.getMapLoader("Left9"), x.getMapLoader("Right9"), 3);
		
		parent.makeTop(4);
		
		parent.makePlayer();
	}

}