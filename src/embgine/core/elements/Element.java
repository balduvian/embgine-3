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
		onScreen = true;
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
		if(script != null) {
			script.start();
		}
	}
	
	abstract public boolean onScreenUpdate(Camera camera);
	
	public void update() {
		if(enabled) {
			subUpdate();
			if(script != null) {
				script.update();
			}
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