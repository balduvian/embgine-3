package game.maps;

import embgine.core.MapScript;
import embgine.core.Scene;

public class MapScript_Level1 extends MapScript{

	public MapScript_Level1(Scene s) {
		super(s);
	}

	public void start() {
		scene.createEntity("Controller", 0, 0);
		scene.createEntity("Player", 3, 14);
	}

}
