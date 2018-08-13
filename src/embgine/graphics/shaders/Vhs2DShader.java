package embgine.graphics.shaders;

import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glUniform1f;

public class Vhs2DShader extends Shader {
	
	private int randLoc;
	private int scanLoc;
	
	protected void subRoutine(float[] p) {
		glUniform1f(randLoc, p[0]);
		glUniform1f(scanLoc, p[1]);
	}
	
	public Vhs2DShader() {
		super("embgine/shaders/vhs2d.vs", "embgine/shaders/vhs2d.fs", 0);
	}
	
	protected void getUniforms() {
		randLoc = glGetUniformLocation(program, "rand");
		scanLoc = glGetUniformLocation(program, "scanline");
	}
	
}