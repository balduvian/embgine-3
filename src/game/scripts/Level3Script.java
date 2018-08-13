package game.scripts;

import embgine.core.Index;
import embgine.core.loaders.BackgroundLoader;
import embgine.core.loaders.MapLoader;
import embgine.core.scripts.StateScript;

public class Level3Script extends StateScript<GameScript>{

	public void start(Object... params) {
		
		Index x = scene.getIndex();
		
		parent.giveStates(this.getClass(), Level4Script.class);
		
		parent.initLevel(3);
		
		parent.makeBackground();
		
		parent.makeBottom();
		
		parent.makeSection(x.getMapLoader("Left4"), x.getMapLoader("Right4"), 1);
		parent.makeSection(x.getMapLoader("Left5"), x.getMapLoader("Right5"), 2);
		parent.makeSection(x.getMapLoader("Left6"), x.getMapLoader("Right6"), 3);
		
		parent.makeTop(4);
		
		parent.makePlayer();
	}

}