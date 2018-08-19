package game.scripts;

import embgine.core.scripts.ObjectScript;
import embgine.graphics.Camera;
import embgine.graphics.Texture;
import embgine.graphics.shaders.Shader;
import embgine.graphics.shapes.Shape;

public class YouWinScript extends ObjectScript{

	private static Shape rect;
	private static Shader shader;
	private static Camera camera;
	private static Texture texture;
	
	public void render() {
		texture.bind();
		
		shader.enable();
		
		shader.setMvp(camera.getModelViewProjectionMatrix(camera.getModelMatrix(parent.getTransform())));
		
		rect.render();
		
		shader.disable();
		
		texture.unbind();
	}

	@Override
	public void start(Object... params) {
		
	}

	@Override
	public void update() {
		
	}

	@Override
	public void staticInit() {
		rect = Shape.RECT;
		shader = Shader.TEX2DSHADER;
		texture = new Texture("game/textures/you-win.png");
		camera = parent.getCamera();
	}

}
