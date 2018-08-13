package game.scripts;

import embgine.core.Base;
import embgine.core.Utils;
import embgine.core.components.TilRenderer;
import embgine.core.scripts.ObjectScript;
import game.master.CrushyMaster;
//https://www.beepbox.co/#6n31s1kbl00e07t7m0a7g0fj7i0r1o3210T0w1f1d1c0h0v0T0w2f4d1c2h7v0T0w1f1d1c0h0v1T2w1d1v0b8y8h4N4h4h418gcQ4h4h4h8z4j8h4h4h4ycjcN4h4h4p23dFHY9zW5cYbqV0twNQ1E3Qba3Jq62P77BcuF6hOYPbbcPcOOPcPcIIUitDOczBVCmmpCpBBCpCpppAVahAp9d7xc-sHpSJ6jmCN-pdBtwkP-KFFFFFFFzPjjjjjjjjjjjjjjjOAQ-sjOIGh1j4Uz03j0w6i6Aegawn9M050a0pKi2w50cH91g2w6lAwE1g3D4rxV8cwzjA0E40
public class GibScript extends ObjectScript{

	private int frameX;
	private int frameY;
	
	private float yVel;
	private float xVel;
	
	public void start(Object... params) {
		
		yVel = Utils.rand(-9 * 16f, -14 * 16f);
		xVel = Utils.rand(9 * 16, 14 * 16) * ( Utils.random() - 0.5f);
		
	}
	
	@Override
	public void update() {
		
		yVel += CrushyMaster.GRAVITY;
		
		if(yVel > 0) {
			if(xVel > 0) {
				frameX = 1;
				frameY = 1;
			}else {
				frameX = 0;
				frameY = 1;
			}
		}else {
			if(xVel > 0) {
				frameX = 1;
				frameY = 0;
			}else {
				frameX = 0;
				frameY = 0;
			}
		}
		
		((TilRenderer)parent.getComponent(0)).setFrame(frameX, frameY);
		
		parent.getTransform().move( (float)(xVel * Base.time), (float)(yVel * Base.time));
		
		if(!parent.getOnScreen()) {
			((GameScript)scene.getScript()).reduceGib();
			scene.destroyObject(parent);
		}
	}

}
