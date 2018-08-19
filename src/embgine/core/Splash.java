package embgine.core;

import org.joml.Matrix4f;

import embgine.graphics.Camera;
import embgine.graphics.Texture;
import embgine.graphics.Transform;
import embgine.graphics.Window;
import embgine.graphics.shaders.Shader;
import embgine.graphics.shapes.Shape;

public class Splash {
	
	private static final double SHOW_TIME = 3;
	
	private double timer;
	private Camera camera;
	
	private Transform gt;
	
	private Texture logoTex;
	private Font logoFont;
	
	private Shape rect;
	private Shader colorShader;
	private Shader tileShader;
	
	private float width;
	private float height;
	
	private char[][] madeWithEmbgine;
	
	public Splash(Camera c) {
		camera = c;
		gt = new Transform();
		rect = Shape.RECT;
		colorShader = Shader.COL2DSHADER;
		tileShader = Shader.TIL2DSHADER;
		
		Transform ct = camera.getTransform();
		width = ct.width;
		height = ct.height;
		
		logoTex = new Texture("embgine/standard/logo.png");
		logoFont = new Font(new Texture("embgine/standard/text.png", 16, 8), 9, 5);
		
		timer = SHOW_TIME;
		
		madeWithEmbgine = new char[][] {{'M','a','d','e',' ','W','i','t','h',' ','E','m','b','g','i','n','e'}};
	}
	
	public boolean update(Window w) {
		camera.update();
		timer -= Base.time;
		if(w.keyPressed(81)) {
			return(true);
		}else {
			return (timer <= 0);
		}
	}
	
	public void render() {
		
		gt.set(0, 0, width, height);
		colorShader.enable(1f, 1f, 1f, 1f);
		colorShader.setMvp(camera.getModelProjectionMatrix(camera.getModelMatrix(gt)));
		rect.render();
		colorShader.disable();

		gt.set(96, 40, 64, 64);
		logoTex.bind();
		tileShader.enable(1f, 1f, 1f, 1f, -1f, -1f, -1f, 1f);
		tileShader.setMvp(camera.getModelProjectionMatrix(camera.getModelMatrix(gt)));
		rect.render();
		tileShader.disable();
		logoTex.unbind();
		
		gt.set(128, 112, 9, 9);
		logoFont.render(gt, true, madeWithEmbgine, true, 0f, 0f, 0f, 1f);
		
	}
}