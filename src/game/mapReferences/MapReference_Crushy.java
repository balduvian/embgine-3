package game.mapReferences;

import embgine.core.MapReference;

public class MapReference_Crushy extends MapReference{

	public MapReference_Crushy() {
		super(
			new int[] {
				0xff23BBDE, //door
				0xff440E01, //orange
				0xff0E0042, //pole
				0xff0C0022, //purple
				0xff40005E, //low slab
				0xff9000D3, //high slab
				0xff5E4900, //up spike
				0xff60233E, //right spike
				0xff990000, //down spike
				0xff5E230A, //left spike
			},
			new String[] {
				"Door",
				"Orange",
				"Pole",
				"Purple",
				"Slab",
				"Slab",
				"Spike",
				"Spike",
				"Spike",
				"Spike"
			},
			new int[][] {
				{0, 0}, //door
				{0, 0}, //orange
				{0, 0}, //pole
				{0, 0}, //purple
				{0, 0}, //low slab
				{1, 0}, //high slab
				{0, 0}, //up spike
				{1, 0}, //right spike
				{0, 1}, //down spike
				{1, 1}, //left spike
			}
		);
	}
}
