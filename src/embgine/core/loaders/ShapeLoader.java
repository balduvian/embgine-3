package embgine.core.loaders;

import embgine.graphics.Camera;
import embgine.graphics.Shape;

public class ShapeLoader {
	
	private static Camera camera;
	
	private float[] vertices;
	private int  [] indices;
	private float[] texCoords;
	
	public ShapeLoader(float[] v, int[] i, float[] t) {
		vertices = v;
		indices = i;
		texCoords = t;
	}
	
	public static void giveCamera(Camera c) {
		camera = c;
	}
	
	public Shape create() {
		return new Shape(camera, vertices, indices, texCoords);
	}
}