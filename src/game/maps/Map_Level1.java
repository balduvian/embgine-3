package game.maps;

import embgine.core.Map;

public class Map_Level1 extends Map{

	public Map_Level1() {
		super("game/maps/map_level1.png", new MapRef_World1(), Map.EDGE_REPEAT, null, Script_Level1.class);
	}

}
