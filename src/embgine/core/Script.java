package embgine.core;

public abstract class Script {
	
	protected GameObject parent;
	protected Scene scene;
	
	public Script(GameObject p, Scene s) {
		parent = p;
		scene = s;
	}
	
	abstract public void start(Object... params);
	
	abstract public void update();
}
