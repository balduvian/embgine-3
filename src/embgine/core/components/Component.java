package embgine.core.components;

import embgine.core.elements.GameObject;

abstract public class Component {
	
	protected GameObject parent;
	
	public void setParent(GameObject o) {
		parent = o;
	}
	
	abstract public Component clone();
	
	abstract public void update();
	
	abstract public void render();
	
}
