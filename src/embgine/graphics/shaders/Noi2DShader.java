package embgine.graphics.shaders;

import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glUniform4f;

import embgine.graphics.Packet;

import static org.lwjgl.opengl.GL20.glUniform2f;

public class Noi2DShader extends Shader {
	
	private int passColorLoc;
	private int inPosLoc;
	
	protected Noi2DShader() {
		super("embgine/shaders/noi2d.vs", "embgine/shaders/noi2d.fs");
	}
	
	protected void getUniforms() {
		passColorLoc = glGetUniformLocation(program, "passColor");
		inPosLoc = glGetUniformLocation(program, "inPos");
	}

	@Override
	protected void subRoutine(Packet p) {
		glUniform4f(passColorLoc, p.x, p.y, p.z, p.w);
		glUniform2f(inPosLoc, p.p[0], p.p[1]);
	}
}
