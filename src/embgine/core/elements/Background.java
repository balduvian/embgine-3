package embgine.core.elements;

import embgine.graphics.Camera;
import embgine.graphics.Texture;
import embgine.graphics.Transform;
import embgine.graphics.shaders.Shader;
import embgine.graphics.shaders.Til2DShader;
import embgine.graphics.shapes.Shape;

public class Background extends Element{
	
	private static Camera camera;
	private static Til2DShader shader;
	private static Shape rect;
	
	private Texture texture;
	private float parallax;
	private float r, g, b, a;
	
	public Background(boolean enabled, int type, Texture tex, float w, float h, float x, float y, float par, int l) {
		super(new Transform(w, h, x, y), null, enabled, type, l);
		texture = tex;
		parallax = par;
		r = 1;
		g = 1;
		b = 1;
		a = 1;
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
			
			texture.bind();

			shader.setMvp(camera.getModelProjectionMatrix(camera.getModelMatrix(ct)));
			
			shader.enable(1, 1, (float)(ct.abcissa * parallax - transform.abcissa) / ct.getWidth(), (float)(ct.ordinate * parallax - transform.ordinate) / ct.getHeight(), r, g, b, a);
			
			rect.render();
			
			shader.disable();
			
			texture.unbind();
		}
	}
	
}