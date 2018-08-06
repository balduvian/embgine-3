package embgine.core.elements;

import embgine.core.HitBox;
import embgine.core.Scene;
import embgine.core.renderers.Renderer;
import embgine.core.scripts.ObjectScript;
import embgine.core.scripts.Script;
import embgine.graphics.Transform;

public class GameObject extends Element{
	
	private Renderer[] renderers;
	private HitBox[] hitBoxes;
	
	private boolean gui;
	
	private int layer;
	
	public GameObject(Transform transform, ObjectScript script, boolean enabled, int type, Renderer[] rs, boolean g, int l) {
		super(transform, script, enabled, type);
		
		script.setParent(this);
		
		renderers = rs;
		
		gui = g;
		
		layer = l;
	}
	
	public void subRender(int l) {
		if(layer == l) {
			for(Renderer r : renderers) {
				r.setGui(gui);
				r.setTransform(transform);
				r.render();
			}
		}
	}
	
	public void setGui(boolean g) {
		gui = g;
	}
	
	public boolean getGui() {
		return gui;
	}
	
	public Renderer getRenderer(int i) {
		return renderers[i];
	}
	
	public void initHitBoxes(int num) {
		hitBoxes = new HitBox[num];
	}
	
	public void giveHitBox(HitBox x, int i) {
		hitBoxes[i] = x;
	}
	
	public HitBox getHitBox(int i) {
		return hitBoxes[i];
	}

	public void setLayer(int l) {
		layer = l;
	}
	
}