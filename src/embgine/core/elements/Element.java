package embgine.core.elements;

import embgine.core.Index;
import embgine.core.scripts.Script;
import embgine.graphics.Camera;
import embgine.graphics.Transform;
import embgine.graphics.Window;

abstract public class Element {
	
	protected static Index index;
	protected static Camera camera;
	protected static Window window;
	
	protected Script<?> script;
	protected Transform transform;
	protected boolean enabled;
	protected boolean onScreen;
	protected int managerIndex;
	protected int type;
	protected int layer;
	
	public Element(Transform t, Script<?> s, boolean e, int y, int l) {
		transform = t;
		script = s;
		enabled = e;
		type = y;
		layer = l;
		onScreen = true;
	}
	
	/**
	 * statically gives the element class and all of its extensions some important game stuff
	 * 
	 * @param x - the game index
	 * @param c - the game camera
	 * @param w - the game window
	 * 
	 */
	public static void init(Index x, Camera c, Window w) {
		index = x;
		camera = c;
		window = w;
	}
	
	//GETTERS
	public Script<?> getScript() {
		return script;
	}
	
	public Transform getTransform() {
		return transform;
	}
	
	public int getManagerIndex() {
		return managerIndex;
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
	
	public int getLayer() {
		return layer;
	}
	
	//SETTERS
	public void setIndex(int value) {
		managerIndex = value;
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
	
	public void setLayer(int value) {
		layer = value;
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