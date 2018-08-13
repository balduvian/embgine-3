package embgine.core.loaders;

import embgine.core.elements.Background;
import embgine.graphics.Texture;

public class BackgroundLoader {
	
	private Texture texture;
	private int type;
	private int layer;
	private int width, height;
	
	public BackgroundLoader(Texture t, int w, int h, int l) {
		texture = t;
		layer = l;
		width = w;
		height = h;
	}
	
	public void giveType(int t) {
		type = t;
	}
	
	public Background create(int x, int y, boolean e, float p) {
		return new Background(e, type, texture, width, height, x, y, p, layer);
	}
	
}