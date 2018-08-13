package embgine.core.elements;

import org.joml.Vector4f;

import embgine.core.Index;
import embgine.graphics.Camera;
import embgine.graphics.Texture;
import embgine.graphics.Transform;
import embgine.graphics.infos.Info;
import embgine.graphics.infos.TilInfo;
import embgine.graphics.shaders.Shader;
import embgine.graphics.shaders.Til2DShader;
import embgine.graphics.shapes.Shape;

public class Background extends Element{
	
	private static Camera camera;
	private static Til2DShader shader;
	private static Shape rect;
	
	private Texture texture;
	private int width;
	private int height;
	private int x;
	private int y;
	private float parallax;
	private float r, g, b, a;
	private int layer;
	
	public Background(boolean enabled, int type, Texture tex, int w, int h, int levX, int levY, float par, int l) {
		super(new Transform(), null, enabled, type);
		texture = tex;
		width = w;
		height = h;
		x = levX;
		y = levY;
		parallax = par;
		r = 1;
		g = 1;
		b = 1;
		a = 1;
		layer = l;
	}
	
	public static void init(Camera c) {
		camera = c;
		shader = Shader.TIL2DSHADER;
		rect = Shape.RECT;
	}
	
	public void setColor(float red, float green, float blue, float alpha) {
		r = red;
		g = green;
		b = blue;
		a = alpha;
	}

	public boolean onScreenUpdate(Camera camera) {
		onScreen = true;
		return true;
	}

	public void subUpdate() {

	}

	public void subRender(int l) {
		if(layer == l) {
			Transform ct = camera.getTransform();
			
			transform.set(ct.getX(), ct.getY(), ct.getWidth(), ct.getHeight());
			
			rect.getTransform().set(transform);
			
			texture.bind();

			shader.enable(1, 1, (float)(ct.getX() * parallax - x) / ct.getWidth(), (float)(ct.getY() * parallax - y) / ct.getHeight(), r, g, b, a);
			
			shader.setMvp(rect.getMatrix());
			
			rect.getVAO().render();
			
			shader.disable();
			texture.unbind();
		}
	}
	
}