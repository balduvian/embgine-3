package embgine.core.loaders;

import embgine.core.EID;
import embgine.graphics.Camera;
import embgine.graphics.Shape;

public abstract class ShapeLoader implements EID{
	
	float[] vertices;
	  int[]   indices;
	float[] texCoords;
	
	public ShapeLoader() {
		
	}
	
	public ShapeLoader(float[] ver, int[] ind, float[] tex) {
		vertices = ver;
		indices = ind;
		texCoords = tex;
	}
	
	public Shape create(Camera c) {
		return new Shape(c, vertices, indices, texCoords);
	}
}
