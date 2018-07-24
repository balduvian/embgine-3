package game.maps;

import embgine.core.MapReference;
import game.block.Block_Bricks;
import game.block.Block_Bush;
import game.block.Block_Ground;
import game.block.Block_Pipe;
import game.block.Block_Pole;
import game.block.Block_Question;
import game.block.Block_X;

public class MapRef_World1 extends MapReference{

	@SuppressWarnings("unchecked")
	public MapRef_World1() {
		super(
				"game/levels/level1.png",
				new int[] {
					0x000000,
					0x8b0000,
					0xee9700,
					0x193b04,
					0xdef200,
					0x004fc6,
					0xdf4343,
					0xdf00fd,
				},
				new Class[] {
					Block_Ground.class,
					Block_X.class,
					Block_Bush.class,
					Block_Bricks.class,
					Block_Question.class,
					Block_Pipe.class,
					Block_Pole.class,
				}
		);
	}
}
