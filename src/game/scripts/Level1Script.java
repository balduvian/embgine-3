package game.scripts;

import embgine.core.Index;
import embgine.core.loaders.MapLoader;
import embgine.core.loaders.ObjectLoader;
import embgine.core.scripts.StateScript;

public class Level1Script extends StateScript<GameScript>{

	MapLoader level1;
	
	public void start(Object... params) {
		parent.makePlayer(Index.TILE * 2, Index.TILE * -2);
		
		level1 = scene.getIndex().getMapLoader("Level1");
		
		parent.makeLevelMap(level1);
	}

}