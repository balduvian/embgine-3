package game.scenes;

import embgine.core.Scene;
import game.block.Block_Door;
import game.block.Block_Orange;
import game.block.Block_Pole;
import game.block.Block_Slab;
import game.block.Block_Spike;
import game.block.Block_Purple;
import game.fonts.Font_Pixel;
import game.gameObjects.Object_PlayButton;
import game.gameObjects.Object_Player;
import game.gameObjects.Object_RecordButton;
import game.gameObjects.Object_RecordPanel;
import game.gameObjects.Object_TitleCat;
import game.gameObjects.Object_TitleText;
import game.gameObjects.Object_White;
import game.mapReferences.MapReference_Crushy;
import game.maps.Map_Bottom;
import game.maps.Map_Left0;
import game.maps.Map_Right0;
import game.maps.Map_Top;
import game.master.CrushyMaster;
import game.scripts.GameScript;
import game.scripts.Level1Script;
import game.scripts.TitleScript;

public class Scene_Title extends Scene{

	@SuppressWarnings("unchecked")
	public Scene_Title() {
		super(
			TitleScript.class,
			null,
			CrushyMaster.GAMELAYERS,
			16,
			32,
			2,
			new String[] {
				"game/sounds/slide.wav",
				"game/sounds/switch.wav"
			}, 
			new Class[] {
				Font_Pixel.class,
			}, 
			new Class[] {
				Object_TitleCat.class,
				Object_TitleText.class,
				Object_White.class,
				Object_PlayButton.class,
				Object_RecordButton.class,
				Object_RecordPanel.class
			}, 
			new Class[] {
				
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
				//maps
			} 
		);
	}
	
}
