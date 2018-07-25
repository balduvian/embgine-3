package embgine.core.loaders;

import embgine.core.Block;
import embgine.graphics.Texture;

public class BlockLoader {
	
	private boolean   solid;
	private Texture texture;
	
	public BlockLoader(boolean s, Texture t) {
		solid   = s;
		texture = t;
	}
	
	public Block create(){
		return new Block(solid, texture);
	}
}
