package embgine.core;

import embgine.graphics.Texture;

public class Font {
		
	private Texture texture;
	private int pixelWidth;
	
	private float width;
	private float gWidth;
	
	public Font(Texture t, int pw, int ww) {
		texture = t;
		pixelWidth = pw;
		width = ((float)ww/pixelWidth);
		gWidth = ((float)1/pixelWidth);
	}
	
	public float getGutter() {
		return gWidth;
	}
	
	public float getWidth() {
		return width;
	}
	
	public Texture getTexture() {
		return texture;
	}
	
}