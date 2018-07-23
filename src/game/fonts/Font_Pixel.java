package game.fonts;

import embgine.core.Font;
import embgine.graphics.Texture;

public class Font_Pixel extends Font{

	public Font_Pixel() {
		super(
				new Texture("countdown/resources/textures/text.png"),
				8,
				new int[] {
						9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9,
						9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9,
						6, 2, 4, 9, 9, 9, 9, 2, 3, 3, 6, 6, 3, 5, 2, 5,
						6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 2, 2, 6, 6, 6, 6,
						8, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9,
						9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 3, 5, 3, 4, 9,
						3, 7, 6, 6, 6, 6, 6, 6, 6, 2, 4, 6, 2, 7, 6, 6,
						6, 6, 5, 6, 6, 6, 6, 7, 6, 6, 6, 4, 2, 4, 6, 6,
				}
			);
	}

}
