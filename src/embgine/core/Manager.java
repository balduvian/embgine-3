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
	
	public void update(Camera c) {
		for(int i = 0; i < last; ++i) {
			Element e = collection[i];
			if(e != null) {
				e.update(c);
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
			
			if(last < first) {
				last = first;
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
	
	public void clearType(Class<? extends Element> e) {
		for(int i = 0; i < last; ++i) {
			if(collection[i].getClass() == e) {
				remove(i);
			}
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
	
}