package embgine.graphics.shaders;

import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glUniform4f;

/**
 * draws a simple single color into a shape
 */
public class Col2DShader extends Shader {

	private int colorLoc;
	
	@Override
	protected void sendUniforms(Object... params) {
		glUniform4f(colorLoc, (float)params[0], (float)params[1], (float)params[2], (float)params[3]);
	}
	
	public Col2DShader() {
		super("embgine/shaders/col2d.vs", "embgine/shaders/col2d.fs");
	}
	
	protected void getUniforms() {
		colorLoc = glGetUniformLocation(program, "color");
	}

}
