package game.fonts;

import embgine.core.Font;
import embgine.graphics.Texture;

public class Font_Pixel extends Font{

	public Font_Pixel() {
		super(
				new Texture("game/textures/text.png"),
				9,
				6
			);
	}

}
