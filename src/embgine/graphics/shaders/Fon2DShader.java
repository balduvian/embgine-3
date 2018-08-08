package embgine.graphics.shaders;

import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glUniform4f;
import static org.lwjgl.opengl.GL20.glUniform1i;

public class Fon2DShader extends Shader {
	
	private int charLoc;
	private int colorLoc;
	
	protected void subRoutine(float[] p) {
		glUniform1i(charLoc, (int)(p[4]));
		glUniform4f(colorLoc, p[0], p[1], p[2], p[3]);
	}
	
	protected Fon2DShader() {
		super("embgine/shaders/fon2d.vs", "embgine/shaders/fon2d.fs", 5);
		
	}
	
	protected void getUniforms() {
		charLoc = glGetUniformLocation(program, "character");
		colorLoc = glGetUniformLocation(program, "inColor");
	}
	
}
