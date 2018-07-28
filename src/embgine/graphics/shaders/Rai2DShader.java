package embgine.graphics.shaders;

import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glUniform4f;

import embgine.graphics.Packet;

import static org.lwjgl.opengl.GL20.glUniform1f;

public class Rai2DShader extends Shader {
	
	private int enableLoc;
	private int texLoc;
	private int colorLoc;
	
	private double timer;//used internally
	
	public Rai2DShader() {
		super("embgine/shaders/rai2d.vs", "embgine/shaders/rai2d.fs");
		
	}
	
	protected void getUniforms() {
		colorLoc = glGetUniformLocation(program, "gradient");
		enableLoc = glGetUniformLocation(program, "enable");
		texLoc = glGetUniformLocation(program, "frame");
	}

	@Override
	protected void subRoutine(Packet p) {
		glUniform4f(texLoc, p.x, p.y, p.z, p.w);
		glUniform1f(enableLoc, p.p[0]);
		
		timer = (p.p[0]) % (Math.PI*2);
		
		glUniform1f(colorLoc, (float)timer);
	}
	
}
