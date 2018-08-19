package embgine.core;

import embgine.graphics.Transform;

public class HitBox {
	
	private Transform parent;
	
	private boolean drawn;
	
	private float  left;
	private float right;
	private float    up;
	private float  down;
	
	public HitBox(Transform p, int l, int r, int u, int d, boolean s) {
		parent = p;
		 left = l;
		right = r;
		   up = u;
		 down = d;
		 drawn = false;
	}
	
	public float getLeft() {
		return left * parent.wScale + parent.abcissa;
	}
	
	public float getRight() {
		return right * parent.wScale + parent.abcissa;
	}
	
	public float getUp() {
		return up * parent.hScale + parent.ordinate;
	}
	
	public float getDown() {
		return down * parent.hScale + parent.ordinate;
	}
	
	public float getJustLeft() {
		return left * parent.wScale;
	}
	
	public float getJustRight() {
		return right * parent.wScale;
	}
	
	public float getJustUp() {
		return up * parent.hScale;
	}
	
	public float getJustDown() {
		return down * parent.hScale;
	}
	
	public void setDrawn(boolean d) {
		drawn = d;
	}
	
	public void render() {
		if(drawn) {
			
		}
	}

}