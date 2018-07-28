package embgine.graphics.shaders;

import static org.lwjgl.opengl.GL20.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.joml.Matrix4f;

import embgine.graphics.Packet;

abstract public class Shader {
	
	public static Til2DShader TIL2DSHADER;
	public static Col2DShader COL2DSHADER;
	public static Cir2DShader CIR2DSHADER;
	public static Noi2DShader NOI2DSHADER;
	public static Rai2DShader RAI2DSHADER;
	public static Fon2DShader FON2DSHADER;
	public static Tex2DShader TEX2DSHADER;
	
	protected int program;
	
	protected int mvpLoc;
	
	public static void init() {
		COL2DSHADER = new Col2DShader();
		TIL2DSHADER = new Til2DShader();
		CIR2DSHADER = new Cir2DShader();
		NOI2DSHADER = new Noi2DShader();
		RAI2DSHADER = new Rai2DShader();
		FON2DSHADER = new Fon2DShader();
		TEX2DSHADER = new Tex2DShader();
	}
	
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
			throw new RuntimeException("Failed to compile shader | "+path+" | "+type+" | "+glGetShaderInfoLog(shader));
		}
		return shader;
	}
	
	protected abstract void getUniforms();
	
	public void setMvp(Matrix4f m) {
		glUniformMatrix4fv(mvpLoc, false, m.get(new float[16]));
	}
	
	//render with passing information
	public void enable(Packet p) {
		glUseProgram(program);
		subRoutine(p);
	}
	
	//this is what each shader uses to pass information into the shader
	abstract protected void subRoutine(Packet p);
	
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
	
}
