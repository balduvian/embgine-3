package game.scripts;

import embgine.core.Index;
import embgine.core.loaders.BackgroundLoader;
import embgine.core.loaders.MapLoader;
import embgine.core.scripts.StateScript;

public class Level1Script extends StateScript<GameScript>{

	public void start(Object... params) {
		
		Index x = scene.getIndex();
		
		parent.giveStates(this.getClass(), Level2Script.class);
		
		parent.initLevel(2);
		
		parent.makeBackground();
		
		parent.makeBottom();
		
		parent.makeSection(x.getMapLoader("Left0"), x.getMapLoader("Right0"), 1);
		parent.makeSection(x.getMapLoader("Left1"), x.getMapLoader("Right1"), 2);
		
		parent.makeTop(3);
		
		parent.makePlayer();
	}

}