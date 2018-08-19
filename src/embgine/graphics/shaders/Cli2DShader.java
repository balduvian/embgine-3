package embgine.graphics.shaders;

import static org.lwjgl.opengl.GL20.*;

import org.joml.Matrix4f;

public class Cli2DShader extends Shader {
	
	private int texLoc;
	private int colorLoc;
	private int planeLoc;
	private int modelLoc;
	
	protected void sendUniforms(Object... params) {
		glUniform4f(texLoc, (float)params[0], (float)params[1], (float)params[2], (float)params[3]);
		glUniform4f(colorLoc, (float)params[4], (float)params[5], (float)params[6], (float)params[7]);
		glUniform4f(planeLoc, (float)params[8], (float)params[9], (float)params[10], (float)params[11]);
		glUniformMatrix4fv(modelLoc, false, ((Matrix4f)params[12]).get(new float[16]));
	}
	
	public Cli2DShader() {
		super("embgine/shaders/cli2d.vs", "embgine/shaders/cli2d.fs");
	}
	
	protected void getUniforms() {
		texLoc = glGetUniformLocation(program, "frame");
		colorLoc = glGetUniformLocation(program, "inColor");
		planeLoc = glGetUniformLocation(program, "plane");
		modelLoc = glGetUniformLocation(program, "model");
	}
	
}
