package embgine.core.loaders;

import embgine.core.GameObject;
import embgine.core.Scene;
import embgine.core.Script;
import embgine.core.renderers.Renderer;

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
		try {
			
			int num = renderers.length;
			Renderer[] cloneRenderers = new Renderer[num];
			for(int i = 0; i < num; ++i) {
				cloneRenderers[i] = renderers[i].clone();
			}
			
			return new GameObject(width, height, cloneRenderers, gui, script, layer, type, scene);
			
		} catch(Exception ex) {
			ex.printStackTrace();
			return null;
		}
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