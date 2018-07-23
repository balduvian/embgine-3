package embgine.core;

import embgine.core.renderers.ColorRenderer;
import embgine.core.renderers.TileRenderer;
import embgine.graphics.Camera;
import embgine.graphics.Shape;
import embgine.graphics.Texture;
import game.shapes.Shape_Rect;

public class Splash {
	
	private static final int SHOW_TIME = 2;
	
	private double timer;
	private GameObject logo;
	private GameObject white;
	private Camera camera;
	
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
		
		logo = new GameObject(5, 7, new Renderer[] {new TileRenderer(rect, new Texture("embgine/standard/textures/logo.png"))}, new NullScript(), true, 0, -1);
		white = new GameObject(16, 9, new Renderer[] {new ColorRenderer(rect)}, new NullScript(), true, 0, -1);
		((ColorRenderer)white.getRenderer(0)).setColor(1, 1, 1, 1);
		timer = SHOW_TIME;
	}
	
	public boolean update() {
		timer -= Base.time;
		return (timer <= 0);
	}
	
	public void render() {
		white.render();
		logo.render();
	}
}
