package embgine.graphics.shaders;

import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glUniform4f;

public class Til2DShader extends Shader {
	
	private int texLoc;
	private int colorLoc;
	
	protected void sendUniforms(Object... params) {
		glUniform4f(texLoc, (float)params[0], (float)params[1], (float)params[2], (float)params[3]);
		glUniform4f(colorLoc, (float)params[4], (float)params[5], (float)params[6], (float)params[7]);
	}
	
	public Til2DShader() {
		super("embgine/shaders/til2d.vs", "embgine/shaders/til2d.fs");
		
	}
	
	protected void getUniforms() {
		texLoc = glGetUniformLocation(program, "frame");
		colorLoc = glGetUniformLocation(program, "inColor");
	}
	
}
