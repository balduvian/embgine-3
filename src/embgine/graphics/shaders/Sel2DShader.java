package embgine.graphics.shaders;

import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glUniform1f;

public class Sel2DShader extends Shader {
	
	private int randLoc;
	private int enableLoc;
	
	protected void sendUniforms(Object... params) {
		glUniform1f(randLoc, (float)params[0]);
		glUniform1f(enableLoc, (float)params[1]);
	}
	
	public Sel2DShader() {
		super("embgine/shaders/sel2d.vs", "embgine/shaders/sel2d.fs");
	}
	
	protected void getUniforms() {
		randLoc = glGetUniformLocation(program, "rand");
		enableLoc = glGetUniformLocation(program, "enabled");
	}
	
}