package embgine.graphics;

import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glUniform4f;
import static org.lwjgl.opengl.GL20.glUniform1i;

import org.joml.Vector4f;

public class Fon2DShader extends Shader {
	
	private int charLoc;
	private int colorLoc;
	
	protected void subRoutine(Packet p) {
		glUniform1i(charLoc, (int)(p.p[0]));
		glUniform4f(colorLoc, p.r, p.g, p.b, p.a);
	}
	
	protected Fon2DShader() {
		super("embgine/shaders/fon2d.vs", "embgine/shaders/fon2d.fs");
		
	}
	
	protected void getUniforms() {
		charLoc = glGetUniformLocation(program, "character");
		colorLoc = glGetUniformLocation(program, "inColor");
	}
	
}
