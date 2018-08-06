package game.block;

import embgine.core.loaders.BlockLoader;
import embgine.graphics.Texture;
import game.master.MarioMaster;

public class Block_Bush extends BlockLoader{

	public Block_Bush() {
		super(false, new Texture("game/textures/block_bush.png", 3), MarioMaster.LAYER_DECOR, true);
		
		setTile(1, 1, 0, 0, 0, 1, 0, 0, 0);
		
		setTile(2, 1, 0, 0, 0, 0, 0, 0, 0);
	}

}
