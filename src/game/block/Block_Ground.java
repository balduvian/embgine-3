package game.block;

import embgine.core.loaders.BlockLoader;
import embgine.graphics.Texture;
import game.master.MarioMaster;

public class Block_Ground extends BlockLoader{

	public Block_Ground() {
		super(true, new Texture("game/textures/block_ground.png", 9), MarioMaster.LAYER_GAME, false);
	}

}
