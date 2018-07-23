package embgine.core.loaders;

import embgine.core.GameObject;
import embgine.core.Renderer;
import embgine.core.Scene;
import embgine.core.Script;

abstract public class ObjectLoader extends Loader<GameObject> {
	
	private float width;
	private float height;
	private boolean gui;
	private Object[][] rTemplates;
	private Class<? extends Script> script;
	private int layer;
	private Renderer[] renderers;
	private int type;
	
	@SuppressWarnings("unchecked")
	@Override
	public void sets(Object... objects) {
		width = (float)objects[0];
		height = (float)objects[1];
		gui = (boolean)objects[2];
		rTemplates = (Object[][])objects[3];
		script = (Class<? extends Script>)objects[4];
		layer = (int)objects[5];
	}
	
	@Override
	public GameObject create(Object... scene) {
		return new GameObject(width, height, renderers, gui, script, layer, type, (Scene)scene[0]);
	}
	
	public Object[][] getTemplates() {
		return rTemplates;
	}
	
	public void giveRenderers(Renderer[] r) {
		renderers = r;
	}
	
	public void giveType(int t) {
		type = t;
	}
	
}
