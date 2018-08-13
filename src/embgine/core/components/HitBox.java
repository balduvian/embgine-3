package embgine.core.components;

import embgine.graphics.Transform;

public class HitBox extends Component{
	
	private boolean drawn;
	
	private int  left;
	private int right;
	private int    up;
	private int  down;
	
	private HitBox(int l, int r, int u, int d, boolean s) {
		super(new Transform(), s);
		 left = l;
		right = r;
		   up = u;
		 down = d;
		 drawn = false;
	}
	
	//creates a hitbox based on the absolute positions of the bounds
	public static HitBox createB(int l, int r, int u, int d, boolean s) {
		return new HitBox(l, r, u, d, s);
	}

	//creates a hitbox by finding the bounds of a square with a certain center position and dimensions
	public static HitBox createT(int x, int y, int w, int h, boolean s) {

		return new HitBox(
			x    ,
			x + w,
			y    ,
			y + h,
			s
		);
	}
	
	public int getLeft() {
		return Math.round(left * transform.getXScale() + transform.getX());
	}
	
	public int getRight() {
		return Math.round(right * transform.getXScale() + transform.getX());
	}
	
	public int getUp() {
		return Math.round(up * transform.getYScale() + transform.getY());
	}
	
	public int getDown() {
		return Math.round(down * transform.getYScale() + transform.getY());
	}
	
	public int getJustLeft() {
		return Math.round(left * transform.getYScale());
	}
	
	public int getJustRight() {
		return Math.round(right * transform.getYScale());
	}
	
	public int getJustUp() {
		return Math.round(up * transform.getYScale());
	}
	
	public int getJustDown() {
		return Math.round(down * transform.getYScale());
	}
	
	public void setDrawn(boolean d) {
		drawn = d;
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