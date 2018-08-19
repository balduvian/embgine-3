package game.scripts;

import embgine.core.Font;
import embgine.core.scripts.ObjectScript;
import embgine.graphics.shapes.Shape;
import game.fonts.Font_Pixel;
import game.master.CrushyMaster;

public class CounterScript extends ObjectScript{
	
	private static Shape rect;
	private static Font font;
	private static char[][] text;
	
	public void start(Object... params) { 
		text = new char[1][11];
	}
	
	@Override
	public void update() {
		CrushyMaster.charTime(text, CrushyMaster.TIMER);
	}

	@Override
	public void render() {
		font.render(parent.getTransform(), true, text, false, 1, 1, 1, 0.75f);
	}

	@Override
	public void staticInit() {
		rect = Shape.RECT;
		font = new Font_Pixel();
	}

}
