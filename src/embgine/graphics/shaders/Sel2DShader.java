package embgine.graphics.shaders;

import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glUniform1f;

public class Sel2DShader extends Shader {
	
	private int randLoc;
	private int enableLoc;
	
	protected void subRoutine(float[] p) {
		glUniform1f(randLoc, p[0]);
		glUniform1f(enableLoc, p[1]);
	}
	
	public Sel2DShader() {
		super("embgine/shaders/sel2d.vs", "embgine/shaders/sel2d.fs", 2);
	}
	
	protected void getUniforms() {
		randLoc = glGetUniformLocation(program, "rand");
		enableLoc = glGetUniformLocation(program, "enabled");
	}
	
}