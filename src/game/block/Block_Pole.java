package game.block;

import embgine.core.loaders.BlockLoader;
import embgine.graphics.Texture;

public class Block_Pole extends BlockLoader{

	public Block_Pole() {
		super(false, new Texture("game/textures/block_pole.png", 2), false);
	}

}
