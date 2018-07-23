package embgine.graphics;

import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glUniform4f;
import static org.lwjgl.opengl.GL20.*;

public class Cir2DShader extends Shader {
	
	private int sizeLoc;
	private int passColorLoc;
	
	protected Cir2DShader() {
		super("embgine/shaders/cir2d.vs", "embgine/shaders/cir2d.fs");
	}
	
	protected void getUniforms() {
		passColorLoc = glGetUniformLocation(program, "passColor");
		sizeLoc = glGetUniformLocation(program, "size");
	}

	@Override
	protected void subRoutine(Packet p) {
		glUniform4f(passColorLoc, p.x, p.y, p.z, p.w);
		glUniform1f(sizeLoc, p.p[0]);
	}
}
