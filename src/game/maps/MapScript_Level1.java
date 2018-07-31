package game.maps;

import embgine.core.GameObject;
import embgine.core.MapScript;
import embgine.core.Scene;

public class MapScript_Level1 extends MapScript{

	public MapScript_Level1(Scene s) {
		super(s);
	}

	public void start() {
		
		GameObject player = scene.createEntity("Player", 3, 10);
		
		scene.createEntity("Controller", 0, 0, player);
		
	}

}
