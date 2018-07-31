package game.block;

import embgine.core.loaders.BlockLoader;
import embgine.graphics.Texture;

public class Block_Pipe extends BlockLoader{

	public Block_Pipe() {
		super(true, new Texture("game/textures/block_pipe.png", 4));
	}

}
