package embgine.graphics.shapes;

import embgine.graphics.Camera;
import embgine.graphics.VBO;

abstract public class Shape {
	
	private VBO vbo;
	
	public static RectShape RECT;
	public static ArrowShape ARROW;
	
	public static void init(Camera c) {
		RECT = new RectShape();
		ARROW = new ArrowShape();
	}
	
	public Shape(float vertices[], int[] indices, float[] texCoords, int drawMode) {
		vbo = new VBO(vertices, indices, drawMode);
		vbo.addAttrib(texCoords, 2);
	}
	
	public void render() {
		vbo.render();
	}
	
}