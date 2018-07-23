package game.levels;

import embgine.core.Map;
import embgine.core.Scene;
import embgine.core.loaders.MapLoader;

public class Map_Level1 extends MapLoader{

	public Map_Level1() {
		super("game/maps/map_level1.png", new MapRef_World1(), Map.EDGE_REPEAT, null, Script_Level1.class);
	}

	@Override
	public int getID() {
		return 11;
	}

}
