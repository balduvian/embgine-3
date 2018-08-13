package game.scripts;

import embgine.core.Base;
import embgine.core.Utils;
import embgine.core.components.FonRenderer;
import embgine.core.components.TilRenderer;
import embgine.core.scripts.ObjectScript;
import game.master.CrushyMaster;

public class CounterScript extends ObjectScript{
	
	public void start(Object... params) {
		
	}
	
	@Override
	public void update() {
		FonRenderer fon = (FonRenderer) parent.getComponent(0);
		
		fon.setText(CrushyMaster.toTime(CrushyMaster.TIMER));
	}

}
