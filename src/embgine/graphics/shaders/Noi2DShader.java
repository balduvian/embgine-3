package embgine.graphics.shaders;

import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glUniform4f;

import static org.lwjgl.opengl.GL20.glUniform2f;

public class Noi2DShader extends Shader {
	
	private int passColorLoc;
	private int inPosLoc;
	
	protected Noi2DShader() {
		super("embgine/shaders/noi2d.vs", "embgine/shaders/noi2d.fs", 6);
	}
	
	protected void getUniforms() {
		passColorLoc = glGetUniformLocation(program, "passColor");
		inPosLoc = glGetUniformLocation(program, "inPos");
	}

	@Override
	protected void subRoutine(float[] p) {
		glUniform4f(passColorLoc, p[2], p[3], p[4], p[5]);
		glUniform2f(inPosLoc, p[0], p[1]);
	}
}
