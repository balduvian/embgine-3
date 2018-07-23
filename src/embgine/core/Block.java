package embgine.core;

import embgine.graphics.Texture;

public class Block {
	
	private boolean solid;
	private Texture texture;
	
	private int value;
	
	private Block(Block b) {
		solid = b.solid;
		if(b.texture != null) {
			texture = b.texture;
		}
	}
	
	public Block(boolean s, Texture t) {
		solid = s;
		texture = t;
	}
	
	public boolean isSolid() {
		return solid;
	}
	
	public Block create() {
		return new Block(this);
	}
	
	public void setValue(int v) {
		value = v;
	}
	
	public int getValue() {
		return value;
	}
	
	public Texture getTexture() {
		return texture;
	}
}
