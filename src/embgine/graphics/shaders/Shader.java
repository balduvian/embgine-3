package embgine.graphics.shaders;

import static org.lwjgl.opengl.GL20.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.joml.Matrix4f;

abstract public class Shader {
	
	public static Til2DShader TIL2DSHADER;
	public static Col2DShader COL2DSHADER;
	public static Cir2DShader CIR2DSHADER;
	public static Tex2DShader TEX2DSHADER;
	public static Vhs2DShader VHS2DSHADER;
	public static Sel2DShader SEL2DSHADER;
	public static Cli2DShader CLI2DSHADER;
	
	protected int program;
	
	protected int mvpLoc;
	
	protected int numParams;
	
	public static void init() {
		COL2DSHADER = new Col2DShader();
		TIL2DSHADER = new Til2DShader();
		CIR2DSHADER = new Cir2DShader();
		TEX2DSHADER = new Tex2DShader();
		VHS2DSHADER = new Vhs2DShader();
		SEL2DSHADER = new Sel2DShader();
		CLI2DSHADER = new Cli2DShader();
	}
	
	protected Shader(String vertPath, String fragPath, int numP) {
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
		
		numParams = numP;
		
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
			throw new RuntimeException("Failed to compile shader | "+path+" | "+type+" | "+glGetShaderInfoLog(shader));
		}
		return shader;
	}
	
	protected abstract void getUniforms();
	
	public void setMvp(Matrix4f m) {
		glUniformMatrix4fv(mvpLoc, false, m.get(new float[16]));
	}
	
	//render with passing information
	public void enable(float... p) {
		glUseProgram(program);
		subRoutine(p);
	}
	
	//this is what each shader uses to pass information into the shader
	abstract protected void subRoutine(float[] p);
	
	//run the shader without passing anything or calling the sub routine
	public void enable() {
		glUseProgram(program);
	}
	
	public void disable() {
		glUseProgram(0);
	}
	
	public void destroy() {
		glDeleteProgram(program);
	}
	
	public int getNumParams() {
		return numParams;
	}
}
