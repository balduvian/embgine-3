package embgine.graphics.shaders;

import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glUniform4f;

public class Til2DShader extends Shader {
	
	private int texLoc;
	private int colorLoc;
	
	protected void subRoutine(float[] p) {
		glUniform4f(texLoc, p[0], p[1], p[2], p[3]);
		glUniform4f(colorLoc, p[4], p[5], p[6], p[7]);
	}
	
	public Til2DShader() {
		super("embgine/shaders/til2d.vs", "embgine/shaders/til2d.fs", 8);
		
	}
	
	protected void getUniforms() {
		texLoc = glGetUniformLocation(program, "frame");
		colorLoc = glGetUniformLocation(program, "inColor");
	}
	
}
