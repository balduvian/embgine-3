package game.backgrounds;

import embgine.core.loaders.BackgroundLoader;
import embgine.graphics.Texture;
import game.master.CrushyMaster;

public class Background_City extends BackgroundLoader{

	public Background_City() {
		super(new Texture("game/textures/background.png", true, true), 256, 128, CrushyMaster.LAYER_BACKGROUND);
	}

}
