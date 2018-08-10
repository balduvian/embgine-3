package embgine.core;

import embgine.graphics.Camera;
import embgine.graphics.Texture;
import embgine.graphics.Transform;
import embgine.graphics.infos.TilInfo;

public class Background {
	
	public static final int MODE_TILE_ALL = 0;
	public static final int MODE_TILE_HORZ = 1;
	public static final int MODE_TILE_VERT = 2;
	
	private static Camera camera;
	private Transform transform;
	private Texture texture;
	private TilInfo info;
	private float level;
	private float parallax;
	private int mode;
	
	public Background(Transform tra, Texture tex, float lev, float par, int mod) {
		transform = tra;
		texture = tex;
		level = lev;
		parallax = par;
		mode = mod;
	}
	
	public void render() {
		switch(mode) {
			case(MODE_TILE_ALL):
				
				break;
			case(MODE_TILE_HORZ):
				
				break;
			case(MODE_TILE_VERT):
				
				break;
		}
	}
	
}