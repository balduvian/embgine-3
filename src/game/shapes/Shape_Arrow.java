package game.shapes;

import embgine.core.loaders.ShapeLoader;

public class Shape_Arrow extends ShapeLoader{

	public Shape_Arrow() {
		super(
				new float[] {
				    0f, -0.5f, 0,
				    0.5f,  0.5f, 0,
				    0f,  0.25f, 0,
				    -0.5f, 0.5f, 0
				}, new int[] {
					0, 1, 2,
					0, 3, 2
				}, new float[] {
					0.5f, 1,
					1, 0,
					0.5f, 0.25f,
					0, 0
				}
		);
	}

	@Override
	public int getID() {
		return 73;
	}

}
