package embgine.graphics.shaders;

import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glUniform4f;
import static org.lwjgl.opengl.GL20.glUniform1i;

import embgine.graphics.Packet;

public class Fon2DShader extends Shader {
	
	private int charLoc;
	private int colorLoc;
	
	protected void subRoutine(Packet p) {
		glUniform1i(charLoc, (int)(p.p[4]));
		glUniform4f(colorLoc, p.p[0], p.p[1], p.p[2], p.p[3]);
	}
	
	protected Fon2DShader() {
		super("embgine/shaders/fon2d.vs", "embgine/shaders/fon2d.fs");
		
	}
	
	protected void getUniforms() {
		charLoc = glGetUniformLocation(program, "character");
		colorLoc = glGetUniformLocation(program, "inColor");
	}
	
}
