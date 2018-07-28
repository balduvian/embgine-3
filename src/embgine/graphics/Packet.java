package embgine.graphics;

public class Packet {
	
	public float x, y, z, w;
	public float p[];
	
	public Packet(float... pp) {
		x = 1;
		y = 1;
		z = 1;
		w = 1;
		p = pp;
	}
	
	public void giveFrame(float xx, float yy, float zz, float ww) {
		x = xx;
		y = yy;
		z = zz;
		w = ww;
	}

	public void giveParam(int i, float v) {
		p[i] = v;
	}
	
	public void setParams(float... pp) {
		p = pp;
	}
}