package game.block;

import embgine.core.loaders.BlockLoader;
import embgine.graphics.Texture;
import game.master.CrushyMaster;

public class Block_Spike extends BlockLoader{

	public Block_Spike() {
		super(true, new Texture("game/textures/blocks/spikes.png", 2, 2), CrushyMaster.LAYER_GAME, false);
	}
	
}
