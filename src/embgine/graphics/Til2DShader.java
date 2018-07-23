package embgine.graphics;

import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glUniform4f;

import org.joml.Vector4f;

public class Til2DShader extends Shader {
	
	private int texLoc;
	private int colorLoc;
	
	protected void subRoutine(Packet p) {
		glUniform4f(texLoc, p.x, p.y, p.z, p.w);
		glUniform4f(colorLoc, p.r, p.g, p.b, p.a);
	}
	
	protected Til2DShader() {
		super("embgine/shaders/til2d.vs", "embgine/shaders/til2d.fs");
		
	}
	
	protected void getUniforms() {
		texLoc = glGetUniformLocation(program, "frame");
		colorLoc = glGetUniformLocation(program, "inColor");
	}
	
}
