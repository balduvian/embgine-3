package game.block;

import embgine.core.loaders.BlockLoader;
import embgine.graphics.Texture;
import game.master.CrushyMaster;

public class Block_Slab extends BlockLoader{

	public Block_Slab() {
		super(true, new Texture("game/textures/blocks/slabs.png", 2, 2), CrushyMaster.LAYER_GAME, false);
	}
	
	//asdfasdfpoiuffffhdjdjdyou

}
