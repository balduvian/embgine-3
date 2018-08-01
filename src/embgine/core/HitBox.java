package embgine.core;

public class HitBox {
	
	public float  left;
	public float right;
	public float    up;
	public float  down;
	
	private HitBox(float l, float r, float u, float d) {
		 left = l;
		right = r;
		   up = u;
		 down = d;
	}
	
	//creates a hitbox based on the absolute positions of the bounds
	public static HitBox createB(float l, float r, float u, float d) {
		return new HitBox(l, r, u, d);
	}

	//creates a hitbox by finding the bounds of a square with a certain center position and dimensions
	public static HitBox createT(float x, float y, float w, float h) {
		float halfW = w / 2;
		float halfH = h / 2;
		return new HitBox(
			x - halfW,
			x + halfW,
			y - halfH,
			y + halfH
		);
	}
	
}