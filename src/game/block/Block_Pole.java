package game.block;

import embgine.core.loaders.BlockLoader;
import embgine.graphics.Texture;
import game.master.CrushyMaster;

public class Block_Pole extends BlockLoader{

	public Block_Pole() {
		super(false, new Texture("game/textures/blocks/poles.png", 4, 4), CrushyMaster.LAYER_GAME, true);
		
		//this.setTile(5, 0, 0, 0, 0, 0, 0, 0, 0, 0);
		
		//           x  y   l  lu u  ur r  rd d  dl
		this.setTile(0, 0,  0, 0, 0, 0, 1, 0, 0, 0);
		this.setTile(1, 0,  1, 0, 0, 0, 1, 0, 0, 0);
		this.setTile(2, 0,  1, 0, 0, 0, 0, 0, 0, 0);
		
		this.setTile(0, 1,  0, 0, 0, 0, 0, 0, 0, 0);
		this.setTile(2, 1,  0, 0, 0, 0, 0, 0, 0, 0);
		
		this.setTile(3, 1,  0, 0, 0, 0, 0, 0, 1, 0);
		
		this.setTile(1, 2,  1, 0, 1, 0, 1, 0, 1, 0);
		
		this.setTile(3, 2,  0, 0, 1, 0, 0, 0, 1, 0);
		this.setTile(3, 3,  0, 0, 1, 0, 0, 0, 0, 0);
	}

}
