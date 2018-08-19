package embgine.graphics.shaders;

import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glUniform4f;

import static org.lwjgl.opengl.GL20.*;

/**
 * draws a circle in a box
 */
public class Cir2DShader extends Shader {
	
	private int sizeLoc;
	private int passColorLoc;
	
	@Override
	protected void sendUniforms(Object... params) {
		glUniform1f(sizeLoc, (float)params[0]);
		glUniform4f(passColorLoc, (float)params[1], (float)params[2], (float)params[3], (float)params[4]);
	}
	
	public Cir2DShader() {
		super("embgine/shaders/cir2d.vs", "embgine/shaders/cir2d.fs");
	}
	
	protected void getUniforms() {
		passColorLoc = glGetUniformLocation(program, "passColor");
		sizeLoc = glGetUniformLocation(program, "size");
	}

}
