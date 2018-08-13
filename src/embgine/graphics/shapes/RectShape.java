package embgine.graphics.shapes;

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
				Shape.TRIANGLES
		);
	}
	
}
