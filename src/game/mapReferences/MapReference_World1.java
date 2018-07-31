package game.mapReferences;

import embgine.core.MapReference;

public class MapReference_World1 extends MapReference{

	public MapReference_World1() {
		super(
			new int[] {
				0xff000000,
				0xff8B0000,
				0xffEE9700,
				0xff193B04,
				0xffDEF200,
				0xff004FC6,
				0xffDF4343
			},
			new String[] {
				"Ground",
				"X",
				"Bush",
				"Bricks",
				"Question",
				"Pipe",
				"Pole"
			}
		);
	}
}
