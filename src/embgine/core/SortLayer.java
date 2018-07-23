package embgine.core;

import java.util.ArrayList;

import org.joml.Vector3f;

import embgine.graphics.Camera;
import embgine.graphics.Transform;

public class SortLayer {
	
	private int len;
	private ArrayList<GameObject> entities;
	
	public SortLayer() {
		len = 0;
		entities = new ArrayList<GameObject>();
	}
	
	public void update() {
		for(int i = 0; i < len; ++i) {
			entities.get(i).update();
		}
	}
	
	public void render() {
		for(int i = 0; i < len; ++i) {
			entities.get(i).render();
		}
	}
	
	public void add(GameObject o) {
		o.setIndex(len);
		entities.add(o);
		++len;
	}
	
	public void remove(int i) {
		entities.remove(i);
		--len;
		for(int g = i; g < len; ++g) {
			entities.get(g).setIndex(entities.get(g).getIndex()-1);
		}
	}
	
	public void clear() {
		for(int g = len-1; g > -1; --g) {
			GameObject i = entities.get(g);
			if(!i.isEternal()) {
				remove(g);
			}
		}
	}
	
	public void sendForward(int i) {
		
		GameObject temp = entities.get(i);
		remove(i);
		add(temp);
		
	}
}
