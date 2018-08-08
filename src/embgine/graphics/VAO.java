package embgine.graphics;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.GL_STREAM_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

import org.lwjgl.opengl.GL11;

public class VAO {
	
	private int mode;
	private int numAttribs;
	private int ibo;
	private int vao;
	private int count;
	private int[] vbos = new int[15];
	
	public VAO(float[] vertices, int[] indices, int drawMode) {
		vao = glGenVertexArrays();
		glBindVertexArray(vao);
		addAttrib(vertices, 3);
		
		ibo = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);
		
		count = indices.length;
		
		mode = drawMode;
	}
	
	public void addAttrib(float[] f, int s) {
		int vbo = glGenBuffers();
		vbos[numAttribs] = vbo;
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glBufferData(GL_ARRAY_BUFFER, f, GL_STATIC_DRAW);
		glVertexAttribPointer(numAttribs, s, GL_FLOAT, false, 0, 0);
		++numAttribs;
	}
	
	public void render() {
		glBindVertexArray(vao);
		for(int i = 0; i < numAttribs; ++i) {
			glEnableVertexAttribArray(i);
		}
		glDrawElements(mode, count, GL_UNSIGNED_INT, 0);
		for(int i = 0; i < numAttribs; ++i) {
			glDisableVertexAttribArray(i);
		}
	}
	
	public void destroy() {
		for(int vbo: vbos) {
			glDeleteBuffers(vbo);
		}
		glDeleteBuffers(ibo);
		glDeleteVertexArrays(vao);
	}
	
}
