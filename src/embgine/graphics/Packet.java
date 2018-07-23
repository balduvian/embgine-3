package embgine.graphics;

import embgine.core.Base;

public class Packet {
	
	public float x, y, z, w, r, g, b, a;
	public double t;
	public float p[];
	
	public Packet(float xx, float yy, float zz, float ww, float rr, float gg, float bb, float aa, double tt, float[] pp) {
		x = xx;
		y = yy;
		z = zz;
		w = ww;
		r = rr;
		g = gg;
		b = bb;
		a = aa;
		t = tt;
		p = pp;
	}
	
	public Packet(int pl) {
		x = 1;
		y = 1;
		z = 1;
		w = 1;
		r = 1;
		g = 1;
		b = 1;
		a = 1;
		t = 0;
		p = new float[pl];
	}
	
	public void giveColor(float rr, float gg, float bb, float aa) {
		r = rr;
		g = gg;
		b = bb;
		a = aa;
	}
	
	public void giveColor(float rr, float gg, float bb) {
		r = rr;
		g = gg;
		b = bb;
	}
	
	public void giveFrame(float xx, float yy, float zz, float ww) {
		x = xx;
		y = yy;
		z = zz;
		w = ww;
	}
	
	public void giveTime() {
		t = Base.time;
	}

	public void giveParam(int i, float v) {
		p[i] = v;
	}
}