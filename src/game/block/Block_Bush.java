package game.block;

import embgine.core.loaders.BlockLoader;
import embgine.graphics.Texture;

public class Block_Bush extends BlockLoader{

	public Block_Bush() {
		super(false, new Texture("game/textures/block_bush.png"));
	}

}
