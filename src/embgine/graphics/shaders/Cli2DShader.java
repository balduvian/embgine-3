package embgine.graphics.shaders;

import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL20.*;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class Cli2DShader extends Shader {
	
	private int texLoc;
	private int colorLoc;
	private int planeLoc;
	private int viewLoc;
	
	protected void subRoutine(float[] p) {
		glUniform4f(texLoc, p[0], p[1], p[2], p[3]);
		glUniform4f(colorLoc, p[4], p[5], p[6], p[7]);
		glUniform4f(planeLoc, p[8], p[9], p[10], p[11]);
		float[] array = new float[16];
		for(int i = 0; i < 16; ++i) {
			array[i] = p[i + 12];
		}
		glUniformMatrix4fv(viewLoc, false, array);
	}
	
	public Cli2DShader() {
		super("embgine/shaders/cli2d.vs", "embgine/shaders/cli2d.fs", 28);
		
	}
	
	protected void getUniforms() {
		texLoc = glGetUniformLocation(program, "frame");
		colorLoc = glGetUniformLocation(program, "inColor");
		planeLoc = glGetUniformLocation(program, "plane");
		viewLoc = glGetUniformLocation(program, "view");
	}
	
}
