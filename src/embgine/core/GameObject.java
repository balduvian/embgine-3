package embgine.core;

import embgine.core.renderers.Renderer;
import embgine.core.renderers.TileRenderer;
import embgine.graphics.Transform;

public class GameObject {
	
	private Transform transform;
	
	private Renderer[] renderers;
	
	private Script script;
	private boolean enabled;

	private int index;
	private int layer;
	
	private boolean eternal;
	
	private int frame;
	
	private boolean gui;
	
	private int type;
	
	private boolean onScreen;
	
	public GameObject(float w, float h, Renderer[] rs, boolean g, Class<? extends Script> sc, int l, int p, Scene scene) {
		transform = new Transform(w, h);
		
		renderers = rs;
		
		try {
			script = (Script)script.getClass().getConstructors()[0].newInstance(this, scene);
		} catch(Exception ex) {
			script = null;
		}
		
		enabled = true;
		layer = l;
		frame = 0;
		gui = g;
		eternal = false;
		
		type = p;
	}
	
	public void eternalize() {
		eternal = true;
	}
	
	public boolean isEternal() {
		return eternal;
	}
	
	public void update() {
		if(enabled && script != null) {
			script.update();
		}
	}
	
	public void render() {
		if(enabled) {
			for(Renderer r : renderers) {
				if(r instanceof TileRenderer) {
					((TileRenderer)r).giveFrame(frame);
				}
				r.setGui(gui);
				r.setTransform(transform);
				r.render();
			}
		}
	}
	
	public int getLayer() {
		return layer;
	}
	
	public void setIndex(int i) {
		index = i;
	}
	
	public void deIncrement() {
		--index;
	}
	
	public void setEnabled(boolean e) {
		enabled = e;
	}
	
	public Transform getTransform() {
		return transform;
	}
	
	public Script getScript() {
		return script;
	}
	
	public int getIndex() {
		return index;
	}
	
	public void setFrame(int f) {
		frame = f;
	}
	
	public int getFrame() {
		return frame;
	}
	
	public void setGui(boolean g) {
		gui = g;
	}
	
	public int getType() {
		return type;
	}
	
	public void setOnScreen(boolean b) {
		onScreen = b;
	}
	
	public boolean getOnScreen() {
		return onScreen;
	}
	
	public boolean getGui() {
		return gui;
	}
	
	public Renderer getRenderer(int i) {
		return renderers[i];
	}
	
}