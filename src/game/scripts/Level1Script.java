package game.scripts;

import embgine.core.loaders.MapLoader;
import embgine.core.loaders.ObjectLoader;
import embgine.core.scripts.StateScript;

public class Level1Script extends StateScript<GameScript>{

	MapLoader level1;
	
	public void start(Object... params) {
		parent.makePlayer(3, 10);
		
		level1 = scene.getIndex().getMapLoader("Level1");
				
		scene.createMap(level1, 0, 0, true);
	}

}