package embgine.core.components;

import embgine.core.elements.GameObject;
import embgine.graphics.Transform;

abstract public class Component {
	
	protected GameObject parent;
	protected Transform transform;
	protected boolean tSync;
	
	public Component(Transform t, boolean s) {
		transform = t;
		tSync = s;
	}
	
	public void setParent(GameObject o){
		parent = o;
	}
	
	public Transform getTransform() {
		return transform;
	}
	
	public void setTSync(boolean s) {
		tSync = s;
	}
	
	abstract public Component clone();
	
	abstract public void update();
	
	abstract public void render();
	
}
