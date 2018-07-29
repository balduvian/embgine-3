package embgine.core;

import embgine.core.renderers.ColorRenderer;
import embgine.core.renderers.FontRenderer;
import embgine.core.renderers.TextureRenderer;
import embgine.graphics.Camera;
import embgine.graphics.Shape;
import embgine.graphics.Texture;
import embgine.graphics.Transform;

public class Splash {
	
	private static final int SHOW_TIME = 2;
	
	private double timer;
	private Camera camera;
	
	private Transform gt;
	private ColorRenderer white;
	private TextureRenderer logo;
	private FontRenderer text;
	
	public Splash() {
		camera = new Camera(16, 9);
		camera.update();
		
		Shape rect = new Shape(
			camera,
			new float[] {
	           0.5f, -0.5f, 0,
	           0.5f,  0.5f, 0,
	           -0.5f,  0.5f, 0,
	           -0.5f, -0.5f, 0
			}, new int[] {
				0, 1, 3,
				1, 2, 3
			}, new float[] {
		        1, 0,
		        1, 1,
		        0, 1,
		        0, 0
			}
		);
		
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
		
		gt.setPosition(0, 2);
		text.setTransform(gt);
		text.render();
		
	}
}