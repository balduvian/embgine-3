package game.mapReferences;

import embgine.core.MapReference;

public class MapReference_World1 extends MapReference{

	public MapReference_World1() {
		super(
			new int[] {
				0x000000,
				0x8b0000,
				0xee9700,
				0x193b04,
				0xdef200,
				0x004fc6,
				0xdf4343,
				0xdf00fd
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
