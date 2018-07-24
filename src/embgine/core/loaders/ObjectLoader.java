package embgine.core.loaders;

import embgine.core.GameObject;
import embgine.core.Renderer;
import embgine.core.Scene;
import embgine.core.Script;

abstract public class ObjectLoader {
	
	private float width;
	private float height;
	private boolean gui;
	private Object[][] rTemplates;
	private Class<? extends Script> script;
	private int layer;
	private Renderer[] renderers;
	private int type;
	
	public ObjectLoader(float w, float h, boolean g, Object[][] r, Class<? extends Script> s, int l) {
		width      = w;
		height     = h;
		gui        = g;
		rTemplates = r;
		script     = s;
		layer      = l;
	}
	
	public GameObject create(Scene scene) {
		return new GameObject(width, height, renderers, gui, script, layer, type, scene);
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
