package embgine.core.loaders;

import embgine.core.EID;
import embgine.core.GameObject;
import embgine.core.Renderer;
import embgine.core.Scene;
import embgine.core.Script;

abstract public class ObjectLoader implements EID{
	
	protected float width;
	protected float height;
	protected boolean gui;
	protected Object[] rTemplates;
	protected Class<? extends Script> script;
	protected int layer;
	protected Renderer[] renderers;
	protected int type;
	
	public ObjectLoader() {
		
	}
	
	public ObjectLoader(float w, float h, boolean g, Object[][] r, Class<? extends Script> s, int l) {
		width = w;
		height = h;
		gui = g;
		rTemplates = r;
		script = s;
		layer = l;
	}
	
	public void sets() {
		
	}
	
	public Object[][] getTemplates() {
		return (Object[][])rTemplates;
	}
	
	public void giveRenderers(Renderer[] r) {
		renderers = r;
	}
	
	public void giveType(int t) {
		type = t;
	}
	
	public GameObject create(Scene scene) {
		return new GameObject(width, height, renderers, gui, script, layer, type, scene);
	}
	
}
