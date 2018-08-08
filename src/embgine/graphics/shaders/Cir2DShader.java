package embgine.graphics.shaders;

import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glUniform4f;

import static org.lwjgl.opengl.GL20.*;

public class Cir2DShader extends Shader {
	
	private int sizeLoc;
	private int passColorLoc;
	
	public Cir2DShader() {
		super("embgine/shaders/cir2d.vs", "embgine/shaders/cir2d.fs", 5);
	}
	
	protected void getUniforms() {
		passColorLoc = glGetUniformLocation(program, "passColor");
		sizeLoc = glGetUniformLocation(program, "size");
	}

	@Override
	protected void subRoutine(float[] p) {
		glUniform4f(passColorLoc, p[1], p[2], p[3], p[4]);
		glUniform1f(sizeLoc, p[0]);
	}
}
