package game.scripts;

import embgine.core.Base;
import embgine.core.elements.GameObject;
import embgine.core.loaders.ObjectLoader;
import embgine.core.scripts.SceneScript;
import embgine.core.components.ColRenderer;
import embgine.core.components.FonRenderer;
import embgine.core.components.SelRenderer;
import embgine.graphics.Transform;
import game.master.CrushyMaster;

public class TitleScript extends SceneScript{

	public static final int STAGE_CAT_FLY = 0;
	public static final int STAGE_TITLE_FLY = 1;
	public static final int STAGE_READY = 2;
	
	private GameObject cat;
	private GameObject text;
	private GameObject white;
	private GameObject play;
	private GameObject record;
	private GameObject panel;
	
	private int stage;
	
	private int selection;
	
	private boolean upRelease;
	private boolean downRelease;
	private boolean spaceRelease;
	
	private int menuLayer;
	
	public void start(Object... params) {
		ObjectLoader catLoader = parent.getObjectLoader("TitleCat");
		ObjectLoader textLoader = parent.getObjectLoader("TitleText");
		ObjectLoader whiteLoader = parent.getObjectLoader("White");
		ObjectLoader playLoader = parent.getObjectLoader("PlayButton"); 
		ObjectLoader recordLoader = parent.getObjectLoader("RecordButton"); 
		ObjectLoader panelLoader = parent.getObjectLoader("RecordPanel"); 
		
		cat    = scene.createObject(   catLoader,  0, 144,  true);
		text   = scene.createObject(  textLoader, 64, -32,  true);
		white  = scene.createObject( whiteLoader,  0,   0,  true);
		play   = scene.createObject(  playLoader, 96,  48, false);
		record = scene.createObject(recordLoader, 80,  80, false);
		panel  = scene.createObject( panelLoader,  0,   0, false);
		((ColRenderer)white.getComponent(0)).setColor(1, 1, 1, 1);
		
		stage = STAGE_CAT_FLY;
		
		menuLayer = 0;
		
		CrushyMaster.game_music.stop();
		CrushyMaster.menu_music.play(true);
	}

	public void preUpdate() {
		if(stage == STAGE_READY) {
			
			//pressing the key W
			if(parent.getWindow().keyPressed(87)) {
				if(menuLayer == 0 && !upRelease) {
					--selection;
					selection %= 2;
					scene.sound("switch.wav", 1, false);
					upRelease = true;
				}
			}else {
				upRelease = false;
			}
			
			//pressing the key S
			if(parent.getWindow().keyPressed(83)) {
				if(menuLayer == 0 && !downRelease) {
					++selection;
					selection %= 2;
					scene.sound("switch.wav", 1, false);
					downRelease = true;
				}
			}else {
				downRelease = false;
			}
			
			if(selection == 0) {
				((SelRenderer)play.getComponent(0)).setEnabled(1);
				((SelRenderer)record.getComponent(0)).setEnabled(0);
			}else {
				((SelRenderer)play.getComponent(0)).setEnabled(0);
				((SelRenderer)record.getComponent(0)).setEnabled(1);
			}
			
			//pressing the key space
			if(parent.getWindow().keyPressed(32)) {
				if(!spaceRelease) {
					if(selection == 0) {
						scene.switchScene("Game");
						CrushyMaster.game_music.play(true);
						CrushyMaster.menu_music.stop();
						CrushyMaster.TIMER = 0;
					}else {
						if(menuLayer == 0) {
							menuLayer = 1;
							CrushyMaster.readBestTime();
							((FonRenderer)panel.getComponent(1)).setText(CrushyMaster.BEST_STRING);
							((FonRenderer)panel.getComponent(1)).getTransform().setPosition(116,68);
							//((FonRenderer)panel.getComponent(1)).getTransform().setPosition(0, 0);
							panel.setEnabled(true);
						}else {
							menuLayer = 0;
							panel.setEnabled(false);
						}
					}
					spaceRelease = true;
				}
			}else {
				spaceRelease = false;
			}
			
		}else {
			//press Q to skip
			if(parent.getWindow().keyPressed(81)) {
				moveStage();
			}
		}
	}
	
	public void update() {
		switch(stage) {
			case(STAGE_CAT_FLY):
				Transform ct = cat.getTransform();
				if(ct.getY() > 0) {
					ct.moveY((float) (Base.time * -48));
				}else {
					moveStage();
				}
				break;
			case(STAGE_TITLE_FLY):
				Transform tt = text.getTransform();
				if(tt.getY() < 0) {
					tt.moveY((float) (Base.time * 24));
				}else {
					moveStage();
				}
				break;
			case(STAGE_READY):
				break;
		}
	}
	
	private void moveStage() {
		switch(stage) {
			case(STAGE_CAT_FLY):
				cat.getTransform().setY(0);
				stage = STAGE_TITLE_FLY;
				scene.sound("slide.wav", 1, false);
				break;
			case(STAGE_TITLE_FLY):
				text.getTransform().setY(0);
				stage = STAGE_READY;
				play.setEnabled(true);
				record.setEnabled(true);
				selection = 0;
				upRelease = false;
				downRelease = false;
				break;
		}
	}

}
