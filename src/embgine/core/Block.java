package embgine.core;

import embgine.graphics.Texture;

public class Block {
	
	private boolean solid;
	private Texture texture;
	
	private int type;
	
	private int valueX;
	private int valueY;
	
	private int layer;
	
	public Block(boolean s, Texture t, int l, int y) {
		solid = s;
		texture = t;
		layer = l;
		type = y;
	}
	
	public boolean isSolid() {
		return solid;
	}
	
	public int getLayer() {
		return layer;
	}
	
	public void setValue(int[] v) {
		valueX = v[0];
		valueY = v[1];
	}
	
	public void setValue(int x, int y) {
		valueX = x;
		valueY = y;
	}
	
	public void setValueX(int v) {
		valueX = v;
	}
	
	public void setValueY(int v) {
		valueY = v;
	}
	
	public int getValueX() {
		return valueX;
	}
	
	public int getValueY() {
		return valueY;
	}
	
	public Texture getTexture() {
		return texture;
	}
	
	public int getType() {
		return type;
	}
}
