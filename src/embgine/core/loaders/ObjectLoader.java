package embgine.core.loaders;

import embgine.core.Scene;
import embgine.core.elements.GameObject;
import embgine.core.scripts.ObjectScript;
import embgine.graphics.Transform;

abstract public class ObjectLoader extends ElementLoader<GameObject>{
	
	private float width;
	private float height;
	private Class<? extends ObjectScript> script;
	
	/**
	 * makes the objectLoader for the scene
	 * 
	 * @param w - the width of the gameObject
	 * @param h - the height of the gameObject
	 * @param g - whether the gameObject will be a gui item or not
	 * @param s - the class of the gameObject's script
	 * @param l - the scene layer the gameObject will render on
	 */
	public ObjectLoader(float w, float h, Class<? extends ObjectScript> s, int l) {
		width      = w;
		height     = h;
		script     = s;
		layer      = l;
		
		//statically inits the script
		try {
			s.newInstance().staticInit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * makes the gameObject instance from the loader
	 * 
	 * @return the gameObject instance
	 */
	public GameObject create(Scene scene, float x, float y, boolean enabled) {
		ObjectScript sInstance = null;
		try {
			sInstance = (ObjectScript)script.getConstructors()[0].newInstance();
			sInstance.setScene(scene);
		} catch(Exception ex) { }
		
		Transform transform = new Transform(x, y, width, height);
		
		return new GameObject(transform, sInstance, enabled, type, layer);
	}
	
}