package embgine.core;

import embgine.graphics.Texture;

public class Font {
		
	private Texture texture;
	private int pixelWidth;
	
	private int[] widths;
	
	public Font(Texture t, int pw, int[] ww) {
		texture = t;
		pixelWidth = pw;
		widths = ww;
	}
	
	public float advance(char i) {
		return ((float)widths[i]/pixelWidth);
	}
	
	public Texture getTexture() {
		return texture;
	}
	
}
