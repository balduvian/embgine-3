package game.maps;

import embgine.core.Map;
import game.mapReferences.MapReference_World1;

public class Map_Level1 extends Map{

	public Map_Level1() {
		super("game/maps/map_level1.png", "World1", Map.EDGE_REPEAT, null, MapScript_Level1.class);
	}

}
