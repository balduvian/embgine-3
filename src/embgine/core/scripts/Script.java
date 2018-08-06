package embgine.core.scripts;

import embgine.core.Scene;

abstract public class Script<P> {
	
	protected P parent;
	protected Scene scene;

	public void setScene(Scene s) {
		scene = s;
	}
	
	public void setParent(P p) {
		parent = p;
	}
	
	abstract public void start(Object... params);
	
	abstract public void update();
	
}