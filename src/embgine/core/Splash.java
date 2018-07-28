package embgine.core;

import embgine.core.renderers.ColorRenderer;
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
		logo = new TextureRenderer(rect, new Texture("embgine/standard/logo.png"));
		
		timer = SHOW_TIME;
	}
	
	public boolean update() {
		timer -= Base.time;
		return (timer <= 0);
	}
	
	public void render() {
		
		gt.setSize(16, 9);
		white.setTransform(gt);
		white.render();
		
		gt.setSize(5, 5);
		logo.setTransform(gt);
		logo.render();
		
	}
}