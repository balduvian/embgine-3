package game.block;

import embgine.core.loaders.BlockLoader;
import embgine.graphics.Texture;
import game.master.MarioMaster;

public class Block_X extends BlockLoader{

	public Block_X() {
		super(true, new Texture("game/textures/block_x.png"), MarioMaster.LAYER_GAME, false);
	}

}
