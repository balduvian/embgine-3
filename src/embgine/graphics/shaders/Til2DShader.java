package embgine.graphics.shaders;

import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glUniform4f;

import org.joml.Vector4f;

import embgine.graphics.Packet;

public class Til2DShader extends Shader {
	
	private int texLoc;
	private int colorLoc;
	
	protected void subRoutine(Packet p) {
		glUniform4f(texLoc, p.x, p.y, p.z, p.w);
		glUniform4f(colorLoc, p.p[0], p.p[1], p.p[2], p.p[3]);
	}
	
	public Til2DShader() {
		super("embgine/shaders/til2d.vs", "embgine/shaders/til2d.fs");
		
	}
	
	protected void getUniforms() {
		texLoc = glGetUniformLocation(program, "frame");
		colorLoc = glGetUniformLocation(program, "inColor");
	}
	
}
