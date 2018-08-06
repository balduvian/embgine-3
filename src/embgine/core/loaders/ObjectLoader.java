package embgine.core.loaders;

import embgine.core.Scene;
import embgine.core.elements.GameObject;
import embgine.core.renderers.Renderer;
import embgine.core.scripts.ObjectScript;
import embgine.graphics.Transform;

abstract public class ObjectLoader {
	
	private float width;
	private float height;
	private boolean gui;
	private Object[][] rTemplates;
	private Class<? extends ObjectScript> script;
	private Renderer[] renderers;
	private int type;
	private int layer;
	
	public ObjectLoader(float w, float h, boolean g, Object[][] r, Class<? extends ObjectScript> s, int l) {
		width      = w;
		height     = h;
		gui        = g;
		rTemplates = r;
		script     = s;
		layer      = l;
	}
	
	public GameObject create(Scene scene, float x, float y, boolean enabled) {
		
		int num = renderers.length;
		Renderer[] cloneRenderers = new Renderer[num];
		for(int i = 0; i < num; ++i) {
			cloneRenderers[i] = renderers[i].clone();
		}
		
		ObjectScript sInstance = null;
		try {
			sInstance = (ObjectScript)script.getConstructors()[0].newInstance();
			sInstance.setScene(scene);
		} catch(Exception ex) { }
		
		Transform transform = new Transform(x, y, width, height);
		
		return new GameObject(transform, sInstance, enabled, type, cloneRenderers, gui, layer);
	}
	
	public Object[][] getTemplates() {
		return rTemplates;
	}
	
	public void setup(Renderer[] r, int t) {
		renderers = r;
		type = t;
	}
	
}