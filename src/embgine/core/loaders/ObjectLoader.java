package embgine.core.loaders;

import embgine.core.Scene;
import embgine.core.components.Component;
import embgine.core.elements.GameObject;
import embgine.core.scripts.ObjectScript;
import embgine.graphics.Transform;

abstract public class ObjectLoader {
	
	private float width;
	private float height;
	private boolean gui;
	private Class<? extends ObjectScript> script;
	private int type;
	private int layer;
	
	private Component[] components;
	
	public ObjectLoader(float w, float h, boolean g, Component[] c, Class<? extends ObjectScript> s, int l) {
		width      = w;
		height     = h;
		gui        = g;
		components = c;
		script     = s;
		layer      = l;
	}
	
	public void giveType(int t) {
		type = t;
	}
	
	public GameObject create(Scene scene, float x, float y, boolean enabled) {
		
		int num = components.length;
		Component[] clones = new Component[num];
		for(int i = 0; i < num; ++i) {
			clones[i] = components[i].clone();
		}
		
		ObjectScript sInstance = null;
		try {
			sInstance = (ObjectScript)script.getConstructors()[0].newInstance();
			sInstance.setScene(scene);
		} catch(Exception ex) { }
		
		Transform transform = new Transform(x, y, width, height);
		
		return new GameObject(transform, sInstance, enabled, type, clones, gui, layer);
		
	}
	
}