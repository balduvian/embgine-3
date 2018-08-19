package embgine.core.loaders;

import embgine.core.Scene;
import embgine.core.elements.Background;
import embgine.core.elements.Element;
import embgine.graphics.Texture;

public class BackgroundLoader extends ElementLoader<Background>{
	
	private Texture texture;
	private int type;
	private float width, height;
	private float parallax;
	
	public BackgroundLoader(Texture t, int w, int h, int l) {
		texture = t;
		layer = l;
		width = w;
		height = h;
	}
	
	public void giveType(int t) {
		type = t;
	}
	
	public Background create(Scene scene, float x, float y, boolean e) {
		return new Background(e, type, texture, width, height, x, y, parallax, layer);
	}
	
}