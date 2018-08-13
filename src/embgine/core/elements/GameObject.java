package embgine.core.elements;

import embgine.core.Index;
import embgine.core.components.Component;
import embgine.core.components.HitBox;
import embgine.core.scripts.ObjectScript;
import embgine.graphics.Camera;
import embgine.graphics.Transform;

public class GameObject extends Element{
	
	private static Camera camera;
	
	private int numComponents;
	private Component[] components;
	
	private boolean gui;
	
	private int layer;
	
	public GameObject(Transform transform, ObjectScript script, boolean enabled, int type, Component[] componentList, boolean g, int l) {
		super(transform, script, enabled, type);
		
		if(script != null) {
			script.setParent(this);
		}
		
		components = componentList;
		numComponents = components.length;
		
		for(int i = 0; i < numComponents; ++i) {
			components[i].setParent(this);
		}
		
		gui = g;
		
		layer = l;
	}
	
	public static void init(Camera c) {
		camera = c;
	}
	
	public void subUpdate() {
		for(int i = 0; i < numComponents; ++i) {
			components[i].update();
		}
	}
	
	public void subRender(int l) {
		if(layer == l) {
			for(int i = 0; i < numComponents; ++i) {
				components[i].render();
			}
		}
		if(gui) {
			transform.setPosition(camera.getTransform().getX() + 24, camera.getTransform().getY() + camera.getTransform().getHeight() - 16);
		}
	}
	
	public void setGui(boolean g) {
		gui = g;
	}
	
	public boolean getGui() {
		return gui;
	}
	
	public Component getComponent(int i) {
		return components[i];
	}
	
	public void replaceComponent(Component x, int i) {
		components[i] = x;
		x.setParent(this);
		x.update();
	}

	public void setLayer(int l) {
		layer = l;
	}

	@Override
	public boolean onScreenUpdate(Camera camera) {
		if(enabled) {
			float ex = transform.     getX();
			float ey = transform.     getY();
			float ew = transform. getWidth();
			float eh = transform.getHeight();
			
			Transform cTransform = camera.getTransform();
			
			float cx = cTransform.     getX();
			float cy = cTransform.     getY();
			float cw = cTransform. getWidth();
			float ch = cTransform.getHeight();
			
			onScreen = (ex + ew > cx) && (ex < cx + cw) && (ey + eh > cy) && (ey < cy + ch);
			return onScreen;
		}
		return false;
	}
	
}