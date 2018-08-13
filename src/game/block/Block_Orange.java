package game.block;

import embgine.core.loaders.BlockLoader;
import embgine.graphics.Texture;
import game.master.CrushyMaster;

public class Block_Orange extends BlockLoader{

	public Block_Orange() {
		super(true, new Texture("game/textures/blocks/orange.png", 8, 8), CrushyMaster.LAYER_GAME, true);
		
		this.setTile(0, 0,  0, 0, 0, 0, 0, 0, 0, 0);
		this.setTile(1, 0,  0, 0, 0, 0, 1, 1, 1, 0);
		this.setTile(2, 0,  1, 0, 0, 0, 1, 1, 1, 1);
		this.setTile(3, 0,  1, 0, 0, 0, 0, 0, 1, 1);
		this.setTile(4, 0,  0, 0, 0, 0, 1, 0, 1, 0);
		this.setTile(5, 0,  1, 0, 1, 0, 0, 0, 0, 0);
		this.setTile(6, 0,  0, 0, 1, 0, 1, 0, 0, 0);
		this.setTile(7, 0,  1, 0, 0, 0, 0, 0, 1, 0);
		this.setTile(0, 1,  0, 0, 1, 0, 0, 0, 1, 0);
		this.setTile(1, 1,  0, 0, 1, 1, 1, 1, 1, 0);
		this.setTile(2, 1,  1, 1, 1, 1, 1, 1, 1, 1);
		this.setTile(3, 1,  1, 1, 1, 0, 0, 0, 1, 1);
		this.setTile(4, 1,  1, 0, 1, 0, 1, 0, 0, 0);
		this.setTile(5, 1,  1, 0, 0, 0, 1, 0, 1, 0);
		this.setTile(6, 1,  0, 0, 1, 0, 1, 0, 1, 0);
		this.setTile(7, 1,  1, 0, 1, 0, 0, 0, 1, 0);
		this.setTile(0, 2,  0, 0, 1, 0, 0, 0, 0, 0);
		this.setTile(1, 2,  0, 0, 1, 1, 1, 0, 0, 0);
		this.setTile(2, 2,  1, 1, 1, 1, 1, 0, 0, 0);
		this.setTile(3, 2,  1, 1, 1, 0, 0, 0, 0, 0);
		this.setTile(4, 2,  1, 0, 1, 1, 1, 0, 1, 0);
		this.setTile(5, 2,  1, 1, 1, 0, 1, 0, 1, 0);
		this.setTile(6, 2,  1, 0, 1, 0, 1, 0, 1, 1);
		this.setTile(7, 2,  1, 0, 1, 0, 1, 1, 1, 0);
		this.setTile(0, 3,  0, 0, 0, 0, 0, 0, 1, 0);
		this.setTile(1, 3,  0, 0, 0, 0, 1, 0, 0, 0);
		this.setTile(2, 3,  1, 0, 0, 0, 1, 0, 0, 0);
		this.setTile(3, 3,  1, 0, 0, 0, 0, 0, 0, 0);
		this.setTile(4, 3,  1, 1, 1, 1, 1, 0, 1, 0);
		this.setTile(5, 3,  1, 0, 1, 0, 1, 1, 1, 1);
		this.setTile(6, 3,  1, 1, 1, 0, 1, 0, 1, 1);
		this.setTile(7, 3,  1, 0, 1, 1, 0, 1, 1, 0);
		this.setTile(0, 4,  1, 1, 1, 1, 1, 1, 1, 0);
		this.setTile(1, 4,  1, 1, 1, 1, 1, 0, 1, 1);
		this.setTile(2, 4,  1, 0, 1, 1, 1, 1, 1, 1);
		this.setTile(3, 4,  1, 1, 1, 0, 1, 1, 1, 1);
		this.setTile(4, 4,  1, 1, 1, 0, 1, 1, 1, 0);
		this.setTile(5, 4,  1, 0, 1, 1, 1, 0, 1, 0);
		this.setTile(6, 4,  1, 0, 1, 0, 1, 0, 1, 0);
	}

}
