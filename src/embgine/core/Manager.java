package embgine.core;

import embgine.core.elements.Element;
import embgine.graphics.Camera;

public class Manager{
	
	private int size;
	
	private Element[] collection;
	
	private int first; //the first position an object can go in
	
	private int last; //the last position an object exists in
	
	private boolean full;

	public Manager(int s) {
		size = s;
		
		collection = new Element[size];
		
		first = 0;
		last = 0;
		
		full = false;
	}
	
	public Element[] onScreenUpdate(Camera camera) {
		int length = 0;
		Element[] temp = new Element[size];
		for(int i = 0; i < last; ++i) {
			Element e = collection[i];
			if(e != null && e.onScreenUpdate(camera)) {
				temp[length] = e;
				++length;
			}
		}
		Element[] ret = new Element[length];
		for(int i = 0; i < length; ++i) {
			ret[i] = temp[i];
		}
		return ret;
	}
	
	public void update() {
		for(int i = 0; i < last; ++i) {
			Element e = collection[i];
			if(e != null) {
				e.update();
			}
		}
	}
	
	public void render(int layer) {
		for(int i = 0; i < last; ++i) {
			Element e = collection[i];
			if(e != null) {
				e.render(layer);
			}
		}
	}
	
	public void add(Element o) {
		if(!full) {
			
			o.setIndex(first);
			
			collection[first] = o;
			
			if(last < first + 1) {
				last = first + 1;
			}
			
			forwardFirst(first + 1);
		}
	}
	
	public void remove(int ind) {
		collection[ind] = null;
		
		forwardFirst(0);
		
		if(ind == last) {
			last = 0;
			for(int i = last - 1; i > -1; --i) {
				if(collection[i] != null) {
					last = i;
					break;
				}
			}
		}
		
		full = false;
	}
	
	private void forwardFirst(int start) {
		over: {
			for(int i = start; i < size; ++i) {
				if(collection[i] == null) {
					first = i;
					break over;
				}
			}
			full = true;
		}
	}
	
	public void clear() {
		for(int i = 0; i < last; ++i) {
			collection[i] = null;
		}
		
		first = 0;
		last = 0;
		
		full = false;
	}
	
	public void getLast() {
		System.out.println("last: " + last);
	}
	
}