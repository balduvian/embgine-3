package embgine.graphics.shapes;

import org.lwjgl.opengl.GL11;

public class ArrowShape extends Shape{

	public ArrowShape() {
		super(
				new float[] {
				    0.5f,    0f, 0,
				      1f,    1f, 0,
				    0.5f, 0.25f, 0,
				      0f,    1f, 0
				}, new int[] {
					0, 1, 2,
					0, 3, 2
				}, new float[] {
					0.5f, 1,
					1, 0,
					0.5f, 0.25f,
					0, 0
				},
				GL11.GL_TRIANGLES
		);
	}

}
