package embgine.graphics;

import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glUniform4f;

public class Col2DShader extends Shader {

	private int colorLoc;
	
	protected Col2DShader() {
		super("embgine/shaders/col2d.vs", "embgine/shaders/col2d.fs");
		
	}
	
	protected void getUniforms() {
		colorLoc = glGetUniformLocation(program, "color");
	}

	@Override
	protected void subRoutine(Packet p) {
		glUniform4f(colorLoc, p.r, p.g, p.b, p.a);
	}
	
}
