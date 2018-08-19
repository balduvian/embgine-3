package embgine.graphics.shapes;

import org.lwjgl.opengl.GL11;

public class RectShape extends Shape{

	public RectShape() {
		super(
				new float[] {
		           1, 0, 0,
		           1, 1, 0,
		           0, 1, 0,
		           0, 0, 0
				}, new int[] {
					0, 1, 3,
					1, 2, 3
				}, new float[] {
			        1, 0,
			        1, 1,
			        0, 1,
			        0, 0
				},
				GL11.GL_TRIANGLES
		);
	}
	
}
