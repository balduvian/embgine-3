package game.scenes;

import embgine.core.Scene;
import game.backgrounds.Background_City;
import game.block.Block_Door;
import game.block.Block_Orange;
import game.block.Block_Pole;
import game.block.Block_Slab;
import game.block.Block_Spike;
import game.block.Block_Purple;
import game.fonts.Font_Pixel;
import game.gameObjects.Object_Counter;
import game.gameObjects.Object_Gib;
import game.gameObjects.Object_Player;
import game.gameObjects.Object_YouWin;
import game.mapReferences.MapReference_Crushy;
import game.maps.Map_Bottom;
import game.maps.Map_Left0;
import game.maps.Map_Left1;
import game.maps.Map_Left2;
import game.maps.Map_Left3;
import game.maps.Map_Left4;
import game.maps.Map_Left5;
import game.maps.Map_Left6;
import game.maps.Map_Left7;
import game.maps.Map_Left8;
import game.maps.Map_Left9;
import game.maps.Map_Right0;
import game.maps.Map_Right1;
import game.maps.Map_Right2;
import game.maps.Map_Right3;
import game.maps.Map_Right4;
import game.maps.Map_Right5;
import game.maps.Map_Right6;
import game.maps.Map_Right7;
import game.maps.Map_Right8;
import game.maps.Map_Right9;
import game.maps.Map_Top;
import game.master.CrushyMaster;
import game.scripts.GameScript;
import game.scripts.Level1Script;
import game.scripts.Level3Script;
import game.scripts.LevelWinScript;

public class Scene_Game extends Scene{

	@SuppressWarnings("unchecked")
	public Scene_Game() {
		super(
			GameScript.class,
			Level1Script.class,
			CrushyMaster.GAMELAYERS,
			16,
			32,
			2,
			new String[] {
				"game/sounds/jump.wav",
				"game/sounds/crush.wav"
			}, 
			new Class[] {
				Font_Pixel.class,
			}, 
			new Class[] {
				Object_Player.class,
				Object_Gib.class,
				Object_YouWin.class,
				Object_Counter.class
			}, 
			new Class[] {
				Background_City.class,
			},
			new Class[] {
				Block_Door.class,
				Block_Orange.class,
				Block_Pole.class,
				Block_Purple.class,
				Block_Slab.class,
				Block_Spike.class
			},
			new Class[] {
				MapReference_Crushy.class,
			},
			new Class[] {
				Map_Bottom.class,
				Map_Top.class,
				Map_Left0.class,
				Map_Right0.class,
				Map_Left1.class,
				Map_Right1.class,
				Map_Left2.class,
				Map_Right2.class,
				Map_Left3.class,
				Map_Right3.class,
				Map_Left4.class,
				Map_Right4.class,
				Map_Left5.class,
				Map_Right5.class,
				Map_Left6.class,
				Map_Right6.class,
				Map_Left7.class,
				Map_Right7.class,
				Map_Left8.class,
				Map_Right8.class,
				Map_Left9.class,
				Map_Right9.class,
			} 
		);
	}
	
}
