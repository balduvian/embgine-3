package embgine.core;

import embgine.graphics.Texture;

public class Block {
	
	private boolean solid;
	private Texture texture;
	
	private int value;
	
	private int layer;
	
	public Block(boolean s, Texture t, int l) {
		solid = s;
		texture = t;
		layer = l;
	}
	
	public boolean isSolid() {
		return solid;
	}
	
	public int getLayer() {
		return layer;
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
