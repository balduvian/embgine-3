package game.block;

import embgine.core.loaders.BlockLoader;
import embgine.graphics.Texture;
import game.master.CrushyMaster;

public class Block_Door extends BlockLoader{

	public Block_Door() {
		super(false, new Texture("game/textures/blocks/door.png", 2, 2), CrushyMaster.LAYER_GAME, true);
		
		//      x  y   l  lu u  ur r  rd d  dl
		setTile(0, 0,  0, 0, 0, 0, 1, 1, 1, 0);
		setTile(0, 1,  0, 0, 1, 1, 1, 0, 0, 0);
	}

}
