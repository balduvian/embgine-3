package embgine.core.elements;

import embgine.core.scripts.Script;
import embgine.graphics.Camera;
import embgine.graphics.Transform;

abstract public class Element {
	
	protected Script<?> script;
	
	protected Transform transform;
	
	protected int index;
	
	protected boolean enabled;
	
	protected boolean onScreen;
	
	protected int type;
	
	public Element(Transform t, Script<?> s, boolean e, int y) {
		transform = t;
		script = s;
		enabled = e;
		type = y;
	}
	
	//GETTERS
	public Script<?> getScript() {
		return script;
	}
	
	public Transform getTransform() {
		return transform;
	}
	
	public int getIndex() {
		return index;
	}
	
	public boolean getEnabled() {
		return enabled;
	}
	
	public boolean getOnScreen() {
		return onScreen;
	}
	
	public int getType() {
		return type;
	}
	
	//SETTERS
	public void setIndex(int value) {
		index = value;
	}
	
	public void setEnabled(boolean value) {
		enabled = value;
	}
	
	public void setOnScreen(boolean value) {
		onScreen = value;
	}
	
	public void setType(int value) {
		type = value;
	}
	
	//ROUTINE
	public void start() {
		script.start();
	}
	
	public boolean onScreenUpdate(Camera camera) {
		if(enabled) {
			float ex = transform.     getX()    ;
			float ey = transform.     getY()    ;
			float ew = transform. getWidth() / 2;
			float eh = transform.getHeight() / 2;
			
			Transform cTransform = camera.getTransform();
			
			float cx = cTransform.     getX()    ;
			float cy = cTransform.     getY()    ;
			float cw = cTransform. getWidth() / 2;
			float ch = cTransform.getHeight() / 2;
			
			onScreen = (ex + ew > cx - cw) && (ex - ew < cx + cw) && (ey + eh > cy - ch) && (ey - eh < cy + ch);
			return onScreen;
		}
		return false;
	}
	
	public void update() {
		if(enabled && script != null) {
			script.update();
			subUpdate();
		}
	}
	
	public void render(int layer) {
		if(enabled && onScreen) {
			subRender(layer);
		}
	}
	
	public abstract void subUpdate();
	
	public abstract void subRender(int layer);
	
}