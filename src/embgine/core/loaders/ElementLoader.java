package embgine.core.loaders;

import embgine.core.Scene;
import embgine.core.elements.Element;

abstract public class ElementLoader<T extends Element> {
	
	protected int type;
	protected int layer;
	
	/**
	 * makes an instance of the type the loader is loading
	 * 
	 * @param scene - the scene the element will be loaded into
	 * @param x - the x position of the element
	 * @param y - the y position of the element
	 * @param enabled - the initial enabled state of the element
	 * 
	 * @return an instance of the element type
	 */
	public abstract T create(Scene scene, float x, float y, boolean enabled);
	
	/**
	 * sets the type identifier for the loader in the current scene
	 * 
	 * @param t - the type identifier
	 */
	public void setType(int t) {
		type = t;
	}
	
	/**
	 * gets the type identifier of the elements this loader makes
	 * 
	 * @return the type identifier of the element loader
	 */
	public int getType() {
		return type;
	}
	
	/**
	 * sets the render layer of the element this loader makes
	 * 
	 * @param l - the redner layer
	 */
	public void setLayer(int l) {
		layer = l;
	}
	
	/**
	 * gets the render layer of the element this loader makes
	 * 
	 * @return the render layer of this element loader
	 */
	public int getLayer() {
		return layer;
	}
	
}