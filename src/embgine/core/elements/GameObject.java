package embgine.core.elements;

import embgine.core.scripts.ObjectScript;
import embgine.graphics.Camera;
import embgine.graphics.Transform;

public class GameObject extends Element{
	
	public GameObject(Transform transform, ObjectScript script, boolean enabled, int type, int l) {
		super(transform, script, enabled, type, l);
		
		if(script != null) {
			script.setParent(this);
		}
		
	}
	
	public static void init(Camera c) {
		camera = c;
	}
	
	public Camera getCamera() {
		return camera;
	}
	
	public void subUpdate() {
		
	}
	
	public void subRender(int l) {
		((ObjectScript)script).render();
	}

	@Override
	public boolean onScreenUpdate(Camera camera) {
		if(enabled) {
			float ex = transform.    abcissa;
			float ey = transform.   ordinate;
			float ew = transform. getWidth();
			float eh = transform.getHeight();
			
			Transform cTransform = camera.getTransform();
			
			float cx = cTransform.    abcissa;
			float cy = cTransform.   ordinate;
			float cw = cTransform. getWidth();
			float ch = cTransform.getHeight();
			
			onScreen = (ex + ew > cx) && (ex < cx + cw) && (ey + eh > cy) && (ey < cy + ch);
			return onScreen;
		}
		return false;
	}
	
}