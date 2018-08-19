package embgine.graphics.shaders;

import static org.lwjgl.opengl.GL20.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.joml.Matrix4f;

abstract public class Shader {
	
	/**
	 * the program ID of the shader
	 */
	protected int program;
	
	/**
	 * all these shaders use the model view projection matrix, this is the uniform location
	 */
	protected int mvpLoc;
	
	public static Til2DShader TIL2DSHADER;
	public static Col2DShader COL2DSHADER;
	public static Cir2DShader CIR2DSHADER;
	public static Tex2DShader TEX2DSHADER;
	public static Vhs2DShader VHS2DSHADER;
	public static Sel2DShader SEL2DSHADER;
	public static Cli2DShader CLI2DSHADER;
	
	/**
	 * statically initializes all the shaders for the game
	 */
	public static void init() {
		COL2DSHADER = new Col2DShader();
		TIL2DSHADER = new Til2DShader();
		CIR2DSHADER = new Cir2DShader();
		TEX2DSHADER = new Tex2DShader();
		VHS2DSHADER = new Vhs2DShader();
		SEL2DSHADER = new Sel2DShader();
		CLI2DSHADER = new Cli2DShader();
	}
	
	/**
	 * creates a shader
	 * 
	 * @param vertPath - path to the base code of the vertex shader
	 * @param fragPath - path to the base code of the fragment shader
	 */
	protected Shader(String vertPath, String fragPath) {
		program = glCreateProgram();
		int vert = loadShader(vertPath, GL_VERTEX_SHADER);
		int frag = loadShader(fragPath, GL_FRAGMENT_SHADER);
		glAttachShader(program, vert);
		glAttachShader(program, frag);
		glLinkProgram(program);
		glDetachShader(program, vert);
		glDetachShader(program, frag);
		glDeleteShader(vert);
		glDeleteShader(frag);
		
		mvpLoc = glGetUniformLocation(program, "mvp");
		
		getUniforms();
	}
	
	private int loadShader(String path, int type) {
		StringBuilder build = new StringBuilder();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(this.getClass().getClassLoader().getResource(path).openStream()));
			String line;
			while((line = br.readLine()) != null) {
				build.append(line);
				build.append('\n');
			}
			br.close();
		} catch(IOException ex) {
			ex.printStackTrace();
		}
		String src = build.toString();
		int shader = glCreateShader(type);
		glShaderSource(shader, src);
		
		glCompileShader(shader);
		if(glGetShaderi(shader,GL_COMPILE_STATUS) != 1) {
			throw new RuntimeException("Failed to compile shader | " + path + " | " + type + " | " + glGetShaderInfoLog(shader));
		}
		return shader;
	}
	
	protected abstract void getUniforms();
	
	public void setMvp(Matrix4f mvp) {
		glUniformMatrix4fv(mvpLoc, false, mvp.get(new float[16]));
	}
	
	/**
	 * starts to use the shader
	 * 
	 * @param params - literally anything you want you can pass into the shader
	 */
	public void enable(Object... params) {
		glUseProgram(program);
		sendUniforms(params);
	}
	
	/**
	 * override this method to pass in uniforms to the shader
	 * 
	 * @param params - the parameters passed into the shader. Do with them what you will
	 */
	abstract protected void sendUniforms(Object... params);
	
	/**
	 * call this after rendering is done to stop using the shader
	 */
	public void disable() {
		glUseProgram(0);
	}
	
	/**
	 * deletes the shader from opengl
	 */
	public void destroy() {
		glDeleteProgram(program);
	}

}