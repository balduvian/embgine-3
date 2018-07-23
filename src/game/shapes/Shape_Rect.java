package game.shapes;

import embgine.core.loaders.ShapeLoader;
import embgine.graphics.Camera;

public class Shape_Rect extends ShapeLoader{

	public Shape_Rect(boolean b) {
		super(
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
	}

	@Override
	public int getID() {
		return 4;
	}
	
}
