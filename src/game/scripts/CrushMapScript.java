package game.scripts;

import embgine.core.Base;
import embgine.core.scripts.MapScript;
import embgine.graphics.Transform;

public class CrushMapScript extends MapScript{

	private Transform transform;
	
	private float speed;
	private float movement;
	
	private int lastX;
	private int move;
	
	public void start(Object... params) {
		transform = parent.getTransform();
		lastX = parent.getTransform().getX();
	}

	@Override
	public void update() {
		movement = (float) (speed * Base.time);
		transform.moveX(movement);
		int nowX = transform.getX();
		move = nowX-lastX;
		lastX = nowX;
	}
	
	public void setSpeed(float s) {
		speed = s;
	}
	
	public int getMove() {
		return move;
	}

}
