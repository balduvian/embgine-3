package embgine.core.components;

public class HitBox extends Component{
	
	private boolean drawn;
	
	private float  baseLeft;
	private float baseRight;
	private float    baseUp;
	private float  baseDown;
	
	public float  left;
	public float right;
	public float    up;
	public float  down;
	
	private HitBox(float l, float r, float u, float d) {
		 baseLeft = l;
		baseRight = r;
		   baseUp = u;
		 baseDown = d;
		 drawn = false;
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
	
	public void setDrawn(boolean d) {
		drawn = d;
	}
	
	private void scale() {
		float w = parent.getTransform().getWidth();
		float h = parent.getTransform().getHeight();
		left = baseLeft * w;
		right = baseRight * w;
		up = baseUp * h;
		down = baseDown * h;
	}
	
	@Override
	public void update() {
		scale();
	}

	@Override
	public void render() {
		if(drawn) {
			
		}
	}

	@Override
	public Component clone() {
		return new HitBox(baseLeft, baseRight, baseUp, baseDown);
	}
	
}