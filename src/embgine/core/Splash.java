package embgine.core;

import embgine.core.renderers.ColorRenderer;
import embgine.core.renderers.FontRenderer;
import embgine.core.renderers.TextureRenderer;
import embgine.graphics.Camera;
import embgine.graphics.Texture;
import embgine.graphics.Transform;
import embgine.graphics.shapes.Shape;

public class Splash {
	
	private static final double SHOW_TIME = 0.5;
	
	private double timer;
	private Camera camera;
	
	private Transform gt;
	private ColorRenderer white;
	private TextureRenderer logo;
	private FontRenderer text;
	
	public Splash() {
		camera = new Camera(16, 9);
		camera.update();
		
		Shape rect = Shape.RECT;
		
		gt = new Transform(0, 0, 0, 0, 0);
		
		white = new ColorRenderer(rect);
		white.setColor(1, 1, 1, 1);
		logo = new TextureRenderer(rect, new Texture("embgine/standard/logo.png", false));
		text = new FontRenderer(rect, new Font(new Texture("embgine/standard/text.png", 16, 8), 9, 6), 0.75f, true, true, true);
		text.setText(new char[][] {{'M','a','d','e',' ','W','i','t','h',' ','E','m','b','g','i','n','e'}});
		text.setColor(0, 0, 0, 1);
		
		timer = SHOW_TIME;
	}
	
	public boolean update() {
		timer -= Base.time;
		return (timer <= 0);
	}
	
	public void render() {
		
		gt.set(0, 0, 16, 9);
		white.setTransform(gt);
		white.render();
		
		gt.set(0, -1, 5, 5);
		logo.setTransform(gt);
		logo.render();
		
		gt.setPosition(0, 2.5f);
		text.setTransform(gt);
		text.render();
		
	}
}