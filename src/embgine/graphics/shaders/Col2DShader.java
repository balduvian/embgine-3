package embgine.graphics.shaders;

import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glUniform4f;

import embgine.graphics.Packet;

public class Col2DShader extends Shader {

	private int colorLoc;
	
	public Col2DShader() {
		super("embgine/shaders/col2d.vs", "embgine/shaders/col2d.fs");
		
	}
	
	protected void getUniforms() {
		colorLoc = glGetUniformLocation(program, "color");
	}

	@Override
	protected void subRoutine(Packet p) {
		glUniform4f(colorLoc, p.p[0], p.p[1], p.p[2], p.p[3]);
	}
	
}
