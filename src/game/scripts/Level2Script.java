package game.scripts;

import embgine.core.Index;
import embgine.core.loaders.BackgroundLoader;
import embgine.core.loaders.MapLoader;
import embgine.core.scripts.StateScript;

public class Level2Script extends StateScript<GameScript>{

	public void start(Object... params) {
		
		Index x = scene.getIndex();
		
		parent.giveStates(this.getClass(), Level3Script.class);
		
		parent.initLevel(2);
		
		parent.makeBackground();
		
		parent.makeBottom();
		
		parent.makeSection(x.getMapLoader("Left2"), x.getMapLoader("Right2"), 1);
		parent.makeSection(x.getMapLoader("Left3"), x.getMapLoader("Right3"), 2);
		
		parent.makeTop(3);
		
		parent.makePlayer();
	}

}