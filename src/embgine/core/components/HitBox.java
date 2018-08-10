package embgine.core.components;

import embgine.graphics.Transform;

public class HitBox extends Component{
	
	private boolean drawn;
	
	private float  left;
	private float right;
	private float    up;
	private float  down;
	
	private HitBox(float l, float r, float u, float d, boolean s) {
		super(new Transform(), s);
		 left = l;
		right = r;
		   up = u;
		 down = d;
		 drawn = false;
	}
	
	//creates a hitbox based on the absolute positions of the bounds
	public static HitBox createB(float l, float r, float u, float d, boolean s) {
		return new HitBox(l, r, u, d, s);
	}

	//creates a hitbox by finding the bounds of a square with a certain center position and dimensions
	public static HitBox createT(float x, float y, float w, float h, boolean s) {
		float halfW = w / 2;
		float halfH = h / 2;
		return new HitBox(
			x - halfW,
			x + halfW,
			y - halfH,
			y + halfH,
			s
		);
	}
	
	public float getLeft() {
		return left * transform.getXScale() + transform.getX();
	}
	
	public float getRight() {
		return right * transform.getXScale() + transform.getX();
	}
	
	public float getUp() {
		return up * transform.getYScale() + transform.getY();
	}
	
	public float getDown() {
		return down * transform.getYScale() + transform.getY();
	}
	
	public float getJustDown() {
		return down * transform.getYScale();
	}
	
	public void setDrawn(boolean d) {
		drawn = d;
	}
	
	public void scale(float xf, float yf) {
		left *= xf;
		right *= xf;
		up *= yf;
		down *= yf;
	}
	
	@Override
	public void update() {
		if(tSync) {
			transform = new Transform(parent.getTransform());
		}
	}

	@Override
	public void render() {
		if(drawn) {
			
		}
	}

	@Override
	public Component clone() {
		return new HitBox(left, right, up, down, tSync);
	}
	
}